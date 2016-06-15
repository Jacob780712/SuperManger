package cn.bc.settlement.po;

import java.util.Date;
/*
 * 商户每日购卡统计
 */
public class StmMerchant {
	private int id;//id
	private Date date;//日期
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private String sale_card_number;//售卡数量
	private int sale_card_amount;//售卡金额
	private int sale_card_fee;//售卡手续费
	private int settlement_amount;//结算金额
	private String batch_number;//批次号
	private String type;//0为商户开通购卡（不再参与其他结算），1为其他购卡
	private String status;//状态(是否参与过结算计算，0已参与 1未参与）
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getSale_card_number() {
		return sale_card_number;
	}
	public void setSale_card_number(String sale_card_number) {
		this.sale_card_number = sale_card_number;
	}
	public int getSale_card_amount() {
		return sale_card_amount;
	}
	public void setSale_card_amount(int sale_card_amount) {
		this.sale_card_amount = sale_card_amount;
	}
	public int getSale_card_fee() {
		return sale_card_fee;
	}
	public void setSale_card_fee(int sale_card_fee) {
		this.sale_card_fee = sale_card_fee;
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
