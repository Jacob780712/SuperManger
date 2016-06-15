package cn.bc.mchant.po;

import java.util.Date;
/*
 * 商户账号管理
 */
public class MchAccountNumber {
	private int id;//id
	private String mch_number;//商户编码
	private String account_number;//账号
	private String account_pass;//密码
	private String authority;//权限 0全部 1交易验证
	private String status;//状态0开通 1关闭 2未改密
	private String remark;//备注
	private Date create_date;//创建日期
	private String create_person;//创建人
	private Date update_date;//修改日期
	private String update_person;//修改人
	private Integer branch_id;//门店id
	private String source;//来源0商户 1分店
	
	
	public Integer getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
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
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getAccount_pass() {
		return account_pass;
	}
	public void setAccount_pass(String account_pass) {
		this.account_pass = account_pass;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
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
