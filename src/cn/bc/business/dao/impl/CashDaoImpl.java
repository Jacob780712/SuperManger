package cn.bc.business.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import cn.bc.business.dao.CashDao;
import cn.bc.business.po.CashOutStatis;
import cn.bc.business.po.OnBankCardVerifi;
import cn.bc.business.po.OnCashOuts;
import cn.bc.business.po.OnUser;
import cn.bc.business.vo.OnCashOutsFilter;
import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.OnBankCard;
import cn.bc.util.StringToDate;

public class CashDaoImpl extends BaseHibernateDao implements CashDao{
	private String format = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	@Override
	public Page list(OnCashOutsFilter filter) {
		String sql = " from OnCashOuts m ";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= ? ";
				listParamter.add(StringToDate.toDate(format,filter.getBeginTime().trim()+" 00:00:00"));
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= ? ";
				listParamter.add(StringToDate.toDate(format,filter.getEndTime().trim()+" 23:59:59"));
			}
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = ? ";
				listParamter.add(filter.getFull_name().trim());
			}
			
			if(filter.getMobilePhone()!=null&&!"".equals(filter.getMobilePhone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.user_id in (select a.user_id from OnUser a where mobile_phone=? ) ";
				listParamter.add(filter.getMobilePhone().trim());
			}
			
			if(filter.getCard_number()!=null&&!"".equals(filter.getCard_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.card_number = ? ";
				listParamter.add(filter.getCard_number().trim());
			}
			if(filter.getStatus()!=null&&!"".equals(filter.getStatus())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.status = ? ";
				listParamter.add(filter.getStatus());
			}
			if(filter.getBatch_number()!=null&&!"".equals(filter.getBatch_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.batch_number = ? ";
				listParamter.add(filter.getBatch_number().trim());
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		sql = sql+condtion+" order by create_date desc";
		return super.pagedQuery(sql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());

	}
	
	
	@Override
	public void update(OnCashOuts onCashOuts) {
		String hql = "update OnCashOuts set status='"+onCashOuts.getStatus()+"',"+
					"remark='"+onCashOuts.getRemark()+"',update_date='"+sdf.format(onCashOuts.getUpdate_date())+"',"+
					"update_person='"+onCashOuts.getUpdate_person()+"' "+
					" where card_number='"+onCashOuts.getCard_number()+"' and amount='"+onCashOuts.getAmount()+
					"' and status='0' and batch_number='"+onCashOuts.getBatch_number()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}


	@Override
	public void reject(OnCashOuts onCashOuts) {
		String hql = " update OnCashOuts set status='"+onCashOuts.getStatus()+"',remark='"+onCashOuts.getRemark()+
				"',batch_number='"+onCashOuts.getBatch_number()+
				"' where id='"+onCashOuts.getId()+
				"',update_date='"+sdf.format(onCashOuts.getUpdate_date())+"',update_person='"+onCashOuts.getUpdate_person()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}


	@Override
	public List<OnCashOuts> search(OnCashOutsFilter filter) {
		String hql = " update OnCashOuts set batch_number='"+filter.getBatch_number()+"',update_date='"+
				sdf.format(filter.getUpdate_date())+"',update_person='"+filter.getUpdate_person()+"' where status='0'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
		String findHql = " from OnCashOuts where status='0' and batch_number='"+filter.getBatch_number()+
				"' order by card_number,amount desc";
		List<OnCashOuts> list = super.find(findHql);
		return list;
	}




	@Override
	public OnCashOuts loadCstUserCashOut(OnCashOuts onCashOuts) {
		String hql = " from OnCashOuts c where ";
		
		hql = hql+ " c.card_number like ? and c.amount=? and batch_number=?";
		
		hql = hql+ " order by c.create_date desc";
		String cardNum = onCashOuts.getCard_number().trim();
		if(cardNum.indexOf("*")>0){
			cardNum = cardNum.substring(0,cardNum.indexOf("*")).trim()+"%"+
					cardNum.substring(cardNum.lastIndexOf("*")+1,cardNum.length()).trim();
		}
		List<OnCashOuts> list = this.find(hql, cardNum,onCashOuts.getAmount(),onCashOuts.getBatch_number());
		if(list!=null && list.size()>0)
		{
			return list.get(0);
		}
		return null;
		
	}


	@Override
	public List<OnCashOuts> importResult(String batch_number) {
		String hql = " from OnCashOuts c where batch_number=? ";
		hql = hql+ " order by c.create_date desc";
		return this.find(hql, batch_number);
	}


	@Override
	public void saveCoAccount(CoAccount coAccount) {


	}


	@Override
	public Page cashOutStatis(OnCashOutsFilter filter) throws Exception {
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");  
		String hql = "select left(create_date,10),count(*),sum(amount)"
				+ " from on_cash_outs  where 1=1 ";
		if(filter.getBeginTime()!=null){
			hql = hql + "and left(create_date,10)>='"+filter.getBeginTime()+"' ";
		}
		
		if(filter.getEndTime()!=null){
			hql = hql + "and left(create_date,10)<='"+filter.getEndTime()+"' ";
		}
		String hql2 = hql + " group by left(create_date,10) order by left(create_date,10)";
		List<Object> listTotle = getSession().createSQLQuery(hql2).list();
		hql = hql + " group by left(create_date,10) order by left(create_date,10) "+ " DESC " +" limit "+(filter.getPageNo()-1)+","+filter.getPageSize();//添加“desc”倒序符号
		List<Object> cashOutList = getSession().createSQLQuery(hql).list();
		List<CashOutStatis> resultList = new ArrayList<CashOutStatis>();
		for(int i=0;i<cashOutList.size();i++){
			Object[] ary = (Object[]) cashOutList.get(i);
			CashOutStatis cas = new CashOutStatis();
			cas.setDate(sdf2.parse((String) ary[0]));
			cas.setApplyCount(((BigInteger) ary[1]).intValue());
			cas.setApplyAmount(((BigDecimal) ary[2]).intValue());
			resultList.add(cas);
		}
		Page page = new Page();
		page.setData(resultList);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(listTotle.size());
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(listTotle.size()%filter.getPageSize()==0){
			totalPageCount = listTotle.size()/filter.getPageSize();
		}else{
			totalPageCount = listTotle.size()/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
	}


	@Override
	public Page listBankCard(OnCashOutsFilter filter) {
		// TODO Auto-generated method stub
		String sql = " from OnBankCard bank "
				+ "where bank.card_number not in(select cash.card_number from OnCashOuts cash)"
				+ " and bank.status='1'";
		return super.pagedQuery(sql.toString(), filter.getPageNo(), filter.getPageSize());
	}


	@Override
	public void reject(String cardNumber) {
		// TODO Auto-generated method stub
		String hql = " update OnBankCard set status='2' where card_number='"+cardNumber.trim()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}


	@Override
	public List<OnBankCard> exportBankCard() {
		// TODO Auto-generated method stub
		String sql = " from OnBankCard bank "
				+ "where bank.card_number not in(select cash.card_number from OnCashOuts cash)"
				+ " and bank.status='1'";
		return super.find(sql);
	}


	@Override
	public void setStatus(OnBankCard card) {
		// TODO Auto-generated method stub
		String hql = " update OnBankCard set status='"+card.getStatus()+"'"
				+ " where card_number='"+card.getCard_number().trim()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}


	@Override
	public void setVerifiResult(OnBankCardVerifi onBankCardVerifi) {
		// TODO Auto-generated method stub
		super.save(onBankCardVerifi);
	}


	@Override
	public OnBankCard getUser(String cardNumber) {
		// TODO Auto-generated method stub
		String hql = " from OnBankCard user where user.card_number='"+cardNumber.trim()+"'";
		List<OnBankCard> list = super.find(hql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}


	@Override
	public boolean checkUserInfo(OnUser user) {
		// TODO Auto-generated method stub
		String hql = " from OnUser user where user.full_name='"+user.getFull_name()+"'"
				+ " and user.mobile_phone='"+user.getMobile_phone()+"'"
				+ " and user.id_card='"+user.getId_card()+"'";
		List<OnUser> list = super.find(hql);
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean checkCard(String cardNumber) {
		// TODO Auto-generated method stub
		String hql = " from OnBankCard where card_number='"+cardNumber.trim()+"'";
		List<OnUser> list = super.find(hql);
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void setUserBankCard(OnUser user, String cardNumber) {
		// TODO Auto-generated method stub
		String hql = " from OnUser user where user.full_name='"+user.getFull_name()+"'"
				+ " and user.mobile_phone='"+user.getMobile_phone()+"'"
				+ " and user.id_card='"+user.getId_card()+"'";
		List<OnUser> list = super.find(hql);
		int user_id = list.get(0).getUser_id();
		OnBankCard card = new OnBankCard();
		card.setUser_id(user_id);
		card.setFull_name(user.getFull_name());
		card.setCard_number(cardNumber);
		card.setStatus("0");
		card.setRemark("客服验证成功后添加");
		card.setCreate_date(new Date());
		card.setCreate_person("system");
		super.save(card);
	}



}
