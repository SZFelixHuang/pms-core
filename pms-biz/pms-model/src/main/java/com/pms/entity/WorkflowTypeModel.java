package com.pms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "WorkflowTypeModel")
@Table(name = "PMS_WORKFLOW_TYPE")
public class WorkflowTypeModel implements SearchModelAware
{
	/** The id. */
	@Id
	private Integer id;

	/** The agency. */
	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "TYPE_NAME")
	private String name;

	@Column(name = "IS_ENABLE")
	private Boolean enable;

	@Column(name = "MASTER_PROCESS")
	private String masterProcess;

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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}

	public String getMasterProcess()
	{
		return masterProcess;
	}

	public void setMasterProcess(String masterProcess)
	{
		this.masterProcess = masterProcess;
	}
}
