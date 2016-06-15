package cn.bc.query.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bc.business.po.OnUser;
import cn.bc.common.support.page.Page;
import cn.bc.query.bo.PlatFormRE;
import cn.bc.query.bo.PlatShuoru;
import cn.bc.query.dao.QueryDao;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.CoAccountBill;
import cn.bc.query.po.CoDepositCard;
import cn.bc.query.po.OnBankCard;
import cn.bc.query.po.OnDepositCard;
import cn.bc.query.po.OnSvcSpendBill;
import cn.bc.query.po.OrderPayBill;
import cn.bc.query.po.PayCardCount;
import cn.bc.query.services.QueryServices;
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

public class QueryServicesImpl implements QueryServices{
	private QueryDao queryDao;
	
	public void setQueryDao(QueryDao queryDao) {
		this.queryDao = queryDao;
	}

	@Override
	public Page listUser(UserFilter filter) {
		Page page = queryDao.listUser(filter);
		List<OnUser> list = (List<OnUser>) page.getResult();
		for(OnUser user : list){
			user.setQudao(queryDao.getQudao(user.getOpen_id()));
		}
		return page;
	}

	@Override
	public List<OnUser> tjUser(UserFilter filter){
		
		return queryDao.tjUser(filter);
	}
	@Override
	public void update() {
		
	}

	@Override
	public void save() {
		
	}

	@Override
	public List<OnBankCard> listBankCard(String userId) {
		return queryDao.listBankCard(userId);
	}

	@Override
	public Page listDepositCard(DepositCardFilter filter) {
		return queryDao.listDepositCard(filter);
	}
	
	@Override
	public Page listCoDepositCard(DepositCardFilter filter) {
		return queryDao.listCoDepositCard(filter);
	}

	@Override
	public Page listSvcSpendBill(SvcSpendBillFilter filter) {
		return queryDao.listSvcSpendBill(filter);
	}

	@Override
	public Page listOrderPayCard(OrderSubPayCardFilter filter) {
		return queryDao.listOrderPayCard(filter);
	}

	@Override
	public Page listApayBill(OrderPayBillFilter filter) {
		return queryDao.listApayBill(filter);
	}

	@Override
	public Page listBpayBill(OrderPayBillFilter filter) throws Exception{
		return queryDao.listBpayBill(filter);
	}
	@Override
	public List<OnSvcSpendBill> DetailBpayBill(String orderNumber,String mobile){
		return queryDao.DetailBpayBill(orderNumber,mobile);
	}

	@Override
	public Page listCoAccountBill(CoAccountBillFilter filter) {
		return queryDao.listCoAccountBill(filter);
	}

	@Override
	public CoAccount CoAccountBillTotal() {
		return queryDao.CoAccountBillTotal();
	}

	@Override
	public Page listStmMerchant(StmMerchantFilter filter) {
		return queryDao.listStmMerchant(filter);
	}

	@Override
	public Page listPlatform(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		return queryDao.listPlatform(filter);
	}

	@Override
	public Page listCoAccountBill(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		return queryDao.listCoAccountBill(filter);
	}

	@Override
	public void addCoAccountBlance(int amount,String name) throws Exception {
		// TODO Auto-generated method stub
		queryDao.addCoAccountBlance(amount,name);
	}

	@Override
	public CoAccount CoAccountDetail() {
		// TODO Auto-generated method stub
		return queryDao.CoAccountDetail();
	}

	@Override
	public List<Object> listUserBalance(UserFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.listUserBalance(filter);
	}

	@Override
	public List<OnDepositCard> DepositCard(DepositCardFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.DepositCard(filter);
	}

	@Override
	public List<OnSvcSpendBill> OnSvcSpendBillList(SvcSpendBillFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.OnSvcSpendBillList(filter);
	}

	@Override
	public List<PayCardCount> listTotle(OrderSubPayCardFilter filter) {
		// TODO Auto-generated method stub
		List<Object> list = queryDao.listTotle(filter);
		List<PayCardCount> reList = new ArrayList<PayCardCount>();
		for(int i=0;i<list.size();i++){
			Object[] ary = (Object[]) list.get(i);
			PayCardCount pay = new PayCardCount();
			pay.setDate((String) ary[0]);
			pay.setAllCount(((BigInteger)ary[1]).longValue());
			pay.setAmout(((BigDecimal)ary[2]).longValue());
			pay.setAllck_ck_quota(((BigDecimal)ary[3]).longValue());
			reList.add(pay);
		}
		return reList;
	}

