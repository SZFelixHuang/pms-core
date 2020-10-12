package com.pms.entity.enums;

public enum SerialNumberTypeEnum
{
	CONSTANT, DATETIME, RANDOMSTR, RANDOMCHARS, RANDOMNUM;

	public String getLabel()
	{
		return this.toString();
	}
	
	public int getValue()
	{
		switch (this)
		{
			case CONSTANT:
				return 0;
			case DATETIME:
				return 1;
			case RANDOMSTR:
				return 2;
			case RANDOMCHARS:
				return 3;
			case RANDOMNUM:
				return 4;
		}
		return 0;
	}

	public static SerialNumberTypeEnum convert2Enum(int status)
	{
		switch (status)
		{
			case 0:
				return SerialNumberTypeEnum.CONSTANT;
			case 1:
				return SerialNumberTypeEnum.DATETIME;
			case 2:
				return SerialNumberTypeEnum.RANDOMSTR;
			case 3:
				return SerialNumberTypeEnum.RANDOMCHARS;
			case 4:
				return SerialNumberTypeEnum.RANDOMNUM;
		}
		return null;
	}
}