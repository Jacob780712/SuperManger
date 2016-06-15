package cn.bc.mchant.po;

import java.util.Date;

public class MchBackAllot {
	private int id;//id
	private String mch_number;//商户编号
	private String a_allot;//用户A分配(单位百分比) 
	private String b_allot;//用户B分配(单位百分比)
	private String o_allot;//公司O分配(单位百分比)
	private String status;//状态0正常 1失效 9删除
	private Date create_date;//创建日期
	private String create_person;//创建人
	private Date update_date;//修改日期
	private String update_person;//修改人
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
	public String getA_allot() {
		return a_allot;
	}
	public void setA_allot(String a_allot) {
		this.a_allot = a_allot;
	}
	public String getB_allot() {
		return b_allot;
	}
	public void setB_allot(String b_allot) {
		this.b_allot = b_allot;
	}
	public String getO_allot() {
		return o_allot;
	}
	public void setO_allot(String o_allot) {
		this.o_allot = o_allot;
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
