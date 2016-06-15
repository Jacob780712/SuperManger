package cn.bc.query.po;

import java.util.Date;

public class OrderPayCourtesy {
	private int id;
	private String order_number;//订单编号
	private int user_id;//用户id
	private String mobile_phone;//手机号
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private int purchase_amount;//购买金额
	private int svc_pay_amount;//储值卡支付金额
	private int account_pay_amount;//账户支付金额
	private int pay_amount;//应付金额
	private String pay_source;//支付渠道0账户 1微信 2支付宝 3商户售卡
	private String status;//状态0未支付 1已支付 2已失效
	private int o_return_amount;//平台返现金额
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//修改时间
	private String update_person;//修改人
	private Integer branch_id;//门店id
	private String branchName;//门店名称
	
	
	public Integer getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	public int getPurchase_amount() {
		return purchase_amount;
	}
	public void setPurchase_amount(int purchase_amount) {
		this.purchase_amount = purchase_amount;
	}
	public int getSvc_pay_amount() {
		return svc_pay_amount;
	}
	public void setSvc_pay_amount(int svc_pay_amount) {
		this.svc_pay_amount = svc_pay_amount;
	}
	public int getAccount_pay_amount() {
		return account_pay_amount;
	}
	public void setAccount_pay_amount(int account_pay_amount) {
		this.account_pay_amount = account_pay_amount;
	}
	public int getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(int pay_amount) {
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
	public int getO_return_amount() {
		return o_return_amount;
	}
	public void setO_return_amount(int o_return_amount) {
		this.o_return_amount = o_return_amount;
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
	
}
