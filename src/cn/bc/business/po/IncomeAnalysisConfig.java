package cn.bc.business.po;

import java.util.Date;
/*
 * 储值收益分析列表
 */
public class IncomeAnalysisConfig {
	private int id;
	private int ranges;//计算范围:如 7 统计7天内的交易，用以计算日单量和客单价
	private Date create_date;//创建时间
	private String create_person;//创建人
	private Date update_date;//更新时间
	private String update_person;//更新人
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getRanges() {
		return ranges;
	}
	public void setRanges(int ranges) {
		this.ranges = ranges;
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
