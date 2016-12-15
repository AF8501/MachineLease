package com.example.gyx.jixiezulin.MachineLease;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gyx.jixiezulin.BrandList.BrandListActivity;
import com.example.gyx.jixiezulin.Common.CommonUtil;
import com.example.gyx.jixiezulin.EquipmentList.EquipmentListActivity;
import com.example.gyx.jixiezulin.MachineLease.Data.MachineLeaseData;
import com.example.gyx.jixiezulin.ModelList.ModelListActivity;
import com.example.gyx.jixiezulin.PostContracts.PostContractsActivity;
import com.example.gyx.jixiezulin.R;
import com.example.gyx.jixiezulin.widget.SoftKeyboardStateHelper;
import com.example.gyx.jixiezulin.widget.SwitchButton.SwitchButton;
import com.example.gyx.jixiezulin.widget.WheelView.PickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gyx on 2016-12-06.
 * 一、功能描述：显示机械租赁页面。
 * 二、重要属性：
 * 1、private MachineLeaseData machineLeaseData：用于存储用户输入数据和提供控件用数据。初始化各类控件前
 * 一定要先设置machineLeaseData的specList;//规格列表，factoryList;//厂商列表和countList;//数量列表。
 * 三、逻辑关系：
 * 点击合约预览，验证用户输入是否完整，序列化machineLeaseData传送并跳转至提交合约支付保证金页面。
 */