	@Override
	public List<CoAccountBill> listCoAccount(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		return queryDao.listCoAccount(filter);
	}

	@Override
	public List<PlatFormRE> platFormTotle(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		return queryDao.platFormTotle(filter);
	}

	@Override
	public List<CoDepositCard> coDepositCard(DepositCardFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.coDepositCard(filter);
	}

	@Override
	public Page listCoAccountCz(Filter filter) {
		// TODO Auto-generated method stub
		return queryDao.listCoAccountCz(filter);
	}

	@Override
	public List<CoAccountBill> CoAccountCz(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		return queryDao.CoAccountCz(filter);
	}

	@Override
	public Page userPayInfo(Filter filter, String user_id) {
		// TODO Auto-generated method stub
		return queryDao.userPayInfo(filter, user_id);
	}

	@Override
	public Page transData(TransDataFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.transData(filter);
	}
	
	@Override
	public List<TransDataBo> transDataList(TransDataFilter filter){
		return queryDao.transDataList(filter);
	}
	@Override
	public Page userBuyCard(TransDataFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.userBuyCard(filter);
	}

	@Override
	public List<TransDataBo> userBuyCardList(TransDataFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.userBuyCardList(filter);
	}
	
	@Override
	public Page gsBuyCard(TransDataFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.gsBuyCard(filter);
	}

	@Override
	public List<TransDataBo> gsBuyCardList(TransDataFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.gsBuyCardList(filter);
	}

	@Override
	public Page userTrans(TransDataFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.userTrans(filter);
	}

	@Override
	public List<TransDataBo> userTransList(TransDataFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.userTransList(filter);
	}

	@Override
	public List<OrderPayBill> listpayBill(OrderPayBillFilter filter)
			throws Exception {
		// TODO Auto-generated method stub
		return queryDao.listpayBill(filter);
	}

	@Override
	public Page transactionDetail(OrderPayBillFilter filter) {
		// TODO Auto-generated method stub
		Page page = queryDao.transactionDetail(filter);
		List<TransactionDetail> list = (List<TransactionDetail>) page.getResult();
		for(int i=0;i<list.size();i++){
			TransactionDetail td = list.get(i);
			if(queryDao.isMember(td.getUser_id(),td.getMch_number())){
				td.setUserType("1");
			}else{
				td.setUserType("0");
			}
			Map<String,Integer> map = queryDao.getSpendAmout(td.getOrder_number());
			td.setSpendAmount(map.get("spendAmount"));
			td.setFanxian(map.get("userReturn"));
		}
		page.setData(list);
		return page;
	}

	@Override
	public Page prepaidCardDetails(DepositCardFilter filte) {
		// TODO Auto-generated method stub
		Page page = queryDao.prepaidCardDetails(filte);
		List<PrepaidCardDetails> list = (List<PrepaidCardDetails>) page.getResult();
		for(PrepaidCardDetails ls : list){
			Map<String,Object> map = queryDao.getBranchAndCount(ls.getId(),ls.getAccoutsId());
			ls.setBranch_name(map.get("branchName").toString());
			ls.setZhangshu((Integer)map.get("count"));
		}
		return page;
	}

	@Override
	public Page cardUserDetail(Filter filter){
		// TODO Auto-generated method stub
		return queryDao.cardUserDetail(filter);
	}

	@Override
	public String cardBalance() {
		// TODO Auto-generated method stub
		return queryDao.cardBalance();
	}

	@Override
	public Page listCoAccountDetail(Filter filter) {
		// TODO Auto-generated method stub
		return queryDao.listCoAccountDetail(filter);
	}

	@Override
	public List<TransactionDetail> transactionDetailExport(
			OrderPayBillFilter filter) {
		// TODO Auto-generated method stub
		List<TransactionDetail> list = queryDao.transactionDetailExport(filter);
		return list;
	}

	@Override
	public List<PrepaidCardDetails> prepaidCardDetailsExport(
			DepositCardFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.prepaidCardDetailsExport(filter);
	}

	@Override
	public List<PlatFormRE> listPlatformExport(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		return queryDao.listPlatformExport(filter);
	}

	@Override
	public Page platShouru(Filter filter) {
		// TODO Auto-generated method stub
		return queryDao.platShouru(filter);
	}

	@Override
	public List<PlatShuoru> platShouruList(Filter filter) {
		// TODO Auto-generated method stub
		return queryDao.platShouruList(filter);
	}

	@Override
	public TransDataBo getData(TransDataFilter filter) {
		// TODO Auto-generated method stub
		return queryDao.getData(filter);
	}


	
}
