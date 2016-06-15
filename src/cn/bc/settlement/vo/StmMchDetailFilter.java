package cn.bc.settlement.vo;

import java.util.Date;
/*
 * 商户结算  付款
 */
public class StmMchDetailFilter {
	private int id;//id
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private String type;//结算类型 0 商户开通首次购卡 1按周期结算 2提前结算
	private Date start_date;//开始日期
	private Date end_date;//结束日期
	private int upper_dps_balance;//上期储值余额
	private int cur_add_dps_amout;//本期新增储值
	private Date upper_dps_date;//上期结算日(上期的结算日期）
	private int upper_prepay;//上期预付
	private int cur_consume;//本期消费
	private int cur_arrears;//本期商户垫款（商户本期消费-上期预付）
	private int all_dps_balance;//未结算储值余额（总数）
	private String last_to_this_day;//距上个结算日（天）
	private String this_to_next_day;//距下个结算日（天）
	private int cur_prepay;//本期实际预付(付款）
	private int cur_fee;//本期结算手续费
	private int cur_prepay_net;//本期预付净额
	private String status;//结算状态0未结算 1已结算
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
	private String fkdate;//付款日期
	private int pageSize;
	private int pageNo;
	private String beginTime;
	private String endTime;
	
	
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	public String getFkdate() {
		return fkdate;
	}
	public void setFkdate(String fkdate) {
		this.fkdate = fkdate;
	}
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
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public int getUpper_dps_balance() {
		return upper_dps_balance;
	}
	public void setUpper_dps_balance(int upper_dps_balance) {
		this.upper_dps_balance = upper_dps_balance;
	}
	public int getCur_add_dps_amout() {
		return cur_add_dps_amout;
	}
	public void setCur_add_dps_amout(int cur_add_dps_amout) {
		this.cur_add_dps_amout = cur_add_dps_amout;
	}
	public Date getUpper_dps_date() {
		return upper_dps_date;
	}
	public void setUpper_dps_date(Date upper_dps_date) {
		this.upper_dps_date = upper_dps_date;
	}
	public int getUpper_prepay() {
		return upper_prepay;
	}
	public void setUpper_prepay(int upper_prepay) {
		this.upper_prepay = upper_prepay;
	}
	public int getCur_consume() {
		return cur_consume;
	}
	public void setCur_consume(int cur_consume) {
		this.cur_consume = cur_consume;
	}
	public int getCur_arrears() {
		return cur_arrears;
	}
	public void setCur_arrears(int cur_arrears) {
		this.cur_arrears = cur_arrears;
	}
	public int getAll_dps_balance() {
		return all_dps_balance;
	}
	public void setAll_dps_balance(int all_dps_balance) {
		this.all_dps_balance = all_dps_balance;
	}
	public String getLast_to_this_day() {
		return last_to_this_day;
	}
	public void setLast_to_this_day(String last_to_this_day) {
		this.last_to_this_day = last_to_this_day;
	}
	public String getThis_to_next_day() {
		return this_to_next_day;
	}
	public void setThis_to_next_day(String this_to_next_day) {
		this.this_to_next_day = this_to_next_day;
	}
	public int getCur_prepay() {
		return cur_prepay;
	}
	public void setCur_prepay(int cur_prepay) {
		this.cur_prepay = cur_prepay;
	}
	public int getCur_fee() {
		return cur_fee;
	}
	public void setCur_fee(int cur_fee) {
		this.cur_fee = cur_fee;
	}
	public int getCur_prepay_net() {
		return cur_prepay_net;
	}
	public void setCur_prepay_net(int cur_prepay_net) {
		this.cur_prepay_net = cur_prepay_net;
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
