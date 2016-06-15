package cn.bc.mchant.po;

import java.util.Date;
/*
 * 分店基本信息
 */
public class MchMerchantBranch {
	private int id;//id
	private String mch_number;//商户编号
	private int city_id;//分店城市id
	private String city_name;//分店城市名称
	private String branch_name;//分店名称
	private String branch_pic_url;//分店图片
	private String branch_addr;//分店地址
	private String telephone;//电话
	private String longitude;//经度
	private String latitude;//纬度
	private String status;//状态0开通 1关闭 9删除
	private String remark;//备注
	private Date create_date;//创建日期
	private String create_person;//创建人
	private Date update_date;//修改日期
	private String update_person;//修改人
	private String biz_districts;//商圈
	private String use_note;//使用须知
	
	public String getBiz_districts() {
		return biz_districts;
	}
	public void setBiz_districts(String biz_districts) {
		this.biz_districts = biz_districts;
	}
	public String getUse_note() {
		return use_note;
	}
	public void setUse_note(String use_note) {
		this.use_note = use_note;
	}
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
	public int getCity_id() {
		return city_id;
	}
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getBranch_pic_url() {
		return branch_pic_url;
	}
	public void setBranch_pic_url(String branch_pic_url) {
		this.branch_pic_url = branch_pic_url;
	}
	public String getBranch_addr() {
		return branch_addr;
	}
	public void setBranch_addr(String branch_addr) {
		this.branch_addr = branch_addr;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getCreate_person() {
		return create_person;
	}
	public void setCreate_person(String create_person) {
		this.create_person = create_person;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getUpdate_person() {
		return update_person;
	}
	public void setUpdate_person(String update_person) {
		this.update_person = update_person;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
}
