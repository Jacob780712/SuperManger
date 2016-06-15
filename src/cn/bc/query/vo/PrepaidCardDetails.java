package cn.bc.query.vo;

import java.util.Date;

public class PrepaidCardDetails {
	private String mch_name;
	private String ck_name;
	private String status;
	private String share;//0分享 1不分享
	private String svc_number;//
	private Integer ck_quota;
	private Integer sales_amount;
	private Integer svc_balance;
	private String name;
	private String mobile;//
	private String firstFlag;//是否首次购卡 1是 0否
	private Integer zhangshu;//
	private Date create_date;//
	private String ck_type;//类型
	private Integer ck_discount;//折扣
	private String branch_name;//门店名称
	private String id;//储值卡id
	private String accoutsId;//账户id
	
	
	
	public String getAccoutsId() {
		return accoutsId;
	}
	public void setAccoutsId(String accoutsId) {
		this.accoutsId = accoutsId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShare() {
		return share;
	}
	public void setShare(String share) {
		this.share = share;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getCk_type() {
		return ck_type;
	}
	public void setCk_type(String ck_type) {
		this.ck_type = ck_type;
	}
	public Integer getCk_discount() {
		return ck_discount;
	}
	public void setCk_discount(Integer ck_discount) {
		this.ck_discount = ck_discount;
	}
	public String getSvc_number() { 
		return svc_number;
	}
	public void setSvc_number(String svc_number) {
		this.svc_number = svc_number;
	}
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	public String getCk_name() {
		return ck_name;
	}
	public void setCk_name(String ck_name) {
		this.ck_name = ck_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCk_quota() {
		return ck_quota;
	}
	public void setCk_quota(Integer ck_quota) {
		this.ck_quota = ck_quota;
	}
	public Integer getSales_amount() {
		return sales_amount;
	}
	public void setSales_amount(Integer sales_amount) {
		this.sales_amount = sales_amount;
	}
	public Integer getSvc_balance() {
		return svc_balance;
	}
	public void setSvc_balance(Integer svc_balance) {
		this.svc_balance = svc_balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFirstFlag() {
		return firstFlag;
	}
	public void setFirstFlag(String firstFlag) {
		this.firstFlag = firstFlag;
	}
	public Integer getZhangshu() {
		return zhangshu;
	}
	public void setZhangshu(Integer zhangshu) {
		this.zhangshu = zhangshu;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
}
