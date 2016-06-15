package cn.bc.query.po;

import java.util.Date;

public class OnAccountsBill {
	private int id;//id
	private int accounts_id;//账户id
	private int amount;//金额
	private int accounts_balance;//账户余额
	private String type;//类型 0收入 1支出
	private String source_id;//渠道0提现 1提现退还 2B用户消费返金 3购卡消费 4买单消费 5购买礼遇 6退款充值 7系统扣款
	private String order_number;//订单号
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
	private String mobile;//手机号
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccounts_id() {
		return accounts_id;
	}
	public void setAccounts_id(int accounts_id) {
		this.accounts_id = accounts_id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getAccounts_balance() {
		return accounts_balance;
	}
	public void setAccounts_balance(int accounts_balance) {
		this.accounts_balance = accounts_balance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource_id() {
		return source_id;
	}
	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
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
