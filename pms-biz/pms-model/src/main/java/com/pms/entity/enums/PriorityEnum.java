package com.pms.entity.enums;

import com.pms.entity.label.EnumLabel;

public enum PriorityEnum {
	
	URGENT(10000), HIGN(5000), NORMAL(1000), LOW(100);

	private int priority;
	
	
	public String getLabel() {
		switch (this)
		{
			case URGENT:
				return EnumLabel.PRIORITY_URGENT;
			case HIGN:
				return EnumLabel.PRIORITY_HIGH;
			case NORMAL:
				return EnumLabel.PRIORITY_NORMAL;
			case LOW:
				return EnumLabel.PRIORITY_LOW;
		}
		return EnumLabel.PRIORITY_NORMAL;
	}

	public int getValue() {
		return this.priority;
	}
	
	private PriorityEnum(int priority)
	{
		this.priority = priority;
	}

	public static PriorityEnum convert2Enum(int priority) {
		if(priority <= 100)
		{
			return PriorityEnum.LOW;
		}
		if(priority <= 1000)
		{
			return PriorityEnum.NORMAL;
		}
		if(priority <= 5000)
		{
			return PriorityEnum.HIGN;
		}
		if(priority > 5000)
		{
			return PriorityEnum.URGENT;
		}
		return null;
	}
}