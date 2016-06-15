package cn.bc.query.vo;

import java.util.Date;

public class OrderSubPayCardFilter {
	private int id;
	private String flag;//a 查询统计 b查询详情
	private String order_number;//主订单编号
	private int business_amount;//交易金额
	private int ck_id;//储值卡种id
	private int svc_id;//储值卡id
	private String svc_number;//储值卡号
	private String status;//状态0未支付 1已支付
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
	private String mch_name;//商户名称
	private int pageSize;
	private int pageNo;
	private String beginTime;
	private String endTime;
	private String detailDate;//查询详情是的日期参数
	
	
	public String getDetailDate() {
		return detailDate;
	}
	public void setDetailDate(String detailDate) {
		this.detailDate = detailDate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public int getBusiness_amount() {
		return business_amount;
	}
	public void setBusiness_amount(int business_amount) {
		this.business_amount = business_amount;
	}
	public int getCk_id() {
		return ck_id;
	}
	public void setCk_id(int ck_id) {
		this.ck_id = ck_id;
	}
	public int getSvc_id() {
		return svc_id;
	}
	public void setSvc_id(int svc_id) {
		this.svc_id = svc_id;
	}
	public String getSvc_number() {
		return svc_number;
	}
	public void setSvc_number(String svc_number) {
		this.svc_number = svc_number;
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
	public String getMch_name() {
		return mch_name;
	}
	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}
	
}
