package com.pms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pms.entity.aware.SearchModelAware;
import com.pms.entity.enums.SerialNumberTypeEnum;

@Entity(name = "SerialNumberModel")
@Table(name = "PMS_SERIAL_NUMBER")
public class SerialNumberModel implements SearchModelAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "CATEGORY")
	private String category;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "TYPE")
	private SerialNumberTypeEnum type;

	@Column(name = "LENGTH")
	private Integer length;

	@Column(name = "SEQUENCE")
	private Integer sequence;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "PARENT_ID")
	private Integer parentId;

	@Transient
	private List<SerialNumberModel> constituents;

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

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public SerialNumberTypeEnum getType()
	{
		return type;
	}

	public void setType(SerialNumberTypeEnum type)
	{
		this.type = type;
	}

	public Integer getLength()
	{
		return length;
	}

	public void setLength(Integer length)
	{
		this.length = length;
	}

	public Integer getSequence()
	{
		return sequence;
	}

	public void setSequence(Integer sequence)
	{
		this.sequence = sequence;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public List<SerialNumberModel> getConstituents()
	{
		return constituents;
	}

	public void setConstituents(List<SerialNumberModel> constituents)
	{
		this.constituents = constituents;
	}

	public Integer getParentId()
	{
		return parentId;
	}

	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
	}
}