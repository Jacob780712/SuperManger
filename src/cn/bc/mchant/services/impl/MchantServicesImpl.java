package cn.bc.mchant.services.impl;


import java.util.List;

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
import cn.bc.mchant.services.MchantServices;
import cn.bc.mchant.vo.CityFilter;
import cn.bc.mchant.vo.MchantFilter;

public class MchantServicesImpl implements MchantServices{
	MchantDao mchantDao;
	
	public void setMchantDao(MchantDao mchantDao) {
		this.mchantDao = mchantDao;
	}

	@Override
	public Page MchantList(MchantFilter filter) {
		return mchantDao.MchantList(filter);
	}

	@Override
	public List<MchGroups> MchGroupList() {
		return mchantDao.MchGroupList();
	}

	@Override
	public void saveMchant(MchMerchant mchant) {
		mchantDao.saveMchant(mchant);
	}

	@Override
	public void saveMchPic(MchPic mchPic) {
		mchantDao.saveMchPic(mchPic);
	}

	@Override
	public void saveMchCityRef(MchCityRef mchCityRef, String citys) {
		String[] strs = citys.split(",");
		for(int i=0;i<strs.length;i++){
			mchCityRef.setCity_id(Integer.valueOf(strs[i]));
			mchantDao.saveMchCityRef(mchCityRef);
		}
		
	}

	@Override
	public MchMerchant isMchantExist(String mchantNo) {
		return mchantDao.isMchantExist(mchantNo);
	}

	@Override
	public boolean isBranch(String mchantNo,String branchName) {
		return mchantDao.isBranch(mchantNo,branchName);
	}

	@Override
	public int saveMchBranch(MchMerchantBranch mchMerchantBranch) {
		return mchantDao.saveMchBranch(mchMerchantBranch);
	}

	@Override
	public void saveMchCardKinds(MchCardKinds mchCardKinds) throws Exception {
		if(mchantDao.loadMchCardKinds(mchCardKinds)){
			throw new Exception("储值卡种重复，请至商户储值卡类型修改处修改");
		}else{
			mchantDao.saveMchCardKinds(mchCardKinds);
		}
	}

	@Override
	public void saveMchAccountNumber(MchAccountNumber mchAccountNumber) throws Exception{
		mchantDao.saveMchAccountNumber(mchAccountNumber);
	}

	@Override
	public void updateMchMerchant(String mchantNo) {
		mchantDao.updateMchMerchant(mchantNo);
	}

	@Override
	public void mchantDel(String mchantNo) {
		mchantDao.mchantDel(mchantNo);
	}

	@Override
	public MchMerchant MchMerchantDetail(String mchantNo) {
		return mchantDao.MchMerchantDetail(mchantNo);
	}

	@Override
	public List<MchPic> MchPicDetail(String mchantNo) {
		return mchantDao.MchPicDetail(mchantNo);
	}

	@Override
	public List<FndCitys> MchCityRefDetail(String mchantNo) {
		return mchantDao.MchCityRefDetail(mchantNo);
	}

	@Override
	public List<MchMerchantBranch> MchMerchantBranchDetail(String mchantNo) {
		return mchantDao.MchMerchantBranchDetail(mchantNo);
	}

	@Override
	public List<MchAccountNumber> MchAccountNumberDetail(String mchantNo) {
		return mchantDao.MchAccountNumberDetail(mchantNo);
	}

	@Override
	public List<MchCardKinds> MchCardKindsDetail(String mchantNo) {
		return mchantDao.MchCardKindsDetail(mchantNo);
	}

	@Override
	public void updateMchant(MchMerchant mchant) {
		mchantDao.updateMchant(mchant);
	}

	@Override
	public void updateMchPic(MchPic mpic) {
		mchantDao.updateMchPic(mpic);
	}

	@Override
	public void saveMchCityRef(MchCityRef mchCityRef) {
		mchantDao.saveMchCityRef(mchCityRef);
		
	}

	@Override
	public void updateMchMerchantBranch(MchMerchantBranch branch) {
		mchantDao.updateMchMerchantBranch(branch);
	}

	@Override
	public boolean cityRefIsExits(MchCityRef mchCityRef) {
		return mchantDao.cityRefIsExits(mchCityRef);
	}

	@Override
	public void deleteStore(MchMerchantBranch branch) {
		mchantDao.deleteStore(branch);
	}

	@Override
	public boolean accoutnIsExits(MchAccountNumber account) {
		return mchantDao.accoutnIsExits(account);
	}

	@Override
	public void updateCard(MchCardKinds mchCardKinds) {
		mchantDao.updateCard(mchCardKinds);
	}

	@Override
	public void saveAllot(MchBackAllot mchBackAllot) {
		mchantDao.saveAllot(mchBackAllot);
	}

	@Override
	public List<MchBackAllot> MchAllotDetail(String mchantNo) {
		return mchantDao.MchAllotDetail(mchantNo);
	}

