package com.pms.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ServiceMappingModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String agency;

	private ServiceModel service;

	private BrandBasicModel brandBasic;

	private BrandDetailModel brandDetail;

	private ArrayList<MaterialMappingModel> materialMappings;

	public String getAgency()
	{
		return agency;
	}

	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	public ServiceModel getService()
	{
		return service;
	}

	public void setService(ServiceModel service)
	{
		this.service = service;
	}

	public BrandBasicModel getBrandBasic()
	{
		return brandBasic;
	}

	public void setBrandBasic(BrandBasicModel brandBasic)
	{
		this.brandBasic = brandBasic;
	}

	public ArrayList<MaterialMappingModel> getMaterialMappings()
	{
		return materialMappings;
	}

	public void setMaterialMappings(ArrayList<MaterialMappingModel> materialMappings)
	{
		this.materialMappings = materialMappings;
	}

	public BrandDetailModel getBrandDetail()
	{
		return brandDetail;
	}

	public void setBrandDetail(BrandDetailModel brandDetail)
	{
		this.brandDetail = brandDetail;
	}
}