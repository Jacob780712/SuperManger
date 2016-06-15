package cn.bc.mchant.action;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.bc.business.action.CashOutImportAction;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.mchant.po.FndCitys;
import cn.bc.mchant.po.MchAccountNumber;
import cn.bc.mchant.po.MchBackAllot;
import cn.bc.mchant.po.MchCardKinds;
import cn.bc.mchant.po.MchCityRef;
import cn.bc.mchant.po.MchGroups;
import cn.bc.mchant.po.MchMerchant;
import cn.bc.mchant.po.MchMerchantBranch;
import cn.bc.mchant.po.MchPic;
import cn.bc.mchant.po.MchStmConfig;
import cn.bc.mchant.services.MchantServices;
import cn.bc.util.MD5;
import cn.bc.util.UnZip;
import cn.bc.util.UpLoadPic;
import cn.common.security.vo.User;

public class MchantUpdateAction extends BaseAction{
	
	/**
	 * 商户基本信息修改
	 */
	private static final long serialVersionUID = 1L;
	private MchantServices mchService;
	private String mch_name;//商户名称 
	private int id;//商户信息数据库id
	private String oldCity;//修改前的城市标签 市,市,市...
	private String cityString;//修改后的城市标签 市,市,市...
	private String mchant_number;//商户编号
	private String mch_group_id;//商户类型id
	private String status;//商户状态
	private String capita_consumption;//人均消费金额
	private String use_note;//使用说明
	private String discount_note;//折扣说明
	private String mch_min_discount;//最低折扣
	
	private String mch_max_discount;//最大折扣
	private String mch_key_word;//商户关键字
	private File pic_url;//商户图片
	private File pic_url_logo;//商户logo
	private String sale_card_status;//售卡权限
	private String sale_poundage;//商户结算手续费
	private String mch_time_sale;//商户营业时间
	private String mch_biz_districts;//所属商圈
	private String mch_card_range;//储值卡使用范围（哪类商品无法使用）
	private String mch_more;//其他
	private String mch_indus_name;//商户工商注册名
	private String mch_oper_per;//商户营业期限
	private String mch_leg_person;//商户法人
	
	/*
	 * 门店信息
	 */
	private String branch_name;//门店名称
	private String city_id;//门店所属城市编号
	private File branch_pic_url;//门店图片
	private String branch_addr;//门店地址
	private String biz_districts;//门店商圈
	private String telephone;//门店联系方式
	private String longitude;//经度
	private String latitude;//纬度
	private String storeAccountJosn;//门店账户json
	private String branchId;//门店id
	
	public void setMchService(MchantServices mchService) {
		this.mchService = mchService;
	}
	
	//进入商户修改页面
	public String mchantUpdate(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		List<MchGroups> list =  mchService.MchGroupList();
		List<MchPic> mchPic = mchService.MchPicDetail(mchantNo);
		List<FndCitys> listCity = mchService.MchCityRefDetail(mchantNo);
		String mchpicUrl = "";
		for(int i=0;i<mchPic.size();i++){
			mchpicUrl = mchpicUrl + mchPic.get(i).getPic_url()+"--";
		}
		JSONArray Cityjson = JSONArray.fromObject(listCity); 
	    request.setAttribute("citysNameList", listCity);
		request.setAttribute("mchGroupList", list);
		request.setAttribute("mchant", mchant);
		request.setAttribute("mchPic", mchpicUrl);
		request.setAttribute("listCity", listCity);
		request.setAttribute("Cityjson", Cityjson);
		return SUCCESS;
	}
	
