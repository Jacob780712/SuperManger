package cn.bc.settlement.po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxsoul on 2016/3/16.
 */
public class BatchSettlePayment {
    private String monitorialMode;//审核方式
    private float totalAmount;//总金额
    private int totalCount;//总笔数
    private List<SettlePayment> paymentList;

    public BatchSettlePayment() {
        this.monitorialMode = "1";
        totalAmount = 0.0f;
        totalCount = 0;
        paymentList = new ArrayList<SettlePayment>();
    }

    public String getMonitorialMode() {
        return monitorialMode;
    }

    public void setMonitorialMode(String monitorialMode) {
        this.monitorialMode = monitorialMode;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<SettlePayment> getPaymentList() {
        return paymentList;
    }

    public void addSettltPayment(SettlePayment payment){
        if(payment.getPayAmount() < 0)
            return;
        this.totalCount++;
        this.totalAmount += payment.getPayAmount();
        this.paymentList.add(payment);
    }
}
