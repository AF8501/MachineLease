package com.example.gyx.jixiezulin.PostContracts;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gyx.jixiezulin.MachineLease.Data.MachineLeaseData;
import com.example.gyx.jixiezulin.MachineLease.MachineLeaseActivity;
import com.example.gyx.jixiezulin.PostContracts.Data.PostContractsData;
import com.example.gyx.jixiezulin.R;

/**
 * Created by gyx on 2016-12-10.
 *
 */

public class PostContractsActivity extends AppCompatActivity {

    private Button btnPostContracts;

    private PostContractsData postContractsData;

    private MachineLeaseData machineLeaseData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_contracts);
        initToolbar();
        initView();
        machineLeaseData = getIntent().getBundleExtra(MachineLeaseActivity.MACHINE_LEASE_DATA).getParcelable(MachineLeaseActivity.MACHINE_LEASE_DATA);
        testData();//测试数据
    }


    private void testData() {
        postContractsData = new PostContractsData();
        postContractsData.setDemandName("挖车租赁");
        postContractsData.setDeposit(2000.00f);
        postContractsData.setBalance(1000.00f);
    }

    private void initView() {
        btnPostContracts = $(R.id.btn_item_post_contract);
        btnPostContracts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(postContractsData.getBalance()>= postContractsData.getDeposit()){
                    showEnoughDialog();
                }else {
                    showUnEnoughDialog();
                }
            }
        });
    }

    /**
     * 显示余额不足时的确认对话框
     */
    private void showUnEnoughDialog() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_post_contracts_unenough, null);
        final Dialog dialog = new Dialog(this,R.style.PostContractsDialog);
        dialog.setContentView(outerView);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = -100;//设置对话框的Y轴位置，相对位置。
        TextView tvDialgTip = (TextView)outerView.findViewById(R.id.tv_post_contracts_dialog_enough);
        Button btnDialogWechat = (Button)outerView.findViewById(R.id.btn_post_contracts_dialog_unenough_wechat);
        Button btnDialogAlipay = (Button)outerView.findViewById(R.id.btn_post_contracts_dialog_unenough_alipay);
        Button btnDialogCancel = (Button)outerView.findViewById(R.id.btn_post_contracts_dialog_unenough_cancel);
        if(postContractsData!=null){
            if(postContractsData.getDemandName()!=null&&postContractsData.getDeposit()!=-1){
                String text = "发布"+ postContractsData.getDemandName()+"需求需要"
                        +postContractsData.getDeposit()+"元保证金，"+"\n"
                        +"当前余额"+postContractsData.getBalance()+"元\n"
                        +"保证金会在交易完后退还。";
                tvDialgTip.setText(text);
            }
        }
        btnDialogWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo:点击微信支付后相应动作
            }
        });
        btnDialogAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo:点击支付宝支付后相应动作

            }
        });
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    /**
     * 显示余额充足时的确认对话框
     */
    private void showEnoughDialog() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_post_contracts_enough, null);
        final Dialog dialog = new Dialog(this,R.style.PostContractsDialog);
        dialog.setContentView(outerView);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = -100;//设置对话框的Y轴位置，相对位置。
        TextView tvDialgTip = (TextView)outerView.findViewById(R.id.tv_post_contracts_dialog_enough);
        Button btnDialogOk = (Button)outerView.findViewById(R.id.btn_post_contracts_dialog_enough_ok);
        Button btnDialogcancel = (Button)outerView.findViewById(R.id.btn_post_contracts_dialog_enough_cancel);
        if(postContractsData!=null){
            if(postContractsData.getDemandName()!=null&&postContractsData.getDeposit()!=-1){
                String text = "发布"+ postContractsData.getDemandName()+"需求需要"
                        +postContractsData.getDeposit()+"保证金，保证金会在交易完后退还。";
                tvDialgTip.setText(text);
            }
        }
        btnDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo:点击确认支付后相应动作
            }
        });
        btnDialogcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView txTitle = (TextView) findViewById(R.id.toolbar_title);
        txTitle.setText(R.string.PreviewContractsActivity_title);
        ImageButton imgBack = (ImageButton) findViewById(R.id.toolbar_back);
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
}
