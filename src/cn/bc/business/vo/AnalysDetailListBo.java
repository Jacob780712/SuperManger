package cn.bc.business.vo;

public class AnalysDetailListBo {
	private String ck_name;//卡种名称
	private Integer miane;//面额
	private Integer saleAmount;//售卡金额
	private Integer zhangshu;//储值卡张数
	private Integer allMiane;//总面额
	private Integer allSaleAmount;//总售卡金额
	private Integer balance;//储值余额
	private Integer fanxian;//累计返现
	public String getCk_name() {
		return ck_name;
	}
	public void setCk_name(String ck_name) {
		this.ck_name = ck_name;
	}
	public Integer getMiane() {
		return miane;
	}
	public void setMiane(Integer miane) {
		this.miane = miane;
	}
	public Integer getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(Integer saleAmount) {
		this.saleAmount = saleAmount;
	}
	public Integer getZhangshu() {
		return zhangshu;
	}
	public void setZhangshu(Integer zhangshu) {
		this.zhangshu = zhangshu;
	}
	public Integer getAllMiane() {
		return allMiane;
	}
	public void setAllMiane(Integer allMiane) {
		this.allMiane = allMiane;
	}
	public Integer getAllSaleAmount() {
		return allSaleAmount;
	}
	public void setAllSaleAmount(Integer allSaleAmount) {
		this.allSaleAmount = allSaleAmount;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getFanxian() {
		return fanxian;
	}
	public void setFanxian(Integer fanxian) {
		this.fanxian = fanxian;
	}
	
}
