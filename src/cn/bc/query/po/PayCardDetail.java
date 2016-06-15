package cn.bc.query.po;


public class PayCardDetail {
	private String mch_name;//商户名称
	private int ck_id;//卡种id
	private String ck_name;//卡种名称
	private String ck_type;//卡种类型 储值卡类型0买送 1折扣
	private Long cardAccount;//售卡张数
	private Long amout;//售卡金额
	private Double sale_poundage;//售卡手续费
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	public int getCk_id() {
		return ck_id;
	}
	public void setCk_id(int ck_id) {
		this.ck_id = ck_id;
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
	public Long getCardAccount() {
		return cardAccount;
	}
	public void setCardAccount(Long cardAccount) {
		this.cardAccount = cardAccount;
	}
	public Long getAmout() {
		return amout;
	}
	public void setAmout(Long amout) {
		this.amout = amout;
	}
	public Double getSale_poundage() {
		return sale_poundage;
	}
	public void setSale_poundage(Double sale_poundage) {
		this.sale_poundage = sale_poundage;
	}
	
}
