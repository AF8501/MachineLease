package com.example.gyx.jixiezulin.EquipmentList.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gyx.jixiezulin.Common.Pinyin4jUtil;
import com.example.gyx.jixiezulin.EquipmentList.model.EquipmentItem;
import com.example.gyx.jixiezulin.widget.contactList.ContactItemInterface;

/**
 * 功能：EquipmentListActivity的数据接口，格式化传入信息，以供设备列表显示。
 * 数据格式：支持两种数据格式，分别是Json和List<String>。注意：Json 的name字段固定使用用"equipments"。
 * 使用方法：new EquipmentData()，然后setEquipmentJson或者setEquipmentList,然后getItemList()。
 * 示例代码：
 * EquipmentData data = new EquipmentData();
 * String json = "{'equipments':['混凝土泵车','混凝土搅拌机','混凝土喷湿机','搅拌式运输机','铰链式卡车','静力压桩机','挖掘机','稳定土搅拌机','混凝土泵车','混凝土喷湿机','混凝土泵车','混凝土搅拌机','混凝土喷湿机','搅拌式运输机','铰链式卡车','静力压桩机','挖掘机','稳定土搅拌机','混凝土泵车','混凝土喷湿机']}";
 * data.setJson(json);
 *
 */
public class EquipmentData
{

	private String json; //Jsons格式数据
	private List<String> list;//List格式数据

	private List<ContactItemInterface> itemList;

	public EquipmentData() {
	}

	public EquipmentData(String json) {
		setJson(json);
	}

	public EquipmentData(List<String> list) {
		setList(list);
	}


	public List<ContactItemInterface> getItemList() {
		return itemList;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
		itemList = getSampleContactList(json);
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
		itemList = getSampleContactList(list);
	}

	//以下两个为两种格式的测试用数据。
	public final static String EquipmentJson = "{'equipments':['混凝土泵车','混凝土搅拌机','混凝土喷湿机','搅拌式运输机','铰链式卡车','静力压桩机','挖掘机','稳定土搅拌机','混凝土泵车','混凝土喷湿机','混凝土泵车','混凝土搅拌机','混凝土喷湿机','搅拌式运输机','铰链式卡车','静力压桩机','挖掘机','稳定土搅拌机','混凝土泵车','混凝土喷湿机']}";
	public final static String[] EquipmentList = {"混凝土泵车","混凝土搅拌机","混凝土喷湿机","搅拌式运输机","铰链式卡车","静力压桩机","挖掘机","稳定土搅拌机","混凝土泵车","混凝土喷湿机","混凝土泵车","混凝土搅拌机","混凝土喷湿机","搅拌式运输机","铰链式卡车","静力压桩机","挖掘机","稳定土搅拌机","混凝土泵车","混凝土喷湿机"};
	
	private List<ContactItemInterface> getSampleContactList(String josnData)
	{
		List<ContactItemInterface> list = new ArrayList<ContactItemInterface>();
		
		try
		{
			JSONObject jo1 = new JSONObject(josnData);
			JSONArray ja1 = jo1.getJSONArray("equipments");

			//这里实时将类别汉字转换为拼音，主要是为了在列表里面排序，如果设备类型基本是固定的，可以
			//直接再建一个json用于存储对应的拼音。
			for(int i = 0; i < ja1.length(); i++)
			{
				String equipmentName = ja1.getString(i);
				list.add(new EquipmentItem(equipmentName, Pinyin4jUtil.converterToSpell(equipmentName)));
			}
		} 
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		return list;
	}


	private List<ContactItemInterface> getSampleContactList(List<String> stringList){
		List<ContactItemInterface> list = new ArrayList<ContactItemInterface>();
		
		//这里实时将类别汉字转换为拼音，主要是为了在列表里面排序，如果设备类型基本是固定的，可以
		//直接再建一个json用于存储对应的拼音。
		for(int i = 0; i < stringList.size(); i++)
		{
			String equipmentName = stringList.get(i);
			list.add(new EquipmentItem(equipmentName, Pinyin4jUtil.converterToSpell(equipmentName)));
		}
		return list;
	}
	
}
