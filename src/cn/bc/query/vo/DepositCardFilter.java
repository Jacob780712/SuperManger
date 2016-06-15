package cn.bc.query.vo;

import java.util.Date;

public class DepositCardFilter {
	private int id;
	private String ck_id;//储值卡种id
	private int accounts_id;//账户id
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private int svc_balance;//储值卡余额
	private String svc_number;//卡号
	private int purchase_amount;//购卡金额
	private Date expiry_date;//失效日期
	private String share;//0分享 1不分享
	private String status;//状态0正常 1用尽
	private Date last_usage_time;//最后使用时间(B用户)
	private String remark;//备注
	private int pageSize;
	private int pageNo;
	private String beginTime;
	private String endTime;
	private String mobile_phone;
	private String full_name;
	private String firstFlag;
	private String branch_name;//门店名称
	
	
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getFirstFlag() {
		return firstFlag;
	}
	public void setFirstFlag(String firstFlag) {
		this.firstFlag = firstFlag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCk_id() {
		return ck_id;
	}
	public void setCk_id(String ck_id) {
		this.ck_id = ck_id;
	}
	public int getAccounts_id() {
		return accounts_id;
	}
	public void setAccounts_id(int accounts_id) {
		this.accounts_id = accounts_id;
	}
	public String getMch_number() {
		return mch_number;
	}
	public void setMch_number(String mch_number) {
		this.mch_number = mch_number;
	}
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	public int getSvc_balance() {
		return svc_balance;
	}
	public void setSvc_balance(int svc_balance) {
		this.svc_balance = svc_balance;
	}
	public String getSvc_number() {
		return svc_number;
	}
	public void setSvc_number(String svc_number) {
		this.svc_number = svc_number;
	}
	public int getPurchase_amount() {
		return purchase_amount;
	}
	public void setPurchase_amount(int purchase_amount) {
		this.purchase_amount = purchase_amount;
	}
	public Date getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}
	public String getShare() {
		return share;
	}
	public void setShare(String share) {
		this.share = share;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getLast_usage_time() {
		return last_usage_time;
	}
	public void setLast_usage_time(Date last_usage_time) {
		this.last_usage_time = last_usage_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
}
