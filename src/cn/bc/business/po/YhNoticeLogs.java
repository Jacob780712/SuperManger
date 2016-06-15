package cn.bc.business.po;

import java.util.Date;

/**
 * YhNoticeLogs entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class YhNoticeLogs implements java.io.Serializable {

	// Fields

	private Integer noticeId;
	private Integer cstUserId;
	private String toAddress;
	private String noticeFlag;
	private String noticeType;
	private Date createDate;
	private Date noticeDate;
	private String noticeResultDesc;
	private String noticeContent;
	private Integer transactionId;
	private String noticeResult;
	private String deliveryStatus;
	private String deliveryDesc;
	private String messageId;

	// Constructors

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/** default constructor */
	public YhNoticeLogs() {
	}

	/** full constructor */
	public YhNoticeLogs(Integer cstUserId, String toAddress, String noticeFlag,
			String noticeType, Date createDate, Date noticeDate,
			String noticeResultDesc, String noticeContent,
			Integer transactionId, String noticeResult, String deliveryStatus,
			String deliveryDesc) {
		this.cstUserId = cstUserId;
		this.toAddress = toAddress;
		this.noticeFlag = noticeFlag;
		this.noticeType = noticeType;
		this.createDate = createDate;
		this.noticeDate = noticeDate;
		this.noticeResultDesc = noticeResultDesc;
		this.noticeContent = noticeContent;
		this.transactionId = transactionId;
		this.noticeResult = noticeResult;
		this.deliveryStatus = deliveryStatus;
		this.deliveryDesc = deliveryDesc;
	}

	// Property accessors

	public Integer getNoticeId() {
		return this.noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public Integer getCstUserId() {
		return this.cstUserId;
	}

	public void setCstUserId(Integer cstUserId) {
		this.cstUserId = cstUserId;
	}

	public String getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getNoticeFlag() {
		return this.noticeFlag;
	}

	public void setNoticeFlag(String noticeFlag) {
		this.noticeFlag = noticeFlag;
	}

	public String getNoticeType() {
		return this.noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getNoticeDate() {
		return this.noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getNoticeResultDesc() {
		return this.noticeResultDesc;
	}

	public void setNoticeResultDesc(String noticeResultDesc) {
		this.noticeResultDesc = noticeResultDesc;
	}

	public String getNoticeContent() {
		return this.noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Integer getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getNoticeResult() {
		return this.noticeResult;
	}

	public void setNoticeResult(String noticeResult) {
		this.noticeResult = noticeResult;
	}

	public String getDeliveryStatus() {
		return this.deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryDesc() {
		return this.deliveryDesc;
	}

	public void setDeliveryDesc(String deliveryDesc) {
		this.deliveryDesc = deliveryDesc;
	}

}