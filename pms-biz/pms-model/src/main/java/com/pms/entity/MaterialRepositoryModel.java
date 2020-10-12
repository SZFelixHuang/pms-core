package com.pms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "MaterialRepositoryModel")
@Table(name = "PMS_MATERIALS_REPOSITORY")
public class MaterialRepositoryModel implements SearchModelAware
{
	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "SERIAL_NUMBER")
	private String serialNum;

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

	@Column(name = "PURCHASE_PRICE")
	private Float purchasePrice;

	@Column(name = "SALE_PRICE")
	private Float salePrice;

	@Column(name = "PURCHASE_DATE")
	private Date purchaseDate;

	@Column(name = "PURCHASE_AMOUNT")
	private Integer purchaseAmount;

	@Column(name = "SALED_AMOUNT")
	private Integer saledAmount;

	@Column(name = "PRODUCTION_DATE")
	private Date productionDate;

	@Column(name = "EXPIRATION_DATE")
	private Date expirationDate;

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

	public String getSerialNum()
	{
		return serialNum;
	}

	public void setSerialNum(String serialNum)
	{
		this.serialNum = serialNum;
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

	public Float getPurchasePrice()
	{
		return purchasePrice;
	}

	public void setPurchasePrice(Float purchasePrice)
	{
		this.purchasePrice = purchasePrice;
	}

	public Float getSalePrice()
	{
		return salePrice;
	}

	public void setSalePrice(Float salePrice)
	{
		this.salePrice = salePrice;
	}

	public Date getPurchaseDate()
	{
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}

	public Integer getPurchaseAmount()
	{
		return purchaseAmount;
	}

	public void setPurchaseAmount(Integer purchaseAmount)
	{
		this.purchaseAmount = purchaseAmount;
	}

	public Date getProductionDate()
	{
		return productionDate;
	}

	public void setProductionDate(Date productionDate)
	{
		this.productionDate = productionDate;
	}

	public Date getExpirationDate()
	{
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate)
	{
		this.expirationDate = expirationDate;
	}

	public Integer getSaledAmount()
	{
		return saledAmount;
	}

	public void setSaledAmount(Integer saledAmount)
	{
		this.saledAmount = saledAmount;
	}
}