package cn.bc.settlement.po;

/**
 * Created by xxsoul on 2016/3/16.
 */
public class PaymentInfo {
    private String customerId;//客户号
    private String paymentAccount;//付款账号
    private String bankName;//银行名称

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
