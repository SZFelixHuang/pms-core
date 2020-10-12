package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.SerialNumberModel;
import com.pms.entity.enums.SerialNumberTypeEnum;

public class SerialNumberService extends PMSService<SerialNumberModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public List<SerialNumberModel> getSerialNumbers(String agency) throws ServiceException
	{
		return this.invokeDAO("getSerialNumbers", List.class, agency);
	}

	public SerialNumberModel createSerialNumber(SerialNumberModel newSerialNumberModel) throws ServiceException
	{
		return this.invoke("direct:createSerialNumber", SerialNumberModel.class, newSerialNumberModel);
	}

	public SerialNumberModel updateSerialNumber(SerialNumberModel serialNumber) throws ServiceException
	{
		this.invokeDAO("deleteSerialNumberConstituents", Integer.class, serialNumber.getId());
		return this.createSerialNumber(serialNumber);
	}

	public SerialNumberModel getSerialNumber(Integer serialNumberId) throws ServiceException
	{
		return this.invoke("direct:getSerialNumberById", SerialNumberModel.class, serialNumberId);
	}

	@SuppressWarnings("unchecked")
	public List<SerialNumberModel> deleteSerialNumber(String agency, Integer serialNumberId) throws ServiceException
	{
		return this.invoke("direct:deleteSerialNumber", List.class, agency, serialNumberId);
	}

	public String generateSerialNumber(String agency, String category) throws ServiceException
	{
		SerialNumberModel serialNumber = this.invoke("direct:getSerialNumberByCategory", SerialNumberModel.class,
			agency, category);
		if (serialNumber != null)
		{
			StringBuilder newSerialNumber = new StringBuilder();
			for (SerialNumberModel contituent : serialNumber.getConstituents())
			{
				String contituentValue = contituent.getValue();
				if (SerialNumberTypeEnum.CONSTANT == contituent.getType())
				{
					newSerialNumber.append(contituentValue);
				}
				else if (SerialNumberTypeEnum.DATETIME == contituent.getType())
				{
					SimpleDateFormat format = new SimpleDateFormat(contituentValue);
					newSerialNumber.append(format.format(Calendar.getInstance().getTime()));
				}
				else if (SerialNumberTypeEnum.RANDOMSTR == contituent.getType())
				{
					int length = contituent.getLength() / 32 + (contituent.getLength() % 32 > 0 ? 1 : 0);
					StringBuilder randomStrBuilder = new StringBuilder();
					for (int index = 0; index < length; index++)
					{
						String randomStr = UUID.randomUUID().toString().replaceAll("-", "");
						randomStrBuilder.append(randomStr);
					}
					String randomStr = randomStrBuilder.substring(0, contituent.getLength());
					if (contituent.getValue().equals("[a-z][0-9]"))
					{
						randomStr = randomStr.toLowerCase();
					}
					else if (contituent.getValue().equals("[A-Z][0-9]"))
					{
						randomStr = randomStr.toUpperCase();
					}
					newSerialNumber.append(randomStr);
				}
				else if (SerialNumberTypeEnum.RANDOMNUM == contituent.getType())
				{
					int length = contituent.getLength() / 9 + (contituent.getLength() % 9 > 0 ? 1 : 0);
					StringBuilder randomNumStrBuilder = new StringBuilder();
					for (int index = 0; index < length; index++)
					{
						String randomNumStr = Integer.valueOf((int) (Math.random() * 1000000000)).toString();
						randomNumStrBuilder.append(randomNumStr);
					}
					newSerialNumber.append(randomNumStrBuilder.subSequence(0, contituent.getLength()));
				}
				else if (SerialNumberTypeEnum.RANDOMCHARS == contituent.getType())
				{
					String strTemplate = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM";
					if (contituent.getValue().equals("[a-z]"))
					{
						strTemplate = strTemplate.toLowerCase();
					}
					else if (contituent.getValue().equals("[A-Z]"))
					{
						strTemplate = strTemplate.toUpperCase();
					}
					Random random = new Random();
					for (int index = 0; index < contituent.getLength(); index++)
					{
						int number = random.nextInt(strTemplate.length());
						newSerialNumber.append(strTemplate.charAt(number));
					}
				}
			}
			return newSerialNumber.toString();
		}
		return null;
	}

	@Override
	public ActiveMQQueue getQueueBefore()
	{
		return systemQueueBefore;
	}

	@Override
	public ActiveMQQueue getQueueAfter()
	{
		return systemQueueAfter;
	}

	@Override
	public String beanName()
	{
		return "serialNumberDAO";
	}
}