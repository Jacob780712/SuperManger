package cn.bc.query.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;




















import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.bc.business.po.OnUser;
import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.common.support.page.Page;
import cn.bc.mchant.po.MchMerchant;
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
import cn.bc.query.vo.CoAccountBillFilter;
import cn.bc.query.vo.CoResult;
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
import cn.bc.util.StringToDate;

public class QueryDaoImpl extends BaseHibernateDao implements QueryDao{
	private String format = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat mdf=new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public Page listUser(UserFilter filter) {
		String sql = " select "
				+ " m.full_name as full_name,m.mobile_phone as mobile_phone,"
				+ " accounts.accounts_balance as accounts_balance,"
				+ " card.account as account,"
				+ " bill.md as md," 
				+ " m.create_date as create_date," 
				+ " m.open_id as open_id," 
				+ " m.user_id as user_id" 
				+ " from on_users m "
				+ " left join on_accounts accounts on m.user_id=accounts.user_id "
				+ " left join ("
				+ "	select count(*) as account,accounts_id as accounts_id from on_deposit_card group by accounts_id"
				+ ") card on card.accounts_id= accounts.accounts_id "
				+ " left join("
				+ "	select count(*) as md,user_id as user_id from order_pay_bill where status='1' group by user_id"
				+ ")bill on bill.user_id=m.user_id ";
		String condtion = "";
		if(filter!=null){
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = '"+filter.getFull_name().trim()+"' ";
			}
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = '"+filter.getMobile_phone().trim()+"' ";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"' ";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"' ";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		if(filter.getOrderFlag()!=null&&!"".equals(filter.getOrderFlag())){
			if("0".equals(filter.getOrderFlag())){
				sql = sql+condtion+" order by m.create_date desc";
			}
			if("00".equals(filter.getOrderFlag())){
				sql = sql+condtion+" order by m.create_date";
			}
			if("1".equals(filter.getOrderFlag())){
				sql = sql+condtion+" order by card.account desc , m.create_date desc";
			}
			if("11".equals(filter.getOrderFlag())){
				sql = sql+condtion+" order by card.account , m.create_date desc";
			}
			if("2".equals(filter.getOrderFlag())){
				sql = sql+condtion+" order by bill.md desc, m.create_date desc";
			}
			if("22".equals(filter.getOrderFlag())){
				sql = sql+condtion+" order by bill.md, m.create_date desc";
			}
			if("3".equals(filter.getOrderFlag())){
				sql = sql+condtion+" order by accounts.accounts_balance desc, m.create_date desc";
			}
			if("33".equals(filter.getOrderFlag())){
				sql = sql+condtion+" order by accounts.accounts_balance, m.create_date desc";
			}
		}else{
			sql = sql+condtion+" order by m.create_date desc";
		}
		
		sql = sql+" limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List<Object> list = getSession().createSQLQuery(sql).list();
		List<OnUser> resultList = new ArrayList<OnUser>();
		for(int i=0;i<list.size();i++){
			OnUser user = new OnUser();
			Object[] ary = (Object[]) list.get(i);
			user.setFull_name((String)ary[0]);
			user.setMobile_phone((String)ary[1]);
			user.setAccounts_balance((Integer)ary[2]);
			if(ary[3]!=null)
				user.setCardCount(((BigInteger)ary[3]).intValue());
			if(ary[4]!=null)
				user.setPayCount(((BigInteger)ary[4]).intValue());
			try {
				user.setCreate_date(sdf.parse((String) ary[5].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setOpen_id((String)ary[6]);
			user.setUser_id((Integer)ary[7]);
			resultList.add(user);
		}
		String totleSql = " select count(*)"
				+ " from on_users m "
				+ " left join on_accounts accounts on m.user_id=accounts.user_id "
				+ " left join ("
				+ "	select count(*) as account,accounts_id as accounts_id from on_deposit_card group by accounts_id"
				+ ") card on card.accounts_id= accounts.accounts_id "
				+ " left join("
				+ "	select count(*) as md,user_id as user_id from order_pay_bill where status='1' group by user_id"
				+ ")bill on bill.user_id=m.user_id ";
		String condtions = "";
		if(filter!=null){
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtions.length() > 0)
					condtions = condtions + " and ";
				condtions = condtions + " m.full_name = '"+filter.getFull_name().trim()+"' ";
			}
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtions.length() > 0)
					condtions = condtions + " and ";
				condtions = condtions + " m.mobile_phone = '"+filter.getMobile_phone().trim()+"' ";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtions.length() > 0)
					condtions = condtions + " and ";
				condtions = condtions + " m.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"' ";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtions.length() > 0)
					condtions = condtions + " and ";
				condtions = condtions + " m.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"' ";
			}
		}
		if (condtions.length() > 0)
			condtions = " where " + condtions;
		totleSql = totleSql+condtions;
		List<Object> totleList = getSession().createSQLQuery(totleSql).list();
		int size = ((BigInteger)totleList.get(0)).intValue();
		Page page = new Page();
		page.setData(resultList);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(size);
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(size%filter.getPageSize()==0){
			totalPageCount = size/filter.getPageSize();
		}else{
			totalPageCount = size/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
	}

	
	@Override
	public List<OnUser> tjUser(UserFilter filter) {
		String hql = " from OnUser m";
		String condtion = "";
		if(filter!=null){
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = '"+filter.getFull_name().trim()+"'";
			}
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = '"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= '"+filter.getEndTime().trim()+" 23:59:59'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		if(filter.getOrderFlag()!=null&&!"".equals(filter.getOrderFlag())){
			if("0".equals(filter.getOrderFlag())){
				hql = hql+condtion+" order by m.create_date desc";
			}
			if("1".equals(filter.getOrderFlag())){
				hql = hql+condtion+" order by m.cardCount desc";
			}
			if("2".equals(filter.getOrderFlag())){
				hql = hql+condtion+" order by m.payCount desc";
			}
		}else{
			hql = hql+condtion+" order by m.create_date desc";
		}
		List<OnUser> list = super.find(hql);
		return list;
		
