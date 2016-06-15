package cn.bc.mchant.po;

import java.util.Date;

public class MchCourtesyRef implements Comparable<MchCourtesyRef>{
	private int id;
	private String mch_number;//商户编号
	private String goods_number;//商品编号
	private String goods_name;//商品名称
	private String status;//状态
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//修改时间
	private String update_person;//修改人
	private Integer branch_id;//门店id
	
	
	public Integer getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getGoods_number() {
		return goods_number;
	}
	public void setGoods_number(String goods_number) {
		this.goods_number = goods_number;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
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
	@Override
	public int compareTo(MchCourtesyRef o) {
		// TODO Auto-generated method stub
		return this.goods_number.compareTo(o.getGoods_number());
	}
	
}
