package cn.bc.query.po;

import java.math.BigDecimal;
import java.util.Date;
/*
 * 公司收支明细
 */
public class CoAccountBill {
	private int id;//id
	private String date;//日期
	private int co_id;//公司id
	private String order_number;//来源订单号
	private String buyCardMchName;//购卡的公司名称
	private String shouruMchName;//普通用户买单收入的商户名称
	private int account_balance;//账户余额
	private int amount;//金额
	private int weixin;//微信手续费
	private int tixian;//提现手续费
	private int payCard;//当做A用户购卡费用
	private int zhichu;//支出
	private int mchant;//商户结算手续费
	private int bill_B;//B类消费
	private int shouru;//收入
	private int shouru_all;//总收入
	private String type;//类型 0收入 1支出
	private String source_id;//来源 支出：0提现手续费 1微信手续费 2当做A用户购卡；收入：3商户手续费 4B类消费返现 5后台充值
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
	public int getId() {
		return id;
	}
	
	public String getBuyCardMchName() {
		return buyCardMchName;
	}

	public void setBuyCardMchName(String buyCardMchName) {
		this.buyCardMchName = buyCardMchName;
	}

	public String getShouruMchName() {
		return shouruMchName;
	}

	public void setShouruMchName(String shouruMchName) {
		this.shouruMchName = shouruMchName;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCo_id() {
		return co_id;
	}
	public void setCo_id(int co_id) {
		this.co_id = co_id;
	}
	public int getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(int account_balance) {
		this.account_balance = account_balance;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getWeixin() {
		return weixin;
	}
	public void setWeixin(int weixin) {
		this.weixin = weixin;
	}
	public int getTixian() {
		return tixian;
	}
	public void setTixian(int tixian) {
		this.tixian = tixian;
	}
	public int getPayCard() {
		return payCard;
	}
	public void setPayCard(int payCard) {
		this.payCard = payCard;
	}
	public int getZhichu() {
		return zhichu;
	}
	public void setZhichu(int zhichu) {
		this.zhichu = zhichu;
	}
	public int getMchant() {
		return mchant;
	}
	public void setMchant(int mchant) {
		this.mchant = mchant;
	}
	public int getBill_B() {
		return bill_B;
	}
	public void setBill_B(int bill_B) {
		this.bill_B = bill_B;
	}
	public int getShouru() {
		return shouru;
	}
	public void setShouru(int shouru) {
		this.shouru = shouru;
	}
	public int getShouru_all() {
		return shouru_all;
	}
	public void setShouru_all(int shouru_all) {
		this.shouru_all = shouru_all;
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