package cn.bc.settlement.services;

import java.util.List;

import cn.bc.settlement.po.PaymentInfo;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.CoAccountBill;
import cn.bc.settlement.po.StmMchDetail;
import cn.bc.settlement.po.StmMerchant;
import cn.bc.settlement.vo.StmMchDetailFilter;
import cn.bc.settlement.vo.StmWechatFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface SettlementService {
	
	//查询微信结算列表
	public Page StmWechatList(StmWechatFilter filter);
	//微信结算
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
	//根据商户查询筛选器中的数据导出批量付款Excel表格。
	public HSSFWorkbook createSettlementPayExcel(StmMchDetailFilter filter, PaymentInfo paymentInfo);
}
