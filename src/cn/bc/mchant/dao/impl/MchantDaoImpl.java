package cn.bc.mchant.dao.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.common.support.page.Page;
import cn.bc.mchant.dao.MchantDao;
import cn.bc.mchant.po.FndCitys;
import cn.bc.mchant.po.MchAccountNumber;
import cn.bc.mchant.po.MchBackAllot;
import cn.bc.mchant.po.MchBrand;
import cn.bc.mchant.po.MchCardKinds;
import cn.bc.mchant.po.MchCityRef;
import cn.bc.mchant.po.MchGroups;
import cn.bc.mchant.po.MchMerchant;
import cn.bc.mchant.po.MchMerchantBranch;
import cn.bc.mchant.po.MchOpenPurchase;
import cn.bc.mchant.po.MchPic;
import cn.bc.mchant.po.MchStmConfig;
import cn.bc.mchant.vo.CityFilter;
import cn.bc.mchant.vo.MchantFilter;

public class MchantDaoImpl extends BaseHibernateDao<MchMerchant> implements MchantDao{
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	@Override
	public Page MchantList(MchantFilter filter) {
		String sql = " from MchMerchant m ";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getMchantName()!=null&&!"".equals(filter.getMchantName())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name = ? ";
				listParamter.add(filter.getMchantName().trim());
			}
			if(filter.getMchantNo()!=null&&!"".equals(filter.getMchantNo())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_number = ? ";
				listParamter.add(filter.getMchantNo().trim());
			}
			if(filter.getStatus()!=null&&!"".equals(filter.getStatus())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.status = ? ";
				listParamter.add(filter.getStatus().trim());
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		sql = sql+condtion+" order by create_date desc";
		return super.pagedQuery(sql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}

	@Override
	public List<MchGroups> MchGroupList() {
		String hql = " from MchGroups m ";
		List<MchGroups> list = super.find(hql);
		return list;
	}

	@Override
	public void saveMchant(MchMerchant mchant) {
		super.save(mchant);
	}

	@Override
	public void saveMchPic(MchPic mchPic) {
		super.save(mchPic);
	}

	@Override
	public void saveMchCityRef(MchCityRef mchCityRef) {
		super.save(mchCityRef);
	}

	@Override
	public MchMerchant isMchantExist(String mchantNo) {
		String hql = " from MchMerchant m where m.mch_number='"+mchantNo+"'";
		List<MchMerchant> list = super.find(hql);
		if(list!=null&&list.size()>0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public boolean isBranch(String mchantNo,String branchName) {
		String hql = " from MchMerchantBranch m where m.branch_name='"+branchName+"' and mch_number='"+mchantNo+"'";
		List<MchGroups> list = super.find(hql);
		if(list.size()>0)
			return true;
		else
			return false;
	}

	@Override
	public int saveMchBranch(MchMerchantBranch mchMerchantBranch) {
		super.save(mchMerchantBranch);
		return mchMerchantBranch.getId();
		
	}

	@Override
	public void saveMchCardKinds(MchCardKinds mchCardKinds) {
		super.save(mchCardKinds);
	}

	@Override
	public void saveMchAccountNumber(MchAccountNumber mchAccountNumber) throws Exception {
		String hql = " from MchAccountNumber m where m.account_number='"+mchAccountNumber.getAccount_number().trim()+"'";
		List<MchAccountNumber> list = super.find(hql);
		if(list!=null&&list.size()>0){
			throw new Exception("商户账号名重复，请点击返回重新设置");
		}else{
			super.save(mchAccountNumber);
		}
	}

	@Override
	public void updateMchMerchant(String mchantNo) {
		String hql = "update MchMerchant set status='0' where mch_number='"+mchantNo+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void mchantDel(String mchantNo) {
		Session  session = getSession();
		Transaction  tx = session.beginTransaction();
		String hql = "update MchMerchant set status='9' ,sale_card_status='1'  where mch_number='"+mchantNo+"'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
		String hql2 = "update MchCardKinds set status='1' where mch_number='"+mchantNo+"'";
		Query query2 = session.createQuery(hql2);
		query2.executeUpdate();
		String hql3 = "update MchMerchantBranch set status='9' where mch_number='"+mchantNo+"'";
		Query query3 = session.createQuery(hql3);
		query3.executeUpdate();
		tx.commit();
	}

	@Override
	public MchMerchant MchMerchantDetail(String mchantNo) {
		String hql = " from MchMerchant where mch_number='"+mchantNo+"'";
		List<MchMerchant> list = super.find(hql);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<MchPic> MchPicDetail(String mchantNo) {
		String hql = " from MchPic where mch_number='"+mchantNo+"' and source='0' and branch_id='0' order by id";
		List<MchPic> list = super.find(hql);
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<FndCitys> MchCityRefDetail(String mchantNo) {
		String hql = " from MchCityRef where mch_number='"+mchantNo+"'";
		List<MchCityRef> list = super.find(hql);
		String cityId = "";
		for(int i=0;i<list.size();i++){
			if(i==list.size()-1){
				cityId = cityId + list.get(i).getCity_id();
			}else{
				cityId = cityId + list.get(i).getCity_id() + ",";
			}
			
		}
		String hql2 = " from FndCitys where city_id in ("+cityId+")";
		System.out.println(hql2);
		List<FndCitys> list2 = super.find(hql2);
		return list2;
	}

	@Override
	public List<MchMerchantBranch> MchMerchantBranchDetail(String mchantNo) {
		String hql = " from MchMerchantBranch where mch_number='"+mchantNo+"'";
		return super.find(hql);
	}

	@Override
	public List<MchAccountNumber> MchAccountNumberDetail(String mchantNo) {
		String hql = " from MchAccountNumber where mch_number='"+mchantNo+"' and branch_id='0' and source='0'";
		return super.find(hql);
	}

	@Override
	public List<MchCardKinds> MchCardKindsDetail(String mchantNo) {
		String hql = " from MchCardKinds where mch_number='"+mchantNo+"'";
		return super.find(hql);
	}

	@Override
	public void updateMchant(MchMerchant mchant) {
		Session  session = getSession();
		Transaction  tx = session.beginTransaction();
		
		String hql = "update MchMerchant set update_date='"+sdf.format(mchant.getUpdate_date())+
				"',update_person='"+mchant.getUpdate_person()+"',"+
				" mch_name='"+mchant.getMch_name()+"',";
		if(mchant.getPic_url_logo()!=null&&!"".equals(mchant.getPic_url_logo())){
			hql = hql + " pic_url_logo='"+mchant.getPic_url_logo()+"',";
		}
				
		hql = hql+"mch_group_id='"+mchant.getMch_group_id()+"',"+
				"mch_key_word='"+mchant.getMch_key_word()+"',"+
				"sale_poundage='"+mchant.getSale_poundage()+"',status='"+mchant.getStatus()+"',"+
				"sale_card_status='"+mchant.getSale_card_status()+"',"+
				"capita_consumption='"+mchant.getCapita_consumption()+"',"+
				"use_note='"+mchant.getUse_note()+"',"+
				"mch_min_discount='"+mchant.getMch_min_discount()+"',"+
				"mch_max_discount='"+mchant.getMch_max_discount()+"',"+
				"mch_biz_districts='"+mchant.getMch_biz_districts()+"',"+
				"sale_poundage='"+mchant.getSale_poundage()+"',discount_note='"+mchant.getDiscount_note()+"'"+
				",mch_time_sale='"+mchant.getMch_time_sale()+"',mch_card_range='"+mchant.getMch_card_range()+"'"+
				",mch_more='"+mchant.getMch_more()+"',mch_indus_name='"+mchant.getMch_indus_name()+"'"+
				",mch_oper_per='"+mchant.getMch_oper_per()+"',mch_leg_person='"+mchant.getMch_leg_person()+"'";
		if(mchant.getUpdate_date()!=null){
			hql = hql +",update_date='"+sdf.format(mchant.getUpdate_date())+"'";
		}
		if(mchant.getUpdate_person()!=null){
			hql = hql+",update_person='"+mchant.getUpdate_person()+"'";
		}
		hql = hql + " where mch_number='"+mchant.getMch_number().trim()+"' and id='"+mchant.getId()+"'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
		if(mchant.getStatus().equals("0")){
			//开通
			String branchHql = "update MchMerchantBranch set status='0' where mch_number='"+mchant.getMch_number()+"'";
			Query query2 = session.createQuery(branchHql);
			query2.executeUpdate();
		}
		if(mchant.getStatus().equals("1")){
			//关闭
			String branchHql = "update MchMerchantBranch set status='1' where mch_number='"+mchant.getMch_number()+"'";
			Query query2 = session.createQuery(branchHql);
			query2.executeUpdate();
		}
		
		String cardSql = "update MchCardKinds m set m.mch_name='"+mchant.getMch_name()+"' where m.mch_number='"+mchant.getMch_number()+"'";
		Query query3 = session.createQuery(cardSql);
		query3.executeUpdate();
		
		tx.commit();
	}

	@Override
	public void updateMchPic(MchPic mpic) {
		String hql = "update MchPic set pic_url='"+mpic.getPic_url()+
				"' ,update_date='"+sdf.format(mpic.getUpdate_date())+"',update_person='"+mpic.getUpdate_person()+
				"' where mch_number='"+mpic.getMch_number().trim()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void updateMchMerchantBranch(MchMerchantBranch branch) {
		String hql = "update MchMerchantBranch set city_id='"+branch.getCity_id()+"',"
				+ "branch_name='"+branch.getBranch_name()+"',"
				+ "branch_addr='"+branch.getBranch_addr()+"',"
				+ "telephone='"+branch.getTelephone()+"',"
				+ "longitude='"+branch.getLongitude()+"',"
				+ "latitude='"+branch.getLatitude()+"',"
				+ "status='"+branch.getStatus()+"',"
				+ "biz_districts='"+branch.getBiz_districts()+"',"
				+ "use_note='"+branch.getUse_note()+"' where "
				+ " mch_number='"+branch.getMch_number()+"' and id='"+branch.getId()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public boolean cityRefIsExits(MchCityRef mchCityRef) {
		String hql = "select mch_number from MchCityRef where city_id='"+mchCityRef.getCity_id()+"' "+
		"and mch_number='"+mchCityRef.getMch_number()+"'";
		List<String> list= super.find(hql);
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void deleteStore(MchMerchantBranch branch) {
		String hql = "delete from MchMerchantBranch where id='"+branch.getId()+
				"' and mch_number='"+branch.getMch_number()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public boolean accoutnIsExits(MchAccountNumber account) {
		String hql = "select account_number from MchAccountNumber where mch_number='"+account.getMch_number()+"' "+
				"and account_number='"+account.getAccount_number()+"'";
		List<String> list= super.find(hql);
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void updateCard(MchCardKinds mchCardKinds) {
		String hql = "update MchCardKinds set status='"+mchCardKinds.getStatus()+
				"',update_date='"+sdf.format(mchCardKinds.getUpdate_date())+"', update_person='"+mchCardKinds.getUpdate_person()+"'"+
				" where id='"+mchCardKinds.getId()+"' and mch_number='"+mchCardKinds.getMch_number()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void saveAllot(MchBackAllot mchBackAllot) {
		super.save(mchBackAllot);
	}

	@Override
	public List<MchBackAllot> MchAllotDetail(String mchantNo) {
		String hql = " from MchBackAllot m where m.mch_number='"+mchantNo+"'";
		List<MchBackAllot> list= super.find(hql);
		return list;
	}

	@Override
	public void updateAllot(MchBackAllot mchBackAllot) {
		String hql = " update MchBackAllot set a_allot='"+mchBackAllot.getA_allot()+"',"+
					 " b_allot='"+mchBackAllot.getB_allot()+"',o_allot='"+mchBackAllot.getO_allot()+"',"+
					 " update_date='"+sdf.format(mchBackAllot.getUpdate_date())+"',"+
					 " update_person='"+mchBackAllot.getUpdate_person()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void saveMchStmConfig(MchStmConfig config) {
		super.save(config);
	}

	@Override
	public boolean loadMchCardKinds(MchCardKinds mchCardKinds) {
		String hql = " from MchCardKinds m where m.ck_name='"+mchCardKinds.getCk_name().trim()+"'"+
					 " and m.sales_amount='"+mchCardKinds.getSales_amount()+"'"+
					 " and m.mch_number='"+mchCardKinds.getMch_number().trim()+"'";
		List<MchCardKinds> list = super.find(hql);
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public void delStore(String mchantNo) {
		if(mchantNo!=null&&!"".equals(mchantNo)){
			String hql = "delete from MchMerchantBranch where mch_number='"+mchantNo.trim()+"'";
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
		}
		
	}

	@Override
	public void delMchAccount(String mchantNo) {
		if(mchantNo!=null&&!"".equals(mchantNo)){
			String hql = "delete from MchAccountNumber where mch_number='"+mchantNo.trim()+"'";
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
		}
	}

	@Override
	public void delCard(String mchantNo) {
		if(mchantNo!=null&&!"".equals(mchantNo)){
			String hql = "delete from MchCardKinds where mch_number='"+mchantNo.trim()+"'";
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
		}
	}

	@Override
	public void delAllot(String mchantNo) {
		if(mchantNo!=null&&!"".equals(mchantNo)){
			String hql = "delete from MchBackAllot where mch_number='"+mchantNo.trim()+"'";
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
		}
	}

	@Override
	public void delStmConfig(String mchantNo) {
		if(mchantNo!=null&&!"".equals(mchantNo)){
			String hql = "delete from MchStmConfig where mch_number='"+mchantNo.trim()+"'";
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
		}
	}

	@Override
	public void delMpic(String mchantNo) {
		if(mchantNo!=null&&!"".equals(mchantNo)){
			String hql = "delete from MchPic where mch_number='"+mchantNo.trim()+"' and branch_id='0' and source='0'";
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
		}
	}

	@Override
	public void delMchCity(String mchantNo) {
		if(mchantNo!=null&&!"".equals(mchantNo)){
			String hql = "delete from MchCityRef where mch_number='"+mchantNo.trim()+"'";
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
		}
	}

	@Override
	public MchStmConfig detailMchStmConfig(String mchantNo) {
		String hql = " from MchStmConfig where mch_number='"+mchantNo.trim()+"'";
		List<MchStmConfig> list = super.find(hql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public void updateMchStmConfig(MchStmConfig stmConfig) {
		try {
			super.update(stmConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateMchAccountStatus(MchAccountNumber account) {
		String hql = "update MchAccountNumber set status='"+account.getStatus()+"',"+
					 " update_date='"+sdf.format(account.getUpdate_date())+"',"+
					 " update_person='"+account.getUpdate_person()+"'"+
				     " where mch_number='"+account.getMch_number()+"' "
				     + "and account_number='"+account.getAccount_number()+"' and "
				     + " branch_id='0' and source='0'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void setMchOpenType(String mchantNo,String type_open) {
		String hql = "update MchMerchant set type_open='"+type_open+"' "+
			     " where mch_number='"+mchantNo+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void setMchOpenPurchase(MchOpenPurchase op) {
		super.save(op);
	}

	@Override
	public void updateMchOpenPurchase(MchOpenPurchase op) {
		String hql = "update MchOpenPurchase set status='"+op.getStatus()+"',"+
					 " update_date='"+sdf.format(op.getUpdate_date())+"',"+
					 " update_person='"+op.getUpdate_person()+"'"+
			         " where mch_number='"+op.getMch_number()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public MchOpenPurchase findMchOpenPurchase(String mchantNo) {
		String hql = " from MchOpenPurchase m where m.mch_number='"+mchantNo+"'";
		List<MchOpenPurchase> list = super.find(hql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else
			return null;
	}

	@Override
	public void addUpdateMchant(MchMerchant mchant) throws Exception {
		super.update(mchant);
	}

	@Override
	public MchCardKinds findMchCardKinds(String ck_id) {
		// TODO Auto-generated method stub
		String sql = " from MchCardKinds where id='"+ck_id+"'";
		List<MchCardKinds> list = super.find(sql);
		return list.get(0);
	}

	@Override
	public Page MchantBrandList(MchantFilter filter) {
		String sql = " from MchMerchantBrand m ";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getMchantName()!=null&&!"".equals(filter.getMchantName())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_name = ? ";
				listParamter.add(filter.getMchantName().trim());
			}
			if(filter.getMchantNo()!=null&&!"".equals(filter.getMchantNo())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.mch_number = ? ";
				listParamter.add(filter.getMchantNo().trim());
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		sql = sql+condtion+" order by create_date desc";
		return super.pagedQuery(sql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}

	@Override
	public void saveBrand(MchBrand mchb) {
		// TODO Auto-generated method stub
		String sql = " from MchBrand where mch_number='"+mchb.getMch_number()+"'";
		List<MchBrand> list = super.find(sql);
		if(list!=null&&list.size()>1){
			
		}else{
			super.save(mchb);
		}
	}

	@Override
	public void delBrand(String mchNumber) {
		// TODO Auto-generated method stub
		String hql = " delete from MchBrand where mch_number='"+mchNumber+"'";
		Session  session = getSession();
		Transaction  tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.executeUpdate();
		tx.commit();
		
	}

	@Override
	public MchBrand MchantBrand(String mchNumber) {
		// TODO Auto-generated method stub
		String hql = " from MchBrand m where m.mch_number='"+mchNumber.trim()+"'";
		List<MchBrand> list = super.find(hql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<MchMerchant> MchMerchantList() {
		// TODO Auto-generated method stub
		String sql = " from MchMerchant";
		List<MchMerchant> list = super.find(sql);
		return list;
	}

	@Override
	public MchMerchantBranch MchMerchantBranchDetail(String mchantNo,
			String branchId) {
		// TODO Auto-generated method stub
		String sql = " from MchMerchantBranch where mch_number='"+mchantNo+"'"
				+ " and id='"+branchId+"'";
		List<MchMerchantBranch> list = super.find(sql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<MchAccountNumber> MchAccountNumberDetail(String mchantNo,
			String branchId) {
		// TODO Auto-generated method stub
		String sql = " from MchAccountNumber where mch_number='"+mchantNo+"' "
				+ " and branch_id='"+branchId+"' and source='1'";
		List<MchAccountNumber> list = super.find(sql);
		return list;
	}

	@Override
	public List<MchPic> MchPicDetail(String mchantNo, String branchId) {
		// TODO Auto-generated method stub
		String hql = " from MchPic  where mch_number='"+mchantNo+"' and branch_id='"+branchId+"' and source='1'";
		List<MchPic> list = super.find(hql);
		return list;
	}

	@Override
	public void storeDelete(String mchantNo, String branchId) {
		Session  session = getSession();
		Transaction  tx = session.beginTransaction();
		// TODO Auto-generated method stub
		String hql = " update MchMerchantBranch set status='9' where mch_number='"+mchantNo+"' and id='"+branchId+"'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
		String hql2 = "update MchAccountNumber set status='1' where mch_number='"+mchantNo+"' and branch_id='"+branchId+"'";
		Query query2 = session.createQuery(hql2);
		query2.executeUpdate();
		tx.commit();
	}

	@Override
	public void udpateStoreAccount(MchAccountNumber account) {
		// TODO Auto-generated method stub
		String hql = "update MchAccountNumber set status='"+account.getStatus()+"',"+
				 " update_date='"+sdf.format(account.getUpdate_date())+"',"+
				 " update_person='"+account.getUpdate_person()+"'"+
			     " where mch_number='"+account.getMch_number()+"' and account_number='"+account.getAccount_number()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
		
	}

	@Override
	public void delStorePic(String mchNumber, String branchId) {
		// TODO Auto-generated method stub
		String hql = "delete from MchPic where mch_number='"+mchNumber+"' "
				+ " and branch_id='"+branchId+"' and source='1'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
		
	}

	@Override
	public void updateStoreAccount(MchAccountNumber account) {
		// TODO Auto-generated method stub
		String hql = "update MchAccountNumber set status='"+account.getStatus()+"'"
				+ " where mch_number='"+account.getMch_number()+"'"
				+ " and account_number='"+account.getAccount_number()+"' "
				+ "and source='1' and branch_id='"+account.getBranch_id()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
		
	}

	@Override
	public Page cityList(CityFilter filter) {
		// TODO Auto-generated method stub
		String sql = " from FndCitys m ";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getCityName()!=null&&!"".equals(filter.getCityName())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.city_name like '%"+filter.getCityName()+"%'";
			}
			if(filter.getCityId()!=null&&!"".equals(filter.getCityId())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.city_id ='"+filter.getCityId()+"'";
			}
		}
		if (condtion.length() > 0)
			condtion = " where " + condtion;
		sql = sql+condtion+" order by create_date desc";
		return super.pagedQuery(sql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}

	@Override
	public void changeCity(String cityId,String flag) {
		// TODO Auto-generated method stub
		String hql = "";
		if(flag!=null&&flag.equals("close")){
			//关闭城市
			hql = " update FndCitys set status='1' where city_id='"+cityId+"'";
		}
		if(flag!=null&&flag.equals("open")){
			//开通城市
			hql = " update FndCitys set status='0' where city_id='"+cityId+"'";
		}
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

}