	//商户基本修改修改提交
	public String excuteMchantUpdate() throws IOException{
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		InputStream inStream = CashOutImportAction.class.getResourceAsStream("/mch_pic_url.properties");
		Properties prop = new Properties();  
		try {
			prop.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		String url = prop.getProperty("url");
		String projectName=prop.getProperty("projectName").trim();//工程名
		String dir=prop.getProperty("dir").trim();//文件夹名字
		String root = ServletActionContext.getServletContext().getRealPath("/").trim();//项目路径
		String uperRoot = root.substring(0,root.lastIndexOf(projectName))+dir;//上级目录
		Date date = new Date();
        if(pic_url!=null){
        	String filePath = uperRoot+"/mchpic/";
    		UnZip.unzipFile(filePath, pic_url);
    		
    		File unzipFile = new File(filePath);
    		String[] filelist = unzipFile.list();
    		Map<Integer,MchPic> map = new HashMap<Integer,MchPic>();
            for (int k = 0; k < filelist.length; k++) {
            	File readfile = new File(unzipFile + "/" + filelist[k]);
    			String pic_url_name = mchant_number+k+"mch_pic.png";//商户图片名字
    			UpLoadPic.putObject(pic_url_name, readfile);
    	        MchPic mpic = new MchPic();
    	        mpic.setBranch_id(0);
    	        mpic.setSource("0");
    	    	mpic.setPic_url(url+"/"+dir+"/"+pic_url_name);
    	    	mpic.setPic_type("0");
    	    	mpic.setStatus("0");
    	    	mpic.setCreate_date(date);
    	    	mpic.setCreate_person(user.getName());
    	    	mpic.setMch_number(mchant_number);
    	    	int index = readfile.getName().indexOf('.');
    			String id = readfile.getName().substring(0,index);
    			map.put(Integer.valueOf(id), mpic);
            }
            if(filelist.length>0){
    			mchService.delMpic(mchant_number);
    		}
            for(int j=1;j<map.size()+1;j++){
            	mchService.saveMchPic(map.get(j));
            }
            UnZip.delAllFile(filePath);
        }
        
        String pic_url_logo_name = mchant_number+"mch_pic_logo.png";//商户名字
        if(pic_url_logo!=null){
        	UpLoadPic.putObject(pic_url_logo_name, pic_url_logo);
        }
    	
       
        MchMerchant mchant = new MchMerchant();
        mchant.setId(id);
        mchant.setMch_number(mchant_number);
        mchant.setMch_name(mch_name);
        if(pic_url_logo!=null){
        	mchant.setPic_url_logo(url+"/"+dir+"/"+pic_url_logo_name);
        }
        mchant.setMch_group_id(mch_group_id);
        mchant.setMch_key_word(mch_key_word);
        int capita_consumption_int = (int)(Double.valueOf(capita_consumption)*100.00);
        mchant.setCapita_consumption(capita_consumption_int);
        mchant.setUse_note(use_note);
        mchant.setSale_poundage(sale_poundage);
        mchant.setStatus(status);
        mchant.setSale_card_status(sale_card_status);
        mchant.setSale_poundage(sale_poundage);
        mchant.setDiscount_note(discount_note);
        mchant.setMch_min_discount(mch_min_discount);
        mchant.setMch_max_discount(mch_max_discount);
        mchant.setMch_time_sale(mch_time_sale);
        mchant.setMch_biz_districts(mch_biz_districts);
        mchant.setMch_card_range(mch_card_range);
        mchant.setMch_more(mch_more);
        mchant.setMch_indus_name(mch_indus_name);
        mchant.setMch_oper_per(mch_oper_per);
        mchant.setMch_leg_person(mch_leg_person);
        mchant.setUpdate_date(date);
        mchant.setUpdate_person(user.getName());
        mchService.updateMchant(mchant);
        JSONArray jsonArray = JSONArray.fromObject(oldCity);  
        List<FndCitys> listOld = (List) JSONArray.toCollection(jsonArray,FndCitys.class);  
        String[] strCity = cityString.trim().split(",");
        for(int j=0;j<strCity.length;j++){
        	String cityId = strCity[j];
        	for(int k=0;k<listOld.size();k++){
        		if(!cityId.equals(listOld.get(k).getCity_id())){
        			MchCityRef mchCityRef = new MchCityRef();
        			mchCityRef.setCity_id(Integer.valueOf(cityId));
    		        mchCityRef.setMch_number(mchant_number);
    		        mchCityRef.setCreate_date(date);
    		        mchCityRef.setCreate_person(user.getName());
    		        if(!mchService.cityRefIsExits(mchCityRef)){
    		        	mchService.saveMchCityRef(mchCityRef);
    		        }
        		}
        	}
        }
        response.sendRedirect("mchant_list.jhtml");
		return null;
	}
	
	
	//修改商户门店信息
	public String storeUpdate(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		String branchId = request.getParameter("branchId");
		MchMerchantBranch branch = mchService.MchMerchantBranchDetail(mchantNo, branchId);
		List<MchAccountNumber> listAccount = mchService.MchAccountNumberDetail(mchantNo, branchId);
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		List<MchPic> listPic = mchService.MchPicDetail(mchantNo, branchId);
		
		String mchpicUrl = "";
		for(int i=0;i<listPic.size();i++){
			mchpicUrl = mchpicUrl + listPic.get(i).getPic_url()+"--";
		}
		request.setAttribute("listAccount", listAccount);
		request.setAttribute("oldSize", listAccount.size());
		request.setAttribute("mchpicUrl", mchpicUrl);
		request.setAttribute("branch", branch);
		List<FndCitys> citysNameList = mchService.MchCityRefDetail(mchantNo);
		request.setAttribute("citysNameList", citysNameList);
		request.setAttribute("mch_name", mchant.getMch_name());
		request.setAttribute("mchantNo", mchant.getMch_number());
		request.setAttribute("mchant", mchant);
		return SUCCESS;
	}
	//商户门店信息修改提交
	public String excuteStoreUpdate() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		MchMerchantBranch branch = new MchMerchantBranch();
		MchMerchant mchant = mchService.MchMerchantDetail(mchant_number);
		if(branch_name!=null&&!"".equals(branch_name)){
			Date date = new Date();
			branch.setId(Integer.valueOf(branchId));
			branch.setMch_number(mchant_number);
			branch.setCity_id(Integer.valueOf(city_id));
			branch.setBranch_name(branch_name);
			branch.setBiz_districts(biz_districts);
			if(use_note!=null&&!"".equals(use_note)){
				branch.setUse_note(use_note);
			}else{
				branch.setUse_note(mchant.getUse_note());
			}
			branch.setBranch_addr(branch_addr);
			branch.setTelephone(telephone);
			branch.setLongitude(longitude);
			branch.setLatitude(latitude);
			branch.setStatus(status);
			branch.setUpdate_date(new Date());
			ActionContext actionContext = ActionContext.getContext();
	        Map session = actionContext.getSession();
			User user = (User) session.get("loginUser");
			branch.setUpdate_person(user.getName());
			mchService.updateMchMerchantBranch(branch);
			
			JSONArray jsonArray = JSONArray.fromObject(storeAccountJosn);  
			List<MchAccountNumber> list = (List) JSONArray.toCollection(jsonArray,MchAccountNumber.class); 
			for(int i=0;i<list.size();i++){
				MchAccountNumber account = list.get(i);
				account.setMch_number(mchant_number);
				account.setBranch_id(Integer.valueOf(branchId));
				account.setSource("1");
				account.setStatus("2");
				account.setAccount_pass(MD5.GetMD5Code("123456"));
			
				if(mchService.accoutnIsExits(account)){
					account.setUpdate_date(date);
					account.setUpdate_person(user.getName());
					mchService.udpateStoreAccount(account);
				}else{
					account.setCreate_date(date);
					account.setCreate_person(user.getName());
					mchService.saveMchAccountNumber(account);
				}
				
			}
			if(branch_pic_url!=null){
				//保存门店图片
				InputStream inStream = CashOutImportAction.class.getResourceAsStream("/mch_pic_url.properties");
				Properties prop = new Properties();  
				try {
					prop.load(inStream);
				} catch (IOException e) {
					e.printStackTrace();
				}  
				String url = prop.getProperty("url");
				String projectName=prop.getProperty("projectName").trim();//工程名
				String dir=prop.getProperty("dir").trim();//文件夹名字
				String root = ServletActionContext.getServletContext().getRealPath("/").trim();//项目路径
				String uperRoot = root.substring(0,root.lastIndexOf(projectName))+dir;//上级目录
		       
				String filePath = uperRoot+"/mchpic/";
				UnZip.unzipFile(filePath, branch_pic_url);
				
				File unzipFile = new File(filePath);
				String[] filelist = unzipFile.list();
				Map<Integer,MchPic> map = new HashMap<Integer,MchPic>();
		        for (int k = 0; k < filelist.length; k++) {
		        	File readfile = new File(unzipFile + "/" + filelist[k]);
					String pic_url_name = mchant_number+k+branchId+"mch_branch_pic.png";//商户图片名字
					UpLoadPic.putObject(pic_url_name, readfile);
			        MchPic mpic = new MchPic();
			        mpic.setBranch_id(Integer.valueOf(branchId));
			        mpic.setSource("1");
			        mpic.setMch_number(mchant_number);
			        mpic.setPic_type("0");
			        mpic.setPic_url(url+"/"+dir+"/"+pic_url_name);
			        mpic.setStatus("0");
			        mpic.setCreate_date(date);
			        mpic.setCreate_person(user.getName());
					int index = readfile.getName().indexOf('.');
					String id = readfile.getName().substring(0,index);
					map.put(Integer.valueOf(id), mpic);
		        }
		        if(filelist.length>0){
		        	mchService.delStorePic(mchant_number, branchId);
		        }
		        for(int j=1;j<map.size()+1;j++){
		        	mchService.saveMchPic(map.get(j));
		        }
		        UnZip.delAllFile(filePath);
			}
		}
		response.sendRedirect("mchantStoreDetail.jhtml?mchantNo="+mchant_number);
		return null;
	}
	
