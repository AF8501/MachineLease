package com.example.gyx.jixiezulin.EquipmentList.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gyx.jixiezulin.R;

import java.util.List;

/**
 * Created by gyx on 2016-12-05.
 */

public class HotSearchGridAdapter extends ArrayAdapter {

    private Context context;
    private List<String> historySearchList;
    private int resource;
    private LayoutInflater mInflater;
    private ItemViewTag viewTag;

    private int selectedPostion;//判断哪个item被点击了。

    public HotSearchGridAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        historySearchList = objects;
        mInflater = LayoutInflater.from(context);
        viewTag = new ItemViewTag();
        selectedPostion = -1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = mInflater.inflate(resource,null);
            viewTag.txSearchItem = (TextView) convertView.findViewById(R.id.tx_hotsearch_item);
            viewTag.llSearchItem = (LinearLayout) convertView.findViewById(R.id.ll_hotsearch_item);
        }else {
            viewTag.txSearchItem = (TextView)convertView.findViewById(R.id.tx_hotsearch_item);
            viewTag.llSearchItem = (LinearLayout) convertView.findViewById(R.id.ll_hotsearch_item);
        }
        String s = (String)getItem(position);
        if(s!=null){
            viewTag.txSearchItem.setText(s);
        }
        //如果被点击了，则改变button背景。
        if(selectedPostion==position){
            viewTag.llSearchItem.setBackgroundResource(R.drawable.btn_hotsearch_clicked);
            viewTag.txSearchItem.setTextColor(context.getResources().getColor(R.color.hot_search_clicked));
        }else {
            viewTag.llSearchItem.setBackgroundResource(R.drawable.btn_hotsearch_normal);
            viewTag.txSearchItem.setTextColor(context.getResources().getColor(R.color.hot_search_normal));
        }
        return convertView;
    }

    public class ItemViewTag{
        private LinearLayout llSearchItem;
        private TextView txSearchItem;
        public TextView getTxSearchItem() {
            return txSearchItem;
        }

    }

    public int getSelectedPostion() {
        return selectedPostion;
    }

    public void setSelectedPostion(int selectedPostion) {
        this.selectedPostion = selectedPostion;
    }
}
