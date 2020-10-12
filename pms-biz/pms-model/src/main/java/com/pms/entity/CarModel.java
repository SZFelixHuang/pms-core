package com.pms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "CarModel")
@Table(name = "PMS_CAR")
public class CarModel implements SearchModelAware
{

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "CAR_NUMBER")
	private String carNum;

	@Column(name = "BRAND")
	private String brand;

	@Column(name = "MODEL")
	private String model;

	@Column(name = "PUBLISH")
	private Integer publish;

	@Column(name = "OUTPUT_VOLUME")
	private Float outputVolume;

	@Column(name = "COLOR")
	private String color;

	@Column(name = "MILEAGE")
	private Integer mileage;

	@Column(name = "LOGO")
	private String logo;

	@Column(name = "NAME")
	private String name;

	@Column(name = "INLET_FORM")
	private String inletForm;

	@Column(name = "GEARBOX")
	private String gearbox;

	@Column(name = "PICTURE")
	private String picture;

	@Column(name = "CAR_TYPE")
	private String carType;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	@Override
	public String getAgency()
	{
		return agency;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getCarNum()
	{
		return carNum;
	}

	public void setCarNum(String carNum)
	{
		this.carNum = carNum;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public Integer getPublish()
	{
		return publish;
	}

	public void setPublish(Integer publish)
	{
		this.publish = publish;
	}

	public Float getOutputVolume()
	{
		return outputVolume;
	}

	public void setOutputVolume(Float outputVolume)
	{
		this.outputVolume = outputVolume;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public Integer getMileage()
	{
		return mileage;
	}

	public void setMileage(Integer mileage)
	{
		this.mileage = mileage;
	}

	public String getLogo()
	{
		return logo;
	}

	public void setLogo(String logo)
	{
		this.logo = logo;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture;
	}

	public String getCarType()
	{
		return carType;
	}

	public void setCarType(String carType)
	{
		this.carType = carType;
	}
}