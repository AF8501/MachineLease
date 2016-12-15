package com.example.gyx.jixiezulin.MachineLease.Adapter;

import android.content.Context;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gyx.jixiezulin.MachineLease.Data.MachineLeaseData;
import com.example.gyx.jixiezulin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyx on 2016-12-06.
 */

public class MachineLeaseListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
//    private LayoutInflater inflater;
//
//    private Context context;
//    private List<MachineLeaseData> dataList;
//
//    private TextView itemName;
//    private TextView itemContent;
//    private TextPaint textPaint;
//    private ImageButton previewContract;
//
//
//    public MachineLeaseListAdapter(Context context ,MachineLeaseData data) {
//        if(context!=null){
//            this.context = context;
//            inflater = LayoutInflater.from(context);
//        }
//        dataList = new ArrayList<>();
//        if(data!=null){
//            //这里取巧，机械租赁页面列表一共有19行，在dataList添加19个数据，listView
//            //便可以显示这19行，所有行的数据全部来自data。
//            for(int i=0;i<19;i++){
//                dataList.add(data);
//            }
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return dataList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return dataList==null ? null : dataList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        switch (position){
//            case 0:
//
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
//
//                itemName.setText("设备类型");
//                if(dataList.get(0).getEquipmentType()!=null){
//                    itemContent.setText(dataList.get(0).getEquipmentType());
//                }
//                break;
//            case 1:
//
//                    convertView = inflater.inflate(R.layout.machine_lease_item6,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item6_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item6_content);
//
//                itemName.setText("品牌型号");
//                if(dataList.get(0).getBrand()!=null){
//                    itemContent.setText(dataList.get(0).getBrand());
//                }
//                break;
//            case 2:
//
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
//
//                itemName.setText("规格");
//                if(dataList.get(0).getSpec()!=null){
//                    itemContent.setText(dataList.get(0).getSpec());
//                }
//                break;
//            case 3:
//
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
//
//                itemName.setText("厂商类别");
//                if(dataList.get(0).getFactoryType()!=null){
//                    itemContent.setText(dataList.get(0).getFactoryType());
//                }
//                break;
//            case 4:
//
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
//
//                itemName.setText("数量");
//                if(dataList.get(0).getCounts()!=-1){
//                    itemContent.setText(dataList.get(0).getCounts()+"台");
//                }
//                break;
//            case 5:
//
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
//
//                itemName.setText("最迟进厂时间");
//                if(dataList.get(0).getLatestComeTime()!=null){
//                    itemContent.setText(dataList.get(0).getLatestComeTime());
//                }
//                break;
//            case 6:
//
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
//
//                itemName.setText("工期");
//                if(dataList.get(0).getProjectDuration()!=-1){
//                    itemContent.setText(dataList.get(0).getProjectDuration()+"天");
//                }
//                break;
//            case 7:
//
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
//
//                itemName.setText("城市");
//                if(dataList.get(0).getCity()!=null){
//                    itemContent.setText(dataList.get(0).getCity());
//                }
//                break;
//            case 8:
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
//
//                itemName.setText("施工地点");
//                if(dataList.get(0).getProjectAddress()!=null){
//                    itemContent.setText(dataList.get(0).getProjectAddress());
//                }
//                break;
//            case 9:
//                    convertView = inflater.inflate(R.layout.machine_lease_item2,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item2_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item2_content);
//                itemName.setText("工程描述");
//                if(dataList.get(0).getProjectDescription()!=null){
//                    itemContent.setText(dataList.get(0).getProjectDescription());
//                }
//                break;
//            case 10:
//                    convertView = inflater.inflate(R.layout.machine_lease_item2,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item2_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item2_content);
//                itemName.setText("特别要求");
//                if(dataList.get(0).getSpecialRequest()!=null){
//                    itemContent.setText(dataList.get(0).getSpecialRequest());
//                }
//                break;
//            case 11:
////                if(convertView==null){
//                    convertView = inflater.inflate(R.layout.machine_lease_item3,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item3_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.rb_machine_lease_item3_content);
////                }else {
////                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item3_Name);
////                    itemContent = (TextView)convertView.findViewById(R.id.rb_machine_lease_item3_content);
////                }
//                itemName.setText("负责食宿");
//                break;
//            case 12:
////                if(convertView==null){
//                    convertView = inflater.inflate(R.layout.machine_lease_item3,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item3_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.rb_machine_lease_item3_content);
////                }else {
////                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item3_Name);
////                    itemContent = (TextView)convertView.findViewById(R.id.rb_machine_lease_item3_content);
////                }
//                itemName.setText("负责燃油");
//                break;
//            case 13:
////                if(convertView==null){
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
////                }else {
////                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
////                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
////                }
//                itemName.setText("承担运费");
//                if(dataList.get(0).getFreight()!=-1){
//                    itemContent.setText(dataList.get(0).getFreight()+"元");
//                }
//                break;
//            case 14:
////                if(convertView==null){
//                    convertView = inflater.inflate(R.layout.machine_lease_item5,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item5_name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item5_content);
////                }else {
////                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
////                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
////                }
//                itemName.setText("价格");
//                if(dataList.get(0).getPrice()!=-1){
//                    itemContent.setText(dataList.get(0).getPrice()+"元");
//                }
//                break;
//            case 15:
////                if(convertView==null){
//                    convertView = inflater.inflate(R.layout.item_equitment_type,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
////                }else {
////                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
////                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
////                }
//                itemName.setText("付款方式");
//                if(dataList.get(0).getWayOfPayment()!=null){
//                    itemContent.setText(dataList.get(0).getWayOfPayment());
//                }
//                break;
//            case 16:
////                if(convertView==null){
//                    convertView = inflater.inflate(R.layout.machine_lease_item4,null);
//                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item4_Name);
//                    itemContent = (TextView)convertView.findViewById(R.id.et_machine_lease_item4);
////                }else {
////                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
////                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
////                }
//                itemName.setText("联系人");
//                if(dataList.get(0).getContacts()!=null){
//                    itemContent.setText(dataList.get(0).getContacts());
//                }
//                break;
//            case 17:
////                if(convertView==null){
//                convertView = inflater.inflate(R.layout.machine_lease_item4,null);
//                itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item4_Name);
//                itemContent = (TextView)convertView.findViewById(R.id.et_machine_lease_item4);
////                }else {
////                    itemName = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_Name);
////                    itemContent = (TextView)convertView.findViewById(R.id.tx_machine_lease_item1_content);
////                }
//                itemName.setText("联系电话");
//                if(dataList.get(0).getPhoneNum()!=null){
//                    itemContent.setText(dataList.get(0).getPhoneNum());
//                }
//                break;
//            case 18:
//                convertView = inflater.inflate(R.layout.item_preview_contract,null);
//                previewContract = (ImageButton)convertView.findViewById(R.id.ib_machine_lease_pre_contract);
//                previewContract.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d("112233","jj");
//                    }
//                });
//                break;
//        }
//        return convertView;
//    }


}
