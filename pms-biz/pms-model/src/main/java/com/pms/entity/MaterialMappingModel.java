package com.pms.entity;

import java.io.Serializable;

public class MaterialMappingModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MaterialModel material;

	private Integer materialAmount;

	public MaterialModel getMaterial()
	{
		return material;
	}

	public void setMaterial(MaterialModel material)
	{
		this.material = material;
	}

	public Integer getMaterialAmount()
	{
		return materialAmount;
	}

	public void setMaterialAmount(Integer materialAmount)
	{
		this.materialAmount = materialAmount;
	}
}