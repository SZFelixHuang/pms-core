package com.pms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "MaterialModel")
@Table(name = "PMS_MATERIALS")
public class MaterialModel implements SearchModelAware
{
	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "MATERIAL_NAME")
	private String materialName;

	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "MATERIAL_BRAND")
	private String materialBrand;

	@Column(name = "MATERIAL_TYPE")
	private String materialType;

	@Column(name = "MATERIAL_UNIT")
	private String materialUnit;

	@Column(name = "MATERIAL_ICON")
	private String materialIcon;

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

	public String getMaterialName()
	{
		return materialName;
	}

	public void setMaterialName(String materialName)
	{
		this.materialName = materialName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getMaterialBrand()
	{
		return materialBrand;
	}

	public void setMaterialBrand(String materialBrand)
	{
		this.materialBrand = materialBrand;
	}

	public String getMaterialType()
	{
		return materialType;
	}

	public void setMaterialType(String materialType)
	{
		this.materialType = materialType;
	}

	public String getMaterialIcon()
	{
		return materialIcon;
	}

	public void setMaterialIcon(String materialIcon)
	{
		this.materialIcon = materialIcon;
	}

	public String getMaterialUnit()
	{
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit)
	{
		this.materialUnit = materialUnit;
	}
}
