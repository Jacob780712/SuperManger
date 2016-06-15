package cn.bc.settlement.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.CoAccountBill;
import cn.bc.settlement.dao.SettlementDao;
import cn.bc.settlement.po.StmMchDetail;
import cn.bc.settlement.po.StmMerchant;
import cn.bc.settlement.vo.StmMchDetailFilter;
import cn.bc.settlement.vo.StmWechatFilter;
import cn.bc.util.StringToDate;

public class SettlementDaoImpl extends BaseHibernateDao implements SettlementDao  {
	private String format = "yyyy-MM-dd";
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
	private SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	@Override
	public Page StmWechatList(StmWechatFilter filter) {
		String hql = " from StmWechat m ";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.date >= ? ";
				listParamter.add(StringToDate.toDate(format,filter.getBeginTime().trim()));
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.date <= ? ";
				listParamter.add(StringToDate.toDate(format,filter.getEndTime().trim()));
			}
			if(filter.getStatus()!=null&&!"".equals(filter.getStatus())){
				if(!"2".equals(filter.getStatus())){
					if (condtion.length() > 0)
						condtion = condtion + " and ";
					condtion = condtion + " m.status=? ) ";
					listParamter.add(filter.getStatus().trim());
				}
			}
			if(filter.getBatch_number()!=null&&!"".equals(filter.getBatch_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.batch_number=?  ";
				listParamter.add(filter.getBatch_number());
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by m.date desc";
		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}
	@Override
	public void StmWechatUpdate(List<StmWechatFilter> filter) {
		Date date = new Date();
		Session  session = getSession();
		Transaction  tx = session.beginTransaction();
		try{
		
			String idstr = "";
			for(int i=0;i<filter.size();i++){
				if(i<filter.size()-1){
					idstr = idstr+filter.get(i).getId()+",";
				}else{
					idstr = idstr+filter.get(i).getId();
				}
			}
			String hql = " update StmWechat set status='0',batch_number='"+filter.get(0).getBatch_number()+"',"+
						 " aut_business_fee='"+filter.get(0).getAut_business_fee()+"',"+
						 " aut_settlement_amount='"+filter.get(0).getAut_settlement_amount()+"',"+
						 " update_date='"+sdf.format(filter.get(0).getUpdate_date())+"',update_person='"+filter.get(0).getUpdate_person()+"'"+
			             " where id in("+idstr+")";
			Query query = session.createQuery(hql);
			query.executeUpdate();
			tx.commit();
			session.close();
		}catch(Exception e){
			tx.rollback();
			session.close();
			try {
				throw new Exception("微信结算错误，请联系管理员");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	@Override
	public Page listStmMchDetail(StmMchDetailFilter filter) {
		String hql = " from StmMchDetail m ";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= ? ";
				listParamter.add(StringToDate.toDate(format,filter.getBeginTime().trim()));
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= ? ";
				listParamter.add(StringToDate.toDate(format,filter.getEndTime().trim()));
			}
			if(filter.getStatus()!=null&&!"".equals(filter.getStatus())){
				if(!"2".equals(filter.getStatus())){
					if (condtion.length() > 0)
						condtion = condtion + " and ";
					condtion = condtion + " m.status=?  ";
					listParamter.add(filter.getStatus().trim());
				}
			}
			if(filter.getMch_number()!=null&&!"".equals(filter.getMch_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_number=?  ";
				listParamter.add(filter.getMch_number());
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name=?  ";
				listParamter.add(filter.getMch_name().trim());
			}
			if(filter.getId()>0){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.id=?  ";
				listParamter.add(filter.getId());
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by m.create_date desc";
		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());

	}
	
	//商户结算完成
	@Override
	public void updateStmMchDetail(StmMchDetailFilter filter) {
		Session  session = getSession();
		Transaction  tx = session.beginTransaction();
		try {
			String hql = " update StmMchDetail s set s.status='"+filter.getStatus()+"',s.fkdate='"+filter.getFkdate()+"'"
					+ " where s.id='"+filter.getId()+"'";
			Query query = session.createQuery(hql);
			query.executeUpdate();
			tx.commit();
			session.close();
		} catch (Exception e) {
			tx.rollback();
			try {
				throw new Exception("商户结算错误，请联系管理员");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	@Override
	public void setOpenPuerDate(StmMerchant stm) {
		super.save(stm);
	}
	@Override
	public void setStmMchDetail(StmMchDetail stm) {
		super.save(stm);
	}
	@Override
	public void updateCoAccount(CoAccount co) {
		
	}

	@Override
	public List stmMchDetailList(StmMchDetailFilter filter) {
		String hql = " from StmMchDetail m ";
		String condtion = "";
		if(filter!=null){
			if(filter.getBeginTime()!=null&&!"".equals(filter.getBeginTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date >= '" + StringToDate.toDate(format,filter.getBeginTime().trim()) + "' ";
			}
			if(filter.getEndTime()!=null&&!"".equals(filter.getEndTime())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.create_date <= '" + StringToDate.toDate(format,filter.getEndTime().trim()) +"'";
			}
			if(filter.getStatus()!=null&&!"".equals(filter.getStatus())){
				if(!"2".equals(filter.getStatus())){
					if (condtion.length() > 0)
						condtion = condtion + " and ";
					condtion = condtion + " m.status= '" + filter.getStatus().trim() + "'";
				}
			}
			if(filter.getMch_number()!=null&&!"".equals(filter.getMch_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_number= '" + filter.getMch_number() + "'";
			}
			if(filter.getMch_name()!=null&&!"".equals(filter.getMch_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name= '" + filter.getMch_name().trim() + "'";
			}
			if(filter.getId()>0){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.id= '" + filter.getId() + "'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		hql = hql+condtion+" order by m.create_date desc";
		return super.find(hql);
	}

	@Override
	public void updateCoAccountBill(CoAccountBill coBill) {
		super.save(coBill);
	}

}
