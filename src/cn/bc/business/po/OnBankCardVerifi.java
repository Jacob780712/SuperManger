package cn.bc.business.po;

import java.util.Date;

public class OnBankCardVerifi {
	private int id;//id
	private String status;//状态  1打款成功 0打款失败
	private String cardNumber;//银行卡号
	private Date create_date;//创建日期
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
}
