package cn.bc.query.po;


public class PayCardCount {
	private String date;
	private String mch_name;
	private String mchantNo;
	private String ck_type;
	private String ck_name;
	private Long allCount;//总张数
	private Long amout;//总售卡金额
	private Long allck_ck_quota;//总售卡面额
	
	public Long getAllck_ck_quota() {
		return allck_ck_quota;
	}
	public void setAllck_ck_quota(Long allck_ck_quota) {
		this.allck_ck_quota = allck_ck_quota;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getAllCount() {
		return allCount;
	}
	public void setAllCount(Long allCount) {
		this.allCount = allCount;
	}
	public Long getAmout() {
		return amout;
	}
	public void setAmout(Long amout) {
		this.amout = amout;
	}
	public String getMch_name() {
		return mch_name;
	}
	
	public String getMchantNo() {
		return mchantNo;
	}
	public void setMchantNo(String mchantNo) {
		this.mchantNo = mchantNo;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	public String getCk_type() {
		return ck_type;
	}
	public void setCk_type(String ck_type) {
		this.ck_type = ck_type;
	}
	public String getCk_name() {
		return ck_name;
	}
	public void setCk_name(String ck_name) {
		this.ck_name = ck_name;
	}
	
}
