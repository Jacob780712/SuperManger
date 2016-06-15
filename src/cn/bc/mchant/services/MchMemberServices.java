package cn.bc.mchant.services;

import java.util.List;

import cn.bc.common.support.page.Page;
import cn.bc.mchant.po.Goods;
import cn.bc.mchant.po.MchCourtesyRef;
import cn.bc.mchant.po.MchMerchant;
import cn.bc.mchant.vo.GoodsFilter;
import cn.bc.mchant.vo.MchCourtesyRefFilter;

public interface MchMemberServices {
	public Page goodsList(GoodsFilter filter);
	public List<Goods> goodsList();
	public void addGoods(Goods goods);
	public void delGoods(int id);
	public void updateGoods(Goods goods);
	public Goods detail(int id);
	public String goodsNumber() throws Exception;
	public Page mchantShipList(MchCourtesyRefFilter filter);
	public List<MchCourtesyRef> mchantShipList(String mchNumber, String branchId);
	public void updateMchShips(List<MchCourtesyRef> list, String mchNumber);
	public void updateMchVipInfo(MchMerchant mchant);
	public void delMchShips(String mchNumber, String branchId);
}	
