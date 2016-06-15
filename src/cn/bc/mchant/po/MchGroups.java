package cn.bc.mchant.po;

import java.util.Date;

/*
 * 商户类型
 */
public class MchGroups implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int mch_group_id;
	private String group_name;
	private String group_desc;
	private Date create_date;
	private Integer create_user_id;
	private Date last_update_date;
	private Integer update_user_id;
	public int getMch_group_id() {
		return mch_group_id;
	}
	public void setMch_group_id(int mch_group_id) {
		this.mch_group_id = mch_group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getGroup_desc() {
		return group_desc;
	}
	public void setGroup_desc(String group_desc) {
		this.group_desc = group_desc;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Integer getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(Integer create_user_id) {
		this.create_user_id = create_user_id;
	}
	public Date getLast_update_date() {
		return last_update_date;
	}
	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}
	public Integer getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}
	

}