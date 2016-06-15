package cn.bc.mchant.po;

import java.util.Date;

public class MchBrand {
	private int id;//id
	private String mch_number;//商户编号
	private String brand_info;//标题
	private String input_info;//录入信息
	private String encode_info;//转码文本
	private Date create_date;//创建日期
	private Date update_date;//修改日期
	
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
	public String getBrand_info() {
		return brand_info;
	}
	public void setBrand_info(String brand_info) {
		this.brand_info = brand_info;
	}
	public String getInput_info() {
		return input_info;
	}
	public void setInput_info(String input_info) {
		this.input_info = input_info;
	}
	public String getEncode_info() {
		return encode_info;
	}
	public void setEncode_info(String encode_info) {
		this.encode_info = encode_info;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	
}
