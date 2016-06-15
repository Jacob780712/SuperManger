package cn.bc.mchant.po;

import java.util.Date;
/*
 * 商户基本信息
 */
public class MchMerchantBrand {
	private int id; //商户id
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private Integer brandId;//品牌信息的id
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
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	
	
	
}
