package cn.bc.query.vo;

public class TransDataBo {
	private String date;
	private String mch_name;
	private int weixin;//微信关注总数
	private int qg;//取消关注总数
	private int twomd;//买单2次及以上人数 | 首次买单人数
	private int twomk;//购卡2张及以上的人数 | 首次购卡人数
	private int renshu;//人数
	private int bishu;//注册数
	private int bussiness_amount;//买单总数
	private int pay_amount;//买卡人数
	private int allrenshu;
	private int allBishu;
	private int all_bussiness_amount;
	private int all_pay_amount;
	private int account_pay_amount;//账户支付金额 
	private int cofanxian;//平台返现金额
	private int fanxian;//返现总金额
	private int purchase_number;//购卡数量
	private int svc_quota;//储值面额
	private int purchase_amount;//购买金额
	private int svc_pay_amount;//储值卡支付金额（本人）
	
	
	public int getSvc_pay_amount() {
		return svc_pay_amount;
	}
	public void setSvc_pay_amount(int svc_pay_amount) {
		this.svc_pay_amount = svc_pay_amount;
	}
	public int getPurchase_amount() {
		return purchase_amount;
	}
	public void setPurchase_amount(int purchase_amount) {
		this.purchase_amount = purchase_amount;
	}
	public int getSvc_quota() {
		return svc_quota;
	}
	public void setSvc_quota(int svc_quota) {
		this.svc_quota = svc_quota;
	}
	public int getPurchase_number() {
		return purchase_number;
	}
	public void setPurchase_number(int purchase_number) {
		this.purchase_number = purchase_number;
	}
	public int getCofanxian() {
		return cofanxian;
	}
	public void setCofanxian(int cofanxian) {
		this.cofanxian = cofanxian;
	}
	public int getAccount_pay_amount() {
		return account_pay_amount;
	}
	public void setAccount_pay_amount(int account_pay_amount) {
		this.account_pay_amount = account_pay_amount;
	}
	public int getFanxian() {
		return fanxian;
	}
	public void setFanxian(int fanxian) {
		this.fanxian = fanxian;
	}
	public int getWeixin() {
		return weixin;
	}
	public void setWeixin(int weixin) {
		this.weixin = weixin;
	}
	public int getQg() {
		return qg;
	}
	public void setQg(int qg) {
		this.qg = qg;
	}
	public int getTwomd() {
		return twomd;
	}
	public void setTwomd(int twomd) {
		this.twomd = twomd;
	}
	public int getTwomk() {
		return twomk;
	}
	public void setTwomk(int twomk) {
		this.twomk = twomk;
	}
	public int getAllrenshu() {
		return allrenshu;
	}
	public void setAllrenshu(int allrenshu) {
		this.allrenshu = allrenshu;
	}
	public int getRenshu() {
		return renshu;
	}
	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	public int getBishu() {
		return bishu;
	}
	public void setBishu(int bishu) {
		this.bishu = bishu;
	}
	public int getBussiness_amount() {
		return bussiness_amount;
	}
	public void setBussiness_amount(int bussiness_amount) {
		this.bussiness_amount = bussiness_amount;
	}
	public int getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(int pay_amount) {
		this.pay_amount = pay_amount;
	}
	public int getAllBishu() {
		return allBishu;
	}
	public void setAllBishu(int allBishu) {
		this.allBishu = allBishu;
	}
	public int getAll_bussiness_amount() {
		return all_bussiness_amount;
	}
	public void setAll_bussiness_amount(int all_bussiness_amount) {
		this.all_bussiness_amount = all_bussiness_amount;
	}
	public int getAll_pay_amount() {
		return all_pay_amount;
	}
	public void setAll_pay_amount(int all_pay_amount) {
		this.all_pay_amount = all_pay_amount;
	}
	
	
}