//		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}

	
	
	@Override
	public void update() {
		
	}

	@Override
	public void save() {
		
	}

	@Override
	public List<OnBankCard> listBankCard(String userId) {
		String hql = " from OnBankCard m where m.user_id='"+userId+"'";
		List<OnBankCard> list = super.find(hql);
		return list;
	}

	@Override
	public Page listDepositCard(DepositCardFilter filter) {
		String hql = " from OnDepositCard m";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = ? ";
				listParamter.add(filter.getMobile_phone().trim());
			}
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = ? ";
				listParamter.add(filter.getFull_name().trim());
			}
			
			if(filter.getSvc_number()!=null&&!"".equals(filter.getSvc_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.svc_number = ? ";
				listParamter.add(filter.getSvc_number().trim());
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name = ? ";
				listParamter.add(filter.getMch_name().trim());
			}
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
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by m.create_date desc";
		
		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());

	}

	@Override
	public Page listCoDepositCard(DepositCardFilter filter) {
		String hql = " from CoDepositCard m";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = ? ";
				listParamter.add(filter.getMobile_phone().trim());
			}
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = ? ";
				listParamter.add(filter.getFull_name().trim());
			}
			
			if(filter.getSvc_number()!=null&&!"".equals(filter.getSvc_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.svc_number = ? ";
				listParamter.add(filter.getSvc_number().trim());
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name = ? ";
				listParamter.add(filter.getMch_name().trim());
			}
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
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by create_date desc";
		
		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());

	}
	@Override
	public Page listSvcSpendBill(SvcSpendBillFilter filter) {
		String hql = " from OnSvcSpendBill m where m.status='1'";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = ? ";
				listParamter.add(filter.getMobile_phone().trim());
			}
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = ? ";
				listParamter.add(filter.getFull_name().trim());
			}
			
			if(filter.getSvc_number()!=null&&!"".equals(filter.getSvc_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.svc_number = ? ";
				listParamter.add(filter.getSvc_number().trim());
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name = ? ";
				listParamter.add(filter.getMch_name().trim());
			}
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
			if(filter.getType()!=null&&!"".equals(filter.getType())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.type = ? ";
				listParamter.add(filter.getType());
			}
		}
		if (condtion.length() > 0)
			condtion = " and " + condtion;
		hql = hql+condtion+" order by m.create_date desc";
		
		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}

	@Override
	public Page listOrderPayCard(OrderSubPayCardFilter filter) {
		Page page = new Page();
		if("a".equals(filter.getFlag())){
			String hql = "select date_format(c.create_date,'%Y-%m-%d') ,count(*) ,"+ 
			" sum(c.business_amount) from order_sub_pay_card c ";
			List listParamter = new ArrayList();
			String condtion = " c.status='1' ";
			if(filter!=null){
				if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
					if (condtion.length() > 0)
						condtion = condtion + " and ";
					condtion = condtion + " c.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"'";
				}
				if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
					if (condtion.length() > 0)
						condtion = condtion + " and ";
					condtion = condtion + " c.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"'";
				}
			}
			if (condtion.length() > 0)
				condtion = " where " + condtion;
			hql = hql+condtion+" group by date_format(c.create_date,'%Y-%m-%d') order by date_format(c.create_date,'%Y-%m-%d') desc";
			List<Object> listTotle = getSession().createSQLQuery(hql).list();
			String hql2 = hql+" limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
			List<Object> list = getSession().createSQLQuery(hql2).list();
			List<PayCardCount> reList = new ArrayList<PayCardCount>();
			for(int i=0;i<list.size();i++){
				Object[] ary = (Object[]) list.get(i);
				PayCardCount pay = new PayCardCount();
				pay.setDate((String) ary[0]);
				pay.setAllCount(((BigInteger)ary[1]).longValue());
				pay.setAmout(((BigDecimal)ary[2]).longValue());
				reList.add(pay);
			}
			page.setData(reList);
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
		if("b".equals(filter.getFlag())){
			
			String hql = "SELECT b.mch_name,b.mch_number,b.ck_name,b.ck_type,count(*),"
					+ "sum(a.business_amount) from order_sub_pay_card a LEFT JOIN "
					+ "mch_card_kinds b on a.ck_id=b.id ";
			String condtion = " a.status='1' ";
			if(filter!=null){
				if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
					if (condtion.length() > 0)
						condtion = condtion + " and ";
					condtion = condtion + " b.mch_name = '"+filter.getMch_name().trim()+"'";
				}
				if(filter.getDetailDate()!=null&&!"".equals(filter.getDetailDate())){
					if (condtion.length() > 0)
						condtion = condtion + " and ";
					condtion = condtion + " date_format(a.create_date, '%Y-%m-%d')= '"+filter.getDetailDate().trim()+"'";
				}
			}
			if (condtion.length() > 0)
				condtion = " where " + condtion;
			hql = hql+condtion+" GROUP BY a.ck_id order by a.create_date desc";
			List<Object> listTotle = getSession().createSQLQuery(hql).list();
			String hql2 = hql+" limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
			List<Object> list = getSession().createSQLQuery(hql2).list();
			List<PayCardCount> reList = new ArrayList<PayCardCount>();
			for(int i=0;i<list.size();i++){
				Object[] ary = (Object[]) list.get(i);
				PayCardCount pay = new PayCardCount();
				pay.setMch_name((String) ary[0]);
				pay.setMchantNo((String) ary[1]);
				pay.setCk_name((String) ary[2]);
				pay.setCk_type((String) ary[3]);
				pay.setAllCount(((BigInteger)ary[4]).longValue());
				pay.setAmout(((BigDecimal)ary[5]).longValue());
				reList.add(pay);
			}
			page.setData(reList);
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
		return page;
	}

	@Override
	public Page listApayBill(OrderPayBillFilter filter) {
		String hql = " from OrderPayBill m";
		List listParamter = new ArrayList();
		String condtion = " m.status='1' and m.order_type='0' ";
		if(filter!=null){
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name like '%"+filter.getMch_name().trim()+"%' ";
			}
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = ? ";
				listParamter.add(filter.getMobile_phone().trim());
			}
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
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by m.create_date desc";
		Page  page = super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
		List<OrderPayBill> list = new ArrayList<OrderPayBill>();
		if(page!=null){
			list = (List<OrderPayBill>) page.getResult();
		}
		for(int i=0;i<list.size();i++){
			String sql = " from OnSvcSpendBill where order_number='"+list.get(i).getOrder_number()+"'";
			List<OnSvcSpendBill>  cardList = super.find(sql);
			list.get(i).setCardList(cardList);
			list.get(i).setListSize(cardList.size());
		}
		page.setData(list);
		return page;
	}

	@Override
	public Page listBpayBill(OrderPayBillFilter filter) throws Exception {
		String hql = " from OrderPayBill m";
		List listParamter = new ArrayList();
		String condtion = " m.status='1' and m.order_type='1' ";
		if(filter!=null){
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name like '%"+filter.getMch_name().trim()+"%'";
			}
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = ? ";
				listParamter.add(filter.getMobile_phone().trim());
			}
			if(filter.getFirstFlag()!=null&&!"".equals(filter.getFirstFlag())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.firstFlag = ? ";
				listParamter.add(filter.getFirstFlag().trim());
			}
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
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by m.create_date desc";
		Page  page = super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
		List<OrderPayBill> list = new ArrayList<OrderPayBill>();
		if(page!=null){
			list = (List<OrderPayBill>) page.getResult();
		}
		
		for(int i=0;i<list.size();i++){
			String sql = "select sum(m.actual_amount) from OnSvcSpendBill m where m.order_number='"+list.get(i).getOrder_number()+"' "+
						 " and m.status='1' group by m.order_number";
			List  BorderBack = super.find(sql);
			list.get(i).setDiscontA((Long) BorderBack.get(0));
			if(list.get(i).getO_return_amount()==null){
				list.get(i).setDiscontB(Long.valueOf(list.get(i).getBusiness_amount()-list.get(i).getDiscontA()));
			}else{
				list.get(i).setDiscontB(Long.valueOf(list.get(i).getBusiness_amount()-
						list.get(i).getO_return_amount()-list.get(i).getDiscontA()));
			}
		}
		page.setData(list);
		return page;
	}

	@Override
	public List<OnSvcSpendBill> DetailBpayBill(String orderNumber,String mobile) {
		String sql = " from OnSvcSpendBill m where m.order_number='"+orderNumber+"' ";
		List<OnSvcSpendBill>  list = super.find(sql);
		for(int i=0;i<list.size();i++){
			OnSvcSpendBill osb = list.get(i);
			int svc_id = osb.getSvc_id();//储值卡id
			String hql =" select o.accounts_id from on_deposit_card o where o.id='"+svc_id+"'"
					+ " and o.accounts_id=(select oa.accounts_id from on_accounts oa "
					+ " where oa.user_id=(select user.user_id from on_users user "
					+ " where user.mobile_phone='"+mobile.trim()+"'))";
			List listUser = getSession().createSQLQuery(hql).list();
			if(listUser!=null&&listUser.size()>0){
				list.get(i).setActual_amount(0);
			}
		}
		return list;
	}

	@Override
	public Page listCoAccountBill(CoAccountBillFilter filter) {
		Page page = new Page();
		String hql = "select  b.date,sum(b.abill),sum(b.bill_b)"+
					 " from(select left(create_date,10) date,"+
					 " case a.source_id when '2' then sum(a.amount) else 0 end as abill,"+
					 " case a.source_id when '4' then sum(a.amount) else 0 end as bill_b"+
					 " from co_account_bill a  ";
		String condtion = "";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " a.create_date >='"+filter.getBeginTime().trim()+" 00:00:00"+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " a.create_date <='"+filter.getEndTime().trim()+" 23:59:59"+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		String hql2 = hql+condtion+" GROUP BY source_id ,left(create_date,10) ) b GROUP BY b.date order by b.date desc";
		List<Object> listTotle = getSession().createSQLQuery(hql2).list();
		hql = hql+condtion+" GROUP BY source_id ,left(create_date,10) ) b GROUP BY b.date order by b.date desc limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List<Object> list = getSession().createSQLQuery(hql).list();
		List<CoAccountBill> reList = new ArrayList<CoAccountBill>();
		for(int i=0;i<list.size();i++){
			Object[] ary = (Object[]) list.get(i);
			CoAccountBill bill = new CoAccountBill();
			BigDecimal d = (BigDecimal) ary[1];//A买卡
			BigDecimal a = (BigDecimal) ary[2];//B类返现
			bill.setDate((String) ary[0]);
			bill.setPayCard(d.intValue());
			bill.setBill_B(a.intValue());
			bill.setShouru_all(a.intValue()-d.intValue());
			reList.add(bill);
		}
		page.setData(reList);
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
	public CoAccount CoAccountBillTotal() {
		String  sql = " from CoAccount" ;
		List<CoAccount> list = super.find(sql);
		CoAccount bill = new CoAccount();
		if(list.size()>0){
			bill = list.get(0);
		}
		return bill;
	}

	@Override
	public Page listStmMerchant(StmMerchantFilter filter) {
		String hql = " from StmMerchant m";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getMch_number()!=null&&!"".equals(filter.getMch_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_number = ? ";
				listParamter.add(filter.getMch_number().trim());
			}
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
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by create_date desc";
		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}

	@Override
	public Page listPlatform(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		String contines = "";
		String hql = "select n.date,sum(n.weixin),sum(n.tixian),sum(n.obill),sum(n.mchantAmount),sum(n.verifi) from( "+
					" select left(a.date,10) as date,sum(a.amount) as weixin ,0 as tixian,0 as obill,0 as mchantAmount,"
					+ "0 as verifi "+ 
					" from(select left(o.create_date,10) date,sum(o.pay_amount) amount from order_pay_card o "+ 
					" where o.status='1' and o.pay_source='1' group by left(o.create_date,10) UNION "+
					" select left(b.create_date,10) date,sum(b.pay_amount) amount from order_pay_bill b "+
					" where b.status='1' and b.pay_amount>0 and b.pay_source='1' group by left(b.create_date,10) "+
					" ) a group by left(a.date,10) UNION"+
					" select left(b.update_date,10) as date,0 as weixin,count(*) as tixian,0 as obill,0 as mchantAmount,"
					+ "0 as verifi "+
					" from on_cash_outs b where b.status='1' GROUP BY left(b.update_date,10) UNION"+
					" select left(b.create_date,10) as date,0 as weixin,0 as tixian,0 as obill,0 as mchantAmount,"
					+ "count(*) as verifi "+
					" from on_bank_card_verifi b where b.status='1' GROUP BY left(b.create_date,10) UNION"+
					" select left(o.create_date,10) as date,0 as weixin,0 as tixian,sum(o.o_return_amount) as obill,0 as mchantAmount,"
					+ "0 as verifi "+
					" from order_pay_bill o where o.status='1' GROUP BY left(o.create_date,10) UNION"+
					" select  left(a.create_date,10),0 as weixin,0 as tixian,0 as obill,"+
					" sum(a.business_amount*a.sale_poundage) as mchantAmount,0 as verifi from("+
					" select s.create_date,s.business_amount,m.sale_poundage from order_sub_pay_card s"+
					" left JOIN mch_card_kinds k on k.id=s.ck_id left JOIN"+
					" mch_merchant m on m.mch_number=k.mch_number where s.status='1')a GROUP BY left(a.create_date,10))n ";
		if(filter!=null){
			
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				contines = contines + " and date>='"+filter.getBeginTime()+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				contines = contines + " and date<='"+filter.getEndTime()+"'";
			}
		}
		String hql2 = hql+"where 1=1 "+contines+" GROUP BY n.date order BY n.date desc";
		List<Object> listTotal = getSession().createSQLQuery(hql2).list();
		
		hql = hql + "where 1=1 "+contines+" GROUP BY n.date  order BY date desc limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List<Object> list = getSession().createSQLQuery(hql).list();
		List<PlatFormRE> reList = new ArrayList<PlatFormRE>();
		for(int i=0;i<list.size();i++){
			Object[] ary = (Object[]) list.get(i);
			PlatFormRE pr = new PlatFormRE();
			pr.setDate((String) ary[0]);
			int weixin =0;
			if((BigDecimal) ary[1]!=null){
				weixin = (int) (((BigDecimal) ary[1]).intValue()*0.006);//微信手续费，0.6%费率
			}
			int tixian = 0;
			if((BigDecimal)ary[2]!=null){
				tixian = ((BigDecimal)ary[2]).intValue()*80;//提现手续费，每笔8毛  80分
			}
			if((BigDecimal)ary[5]!=null){
				tixian = tixian +((BigDecimal)ary[5]).intValue()*81;//银行卡验证 每笔=1分+8毛
			}
			int obill = 0;
			if((BigDecimal)ary[3]!=null){
				obill = ((BigDecimal)ary[3]).intValue();//B类消费  平台返现
			}
			int mchantAmount = 0;
			if((Double)ary[4]!=null){
				mchantAmount = ((Double)ary[4]).intValue();//商户结算手续费
			}
			int zhichu = weixin+tixian;
			int shouru = obill+mchantAmount;
			int all = shouru-zhichu;
			pr.setWeixinAmount(weixin);
			pr.setTixianAmount(tixian);
			pr.setZhichu(zhichu);
			pr.setMachantAmount(mchantAmount);
			pr.setPlatAmount(obill);
			pr.setShouru(shouru);
			pr.setAll(all);
			reList.add(pr);
		}
		Page page = new Page();
		page.setData(reList);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(listTotal.size());
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(listTotal.size()%filter.getPageSize()==0){
			totalPageCount = listTotal.size()/filter.getPageSize();
		}else{
			totalPageCount = listTotal.size()/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
	}

	@Override
	public Page listCoAccountBill(Filter filter) throws Exception {
		String hql = "select left(co.create_date,10) as date,"
				+ "co.type,"
				+ "count(co.id),"
				+ "sum(co.amount) "
				+ " from co_account_bill co ";
		String condtion = " co.source_id<>'5' ";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >='"+filter.getBeginTime().trim()+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <='"+filter.getEndTime().trim()+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		
		hql = hql+condtion+" GROUP BY left(co.create_date,10),co.type order by date desc";
		List<Object> listTotal = getSession().createSQLQuery(hql).list();
		
		hql = hql + " limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List<Object> listPage = getSession().createSQLQuery(hql).list();
		List<CoResult> reList = new ArrayList<CoResult>();
		for(int i=0;i<listPage.size();i++){
			CoResult co = new CoResult();
			Object[] ary = (Object[]) listPage.get(i);
			co.setDate((String) ary[0]);
			co.setType((String) ary[1]);
			co.setBishu(((BigInteger)ary[2]).intValue());
			co.setAmount(((BigDecimal)ary[3]).intValue());
			reList.add(co);
		}
		Page page = new Page();
		page.setData(reList);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(listTotal.size());
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(listTotal.size()%filter.getPageSize()==0){
			totalPageCount = listTotal.size()/filter.getPageSize();
		}else{
			totalPageCount = listTotal.size()/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
	}

	@Override
	public void addCoAccountBlance(int amount,String name) throws Exception {
		// TODO Auto-generated method stub
		Session  session = getSession();
		Transaction  tx = session.beginTransaction();
		String date = sdf.format(new Date());
		String sql = " from CoAccount";
		List<CoAccount> list = super.find(sql);
		if(list!=null&&list.size()>0){
			String hql = "update CoAccount set account_balance="+(list.get(0).getAccount_balance()+amount)+
						 ",update_date='"+date+"',update_person='"+name+"' where co_id=1";
			Query query = session.createQuery(hql);
			query.executeUpdate();
			CoAccountBill coBill = new CoAccountBill();
			coBill.setCo_id(list.get(0).getCo_id());
			coBill.setAccount_balance(list.get(0).getAccount_balance()+amount);
			coBill.setAmount(amount);
			coBill.setType("0");
			coBill.setSource_id("5");
			coBill.setRemark("后台充值");
			coBill.setCreate_date(new Date());
			coBill.setCreate_person(name);
			super.save(coBill);
			tx.commit();
		}
		
	}

	@Override
	public CoAccount CoAccountDetail() {
		// TODO Auto-generated method stub
		String sql = " from CoAccount ";
		List<CoAccount> list = super.find(sql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<Object> listUserBalance(UserFilter filter) {
		// TODO Auto-generated method stub
		String hql = " select sum(o.accounts_balance) from on_accounts o left join on_users m on o.user_id=m.user_id ";
		String condtion = "";
		if(filter!=null){
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = '"+filter.getFull_name().trim()+"'";
			}
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = '"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion;
		List<Object> list = getSession().createSQLQuery(hql).list();
		return list;
	}

	@Override
	public List<OnDepositCard> DepositCard(DepositCardFilter filter) {
		// TODO Auto-generated method stub
		String hql = " from OnDepositCard m";
		String condtion = "";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = '"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = '"+filter.getFull_name().trim()+"'";
			}
			
			if(filter.getSvc_number()!=null&&!"".equals(filter.getSvc_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.svc_number = '"+filter.getSvc_number().trim()+"'";
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name = '"+filter.getMch_name().trim()+"'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by m.create_date desc";
		return super.find(hql);
	}

	@Override
	public List<OnSvcSpendBill> OnSvcSpendBillList(SvcSpendBillFilter filter) {
		String hql = " from OnSvcSpendBill m where m.status='1' and (m.type='1' or m.type='0')";
		String condtion = "";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = '"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = '"+filter.getFull_name().trim()+"'";
			}
			
			if(filter.getSvc_number()!=null&&!"".equals(filter.getSvc_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.svc_number = '"+filter.getSvc_number().trim()+"'";
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name = '"+filter.getMch_name().trim()+"'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"'";
			}
			if(filter.getType()!=null&&!"".equals(filter.getType())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.type = '"+filter.getType()+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " and " + condtion;
		hql = hql+condtion+" order by m.create_date desc";
		
		return super.find(hql);

	}

	@Override
	public List<Object> listTotle(OrderSubPayCardFilter filter) {
		// TODO Auto-generated method stub
		String hql = "select date_format(c.create_date,'%Y-%m-%d') ,count(*) ,"+ 
				" sum(c.business_amount),sum(m.ck_quota)  from order_sub_pay_card c "
				+ "left JOIN mch_card_kinds m on c.ck_id=m.id";
		List listParamter = new ArrayList();
		String condtion = " c.status='1' ";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " c.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " c.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion;
		List<Object> listTotle = getSession().createSQLQuery(hql).list();
		return listTotle;
	}

	@Override
	public List<CoAccountBill> listCoAccount(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		String hql = " from CoAccountBill m ";
		String condtion = " m.source_id<>'5' ";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion;
		return super.find(hql);
	}

	@Override
	public List<PlatFormRE> platFormTotle(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		String contines = "";
		String hql = "select n.date,sum(n.weixin),sum(n.tixian),sum(n.obill),sum(n.mchantAmount),sum(n.verifi) from( "+
					" select left(a.date,10) as date,sum(a.amount) as weixin ,0 as tixian,0 as obill,0 as mchantAmount,"
					+ "0 as verifi "+ 
					" from(select left(o.create_date,10) date,sum(o.total_principal) amount from order_pay_card o "+ 
					" where o.status='1' and o.pay_source='1' group by left(o.create_date,10) UNION "+
					" select left(b.create_date,10) date,sum(b.pay_amount) amount from order_pay_bill b "+
					" where b.status='1' and b.pay_amount>0 and b.pay_source='1' group by left(b.create_date,10) "+
					" ) a group by left(a.date,10) UNION"+
					" select left(b.update_date,10) as date,0 as weixin,count(*) as tixian,0 as obill,0 as mchantAmount,"
					+ "0 as verifi  "+
					" from on_cash_outs b where b.status='1' GROUP BY left(b.update_date,10) UNION"+
					" select left(b.create_date,10) as date,0 as weixin,0 as tixian,0 as obill,0 as mchantAmount,"
					+ "count(*) as verifi "+
					" from on_bank_card_verifi b where b.status='1' GROUP BY left(b.create_date,10) UNION"+
					" select left(o.create_date,10) as date,0 as weixin,0 as tixian,sum(o.o_return_amount) as obill,0 as mchantAmount,"
					+ "0 as verifi  "+
					" from order_pay_bill o where o.status='1' GROUP BY left(o.create_date,10) UNION"+
					" select  left(a.create_date,10),0 as weixin,0 as tixian,0 as obill,"+
					" sum(a.business_amount*a.sale_poundage) as mchantAmount,"
					+ "0 as verifi  from("+
					" select s.create_date,s.business_amount,m.sale_poundage from order_sub_pay_card s"+
					" left JOIN mch_card_kinds k on k.id=s.ck_id left JOIN"+
					" mch_merchant m on m.mch_number=k.mch_number where s.status='1')a GROUP BY left(a.create_date,10))n ";
		if(filter!=null){
			
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				contines = contines + " and date>='"+filter.getBeginTime()+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				contines = contines + " and date<='"+filter.getEndTime()+"'";
			}
		}
		String hql2 = hql+" where 1=1 "+contines+" GROUP BY n.date order BY n.date desc";
		List<Object> listTotal = getSession().createSQLQuery(hql2).list();
		
		List<PlatFormRE> reList = new ArrayList<PlatFormRE>();
		for(int i=0;i<listTotal.size();i++){
			Object[] ary = (Object[]) listTotal.get(i);
			PlatFormRE pr = new PlatFormRE();
			pr.setDate((String) ary[0]);
			int weixin = 0;
			if((BigDecimal) ary[1]!=null){
				weixin = (int) (((BigDecimal) ary[1]).intValue()*0.006);//微信手续费，0.6%费率
			}
			int tixian = 0;
			if((BigDecimal)ary[2]!=null){
				tixian = ((BigDecimal)ary[2]).intValue()*80;//提现手续费，每笔8毛  80分
			}
			if((BigDecimal)ary[5]!=null){
				tixian = tixian + ((BigDecimal)ary[5]).intValue()*81;//提现手续费，每笔8毛  80分
			}
			int obill = 0;
			if((BigDecimal)ary[3]!=null){
				obill = ((BigDecimal)ary[3]).intValue();//B类消费  平台返现
			}
			int mchantAmount = 0;
			if((Double)ary[4]!=null){
				mchantAmount = ((Double)ary[4]).intValue();//商户结算手续费
			}
			int zhichu = weixin+tixian;
			int shouru = obill+mchantAmount;
			int all = shouru-zhichu;
			pr.setWeixinAmount(weixin);
			pr.setTixianAmount(tixian);
			pr.setZhichu(zhichu);
			pr.setMachantAmount(mchantAmount);
			pr.setPlatAmount(obill);
			pr.setShouru(shouru);
			pr.setAll(all);
			reList.add(pr);
		}
		return reList;
	}

	@Override
	public List<CoDepositCard> coDepositCard(DepositCardFilter filter) {
		// TODO Auto-generated method stub
		String hql = " from CoDepositCard m";
		String condtion = "";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile_phone = '"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.full_name = '"+filter.getFull_name().trim()+"'";
			}
			
			if(filter.getSvc_number()!=null&&!"".equals(filter.getSvc_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.svc_number = '"+filter.getSvc_number().trim()+"'";
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name = '"+filter.getMch_name().trim()+"'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by create_date desc";
		return super.find(hql);
	}

	@Override
	public Page listCoAccountCz(Filter filter) {
		String hql = " from CoAccountBill m ";
		List listParamter = new ArrayList();
		String condtion = " m.source_id='5' ";
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
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by create_date desc";
		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}

	@Override
	public List<CoAccountBill> CoAccountCz(Filter filter) throws Exception {
		String hql = " from CoAccountBill m ";
		String condtion = " m.source_id='5' ";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= '"+filter.getBeginTime().trim()+" 00:00:00"+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= '"+filter.getEndTime().trim()+" 23:59:59"+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion;
		return super.find(hql);
	}

	@Override
	public Page userPayInfo(Filter filter, String user_id) {
		String hql = " from OrderPayBill m ";
		List listParamter = new ArrayList();
		String condtion = " m.status='1' and m.user_id='"+user_id+"'";
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
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by m.create_date desc";
		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}

	@Override
	public Page transData(TransDataFilter filter) {
		// TODO Auto-generated method stub
		String sql = "select left(a.date,10) as date,"
				+ "count(DISTINCT a.user_id) as renshu,"
				+" sum(a.firstFlag) as firstFlag,"
				+ "count(a.user_id) as bishu,"
				+ "sum(a.business_amount) as business_amount,"
				+ "sum(a.svc_pay_amount) as svc_pay_amount,"
				+ "sum(a.account_pay_amount) as account_pay_amount,"
				+ "sum(a.pay_amount) as pay_amount,"
				+ "sum(a.o_return_amount) as o_return_amount,"
				+ "sum(a.actual_amount) as actual_amount "
				+ " from "
				+ "(select o.create_date as date,"
				+ " m.mch_name  as mch_name,"
				+ " o.user_id as user_id,"
				+ "case when o.create_date=ss.create_date and ss.user_id=o.user_id then 1 else 0 end as firstFlag,"
				+ "o.business_amount as business_amount,"
				+ "o.svc_pay_amount as svc_pay_amount,"
				+ "o.account_pay_amount as account_pay_amount,"
				+ "o.pay_amount as pay_amount,"
				+ "o.o_return_amount as o_return_amount,"
				+ " mm.actual_amount as actual_amount "
				+ " from order_pay_bill o "
				+ " left join (select ons.order_number as order_number,"
				+ "	sum(ons.actual_amount) as actual_amount from on_svc_spend_bill ons"
				+ " where ons.type='1'"
				+ " group by ons.order_number) mm on mm.order_number=o.order_number"
				+ " left join (select op.user_id as user_id,min(op.create_date) as create_date "
				+ "from order_pay_bill op group by op.user_id)"
				+ " ss on ss.user_id=o.user_id "
				+ " left join mch_merchant m on m.mch_number=o.mch_number "
				+ " where o.status='1'"
				+ " union select cou.create_date as date,"
				+ " m.mch_name  as mch_name,"
				+ " cou.user_id as user_id,0 as firstFlag,"
				+ "cou.purchase_amount as business_amount,"
				+ "cou.svc_pay_amount as svc_pay_amount,"
				+ "cou.account_pay_amount as account_pay_amount,"
				+ "cou.pay_amount as pay_amount,cou.o_return_amount as o_return_amount,"
				+ " mm.actual_amount as actual_amount"
				+ " from order_pay_courtesy cou "
				+ " left join (select ons.order_number as order_number,"
				+ "	sum(ons.actual_amount) as actual_amount from on_svc_spend_bill ons"
				+ " where ons.type='1'"
				+ " group by ons.order_number) mm on mm.order_number=cou.order_number "
				+ " left join mch_merchant m on m.mch_number=cou.mch_number "
				+ " where cou.status='1'"
				+ ") a  where 1=1  ";
		if(filter!=null){
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				sql = sql + " and a.mch_name like '%"+filter.getMchName()+"%'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sql = sql + " and a.date>='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sql = sql + " and a.date<='"+filter.getEndTime()+" 23:59:59'";
			}
			
		}
		sql = sql + "  group by left(a.date,10) ";
		
		String hql = sql + " order BY left(a.date,10) desc limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List<Object> listPage = getSession().createSQLQuery(hql).list();
		List<TransDataBo> list = new ArrayList<TransDataBo>();
		for(int i=0;i<listPage.size();i++){
			/*
				+ "sum(a.actual_amount) as actual_amount "
			 */
			Object[] ary = (Object[]) listPage.get(i);
			TransDataBo bo = new TransDataBo();
			bo.setDate((String) ary[0]);
			if(ary[1]!=null)
			bo.setRenshu(((BigInteger)ary[1]).intValue());
			if(ary[2]!=null)
			bo.setTwomd(((BigDecimal)ary[2]).intValue());
			if(ary[3]!=null)
			bo.setBishu(((BigInteger)ary[3]).intValue());
			if(ary[4]!=null)
			bo.setBussiness_amount(((BigDecimal)ary[4]).intValue());
			if(ary[5]!=null)
			bo.setSvc_pay_amount(((BigDecimal)ary[5]).intValue());
			if(ary[6]!=null)
			bo.setAccount_pay_amount(((BigDecimal)ary[6]).intValue());
			if(ary[7]!=null)
			bo.setPay_amount(((BigDecimal)ary[7]).intValue());
			if(ary[8]!=null)
			bo.setCofanxian(((BigDecimal)ary[8]).intValue());
			if(ary[9]!=null)
			bo.setFanxian(((BigDecimal)ary[9]).intValue());
			list.add(bo);
			
		}
	
		String sqlTotle = "select count(*) "
				+ " from "
				+ "(select o.create_date as date,"
				+ " m.mch_name as mch_name,"
				+ " o.user_id as user_id,"
				+ "o.business_amount as business_amount,"
				+ "o.svc_pay_amount as svc_pay_amount,"
				+ "o.account_pay_amount as account_pay_amount,"
				+ "o.pay_amount as pay_amount,"
				+ "o.o_return_amount as o_return_amount "
				+ " from order_pay_bill o" 
				+ " left join mch_merchant m on m.mch_number=o.mch_number "
				+ " where o.status='1'"
				+ " union select cou.create_date as date,"
				+ " m.mch_name as mch_name,"
				+ " cou.user_id as user_id,"
				+ "cou.purchase_amount as business_amount,"
				+ "cou.svc_pay_amount as svc_pay_amount,"
				+ "cou.account_pay_amount as account_pay_amount,"
				+ "cou.pay_amount as pay_amount,cou.o_return_amount as o_return_amount "
				+ " from order_pay_courtesy cou "
				+ " left join mch_merchant m on m.mch_number=cou.mch_number "
				+ " where cou.status='1'"
				+ ") a  where 1=1  ";
		if(filter!=null){
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				sqlTotle = sqlTotle + " and a.mch_name like '%"+filter.getMchName()+"%'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sqlTotle = sqlTotle + " and a.date>='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sqlTotle = sqlTotle + " and a.date<='"+filter.getEndTime()+" 23:59:59'";
			}
			
		}
		sqlTotle = sqlTotle + "  group by left(a.date,10) ";
		List<Object> listTotal = getSession().createSQLQuery(sqlTotle).list();
		int size = listTotal.size();
		
		Page page = new Page();
		page.setData(list);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(size);
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(size%filter.getPageSize()==0){
			totalPageCount = size/filter.getPageSize();
		}else{
			totalPageCount = size/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
	}
	
	@Override
	public List<TransDataBo> transDataList(TransDataFilter filter) {
		
		String sql = "select "
				+ "case when b.bishu>1 then sum(1) else sum(0) end as twomd,"
				+ "sum(b.renshu) as renshu,sum(b.bishu) as bishu,"
				+ "sum(b.business_amount) as business_amount,"
				+ "sum(b.svc_pay_amount) as svc_pay_amount,"
				+ "sum(b.account_pay_amount) as account_pay_amount,"
				+ "sum(b.pay_amount) as pay_amount,"
				+ "sum(b.o_return_amount) as o_return_amount,"
				+ "sum(b.actual_amount) as actual_amount "
				+ "from("
				+ "select a.date as date,"
				+ "count(DISTINCT a.user_id) as renshu,"
				+ "count(a.user_id) as bishu,"
				+ "sum(a.business_amount) as business_amount,"
				+ "sum(a.svc_pay_amount) as svc_pay_amount,"
				+ "sum(a.account_pay_amount) as account_pay_amount,"
				+ "sum(a.pay_amount) as pay_amount,"
				+ "sum(a.o_return_amount) as o_return_amount,"
				+ "sum(a.actual_amount) as actual_amount "
				+ " from (select o.create_date as date,"
				+ " o.user_id as user_id,"
				+ "o.business_amount as business_amount,"
				+ "o.svc_pay_amount as svc_pay_amount,"
				+ "o.account_pay_amount as account_pay_amount,"
				+ "o.pay_amount as pay_amount,"
				+ "o.o_return_amount as o_return_amount,"
				+ "mm.actual_amount as actual_amount,"
				+ " m.mch_name as mch_name "
				+ " from order_pay_bill o "
				+ " left join (select ons.order_number as order_number,"
				+ " sum(ons.actual_amount) as actual_amount "
				+ " from on_svc_spend_bill ons group by ons.order_number) mm on mm.order_number=o.order_number "
				+ " left join mch_merchant m on m.mch_number=o.mch_number "
				+ " where o.status='1' "
				+ " union select cou.create_date as date,"
				+ " cou.user_id as user_id,cou.purchase_amount as business_amount,"
				+ " cou.svc_pay_amount as svc_pay_amount,"
				+ " cou.account_pay_amount as account_pay_amount,"
				+ " cou.pay_amount as pay_amount,cou.o_return_amount as o_return_amount,"
				+ " mm.actual_amount as actual_amount, "
				+ " m.mch_name as mch_name "
				+ " from order_pay_courtesy cou "
				+ " left join ( "
				+ "select ons.order_number as order_number, sum(ons.actual_amount) as actual_amount "
				+ "from on_svc_spend_bill ons group by ons.order_number)mm"
				+ " on mm.order_number=cou.order_number "
				+ " left join mch_merchant m on m.mch_number=cou.mch_number "
				+ " where cou.status='1') a  where 1=1  ";
		if(filter!=null){
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				sql = sql + " and a.mch_name like '%"+filter.getMchName()+"%'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sql = sql + " and a.date>='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sql = sql + " and a.date<='"+filter.getEndTime()+" 23:59:59'";
			}
			
		}
		sql = sql + "  group by a.user_id) b ";
		List<Object> listTotal = getSession().createSQLQuery(sql).list();
		List<TransDataBo> list = new ArrayList<TransDataBo>();
		for(int i=0;i<listTotal.size();i++){
			Object[] ary = (Object[]) listTotal.get(i);
			TransDataBo bo = new TransDataBo();
			bo.setTwomd(((BigDecimal)ary[0]).intValue());//买单2次及以上人数
			bo.setRenshu(((BigDecimal)ary[1]).intValue());//交易人数
			bo.setBishu(((BigDecimal)ary[2]).intValue());//交易笔数
			bo.setBussiness_amount(((BigDecimal)ary[3]).intValue());//交易金额
			bo.setSvc_pay_amount(((BigDecimal)ary[4]).intValue());//储值卡支付金额（本人）
			bo.setAccount_pay_amount(((BigDecimal)ary[5]).intValue());//账户支付金额
			bo.setPay_amount(((BigDecimal)ary[6]).intValue());//微信支付金额
			bo.setCofanxian(((BigDecimal)ary[7]).intValue());//平台返现金额
			bo.setFanxian(((BigDecimal)ary[8]).intValue());//交易总笔数
			list.add(bo);
		}
		
		return list;
		
		
	}
	
	
	@Override
	public Page userBuyCard(TransDataFilter filter) {
		// TODO Auto-generated method stub
		
		String sql = " select a.date,count(DISTINCT a.user_id),"
				+ "sum(a.firstFlag),sum(a.purchase_number),sum(a.svc_quota),"
				+ "sum(a.purchase_amount),sum(a.account_pay_amount),sum(a.pay_amount) "
				+ "from (select left(s.create_date,10) as date,"
				+ "case when s.user_id is null then 0 else s.user_id end as user_id,"
				+ " case when s.create_date=("
					+ "select min(pay.create_date) from order_pay_card pay "
					+ "where pay.user_id=s.user_id or pay.user_id is null) then 1 else 0 end as firstFlag,"
				+ "(select m.mch_name from mch_merchant m where m.mch_number=s.mch_number) as mch_name,"
				+ "s.purchase_number as purchase_number,"
				+ "(select sum(b.svc_quota) from order_sub_pay_card b where b.order_number=s.order_number ) as svc_quota,"
				+ "s.purchase_amount as purchase_amount,"
				+ "s.account_pay_amount as account_pay_amount,"
				+ "case when s.pay_source='0' then 0 else s.pay_amount end as pay_amount "
				+ "from order_pay_card s where s.status ='1'";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sql = sql + " and s.create_date>='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sql = sql + " and s.create_date<='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				sql = sql + " and s.mch_name like '%"+filter.getMchName().trim()+"%'";
			}
		}
		sql = sql + " ) a GROUP BY left(a.date,10) ";
		String hql = sql + " order BY a.date desc limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List<Object> listPage = getSession().createSQLQuery(hql).list();
		List<TransDataBo> list = new ArrayList<TransDataBo>();
		for(int i=0;i<listPage.size();i++){
			Object[] ary = (Object[]) listPage.get(i);
			TransDataBo bo = new TransDataBo();
			bo.setDate((String) ary[0]);
			bo.setRenshu(((BigInteger)ary[1]).intValue());
			bo.setTwomk(((BigDecimal)ary[2]).intValue());
			bo.setPurchase_number(((Double)ary[3]).intValue());
			bo.setSvc_quota(((BigDecimal)ary[4]).intValue());
			bo.setPurchase_amount(((BigDecimal)ary[5]).intValue());
			if(ary[6]!=null){
				bo.setAccount_pay_amount(((BigDecimal)ary[6]).intValue());
			}else{
				bo.setAccount_pay_amount(0);
			}
			if(ary[7]!=null){
				bo.setPay_amount(((BigDecimal)ary[7]).intValue());
			}else{
				bo.setPay_amount(0);
			}
			list.add(bo);
		}
		
		String sql2 = " select count(*) "
				+ "from (select left(s.create_date,10) as date,"
				+ "case when s.user_id is null then 0 else s.user_id end as user_id,"
				+ " case when s.create_date=("
					+ "select min(pay.create_date) from order_pay_card pay "
					+ "where pay.user_id=s.user_id or pay.user_id is null) then 1 else 0 end as firstFlag,"
				+ "(select m.mch_name from mch_merchant m where m.mch_number=s.mch_number) as mch_name,"
				+ "s.purchase_number as purchase_number,"
				+ "(select sum(b.svc_quota) from order_sub_pay_card b where b.order_number=s.order_number ) as svc_quota,"
				+ "s.purchase_amount as purchase_amount,"
				+ "s.account_pay_amount as account_pay_amount,"
				+ "case when s.pay_source='0' then 0 else s.pay_amount end as pay_amount "
				+ "from order_pay_card s where s.status ='1'";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sql2 = sql2 + " and s.create_date>='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sql2 = sql2 + " and s.create_date<='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				sql2 = sql2 + " and s.mch_name like '%"+filter.getMchName().trim()+"%'";
			}
		}
		sql2 = sql2 + " ) a GROUP BY left(a.date,10) ";
		List<Object> listTotal = getSession().createSQLQuery(sql2).list();
		BigInteger courSizeInteger = (BigInteger) (listTotal.get(0));
		int size = courSizeInteger.intValue();
		Page page = new Page();
		page.setData(list);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(size);
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(size%filter.getPageSize()==0){
			totalPageCount = size/filter.getPageSize();
		}else{
			totalPageCount = size/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
	}

	@Override
	public List<TransDataBo> userBuyCardList(TransDataFilter filter) {
		// TODO Auto-generated method stub
		String sql = "select sum(mn.renshu) as renshu,"
				+ "sum(mn.twomk) as twomk,"
				+ "sum(mn.purchase_number) as purchase_number,"
				+ "sum(mn.svc_quota) as svc_quota,"
				+ "sum(mn.purchase_amount) as purchase_amount,"
				+ "sum(mn.account_pay_amount) as account_pay_amount,"
				+ "sum(mn.pay_amount) as pay_amount "
				+ " from ("
				+ "select count(DISTINCT card.user_id) as renshu,"
				+ " case when  sum(card.purchase_number)>1 then 1 else 0 end as twomk,"
				+ "sum(card.purchase_number) as purchase_number,"
				+ "sum(card.svc_quota) as svc_quota,"
				+ "sum(card.purchase_amount) as purchase_amount,"
				+ "sum(card.account_pay_amount) as account_pay_amount,"
				+ "sum(card.pay_amount) as pay_amount  "
				+ " from"
				+ "(select case when s.user_id is null then 0 else s.user_id end as user_id,"
				+ "(select m.mch_name from mch_merchant m where m.mch_number=s.mch_number) as mch_name,"
				+ "s.purchase_number as purchase_number,"
				+ "(select sum(b.svc_quota) from order_sub_pay_card b where b.order_number=s.order_number ) as svc_quota,"
				+ "s.purchase_amount as purchase_amount,"
				+ "s.account_pay_amount as account_pay_amount,"
				+ "case when s.pay_source='0' then 0 else s.pay_amount end as pay_amount ,"
				+ "s.create_date as date "
				+ "from order_pay_card s where s.status ='1') card where 1=1 ";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sql = sql + " and card.date>='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sql = sql + " and card.date<='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				sql = sql + " and card.mch_name like '%"+filter.getMchName().trim()+"%'";
			}
		}
		sql = sql + " group by card.user_id ) mn ";
		List<Object> listTotal = getSession().createSQLQuery(sql).list();
		List<TransDataBo> list = new ArrayList<TransDataBo>();
		for(int i=0;i<listTotal.size();i++){
			Object[] ary = (Object[]) listTotal.get(i);
			TransDataBo bo = new TransDataBo();
			if(ary[0]!=null){
				bo.setRenshu(((BigDecimal)ary[0]).intValue());
			}else{
				bo.setRenshu(0);
			}
			if(ary[1]!=null){
				bo.setTwomk(((BigDecimal)ary[1]).intValue());
			}else{
				bo.setTwomk(0);
			}
			if(ary[2]!=null){
				bo.setPurchase_number(((Double)ary[2]).intValue());
			}else{
				bo.setPurchase_number(0);
			}
			if(ary[3]!=null){
				bo.setSvc_quota(((BigDecimal)ary[3]).intValue());
			}else{
				bo.setSvc_quota(0);
			}
			if(ary[4]!=null){
				bo.setPurchase_amount(((BigDecimal)ary[4]).intValue());
			}else{
				bo.setPurchase_amount(0);
			}
			if(ary[5]!=null){
				bo.setAccount_pay_amount(((BigDecimal)ary[5]).intValue());
			}else{
				bo.setAccount_pay_amount(0);
			}
			if(ary[6]!=null){
				bo.setPay_amount(((BigDecimal)ary[6]).intValue());
			}else{
				bo.setPay_amount(0);
			}
			list.add(bo);
		}
		return list;
	}
	
	@Override
	public Page gsBuyCard(TransDataFilter filter) {
		// TODO Auto-generated method stub
		
		String sql = " select left(a.create_date,10),m.mch_name,sum(a.purchase_number),sum(a.pay_amount), "
				+ " sum(b.svc_quota) from order_pay_card a left join (select c.order_number  "+
				" order_number,sum(c.svc_quota) svc_quota from order_sub_pay_card c GROUP BY  "
				+ " c.order_number) b on a.order_number = b.order_number left join  "
				+" mch_merchant m on a.mch_number=m.mch_number  where a.status='1' "
				+ "and a.user_id is null and a.pay_source!=3";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sql = sql + " and a.create_date>='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sql = sql + " and a.create_date<='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				sql = sql + " and m.mch_name like '%"+filter.getMchName().trim()+"%'";
			}
		}
		sql = sql + " GROUP BY left(a.create_date,10),a.mch_number";
		List<Object> listTotal = getSession().createSQLQuery(sql).list();
		String hql = sql + " order BY left(a.create_date,10) desc limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List<Object> listPage = getSession().createSQLQuery(hql).list();
		List<TransDataBo> list = new ArrayList<TransDataBo>();
		for(int i=0;i<listPage.size();i++){
			Object[] ary = (Object[]) listPage.get(i);
			TransDataBo bo = new TransDataBo();
			bo.setDate((String) ary[0]);
			bo.setMch_name((String) ary[1]);
			bo.setBishu(((Double)ary[2]).intValue());
			bo.setBussiness_amount(((BigDecimal)ary[3]).intValue());
			bo.setPay_amount(((BigDecimal)ary[4]).intValue());
			bo.setAllBishu(((Double)ary[2]).intValue());
			bo.setAll_bussiness_amount(((BigDecimal)ary[3]).intValue());
			bo.setAll_pay_amount(((BigDecimal)ary[4]).intValue());
			list.add(bo);
			
		}
		
		Page page = new Page();
		page.setData(list);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(listTotal.size());
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(listTotal.size()%filter.getPageSize()==0){
			totalPageCount = listTotal.size()/filter.getPageSize();
		}else{
			totalPageCount = listTotal.size()/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
	}

	@Override
	public List<TransDataBo> gsBuyCardList(TransDataFilter filter) {
		// TODO Auto-generated method stub
		String sql = " select left(a.create_date,10),m.mch_name,sum(a.purchase_number),sum(a.pay_amount), "
				+ " sum(b.svc_quota) from order_pay_card a left join (select c.order_number  "+
				" order_number,sum(c.svc_quota) svc_quota from order_sub_pay_card c GROUP BY  "
				+ " c.order_number) b on a.order_number = b.order_number left join  "
				+" mch_merchant m on a.mch_number=m.mch_number  where a.status='1' "
				+ "and a.user_id is null and a.pay_source!=3";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sql = sql + " and a.create_date>='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sql = sql + " and a.create_date<='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				sql = sql + " and m.mch_name like '%"+filter.getMchName().trim()+"%'";
			}
		}
		sql = sql + " GROUP BY left(a.create_date,10),a.mch_number";
		List<Object> listTotal = getSession().createSQLQuery(sql).list();
		List<TransDataBo> list = new ArrayList<TransDataBo>();
		for(int i=0;i<listTotal.size();i++){
			Object[] ary = (Object[]) listTotal.get(i);
			TransDataBo bo = new TransDataBo();
			bo.setDate((String) ary[0]);
			bo.setMch_name((String) ary[1]);
			bo.setBishu(((Double)ary[2]).intValue());
			bo.setBussiness_amount(((BigDecimal)ary[3]).intValue());
			bo.setPay_amount(((BigDecimal)ary[4]).intValue());
			list.add(bo);
			
		}
		return list;
	}

	@Override
	public Page userTrans(TransDataFilter filter) {
		String sql = "select m.date, sum(m.weixin),sum(m.qg),sum(m.zc),sum(m.md),sum(m.twomd),sum(m.mk),sum(m.twomk) "
				+ " from ("+
				" select left(w.sub_date,10) as date,count(DISTINCT w.open_id) as weixin,"
				+ "0 as qg, 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='0'"
				+ " group by left(w.sub_date,10)"+
				" union "+
				" select left(w.unsub_date,10) as date, 0 as weixin, count(DISTINCT w.open_id) as qg,"
				+ " 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='1'"
				+ " group by left(w.unsub_date,10) "+
				" union "+
				" select left(u.create_date,10) as date,0 as weixin,0 as qg,count(DISTINCT u.user_id) as zc ,"
				+ "0 as md,0 as twomd,0 as mk,0 as twomk from on_users u group by left(u.create_date,10) "+
				" union "+
				" select left(o.create_date,10) as date,0 as weixin,0 as qg,0 as zc ,count(DISTINCT o.user_id) as md,"
				+ "0 as twomd,0 as mk,0 as twomk from ("
				+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
				+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
				+ ") o where o.status='1' and o.user_id is not null "
				+ " group by left(o.create_date,10)"+
				" union "+
				" select left(o.date,10) as date,0 as weixin,0 as qg,0 as zc ,0 as md,"
				+ "sum(o.firstFlag) as twomd,0 as mk,0 as twomk from "
				+ "(select s.create_date as date,"
				+ "case when s.create_date=m.create_date and s.user_id=m.user_id then 1 else 0 end as firstFlag "
				+ "from ("
				+ " select bill.create_date as create_date,bill.user_id as user_id,bill.status as status from order_pay_bill bill "
				+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
				+ ") s "
				+ "left join (select min(op.create_date) as create_date ,op.user_id as user_id from order_pay_bill op group by op.user_id"
				+ ") m on m.user_id=s.user_id where s.status='1') o"
				+ " group by left(o.date,10) "+
				" union "+
				" select left(c.create_date,10) as date,0 as weixin,0 as qg,0 as zc ,0 as md,0 as twomd,count(DISTINCT c.user_id) as mk ,"
				+ "0 as twomk  from order_pay_card c where c.status='1' and c.user_id is not null group by left(c.create_date,10) "+
				" union "+
				" select left(card.date,10) as date, 0 as weixin,0 as qg,0 as zc ,0 as md,"
				+ "0 as twomd,0 as mk,sum(card.firstFlag) as twomk from "
				+ "(select oc.create_date as date,"
				+ "case when oc.create_date=m.create_date "
				+ " and oc.user_id=m.user_id  then 1 else 0 end as firstFlag from order_pay_card oc "
				+ " left join "
				+ "(select min(create_date) as create_date, user_id as user_id from order_pay_card group by user_id )m "
				+ " on m.user_id = oc.user_id "
				+ " where oc.status='1') card"
				+ " group by left(card.date,10) "+
				" ) m where 1=1 ";
		
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sql = sql + " and m.date>='"+filter.getBeginTime()+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sql = sql + " and m.date<='"+filter.getEndTime()+"'";
			}
		}
		
		sql = sql +" group by m.date order by m.date desc";
		String hql = sql + " limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List<Object> listPage = getSession().createSQLQuery(hql).list();
		List<TransDataBo> list = new ArrayList<TransDataBo>();
		for(int i=0;i<listPage.size();i++){
			Object[] ary = (Object[]) listPage.get(i);
			TransDataBo bo = new TransDataBo();
			bo.setDate(ary[0].toString());
			bo.setWeixin(((BigDecimal)ary[1]).intValue());
			bo.setQg(((BigDecimal)ary[2]).intValue());
			bo.setBishu(((BigDecimal)ary[3]).intValue());
			bo.setBussiness_amount(((BigDecimal)ary[4]).intValue());
			bo.setTwomd(((BigDecimal)ary[5]).intValue());
			bo.setPay_amount(((BigDecimal)ary[6]).intValue());
			bo.setTwomk(((BigDecimal)ary[7]).intValue());
			list.add(bo);
			
		}
		
		String sqlTotle = "select count(*) from ("+
				" select left(w.sub_date,10) as date from wechat_sub_info w  where w.status='0'"
				+ " group by left(w.sub_date,10)"+
				" union "+
				" select left(w.unsub_date,10) as date from wechat_sub_info w  where w.status='1'"
				+ " group by left(w.unsub_date,10) "+
				" union "+
				" select left(u.create_date,10) as date from on_users u group by left(u.create_date,10) "+
				" union "+
				" select left(o.create_date,10) as date from ("
				+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
				+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
				+ ") o where o.status='1' and o.user_id is not null "
				+ " group by left(o.create_date,10)"+
				" union "+
				" select left(o.date,10) as date from "
				+ "(select create_date as date from ("
				+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
				+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
				+ ") o where status='1') o"
				+ " group by left(o.date,10) "+
				" union "+
				" select left(c.create_date,10) as date"
				+ " from order_pay_card c where c.status='1' and c.user_id is not null group by left(c.create_date,10) "+
				" union "+
				" select left(card.date,10) as date from "
				+ "(select oc.create_date as date from order_pay_card oc "
				+ " where oc.status='1') card"
				+ " group by left(card.date,10) "+
				" ) m where 1=1 ";
		
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sqlTotle = sqlTotle + " and m.date>='"+filter.getBeginTime()+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				sqlTotle = sqlTotle + " and m.date<='"+filter.getEndTime()+"'";
			}
		}
		List<Object> listTotal = getSession().createSQLQuery(sqlTotle).list();
		BigInteger billSizeInteger = (BigInteger) (listTotal.get(0));
		int size = billSizeInteger.intValue();
		Page page = new Page();
		page.setData(list);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(size);
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(size%filter.getPageSize()==0){
			totalPageCount = size/filter.getPageSize();
		}else{
			totalPageCount = size/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
	}

	@Override
	public List<TransDataBo> userTransList(TransDataFilter filter) {
		// TODO Auto-generated method stub
		String sql = "select sum(m.weixin),sum(m.qg),sum(m.zc),sum(m.md),sum(m.twomd),sum(m.mk),sum(m.twomk) from ("+
				" select count(DISTINCT w.open_id) as weixin,"
				+ "0 as qg, 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='0'"+
				" union "+
				" select 0 as weixin, count(DISTINCT w.open_id) as qg,"
				+ " 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='1'"+
				" union "+
				" select 0 as weixin,0 as qg,count(DISTINCT u.user_id) as zc ,"
				+ "0 as md,0 as twomd,0 as mk,0 as twomk from on_users u  "+
				" union "+
				" select 0 as weixin,0 as qg,0 as zc ,count(DISTINCT o.user_id) as md,"
				+ "0 as twomd,0 as mk,0 as twomk from( "
				+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
				+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
				+ " )o where o.status='1' and o.user_id is not null "+
				" union "+
				" select 0 as weixin,0 as qg,0 as zc ,0 as md,"
				+ "count(DISTINCT o.user_id) as twomd,0 as mk,0 as twomk from "
				+ "(select ors.user_id, count(ors.user_id) as counts from ("
				+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
				+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
				+ ") ors where ors.status='1' group by ors.user_id) o"
				+ " where o.counts>1 "+
				" union "+
				" select  0 as weixin,0 as qg,0 as zc ,0 as md,0 as twomd,count(DISTINCT c.user_id) as mk ,"
				+ "0 as twomk  from order_pay_card c where c.status='1' and c.user_id is not null"+
				" union "+
				" select 0 as weixin,0 as qg,0 as zc ,0 as md,"
				+ "0 as twomd,0 as mk,count(DISTINCT oc.accounts_id) as twomk from "
				+ "(select ors.accounts_id, count(ors.accounts_id) as counts from on_deposit_card ors group by ors.accounts_id) oc"
				+ " where oc.counts>1 "+
				" ) m where 1=1 ";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())&&(filter.getEndTime()==null||"".equals(filter.getEndTime()))){
				sql = "select sum(m.weixin),sum(m.qg),sum(m.zc),sum(m.md),sum(m.twomd),sum(m.mk),sum(m.twomk) from ("+
						" select count(DISTINCT w.open_id) as weixin,"
						+ "0 as qg, 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='0' "
						+ " and w.sub_date>='"+filter.getBeginTime()+" 00:00:00'"+ 
						" union "+
						" select 0 as weixin, count(DISTINCT w.open_id) as qg,"
						+ " 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='1' "
						+ " and w.unsub_date>='"+filter.getBeginTime()+" 00:00:00'"+ 
						" union "+
						" select 0 as weixin,0 as qg,count(DISTINCT u.user_id) as zc ,"
						+ "0 as md,0 as twomd,0 as mk,0 as twomk from on_users u "
						+ "where u.create_date>='"+filter.getBeginTime()+" 00:00:00'"+ 
						" union "+
						" select 0 as weixin,0 as qg,0 as zc ,count(DISTINCT o.user_id) as md,"
						+ "0 as twomd,0 as mk,0 as twomk from ("
						+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
						+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
						+ ")o where o.status='1' and o.user_id is not null "
						+ " and o.create_date>='"+filter.getBeginTime()+" 00:00:00'"+ 
						" union "+
						" select 0 as weixin,0 as qg,0 as zc ,0 as md,"
						+ "count(DISTINCT o.user_id) as twomd,0 as mk,0 as twomk from "
						+ "(select ors.user_id, count(ors.user_id) as counts from ("
						+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
						+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
						+ ") ors where ors.status='1'"
						+ " and ors.create_date>='"+filter.getBeginTime()+" 00:00:00'"+ " group by ors.user_id) o"
						+ " where o.counts>1 "+
						" union "+
						" select  0 as weixin,0 as qg,0 as zc ,0 as md,0 as twomd,count(DISTINCT c.user_id) as mk ,"
						+ "0 as twomk  from order_pay_card c where c.status='1' and c.user_id is not null and"
						+ " c.create_date>='"+filter.getBeginTime()+" 00:00:00'"+
						" union "+
						" select 0 as weixin,0 as qg,0 as zc ,0 as md,"
						+ "0 as twomd,0 as mk,count(DISTINCT oc.accounts_id) as twomk from "
						+ "(select ors.accounts_id, count(ors.accounts_id) as counts from on_deposit_card ors"
						+ " where ors.create_date>='"+filter.getBeginTime()+" 00:00:00'"+" group by ors.accounts_id) oc"
						+ " where oc.counts>1 "+
						" ) m where 1=1 ";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())&&(filter.getBeginTime()==null||"".equals(filter.getBeginTime()))){
				sql = "select sum(m.weixin),sum(m.qg),sum(m.zc),sum(m.md),sum(m.twomd),sum(m.mk),sum(m.twomk) from ("+
						" select count(DISTINCT w.open_id) as weixin,"
						+ "0 as qg, 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='0' "
						+ " and w.sub_date<='"+filter.getEndTime()+" 23:59:59'"+ 
						" union "+
						" select 0 as weixin, count(DISTINCT w.open_id) as qg,"
						+ " 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='1' "
						+ " and w.unsub_date<='"+filter.getEndTime()+" 23:59:59'"+ 
						" union "+
						" select 0 as weixin,0 as qg,count(DISTINCT u.user_id) as zc ,"
						+ "0 as md,0 as twomd,0 as mk,0 as twomk from on_users u where u.create_date<='"+filter.getEndTime()+" 23:59:59'"+ 
						" union "+
						" select 0 as weixin,0 as qg,0 as zc ,count(DISTINCT o.user_id) as md,"
						+ "0 as twomd,0 as mk,0 as twomk from ("
						+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
						+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
						+ ") o where o.status='1' and o.user_id is not null "
						+ " and o.create_date<='"+filter.getEndTime()+" 23:59:59'"+ 
						" union "+
						" select 0 as weixin,0 as qg,0 as zc ,0 as md,"
						+ "count(DISTINCT o.user_id) as twomd,0 as mk,0 as twomk from "
						+ "(select ors.user_id, count(ors.user_id) as counts from ("
						+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
						+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
						+ ") ors where ors.status='1'"
						+ " and ors.create_date<='"+filter.getEndTime()+" 23:59:59'"+ " group by ors.user_id) o"
						+ " where o.counts>1 "+
						" union "+
						" select  0 as weixin,0 as qg,0 as zc ,0 as md,0 as twomd,count(DISTINCT c.user_id) as mk ,"
						+ "0 as twomk  from order_pay_card c  where c.status='1' and c.user_id is not null and "
						+ "where c.create_date<='"+filter.getEndTime()+" 23:59:59'"+
						" union "+
						" select 0 as weixin,0 as qg,0 as zc ,0 as md,"
						+ "0 as twomd,0 as mk,count(DISTINCT oc.accounts_id) as twomk from "
						+ "(select ors.accounts_id, count(ors.accounts_id) as counts from on_deposit_card ors"
						+ " where ors.create_date<='"+filter.getEndTime()+" 23:59:59'"+" group by ors.accounts_id) oc"
						+ " where oc.counts>1 "+
						" ) m where 1=1 ";
			}
			
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())&&filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				sql = "select sum(m.weixin),sum(m.qg),sum(m.zc),sum(m.md),sum(m.twomd),sum(m.mk),sum(m.twomk) from ("+
						" select count(DISTINCT w.open_id) as weixin,"
						+ "0 as qg, 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='0' "
						+ " and w.sub_date>='"+filter.getEndTime()+" 00:00:00'"
								+ " and w.sub_date<='"+filter.getEndTime()+" 23:59:59'"+ 
						" union "+
						" select 0 as weixin, count(DISTINCT w.open_id) as qg,"
						+ " 0 as zc ,0 as md,0 as twomd,0 as mk ,0 as twomk from wechat_sub_info w  where w.status='1' "
						+ " and w.unsub_date>='"+filter.getBeginTime()+" 00:00:00' "
								+ "and w.unsub_date<='"+filter.getEndTime()+" 23:59:59'"+ 
						" union "+
						" select 0 as weixin,0 as qg,count(DISTINCT u.user_id) as zc ,"
						+ "0 as md,0 as twomd,0 as mk,0 as twomk from on_users u where "
						+ "u.create_date>='"+filter.getBeginTime()+" 00:00:00'"
						+ "and u.create_date<='"+filter.getEndTime()+" 23:59:59'"+ 
						" union "+
						" select 0 as weixin,0 as qg,0 as zc ,count(DISTINCT o.user_id) as md,"
						+ "0 as twomd,0 as mk,0 as twomk from ("
						+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
						+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
						+ ") o where o.status='1' and o.user_id is not null "
						+ " and o.create_date>='"+filter.getBeginTime()+" 00:00:00'"
						+ " and o.create_date<='"+filter.getEndTime()+" 23:59:59'"+ 
						" union "+
						" select 0 as weixin,0 as qg,0 as zc ,0 as md,"
						+ "count(DISTINCT o.user_id) as twomd,0 as mk,0 as twomk from "
						+ "(select ors.user_id, count(ors.user_id) as counts from ("
						+ " select bill.create_date as create_date,bill.user_id,bill.status as status from order_pay_bill bill "
						+ " union select pay.create_date as create_date,pay.user_id as user_id,pay.status as status from order_pay_courtesy pay "
						+ ") ors where ors.status='1'"
						+ " and ors.create_date>='"+filter.getBeginTime()+" 00:00:00'"
						+ " and ors.create_date<='"+filter.getEndTime()+" 23:59:59'"+ " group by ors.user_id) o"
						+ " where o.counts>1 "+
						" union "+
						" select  0 as weixin,0 as qg,0 as zc ,0 as md,0 as twomd,count(DISTINCT c.user_id) as mk ,"
						+ "0 as twomk  from order_pay_card c  where c.status='1' and c.user_id is not null and "
						+ " c.create_date>='"+filter.getBeginTime()+" 00:00:00'"
						+ " and c.create_date<='"+filter.getEndTime()+" 23:59:59'"+
						" union "+
						" select 0 as weixin,0 as qg,0 as zc ,0 as md,"
						+ "0 as twomd,0 as mk,count(DISTINCT oc.accounts_id) as twomk from "
						+ "(select ors.accounts_id, count(ors.accounts_id) as counts from on_deposit_card ors"
						+ " where ors.create_date>='"+filter.getEndTime()+" 00:00:00'"
						+ " and ors.create_date<='"+filter.getEndTime()+" 23:59:59'"+" group by ors.accounts_id) oc"
						+ " where oc.counts>1 "+
						" ) m where 1=1 ";
			}
			
		}
		List<Object> listTotal = getSession().createSQLQuery(sql).list();
		List<TransDataBo> list = new ArrayList<TransDataBo>();
		for(int i=0;i<listTotal.size();i++){
			Object[] ary = (Object[]) listTotal.get(i);
			TransDataBo bo = new TransDataBo();
			bo.setWeixin(((BigDecimal)ary[0]).intValue());
			bo.setQg(((BigDecimal)ary[1]).intValue());
			bo.setBishu(((BigDecimal)ary[2]).intValue());
			bo.setBussiness_amount(((BigDecimal)ary[3]).intValue());
			bo.setTwomd(((BigDecimal)ary[4]).intValue());
			bo.setPay_amount(((BigDecimal)ary[5]).intValue());
			bo.setTwomk(((BigDecimal)ary[6]).intValue());
			list.add(bo);
		}
		return list;
	}


	@Override
	public List<OrderPayBill> listpayBill(OrderPayBillFilter filter)
			throws Exception {
		String hql = " from OrderPayBill m where m.status='1' and m.order_type='1'"
				+ " order by m.create_date desc";
		List<OrderPayBill> list = super.find(hql);
		return list;
	}


	@Override
	public Page transactionDetail(OrderPayBillFilter filter) {
		// TODO Auto-generated method stub
		String hql = "select s.create_date as create_date,"
				+ " s.firstFlag as firstFlag,s.order_number as order_number, s.mobile_phone as mobile_phone,"
				+ " s.order_type as order_type,"
				+ " s.business_amount as business_amount,s.mch_name as mch_name,s.branch_name as branch_name,"
				+ " s.svc_pay_amount as svc_pay_amount,s.account_pay_amount as account_pay_amount,"
				+ " s.pay_amount as pay_amount,s.status as status ,s.o_return_amount as o_return_amount,"
				+ " s.mch_number as mch_number, "
				+ " s.user_id as user_id from ("
				+ "select b.create_date as create_date,"
				+ " (case when b.create_date =f.firstDate and b.user_id=f.user_id and b.status='1' then 1 else 0 end) "
				+ " as firstFlag,b.order_number as order_number, u.mobile_phone as mobile_phone,b.order_type as order_type,"
				+ " b.business_amount as business_amount,mch.mch_name as mch_name,br.branch_name as branch_name,"
				+ " b.svc_pay_amount as svc_pay_amount,b.account_pay_amount as account_pay_amount,"
				+ " b.pay_amount as pay_amount,b.status as status ,b.o_return_amount as o_return_amount,"
				+ " b.mch_number as mch_number, "
				+ " b.user_id as user_id "
				+ " from order_pay_bill b LEFT JOIN (SELECT min(o.create_date) as firstDate,o.user_id as user_id "
				+ " from order_pay_bill as o where status ='1' GROUP BY o.user_id ) as f "
				+ " on b.user_id=f.user_id "
				+ " LEFT JOIN mch_merchant mch on b.mch_number=mch.mch_number "
				+ " LEFT JOIN mch_merchant_branch br on b.branch_id=br.id"
				+ " LEFT JOIN on_users u on b.user_id=u.user_id"
				+ " union "
				+ " select s.create_date as create_date,null as firstFlag,"
				+ " s.order_number as order_number, u.mobile_phone as mobile_phone,null as order_type,"
				+ " s.purchase_amount as business_amount,mch.mch_name as mch_name,"
				+ " br.branch_name as branch_name,s.svc_pay_amount as svc_pay_amount,"
				+ " s.account_pay_amount as account_pay_amount,s.pay_amount as pay_amount,"
				+ " s.status as status ,s.o_return_amount as o_return_amount,"
				+ " s.mch_number as mch_number,"
				+ " s.user_id as user_id "
				+ " from order_pay_courtesy s "
				+ " LEFT JOIN mch_merchant mch on s.mch_number=mch.mch_number "
				+ " LEFT JOIN mch_merchant_branch br on s.branch_id=br.id "
				+ " LEFT JOIN on_users u on s.user_id=u.user_id"
				+ " ) s where 1=1";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				hql = hql + " and s.mobile_phone='"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				hql = hql + " and s.mch_name like '%"+filter.getMch_name().trim()+"%'";
			}
			if(filter.getBranch_name()!=null&&!"".equals(filter.getBranch_name())){
				hql = hql + " and s.branch_name like '%"+filter.getBranch_name().trim()+"%'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				hql = hql + " and s.create_date >='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				hql = hql + " and s.create_date <='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getFirstFlag()!=null&&!"".equals(filter.getFirstFlag())){
				hql = hql + " and s.firstFlag ='"+filter.getFirstFlag()+"'";
			}
			if(filter.getStatus()!=null&&!"".equals(filter.getStatus())){
				hql = hql + " and s.status ='"+filter.getStatus()+"'";
			}
		}
		
		hql = hql + " order by s.create_date desc limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List pageList = getSession().createSQLQuery(hql).list();//分页数
		List<TransactionDetail> resultList = new ArrayList<TransactionDetail>();
		for(int i=0;i<pageList.size();i++){
			TransactionDetail td = new TransactionDetail();
			Object[] ary = (Object[]) pageList.get(i);
			try {
				td.setCreate_date(sdf.parse((String) ary[0].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ary[1]!=null){
				td.setFirstFlag(ary[1].toString());
			}
			td.setOrder_number((String)ary[2]);
			td.setMobile_phone((String)ary[3]);
			
			if(ary[4]!=null){
				td.setOrder_type((String)ary[4]);
			}
			if(ary[5]!=null){
				td.setBusiness_amount(((Integer)ary[5]).intValue());
			}else{
				td.setBusiness_amount(0);
			}
			td.setMch_name((String) ary[6]);
			td.setBranch_name((String)ary[7]);
			if(ary[8]!=null){
				td.setSvc_pay_amount(((Integer)ary[8]).intValue());
			}else{
				td.setSvc_pay_amount(0);
			}
			
			if(ary[9]!=null){
				td.setAccount_pay_amount(((Integer)ary[9]).intValue());
			}else{
				td.setAccount_pay_amount(0);
			}
			if(ary[10]!=null){
				td.setPay_amount(((Integer)ary[10]).intValue());
			}else{
				td.setPay_amount(0);
			}
			
			td.setStatus((String)ary[11]);
			if(ary[12]!=null){
				td.setO_return_amount(((Integer)ary[12]).intValue());
			}else{
				td.setO_return_amount(0);
			}
			td.setMch_number((String)ary[13]);
			if(ary[14]!=null){
				td.setUser_id((Integer)ary[14]);
			}
			
			resultList.add(td);
		}
		hql = "";
		hql = "select count(*) from ("
				+ "select b.create_date as create_date,"
				+ " (case when b.create_date =f.firstDate and b.user_id=f.user_id and b.status='1' then 1 else 0 end) "
				+ " as firstFlag,b.order_number as order_number, u.mobile_phone as mobile_phone,b.order_type as order_type,"
				+ " b.business_amount as business_amount,mch.mch_name as mch_name,br.branch_name as branch_name,"
				+ " b.svc_pay_amount as svc_pay_amount,b.account_pay_amount as account_pay_amount,"
				+ " b.pay_amount as pay_amount,b.status as status ,b.o_return_amount as o_return_amount,"
				+ " b.mch_number as mch_number, "
				+ " b.user_id as user_id "
				+ " from order_pay_bill b LEFT JOIN (SELECT min(o.create_date) as firstDate,o.user_id as user_id "
				+ " from order_pay_bill as o where status ='1' GROUP BY o.user_id ) as f "
				+ " on b.user_id=f.user_id "
				+ " LEFT JOIN mch_merchant mch on b.mch_number=mch.mch_number "
				+ " LEFT JOIN mch_merchant_branch br on b.branch_id=br.id"
				+ " LEFT JOIN on_users u on b.user_id=u.user_id"
				+ " union "
				+ " select s.create_date as create_date,null as firstFlag,"
				+ " s.order_number as order_number, u.mobile_phone as mobile_phone,null as order_type,"
				+ " s.purchase_amount as business_amount,mch.mch_name as mch_name,"
				+ " br.branch_name as branch_name,s.svc_pay_amount as svc_pay_amount,"
				+ " s.account_pay_amount as account_pay_amount,s.pay_amount as pay_amount,"
				+ " s.status as status ,s.o_return_amount as o_return_amount,"
				+ " s.mch_number as mch_number,"
				+ " s.user_id as user_id "
				+ " from order_pay_courtesy s "
				+ " LEFT JOIN mch_merchant mch on s.mch_number=mch.mch_number "
				+ " LEFT JOIN mch_merchant_branch br on s.branch_id=br.id "
				+ " LEFT JOIN on_users u on s.user_id=u.user_id"
				+ " ) s where 1=1";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				hql = hql + " and s.mobile_phone='"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				hql = hql + " and s.mch_name like '%"+filter.getMch_name().trim()+"%'";
			}
			if(filter.getBranch_name()!=null&&!"".equals(filter.getBranch_name())){
				hql = hql + " and s.branch_name like '%"+filter.getBranch_name().trim()+"%'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				hql = hql + " and s.create_date >='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				hql = hql + " and s.create_date <='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getFirstFlag()!=null&&!"".equals(filter.getFirstFlag())){
				hql = hql + " and s.firstFlag ='"+filter.getFirstFlag()+"'";
			}
			if(filter.getStatus()!=null&&!"".equals(filter.getStatus())){
				hql = hql + " and s.status ='"+filter.getStatus()+"'";
			}
		}
		List listTotal = getSession().createSQLQuery(hql).list();//总数
		BigInteger billSizeInteger = (BigInteger) (listTotal.get(0));
		int size = billSizeInteger.intValue();
		
		System.out.println(size);
		Page page = new Page();
		page.setData(resultList);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(size);
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(size%filter.getPageSize()==0){
			totalPageCount = size/filter.getPageSize();
		}else{
			totalPageCount = size/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
		
	}


	@Override
	public boolean isMember(int userId,String mchNumber) {
		// TODO Auto-generated method stub
		String hql = "select id,ck_id from on_deposit_card o where o.accounts_id="
				+ "(select accounts_id from on_accounts a where a.user_id='"+userId+"')"
				+ " and o.mch_number='"+mchNumber.trim()+"'";
		List list = getSession().createSQLQuery(hql).list();//分页数
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public Page prepaidCardDetails(DepositCardFilter filter) {
		// TODO Auto-generated method stub
		String hql = "select m.mch_name as mch_name, "+
				   " o.svc_number as svc_number, "+
			       " kin.ck_name as ck_name, "+
			       " kin.ck_type as ck_type, "+
			       " kin.ck_quota as ck_quota, "+
			       " kin.sales_amount as sales_amount, "+
			       " kin.discount as discount, "+
			       " users.full_name as full_name, "+
			       " users.mobile_phone as mobile_phone, "+
			       " o.svc_number as svc_number, "+
			       " o.svc_balance as svc_balance, "+
			       " o.share as share, "+
			       "  o.status as status, "+
			       " (case when o.create_date= fir.create_date "+
			       " and o.accounts_id= fir.accounts_id then 1 else 0 end) as firstFlag, "+
			       " o.create_date as create_date, "+
			       " o.id as id, "+
			       " o.accounts_id as accounts_id "+
			       " from on_deposit_card o "+
			       " left join mch_merchant m on m.mch_number= o.mch_number "+
			       " left join mch_card_kinds kin on kin.id= o.ck_id "+
			       " left join( "+
			       " select u.full_name as full_name, u.mobile_phone as mobile_phone, s.accounts_id as accounts_id "+
			       " from on_users u "+
			       " left join on_accounts s on u.user_id= s.user_id) users on users.accounts_id= o.accounts_id "+
			       " left join( "+
			       " select accounts_id as accounts_id, min(create_date) as create_date "+
			       " from on_deposit_card "+
			       " group by accounts_id) fir on o.accounts_id= fir.accounts_id "+
			      " union "+
			       " select m.mch_name as mch_name, "+
			       " co.svc_number as svc_number, "+
			       " kin.ck_name as ck_name, "+
			       " kin.ck_type as ck_type, "+
			       " kin.ck_quota as ck_quota, "+
			       " kin.sales_amount as sales_amount, "+
			       " kin.discount as discount, "+
			       " ca.co_name as full_name, "+
			       " ca.co_tel as mobile_phone, "+
			       " co.svc_number as svc_number, "+
			       " co.svc_balance as svc_balance, "+
			       " co.share as share, "+
			       "  co.status as status, "+
			       "  (case when co.create_date= fir.create_date and "+
			       "co.ck_id= fir.accounts_id then 1 else 0 end) as firstFlag, "+
			       " co.create_date as create_date, "+
			       " co.id as id, "+
			       " co.co_id as accounts_id "+
			       " from co_deposit_card co "+
			       " left join mch_merchant m on m.mch_number= co.mch_number "+
			       " left join mch_card_kinds kin on kin.id= co.ck_id "+
			       " left join co_account ca on ca.co_id= co.co_id "+
			       " left join( "+
			       " select accounts_id as accounts_id, min(create_date) as create_date "+
			       " from on_deposit_card "+
			       "  group by accounts_id) fir on co.ck_id= fir.accounts_id "+
			       "  where 1=1 ";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				hql = hql + " and mobile_phone='"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				hql = hql + " and full_name like '%"+filter.getFull_name().trim()+"%'";
			}
			if(filter.getSvc_number()!=null&&!"".equals(filter.getSvc_number())){
				hql = hql + " and svc_number='"+filter.getSvc_number().trim()+"'";
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				hql = hql + " and mch_name like '%"+filter.getMch_name().trim()+"%'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				hql = hql + " and create_date >='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				hql = hql + " and create_date <='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getFirstFlag()!=null&&!"".equals(filter.getFirstFlag())){
				hql = hql + " and firstFlag ='"+filter.getFirstFlag().trim()+"'";
			}
		}
		hql = hql + " order by create_date desc limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List pageList = getSession().createSQLQuery(hql).list();//分页数
		List<PrepaidCardDetails> resultList = new ArrayList<PrepaidCardDetails>();
		for(int i=0;i<pageList.size();i++){
			PrepaidCardDetails td = new PrepaidCardDetails();
			Object[] ary = (Object[]) pageList.get(i);
			/*"
			       " (case when o.create_date= fir.create_date "+
			       " and o.accounts_id= fir.accounts_id then 1 else 0 end) as firstFlag, "+
			       " o.create_date as create_date "+
			 */
			td.setMch_name((String)ary[0]);
			td.setSvc_number((String)ary[1]);
			td.setCk_name((String)ary[2]);
			td.setCk_type((String)ary[3]);
			td.setCk_quota(((Integer)ary[4]).intValue());
			td.setSales_amount(((Integer)ary[5]).intValue());
			String dist = (String)ary[6];
			Double distd = Double.valueOf(dist)*100; 
			td.setCk_discount(distd.intValue());
			td.setName((String)ary[7]);
			td.setMobile((String)ary[8]);
			td.setSvc_balance(((Integer)ary[10]).intValue());
			td.setShare((String)ary[11]);
			td.setStatus((String)ary[12]);
			td.setFirstFlag(ary[13].toString());
			try {
				td.setCreate_date(sdf.parse((String) ary[14].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			td.setId(ary[15].toString());
			td.setAccoutsId(ary[16].toString());
			resultList.add(td);
		}
		
		String hqlCoTotle = "select count(*) from co_deposit_card";
		String hqlOnTotle = "select count(*) from on_deposit_card";
		List listCoTotle = getSession().createSQLQuery(hqlCoTotle).list();//总数
		List listOnTotle = getSession().createSQLQuery(hqlOnTotle).list();//总数
		BigInteger CoSize = (BigInteger) (listCoTotle.get(0));
		BigInteger OnSize = (BigInteger) (listOnTotle.get(0));
		int size = CoSize.intValue()+OnSize.intValue();
		Page page = new Page();
		page.setData(resultList);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(size);
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(size%filter.getPageSize()==0){
			totalPageCount = size/filter.getPageSize();
		}else{
			totalPageCount = size/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
		
	}


	@Override
	public Page cardUserDetail(Filter filter) {
		// TODO Auto-generated method stub
		String hql = " from CoDepositCard c where c.svc_number='"+filter.getCardNumber()+"'";
		List<CoDepositCard> list = super.find(hql);
		String svc_id = "";
		if(list!=null&&list.size()>0){
			CoDepositCard cdc = list.get(0);
			svc_id = String.valueOf(cdc.getId());
		}else{
			String hqlOn = " from OnDepositCard c where c.svc_number='"+filter.getCardNumber()+"'";
			List<OnDepositCard> listOn = super.find(hqlOn);
			OnDepositCard odc = listOn.get(0);
			svc_id = String.valueOf(odc.getId());
		}
		String sql = " from OnSvcSpendBill o where o.status='1' and o.svc_id='"+svc_id+"' order by o.create_date desc";
		return super.pagedQuery(sql.toString(), filter.getPageNo(), filter.getPageSize());
	}


	@Override
	public String cardBalance() {
		int balance = 0;
		// TODO Auto-generated method stub
		String onSql = " select sum(svc_balance) from on_deposit_card";
		String coSql = " select sum(svc_balance) from co_deposit_card";
		List onTotal = getSession().createSQLQuery(onSql).list();//总数
		List coTotal = getSession().createSQLQuery(coSql).list();//总数
		BigDecimal  onTotalInteger = (BigDecimal ) (onTotal.get(0));
		BigDecimal  coTotalInteger = (BigDecimal ) (coTotal.get(0));
		balance = onTotalInteger.intValue()+coTotalInteger.intValue();
		return balance+"";
	}


	@Override
	public Page listCoAccountDetail(Filter filter) {
		// TODO Auto-generated method stub
		String sql = " from CoAccountBill m "
				+ " where m.create_date>='"+filter.getBeginTime()+" 00:00:00' "
				+ " and m.create_date <='"+filter.getBeginTime()+" 23:59:59' "
				+ " and m.type='"+filter.getType()+"' "
				+ " and m.source_id<>'5'";
		return super.pagedQuery(sql.toString(), filter.getPageNo(), filter.getPageSize());
	}


	@Override
	public List<TransactionDetail> transactionDetailExport(
			OrderPayBillFilter filter) {
		String hql = "select a.id,a.order_number,a.user_id,a.mobile_phone,"
				+ "a.order_type,a.business_amount,a.mch_number,a.mch_name,"
				+ "a.svc_pay_amount,a.account_pay_amount,a.pay_amount,"
				+ "a.pay_source,a.status,a.o_return_amount,a.create_date,a.firstFlag,a.fanxian,"
				+ "(select sum(bils.spend_amount) "
					+ "from on_svc_spend_bill bils where bils.order_number=a.order_number),"
				+ "a.branch_name as branch_name "//储值卡扣除金额
				+ " from(select o.id as id,o.order_number as order_number,"
				+ "o.user_id as user_id,o.mobile_phone as mobile_phone,"
				+ "o.order_type as order_type,o.business_amount as business_amount,"
				+ "o.mch_number as mch_number,"
				+ "(select m.mch_name from mch_merchant m where m.mch_number=o.mch_number) as mch_name ,"
				+ "(select m.branch_name from mch_merchant_branch m where m.id=o.branch_id) as branch_name ,"
				+ "o.svc_pay_amount as svc_pay_amount,o.account_pay_amount as account_pay_amount,"
				+ "o.pay_amount as pay_amount,o.pay_source as pay_source,"
				+ "o.status as status,o.o_return_amount as o_return_amount,"
				+ "o.create_date as create_date,"
				+ "(case when o.create_date =(select min(p.create_date) from order_pay_bill p where p.status='1'"
				+ " and p.mobile_phone=o.mobile_phone) and o.status='1' then 1 else 0 end) as firstFlag,"
				+" (select sum(m.actual_amount) from on_svc_spend_bill m "
				+ "where m.order_number=o.order_number and m.status='1') as fanxian"
				+ " from order_pay_bill o union "
				+" select c.id as id,c.order_number as order_number,"
				+ "c.user_id as user_id,c.mobile_phone as mobile_phone,"
				+ "null as order_type,c.purchase_amount as business_amount,"
				+ "c.mch_number as mch_number,"
				+ "(select m.mch_name from mch_merchant m where m.mch_number=c.mch_number) as mch_name ,"
				+ "(select m.branch_name from mch_merchant_branch m where m.id=c.branch_id) as branch_name ,"
				+ "c.svc_pay_amount as svc_pay_amount,c.account_pay_amount as account_pay_amount,"
				+ "c.pay_amount as pay_amount,c.pay_source as pay_source,"
				+ "c.status as status,c.o_return_amount as o_return_amount,"
				+ "c.create_date as create_date,null as firstFlag, "
				+" (select sum(m.actual_amount) from on_svc_spend_bill m "
				+ "where m.order_number=c.order_number and m.status='1') as fanxian "
				+ "from order_pay_courtesy c)a where 1=1 ";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				hql = hql + " and a.mobile_phone='"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				hql = hql + " and a.mch_name like '%"+filter.getMch_name().trim()+"%'";
			}
			if(filter.getBranch_name()!=null&&!"".equals(filter.getBranch_name())){
				hql = hql + " and a.branch_name like '%"+filter.getBranch_name().trim()+"%'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				hql = hql + " and a.create_date >='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				hql = hql + " and a.create_date <='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getFirstFlag()!=null&&!"".equals(filter.getFirstFlag())){
				hql = hql + " and a.firstFlag ='"+filter.getFirstFlag()+"'";
			}
			if(filter.getStatus()!=null&&!"".equals(filter.getStatus())){
				hql = hql + " and a.status ='"+filter.getStatus()+"'";
			}
		}
		hql = hql + " order by a.create_date desc";
		List listTotal = getSession().createSQLQuery(hql).list();//总数
		List<TransactionDetail> resultList = new ArrayList<TransactionDetail>();
		for(int i=0;i<listTotal.size();i++){
			TransactionDetail td = new TransactionDetail();
			Object[] ary = (Object[]) listTotal.get(i);
			td.setOrder_number((String)ary[1]);
			td.setUser_id(((Integer)ary[2]).intValue());
			td.setMobile_phone((String)ary[3]);
			td.setOrder_type((String)ary[4]);
			if(ary[5]!=null){
				td.setBusiness_amount(((Integer)ary[5]).intValue());
			}else{
				td.setBusiness_amount(0);
			}
			td.setMch_number((String)ary[6]);
			td.setMch_name((String) ary[7]);
			if(ary[8]!=null){
				td.setSvc_pay_amount(((Integer)ary[8]).intValue());
			}else{
				td.setSvc_pay_amount(0);
			}
			if(ary[9]!=null){
				td.setAccount_pay_amount(((Integer)ary[9]).intValue());
			}else{
				td.setAccount_pay_amount(0);
			}
			if(ary[10]!=null){
				td.setPay_amount(((Integer)ary[10]).intValue());
			}else{
				td.setPay_amount(0);
			}
			td.setPay_source((String)ary[11]);
			td.setStatus((String)ary[12]);
			if(ary[13]!=null){
				td.setO_return_amount(((Integer)ary[13]).intValue());
			}else{
				td.setO_return_amount(0);
			}
			try {
				td.setCreate_date(sdf.parse((String) ary[14].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ary[15]!=null){
				td.setFirstFlag(ary[15].toString());
			}
			if(ary[16]!=null){
				td.setFanxian(((BigDecimal)ary[16]).intValue());
			}else{
				td.setFanxian(0);
			}
			if(ary[17]!=null){
				td.setSpendAmount(((BigDecimal)ary[17]).intValue());
			}else{
				td.setSpendAmount(0);
			}
			td.setBranch_name((String)ary[18]);
			
			resultList.add(td);
		}
		return resultList;
	}


	@Override
	public List<PrepaidCardDetails> prepaidCardDetailsExport(
			DepositCardFilter filter) {
		String hql = " select ss.mch_name,ss.ck_name,ss.svc_number,ss.status,"
				+ "ss.ck_quota,ss.sales_amount,ss.svc_balance,"
				+ "ss.name,ss.mobile,ss.firstFlag,ss.zhangshu,"
				+ "ss.create_date,ss.ck_discount,ss.ck_type,ss.branch_name from( select " 
				+ " (select m.mch_name from mch_merchant m where m.mch_number=c.mch_number) as mch_name,"
				+ " (select m.branch_name from mch_merchant_branch m where m.id=("
				+ "select o.branch_id from order_pay_card o where o.order_number=("
				+ "select sub.order_number from order_sub_pay_card sub where sub.svc_number=c.svc_number))"
				+ ") as branch_name,"
				+ " (select mc.ck_name from mch_card_kinds mc where mc.id=c.ck_id) as ck_name,"
				+ " c.svc_number as svc_number,c.status as status,"
				+ " (select mc.ck_quota from mch_card_kinds mc where mc.id=c.ck_id) as ck_quota,"
				+ " (select mc.sales_amount from mch_card_kinds mc where mc.id=c.ck_id) as sales_amount,"
				+ " (select mc.discount from mch_card_kinds mc where mc.id=c.ck_id) as ck_discount,"
				+ " (select mc.ck_type from mch_card_kinds mc where mc.id=c.ck_id) as ck_type,"
				+ " c.svc_balance as svc_balance,"
				+ "(select coa.co_name from co_account coa where coa.co_id=c.co_id) as name,"
				+ "(select coa.co_tel from co_account coa where coa.co_id=c.co_id) as mobile,"
				+ "(case when c.create_date =(select min(p.create_date) from co_deposit_card p) "
				+ " then 1 else 0 end) as firstFlag,"//是否首次购卡
				+ "(select count(m.id) from co_deposit_card m )as zhangshu,"
				+ "c.create_date as create_date from co_deposit_card c union "
				+ " select (select m.mch_name from mch_merchant m where m.mch_number=o.mch_number) as mch_name,"
				+ " (select m.branch_name from mch_merchant_branch m where m.id=("
				+ "select o.branch_id from order_pay_card o where o.order_number=("
				+ "select sub.order_number from order_sub_pay_card sub where sub.svc_number=o.svc_number))"
				+ ") as branch_name,"
				+ " (select mc.ck_name from mch_card_kinds mc where mc.id=o.ck_id) as ck_name,"
				+ " o.svc_number as svc_number,o.status as status,"
				+ " (select mc.ck_quota from mch_card_kinds mc where mc.id=o.ck_id) as ck_quota,"
				+ " (select mc.sales_amount from mch_card_kinds mc where mc.id=o.ck_id) as sales_amount,"
				+ " (select mc.discount from mch_card_kinds mc where mc.id=o.ck_id) as ck_discount,"
				+ " (select mc.ck_type from mch_card_kinds mc where mc.id=o.ck_id) as ck_type,"
				+ " o.svc_balance as svc_balance,"
				+ "(select user.full_name from on_users user where user.user_id=("
				+ " select acc.user_id from on_accounts acc where acc.accounts_id=o.accounts_id )) as name,"
				+ "(select user.mobile_phone from on_users user where user.user_id=("
				+ " select acc.user_id from on_accounts acc where acc.accounts_id=o.accounts_id)) as mobile,"
				+ "(case when o.id =(select min(p.id) from on_deposit_card  p"
				+ " where p.accounts_id = o.accounts_id ) "
				+ " then 1 else 0 end) as firstFlag,"//是否首次购卡
				+ "(select count(p.svc_number) from on_deposit_card p where p.accounts_id=o.accounts_id)as zhangshu,"
				+ "o.create_date as create_date from on_deposit_card o) ss where 1=1 ";
		if(filter!=null){
			if(filter.getMobile_phone()!=null&&!"".equals(filter.getMobile_phone())){
				hql = hql + " and ss.mobile='"+filter.getMobile_phone().trim()+"'";
			}
			if(filter.getFull_name()!=null&&!"".equals(filter.getFull_name())){
				hql = hql + " and ss.name like '%"+filter.getFull_name().trim()+"%'";
			}
			if(filter.getSvc_number()!=null&&!"".equals(filter.getSvc_number())){
				hql = hql + " and ss.svc_number='"+filter.getSvc_number().trim()+"'";
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				hql = hql + " and ss.mch_name like '%"+filter.getMch_name().trim()+"%'";
			}
			if(filter.getBranch_name()!=null&&!"".equals(filter.getBranch_name())){
				hql = hql + " and ss.branch_name like '%"+filter.getBranch_name().trim()+"%'";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				hql = hql + " and ss.create_date >='"+filter.getBeginTime()+" 00:00:00'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				hql = hql + " and ss.create_date <='"+filter.getEndTime()+" 23:59:59'";
			}
			if(filter.getFirstFlag()!=null&&!"".equals(filter.getFirstFlag())){
				hql = hql + " and ss.firstFlag ='"+filter.getFirstFlag().trim()+"'";
			}
		}
		hql = hql + " order by ss.create_date desc ";
		List listTotal = getSession().createSQLQuery(hql).list();//总数
		List<PrepaidCardDetails> resultList = new ArrayList<PrepaidCardDetails>();
		for(int i=0;i<listTotal.size();i++){
			PrepaidCardDetails td = new PrepaidCardDetails();
			Object[] ary = (Object[]) listTotal.get(i);
			td.setMch_name((String)ary[0]);
			td.setCk_name((String)ary[1]);
			td.setSvc_number((String)ary[2]);
			td.setStatus((String)ary[3]);
			td.setCk_quota(((Integer)ary[4]).intValue());
			td.setSales_amount(((Integer)ary[5]).intValue());
			td.setSvc_balance(((Integer)ary[6]).intValue());
			td.setName((String)ary[7]);
			td.setMobile((String)ary[8]);
			td.setFirstFlag(ary[9].toString());
			td.setZhangshu(((BigInteger)ary[10]).intValue());
			try {
				td.setCreate_date(sdf.parse((String) ary[11].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String dist = (String)ary[12];
			Double distd = Double.valueOf(dist)*100; 
			td.setCk_discount(distd.intValue());
			td.setCk_type((String)ary[13]);
			td.setBranch_name((String)ary[14]);
			resultList.add(td);
		}
		return resultList;
	}


	@Override
	public List<PlatFormRE> listPlatformExport(Filter filter) throws Exception {
		// TODO Auto-generated method stub
		String contines = "";
		String hql = "select n.date,sum(n.weixin),sum(n.tixian),sum(n.obill),sum(n.mchantAmount),sum(n.verifi) from( "+
					" select left(a.date,10) as date,sum(a.amount) as weixin ,0 as tixian,0 as obill,0 as mchantAmount,"
					+ "0 as verifi "+ 
					" from(select left(o.create_date,10) date,sum(o.pay_amount) amount from order_pay_card o "+ 
					" where o.status='1' and o.pay_source='1' group by left(o.create_date,10) UNION "+
					" select left(b.create_date,10) date,sum(b.pay_amount) amount from order_pay_bill b "+
					" where b.status='1' and b.pay_amount>0 and b.pay_source='1' group by left(b.create_date,10) "+
					" ) a group by left(a.date,10) UNION"+
					" select left(b.update_date,10) as date,0 as weixin,count(*) as tixian,0 as obill,0 as mchantAmount,"
					+ "0 as verifi "+
					" from on_cash_outs b where b.status='1' GROUP BY left(b.update_date,10) UNION"+
					" select left(b.create_date,10) as date,0 as weixin,0 as tixian,0 as obill,0 as mchantAmount,"
					+ "count(*) as verifi "+
					" from on_bank_card_verifi b where b.status='1' GROUP BY left(b.create_date,10) UNION"+
					" select left(o.create_date,10) as date,0 as weixin,0 as tixian,sum(o.o_return_amount) as obill,0 as mchantAmount,"
					+ "0 as verifi "+
					" from order_pay_bill o where o.status='1' GROUP BY left(o.create_date,10) UNION"+
					" select  left(a.create_date,10),0 as weixin,0 as tixian,0 as obill,"+
					" sum(a.business_amount*a.sale_poundage) as mchantAmount,0 as verifi from("+
					" select s.create_date,s.business_amount,m.sale_poundage from order_sub_pay_card s"+
					" left JOIN mch_card_kinds k on k.id=s.ck_id left JOIN"+
					" mch_merchant m on m.mch_number=k.mch_number where s.status='1')a GROUP BY left(a.create_date,10))n ";
		if(filter!=null){
			
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				contines = contines + " and date>='"+filter.getBeginTime()+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				contines = contines + " and date<='"+filter.getEndTime()+"'";
			}
		}
		String hql2 = hql+"where 1=1 "+contines+" GROUP BY n.date order BY n.date desc";
		List<Object> listTotal = getSession().createSQLQuery(hql2).list();
		
		List<PlatFormRE> reList = new ArrayList<PlatFormRE>();
		for(int i=0;i<listTotal.size();i++){
			Object[] ary = (Object[]) listTotal.get(i);
			PlatFormRE pr = new PlatFormRE();
			pr.setDate((String) ary[0]);
			int weixin =0;
			if((BigDecimal) ary[1]!=null){
				weixin = (int) (((BigDecimal) ary[1]).intValue()*0.006);//微信手续费，0.6%费率
			}
			int tixian = 0;
			if((BigDecimal)ary[2]!=null){
				tixian = ((BigDecimal)ary[2]).intValue()*80;//提现手续费，每笔8毛  80分
			}
			if((BigDecimal)ary[5]!=null){
				tixian = tixian +((BigDecimal)ary[5]).intValue()*81;//银行卡验证 每笔=1分+8毛
			}
			int obill = 0;
			if((BigDecimal)ary[3]!=null){
				obill = ((BigDecimal)ary[3]).intValue();//B类消费  平台返现
			}
			int mchantAmount = 0;
			if((Double)ary[4]!=null){
				mchantAmount = ((Double)ary[4]).intValue();//商户结算手续费
			}
			int zhichu = weixin+tixian;
			int shouru = obill+mchantAmount;
			int all = shouru-zhichu;
			pr.setWeixinAmount(weixin);
			pr.setTixianAmount(tixian);
			pr.setZhichu(zhichu);
			pr.setMachantAmount(mchantAmount);
			pr.setPlatAmount(obill);
			pr.setShouru(shouru);
			pr.setAll(all);
			reList.add(pr);
		}
		return reList;
	}


	@Override
	public Page platShouru(Filter filter) {
		// TODO Auto-generated method stub
		String hql = " select a.date,sum(a.cz),sum(a.md),sum(a.mjs),sum(a.tx),sum(a.cd),sum(a.wx),"
				+ "sum(a.txx),sum(a.ysh),sum(a.yyh) "
				+ " from (select sd.date as date ,0 as cz,"
						+ "0 as md,0 as mjs,0 as tx,0 as cd,0 as wx,0 as txx,0 as ysh, 0 as yyh "
					+ " from sys_date sd "
				+ " union "
					+ " select left(bcard.create_date,10) as date, sum(bcard.pay_amount) as cz, "
						+ "0 as md,0 as mjs,0 as tx,0 as cd,0 as wx,0 as txx,0 as ysh, 0 as yyh "
					+ " from order_pay_card bcard where bcard.status='1' group by left(bcard.create_date,10) "
				+ " union "
					+ " select left(bill.create_date,10) as date,0 as cz,"
						+ "sum(bill.pay_amount) as md, 0 as mjs,0 as tx,0 as cd,0 as wx,0 as txx,0 as ysh, sum(bill.o_return_amount) as yyh "
					+ " from order_pay_bill bill where bill.status='1' group by left(bill.create_date,10)"
				+ " union "
					+ " select left(stm.create_date,10) as date,0 as cz,0 as md, sum(stm.cur_prepay_net) as mjs,"
						+ "0 as tx,0 as cd,0 as wx,0 as txx,sum(stm.cur_fee) as ysh, 0 as yyh "
					+ " from stm_mch_detail stm where stm.status='1' group by left(stm.create_date,10)"
				+ " union "
					+ " select left(ou.create_date,10) as date, "
						+ " 0 as cz,0 as md,0 mjs ,sum(ou.amount) as tx,0 as cd,0 as wx,count(*)*8 as txx,0 as ysh, 0 as yyh "
					+ " from on_cash_outs ou where ou.status='1' group by left(ou.create_date,10) "
				+ " union "
					+ " select left(obi.create_date,10) as date,0 as cz,0 as md,"
						+ "0 as mjs,0 as tx,sum(obi.amount) as cd,0 as wx,0 as txx,0 as ysh, 0 as yyh "
					+ " from on_accounts_bill obi where obi.type='0' and obi.source_id='2' group by left(obi.create_date,10)"
				+ " union "
					+ " select sw.date as date,"
						+ " 0 as cz,0 as md,0 as mjs,0 as tx,0 as cd,"
						+ " sum(sw.aut_business_fee) as wx,0 as txx,0 as ysh, 0 as yyh "
					+ "from stm_wechat sw where sw.status='0' group by sw.date) a  where 1=1 ";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				hql = hql + " and a.date>='"+filter.getBeginTime()+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				hql = hql + " and a.date<='"+filter.getEndTime()+"'";
			}
		}
		hql = hql + " group by a.date ";
		List listTotal = getSession().createSQLQuery(hql).list();//总数
		hql = hql + " order by a.date desc limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List pageList = getSession().createSQLQuery(hql).list();//分页数
		List<PlatShuoru> resultList = new ArrayList<PlatShuoru>();
		for(int i=0;i<pageList.size();i++){
			PlatShuoru td = new PlatShuoru();
			Object[] ary = (Object[]) pageList.get(i);
			td.setDate((String)ary[0]);
			td.setCz(((BigDecimal)ary[1]).intValue());
			td.setMd(((BigDecimal)ary[2]).intValue());
			td.setMjs(((BigDecimal)ary[3]).intValue());
			td.setTx(((BigDecimal)ary[4]).intValue());
			td.setCd(((BigDecimal)ary[5]).intValue());
			td.setWx(((BigDecimal)ary[6]).intValue());
			td.setTxx(((BigDecimal)ary[7]).intValue());
			td.setYsh(((BigDecimal)ary[8]).intValue());
			td.setYyh(((BigDecimal)ary[9]).intValue());
			resultList.add(td);
		}
		Page page = new Page();
		page.setData(resultList);
		page.setPageSize(filter.getPageSize());
		page.setTotalCount(listTotal.size());
		page.setCurrentPageNo(filter.getPageNo());
		int totalPageCount =0;
		if(listTotal.size()%filter.getPageSize()==0){
			totalPageCount = listTotal.size()/filter.getPageSize();
		}else{
			totalPageCount = listTotal.size()/filter.getPageSize() +1;
		}
		page.setTotalPageCount(totalPageCount);
		return page;
	}


	@Override
	public List<PlatShuoru> platShouruList(Filter filter) {
		// TODO Auto-generated method stub
		String hql = " select a.date,sum(a.cz),sum(a.md),sum(a.mjs),sum(a.tx),sum(a.cd),sum(a.wx),"
				+ "sum(a.txx),sum(a.ysh),sum(a.yyh) "
				+ " from (select sd.date as date ,0 as cz,"
						+ "0 as md,0 as mjs,0 as tx,0 as cd,0 as wx,0 as txx,0 as ysh, 0 as yyh "
					+ " from sys_date sd "
				+ " union "
					+ " select left(bcard.create_date,10) as date, sum(bcard.pay_amount) as cz, "
						+ "0 as md,0 as mjs,0 as tx,0 as cd,0 as wx,0 as txx,0 as ysh, 0 as yyh "
					+ " from order_pay_card bcard where bcard.status='1' group by left(bcard.create_date,10) "
				+ " union "
					+ " select left(bill.create_date,10) as date,0 as cz,"
						+ "sum(bill.pay_amount) as md, 0 as mjs,0 as tx,0 as cd,0 as wx,0 as txx,0 as ysh, sum(bill.o_return_amount) as yyh "
					+ " from order_pay_bill bill where bill.status='1' group by left(bill.create_date,10)"
				+ " union "
					+ " select left(stm.create_date,10) as date,0 as cz,0 as md, sum(stm.cur_prepay_net) as mjs,"
						+ "0 as tx,0 as cd,0 as wx,0 as txx,sum(stm.cur_fee) as ysh, 0 as yyh "
					+ " from stm_mch_detail stm where stm.status='1' group by left(stm.create_date,10)"
				+ " union "
					+ " select left(ou.create_date,10) as date, "
						+ " 0 as cz,0 as md,0 mjs ,sum(ou.amount) as tx,0 as cd,0 as wx,count(*)*8 as txx,0 as ysh, 0 as yyh "
					+ " from on_cash_outs ou where ou.status='1' group by left(ou.create_date,10) "
				+ " union "
					+ " select left(obi.create_date,10) as date,0 as cz,0 as md,"
						+ "0 as mjs,0 as tx,sum(obi.amount) as cd,0 as wx,0 as txx,0 as ysh, 0 as yyh "
					+ " from on_accounts_bill obi where obi.type='0' and obi.source_id='2' group by left(obi.create_date,10)"
				+ " union "
					+ " select sw.date as date,"
						+ " 0 as cz,0 as md,0 as mjs,0 as tx,0 as cd,"
						+ " sum(sw.aut_business_fee) as wx,0 as txx,0 as ysh, 0 as yyh "
					+ "from stm_wechat sw where sw.status='0' group by sw.date) a  where 1=1 ";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				hql = hql + " and a.date>='"+filter.getBeginTime()+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				hql = hql + " and a.date<='"+filter.getEndTime()+"'";
			}
		}
		hql = hql + " group by a.date ";
		List listTotal = getSession().createSQLQuery(hql).list();//总数
		List<PlatShuoru> resultList = new ArrayList<PlatShuoru>();
		for(int i=0;i<listTotal.size();i++){
			PlatShuoru td = new PlatShuoru();
			Object[] ary = (Object[]) listTotal.get(i);
			td.setDate((String)ary[0]);
			td.setCz(((BigDecimal)ary[1]).intValue());
			td.setMd(((BigDecimal)ary[2]).intValue());
			td.setMjs(((BigDecimal)ary[3]).intValue());
			td.setTx(((BigDecimal)ary[4]).intValue());
			td.setCd(((BigDecimal)ary[5]).intValue());
			td.setWx(((BigDecimal)ary[6]).intValue());
			td.setTxx(((BigDecimal)ary[7]).intValue());
			td.setYsh(((BigDecimal)ary[8]).intValue());
			td.setYyh(((BigDecimal)ary[9]).intValue());
			resultList.add(td);
		}
		return resultList;
	}


	@Override
	public TransDataBo getData(TransDataFilter filter) {
		// TODO Auto-generated method stub
		String hql ="select count(*) from("
				+ "select a.user_id,count(*) as cu from("
				+ "select user_id as user_id,mch_number as mch_number,create_date as date "
				+ "from order_pay_bill where status='1' "
				+ "union all "
				+ "select user_id as user_id,mch_number as mch_number,create_date as date "
				+ "from order_pay_courtesy where status='1'"
				+ ") as a where 1=1 ";
		if(filter!=null){
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				hql = hql + " and a.mch_number in (select mch_number from mch_merchant where mch_name like '%"+filter.getMchName()+"%' )";
			}
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				hql = hql + " and left(a.date,10)>='"+filter.getBeginTime()+"'";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				hql = hql + " and left(a.date,10)<='"+filter.getEndTime()+"'";
			}
		}
		hql = hql + " GROUP BY a.user_id) as b where b.cu>1";
		List list = getSession().createSQLQuery(hql).list();//总数
		
		TransDataBo bo = new TransDataBo();
		if(list!=null&&list.size()>0){
			bo.setTwomd(((BigInteger)list.get(0)).intValue());
			return bo;
		}else{
			return null;
		}
		
	}


	@Override
	public int getBalance(int userId) {
		// TODO Auto-generated method stub
		String sql = " select a.accounts_balance from on_accounts a where a.user_id='"+userId+"'";
		List<Object> list = getSession().createSQLQuery(sql).list();
		Integer balanceInteger = (Integer) (list.get(0));
		int balance = balanceInteger.intValue();
		return balance;
	}


	@Override
	public int getCardCount(int userId) {
		// TODO Auto-generated method stub
		String sql = " select count(*) from on_deposit_card a where a.accounts_id"
				+ "=(select b.accounts_id from on_accounts b where b.user_id='"+userId+"')";
		List<Object> list = getSession().createSQLQuery(sql).list();
		BigInteger countInteger = (BigInteger) (list.get(0));
		return countInteger.intValue();
	}


	@Override
	public int getPayCount(int userId) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from order_pay_bill a where a.user_id='"+userId+"' and a.status='1'";
		List<Object> list = getSession().createSQLQuery(sql).list();
		BigInteger payCountInteger = (BigInteger) (list.get(0));
		return payCountInteger.intValue();
	}


	@Override
	public String getQudao(String openId) {
		// TODO Auto-generated method stub
		String sql = "select a.qr_channel_name from qr_code_info a where a.qr_ticket="
				+ "(select b.qr_ticket from wechat_sub_info b where b.open_id='"+openId+"')";
		List<Object> list = getSession().createSQLQuery(sql).list();
		if(list!=null&&list.size()>0){
			String qudao = (String) (list.get(0));
			return qudao;
		}else{
			return "";
		}
	}


	@Override
	public Map<String,Integer> getSpendAmout(String orderNumber) {
		// TODO Auto-generated method stub
		String sql = " select sum(spend_amount),sum(actual_amount)"
				+ " from on_svc_spend_bill where order_number='"+orderNumber+"'";
		List<Object> list = getSession().createSQLQuery(sql).list();
		Map<String,Integer> map = new HashMap<String,Integer>();
		if(list!=null&&list.size()>0){
			Object[] ary = (Object[]) list.get(0);
			int spendAmount = 0;
			if(ary[0]!=null){
				spendAmount = ((BigDecimal)ary[0]).intValue();
			}
			int userReturn = 0;
			if(ary[1]!=null){
				userReturn = ((BigDecimal)ary[1]).intValue();
			}
			map.put("spendAmount", spendAmount);
			map.put("userReturn", userReturn);
		}
		return map;
	}


	@Override
	public Map<String, Object> getBranchAndCount(String id, String accounts_id) {
		// TODO Auto-generated method stub
		String sqlBranch = " select b.branch_name from mch_merchant_branch b "
				+ "	where b.id=("
				+ "select o.branch_id from order_pay_card o where o.order_number=("
				+ "select s.order_number from order_sub_pay_card s where s.svc_id='"+id.trim()+"'))";
		List<Object> listBranch = getSession().createSQLQuery(sqlBranch).list();
		String branchName = "";
		if(listBranch!=null&&listBranch.size()>0){
			branchName = listBranch.get(0).toString();
		}
		
		String sqlCount = "select count(p.svc_number) from on_deposit_card p where p.accounts_id='"+accounts_id+"'";
		List<Object> listCount = getSession().createSQLQuery(sqlCount).list();
		int count = 0;
		if(listCount!=null&&listCount.size()>0){
			count = ((BigInteger)listCount.get(0)).intValue();
		}
		if(count==0){
			String sqlCountCo = "select count(p.svc_number) from co_deposit_card p where p.co_id='"+accounts_id+"'";
			List<Object> listCountCo = getSession().createSQLQuery(sqlCountCo).list();
			if(listCountCo!=null&&listCountCo.size()>0){
				count = ((BigInteger)listCountCo.get(0)).intValue();
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("branchName", branchName);
		map.put("count", count);
		return map;
	}


}
  