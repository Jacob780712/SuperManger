package cn.bc.business.po;

import java.util.Date;

public class MchValueList {
	private int id;//
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private String ck_id;//储值卡种id
	private String ck_name;//储值卡种名称
	private String ck_quate;//卡面额
	private String sale_amount;//售卡金额
	private String card_number;//该卡种的总张数
	private String all_ck_quate;//总面额
	private String all_sale_amont;//总售卡金额
	private String card_balance;//该卡种的总储值余额
	private String total_actrul_amount;//该卡种累计返现
	private Date create_date;//创建日期时间
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
	public String getCk_id() {
		return ck_id;
	}
	public void setCk_id(String ck_id) {
		this.ck_id = ck_id;
	}
	public String getCk_name() {
		return ck_name;
	}
	public void setCk_name(String ck_name) {
		this.ck_name = ck_name;
	}
	public String getCk_quate() {
		return ck_quate;
	}
	public void setCk_quate(String ck_quate) {
		this.ck_quate = ck_quate;
	}
	public String getSale_amount() {
		return sale_amount;
	}
	public void setSale_amount(String sale_amount) {
		this.sale_amount = sale_amount;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getAll_ck_quate() {
		return all_ck_quate;
	}
	public void setAll_ck_quate(String all_ck_quate) {
		this.all_ck_quate = all_ck_quate;
	}
	public String getAll_sale_amont() {
		return all_sale_amont;
	}
	public void setAll_sale_amont(String all_sale_amont) {
		this.all_sale_amont = all_sale_amont;
	}
	public String getCard_balance() {
		return card_balance;
	}
	public void setCard_balance(String card_balance) {
		this.card_balance = card_balance;
	}
	public String getTotal_actrul_amount() {
		return total_actrul_amount;
	}
	public void setTotal_actrul_amount(String total_actrul_amount) {
		this.total_actrul_amount = total_actrul_amount;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	
}
