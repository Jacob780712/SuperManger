package cn.bc.mchant.vo;

import java.util.Date;

public class GoodsFilter {
	private int id;
	private String goods_number;//商品编号
	private String goods_name;//商品名称
	private String goods_introd;//商品介绍
	private String goods_pic;//商品图片
	private int sale_price;//销售价格
	private int orig_price;//原价
	private String stock;//库存-1无限
	private String status;//状态0上架 1下架 9删除
	private Date create_date;//录入时间
	private String create_person;//创建人
	private Date update_date;//修改时间
	private String update_person;//修改人
	private int pageSize;
	private int pageNo;
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getGoods_introd() {
		return goods_introd;
	}
	public void setGoods_introd(String goods_introd) {
		this.goods_introd = goods_introd;
	}
	public String getGoods_pic() {
		return goods_pic;
	}
	public void setGoods_pic(String goods_pic) {
		this.goods_pic = goods_pic;
	}
	public int getSale_price() {
		return sale_price;
	}
	public void setSale_price(int sale_price) {
		this.sale_price = sale_price;
	}
	public int getOrig_price() {
		return orig_price;
	}
	public void setOrig_price(int orig_price) {
		this.orig_price = orig_price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
}