public class MachineLeaseActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int EQUIPMENT_ACTIVITY = 9500;
    public static final int BRAND_MODEL_ACTIVITY = 9501;

    public static final String MACHINE_LEASE_DATA = "machine_lease_data";
    //toolbar 标题和返回按钮
    private TextView txTitle;
    private ImageButton imgBack;

    private TextView matchingNum;//匹配设备数
    private RelativeLayout rlEquipmentType;
    private TextView equipmentTypeContents;//设备类型
    private RelativeLayout rlBrandModle;
    private TextView brandModleContents;//品牌型号
    private RelativeLayout rlSpec;
    private TextView specContents;//规格
    private RelativeLayout rlFactoryType;
    private TextView factoryTypeContents;//厂商类别
    private RelativeLayout rlCounts;
    private TextView countsContents;//数量
    private RelativeLayout rlLatestComeTime;
    private TextView latestComeTimeContents;//最迟进厂时间
    private RelativeLayout rlProjectDuration;
    private TextView projectDurationContents;//工期
    private RelativeLayout rlCity;
    private TextView cityContents;//城市
    private RelativeLayout rlProjectAddress;
    private TextView projectAddressContents;//施工地点
    private RelativeLayout rlProjectDescription;
    private EditText projectDescriptionContents;//工程描述
    private RelativeLayout rlSpecialRequest;
    private TextView specialRequestContents;//特别要求
    private RelativeLayout rlIsProvidFood;
    private SwitchButton isProvidFood;//负责住宿
    private RelativeLayout rlIsProvidOil;
    private SwitchButton isProvidOil;//负责燃油
    private RelativeLayout rlFreight;
    private EditText freightContents;//承担运费
    private TextView freightUnit;//单位元
    private RelativeLayout rlPrice;
    private Spinner spPrice;
    private EditText priceContents;//价格
    private TextView priceUnit;//单位元
    private RelativeLayout rlWayOfPayment;
    private TextView wayOfPaymentContents;//付款方式
    private RelativeLayout rlContacts;
    private EditText contactsContents;//联系人
    private RelativeLayout rlPhoneNum;
    private EditText phoneNumContents;//联系电话
    private Button ibPreViewContract;//预览合约
    private RelativeLayout rlPreViewContract;

    //各选项名称
    private TextView equipmentName;
    private TextView brandModleName;//品牌型号
    private TextView specName;//规格
    private TextView factoryTypeName;//厂商类别
    private TextView countsName;//数量
    private TextView latestComeTimeName;//最迟进厂时间
    private TextView projectDurationName;//工期
    private TextView cityName;//城市
    private TextView projectAddressName;//施工地点
    private TextView projectDescriptionName;//工程描述
    private TextView specialRequestName;//特别要求
    private TextView freightName;//承担运费
    private TextView priceName;//价格
    private TextView wayOfPaymentName;//付款方式
    private TextView contactsName;//联系人
    private TextView phoneNumName;//联系电话

    private MachineLeaseData machineLeaseData;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_lease);
        machineLeaseData = new MachineLeaseData();

        listenSoftKeyboardState();

        initToolbar();

        initView();

        //Todo:测试用数据，完成数据连接后请删除
        testData();


    }

    private void testData() {
        List<String> specList = new ArrayList<>();
        specList.add("1吨");
        specList.add("5吨");
        specList.add("10吨");
        specList.add("11吨");
        specList.add("12吨");
        specList.add("13吨");
        specList.add("15吨");
        machineLeaseData.setSpecList(specList);
        List<String> fatoryList = new ArrayList<>();
        fatoryList.add("不限");
        fatoryList.add("国产");
        fatoryList.add("合资");
        fatoryList.add("进口");
        machineLeaseData.setFactoryList(fatoryList);
        List<String> countList = new ArrayList<>();
        for(int i=1;i<=20;i++){
            countList.add(i+"台");
        }
        machineLeaseData.setCountList(countList);
        machineLeaseData.setCity("四川省成都市");
        cityContents.setText(machineLeaseData.getCity());
        machineLeaseData.setProjectAddress("武侯区科华北路89号");
        projectAddressContents.setText(machineLeaseData.getProjectAddress());
        machineLeaseData.setWayOfPayment("支付宝");
        wayOfPaymentContents.setText(machineLeaseData.getWayOfPayment());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case EQUIPMENT_ACTIVITY:
                if(resultCode==RESULT_OK){
                    Bundle bundle = data.getBundleExtra(EquipmentListActivity.Equipment_ADD);
                    machineLeaseData.setEquipmentType(bundle.getString(EquipmentListActivity.Equipment_ADD));

                    if(machineLeaseData.getEquipmentType()!=null){
                        equipmentName.setTextColor(Color.parseColor("#666666"));
                        equipmentTypeContents.setText(machineLeaseData.getEquipmentType());
                    }

                }
                break;
            case BRAND_MODEL_ACTIVITY:
                if(resultCode==RESULT_OK){
                    Bundle bundle = data.getBundleExtra(BrandListActivity.BRAND_ADD);
                    machineLeaseData.setBrand(bundle.getString(BrandListActivity.BRAND_ADD));
                    machineLeaseData.setModel(bundle.getString(ModelListActivity.MODEL_ADD));
                    brandModleContents.setText(machineLeaseData.getBrand()+" "+machineLeaseData.getModel());
                }
        }
    }

    private void initView() {

        matchingNum = $(R.id.tx_matching_num);

        rlEquipmentType = $(R.id.rl_item_equipment_type);
        equipmentTypeContents = $(R.id.tx_equipment_type__content);
        rlEquipmentType.setOnClickListener(this);

        rlBrandModle = $(R.id.rl_item_brand_model);
        rlBrandModle.setOnClickListener(this);
        brandModleContents = $(R.id.tx_item_brand_model_content);

        rlSpec = $(R.id.rl_item_spec);
        specContents = $(R.id.tx_item_spec_content);
        rlSpec.setOnClickListener(this);

        rlFactoryType = $(R.id.rl_item_factory_type);
        factoryTypeContents = $(R.id.tx_item_factory_type_content);
        rlFactoryType.setOnClickListener(this);

        rlCounts = $(R.id.rl_item_counts);
        countsContents = $(R.id.tx_item_counts_content);
        rlCounts.setOnClickListener(this);

        rlLatestComeTime = $(R.id.rl_item_latest_time);
        latestComeTimeContents = $(R.id.tx_item_latest_time_content);
        rlLatestComeTime.setOnClickListener(this);

        rlProjectDuration = $(R.id.rl_item_project_duration);
        projectDurationContents = $(R.id.tx_item_project_duration_content);
        rlProjectDuration.setOnClickListener(this);

        rlCity = $(R.id.rl_item_city);
        cityContents = $(R.id.tx_item_city_content);
        rlCity.setOnClickListener(this);

        rlProjectAddress = $(R.id.rl_item_project_address);
        projectAddressContents = $(R.id.tx_item_project_address_content);
        rlProjectAddress.setOnClickListener(this);

        rlProjectDescription = $(R.id.rl_item_project_description);
        projectDescriptionContents = $(R.id.tx_item_project_description_content);
        projectDescriptionContents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                machineLeaseData.setProjectDescription(projectDescriptionContents.getText().toString());
                if(machineLeaseData.getProjectDescription().equals("")||machineLeaseData.getProjectDescription()==null){
                    projectDescriptionName.setText("工程描述（请填写内容）");
                    projectDescriptionName.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    projectDescriptionName.setText("工程描述");
                    projectDescriptionName.setTextColor(Color.parseColor("#666666"));
                }
            }
        });

        rlSpecialRequest = $(R.id.rl_item_specail_request);
        specialRequestContents = $(R.id.tx_item_specail_request_content);
        specialRequestContents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                machineLeaseData.setSpecialRequest(specialRequestContents.getText().toString());
                if(machineLeaseData.getSpecialRequest().equals("")||machineLeaseData.getSpecialRequest()==null){
                    specialRequestName.setText("特别要求（请填写内容）");
                    specialRequestName.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    specialRequestName.setText("特别要求");
                    specialRequestName.setTextColor(Color.parseColor("#666666"));
                }
            }
        });

        rlIsProvidFood = $(R.id.rl_item_isprovide_food);
        isProvidFood = $(R.id.sw_item_isprovide_food);
        isProvidFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                machineLeaseData.setProvidFood(isChecked);
            }
        });

        rlIsProvidOil = $(R.id.rl_item_isprovide_oil);
        isProvidOil = $(R.id.sw_item_isprovide_oil);
        isProvidOil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                machineLeaseData.setProvidOil(isChecked);
            }
        });

        rlFreight = $(R.id.rl_item_freight);
        freightContents = $(R.id.et_item_freight_content);
        freightUnit = $(R.id.tx_item_freight_unit);
        freightContents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float freight = Float.parseFloat(freightContents.getText().toString());
                machineLeaseData.setFreight(freight);
                if(machineLeaseData.getFreight()==-1){
                    freightName.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    freightName.setTextColor(Color.parseColor("#666666"));
                    freightUnit.setVisibility(View.VISIBLE);
                }
            }
        });

        rlPrice = $(R.id.rl_item_price);
        spPrice = $(R.id.sp_item_price);
        priceUnit = $(R.id.tx_item_price_unit);
        machineLeaseData.setPriceUnitList(getResources().getStringArray(R.array.data));
        final String[] priceUnitList = machineLeaseData.getPriceUnitList();
        machineLeaseData.setPriceUnit(priceUnitList[0]);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this,R.layout.machine_lease_spinner_text,machineLeaseData.getPriceUnitList());
        spPrice.setAdapter(spinnerAdapter);
        spPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                machineLeaseData.setPriceUnit(priceUnitList[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        priceContents = $(R.id.et_item_price_content);
        priceContents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float price = Float.parseFloat(priceContents.getText().toString());
                machineLeaseData.setPrice(price);
                if(machineLeaseData.getPrice()==-1){
                    priceName.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    priceName.setTextColor(Color.parseColor("#666666"));
                    priceUnit.setVisibility(View.VISIBLE);
                }
            }
        });

        rlWayOfPayment = $(R.id.rl_item_wayofpayment);
        wayOfPaymentContents = $(R.id.tx_item_wayofpayment_content);
        rlWayOfPayment.setOnClickListener(this);

        rlContacts = $(R.id.rl_item_contacts);
        contactsContents = $(R.id.et_item_contacts_content);
        contactsContents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                machineLeaseData.setContacts(contactsContents.getText().toString());
                if(machineLeaseData.getContacts().equals("")||machineLeaseData.getCounts()==null){
                    contactsName.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    contactsName.setTextColor(Color.parseColor("#666666"));
                }
            }
        });

        rlPhoneNum = $(R.id.rl_item_phone);
        phoneNumContents = $(R.id.et_item_phone_content);
        phoneNumContents.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                machineLeaseData.setPhoneNum(phoneNumContents.getText().toString());
                if(machineLeaseData.getPhoneNum().equals("")||machineLeaseData.getPhoneNum()==null){
                    phoneNumName.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    phoneNumName.setTextColor(Color.parseColor("#666666"));
                }
            }
        });

        rlPreViewContract = $(R.id.rl_item_preview_contract);
        ibPreViewContract = $(R.id.btn_item_preview_contract);
        ibPreViewContract.setOnClickListener(this);

        equipmentName = $(R.id.tx_equipment_type_name);
        brandModleName = $(R.id.tx_item_brand_model_name);
        specName = $(R.id.tx_item_spec_name);
        factoryTypeName = $(R.id.tx_item_factory_type_name);
        countsName = $(R.id.tx_item_counts_name);
        latestComeTimeName = $(R.id.tx_item_latest_time_name);
        projectDurationName = $(R.id.tx_item_project_duration_name);
        cityName = $(R.id.tx_item_city_name);
        projectAddressName = $(R.id.tx_item_project_address_name);
        projectDescriptionName = $(R.id.tx_item_project_description_name);
        specialRequestName = $(R.id.tx_item_specail_request_name);
        freightName = $(R.id.tx_item_freight_name);
        priceName = $(R.id.tx_item_price_name);
        wayOfPaymentName = $(R.id.tx_item_wayofpayment_name);
        contactsName = $(R.id.tx_item_contacts_name);
        phoneNumName = $(R.id.tx_item_phone_name);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txTitle = (TextView) findViewById(R.id.toolbar_title);
        txTitle.setText("机械租赁");

