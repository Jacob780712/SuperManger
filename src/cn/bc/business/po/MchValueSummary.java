package cn.bc.business.po;

import java.util.Date;

public class MchValueSummary {
	private int id;
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private String kedan;//平均客单价
	private String ridan;//平均日单量
	private String yueliu;//平均月流水
	private String bussiness_amount;//总交易流水
	private String card_number;//储值卡总张数
	private String ck_quota;//储值总面额
	private String sales_amount;//购买储值的总金额
	private String available_card_number;//未用完储值卡张数
	private String card_balance;//储值余额
	private String back_amount;//返现总金额
	private Date create_date;//创建时间
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
	public String getKedan() {
		return kedan;
	}
	public void setKedan(String kedan) {
		this.kedan = kedan;
	}
	public String getRidan() {
		return ridan;
	}
	public void setRidan(String ridan) {
		this.ridan = ridan;
	}
	public String getYueliu() {
		return yueliu;
	}
	public void setYueliu(String yueliu) {
		this.yueliu = yueliu;
	}
	public String getBussiness_amount() {
		return bussiness_amount;
	}
	public void setBussiness_amount(String bussiness_amount) {
		this.bussiness_amount = bussiness_amount;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getCk_quota() {
		return ck_quota;
	}
	public void setCk_quota(String ck_quota) {
		this.ck_quota = ck_quota;
	}
	public String getSales_amount() {
		return sales_amount;
	}
	public void setSales_amount(String sales_amount) {
		this.sales_amount = sales_amount;
	}
	public String getAvailable_card_number() {
		return available_card_number;
	}
	public void setAvailable_card_number(String available_card_number) {
		this.available_card_number = available_card_number;
	}
	public String getCard_balance() {
		return card_balance;
	}
	public void setCard_balance(String card_balance) {
		this.card_balance = card_balance;
	}
	public String getBack_amount() {
		return back_amount;
	}
	public void setBack_amount(String back_amount) {
		this.back_amount = back_amount;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	
}
