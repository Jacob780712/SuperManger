package cn.bc.mchant.dao;


import java.util.List;

import cn.bc.common.support.page.Page;
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

public interface MchantDao {
	
	public Page MchantList(MchantFilter filter);
	public List<MchMerchant> MchMerchantList();
	public List<MchGroups> MchGroupList();
	
	public MchMerchant isMchantExist(String mchantNo);
	
	public void addUpdateMchant(MchMerchant mchant) throws Exception;
	public void udpateStoreAccount(MchAccountNumber account);
	public void saveMchant(MchMerchant mchant);
	
	public void saveMchPic(MchPic mchPic);
	
	public void saveMchCityRef(MchCityRef mchCityRef);
	
	public boolean isBranch(String mchantNo, String branchName);
	
	public int saveMchBranch(MchMerchantBranch mchMerchantBranch);
	
	public void storeDelete(String mchantNo, String branchId);
	
	public boolean loadMchCardKinds(MchCardKinds mchCardKinds);
	
	public void saveMchCardKinds(MchCardKinds mchCardKinds);
	
	public void saveMchAccountNumber(MchAccountNumber mchAccountNumber) throws Exception;
	
	public List<MchBackAllot> MchAllotDetail(String mchantNo);
	
	public void saveAllot(MchBackAllot mchBackAllot);
	
	public void saveMchStmConfig(MchStmConfig config);
	
	public void updateMchMerchant(String mchantNo);
	
	public void mchantDel(String mchantNo);
	
	public MchMerchant MchMerchantDetail(String mchantNo);
	
	public List<MchPic> MchPicDetail(String mchantNo);
	public List<MchPic> MchPicDetail(String mchantNo, String branchId);
	
	public List<FndCitys> MchCityRefDetail(String mchantNo);
	
	public List<MchMerchantBranch> MchMerchantBranchDetail(String mchantNo);
	public MchMerchantBranch MchMerchantBranchDetail(String mchantNo, String branchId);
	
	public List<MchAccountNumber> MchAccountNumberDetail(String mchantNo);
	public List<MchAccountNumber> MchAccountNumberDetail(String mchantNo, String branchId);
	
	public List<MchCardKinds> MchCardKindsDetail(String mchantNo);
	
	public void updateMchant(MchMerchant mchant);
	
	public void updateMchPic(MchPic mpic);
	
	public void updateMchMerchantBranch(MchMerchantBranch branch);
	
	public boolean cityRefIsExits(MchCityRef mchCityRef);
	
	public void deleteStore(MchMerchantBranch branch);
	
	public boolean accoutnIsExits(MchAccountNumber account);
	
	public void updateMchAccountStatus(MchAccountNumber account);
	
	public void updateCard(MchCardKinds mchCardKinds);
	
	public void updateAllot(MchBackAllot mchBackAllot);
	
	public void delMpic(String mchantNo);
	
	public void delMchCity(String mchantNo);
	
	public void delStore(String mchantNo);
	
	public void delMchAccount(String mchantNo);
	
	public void delCard(String mchantNo);
	
	public void delAllot(String mchantNo);
	
	public void delStmConfig(String mchantNo);
	
	public MchStmConfig detailMchStmConfig(String mchantNo);
	
	public void updateMchStmConfig(MchStmConfig stmConfig);
	
	public void setMchOpenType(String mchantNo, String type_open);
	
	public void setMchOpenPurchase(MchOpenPurchase op);
	
	public void updateMchOpenPurchase(MchOpenPurchase op);
	
	public MchOpenPurchase findMchOpenPurchase(String mchantNo);
	
	public MchCardKinds findMchCardKinds(String ck_id);
	
	public Page MchantBrandList(MchantFilter filter);
	
	public void saveBrand(MchBrand mchb);
	
	public void delBrand(String mchNumber);
	
	public MchBrand MchantBrand(String mchNumber);
	
	public void delStorePic(String mchNumber, String branchId);
	
	public void updateStoreAccount(MchAccountNumber account);
	
	public Page cityList(CityFilter filter);
	
	public void changeCity(String cityId, String flag);
}
