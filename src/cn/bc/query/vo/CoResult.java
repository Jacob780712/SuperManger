package cn.bc.query.vo;

public class CoResult {
	private String date;//日期
	private String type;//类型  0收入 1支出
	private Integer bishu;//笔数
	private Integer amount;//金额
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getBishu() {
		return bishu;
	}
	public void setBishu(Integer bishu) {
		this.bishu = bishu;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}	
