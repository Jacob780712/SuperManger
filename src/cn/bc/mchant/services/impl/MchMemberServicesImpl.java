package cn.bc.mchant.services.impl;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.bc.common.support.page.Page;
import cn.bc.mchant.dao.MchMemberDao;
import cn.bc.mchant.po.Goods;
import cn.bc.mchant.po.MchCourtesyRef;
import cn.bc.mchant.po.MchMerchant;
import cn.bc.mchant.services.MchMemberServices;
import cn.bc.mchant.vo.GoodsFilter;
import cn.bc.mchant.vo.MchCourtesyRefFilter;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.CoAccountBill;
import cn.bc.settlement.po.PaymentInfo;
import cn.bc.settlement.po.StmMchDetail;
import cn.bc.settlement.po.StmMerchant;
import cn.bc.settlement.services.SettlementService;
import cn.bc.settlement.vo.StmMchDetailFilter;
import cn.bc.settlement.vo.StmWechatFilter;

public class MchMemberServicesImpl implements MchMemberServices {
	MchMemberDao mchMemberDao;

	public void setMchMemberDao(MchMemberDao mchMemberDao) {
		this.mchMemberDao = mchMemberDao;
	}

	@Override
	public Page goodsList(GoodsFilter filter) {
		// TODO Auto-generated method stub
		return mchMemberDao.goodsList(filter);
	}

	@Override
	public void addGoods(Goods goods) {
		// TODO Auto-generated method stub
		mchMemberDao.addGoods(goods);
	}

	@Override
	public void delGoods(int id) {
		// TODO Auto-generated method stub
		mchMemberDao.delGoods(id);
	}

	@Override
	public void updateGoods(Goods goods) {
		// TODO Auto-generated method stub
		mchMemberDao.updateGoods(goods);
	}

	@Override
	public Goods detail(int id) {
		// TODO Auto-generated method stub
		return mchMemberDao.detail(id);
	}

	@Override
	public String goodsNumber() throws Exception {
		// TODO Auto-generated method stub
		return mchMemberDao.goodsNumber() ;
	}

	@Override
	public List<Goods> goodsList() {
		// TODO Auto-generated method stub
		return mchMemberDao.goodsList();
	}

	@Override
	public Page mchantShipList(MchCourtesyRefFilter filter) {
		// TODO Auto-generated method stub
		return mchMemberDao.mchantShipList(filter);
	}

	@Override
	public List<MchCourtesyRef> mchantShipList(String mchNumber,String branchId) {
		// TODO Auto-generated method stub
		return mchMemberDao.mchantShipList(mchNumber,branchId);
	}

	@Override
	public void updateMchShips(List<MchCourtesyRef> list,String mchNumber) {
		// TODO Auto-generated method stub
		if(list.size()>0){
			mchMemberDao.del(mchNumber,list.get(0).getBranch_id().toString());
			for(int i=0;i<list.size();i++){
				mchMemberDao.save(list.get(i));
			}
		}
	}

	@Override
	public void updateMchVipInfo(MchMerchant mchant) {
		// TODO Auto-generated method stub
		mchMemberDao.updateMchVipInfo(mchant);
	}

	@Override
	public void delMchShips(String mchNumber, String branchId) {
		// TODO Auto-generated method stub
		mchMemberDao.del(mchNumber,branchId);
	}

	
}
