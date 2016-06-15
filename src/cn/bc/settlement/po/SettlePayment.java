package cn.bc.settlement.po;

import java.math.BigDecimal;

/**
 * Created by xxsoul on 2016/3/16.
 */
public class SettlePayment {
    private String singleType;//制单类型
    private String selfCertificate;//企业自制凭证                        *
    private String customerId;//客户号
    private String appointmentFlag;//预约标志号                    (default="0")

    private String payAccount;//付款账号
    private float payAmount;//交易（支付）金额
    private String collectionAccount;//收款账号
    private String collectionName;//收款人名称
    private String collectionAccountType;//收款账号类型

    private String subCustomerId;//子客户号                              *
    private String subPayAccount;//子付款账户                            *
    private String subAccountName;//子账户名                             *
    private String subPayAccountCreateBankName;//子支付账户开户名称      *
    private String usage;//用途                                          *

    private String remitting;//汇路                                      *
    private String notifyCollection;//是否通知收款人                     *
    private String collectionMobilePhoneNo;//(收款人)手机号码            *
    private String collectionMail;//收款人邮箱                           *

    private String payBankNoAndName;//支付行号和名称

    //根据支付行号生成打款信息
    public SettlePayment(String payBankNoAndName) {
        this.payBankNoAndName = payBankNoAndName;
        this.appointmentFlag = "0";
    }

    //以分为单位设置支付金额
    public void setPayAmountByCoin(int payAmountCoin){
        float res = payAmountCoin / 100.0f;
        BigDecimal bd = new BigDecimal(res);
        res = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        this.setPayAmount(res);
    }

    public String getSelfCertificate() {
        return selfCertificate;
    }

    public void setSelfCertificate(String selfCertificate) {
        this.selfCertificate = selfCertificate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAppointmentFlag() {
        return appointmentFlag;
    }

    public void setAppointmentFlag(String appointmentFlag) {
        this.appointmentFlag = appointmentFlag;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public float getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(float payAmount) {
        this.payAmount = payAmount;
    }

    public String getCollectionAccount() {
        return collectionAccount;
    }

    public void setCollectionAccount(String collectionAccount) {
        this.collectionAccount = collectionAccount;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionAccountType() {
        return collectionAccountType;
    }

    public void setCollectionAccountType(String collectionAccountType) {
        this.collectionAccountType = collectionAccountType;
    }

    public String getSubCustomerId() {
        return subCustomerId;
    }

    public void setSubCustomerId(String subCustomerId) {
        this.subCustomerId = subCustomerId;
    }

    public String getSubPayAccount() {
        return subPayAccount;
    }

    public void setSubPayAccount(String subPayAccount) {
        this.subPayAccount = subPayAccount;
    }

    public String getSubAccountName() {
        return subAccountName;
    }

    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName;
    }

    public String getSubPayAccountCreateBankName() {
        return subPayAccountCreateBankName;
    }

    public void setSubPayAccountCreateBankName(String subPayAccountCreateBankName) {
        this.subPayAccountCreateBankName = subPayAccountCreateBankName;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getRemitting() {
        return remitting;
    }

    public void setRemitting(String remitting) {
        this.remitting = remitting;
    }

    public String getNotifyCollection() {
        return notifyCollection;
    }

    public void setNotifyCollection(String notifyCollection) {
        this.notifyCollection = notifyCollection;
    }

    public String getCollectionMobilePhoneNo() {
        return collectionMobilePhoneNo;
    }

    public void setCollectionMobilePhoneNo(String collectionMobilePhoneNo) {
        this.collectionMobilePhoneNo = collectionMobilePhoneNo;
    }

    public String getCollectionMail() {
        return collectionMail;
    }

    public void setCollectionMail(String collectionMail) {
        this.collectionMail = collectionMail;
    }

    public String getPayBankNoAndName() {
        return payBankNoAndName;
    }

    public void setPayBankNoAndName(String payBankNoAndName) {
        this.payBankNoAndName = payBankNoAndName;
    }

    public String getSingleType() {
        return singleType;
    }

    public void setSingleType(String singleType) {
        this.singleType = singleType;
    }

}
