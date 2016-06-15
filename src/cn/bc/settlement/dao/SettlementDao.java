package cn.bc.settlement.dao;

import java.util.List;

import cn.bc.common.support.page.Page;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.CoAccountBill;
import cn.bc.settlement.po.StmMchDetail;
import cn.bc.settlement.po.StmMerchant;
import cn.bc.settlement.vo.StmMchDetailFilter;
import cn.bc.settlement.vo.StmWechatFilter;

public interface SettlementDao {
	
	public Page StmWechatList(StmWechatFilter filter);
	
	public void StmWechatUpdate(List<StmWechatFilter> filter);
	//商户结算查询
	public Page listStmMchDetail(StmMchDetailFilter filter);
	//商户结算，更改状态
	public void updateStmMchDetail(StmMchDetailFilter filter);
	//商户开通购卡，生成计算数据
	public void setOpenPuerDate(StmMerchant stm);
	//商户开通购卡，生成结算数据
	public void setStmMchDetail(StmMchDetail stm);
	//修改公司账号余额
	public void updateCoAccountBill(CoAccountBill coBill);
	//修改公司账号余额
	public void updateCoAccount(CoAccount co);
	public List stmMchDetailList(StmMchDetailFilter filter);
}
