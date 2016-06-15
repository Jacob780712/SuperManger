package cn.bc.query.bo;

public class PlatShuoru {
	//select a.date,sum(a.cz),sum(a.md),sum(a.mjs),sum(a.tx),sum(a.cd),sum(a.wx),"
//			+ "sum(a.txx),sum(a.ysh),sum(a.yyh) "
	private String date;
	private Integer cz;
	private Integer md;
	private Integer mjs;
	private Integer tx;
	private Integer cd;
	private Integer wx;
	private Integer txx;
	private Integer ysh;
	private Integer yyh;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getCz() {
		return cz;
	}
	public void setCz(Integer cz) {
		this.cz = cz;
	}
	public Integer getMd() {
		return md;
	}
	public void setMd(Integer md) {
		this.md = md;
	}
	public Integer getMjs() {
		return mjs;
	}
	public void setMjs(Integer mjs) {
		this.mjs = mjs;
	}
	public Integer getTx() {
		return tx;
	}
	public void setTx(Integer tx) {
		this.tx = tx;
	}
	public Integer getCd() {
		return cd;
	}
	public void setCd(Integer cd) {
		this.cd = cd;
	}
	public Integer getWx() {
		return wx;
	}
	public void setWx(Integer wx) {
		this.wx = wx;
	}
	public Integer getTxx() {
		return txx;
	}
	public void setTxx(Integer txx) {
		this.txx = txx;
	}
	public Integer getYsh() {
		return ysh;
	}
	public void setYsh(Integer ysh) {
		this.ysh = ysh;
	}
	public Integer getYyh() {
		return yyh;
	}
	public void setYyh(Integer yyh) {
		this.yyh = yyh;
	}
	
}
