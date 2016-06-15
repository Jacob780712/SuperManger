package cn.bc.mchant.services;

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


public interface MchantServices {
	//商户列表
	public Page MchantList(MchantFilter filter);
	public List<MchMerchant> MchMerchantList();
	//商户类型列表
	public List<MchGroups> MchGroupList();
	//检查商户是否存在
	public MchMerchant isMchantExist(String mchantNo);
	//保存商户基本信息
	public void saveMchant(MchMerchant mchant);
	//商户存在时，认为更新商户
	public void addUpdateMchant(MchMerchant mchant) throws Exception;
	//保存商户图片
	public void saveMchPic(MchPic mchPic);
	//保存商户对应城市
	public void saveMchCityRef(MchCityRef mchCityRef, String citys);
	//保存商户对应城市
	public void saveMchCityRef(MchCityRef mchCityRef);
	//检查门店是否存在
	public boolean isBranch(String mchantNo, String branchName);
	//保存商户门店信息
	public int saveMchBranch(MchMerchantBranch mchMerchantBranch);
	//保存商户储值卡信息
	public void saveMchCardKinds(MchCardKinds mchCardKinds)throws Exception;
	//保存商户账号信息
	public void saveMchAccountNumber(MchAccountNumber mchAccountNumber) throws Exception;
	//保存商户B类消费分配信息
	public void saveAllot(MchBackAllot mchBackAllot);
	//保存商户结算配置信息
	public void saveMchStmConfig(MchStmConfig config);
	//更新商户状态为上线
	public void updateMchMerchant(String mchantNo);
	//商户删除
	public void mchantDel(String mchantNo);
	//门店删除
	public void storeDelete(String mchantNo, String branchId);
	//查询商户基本信息
	public MchMerchant MchMerchantDetail(String mchantNo);
	//查询商户图片信息
	public List<MchPic> MchPicDetail(String mchantNo);
	public List<MchPic> MchPicDetail(String mchantNo, String branchId);
	//查询商户对应的城市
	public List<FndCitys> MchCityRefDetail(String mchantNo);
	//查询商户门店信息
	public List<MchMerchantBranch> MchMerchantBranchDetail(String mchantNo);
	public MchMerchantBranch MchMerchantBranchDetail(String mchantNo, String branchId);
	//关闭商户账号
	public void updateMchAccountStatus(MchAccountNumber account);
	//修改门店账户
	public void udpateStoreAccount(MchAccountNumber account);
	//删除门店对应图片
	public void delStorePic(String mchNumber, String branchId);
	//修改门店账户的状态
	public void updateStoreAccount(MchAccountNumber account);
	//查询商户账户详情
	public List<MchAccountNumber> MchAccountNumberDetail(String mchantNo);
	//门店商户账户详情
	public List<MchAccountNumber> MchAccountNumberDetail(String mchantNo, String branchId);
	//查询商户B类消费分配详情
	public List<MchBackAllot> MchAllotDetail(String mchantNo);
	//查询商户储值卡信息
	public List<MchCardKinds> MchCardKindsDetail(String mchantNo);
	//商户基本信息修改提交
	public void updateMchant(MchMerchant mchant);
	//商户图片修改提交
	public void updateMchPic(MchPic mpic);
	//检查该城市是否已添加
	public boolean cityRefIsExits(MchCityRef mchCityRef);
	//修改门店
	public void updateMchMerchantBranch(MchMerchantBranch branch);
	//商户门店删除
	public void deleteStore(MchMerchantBranch branch);
	//判断商户账号是否存在
	public boolean accoutnIsExits(MchAccountNumber account);
	//商户储值卡修改提交
	public void updateCard(MchCardKinds mchCardKinds);
	//B类消费分配修改提交
	public void updateAllot(MchBackAllot mchBackAllot);
	//添加门店时先删除门店
	public void delStore(String mchantNo);
	//添加商户账号时先删除商户账号
	public void delMchAccount(String mchantNo);
	//添加储值卡类型时先删除储值卡类型
	public void delCard(String mchantNo);
	//添加B类消费分配时先删除B类消费分配
	public void delAllot(String mchantNo);
	//添加结算信息时先删除结算信息
	public void delStmConfig(String mchantNo);
	//添加商户信息时先删除商户图片
	public void delMpic(String mchantNo);
	//添加结算信息时先删除商户城市
	public void delMchCity(String mchantNo);
	//商户结算信息详情
	public MchStmConfig detailMchStmConfig(String mchantNo);
	//更新商户结算信息
	public void updateMchStmConfig(MchStmConfig stmConfig);
	//商户添加购卡上线时，设置商户是否开通时购卡
	public void setMchOpenType(String mchantNo, String type_open);
	//商户开通时设置购卡信息
	public void setMchOpenPurchase(MchOpenPurchase op);
	//商户开通时购卡结果
	public void updateMchOpenPurchase(MchOpenPurchase op);
	//查询商户开通时首次购卡信息
	public MchOpenPurchase findMchOpenPurchase(String mchantNo);
	//根据储值卡种ID查询储值卡的信息
	public MchCardKinds findMchCardKinds(String ck_id);
	//商户品牌信息列表
	public Page MchantBrandList(MchantFilter filter);
	//商户品牌信息保存
	public void saveBrand(MchBrand mchb);
	//商户品牌信息删除
	public void delBrand(String mchNumber);
	//查看品牌信息
	public MchBrand MchantBrand(String mchNumber);
	//查看城市列表
	public Page cityList(CityFilter filter);
	//修改城市状态
	public void changeCity(String cityId, String flag);
	
}
