package com.pms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "WorkOrderTypeModel")
@Table(name = "PMS_WORK_ORDER_TYPE")
public class WorkOrderTypeModel implements SearchModelAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	private Integer id;

	/** The agency. */
	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "TYPE_NAME")
	private String name;

	@Column(name = "WO_STATUS_NAME")
	private String workOrderStatusName;

	@Column(name = "WORKFLOW_TYPE_NAME")
	private String workFlowTypeName;

	@Column(name = "IS_ENABLE")
	private Boolean enable;

	@Column(name = "CAR_SECTION")
	private Boolean carSectionEnable;

	@Column(name="CAR_OWNER_SECTION")
	private Boolean carOwnerSectionEnable;

	@Column(name = "SERVICE_SECTION")
	private Boolean serviceSectionEnable;

	@Column(name = "ONSITE_SERVICE_SECTION")
	private Boolean onsiteServiveSectionEnable;

	@Column(name = "CUSTOMIZED_FORM_SECTION")
	private Boolean customizedFormSectionEnable;

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

	public String getWorkOrderStatusName()
	{
		return workOrderStatusName;
	}

	public void setWorkOrderStatusName(String workOrderStatusName)
	{
		this.workOrderStatusName = workOrderStatusName;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}

	public String getWorkFlowTypeName()
	{
		return workFlowTypeName;
	}

	public void setWorkFlowTypeName(String workFlowTypeName)
	{
		this.workFlowTypeName = workFlowTypeName;
	}

	public Boolean getCarSectionEnable()
	{
		return carSectionEnable;
	}

	public void setCarSectionEnable(Boolean carSectionEnable)
	{
		this.carSectionEnable = carSectionEnable;
	}

	public Boolean getCarOwnerSectionEnable()
	{
		return carOwnerSectionEnable;
	}

	public void setCarOwnerSectionEnable(Boolean carOwnerSectionEnable)
	{
		this.carOwnerSectionEnable = carOwnerSectionEnable;
	}

	public Boolean getServiceSectionEnable()
	{
		return serviceSectionEnable;
	}

	public void setServiceSectionEnable(Boolean serviceSectionEnable)
	{
		this.serviceSectionEnable = serviceSectionEnable;
	}

	public Boolean getOnsiteServiveSectionEnable()
	{
		return onsiteServiveSectionEnable;
	}

	public void setOnsiteServiveSectionEnable(Boolean onsiteServiveSectionEnable)
	{
		this.onsiteServiveSectionEnable = onsiteServiveSectionEnable;
	}

	public Boolean getCustomizedFormSectionEnable()
	{
		return customizedFormSectionEnable;
	}

	public void setCustomizedFormSectionEnable(Boolean customizedFormSectionEnable)
	{
		this.customizedFormSectionEnable = customizedFormSectionEnable;
	}
}