//        Typeface fontFace = Typeface.createFromAsset(getAssets(),
//                "fonts/NotoSansCJK-Medium.otf");
//        txTitle.setTypeface(fontFace);
//        //设置粗体
//        TextPaint tp = txTitle.getPaint();
//        tp.setFakeBoldText(true);

        imgBack = (ImageButton) findViewById(R.id.toolbar_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * 每个Item的点击监听事件，不包括editText,Sipneer,等的监听事件。
     * 每个对话框的监听事件在各自的实现函数里。
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.rl_item_equipment_type://点击设备类型
                startEquipmentTypeActivity();
                break;
            case R.id.rl_item_brand_model://点击品牌型号
                if(machineLeaseData.getEquipmentType()==null){
                    Toast.makeText(MachineLeaseActivity.this,"请先选择设备型号",Toast.LENGTH_SHORT).show();
                    break;
                }
                startBrandActivity();
                break;
            case R.id.rl_item_spec://点击规格
                if(machineLeaseData.getEquipmentType()==null){
                    Toast.makeText(MachineLeaseActivity.this,"请先选择设备型号",Toast.LENGTH_SHORT).show();
                    break;
                }
                showPickerDialog(id,machineLeaseData.getSpecList());
                break;
            case R.id.rl_item_factory_type://点击厂商类别
                showPickerDialog(id,machineLeaseData.getFactoryList());
                break;
            case R.id.rl_item_counts://点击数量
                showPickerDialog(R.id.rl_item_counts,machineLeaseData.getCountList());
                break;
            case R.id.rl_item_latest_time://点击最迟进场时间
                showDatePickerDialog();
                break;
            case R.id.rl_item_project_duration://点击工期
                showDurationPickerDialog();
                break;
            case R.id.rl_item_city://点击城市
                break;
            case R.id.rl_item_project_address://点击施工地点(工程描述等editText的监听在initView();
                break;
            case R.id.rl_item_wayofpayment://点击付款方式
                break;
            case R.id.btn_item_preview_contract:
                if(checkInput()){
                    startPostContractsActivity();
                }
                break;

        }
    }

    /**
     * 验证用户输入的完整性,发现未输入项，将该项标红，并Toast提示。
     * @return
     */
    private boolean checkInput() {
        boolean isOk = true;
        String colorAlert = "#ff0000";
        String colorNormal = "#666666";
        if(machineLeaseData.getEquipmentType()==null){
            equipmentName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            equipmentName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getSpec()==null){
            specName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            specName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getFactoryType()==null){
            factoryTypeName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            factoryTypeName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getCounts()==null){
            countsName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            countsName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getLatestComeTime()==null){
            latestComeTimeName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            latestComeTimeName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getProjectDuration()==null){
            projectDurationName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            projectDurationName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getCity()==null){
            cityName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            cityName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getProjectAddress()==null){
            projectAddressName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else{
            projectAddressName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getProjectDescription()==null){
            projectDescriptionName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            projectDescriptionName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getSpecialRequest()==null){
            specialRequestName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            specialRequestName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getFreight()==-1){
            freightName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            freightName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getPrice()==-1){
            priceName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            priceName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getWayOfPayment()==null){
            wayOfPaymentName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            wayOfPaymentName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getContacts()==null){
            contactsName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            contactsName.setTextColor(Color.parseColor(colorNormal));
        }
        if(machineLeaseData.getPhoneNum()==null){
            phoneNumName.setTextColor(Color.parseColor(colorAlert));
            isOk = false;
        }else {
            phoneNumName.setTextColor(Color.parseColor(colorNormal));
        }
        if(!isOk){
            Toast.makeText(this,"信息填写不完整",Toast.LENGTH_SHORT).show();
        }
        return isOk;
    }

    private void startPostContractsActivity() {
        Intent intent = new Intent(MachineLeaseActivity.this, PostContractsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MACHINE_LEASE_DATA,machineLeaseData);
        intent.putExtra(MACHINE_LEASE_DATA,bundle);
        startActivity(intent);
    }

    private void startBrandActivity() {
        Intent intent = new Intent(this, BrandListActivity.class);
        startActivityForResult(intent,BRAND_MODEL_ACTIVITY);
    }

    /**
     * 跳转到设备型号页面
     */
    private void startEquipmentTypeActivity() {
        Intent intent = new Intent(this, EquipmentListActivity.class);
        startActivityForResult(intent,EQUIPMENT_ACTIVITY);
    }

    private void showPickerDialog(final int id, List<String> list){
        final String[] result = new String[1];
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_machine_lease_picker_view, null);
        TextView dialogTitle = (TextView) outerView.findViewById(R.id.tx_dialog_picker_title);
        PickerView pv = (PickerView)outerView.findViewById(R.id.pick_view);
        pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                result[0] = text;
            }
        });
        pv.setData(list);//这里一定要先设置监听事件，再设置data！！！不然没有滑动就不会触发onSelectListener

        switch (id){
            case R.id.rl_item_spec:
                dialogTitle.setText("选择机械规格");
                if(machineLeaseData.getSpec()!=null)
                    pv.setSelected(machineLeaseData.getSpec());
                break;
            case R.id.rl_item_factory_type:
                dialogTitle.setText("选择厂商类别");
                if(machineLeaseData.getFactoryType()!=null)
                    pv.setSelected(machineLeaseData.getFactoryType());
                break;
            case R.id.rl_item_counts:
                dialogTitle.setText("选择机械数量");
                if(machineLeaseData.getCounts()!=null)
                    pv.setSelected(machineLeaseData.getCounts());
                break;
        }

        final Dialog dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        dialog.setContentView(outerView);
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //设置横向全屏
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //取消
        Button btnCancel = (Button)outerView.findViewById(R.id.btn_wheel_view_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //确定
        Button btnOk = (Button)outerView.findViewById(R.id.btn_wheel_view_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (id){
                    case R.id.rl_item_spec:
                        machineLeaseData.setSpec(result[0]);
                        if(machineLeaseData.getSpec()!=null){
                            specName.setTextColor(Color.parseColor("#666666"));
                            specContents.setText(machineLeaseData.getSpec());
                        }

                        break;
                    case R.id.rl_item_factory_type:
                        machineLeaseData.setFactoryType(result[0]);
                        if(machineLeaseData.getFactoryType()!=null){
                            factoryTypeName.setTextColor(Color.parseColor("#666666"));
                            factoryTypeContents.setText(machineLeaseData.getFactoryType());
                        }
                        break;
                    case R.id.rl_item_counts:
                        machineLeaseData.setCounts(result[0]);
                        if(machineLeaseData.getCounts()!=null){
                            countsName.setTextColor(Color.parseColor("#666666"));
                            countsContents.setText(machineLeaseData.getCounts());
                        }
                        break;
                }
                dialog.cancel();

            }
        });

    }

    /**
     * 显示最晚进场时间选择对话框。
     * 1、考虑了闰年的情况。
     * 2、自动去掉今天之前的时间，精确度半个小时。
     * 3、再次点击自动滚到上次选择的位置。
     */
    private void showDatePickerDialog(){
        final String[] date = new String[1];
        final String[] time = new String[1];
        //获取当前时间
        final String[] currentDate = getCurrentDate();

        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_machine_lease_datepicker, null);
        PickerView pvDate = (PickerView)outerView.findViewById(R.id.date_pick_view);
        final PickerView pvTime = (PickerView)outerView.findViewById(R.id.time_pick_view);
        pvTime.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                time[0] = text;
            }
        });
        pvDate.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                if(text.equals(date[0]))
                    return;
                List<String> listTime = createTimeList();

                if(text.equals(currentDate[0])){
                    if(listTime.contains(currentDate[1])){
                        listTime = listTime.subList(listTime.indexOf(currentDate[1]),listTime.size());
                    }
                    pvTime.setData(listTime);
                    pvTime.setSelected(0);
                }else {
                    pvTime.setData(listTime);
                    if(machineLeaseData.getLatestComeTime()!=null) {
                        String s = machineLeaseData.getLatestComeTime();
                        if(s.contains(" ")){
                            s = s.substring(s.indexOf(" ")+1, s.length());
                        }
                        pvTime.setSelected(s);
                    }
                }
                date[0] = text;
            }
        });
        pvDate.setData(createDateList());
        //首先将位置设定在上次选择的位置
        if(machineLeaseData.getLatestComeTime()!=null){
            String s = machineLeaseData.getLatestComeTime();
            if(s.contains(" ")){
                s = s.substring(0,s.indexOf(" "));
            }
            pvDate.setSelected(s);
        }else
            pvDate.setSelected(0);
        final Dialog dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        dialog.setContentView(outerView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //取消
        Button btnCancel = (Button)outerView.findViewById(R.id.btn_wheel_view_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //确定
        Button btnOk = (Button)outerView.findViewById(R.id.btn_wheel_view_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                machineLeaseData.setLatestComeTime(date[0]+" "+time[0]);
                if(machineLeaseData.getLatestComeTime()!=null){
                    latestComeTimeName.setTextColor(Color.parseColor("#666666"));
                    latestComeTimeContents.setText(machineLeaseData.getLatestComeTime());
                }
                dialog.cancel();
            }
        });
    }

    /**
     * 获取当前时间
     * @return 返回一个时间数组，第一个是日期，第二个是时间。
     */
    private String[] getCurrentDate() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatter =  new SimpleDateFormat ("MM月dd日");
        String date  = formatter.format(curDate);
        formatter =  new SimpleDateFormat ("HH");
        String hour =formatter.format(curDate);
        formatter =  new SimpleDateFormat ("mm");
        int t = Integer.parseInt(formatter.format(curDate));
        String time;
        if(t<30){
            time = hour+":"+"30";
        }else
        {
            int h = Integer.parseInt(hour)+1;
            if(h<10)
                time = "0"+h+":"+"00";
            else if(h==24)
                time = "00:00";
            else
                time = h+":"+"00";
        }
        String[] currentDate = {date,time};
        return currentDate;
    }

    private void showDurationPickerDialog(){
        final String[] duration = new String[1];
        final String[] unit = new String[1];
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_machine_lease_duration_picker, null);
        final PickerView pvDuration = (PickerView)outerView.findViewById(R.id.num_pick_view);
        final PickerView pvUnit = (PickerView)outerView.findViewById(R.id.unit_pick_view);
        pvUnit.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                if(text.equals(unit[0]))
                    return;
                unit[0] = text;
            }
        });
        List<String> listUnit = new ArrayList<>();
        listUnit.add("小时");
        listUnit.add("天");
        listUnit.add("月");
        pvUnit.setData(listUnit);
        String s = machineLeaseData.getProjectDuration();
        pvUnit.setSelected(0);

        pvDuration.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {

                duration[0] = text;
            }
        });
        pvDuration.setData(createDuratoinList(1,100));
        pvDuration.setSelected(0);
        final Dialog dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        dialog.setContentView(outerView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //取消
        Button btnCancel = (Button)outerView.findViewById(R.id.btn_wheel_view_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //确定
        Button btnOk = (Button)outerView.findViewById(R.id.btn_wheel_view_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unit[0].equals("月"))
                    unit[0] = "个月";
                machineLeaseData.setProjectDuration(duration[0]+unit[0]);
                projectDurationContents.setText(machineLeaseData.getProjectDuration());
                dialog.cancel();
            }
        });
    }


    private List<String> createDuratoinList(int begin,int end) {
        List<String> list = new ArrayList<>();
        for(int i=begin;i<=end;i++){
            String s = i+"";
            list.add(s);
        }
        return list;
    }

    /**
     * 返回一个包含从今天开始往后一年所有日期的列表，格式:MM-DD
     * @return
     */
    private List<String> createDateList() {
        //获取当前时间
        Calendar CD = Calendar.getInstance();
        int YY = CD.get(Calendar.YEAR);
        int MM = CD.get(Calendar.MONTH) + 1;
        int DD = CD.get(Calendar.DATE);

        List<String> list = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <= 31; j++) {
                String d = i + "月" + j + "日";
                list.add(d);
            }
        }
        //如果不是闰年
        if(YY%4!=0)
            list.remove("2月29日");
        list.remove("2月30日");
        list.remove("2月31日");
        list.remove("4月31日");
        list.remove("6月31日");
        list.remove("9月31日");
        list.remove("11月31日");
        //从今天开始
        String current = MM + "月" + DD + "日";
        list = list.subList(list.indexOf(current), list.size());
        
        //明年的时间
        List<String> listNextYear = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <= 31; j++) {
                String d = YY+1 + "年"+ i + "月" + j + "日";
                listNextYear.add(d);
            }
        }
        //如果不是闰年
        if(YY+1%4!=0)
            listNextYear.remove(YY+1 +"年"+ "2月29日");
        listNextYear.remove(YY+1 +"年" + "2月30日");
        listNextYear.remove(YY+1 +"年" + "2月31日");
        listNextYear.remove(YY+1 +"年" + "4月31日");
        listNextYear.remove(YY+1 +"年" + "6月31日");
        listNextYear.remove(YY+1 +"年" + "9月31日");
        listNextYear.remove(YY+1 +"年" + "11月31日");
        
        //合并两个list和listNextYear
        list.addAll(listNextYear);

        //只取365天
        list = list.subList(0,365);
        return list;
    }

    /**
     *
     * @return
     */
    private List<String> createTimeList() {

        List<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            String t1 = "0"+i+":"+"00";
            String t2 = "0"+i+":"+"30";
            list.add(t1);
            list.add(t2);
        }
        for(int i=10;i<24;i++){
            String t1 = i+":"+"00";
            String t2 = i+":"+"30";
            list.add(t1);
            list.add(t2);
        }

        return list;
    }

    /**
     * 监听键盘打开收起
     */
    private void listenSoftKeyboardState() {
        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.et_item_phone_content));
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                rlPreViewContract.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSoftKeyboardClosed() {
                rlPreViewContract.setVisibility(View.VISIBLE);
                if(machineLeaseData.getPhoneNum()!=null){
                    if(!CommonUtil.isMobileNum(machineLeaseData.getPhoneNum())){
                        Toast.makeText(MachineLeaseActivity.this,"联系电话输入不正确，请重新输入"
                        ,Toast.LENGTH_SHORT).show();
                        phoneNumContents.setText("");
                        machineLeaseData.setPhoneNum(null);
                    }
                }
            }
        });
    }

}
