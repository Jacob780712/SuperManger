package cn.bc.mchant.po;

import java.util.Date;
/*
 * 商户储值卡种
 */
public class MchCardKinds {
	private int id; //id
	private String ck_name;//储值卡种名称
	private int ck_quota;//储值卡种面额
	private int sales_amount;//售卡金额
	private String ck_type;//卡类型0买送 1折扣
	private String discount;//折扣
	private String period_validity;//有效期(月)
	private String mch_number;//商户编码
	private String mch_name;//商户名称
	private String status;//状态0上架 1下架
	private String remark;//备注
	private Date create_date;//创建日期
	private String create_person;//创建人
	private Date update_date;//修改日期
	private String update_person;//修改人
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCk_name() {
		return ck_name;
	}
	public void setCk_name(String ck_name) {
		this.ck_name = ck_name;
	}
	public int getCk_quota() {
		return ck_quota;
	}
	public void setCk_quota(int ck_quota) {
		this.ck_quota = ck_quota;
	}
	public int getSales_amount() {
		return sales_amount;
	}
	public void setSales_amount(int sales_amount) {
		this.sales_amount = sales_amount;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getPeriod_validity() {
		return period_validity;
	}
	public void setPeriod_validity(String period_validity) {
		this.period_validity = period_validity;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCk_type() {
		return ck_type;
	}
	public void setCk_type(String ck_type) {
		this.ck_type = ck_type;
	}
	
	
	
	
}
