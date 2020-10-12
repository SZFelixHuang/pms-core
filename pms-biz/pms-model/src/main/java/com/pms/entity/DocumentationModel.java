package com.pms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "DocumentationModel")
@Table(name = "PMS_DOCUMENTATIONS")
public class DocumentationModel implements SearchModelAware
{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "FILE_KEY")
	private String fileKey;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "FILE_TYPE")
	private String fileType;

	@Column(name = "FILE_SIZE")
	private Long fileSize;

	@Column(name = "CREATE_TIME")
	private Date createDate;

	@Column(name = "UPDATED_TIME")
	private Date updatedDate;

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

	public String getFileKey()
	{
		return fileKey;
	}

	public void setFileKey(String fileKey)
	{
		this.fileKey = fileKey;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public Long getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(Long fileSize)
	{
		this.fileSize = fileSize;
	}
}
