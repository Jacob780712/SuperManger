package cn.bc.settlement.po;

import java.util.Date;

public class StmWechat {
	private int id;//id
	private Date date;//日期 年-月-日
	private String business_pen;//交易笔数
	private int business_amount;//交易金额
	private int business_fee;//交易手续费
	private int settlement_amount;//结算金额
	private Integer aut_business_fee;//时间手续费
	private Integer aut_settlement_amount;//时间结算金额
	private String batch_number;//批次号
	private String status;//状态0已结算 1未结算
	private String remark;//备注
	private Date create_date;//时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
	
	public Integer getAut_business_fee() {
		return aut_business_fee;
	}
	public void setAut_business_fee(Integer aut_business_fee) {
		this.aut_business_fee = aut_business_fee;
	}
	public Integer getAut_settlement_amount() {
		return aut_settlement_amount;
	}
	public void setAut_settlement_amount(Integer aut_settlement_amount) {
		this.aut_settlement_amount = aut_settlement_amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getBusiness_pen() {
		return business_pen;
	}
	public void setBusiness_pen(String business_pen) {
		this.business_pen = business_pen;
	}
	public int getBusiness_amount() {
		return business_amount;
	}
	public void setBusiness_amount(int business_amount) {
		this.business_amount = business_amount;
	}
	public int getBusiness_fee() {
		return business_fee;
	}
	public void setBusiness_fee(int business_fee) {
		this.business_fee = business_fee;
	}
	public int getSettlement_amount() {
		return settlement_amount;
	}
	public void setSettlement_amount(int settlement_amount) {
		this.settlement_amount = settlement_amount;
	}
	public String getBatch_number() {
		return batch_number;
	}
	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
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
	
}
