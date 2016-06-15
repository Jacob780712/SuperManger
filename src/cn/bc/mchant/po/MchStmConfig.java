package cn.bc.mchant.po;

import java.util.Date;

public class MchStmConfig {
	private int id;
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private String mch_account_card;//结算银行账号
	private String mch_account_name;//账户名称
	private String mch_bank_open_bank;//开户行 
	private String mch_bank_open_bank_no;//支行行号
	private String mch_bank_open_addr;//开户地址
	private String mch_account_type;//账户类型：0个人户、1企业户
	private String mch_account_person;//联系人
	private String mch_account_phone;//联系电话
	private String mch_email;//邮箱
	private String mch_setment_type;//结算类型：1全额  2非全额
	private int mch_settlement_period;// 结算周期类型1 2 3 
	private String mch_advance_quota;//公司垫款限额，单位分
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//修改时间
	private String update_person;//修改人
	
	public String getMch_bank_open_bank_no() {
		return mch_bank_open_bank_no;
	}
	public void setMch_bank_open_bank_no(String mch_bank_open_bank_no) {
		this.mch_bank_open_bank_no = mch_bank_open_bank_no;
	}
	public String getMch_account_type() {
		return mch_account_type;
	}
	public void setMch_account_type(String mch_account_type) {
		this.mch_account_type = mch_account_type;
	}
	public String getMch_account_card() {
		return mch_account_card;
	}
	
	public String getMch_setment_type() {
		return mch_setment_type;
	}
	public void setMch_setment_type(String mch_setment_type) {
		this.mch_setment_type = mch_setment_type;
	}
	public void setMch_account_card(String mch_account_card) {
		this.mch_account_card = mch_account_card;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getMch_account_name() {
		return mch_account_name;
	}
	public void setMch_account_name(String mch_account_name) {
		this.mch_account_name = mch_account_name;
	}
	public String getMch_bank_open_bank() {
		return mch_bank_open_bank;
	}
	public void setMch_bank_open_bank(String mch_bank_open_bank) {
		this.mch_bank_open_bank = mch_bank_open_bank;
	}
	public String getMch_bank_open_addr() {
		return mch_bank_open_addr;
	}
	public void setMch_bank_open_addr(String mch_bank_open_addr) {
		this.mch_bank_open_addr = mch_bank_open_addr;
	}
	public String getMch_account_person() {
		return mch_account_person;
	}
	public void setMch_account_person(String mch_account_person) {
		this.mch_account_person = mch_account_person;
	}
	public String getMch_account_phone() {
		return mch_account_phone;
	}
	public void setMch_account_phone(String mch_account_phone) {
		this.mch_account_phone = mch_account_phone;
	}
	public String getMch_email() {
		return mch_email;
	}
	public void setMch_email(String mch_email) {
		this.mch_email = mch_email;
	}
	public int getMch_settlement_period() {
		return mch_settlement_period;
	}
	public void setMch_settlement_period(int mch_settlement_period) {
		this.mch_settlement_period = mch_settlement_period;
	}
	public String getMch_advance_quota() {
		return mch_advance_quota;
	}
	public void setMch_advance_quota(String mch_advance_quota) {
		this.mch_advance_quota = mch_advance_quota;
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
