package cn.bc.query.po;

import java.util.Date;

public class CoDepositCard {
	private int id;
	private String ck_id;//储值卡种id
	private String ck_name;//储值卡种名称
	private String ck_type;//储值卡类型名称
	private int co_id;//账户id
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private int svc_balance;//储值卡余额
	private String svc_number;//卡号
	private int purchase_amount;//购卡金额
	private int ck_quota;//面额
	private Date expiry_date;//失效日期
	private String share;//0分享 1不分享
	private String status;//状态0正常 1用尽
	private Date last_usage_time;//最后使用时间(B用户)
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
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
	
	public int getCo_id() {
		return co_id;
	}
	public void setCo_id(int co_id) {
		this.co_id = co_id;
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
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getCreate_person() {
		return create_person;
	}
	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getUpdate_person() {
		return update_person;
	}
	public void setUpdate_person(String update_person) {
		this.update_person = update_person;
	}
	public String getCk_name() {
		return ck_name;
	}
	public void setCk_name(String ck_name) {
		this.ck_name = ck_name;
	}
	public String getCk_type() {
		return ck_type;
	}
	public void setCk_type(String ck_type) {
		this.ck_type = ck_type;
	}
	public int getCk_quota() {
		return ck_quota;
	}
	public void setCk_quota(int ck_quota) {
		this.ck_quota = ck_quota;
	}
	
}
