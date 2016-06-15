package cn.bc.query.po;

import java.util.Date;
import java.util.List;

public class OrderPayBill {
	private int id;//id
	private String order_number;//订单编号
	private int user_id;//用户id
	private String mobile_phone;//手机号
	private String order_type;//交易类型0A用户 1B用户
	private int business_amount;//交易金额
	private String mch_number;//商户编号
	private String mch_name;//商户名称
	private Integer svc_pay_amount;//储值卡支付金额
	private Integer account_pay_amount;//账户余额支付金额
	private Integer pay_amount;//应付金额
	private String pay_source;//支付渠道0账户 1微信 2支付宝
	private String status;//状态0未支付 1已支付 2已失效
	private Integer o_return_amount;//平台返现总额
	private String remark;//备注
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
	private List<OnSvcSpendBill> cardList;//使用的储值卡集合
	private int listSize;//消费卡的数量
	private Long discontA;//B类消费A用户返现总额
	private Long discontB;//B类消费B用户优惠总额
	private Long discontO;//B类消费A用户返现总额
	private Long ownCardAmount;//扣除本人储值卡金额
	private Long otherCardAmount;//扣除他人储值卡金额
	private String firstFlag;//是否为首次交易 1是,0否
	private Integer branch_id;//门店id
	private String branchName;//门店名称
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Integer getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}
	public String getFirstFlag() {
		return firstFlag;
	}
	public void setFirstFlag(String firstFlag) {
		this.firstFlag = firstFlag;
	}
	public Long getOwnCardAmount() {
		return ownCardAmount;
	}
	public void setOwnCardAmount(Long ownCardAmount) {
		this.ownCardAmount = ownCardAmount;
	}
	public Long getOtherCardAmount() {
		return otherCardAmount;
	}
	public void setOtherCardAmount(Long otherCardAmount) {
		this.otherCardAmount = otherCardAmount;
	}
	public Integer getO_return_amount() {
		return o_return_amount;
	}
	public void setO_return_amount(Integer o_return_amount) {
		this.o_return_amount = o_return_amount;
	}
	public String getPay_source() {
		return pay_source;
	}
	public void setPay_source(String pay_source) {
		this.pay_source = pay_source;
	}
	public Long getDiscontA() {
		return discontA;
	}
	public void setDiscontA(Long discontA) {
		this.discontA = discontA;
	}
	public Long getDiscontB() {
		return discontB;
	}
	public void setDiscontB(Long discontB) {
		this.discontB = discontB;
	}
	public Long getDiscontO() {
		return discontO;
	}
	public void setDiscontO(Long discontO) {
		this.discontO = discontO;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	public List<OnSvcSpendBill> getCardList() {
		return cardList;
	}
	public void setCardList(List<OnSvcSpendBill> cardList) {
		this.cardList = cardList;
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
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public int getBusiness_amount() {
		return business_amount;
	}
	public void setBusiness_amount(int business_amount) {
		this.business_amount = business_amount;
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
	public Integer getSvc_pay_amount() {
		return svc_pay_amount;
	}
	public void setSvc_pay_amount(Integer svc_pay_amount) {
		this.svc_pay_amount = svc_pay_amount;
	}
	public Integer getAccount_pay_amount() {
		return account_pay_amount;
	}
	public void setAccount_pay_amount(Integer account_pay_amount) {
		this.account_pay_amount = account_pay_amount;
	}
	public Integer getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(Integer pay_amount) {
		this.pay_amount = pay_amount;
	}
	
}
