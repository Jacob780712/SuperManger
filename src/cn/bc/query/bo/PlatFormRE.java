package cn.bc.query.bo;


/*
 * 平台收支明细
 */
public class PlatFormRE {
	private String date;//日期
	private int weixinAmount;//微信手续费
	private int tixianAmount;//提现手续费
	private int zhichu;//支出
	private int machantAmount;//商户手续费
	private int platAmount;//B类消费平台手续费
	private int shouru;//收入
	private int all;//净收入
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getWeixinAmount() {
		return weixinAmount;
	}
	public void setWeixinAmount(int weixinAmount) {
		this.weixinAmount = weixinAmount;
	}
	public int getTixianAmount() {
		return tixianAmount;
	}
	public void setTixianAmount(int tixianAmount) {
		this.tixianAmount = tixianAmount;
	}
	public int getZhichu() {
		return zhichu;
	}
	public void setZhichu(int zhichu) {
		this.zhichu = zhichu;
	}
	public int getMachantAmount() {
		return machantAmount;
	}
	public void setMachantAmount(int machantAmount) {
		this.machantAmount = machantAmount;
	}
	public int getPlatAmount() {
		return platAmount;
	}
	public void setPlatAmount(int platAmount) {
		this.platAmount = platAmount;
	}
	public int getShouru() {
		return shouru;
	}
	public void setShouru(int shouru) {
		this.shouru = shouru;
	}
	public int getAll() {
		return all;
	}
	public void setAll(int all) {
		this.all = all;
	}
	
	
}
