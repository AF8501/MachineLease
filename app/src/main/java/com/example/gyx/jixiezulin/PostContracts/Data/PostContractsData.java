package com.example.gyx.jixiezulin.PostContracts.Data;

/**
 * Created by gyx on 2016-12-06.
 *一、共有demandName;//需求名称，deposit;//保证金，balance;//余额三个数据。
 *
 * 二、格式要求：
 *
 *
 */

public class PostContractsData {
    private String demandName;//需求名称
    private float deposit;//保证金
    private float balance;//余额

    public PostContractsData() {
        deposit = -1;//未初始化标志
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

}
