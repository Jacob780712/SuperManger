package cn.bc.query.vo;

import java.util.Date;

public class TransactionDetail {
	private int id;
	private String order_number;
	private int user_id;
	private String mobile_phone;
	private String order_type;
	private Integer business_amount;
	private String mch_number;
	private String mch_name;
	private Integer svc_pay_amount;
	private Integer account_pay_amount;
	private Integer pay_amount;
	private String pay_source;
	private String status;//状态0未支付 1已支付 2已失效
	private Integer o_return_amount;
	private Date create_date;
	private String firstFlag;
	private String userType;//用户类型 1是本店会员，0不是本店会员
	private Integer fanxian;//储值用户返现金额
	private Integer youhui;//优惠金额
	private Integer spendAmount;//储值卡扣除总额
	private String branch_name;//门店名称
	
	
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public Integer getSpendAmount() {
		return spendAmount;
	}
	public void setSpendAmount(Integer spendAmount) {
		this.spendAmount = spendAmount;
	}
	public Integer getYouhui() {
		return youhui;
	}
	public void setYouhui(Integer youhui) {
		this.youhui = youhui;
	}
	public Integer getFanxian() {
		return fanxian;
	}
	public void setFanxian(Integer fanxian) {
		this.fanxian = fanxian;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public Integer getBusiness_amount() {
		return business_amount;
	}
	public void setBusiness_amount(Integer business_amount) {
		this.business_amount = business_amount;
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
	public Integer getSvc_pay_amount() {
		return svc_pay_amount;
	}
	public void setSvc_pay_amount(Integer svc_pay_amount) {
		this.svc_pay_amount = svc_pay_amount;
	}
	public Integer getAccount_pay_amount() {
		return account_pay_amount;
	}
	public void setAccount_pay_amount(Integer account_pay_amount) {
		this.account_pay_amount = account_pay_amount;
	}
	public Integer getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(Integer pay_amount) {
		this.pay_amount = pay_amount;
	}
	public String getPay_source() {
		return pay_source;
	}
	public void setPay_source(String pay_source) {
		this.pay_source = pay_source;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getO_return_amount() {
		return o_return_amount;
	}
	public void setO_return_amount(Integer o_return_amount) {
		this.o_return_amount = o_return_amount;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getFirstFlag() {
		return firstFlag;
	}
	public void setFirstFlag(String firstFlag) {
		this.firstFlag = firstFlag;
	}
	
	
	
}
