package cn.bc.business.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.bc.business.dao.BusinessDao;
import cn.bc.business.po.IncomeAnalysisConfig;
import cn.bc.business.po.IncomeAnalysisList;
import cn.bc.business.po.MchValueList;
import cn.bc.business.po.MchValueSummary;
import cn.bc.business.po.OnReChange;
import cn.bc.business.vo.AnalysBo;
import cn.bc.business.vo.AnalysDetailListBo;
import cn.bc.business.vo.AnalysFilter;
import cn.bc.business.vo.AnalysTongJiBo;
import cn.bc.business.vo.OnReChangeFilter;
import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.OnAccounts;
import cn.bc.query.po.OnAccountsBill;
import cn.bc.query.vo.Filter;
import cn.bc.query.vo.TransDataBo;

public class BusinessDaoImpl extends BaseHibernateDao implements BusinessDao{

	@Override
	public Page list() {
		return null;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void save() {
		
	}

	@Override
	public Page getAnalyis(AnalysFilter filter,int range) {
		// TODO Auto-generated method stub
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date start_date= new Date((new Date()).getTime()-1000*60*60*24*(range));
		Date end_date= new Date((new Date()).getTime()-1000*60*60*24);
		String sql = "select anl.mch_name,"
				+ "anl.ck_name,"
				+"(select count(cardss.id) from on_deposit_card cardss "
				+ "where cardss.ck_id=anl.ck_id and cardss.status='0' and cardss.share='0' ),"
				+ "anl.ck_quota,"
				+ "anl.sales_amount,"
				+ "anl.shouyi,"
				+ "case when anl.ck_type='0' then "
				+ "((anl.zhangshu+1)/anl.ridan)*anl.ck_quota/anl.danjia "
				+ "else"
				+ " ((anl.zhangshu+1)/anl.ridan)*anl.ck_quota/(anl.danjia*anl.discount) "
				+ "end as zhouqi,"
				+ "case when anl.ck_type='0' then "
				+ "30*(anl.shouyi/anl.sales_amount)/(((anl.zhangshu+1)/anl.ridan)*anl.ck_quota/anl.danjia) "
				+ "else "
				+ "30*(anl.shouyi/anl.sales_amount)/(((anl.zhangshu+1)/anl.ridan)*anl.ck_quota/(anl.danjia*anl.discount)) "
				+ "end as yuehua,"
				+ "case when anl.ck_type='0' then "
				+ "12*30*(anl.shouyi/anl.sales_amount)/(((anl.zhangshu+1)/anl.ridan)*anl.ck_quota/anl.danjia) "
				+ "else "
				+ "12*30*(anl.shouyi/anl.sales_amount)/(((anl.zhangshu+1)/anl.ridan)*anl.ck_quota/(anl.danjia*anl.discount)) "
				+ " end as nianhua, "
				+ "anl.mch_number ,"
				+ "anl.status as status "
				+ " from(select m.mch_name as mch_name,"
					+ "m.mch_number as mch_number,"
					+ "card.ck_name as ck_name,"
					+ "card.id as ck_id,"
					+ "card.ck_quota as ck_quota,"
					+ "card.sales_amount as sales_amount,"
					+ "case when card.ck_type='0' then "
					+ "(m.mch_min_discount*card.ck_quota/100-card.sales_amount)*0.8 "
					+ "else "
					+ "(m.mch_min_discount-card.discount*100)/100*(card.sales_amount/card.discount)*0.8 "
					+ " end as  shouyi, "
					+ "(select count(card.id) from on_deposit_card card "
						+ "where card.mch_number =m.mch_number and card.status='0' and card.share='0' ) as zhangshu,"
					+ "s.danjia as danjia,"
					+ "s.ridan as ridan ,"
					+ " card.status as status,"
					+ " card.ck_type,"
					+ " card.discount "
					+ "from mch_merchant m "
					+ "left join mch_card_kinds card on m.mch_number = card.mch_number "
					+ "left join ("
						+ "select bill.mch_number as mch_number,"
						+ "sum(bill.business_amount)/count(order_number) as danjia,"
						+ "count(order_number)/("+range+") as ridan "
						+ "from order_pay_bill bill "
						+ " where bill.order_type='1' and bill.status='1' "
						+ " and bill.create_date>='"+df.format(start_date)+" 00:00:00' "
						+ " and bill.create_date<='"+df.format(end_date)+" 23:59:59' "
						+ "group by bill.mch_number) "
					+ "s on s.mch_number=m.mch_number "
//				+ ") anl where 1=1  ";
			+ ") anl where 1=1 and anl.ridan>1 ";
		if(filter!=null){
			if(filter.getMchName()!=null&&!"".equals(filter.getMchName())){
				sql = sql + " and anl.mch_name like'%"+filter.getMchName()+"%'";
			}
			if(filter.getStatus()!=null&&!"".equals(filter.getStatus())){
				sql = sql + " and anl.status='"+filter.getStatus()+"'";
			}
			if(filter.getOrderFlag()!=null&&!"".equals(filter.getOrderFlag())){
				if("0".equals(filter.getOrderFlag())){
					sql = sql+" order by anl.mch_name desc";
				}
				if("00".equals(filter.getOrderFlag())){
					sql = sql+" order by anl.mch_name";
				}
				if("1".equals(filter.getOrderFlag())){
					sql = sql+" order by zhouqi desc,anl.mch_name desc";
				}
				if("11".equals(filter.getOrderFlag())){
					sql = sql+" order by zhouqi,anl.mch_name desc";
				}
				if("2".equals(filter.getOrderFlag())){
					sql = sql+" order by yuehua desc,anl.mch_name desc";
				}
				if("22".equals(filter.getOrderFlag())){
					sql = sql+" order by yuehua,anl.mch_name desc";
				}
				if("3".equals(filter.getOrderFlag())){
					sql = sql+" order by nianhua desc,anl.mch_name desc";
				}
				if("33".equals(filter.getOrderFlag())){
					sql = sql+" order by nianhua,anl.mch_name desc";
				}
			}else{
				sql = sql+" order by anl.mch_name desc";
			}
		}
		List<Object> listTotal = getSession().createSQLQuery(sql).list();
		sql = sql + " limit "+(filter.getPageNo()-1)*filter.getPageSize()+","+filter.getPageSize();
		List<Object> listPage = getSession().createSQLQuery(sql).list();
		
		List<AnalysBo> list = new ArrayList<AnalysBo>();
		for(int i=0;i<listPage.size();i++){
			Object[] ary = (Object[]) listPage.get(i);
			AnalysBo bo = new AnalysBo();
			bo.setMchName((String)ary[0]);
			bo.setCk_name((String)ary[1]);
			bo.setCk_count(((BigInteger )ary[2]).intValue());
			bo.setCk_quota((Integer)ary[3]);
			bo.setSales_amount((Integer)ary[4]);
			if(ary[5]!=null){
				bo.setShouyi(((Double)ary[5]).intValue());
			}else{
				bo.setShouyi(0);
			}
			if(ary[6]!=null){
				bo.setZhouqi((Double)ary[6]);
			}else{
				bo.setZhouqi(0.00);
			}
			if(ary[7]!=null){
				Double d = ((Double)ary[7])*100.00;
				bo.setYuehua(d.doubleValue());
			}else{
				bo.setYuehua(0.00);
			}
			if(ary[8]!=null){
				Double dd = ((Double)ary[8])*100.00;
				bo.setNianhua(dd.doubleValue());
			}else{
				bo.setNianhua(0.00);
			}
			bo.setMchNumber((String)ary[9]);
			bo.setStatus((String)ary[10]);
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
	public Map<String, Object> getAnalyisDetail(AnalysFilter filter) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		String totalSql=" select a.danjia,a.ridan,a.business_amount,a.yueamount from("
				+ "select bill.mch_number as mch_number,"
				+ " sum(bill.business_amount)/count(order_number) as danjia,"
				+ " count(order_number)/((SELECT TIMESTAMPDIFF(day,min(bill.create_date),sysdate()))+1) as ridan,"
				+ " sum(bill.business_amount) as business_amount, "
				+ " (sum(bill.business_amount)/count(order_number))*("
				+ "count(order_number)/((SELECT TIMESTAMPDIFF(day,min(bill.create_date),sysdate()))+1))*30 as yueamount "
				+ " from order_pay_bill bill  where bill.order_type='1' and bill.status='1' "
				+ " group by bill.mch_number) a where a.mch_number='"+filter.getMchNumber()+"'";
		List<Object> totalList = getSession().createSQLQuery(totalSql).list();
		AnalysTongJiBo tongjiBo = new AnalysTongJiBo();
		Object[] aryss = (Object[]) totalList.get(0);
		if(aryss[0]!=null){
			tongjiBo.setKedan(((BigDecimal)aryss[0]).intValue());
		}else{
			tongjiBo.setKedan(0);
		}
		if(aryss[1]!=null){
			 BigDecimal b1 = new BigDecimal(100);
			tongjiBo.setRidan((((BigDecimal)aryss[1]).multiply(b1)).intValue());
		}else{
			tongjiBo.setRidan(0);
		}
		if(aryss[2]!=null){
			tongjiBo.setAllAmout(((BigDecimal)aryss[2]).intValue());
		}else{
			tongjiBo.setAllAmout(0);
		}
		if(aryss[3]!=null){
			tongjiBo.setYueliu(((BigDecimal)aryss[3]).intValue());
		}else{
			tongjiBo.setYueliu(0);
		}
		
		String sqlOn = "select count(card.id) "
				+ "from on_deposit_card card "
				+ "where card.ck_id in("
					+ "select kind.id from mch_card_kinds kind where kind.mch_number='"+filter.getMchNumber()+"') "
				+ "and card.status='0'"; 
		List<Object> totalOn = getSession().createSQLQuery(sqlOn).list();//个人未用完储值卡张数
		String sqlCo = "select count(card.id) "
				+ "from co_deposit_card card "
				+ "where card.ck_id in("
					+ "select kind.id from mch_card_kinds kind where kind.mch_number='"+filter.getMchNumber()+"') "
				+ "and card.status='0'"; 
		List<Object> totalCo = getSession().createSQLQuery(sqlCo).list();//公司未用完储值卡张数
		Integer freeZhangshu = 0;//未用完储值卡总张数
		if(((BigInteger)totalOn.get(0))!=null){
			freeZhangshu =freeZhangshu + ((BigInteger)totalOn.get(0)).intValue();
		}
		if(((BigInteger) totalCo.get(0))!=null){
			freeZhangshu =freeZhangshu + ((BigInteger) totalCo.get(0)).intValue();
		}
		tongjiBo.setFreeZhangshu(freeZhangshu);
		
		map.put("tongji",tongjiBo);
		String listSql = " select m.mch_number as mch_number,"
				+ " card.ck_name as ck_name,"
				+ " card.id as ck_id,"
				+ " card.ck_quota as ck_quota,"
				+ " card.sales_amount as sales_amount,"
				+ " mm.zhangshu as zhangshu,"
				+ " mm.zhangshu*card.ck_quota as all_ck_quota,"
				+ " mm.zhangshu*card.sales_amount as all_sales_amount,"
				+ " mm.svc_balance as svc_balance,"
				+ " mm.actual_amount as actual_amount "
				+ " from mch_merchant m left join mch_card_kinds card on m.mch_number = card.mch_number "
				+ " left join"
				+ " ("
					+ " select s.ck_id as ck_id,s.ck_name as ck_name,sum(s.zhangshu) as zhangshu,"
					+ " sum(s.svc_balance) as svc_balance,sum(s.actual_amount) as actual_amount"
					+ " from("
						+ " select  cocard.ck_id as ck_id,"
						+ " (select card.ck_name from mch_card_kinds card where card.id=cocard.ck_id) as ck_name,"
						+ " count(cocard.ck_id) as zhangshu, "
						+ " sum(cocard.svc_balance) as svc_balance ,"
						+ " (select sum(bill.actual_amount) from on_svc_spend_bill bill where bill.order_number in("
							+ " select pay.order_number from order_pay_bill pay where pay.business_amount>pay.svc_pay_amount) "
						+ " and bill.svc_id in("
							+ " select cards.id from co_deposit_card cards where cards.ck_id=cocard.ck_id)"
						+ " ) as actual_amount "
						+ " from co_deposit_card cocard GROUP BY cocard.ck_id "
						+ " union "
						+ " select  oncard.ck_id as ck_id,"
						+ " (select card.ck_name from mch_card_kinds card where card.id=oncard.ck_id) as ck_name,"
						+ " count(oncard.ck_id) as zhangshu, sum(oncard.svc_balance) as svc_balance,"
						+ " (select sum(bill.actual_amount) from on_svc_spend_bill bill where bill.order_number in("
							+ " select pay.order_number from order_pay_bill pay where pay.business_amount>pay.svc_pay_amount) "
						+ " and bill.svc_id in("
							+ " select cards.id from on_deposit_card cards where cards.ck_id=oncard.ck_id)"
						+ " ) as actual_amount "
						+ " from on_deposit_card oncard GROUP BY oncard.ck_id"
					+ ") s  group by s.ck_id "
				+ ") mm on mm.ck_id = card.id where m.mch_number='"+filter.getMchNumber()+"'";
		List<Object> listTotal = getSession().createSQLQuery(listSql).list();
		List<AnalysDetailListBo> listTotalResult = new ArrayList<AnalysDetailListBo>();
		for(int i=0;i<listTotal.size();i++){
			Object[] ary = (Object[]) listTotal.get(i);
			AnalysDetailListBo bo = new AnalysDetailListBo();
			bo.setCk_name((String)ary[1]);
			bo.setMiane((Integer)ary[3]);
			bo.setSaleAmount((Integer)ary[4]);
			if(ary[5]!=null){
				bo.setZhangshu(((BigDecimal)ary[5]).intValue());
			}else{
				bo.setZhangshu(0);
			}
			if(ary[6]!=null){
				bo.setAllMiane(((BigDecimal)ary[6]).intValue());
			}else{
				bo.setAllMiane(0);
			}
			if(ary[7]!=null){
				bo.setAllSaleAmount(((BigDecimal)ary[7]).intValue());
			}else{
				bo.setAllSaleAmount(0);
			}
			if(ary[8]!=null){
				bo.setBalance(((BigDecimal)ary[8]).intValue());
			}else{
				bo.setBalance(0);
			}
			if(ary[9]!=null){
				bo.setFanxian(((BigDecimal)ary[9]).intValue());
			}else{
				bo.setFanxian(0);
			}
			listTotalResult.add(bo);
		}
		map.put("listTotalResult", listTotalResult);
		return map;
	}

	//跑批生成数据
	@Override
	public List<IncomeAnalysisList> taskGetAnalyis(Date date) {
		String configSql = " from IncomeAnalysisConfig";
		List<IncomeAnalysisConfig> listConfig = super.find(configSql);
		int ranges = listConfig.get(0).getRanges();
		if(ranges==0){
			ranges = 7;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date start_date= new Date((new Date()).getTime()-1000*60*60*24*(ranges));
		Date end_date= new Date((new Date()).getTime()-1000*60*60*24);
		// TODO Auto-generated method stub
		String sql = "select anl.mch_name,"
				+ "anl.mch_number,"
				+ "anl.ck_name,"
				+ "anl.ck_id,"
				+"(select count(cardss.id) from on_deposit_card cardss "
				+ "where cardss.ck_id=anl.ck_id and cardss.status='0' and cardss.share='0' ),"
				+ "anl.ck_quota,"
				+ "anl.sales_amount,"
				+ "anl.shouyi,"
				+ "((anl.zhangshu+1)/anl.ridan)*anl.ck_quota/anl.danjia as zhouqi,"
				+ "30*(anl.shouyi/anl.sales_amount)/(((anl.zhangshu+1)/anl.ridan)*anl.ck_quota/anl.danjia) as yuehua,"
				+ "12*30*(anl.shouyi/anl.sales_amount)/(((anl.zhangshu+1)/anl.ridan)*anl.ck_quota/anl.danjia) as nianhua "
				+ "from(select m.mch_name as mch_name,"
					+ "m.mch_number as mch_number,"
					+ "card.ck_name as ck_name,"
					+ "card.id as ck_id,"
					+ "card.ck_quota as ck_quota,"
					+ "card.sales_amount as sales_amount,"
					+ "case when card.ck_type='0' then "
					+ "(m.mch_min_discount*card.ck_quota/100-card.sales_amount)*0.8 "
					+ "else "
					+ "(m.mch_min_discount-card.discount*100)/100*(card.sales_amount/card.discount)*0.8 "
					+ " end as  shouyi, "
					+ "(select count(card.id) from on_deposit_card card "
						+ "where card.mch_number =m.mch_number and card.status='0' and card.share='0' ) as zhangshu,"
					+ "s.danjia as danjia,"
					+ "s.ridan as ridan "
					+ "from mch_merchant m "
					+ "left join mch_card_kinds card on m.mch_number = card.mch_number "
					+ "left join ("
						+ "select bill.mch_number as mch_number,"
						+ "sum(bill.business_amount)/count(order_number) as danjia,"
						+ "count(order_number)/"+ranges+" as ridan "
						+ "from order_pay_bill bill "
						+ " where bill.order_type='1' and bill.status='1'"
						+ " and bill.create_date>='"+sdf.format(start_date)+" 00:00:00' "
						+ " and bill.create_date<='"+sdf.format(end_date)+" 23:59:59' "
						+ " group by bill.mch_number) "
					+ "s on s.mch_number=m.mch_number"
				+ ") anl where 1=1 and anl.ridan>1 order by anl.mch_number desc ";
//		+ ") anl where 1=1 order by anl.mch_number desc  ";
		List<Object> listTotal = getSession().createSQLQuery(sql).list();
		
		List<IncomeAnalysisList> list = new ArrayList<IncomeAnalysisList>();
		DecimalFormat   df   =new   DecimalFormat("0.00");
//		System.out.println();

		for(int i=0;i<listTotal.size();i++){
			Object[] ary = (Object[]) listTotal.get(i);
			IncomeAnalysisList ia = new IncomeAnalysisList();
			ia.setMch_name((String)ary[0]);
			ia.setMch_number((String)ary[1]);
			ia.setCk_name((String)ary[2]);
			ia.setCk_id((Integer)ary[3]+"");
			if(ary[4]!=null){
				ia.setCk_count(((BigInteger)ary[4]).toString());
			}else{
				ia.setCk_count("0");
			}
			
			if(ary[5]!=null){
				ia.setCk_quota(((Integer)ary[5]));
			}else{
				ia.setCk_quota(0);
			}
			if(ary[6]!=null){
				ia.setSales_amount(((Integer)ary[6]));
			}else{
				ia.setSales_amount(0);
			}
			if(ary[7]!=null){
				ia.setShouyi(((Double)ary[7]).intValue()+"");
			}else{
				ia.setShouyi("0");
			}
			if(ary[8]!=null){
				String zhouqi = ((BigDecimal)ary[8]).toString();
				ia.setZhouqi(df.format(Double.valueOf(zhouqi)));
			}else{
				ia.setZhouqi("0");
			}
			if(ary[9]!=null){
				Double d = ((Double)ary[9])*100.00;
				String yuehua = d.toString();
				ia.setYuehua(df.format(Double.valueOf(yuehua)));
			}else{
				ia.setYuehua("0");
			}
			if(ary[10]!=null){
				Double dd = ((Double)ary[10])*100.00;
				String nianhua = dd.toString();
				ia.setNianhua(df.format(Double.valueOf(nianhua)));
			}else{
				ia.setNianhua("0");
			}
			ia.setCreate_date(date);
			list.add(ia);
		}
		
		return list;
	}
	
	/*
	 * 保存数据
	 */
	@Override
	public void setData(IncomeAnalysisList incomeAnalysisList){
		super.save(incomeAnalysisList);
	}

	@Override
	public MchValueSummary getMchValueSummary(String mchNumber, Date date) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		String totalSql=" select a.danjia,a.ridan,a.business_amount,a.yueamount,"
				+ "(select m.mch_name from mch_merchant m where m.mch_number='"+mchNumber+"')"
				+ " from("
				+ "select bill.mch_number as mch_number,"
				+ " sum(bill.business_amount)/count(order_number) as danjia,"
				+ " count(order_number)/((SELECT TIMESTAMPDIFF(day,min(bill.create_date),sysdate()))+1) as ridan,"
				+ " sum(bill.business_amount) as business_amount, "
				+ " (sum(bill.business_amount)/count(order_number))*("
				+ "count(order_number)/((SELECT TIMESTAMPDIFF(day,min(bill.create_date),sysdate()))+1))*30 as yueamount "
				+ " from order_pay_bill bill  where bill.order_type='1' and bill.status='1' "
				+ " group by bill.mch_number) a where a.mch_number='"+mchNumber+"'";
		List<Object> totalList = getSession().createSQLQuery(totalSql).list();
		MchValueSummary tongjiBo = new MchValueSummary();
		if(totalList!=null&&totalList.size()>0){
				
			Object[] aryss = (Object[]) totalList.get(0);
			if(aryss[0]!=null){
				tongjiBo.setKedan(((BigDecimal)aryss[0]).intValue()+"");
			}else{
				tongjiBo.setKedan("0");
			}
			if(aryss[1]!=null){
				 BigDecimal b1 = new BigDecimal(100);
				tongjiBo.setRidan((((BigDecimal)aryss[1]).multiply(b1)).intValue()+"");
			}else{
				tongjiBo.setRidan("0");
			}
			if(aryss[2]!=null){
				tongjiBo.setBussiness_amount(((BigDecimal)aryss[2]).intValue()+"");
			}else{
				tongjiBo.setBussiness_amount("0");
			}
			if(aryss[3]!=null){
				tongjiBo.setYueliu(((BigDecimal)aryss[3]).intValue()+"");
			}else{
				tongjiBo.setYueliu("0");
			}
			tongjiBo.setMch_name((String)aryss[4]);
			String sqlOn = "select count(card.id) "
					+ "from on_deposit_card card "
					+ "where card.ck_id in("
						+ "select kind.id from mch_card_kinds kind where kind.mch_number='"+mchNumber+"') "
					+ "and card.status='0'"; 
			List<Object> totalOn = getSession().createSQLQuery(sqlOn).list();//个人未用完储值卡张数
			String sqlCo = "select count(card.id) "
					+ "from co_deposit_card card "
					+ "where card.ck_id in("
						+ "select kind.id from mch_card_kinds kind where kind.mch_number='"+mchNumber+"') "
					+ "and card.status='0'"; 
			List<Object> totalCo = getSession().createSQLQuery(sqlCo).list();//公司未用完储值卡张数
			Integer freeZhangshu = 0;//未用完储值卡总张数
			if(((BigInteger)totalOn.get(0))!=null){
				freeZhangshu =freeZhangshu + ((BigInteger)totalOn.get(0)).intValue();
			}
			if(((BigInteger) totalCo.get(0))!=null){
				freeZhangshu =freeZhangshu + ((BigInteger) totalCo.get(0)).intValue();
			}
			tongjiBo.setAvailable_card_number(freeZhangshu+"");
			tongjiBo.setCreate_date(date);
			return tongjiBo;
		}else{
			return null;
		}	
		
	}

	@Override
	public void setMchValueSummary(MchValueSummary mchValueSummary) {
		// TODO Auto-generated method stub
		super.save(mchValueSummary);
		
	}

	@Override
	public List<MchValueList> getMchValueList(String mchNumber, Date date) {
		// TODO Auto-generated method stub
		String listSql = " select m.mch_number as mch_number,"
				+"m.mch_name as mch_name,"
				+ " card.ck_name as ck_name,"
				+ " card.id as ck_id,"
				+ " card.ck_quota as ck_quota,"
				+ " card.sales_amount as sales_amount,"
				+ " mm.zhangshu as zhangshu,"
				+ " mm.zhangshu*card.ck_quota as all_ck_quota,"
				+ " mm.zhangshu*card.sales_amount as all_sales_amount,"
				+ " mm.svc_balance as svc_balance,"
				+ " mm.actual_amount as actual_amount "
				+ " from mch_merchant m left join mch_card_kinds card on m.mch_number = card.mch_number "
				+ " left join"
				+ " ("
					+ " select s.ck_id as ck_id,s.ck_name as ck_name,sum(s.zhangshu) as zhangshu,"
					+ " sum(s.svc_balance) as svc_balance,sum(s.actual_amount) as actual_amount"
					+ " from("
						+ " select  cocard.ck_id as ck_id,"
						+ " (select card.ck_name from mch_card_kinds card where card.id=cocard.ck_id) as ck_name,"
						+ " count(cocard.ck_id) as zhangshu, "
						+ " sum(cocard.svc_balance) as svc_balance ,"
						+ " (select sum(bill.actual_amount) from on_svc_spend_bill bill where bill.order_number in("
							+ " select pay.order_number from order_pay_bill pay where pay.business_amount>pay.svc_pay_amount) "
						+ " and bill.svc_id in("
							+ " select cards.id from co_deposit_card cards where cards.ck_id=cocard.ck_id)"
						+ " ) as actual_amount "
						+ " from co_deposit_card cocard GROUP BY cocard.ck_id "
						+ " union "
						+ " select  oncard.ck_id as ck_id,"
						+ " (select card.ck_name from mch_card_kinds card where card.id=oncard.ck_id) as ck_name,"
						+ " count(oncard.ck_id) as zhangshu, sum(oncard.svc_balance) as svc_balance,"
						+ " (select sum(bill.actual_amount) from on_svc_spend_bill bill where bill.order_number in("
							+ " select pay.order_number from order_pay_bill pay where pay.business_amount>pay.svc_pay_amount) "
						+ " and bill.svc_id in("
							+ " select cards.id from on_deposit_card cards where cards.ck_id=oncard.ck_id)"
						+ " ) as actual_amount "
						+ " from on_deposit_card oncard GROUP BY oncard.ck_id"
					+ ") s  group by s.ck_id "
				+ ") mm on mm.ck_id = card.id where m.mch_number='"+mchNumber+"'";
		List<Object> listTotal = getSession().createSQLQuery(listSql).list();
		List<MchValueList> listTotalResult = new ArrayList<MchValueList>();
		for(int i=0;i<listTotal.size();i++){
			Object[] ary = (Object[]) listTotal.get(i);
			MchValueList bo = new MchValueList();
			bo.setMch_number((String) ary[0]);
			bo.setMch_name((String)ary[1]);
			bo.setCk_name((String)ary[2]);
			bo.setCk_id(((Integer) ary[3])+"");
			bo.setCk_quate((Integer)ary[4]+"");
			bo.setSale_amount((Integer)ary[5]+"");
			if(ary[6]!=null){
				bo.setCard_number(((BigDecimal)ary[6]).intValue()+"");
			}else{
				bo.setCard_number("0");
			}
			if(ary[7]!=null){
				bo.setAll_ck_quate(((BigDecimal)ary[7]).intValue()+"");
			}else{
				bo.setAll_ck_quate("0");
			}
			if(ary[8]!=null){
				bo.setAll_sale_amont(((BigDecimal)ary[8]).intValue()+"");
			}else{
				bo.setAll_sale_amont("0");
			}
			if(ary[9]!=null){
				bo.setCard_balance(((BigDecimal)ary[9]).intValue()+"");
			}else{
				bo.setCard_balance("0");
			}
			if(ary[10]!=null){
				bo.setTotal_actrul_amount(((BigDecimal)ary[10]).intValue()+"");
			}else{
				bo.setTotal_actrul_amount("0");
			}
			bo.setCreate_date(date);
			listTotalResult.add(bo);
		}
		
		return listTotalResult;
	}

	@Override
	public void setMchValueList(MchValueList mchValueList) {
		// TODO Auto-generated method stub
		super.save(mchValueList);
	}

	@Override
	public IncomeAnalysisConfig getIncomeAnalysisConfig() {
		// TODO Auto-generated method stub
		String hql = " from IncomeAnalysisConfig";
		List<IncomeAnalysisConfig> list = super.find(hql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void saveIncomeAnalysisConfig(IncomeAnalysisConfig config) {
		// TODO Auto-generated method stub
		String hql = " from IncomeAnalysisConfig";
		List<IncomeAnalysisConfig> list = super.find(hql);
		if(list!=null&&list.size()>0){
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String upSql = " update IncomeAnalysisConfig set ranges="+config.getRanges()+""
					+ ",update_date='"+df.format(config.getUpdate_date())+"',"
					+ " update_person='"+config.getUpdate_person()+"'";
			Query query = getSession().createQuery(upSql);
			query.executeUpdate();
		}else{
			config.setUpdate_date(null);
			config.setUpdate_person(null);
			super.save(config);
		}
		
	}


	@Override
	public Page reChangeList(OnReChangeFilter filter) {
		// TODO Auto-generated method stub
		String sql = " from OnAccountsBill m ";
		List listParamter = new ArrayList();
		String condtion = " (source_id='6' or source_id='7') ";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " left(m.create_date,10) >= ? ";
				listParamter.add(filter.getMobilePhone());
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " left(m.create_date,10) <= ? ";
				listParamter.add(filter.getEndTime());
			}
			if(filter.getMobilePhone()!=null&&!"".equals(filter.getMobilePhone())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mobile = ? ";
				listParamter.add(filter.getMobilePhone().trim());
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		sql = sql+condtion+" order by m.create_date desc";
		return super.pagedQuery(sql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}


	@Override
	public void reChange(OnReChange onReChange) throws Exception{
		// TODO Auto-generated method stub
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "from OnAccounts where user_id=("
				+ "select user_id from OnUser where mobile_phone='"+onReChange.getMobile().trim()+"')";
		
		List<OnAccounts> list = super.find(sql);
		if(list!=null&&list.size()>0){
			Session  session = getSession();
			Transaction  tx = session.beginTransaction();
			OnAccounts accounts = list.get(0);
			OnAccountsBill bill = new OnAccountsBill();
			bill.setAccounts_id(accounts.getAccounts_id());
			bill.setAmount(onReChange.getAmount());
			
			String hql = "";
			if(onReChange.getType().equals("2")){
				//系统扣款
				bill.setAccounts_balance(accounts.getAccounts_balance()-onReChange.getAmount());
				bill.setType("1");
				bill.setSource_id("7");
				hql = " update OnAccounts set accounts_balance='"+
						(accounts.getAccounts_balance()-onReChange.getAmount())+"'"
								+ " where accounts_id='"+accounts.getAccounts_id()+"'";
			}else{
				//微信退款充值
				bill.setAccounts_balance(accounts.getAccounts_balance()+onReChange.getAmount());
				bill.setType("0");
				bill.setSource_id("6");
				hql = " update OnAccounts set accounts_balance='"+
						(accounts.getAccounts_balance()+onReChange.getAmount())+"'"
								+ " where accounts_id='"+accounts.getAccounts_id()+"'";
			}
			bill.setRemark(onReChange.getRemark());
			bill.setCreate_date(onReChange.getCreate_date());
			bill.setCreate_person(onReChange.getCreate_person());
			Query query3 = session.createQuery(hql);
			query3.executeUpdate();
			session.save(bill);
			tx.commit();
		}else{
			throw new Exception("用户手机号错误，请核对后重试！");
		}
		
		
	}

	
}
