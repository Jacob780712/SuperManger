package cn.bc.business.po;

import java.util.Date;

public class CashOutStatis {
	private Date date;//日期
	private int applyCount;//提现申请笔数
	private int applyAmount;//提现申请金额
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getApplyCount() {
		return applyCount;
	}
	public void setApplyCount(int applyCount) {
		this.applyCount = applyCount;
	}
	public int getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(int applyAmount) {
		this.applyAmount = applyAmount;
	}
	
}
