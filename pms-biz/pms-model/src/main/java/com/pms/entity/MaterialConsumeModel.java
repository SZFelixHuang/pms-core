package com.pms.entity;

import java.io.Serializable;

public class MaterialConsumeModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MaterialModel material;

	private MaterialRepositoryModel materialRepository;

	private Integer consumedAmount;

	public MaterialRepositoryModel getMaterialRepository()
	{
		return materialRepository;
	}

	public void setMaterialRepository(MaterialRepositoryModel materialRepository)
	{
		this.materialRepository = materialRepository;
	}

	public Integer getConsumedAmount()
	{
		return consumedAmount;
	}

	public void setConsumedAmount(Integer consumedAmount)
	{
		this.consumedAmount = consumedAmount;
	}

	public MaterialModel getMaterial()
	{
		return material;
	}

	public void setMaterial(MaterialModel material)
	{
		this.material = material;
	}
}