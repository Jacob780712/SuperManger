package cn.bc.business.dao;

import java.util.List;

import cn.bc.business.po.OnBankCardVerifi;
import cn.bc.business.po.OnCashOuts;
import cn.bc.business.po.OnUser;
import cn.bc.business.vo.OnCashOutsFilter;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.OnBankCard;

public interface CashDao {
	public Page list(OnCashOutsFilter filter);
	public void reject(OnCashOuts onCashOuts);
	public List<OnCashOuts> search(OnCashOutsFilter filter);
	public void update(OnCashOuts onCashOuts);
	public List<OnCashOuts> importResult(String batch_number);
	public OnCashOuts loadCstUserCashOut(OnCashOuts onCashOuts);
	public void saveCoAccount(CoAccount coAccount);
	public Page cashOutStatis(OnCashOutsFilter filter) throws Exception;
	public Page listBankCard(OnCashOutsFilter filter);
	public void reject(String cardNumber);
	public List<OnBankCard> exportBankCard();
	public void setStatus(OnBankCard card);
	public void setVerifiResult(OnBankCardVerifi onBankCardVerifi);
	public OnBankCard getUser(String cardNumber);
	public boolean checkUserInfo(OnUser user);
	public boolean checkCard(String cardNumber);
	public void setUserBankCard(OnUser user, String cardNumber);
}
