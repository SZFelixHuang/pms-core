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

@Entity(name = "StaffModel")
@Table(name = "PMS_STAFF")
public class StaffModel implements SearchModelAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "FNAME")
	private String FName;

	@Column(name = "MNAME")
	private String MName;

	@Column(name = "LNAME")
	private String LName;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "SCHOOL")
	private String school;

	@Column(name = "EDUCATION_DEGREE")
	private String degree;

	@Column(name = "MAJOR")
	private String major;

	@Column(name = "RHF")
	private String RHF;

	@Column(name = "SSN")
	private String SSN;

	@Column(name = "PIN")
	private String PIN;

	@Column(name = "TEL")
	private String tel;

	@Column(name = "SEX")
	@Enumerated(EnumType.ORDINAL)
	private UserSexEnum sex;

	@Column(name = "BIRTHDAY")
	private Date birthday;

	@Column(name = "CENSUS_ADDRESS")
	private String censusAddress;

	@Column(name = "CENSUS_REGISTER")
	private String censusRegister;

	@Column(name = "CURRENT_ADDRESS")
	private String currentAddress;

	@Column(name = "NATIONALITY")
	private String nationality;

	@Column(name = "HIREDATE")
	private Date hiredate;

	@Column(name = "CREATE_TIME")
	private Date createTime;

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

	public String getFName()
	{
		return FName;
	}

	public void setFName(String fName)
	{
		FName = fName;
	}

	public String getMName()
	{
		return MName;
	}

	public void setMName(String mName)
	{
		MName = mName;
	}

	public String getLName()
	{
		return LName;
	}

	public void setLName(String lName)
	{
		LName = lName;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getSchool()
	{
		return school;
	}

	public void setSchool(String school)
	{
		this.school = school;
	}

	public String getDegree()
	{
		return degree;
	}

	public void setDegree(String degree)
	{
		this.degree = degree;
	}

	public String getMajor()
	{
		return major;
	}

	public void setMajor(String major)
	{
		this.major = major;
	}

	public String getRHF()
	{
		return RHF;
	}

	public void setRHF(String rHF)
	{
		RHF = rHF;
	}

	public String getSSN()
	{
		return SSN;
	}

	public void setSSN(String sSN)
	{
		SSN = sSN;
	}

	public String getPIN()
	{
		return PIN;
	}

	public void setPIN(String pIN)
	{
		PIN = pIN;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public UserSexEnum getSex()
	{
		return sex;
	}

	public void setSex(UserSexEnum sex)
	{
		this.sex = sex;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	public String getCensusAddress()
	{
		return censusAddress;
	}

	public void setCensusAddress(String censusAddress)
	{
		this.censusAddress = censusAddress;
	}

	public String getCensusRegister()
	{
		return censusRegister;
	}

	public void setCensusRegister(String censusRegister)
	{
		this.censusRegister = censusRegister;
	}

	public String getCurrentAddress()
	{
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress)
	{
		this.currentAddress = currentAddress;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	public Date getHiredate()
	{
		return hiredate;
	}

	public void setHiredate(Date hiredate)
	{
		this.hiredate = hiredate;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
}