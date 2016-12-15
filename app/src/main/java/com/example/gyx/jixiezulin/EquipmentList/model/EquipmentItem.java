package com.example.gyx.jixiezulin.EquipmentList.model;

import com.example.gyx.jixiezulin.widget.contactList.ContactItemInterface;

public class EquipmentItem implements ContactItemInterface
{
	private String nickName;
	private String fullName;

	public EquipmentItem(String nickName, String fullName)
	{
		super();
		this.nickName = nickName;
		this.setFullName(fullName);
	}

	@Override
	public String getItemForIndex()
	{
		return fullName;
	}

	@Override
	public String getDisplayInfo()
	{
		return nickName;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

}
