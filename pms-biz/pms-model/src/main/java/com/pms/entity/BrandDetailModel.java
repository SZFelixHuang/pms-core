package com.pms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "BrandDetailModel")
@Table(name = "PMS_BRAND_DETAIL")
public class BrandDetailModel implements SearchModelAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "NAME")
	private String name;

	@Column(name = "COLORS")
	private String colors;

	@Column(name = "OUTPUT_VOLUME")
	private Float outputVolume;

	@Column(name = "INLET_FORM")
	private String inletForm;

	@Column(name = "GEARBOX")
	private String gearbox;

	@Column(name = "PICTURES")
	private String pictures;

	@ManyToOne
	@JoinColumn(name = "BRAND_BASIC_ID")
	private BrandBasicModel brandBasic;

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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getColors()
	{
		return colors;
	}

	public void setColors(String colors)
	{
		this.colors = colors;
	}

	public Float getOutputVolume()
	{
		return outputVolume;
	}

	public void setOutputVolume(Float outputVolume)
	{
		this.outputVolume = outputVolume;
	}

	public String getInletForm()
	{
		return inletForm;
	}

	public void setInletForm(String inletForm)
	{
		this.inletForm = inletForm;
	}

	public String getGearbox()
	{
		return gearbox;
	}

	public void setGearbox(String gearbox)
	{
		this.gearbox = gearbox;
	}

	public String getPictures()
	{
		return pictures;
	}

	public void setPictures(String pictures)
	{
		this.pictures = pictures;
	}

	public BrandBasicModel getBrandBasic()
	{
		return brandBasic;
	}

	public void setBrandBasic(BrandBasicModel brandBasic)
	{
		this.brandBasic = brandBasic;
	}
}
