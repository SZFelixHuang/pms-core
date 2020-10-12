package com.pms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;
import com.pms.entity.enums.UserSexEnum;

@Entity(name = "VipModel")
@Table(name = "PMS_VIP")
public class VipModel implements SearchModelAware
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "SERIAL_NUMBER")
	private String serialNum;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SEX")
	@Enumerated(EnumType.ORDINAL)
	private UserSexEnum sex;

	@Column(name = "HOME_ADDRESS")
	private String homeAddress;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "CAR_NUMBER")
	private String carNum;

	@Column(name = "TEL")
	private String tel;

	@Column(name = "REGISTER_DATE")
	private Date registerDate;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getAgency()
	{
		return agency;
	}

	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	public String getSerialNum()
	{
		return serialNum;
	}

	public void setSerialNum(String serialNum)
	{
		this.serialNum = serialNum;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public UserSexEnum getSex()
	{
		return sex;
	}

	public void setSex(UserSexEnum sex)
	{
		this.sex = sex;
	}

	public String getHomeAddress()
	{
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress)
	{
		this.homeAddress = homeAddress;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getCarNum()
	{
		return carNum;
	}

	public void setCarNum(String carNum)
	{
		this.carNum = carNum;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public Date getRegisterDate()
	{
		return registerDate;
	}

	public void setRegisterDate(Date registerDate)
	{
		this.registerDate = registerDate;
	}
}
