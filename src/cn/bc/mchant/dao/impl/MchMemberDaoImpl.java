package cn.bc.mchant.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.common.support.page.Page;
import cn.bc.mchant.dao.MchMemberDao;
import cn.bc.mchant.po.Goods;
import cn.bc.mchant.po.MchCourtesyRef;
import cn.bc.mchant.po.MchMerchant;
import cn.bc.mchant.vo.GoodsFilter;
import cn.bc.mchant.vo.MchCourtesyRefFilter;

public class MchMemberDaoImpl extends BaseHibernateDao implements MchMemberDao {
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	@Override
	public Page goodsList(GoodsFilter filter) {
		// TODO Auto-generated method stub
		String sql = " from Goods m ";
		List listParamter = new ArrayList();
		String condtion = "";
		if(filter!=null){
			if(filter.getGoods_name()!=null&&!"".equals(filter.getGoods_name())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.goods_name like '%"+filter.getGoods_name().trim()+"%' ";
			}
			if(filter.getGoods_number()!=null&&!"".equals(filter.getGoods_number())){
				if (condtion.length() > 0)
					condtion = condtion + " and ";
				condtion = condtion + " m.goods_number ='"+filter.getGoods_number().trim()+"' ";
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
	public void addGoods(Goods goods) {
		// TODO Auto-generated method stub
		super.save(goods);
		
	}

	@Override
	public void delGoods(int id) {
		// TODO Auto-generated method stub
		String hql = " update Goods set status='9' where id='"+id+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public void updateGoods(Goods goods) {
		// TODO Auto-generated method stub
		String hql = " update Goods set goods_name='"+goods.getGoods_name()+"',"+
				" goods_introd='"+goods.getGoods_introd()+"',"+
				" sale_price='"+goods.getSale_price()+"',"+
				" orig_price='"+goods.getOrig_price()+"',"+
				" status='"+goods.getStatus()+"',"+
				" update_date='"+sdf.format(goods.getUpdate_date())+"',"+
				" update_person='"+goods.getUpdate_person()+"'"+
				" where goods_number='"+goods.getGoods_number()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public Goods detail(int id) {
		// TODO Auto-generated method stub
		String sql = " from Goods m  where m.id='"+id+"'";
		List<Goods> list = super.find(sql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String goodsNumber() throws Exception {
		// TODO Auto-generated method stub
		String hql = "SELECT nextval('seq_goods_number')";
		SQLQuery query = this.getSession().createSQLQuery(hql);  
	    String str = query.uniqueResult().toString();
		if(str!=null&&str.length()>0){
			return str;
		}else{
			throw new Exception("获取礼遇编号报错，请联系管理员");
		}
	}

	@Override
	public List<Goods> goodsList() {
		// TODO Auto-generated method stub
		String sql = " from Goods";
		return super.find(sql);
	}

	@Override
	public Page mchantShipList(MchCourtesyRefFilter filter) {
		// TODO Auto-generated method stub
		String hql = " from MchCourtesyRef m where m.mch_number=? ";
		hql = hql +" and m.status='0' and branch_id='"+filter.getBranchId()+"' order by m.create_date desc";
		List<String> listParamter = new ArrayList<String>();
		listParamter.add(filter.getMch_number());
		return super.pagedQuery(hql.toString(), filter.getPageNo(), filter.getPageSize(), listParamter.toArray());
	}

	@Override
	public List<MchCourtesyRef> mchantShipList(String mchNumber,String branchId) {
		// TODO Auto-generated method stub
		String hql = " from MchCourtesyRef m where m.mch_number= '"+mchNumber+"'"
				+ "and m.status='0' and branch_id='"+branchId+"' order by m.create_date desc";
		return super.find(hql);
	}

	@Override
	public void del(String mchNumber,String branchId) {
		// TODO Auto-generated method stub
		String hql = " delete from MchCourtesyRef m where m.mch_number='"+mchNumber.trim()+"' "
				+ "and branch_id='"+branchId+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
		
	}

	@Override
	public void save(MchCourtesyRef ref) {
		// TODO Auto-generated method stub
		super.save(ref);
	}

	@Override
	public void updateMchVipInfo(MchMerchant mchant) {
		// TODO Auto-generated method stub
		String hql = " update MchMerchant set vip_brief='"+mchant.getVip_brief()+"',"+
					 " vip_introd='"+mchant.getVip_introd()+"'"+
					 " where mch_number='"+mchant.getMch_number()+"'";
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}


}
