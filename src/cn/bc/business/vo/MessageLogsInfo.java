package cn.bc.business.vo;

import java.util.Date;

public class MessageLogsInfo {
//号码、类型、发送时间、发送结果、内容、是否到达
	private String toAddress;
	private String noticeType;
	private Date noticeDate;
	private String noticeResult;
	private String noticeContent;
	private String deliveryStatus;
	
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getNoticeResult() {
		return noticeResult;
	}
	public void setNoticeResult(String noticeResult) {
		this.noticeResult = noticeResult;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	
	
}
