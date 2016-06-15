package cn.bc.query.po;

public class OnAccounts {
	private int accounts_id;
	private int user_id;
	private Integer accounts_balance;
	
	
	public Integer getAccounts_balance() {
		return accounts_balance;
	}
	public void setAccounts_balance(Integer accounts_balance) {
		this.accounts_balance = accounts_balance;
	}
	public int getAccounts_id() {
		return accounts_id;
	}
	public void setAccounts_id(int accounts_id) {
		this.accounts_id = accounts_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}
