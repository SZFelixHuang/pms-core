package com.pms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "GisLocationModel")
@Table(name = "PMS_GIS_LOCATION")
public class GisLocationModel implements SearchModelAware
{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "LOCATION_NAME")
	private String locationName;

	@Column(name = "LOCATION_CATEGORY")
	private String locationCategory;

	@Column(name = "LOCATION_TYPE")
	private String locationType;
	
	@Column(name = "COORDINATE_X")
	private Double coordinateX;

	@Column(name = "COORDINATE_Y")
	private Double coordinateY;

	@Column(name = "KEY1")
	private String key1;

	@Column(name = "KEY2")
	private String key2;

	@Column(name = "KEY3")
	private String key3;

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

	public String getLocationName()
	{
		return locationName;
	}

	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}

	public String getLocationCategory()
	{
		return locationCategory;
	}

	public void setLocationCategory(String locationCategory)
	{
		this.locationCategory = locationCategory;
	}

	public String getLocationType()
	{
		return locationType;
	}

	public void setLocationType(String locationType)
	{
		this.locationType = locationType;
	}

	public Double getCoordinateX()
	{
		return coordinateX;
	}

	public void setCoordinateX(Double coordinateX)
	{
		this.coordinateX = coordinateX;
	}

	public Double getCoordinateY()
	{
		return coordinateY;
	}

	public void setCoordinateY(Double coordinateY)
	{
		this.coordinateY = coordinateY;
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
}