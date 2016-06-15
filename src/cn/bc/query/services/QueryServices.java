package cn.bc.query.services;

import java.util.List;
import java.util.Map;

import cn.bc.business.po.OnUser;
import cn.bc.common.support.page.Page;
import cn.bc.query.bo.PlatFormRE;
import cn.bc.query.bo.PlatShuoru;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.CoAccountBill;
import cn.bc.query.po.CoDepositCard;
import cn.bc.query.po.OnBankCard;
import cn.bc.query.po.OnDepositCard;
import cn.bc.query.po.OnSvcSpendBill;
import cn.bc.query.po.OrderPayBill;
import cn.bc.query.po.PayCardCount;
import cn.bc.query.vo.CoAccountBillFilter;
import cn.bc.query.vo.DepositCardFilter;
import cn.bc.query.vo.Filter;
import cn.bc.query.vo.OrderPayBillFilter;
import cn.bc.query.vo.OrderSubPayCardFilter;
import cn.bc.query.vo.PrepaidCardDetails;
import cn.bc.query.vo.SvcSpendBillFilter;
import cn.bc.query.vo.TransDataBo;
import cn.bc.query.vo.TransDataFilter;
import cn.bc.query.vo.TransactionDetail;
import cn.bc.query.vo.UserFilter;
import cn.bc.settlement.vo.StmMerchantFilter;

public interface QueryServices {
	//用户列表
	public Page listUser(UserFilter filter); 
	//查询所有用户
	public List<Object> listUserBalance(UserFilter filter);
	public List<OnUser> tjUser(UserFilter filter);
	//绑卡信息
	public List<OnBankCard> listBankCard(String userId);
	public void update();
	public void save();
	//个人储值卡查询
	public Page listDepositCard(DepositCardFilter filter);
	public List<OnDepositCard> DepositCard(DepositCardFilter filter);
	//公司储值卡查询
	public Page listCoDepositCard(DepositCardFilter filter);
	public List<CoDepositCard> coDepositCard(DepositCardFilter filter);
	//储值卡交易查询
	public Page listSvcSpendBill(SvcSpendBillFilter filter);
	public List<OnSvcSpendBill> OnSvcSpendBillList(SvcSpendBillFilter filter);
	//售卡查询
	public Page listOrderPayCard(OrderSubPayCardFilter filter);
	public List<PayCardCount> listTotle(OrderSubPayCardFilter filter);
	//A类用户交易查询
	public Page listApayBill(OrderPayBillFilter filter);
	//B类用户交易查询
	public Page listBpayBill(OrderPayBillFilter filter)throws Exception;
	public List<OrderPayBill> listpayBill(OrderPayBillFilter filter)throws Exception;
	//B类用户交易详情
	public List<OnSvcSpendBill> DetailBpayBill(String orderNumber, String mobile);
	//公司收支明细
	public Page listCoAccountBill(CoAccountBillFilter filter);
	//公司收支
	public CoAccount CoAccountBillTotal();
	//商户每日购卡统计
	public Page listStmMerchant(StmMerchantFilter filter);
	//平台收支明细
	public Page listPlatform(Filter filter) throws Exception;
	public List<PlatFormRE> listPlatformExport(Filter filter) throws Exception;
	public List<PlatFormRE> platFormTotle(Filter filter) throws Exception;
	//公司账户明细
	public Page listCoAccountBill(Filter filter) throws Exception;
	public List<CoAccountBill> listCoAccount(Filter filter) throws Exception;
	//公司账户充值
	public void addCoAccountBlance(int amount, String name) throws Exception;
	//公司账户查询
	public CoAccount CoAccountDetail();
	//查询公司账户充值
	public Page listCoAccountCz(Filter filter);
	public List<CoAccountBill> CoAccountCz(Filter filter) throws Exception;
	//查询公司账户充值
	public Page userPayInfo(Filter filter, String user_id);
	//交易数据统计
	public Page transData(TransDataFilter filter);
	public List<TransDataBo> transDataList(TransDataFilter filter);
	//用户购卡统计
	public Page userBuyCard(TransDataFilter filter);
	public List<TransDataBo> userBuyCardList(TransDataFilter filter);
	//公司购卡统计
	public Page gsBuyCard(TransDataFilter filter);
	public List<TransDataBo> gsBuyCardList(TransDataFilter filter);
	//用户行为统计
	public Page userTrans(TransDataFilter filter);
	public List<TransDataBo> userTransList(TransDataFilter filter);

	/*
	 * 查询统计修改
	 */
	public Page transactionDetail(OrderPayBillFilter filter);
	public List<TransactionDetail> transactionDetailExport(OrderPayBillFilter filter);
	//储值明细
	public Page prepaidCardDetails(DepositCardFilter filte);
	public List<PrepaidCardDetails> prepaidCardDetailsExport(DepositCardFilter filter);
	//储值卡使用情况
	public Page cardUserDetail(Filter filter);
	//查询储值卡总余额（公司和个人）
	public String cardBalance();
	//公司账户收支统计详情
	public Page listCoAccountDetail(Filter filter);
	public Page platShouru(Filter filter);
	public List<PlatShuoru> platShouruList(Filter filter);
	public TransDataBo getData(TransDataFilter filter);
}
