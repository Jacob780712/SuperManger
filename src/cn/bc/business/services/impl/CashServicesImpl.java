package cn.bc.business.services.impl;

import java.util.Date;
import java.util.List;

import cn.bc.business.dao.CashDao;
import cn.bc.business.po.OnBankCardVerifi;
import cn.bc.business.po.OnCashOuts;
import cn.bc.business.po.OnUser;
import cn.bc.business.services.CashServices;
import cn.bc.business.vo.OnCashOutsFilter;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.OnBankCard;

public class CashServicesImpl implements CashServices{
	private CashDao cashDao;
	 
	public void setCashDao(CashDao cashDao) {
		this.cashDao = cashDao;
	}

	@Override
	public Page list(OnCashOutsFilter filter) {
		return cashDao.list(filter);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void save() {
		
	}

	@Override
	public void reject(OnCashOuts onCashOuts) {
		cashDao.reject(onCashOuts);
	}

	@Override
	public List<OnCashOuts> search(OnCashOutsFilter filter) {
		return cashDao.search(filter);
	}
	
	@Override
	public void saveImportInfo(List<OnCashOuts> list,Date date,String updatePerson) throws Exception {
		for(int i=0;i<list.size();i++){
			OnCashOuts onCashOuts = list.get(i);
			OnCashOuts onCashOutsOld = cashDao.loadCstUserCashOut(onCashOuts);
			if(onCashOutsOld==null){
				throw new Exception("导入数据中，有一条或者多条数据与体系申请数据不匹配，请检查后重试");
			}
		}
		for(int i=0;i<list.size();i++){
			OnCashOuts onCashOuts = list.get(i);
			OnCashOuts onCashOutsOld = cashDao.loadCstUserCashOut(onCashOuts);
			onCashOutsOld.setStatus(onCashOuts.getStatus());
			onCashOutsOld.setRemark(onCashOuts.getRemark());
			onCashOutsOld.setUpdate_date(date);
			onCashOutsOld.setUpdate_person(updatePerson);
			cashDao.update(onCashOutsOld);
		}
	}

	@Override
	public List<OnCashOuts> importResult(String batch_number) {
		return cashDao.importResult(batch_number);
	}

	@Override
	public Page cashOutStatis(OnCashOutsFilter filter) throws Exception{
		return cashDao.cashOutStatis(filter);
	}

	@Override
	public Page listBankCard(OnCashOutsFilter filter) {
		// TODO Auto-generated method stub
		return cashDao.listBankCard(filter);
	}

	@Override
	public void reject(String cardNumber) {
		// TODO Auto-generated method stub
		cashDao.reject(cardNumber);
	}

	@Override
	public List<OnBankCard> exportBankCard() {
		// TODO Auto-generated method stub
		return cashDao.exportBankCard();
	}

	@Override
	public void setStatus(OnBankCard card) {
		// TODO Auto-generated method stub
		cashDao.setStatus(card);
	}

	@Override
	public void setVerifiResult(OnBankCardVerifi onBankCardVerifi) {
		// TODO Auto-generated method stub
		cashDao.setVerifiResult(onBankCardVerifi);
	}

	@Override
	public OnBankCard getUser(String cardNumber) {
		// TODO Auto-generated method stub
		return cashDao.getUser(cardNumber);
	}

	@Override
	public boolean checkUserInfo(OnUser user) {
		// TODO Auto-generated method stub
		return cashDao.checkUserInfo(user);
	}

	@Override
	public void setUserBankCard(OnUser user, String cardNumber) {
		// TODO Auto-generated method stub
		cashDao.setUserBankCard(user, cardNumber);
	}

	@Override
	public boolean checkCard(String cardNumber) {
		// TODO Auto-generated method stub
		return cashDao.checkCard(cardNumber);
	}

}
