package cn.bc.mchant.vo;

import java.util.Date;

public class MchantFilter {
	private String id;
	private String status;
	private String mchantNo;
	private String mchantName;
	private String moblie;
	private String citys;
	private Date createDate;
	private String createPerson;
	private Date updateDate;
	private String updatePerson;
	private int pageSize;
	private int pageNo;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMchantNo() {
		return mchantNo;
	}
	public void setMchantNo(String mchantNo) {
		this.mchantNo = mchantNo;
	}
	public String getMchantName() {
		return mchantName;
	}
	public void setMchantName(String mchantName) {
		this.mchantName = mchantName;
	}
	public String getMoblie() {
		return moblie;
	}
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	public String getCitys() {
		return citys;
	}
	public void setCitys(String citys) {
		this.citys = citys;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdatePerson() {
		return updatePerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
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
	
	
}
