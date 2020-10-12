package com.pms.entity;

import java.io.Serializable;
import java.util.Date;

public class ActivitiModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String key;

	private String category;

	private Date createTime;

	private Date lastUpdateTime;

	private Integer version;

	private String metaInfo;

	private String deploymentId;

	private String tenantId;

	private boolean editorSource;

	private boolean editorSourceExtra;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
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

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}

	public String getMetaInfo()
	{
		return metaInfo;
	}

	public void setMetaInfo(String metaInfo)
	{
		this.metaInfo = metaInfo;
	}

	public String getDeploymentId()
	{
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId)
	{
		this.deploymentId = deploymentId;
	}

	public String getTenantId()
	{
		return tenantId;
	}

	public void setTenantId(String tenantId)
	{
		this.tenantId = tenantId;
	}

	public boolean getEditorSource()
	{
		return editorSource;
	}

	public void setEditorSource(boolean editorSource)
	{
		this.editorSource = editorSource;
	}

	public boolean getEditorSourceExtra()
	{
		return editorSourceExtra;
	}

	public void setEditorSourceExtra(boolean editorSourceExtra)
	{
		this.editorSourceExtra = editorSourceExtra;
	}
}