package com.pms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "CustomizedFormMappingModel")
@Table(name = "PMS_CUSTOMIZED_FORM_MAPPING")
public class CustomizedFormMappingModel implements SearchModelAware
{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "MAPPING_TYPE")
	private String mappingType;

	@ManyToOne
	@JoinColumn(name = "CUSTOMIZED_FORM_ID")
	private CustomizedFormModel customizedForm;

	@Column(name = "STATUS")
	private Boolean enable;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Override
	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	@Override
	public String getAgency()
	{
		return this.agency;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getMappingType()
	{
		return mappingType;
	}

	public void setMappingType(String mappingType)
	{
		this.mappingType = mappingType;
	}

	public CustomizedFormModel getCustomizedForm()
	{
		return customizedForm;
	}

	public void setCustomizedForm(CustomizedFormModel customizedForm)
	{
		this.customizedForm = customizedForm;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}
}
