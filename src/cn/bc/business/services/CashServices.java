package cn.bc.business.services;

import java.util.Date;
import java.util.List;

import cn.bc.business.po.OnBankCardVerifi;
import cn.bc.business.po.OnCashOuts;
import cn.bc.business.po.OnUser;
import cn.bc.business.vo.OnCashOutsFilter;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.OnBankCard;

public interface CashServices {
	//提现列表
	public Page list(OnCashOutsFilter filter);
	//人工驳回
	public void reject(OnCashOuts onCashOuts);
	//导出数据
	public List<OnCashOuts> search(OnCashOutsFilter filter);
	public void update();
	public void save();
	//根据导入结果，更改数据库提现状态
	public void saveImportInfo(List<OnCashOuts> list, Date date, String updatePerson) throws Exception;
	//显示导入结果
	public List<OnCashOuts> importResult(String batch_number);
	//查询提现统计数据
	public Page cashOutStatis(OnCashOutsFilter filter) throws Exception;
	//查询新增添加的未做过提现的银行卡列表
	public Page listBankCard(OnCashOutsFilter filter);
	//人工驳回新添加的银行卡验证
	public void reject(String cardNumber);
	//导出验证数据
	public List<OnBankCard> exportBankCard();
	//setStatus  修改银行卡状态
	public void setStatus(OnBankCard card);
	//把成功的数据放入银行卡验证结果列表中
	public void setVerifiResult(OnBankCardVerifi onBankCardVerifi);
	public OnBankCard getUser(String cardNumber);
	public boolean checkUserInfo(OnUser user);
	public boolean checkCard(String cardNumber);
	public void setUserBankCard(OnUser user, String cardNumber);
}
