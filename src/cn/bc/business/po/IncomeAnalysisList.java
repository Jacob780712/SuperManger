package cn.bc.business.po;

import java.util.Date;
/*
 * 储值收益分析列表
 */
public class IncomeAnalysisList {
	private int id;
	private String mch_name;//商户名称
	private String mch_number;//商户编号
	private String ck_name;//储值卡名称
	private String ck_id;//储值卡种id
	private Integer ck_quota;//储值卡面额
	private Integer sales_amount;//售卡金额
	private String ck_count;//可用数量
	private String shouyi;//收益金额
	private String zhouqi;//周期
	private String yuehua;//月化收益
	private String nianhua;//年化收益
	private Date create_date;//创建日期
	
	public String getCk_count() {
		return ck_count;
	}
	public void setCk_count(String ck_count) {
		this.ck_count = ck_count;
	}
	public String getCk_id() {
		return ck_id;
	}
	public void setCk_id(String ck_id) {
		this.ck_id = ck_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	public String getMch_number() {
		return mch_number;
	}
	public void setMch_number(String mch_number) {
		this.mch_number = mch_number;
	}
	public String getCk_name() {
		return ck_name;
	}
	public void setCk_name(String ck_name) {
		this.ck_name = ck_name;
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
	public String getShouyi() {
		return shouyi;
	}
	public void setShouyi(String shouyi) {
		this.shouyi = shouyi;
	}
	public String getZhouqi() {
		return zhouqi;
	}
	public void setZhouqi(String zhouqi) {
		this.zhouqi = zhouqi;
	}
	public String getYuehua() {
		return yuehua;
	}
	public void setYuehua(String yuehua) {
		this.yuehua = yuehua;
	}
	public String getNianhua() {
		return nianhua;
	}
	public void setNianhua(String nianhua) {
		this.nianhua = nianhua;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	
}