	//修改商户账号信息
	public String accountUpdate(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		List<MchAccountNumber> listMchantAccount = mchService.MchAccountNumberDetail(mchantNo);
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		request.setAttribute("mchant", mchant);
		request.setAttribute("listMchantAccount", listMchantAccount);
		request.setAttribute("accountSize", listMchantAccount.size());
		return SUCCESS;
	}
	
	//开通/关闭门店账户
	public String updateStoreAccountStatus() throws IOException{
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		String account_number = request.getParameter("account_number");
		String status = request.getParameter("status");
		String branchId = request.getParameter("branchId");
		MchAccountNumber account = new MchAccountNumber();
		account.setMch_number(mchantNo);
		account.setAccount_number(account_number);
		account.setStatus(status);
		account.setBranch_id(Integer.valueOf(branchId));
		mchService.updateStoreAccount(account);
		response.sendRedirect("storeUpdate.jhtml?mchantNo="+mchantNo+"&branchId="+branchId);
		return null;
	}
	
	//开通/关闭商户账号
	public String updateMchAccountStatus() throws IOException{
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		String mchantNo = request.getParameter("mchantNo");
		String accountId = request.getParameter("accountId");
		String status = request.getParameter("status");
		MchAccountNumber account = new MchAccountNumber();
		account.setId(Integer.valueOf(accountId));
		account.setMch_number(mchantNo);
		account.setStatus(status);
		account.setUpdate_date(new Date());
		account.setUpdate_person(user.getName());
		mchService.updateMchAccountStatus(account);
		response.sendRedirect("mchant_list.jhtml");
		return null;
	}
	//商户账号修改提交(增加账户)
	public String excuteAccountUpdate() throws Exception{
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		Date date = new Date();
		String mchaccount_count = request.getParameter("mchaccount_count");//商户账号数
		String mchantNo = request.getParameter("mchantNo");
		String mchaccount_count_old = request.getParameter("mchaccount_count_old");
		int old = 0;
		if(mchaccount_count_old!=null&&!"".equals(mchaccount_count_old)){
			old = Integer.valueOf(mchaccount_count_old);
		}
		for(int i=1+old;i<=Integer.valueOf(mchaccount_count)+old;i++){
			String account_name = request.getParameter("account_number"+i);
			if(account_name!=null&&!"".equals(account_name)){
				MchAccountNumber account = new MchAccountNumber();
				account.setSource("0");
				account.setBranch_id(0);
				account.setMch_number(mchantNo);
				account.setAccount_number(account_name);
				account.setAccount_pass(MD5.GetMD5Code("123456"));
				account.setAuthority(request.getParameter("authority"+i));
				account.setStatus("2");
				account.setRemark(request.getParameter("remark"+i));
				account.setCreate_date(date);
				account.setCreate_person(user.getName());
				if(mchService.accoutnIsExits(account)){
					throw new Exception("商户账号"+account.getAccount_number()+"已经存在！");
				}else{
					mchService.saveMchAccountNumber(account);
				}
			}
		}
		
		response.sendRedirect("mchant_list.jhtml");
		
		return null;
	}
	//门店删除
	public String deleteStore() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String mchantNo = request.getParameter("mchantNo").trim();
		String stotreId = request.getParameter("storeId").trim();
		MchMerchantBranch branch = new MchMerchantBranch();
		branch.setId(Integer.valueOf(stotreId));
		branch.setMch_number(mchantNo);
		mchService.deleteStore(branch);
		response.sendRedirect("mchant_list.jhtml");
		return null;
	}
	
	//修改商户储值卡信息
	public String cardUpdate(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		List<MchCardKinds> listCards = mchService.MchCardKindsDetail(mchantNo);
		request.setAttribute("listCards", listCards);
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		request.setAttribute("mchant", mchant);
		request.setAttribute("cardSize", listCards.size());
		return SUCCESS;
	}
	
	//储值卡修改提交
	public String excuteCardUpdate() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		Date date = new Date();
		String mchantNo = request.getParameter("mchantNo").trim();
		String oldCardSize = request.getParameter("cardSize");//修改前的卡号数量
		int old = 0;
		if(oldCardSize!=null&&!"".equals(oldCardSize)){
			old = Integer.valueOf(oldCardSize);
		}
		for(int i=0;i<old;i++){
			MchCardKinds cardKinds = new MchCardKinds();
			cardKinds.setMch_number(mchantNo);
			String id = request.getParameter("cardId"+i);
			String status = request.getParameter("cardStatus"+i);
			cardKinds.setId(Integer.valueOf(id));
			cardKinds.setStatus(status);
			cardKinds.setUpdate_date(date);
			cardKinds.setUpdate_person(user.getName());
			mchService.updateCard(cardKinds);
		}
		String card_count = request.getParameter("card_count");
		String mchantName = request.getParameter("mchantName");
		if(card_count!=null&&!"".equals(card_count)){
			for(int i=1+old;i<=Integer.valueOf(card_count)+old;i++){
				String ck_name = request.getParameter("ck_name"+i);
				if(ck_name!=null&&!"".equals(ck_name)){
					MchCardKinds cardKinds = new MchCardKinds();
					cardKinds.setMch_name(mchantName);
					cardKinds.setMch_number(mchantNo);
					cardKinds.setCk_name(ck_name);
					cardKinds.setCk_type(request.getParameter("ck_type"+i));
					cardKinds.setCk_quota((int)(Double.valueOf(request.getParameter("ck_quota"+i).trim())*100));
					cardKinds.setSales_amount((int)(Double.valueOf(request.getParameter("sales_amount"+i).trim())*100));
					if(cardKinds.getCk_type().equals("0")){
						float dis = (float)cardKinds.getSales_amount()/cardKinds.getCk_quota();
						DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
						String dist = df.format(dis);
						cardKinds.setDiscount(dist);
					}else{
						float dis = Float.valueOf(request.getParameter("discount"+i));
						DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
						String dist = df.format(dis);
						cardKinds.setDiscount(dist);
					}
					
					cardKinds.setPeriod_validity("36");
					cardKinds.setStatus(request.getParameter("status"+i));
					cardKinds.setCreate_date(date);
					cardKinds.setCreate_person(user.getName());
					mchService.saveMchCardKinds(cardKinds);
				}
			}
		}
		
		response.sendRedirect("mchant_list.jhtml");
		return null;
	}
	
	//进入B类消费折扣分配修改页面
	public String allotUpdate(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		List<MchBackAllot> listAllot = mchService.MchAllotDetail(mchantNo);
		request.setAttribute("listAllot", listAllot);
		request.setAttribute("mchant", mchant);
		request.setAttribute("alloCount", listAllot.size());
		return SUCCESS;
	}
	
	//商户B类消费折扣分配修改提交
	public String excuteAllotUpdate() throws IOException{
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		Date date = new Date();
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String mchantNo = request.getParameter("mchantNo");
		String a_allot = request.getParameter("a_allot");
		String b_allot = request.getParameter("b_allot");
		String o_allot = request.getParameter("o_allot");
		String allotFlag = request.getParameter("allotFlag");
		MchBackAllot mAllot = new MchBackAllot();
		mAllot.setMch_number(mchantNo);
		mAllot.setA_allot(a_allot);
		mAllot.setB_allot(b_allot);
		mAllot.setO_allot(o_allot);
		if(allotFlag!=null&&!"".equals(allotFlag)){
			mAllot.setStatus(request.getParameter("status"));
			mAllot.setCreate_date(date);
			mAllot.setCreate_person(user.getName());
			mchService.saveAllot(mAllot);
		}else{
			mAllot.setUpdate_date(date);
			mAllot.setUpdate_person(user.getName());
			mchService.updateAllot(mAllot);
		}
		response.sendRedirect("mchant_list.jhtml");
		return null;
	}
	
	//进入商户结算信息修改
	public String astmConfigUpdate(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		MchStmConfig config = mchService.detailMchStmConfig(mchantNo);
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		request.setAttribute("mchant", mchant);
		request.setAttribute("stmConfig", config);
		return SUCCESS;
	}
	//商户结算配置修改提交
	public String excuteAstmConfigUpdate() throws IOException{
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		Date date = new Date();
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String mch_number = request.getParameter("mchantNo");//商户编号
		String mch_account_card = request.getParameter("mch_account_card");//结算银行账号
		String mch_account_name = request.getParameter("mch_account_name");//账户名称
		String mch_bank_open_bank = request.getParameter("mch_bank_open_bank");//开户行
		String mch_bank_open_bank_no = request.getParameter("mch_bank_open_bank_no");//支行行号
		String mch_bank_open_addr = request.getParameter("mch_bank_open_addr");//开户地址
		String mch_account_type = request.getParameter("mch_account_type");//账户类型
		String mch_account_person = request.getParameter("mch_account_person");//联系人
		String mch_account_phone = request.getParameter("mch_account_phone");//联系电话
		String mch_email = request.getParameter("mch_email");//邮箱
		String mch_setment_type = request.getParameter("mch_setment_type");
		String mch_settlement_period = request.getParameter("mch_settlement_period");// 结算周期类型1 2 3 
		String mch_advance_quota = request.getParameter("mch_advance_quota");//公司垫款限额，单位分
		MchStmConfig config = mchService.detailMchStmConfig(mch_number);
		if(config==null){
			config = new MchStmConfig();
		}
		config.setMch_account_card(mch_account_card);
		config.setMch_account_name(mch_account_name);
		config.setMch_bank_open_bank(mch_bank_open_bank);
		config.setMch_bank_open_bank_no(mch_bank_open_bank_no);
		config.setMch_bank_open_addr(mch_bank_open_addr);
		config.setMch_account_type(mch_account_type);
		config.setMch_account_person(mch_account_person);
		config.setMch_account_phone(mch_account_phone);
		config.setMch_email(mch_email);
		config.setMch_setment_type(mch_setment_type);
		if(mch_settlement_period!=null&&!"".equals(mch_settlement_period)){
			config.setMch_settlement_period(Integer.valueOf(mch_settlement_period));
		}
		if(mch_advance_quota!=null&&!"".equals(mch_advance_quota)){
			config.setMch_advance_quota(Math.round((Double.valueOf(mch_advance_quota)*100))+"");
		}
		config.setUpdate_date(date);
		config.setUpdate_person(user.getName());
		mchService.updateMchStmConfig(config);
		response.sendRedirect("mchant_list.jhtml");
		return null;
	}
	public String getMch_name() {
		return mch_name;
	}

	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}

	public String getDiscount_note() {
		return discount_note;
	}

	public void setDiscount_note(String discount_note) {
		this.discount_note = discount_note;
	}

	public String getMch_key_word() {
		return mch_key_word;
	}

	public void setMch_key_word(String mch_key_word) {
		this.mch_key_word = mch_key_word;
	}

	public String getSale_card_status() {
		return sale_card_status;
	}

	public void setSale_card_status(String sale_card_status) {
		this.sale_card_status = sale_card_status;
	}

	public String getMch_group_id() {
		return mch_group_id;
	}

	public void setMch_group_id(String mch_group_id) {
		this.mch_group_id = mch_group_id;
	}

	public String getSale_poundage() {
		return sale_poundage;
	}

	public void setSale_poundage(String sale_poundage) {
		this.sale_poundage = sale_poundage;
	}

	public File getPic_url() {
		return pic_url;
	}

	public void setPic_url(File pic_url) {
		this.pic_url = pic_url;
	}

	public String getMchant_number() {
		return mchant_number;
	}

	public void setMchant_number(String mchant_number) {
		this.mchant_number = mchant_number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityString() {
		return cityString;
	}

	public void setCityString(String cityString) {
		this.cityString = cityString;
	}

	public String getOldCity() {
		return oldCity;
	}

	public void setOldCity(String oldCity) {
		this.oldCity = oldCity;
	}

	public String getMch_time_sale() {
		return mch_time_sale;
	}

	public void setMch_time_sale(String mch_time_sale) {
		this.mch_time_sale = mch_time_sale;
	}

	public String getMch_card_range() {
		return mch_card_range;
	}

	public void setMch_card_range(String mch_card_range) {
		this.mch_card_range = mch_card_range;
	}

	public String getMch_more() {
		return mch_more;
	}

	public void setMch_more(String mch_more) {
		this.mch_more = mch_more;
	}

	public String getMch_indus_name() {
		return mch_indus_name;
	}

	public void setMch_indus_name(String mch_indus_name) {
		this.mch_indus_name = mch_indus_name;
	}

	public String getMch_oper_per() {
		return mch_oper_per;
	}

	public void setMch_oper_per(String mch_oper_per) {
		this.mch_oper_per = mch_oper_per;
	}

	public String getMch_leg_person() {
		return mch_leg_person;
	}

	public void setMch_leg_person(String mch_leg_person) {
		this.mch_leg_person = mch_leg_person;
	}

	public String getUse_note() {
		return use_note;
	}

	public void setUse_note(String use_note) {
		this.use_note = use_note;
	}

	public String getMch_min_discount() {
		return mch_min_discount;
	}

	public String getMch_max_discount() {
		return mch_max_discount;
	}

	public void setMch_max_discount(String mch_max_discount) {
		this.mch_max_discount = mch_max_discount;
	}

	public void setMch_min_discount(String mch_min_discount) {
		this.mch_min_discount = mch_min_discount;
	}

	public File getPic_url_logo() {
		return pic_url_logo;
	}

	public void setPic_url_logo(File pic_url_logo) {
		this.pic_url_logo = pic_url_logo;
	}

	public void setMch_biz_districts(String mch_biz_districts) {
		this.mch_biz_districts = mch_biz_districts;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public void setBranch_pic_url(File branch_pic_url) {
		this.branch_pic_url = branch_pic_url;
	}

	public void setBranch_addr(String branch_addr) {
		this.branch_addr = branch_addr;
	}

	public void setBiz_districts(String biz_districts) {
		this.biz_districts = biz_districts;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setStoreAccountJosn(String storeAccountJosn) {
		this.storeAccountJosn = storeAccountJosn;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setCapita_consumption(String capita_consumption) {
		this.capita_consumption = capita_consumption;
	}
	
	
}
