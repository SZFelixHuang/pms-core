package com.pms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "CustomizedFormValuesModel")
@Table(name = "PMS_CUSTOMZIED_FORM_VALUES")
public class CustomizedFormValuesModel implements SearchModelAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "KEY1")
	private String key1;

	@Column(name = "KEY2")
	private String key2;

	@Column(name = "KEY3")
	private String key3;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(name = "CUSTOMIZED_FORM_VALUES", columnDefinition = "CLOB")
	private String values;

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

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getKey1()
	{
		return key1;
	}

	public void setKey1(String key1)
	{
		this.key1 = key1;
	}

	public String getKey2()
	{
		return key2;
	}

	public void setKey2(String key2)
	{
		this.key2 = key2;
	}

	public String getKey3()
	{
		return key3;
	}

	public void setKey3(String key3)
	{
		this.key3 = key3;
	}

	public String getValues()
	{
		return values;
	}

	public void setValues(String values)
	{
		this.values = values;
	}
}