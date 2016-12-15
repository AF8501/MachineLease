package com.example.gyx.jixiezulin.MachineLease.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyx on 2016-12-06.
 *一、数据分两大部分：
 * 1、存储用数据，如设备类型、品牌型号等，用于存储用户的输入信息。
 * 2、控件用数据，有 specList;//规格列表，factoryList;//厂商列表和countList;//数量列表，用于规格选择对话框
 * 等控件显示以供用户选择。
 * 启动MachineLeaseActivity时一定要给控件用数据赋值。
 * 二、格式要求：
 * 1、latestComeTime（最迟进厂时间）的格式必须是“XX月XX日 XX时XX分”！！！
 * 三、该类采用Parcelable序列化，方便在Activity间传输。
 */

public class MachineLeaseData implements Parcelable {
    private String equipmentType;//设备类型

    private String brand;//品牌
    private String model;//型号

    private String spec;//规格

    private String factoryType;//厂商类

    private String counts;//数量

    private String latestComeTime;//最迟进厂时间

    private String projectDuration;//工期

    private String city;//城市

    private String projectAddress;//施工地点

    private String projectDescription;//工程描述

    private String specialRequest;//特别要求

    private boolean isProvidFood;//负责住宿

    private boolean isProvidOil;//负责燃油

    private float freight;//承担运费

    private float price;//价格
    private String[] priceUnitList;//价格的单位列表，即“/小时”、“/月”、“/台班”、“总价”
    private String priceUnit;//存储最终选择的价格单位
    private float referencePrice;//参考价格

    private String wayOfPayment;//付款方式

    private String contacts;//联系人

    private String phoneNum;//联系电话

    private List<String> specList;//规格列表
    private List<String> factoryList;//厂商列表
    private List<String> countList;//数量列表

    public MachineLeaseData() {
        this.isProvidOil = false;
        this.isProvidFood = false;
        this.price = -1;//-1代表未传值
        this.freight = -1;
        //规格列表、厂商列表和数量列表不能为空，在这里初始化一些空值
        specList = new ArrayList<>();
        specList.add("");
        factoryList = new ArrayList<>();
        factoryList.add("");
        countList = new ArrayList<>();
        countList.add("");
    }

    protected MachineLeaseData(Parcel in) {
        equipmentType = in.readString();
        brand = in.readString();
        model = in.readString();
        spec = in.readString();
        factoryType = in.readString();
        counts = in.readString();
        latestComeTime = in.readString();
        projectDuration = in.readString();
        city = in.readString();
        projectAddress = in.readString();
        projectDescription = in.readString();
        specialRequest = in.readString();
        isProvidFood = in.readByte() != 0;
        isProvidOil = in.readByte() != 0;
        freight = in.readInt();
        price = in.readInt();
        priceUnitList = in.createStringArray();
        priceUnit = in.readString();
        referencePrice = in.readInt();
        wayOfPayment = in.readString();
        contacts = in.readString();
        phoneNum = in.readString();
    }

    public static final Creator<MachineLeaseData> CREATOR = new Creator<MachineLeaseData>() {
        @Override
        public MachineLeaseData createFromParcel(Parcel in) {
            return new MachineLeaseData(in);
        }

        @Override
        public MachineLeaseData[] newArray(int size) {
            return new MachineLeaseData[size];
        }
    };

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getFactoryType() {
        return factoryType;
    }

    public void setFactoryType(String factoryType) {
        this.factoryType = factoryType;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getLatestComeTime() {
        return latestComeTime;
    }

    public void setLatestComeTime(String latestComeTime) {
        this.latestComeTime = latestComeTime;
    }

    public String getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(String projectDuration) {
        this.projectDuration = projectDuration;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public boolean isProvidFood() {
        return isProvidFood;
    }

    public void setProvidFood(boolean providFood) {
        isProvidFood = providFood;
    }

    public boolean isProvidOil() {
        return isProvidOil;
    }

    public void setProvidOil(boolean providOil) {
        isProvidOil = providOil;
    }

    public float getFreight() {
        return freight;
    }

    public void setFreight(float freight) {
        this.freight = freight;
    }

    public List<String> getSpecList() {
        return specList;
    }

    public void setSpecList(List<String> specList) {
        this.specList = specList;
    }

    public List<String> getFactoryList() {
        return factoryList;
    }

    public void setFactoryList(List<String> factoryList) {
        this.factoryList = factoryList;
    }

    public List<String> getCountList() {
        return countList;
    }

    public void setCountList(List<String> countList) {
        this.countList = countList;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String[] getPriceUnitList() {
        return priceUnitList;
    }

    public void setPriceUnitList(String[] priceUnitList) {
        this.priceUnitList = priceUnitList;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public float getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(float referencePrice) {
        this.referencePrice = referencePrice;
    }

    public String getWayOfPayment() {
        return wayOfPayment;
    }

    public void setWayOfPayment(String wayOfPayment) {
        this.wayOfPayment = wayOfPayment;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(equipmentType);
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(spec);
        dest.writeList(specList);
        dest.writeString(factoryType);
        dest.writeList(factoryList);
        dest.writeString(counts);
        dest.writeList(countList);
        dest.writeString(latestComeTime);
        dest.writeString(projectDuration);
        dest.writeString(city);
        dest.writeString(projectAddress);
        dest.writeString(projectDescription);
        dest.writeString(specialRequest);
        dest.writeByte((byte) (isProvidFood ? 1 : 0));
        dest.writeByte((byte) (isProvidOil ? 1 : 0));
        dest.writeFloat(freight);
        dest.writeFloat(price);
        dest.writeStringArray(priceUnitList);
        dest.writeString(priceUnit);
        dest.writeFloat(referencePrice);
        dest.writeString(wayOfPayment);
        dest.writeString(contacts);
        dest.writeString(phoneNum);
    }
}
