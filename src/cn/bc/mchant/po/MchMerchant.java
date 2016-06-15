package cn.bc.mchant.po;

import java.util.Date;
/*
 * 商户基本信息
 */
public class MchMerchant {
	private int id; //商户id
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private String pic_url_logo;//商户logo
	private String mch_key_word;//商户关键词
	private String mch_group_id;//商户分类id
	private String sale_card_type;//售卡类型0买送卡 1买折卡
	private int svc_totle_balance;//储值卡总余额
	private String sale_poundage;//结算手续费
	private String status;//状态0开通 1关闭 9删除
	private String sale_card_status;//售卡状态0开通 1关闭
	private String discount_note;//折扣说明
	private String mch_max_discount;//最大折扣
	private String mch_min_discount;//最低折扣
	private String mch_biz_districts;//商户所属商圈
	private String type_open;//商户添加开通时是否购卡：0购卡 1不购卡 2未设置
	private Integer capita_consumption;//人均消费金额
	private String use_note;//使用说明
	private String mch_time_sale;//商户营业时间
	private String mch_card_range;//储值卡使用范围（哪类商品无法使用）
	private String mch_more;//其他
	private String mch_indus_name;//商户工商注册名
	private String mch_oper_per;//商户营业期限
	private String mch_leg_person;//商户法人
	private String remark;//备注
	private Date create_date;//创建日期
	private String create_person;//创建人
	private Date update_date;//修改日期
	private String update_person;//修改人
	private String vip_brief;//会员专享简介--首页
	private String vip_introd;//会员专享介绍--商户详情页
	
	
	
	
	public Integer getCapita_consumption() {
		return capita_consumption;
	}
	public void setCapita_consumption(Integer capita_consumption) {
		this.capita_consumption = capita_consumption;
	}
	public String getSale_card_type() {
		return sale_card_type;
	}
	public void setSale_card_type(String sale_card_type) {
		this.sale_card_type = sale_card_type;
	}
	public String getVip_brief() {
		return vip_brief;
	}
	public void setVip_brief(String vip_brief) {
		this.vip_brief = vip_brief;
	}
	public String getVip_introd() {
		return vip_introd;
	}
	public void setVip_introd(String vip_introd) {
		this.vip_introd = vip_introd;
	}
	public String getMch_biz_districts() {
		return mch_biz_districts;
	}
	public void setMch_biz_districts(String mch_biz_districts) {
		this.mch_biz_districts = mch_biz_districts;
	}
	public String getMch_max_discount() {
		return mch_max_discount;
	}
	public void setMch_max_discount(String mch_max_discount) {
		this.mch_max_discount = mch_max_discount;
	}
	public String getPic_url_logo() {
		return pic_url_logo;
	}
	public void setPic_url_logo(String pic_url_logo) {
		this.pic_url_logo = pic_url_logo;
	}
	public String getMch_min_discount() {
		return mch_min_discount;
	}
	public void setMch_min_discount(String mch_min_discount) {
		this.mch_min_discount = mch_min_discount;
	}
	public String getMch_time_sale() {
		return mch_time_sale;
	}
	public void setMch_time_sale(String mch_time_sale) {
		this.mch_time_sale = mch_time_sale;
	}
	public String getMch_card_range() {
		return mch_card_range;
	}
	public void setMch_card_range(String mch_card_range) {
		this.mch_card_range = mch_card_range;
	}
	public String getMch_more() {
		return mch_more;
	}
	public void setMch_more(String mch_more) {
		this.mch_more = mch_more;
	}
	public String getType_open() {
		return type_open;
	}
	public void setType_open(String type_open) {
		this.type_open = type_open;
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
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	
	public String getMch_key_word() {
		return mch_key_word;
	}
	public void setMch_key_word(String mch_key_word) {
		this.mch_key_word = mch_key_word;
	}
	public String getMch_group_id() {
		return mch_group_id;
	}
	public void setMch_group_id(String mch_group_id) {
		this.mch_group_id = mch_group_id;
	}
	public int getSvc_totle_balance() {
		return svc_totle_balance;
	}
	public void setSvc_totle_balance(int svc_totle_balance) {
		this.svc_totle_balance = svc_totle_balance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSale_card_status() {
		return sale_card_status;
	}
	public void setSale_card_status(String sale_card_status) {
		this.sale_card_status = sale_card_status;
	}
	public String getDiscount_note() {
		return discount_note;
	}
	public void setDiscount_note(String discount_note) {
		this.discount_note = discount_note;
	}
	public String getUse_note() {
		return use_note;
	}
	public void setUse_note(String use_note) {
		this.use_note = use_note;
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
	public String getSale_poundage() {
		return sale_poundage;
	}
	public void setSale_poundage(String sale_poundage) {
		this.sale_poundage = sale_poundage;
	}
	public String getMch_indus_name() {
		return mch_indus_name;
	}
	public void setMch_indus_name(String mch_indus_name) {
		this.mch_indus_name = mch_indus_name;
	}
	public String getMch_oper_per() {
		return mch_oper_per;
	}
	public void setMch_oper_per(String mch_oper_per) {
		this.mch_oper_per = mch_oper_per;
	}
	public String getMch_leg_person() {
		return mch_leg_person;
	}
	public void setMch_leg_person(String mch_leg_person) {
		this.mch_leg_person = mch_leg_person;
	}
	
}
