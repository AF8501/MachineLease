package com.example.gyx.jixiezulin.BrandList.data;

import com.example.gyx.jixiezulin.BrandList.model.BrandItem;
import com.example.gyx.jixiezulin.Common.Pinyin4jUtil;
import com.example.gyx.jixiezulin.EquipmentList.model.EquipmentItem;
import com.example.gyx.jixiezulin.widget.contactList.ContactItemInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：数据接口，格式化传入信息，以供设备列表显示。
 * 数据格式：支持两种数据格式，分别是Json和List<String>。注意：Json 的name字段固定使用用"brands"。
 * 使用方法：new BrandData()，然后setJson或者setList,然后getItemList()。
 * 示例代码：
 * BrandData data = new BrandData();
 * String json =  "{'brands':['全力','合力','恒特重工','鸿达建工','鸿得利','华力重工','湖南湘电','全力','合力','恒特重工','鸿达建工','鸿得利','华力重工','湖南湘电','全力','合力','恒特重工','鸿达建工','鸿得利','华力重工','湖南湘电']}";
 * data.setJson(json);
 */
public class BrandData
{
	private String json; //Jsons格式数据
	private List<String> list;//List格式数据

	private List<ContactItemInterface> itemList;

	public BrandData() {
	}

	public BrandData(String json) {
		setJson(json);
	}

	public BrandData(List<String> list) {
		setList(list);
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

	public List<ContactItemInterface> getItemList() {
		return itemList;
	}


	public final static String BrandJson = "{'brands':['全力','合力','恒特重工','鸿达建工','鸿得利','华力重工','湖南湘电','全力','合力','恒特重工','鸿达建工','鸿得利','华力重工','湖南湘电','全力','合力','恒特重工','鸿达建工','鸿得利','华力重工','湖南湘电']}";
	private List<ContactItemInterface> getSampleContactList(String josnData)
	{
		List<ContactItemInterface> list = new ArrayList<ContactItemInterface>();
		
		try
		{
			JSONObject jo1 = new JSONObject(josnData);
			JSONArray ja1 = jo1.getJSONArray("brands");

			//这里实时将类别汉字转换为拼音，主要是为了在列表里面排序，如果设备类型基本是固定的，可以
			//直接再建一个json用于存储对应的拼音。
			for(int i = 0; i < ja1.length(); i++)
			{
				String equipmentName = ja1.getString(i);
				list.add(new BrandItem(equipmentName, Pinyin4jUtil.converterToSpell(equipmentName)));
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
			list.add(new BrandItem(equipmentName, Pinyin4jUtil.converterToSpell(equipmentName)));
		}
		return list;
	}

}
