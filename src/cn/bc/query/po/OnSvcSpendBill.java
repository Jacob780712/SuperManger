package cn.bc.query.po;

import java.util.Date;

public class OnSvcSpendBill {
	private int id;//id
	private String type;//类型0买单 1他人买单 2购买 3充值
	private String status;//状态0未完成 1已成功 2已撤销
	private int svc_id;//储蓄卡id
	private Integer actual_amount;//实际返现金额（消费的储值卡金额*购卡折扣）
	private int spend_amount;//消费金额
	private int svc_balance;//储值卡余额
	private String order_number;//订单号
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
	private String mobile_phone;//购卡人手机号
	private String co_tel;//公司电话
	private String full_name;//购卡人姓名
	private String co_name;//公司购卡姓名
	private String ck_name;//储值卡种名称
	private String ck_name_co;//储值卡种名称平台购卡表
	private String ck_type;//储值卡类型名称
	private String ck_type_co;//储值卡类型名称平台购卡表
	private String svc_number;//个人储值卡卡号
	private String co_svc_number;//公司储值卡号
	private String mch_name;//商户名称
	private String mch_name_co;//商户名称平台购卡表
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCo_tel() {
		return co_tel;
	}

	public void setCo_tel(String co_tel) {
		this.co_tel = co_tel;
	}

	public String getCo_name() {
		return co_name;
	}

	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}

	public String getType() {
		return type;
	}
	
	public String getCk_name_co() {
		return ck_name_co;
	}

	public void setCk_name_co(String ck_name_co) {
		this.ck_name_co = ck_name_co;
	}

	public String getCk_type_co() {
		return ck_type_co;
	}

	public void setCk_type_co(String ck_type_co) {
		this.ck_type_co = ck_type_co;
	}

	public String getMch_name_co() {
		return mch_name_co;
	}

	public void setMch_name_co(String mch_name_co) {
		this.mch_name_co = mch_name_co;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getCo_svc_number() {
		return co_svc_number;
	}
	public void setCo_svc_number(String co_svc_number) {
		this.co_svc_number = co_svc_number;
	}
	public Integer getActual_amount() {
		return actual_amount;
	}
	public void setActual_amount(Integer actual_amount) {
		this.actual_amount = actual_amount;
	}
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSvc_id() {
		return svc_id;
	}
	public void setSvc_id(int svc_id) {
		this.svc_id = svc_id;
	}
	public int getSpend_amount() {
		return spend_amount;
	}
	public void setSpend_amount(int spend_amount) {
		this.spend_amount = spend_amount;
	}
	public int getSvc_balance() {
		return svc_balance;
	}
	
	public String getSvc_number() {
		return svc_number;
	}
	public void setSvc_number(String svc_number) {
		this.svc_number = svc_number;
	}
	public void setSvc_balance(int svc_balance) {
		this.svc_balance = svc_balance;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
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
	
}
