package cn.bc.business.vo;

public class AnalysBo {
	private String mchName;//商户名称
	private String mchNumber;//商户编号
	private String ck_name;//储值卡种名称
	private Integer ck_count;//可用数量
	private Integer ck_quota;//面额
	private Integer sales_amount;//售卡金额
	private Integer shouyi;//分享收益金额
	private Double zhouqi;//返现周期
	private Double yuehua;//月化收益
	private Double nianhua;//年化收益
	private String status;//储值卡种状态  状态0上架 1下架
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMchNumber() {
		return mchNumber;
	}
	public void setMchNumber(String mchNumber) {
		this.mchNumber = mchNumber;
	}
	public Integer getCk_count() {
		return ck_count;
	}
	public void setCk_count(Integer ck_count) {
		this.ck_count = ck_count;
	}
	public String getMchName() {
		return mchName;
	}
	public void setMchName(String mchName) {
		this.mchName = mchName;
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
	public Integer getShouyi() {
		return shouyi;
	}
	public void setShouyi(Integer shouyi) {
		this.shouyi = shouyi;
	}
	public Double getZhouqi() {
		return zhouqi;
	}
	public void setZhouqi(Double zhouqi) {
		this.zhouqi = zhouqi;
	}
	public Double getYuehua() {
		return yuehua;
	}
	public void setYuehua(Double yuehua) {
		this.yuehua = yuehua;
	}
	public Double getNianhua() {
		return nianhua;
	}
	public void setNianhua(Double nianhua) {
		this.nianhua = nianhua;
	}
	
}
	