	@Override
	public void updateAllot(MchBackAllot mchBackAllot) {
		mchantDao.updateAllot(mchBackAllot);
	}

	@Override
	public void saveMchStmConfig(MchStmConfig config) {
		mchantDao.saveMchStmConfig(config);
	}

	@Override
	public void delStore(String mchantNo) {
		mchantDao.delStore(mchantNo);
	}

	@Override
	public void delMchAccount(String mchantNo) {
		mchantDao.delMchAccount(mchantNo);
	}

	@Override
	public void delCard(String mchantNo) {
		mchantDao.delCard(mchantNo);
	}

	@Override
	public void delAllot(String mchantNo) {
		mchantDao.delAllot(mchantNo);
	}

	@Override
	public void delStmConfig(String mchantNo) {
		mchantDao.delStmConfig(mchantNo);
	}

	@Override
	public void delMpic(String mchantNo) {
		mchantDao.delMpic(mchantNo);
	}

	@Override
	public void delMchCity(String mchantNo) {
		mchantDao.delMchCity(mchantNo);
	}

	@Override
	public MchStmConfig detailMchStmConfig(String mchantNo) {
		return mchantDao.detailMchStmConfig(mchantNo);
	}

	@Override
	public void updateMchStmConfig(MchStmConfig stmConfig) {
		mchantDao.updateMchStmConfig(stmConfig);
	}

	@Override
	public void updateMchAccountStatus(MchAccountNumber account) {
		mchantDao.updateMchAccountStatus(account);
	}

	@Override
	public void setMchOpenType(String mchantNo,String type_open) {
		mchantDao.setMchOpenType(mchantNo,type_open);
	}
	
	@Override
	public void setMchOpenPurchase(MchOpenPurchase op){
		mchantDao.setMchOpenPurchase(op);
	}

	@Override
	public void updateMchOpenPurchase(MchOpenPurchase op) {
		mchantDao.updateMchOpenPurchase(op);
	}

	@Override
	public MchOpenPurchase findMchOpenPurchase(String mchantNo) {
		return mchantDao.findMchOpenPurchase(mchantNo);
	}

	@Override
	public void addUpdateMchant(MchMerchant mchant) throws Exception{
		mchantDao.addUpdateMchant(mchant);
	}

	@Override
	public MchCardKinds findMchCardKinds(String ck_id) {
		// TODO Auto-generated method stub
		return mchantDao.findMchCardKinds(ck_id);
	}

	@Override
	public Page MchantBrandList(MchantFilter filter) {
		// TODO Auto-generated method stub
		return mchantDao.MchantBrandList(filter);
	}

	@Override
	public void saveBrand(MchBrand mchb) {
		// TODO Auto-generated method stub
		mchantDao.saveBrand(mchb);
	}

	@Override
	public void delBrand(String mchNumber) {
		// TODO Auto-generated method stub
		mchantDao.delBrand(mchNumber);
	}

	@Override
	public MchBrand MchantBrand(String mchNumber) {
		// TODO Auto-generated method stub
		return mchantDao.MchantBrand(mchNumber);
	}

	@Override
	public List<MchMerchant> MchMerchantList() {
		// TODO Auto-generated method stub
		return mchantDao.MchMerchantList();
	}

	@Override
	public MchMerchantBranch MchMerchantBranchDetail(String mchantNo,
			String branchId) {
		// TODO Auto-generated method stub
		return mchantDao.MchMerchantBranchDetail(mchantNo, branchId);
	}

	@Override
	public List<MchAccountNumber> MchAccountNumberDetail(String mchantNo,
			String branchId) {
		// TODO Auto-generated method stub
		return mchantDao.MchAccountNumberDetail(mchantNo,branchId);
	}

	@Override
	public List<MchPic> MchPicDetail(String mchantNo, String branchId) {
		// TODO Auto-generated method stub
		return mchantDao.MchPicDetail(mchantNo, branchId);
	}

	@Override
	public void storeDelete(String mchantNo, String branchId) {
		// TODO Auto-generated method stub
		mchantDao.storeDelete(mchantNo, branchId);
	}

	@Override
	public void udpateStoreAccount(MchAccountNumber account) {
		// TODO Auto-generated method stub
		mchantDao.udpateStoreAccount(account);
	}

	@Override
	public void delStorePic(String mchNumber, String branchId) {
		// TODO Auto-generated method stub
		mchantDao.delStorePic(mchNumber, branchId);
	}

	@Override
	public void updateStoreAccount(MchAccountNumber account) {
		// TODO Auto-generated method stub
		mchantDao.updateStoreAccount(account);
	}

	@Override
	public Page cityList(CityFilter filter) {
		// TODO Auto-generated method stub
		return mchantDao.cityList(filter);
	}

	@Override
	public void changeCity(String cityId,String flag) {
		// TODO Auto-generated method stub
		mchantDao.changeCity(cityId,flag);
	}
}
