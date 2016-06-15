package cn.bc.mchant.action;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

import cn.bc.business.action.CashOutImportAction;
import cn.bc.common.support.page.Page;
import cn.bc.common.webapp.base.BaseAction;
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
import cn.bc.mchant.vo.MchCity;
import cn.bc.mchant.vo.MchantFilter;
import cn.bc.settlement.po.StmMchDetail;
import cn.bc.settlement.po.StmMerchant;
import cn.bc.settlement.services.SettlementService;
import cn.bc.util.LastDate;
import cn.bc.util.MD5;
import cn.bc.util.UnZip;
import cn.bc.util.UpLoadPic;
import cn.common.security.vo.User;

public class MchantAction extends BaseAction{
	
	/**
	 * 
	 */
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
	private static final long serialVersionUID = 1L;
	private MchantServices mchService;
	private SettlementService settleService;
	private String mchantNo;//商户编号
	private String mch_name;//商户名称
	private String status;//商户状态
	private String mch_group_id;//商户类型id
	private String sale_card_type;//售卡类型0买送卡 1买折卡
	private String cityString;//城市标签 省-市，省-市...
	private String discount_note;//折扣说明
	private String mch_max_discount;//最大折扣
	private String mch_min_discount;//最低折扣
	private String capita_consumption;//人均消费金额
	private String use_note;//使用说明
	private String mch_key_word;//商户关键词
	private File pic_url;//商户图片
	private File pic_url_logo;//商户logo
	private String sale_card_status;//售卡权限
	private String cityNameString;//城市名称
	private String sale_poundage;//商户结算手续费
	private String mch_time_sale;//商户营业时间
	private String mch_biz_districts;//所属商圈
	private String mch_card_range;//储值卡使用范围（哪类商品无法使用）
	private String mch_more;//其他
	private String mch_indus_name;//商户工商注册名
	private String mch_oper_per;//商户营业期限
	private String mch_leg_person;//商户法人
	private File brandPic;//品牌信息图片
	private String brand_info;//品牌信息
	private String input_info;//输入的品牌信息
	
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
	public void setMchService(MchantServices mchService) {
		this.mchService = mchService;
	}
	
	public void setSettleService(SettlementService settleService) {
		this.settleService = settleService;
	}


	/*
	 * 进入商户列表页面
	 */
	public String mchList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		MchantFilter filter = new MchantFilter();
		String mchantName = request.getParameter("mchantName");
		String mchantNo = request.getParameter("mchantNo");
		String status = request.getParameter("status");
		System.out.println(status);
		filter.setMchantName(mchantName);
		filter.setMchantNo(mchantNo);
		if(status!=null&&!"".equals(status)&&!"8".equals(status)){
			filter.setStatus(status);
		}
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);

		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = mchService.MchantList(filter);
		List<MchMerchant> list = mchService.MchMerchantList();
		int openCount = 0;
		for(int i=0;i<list.size();i++){
			if("0".equals(list.get(i).getStatus())){
				openCount = openCount + 1;
			}
		}
		request.setAttribute("openCount", openCount);
		request.setAttribute("count", list.size());
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	/*
	 * 进入商户添加页面
	 */
	public String addmchant(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		List<MchGroups> list =  mchService.MchGroupList();
		request.setAttribute("mchGroupList", list);
		Date date = new Date();
		String mchantNo = "v"+date.getTime();//商户编号
		request.setAttribute("mchantNo", mchantNo);
		return SUCCESS;
	}
	
	/*
	 * 保存商户基本信息
	 */
	public String savemchant() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		Date date1 = new Date();
//		String mchantNo = "v"+date.getTime();//商户编号

		//保存商户图片
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
		UnZip.unzipFile(filePath, pic_url);
		
		File unzipFile = new File(filePath);
		String[] filelist = unzipFile.list();
		Map<Integer,MchPic> map = new HashMap<Integer,MchPic>();
        for (int k = 0; k < filelist.length; k++) {
        	File readfile = new File(unzipFile + "/" + filelist[k]);
			String pic_url_name = mchantNo+k+"mch_pic.png";//商户图片名字
			UpLoadPic.putObject(pic_url_name, readfile);
	        
	        MchPic mpic = new MchPic();
	        mpic.setBranch_id(0);
	        mpic.setSource("0");
	        mpic.setMch_number(mchantNo);
	        mpic.setPic_type("0");
	        mpic.setPic_url(url+"/"+dir+"/"+pic_url_name);
	        mpic.setStatus("0");
	        mpic.setCreate_date(date1);
	        mpic.setCreate_person(user.getName());
			int index = readfile.getName().indexOf('.');
			String id = readfile.getName().substring(0,index);
			map.put(Integer.valueOf(id), mpic);
        }
        if(filelist.length>0){
			mchService.delMpic(mchantNo);
		}
        for(int j=1;j<map.size()+1;j++){
        	mchService.saveMchPic(map.get(j));
        }
        UnZip.delAllFile(filePath);
        String pic_url_logo_name = "";
        if(pic_url_logo!=null){
	        pic_url_logo_name = mchantNo+"mch_pic_logo.png";//商户图片名字
	        UpLoadPic.putObject(pic_url_logo_name, pic_url_logo);
        }
        
        //保存商户基本信息
        MchMerchant mchant = new MchMerchant();
        mchant.setMch_number(mchantNo);
        mchant.setMch_name(mch_name);
        if(pic_url_logo!=null){
        	mchant.setPic_url_logo(url+"/"+dir+"/"+pic_url_logo_name);
        }
        mchant.setMch_key_word(mch_key_word);
        mchant.setMch_group_id(mch_group_id);
        mchant.setSale_card_type(sale_card_type);
        mchant.setStatus("1");//关闭状态，完成提交后改为开通状态
        mchant.setSale_card_status(sale_card_status);
        mchant.setSale_poundage(sale_poundage);
        int capita_consumption_int = (int)(Double.valueOf(capita_consumption)*100.00);
        mchant.setCapita_consumption(capita_consumption_int);
        mchant.setUse_note(use_note);
        mchant.setDiscount_note(discount_note);
        mchant.setMch_max_discount(mch_max_discount);
        mchant.setMch_min_discount(mch_min_discount);
        mchant.setType_open("2");
        mchant.setMch_time_sale(mch_time_sale);
        mchant.setMch_biz_districts(mch_biz_districts);
        mchant.setMch_card_range(mch_card_range);
        mchant.setMch_more(mch_more);
        mchant.setMch_indus_name(mch_indus_name);
        mchant.setMch_oper_per(mch_oper_per);
        mchant.setMch_leg_person(mch_leg_person);
        mchant.setCreate_date(date1);
       
		mchant.setCreate_person(user.getName());
		MchMerchant mch= mchService.isMchantExist(mchantNo);
		if(mch!=null){
			mchant.setId(mch.getId());
			mchService.addUpdateMchant(mchant);
		}else{
			mchService.saveMchant(mchant);
		}
        //保存商户城市标签
        MchCityRef mchCityRef = new MchCityRef();
        mchCityRef.setMch_number(mchantNo);
        mchCityRef.setCreate_date(date1);
        mchCityRef.setCreate_person(user.getName());
        mchService.delMchCity(mchantNo);
        mchService.saveMchCityRef(mchCityRef, cityString);
        request.setAttribute("mchantNo", mchantNo);
        request.setAttribute("mch_name", mch_name);
        String[] citysName = cityNameString.split(",");
        List<MchCity> citysNameList = new ArrayList<MchCity>();
        for(int j=0;j<citysName.length;j++){
        	MchCity mchCity = new MchCity();
        	String[] str = citysName[j].split("-");
        	mchCity.setCity_id(Integer.valueOf(str[0]));
        	mchCity.setCityName(str[1]);
        	citysNameList.add(mchCity);
        }
        request.setAttribute("citysNameList", citysNameList);
        request.setAttribute("sale_card_type", sale_card_type);
		return SUCCESS;
	}
	
	//查看门店列表
	public String mchantStoreDetail(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		List<MchMerchantBranch>  listBranch = mchService.MchMerchantBranchDetail(mchantNo);
		request.setAttribute("listBranch", listBranch);
		request.setAttribute("mchantNo", mchantNo);
		return SUCCESS;
	}
	
	//进入门店添加页面
	public String addStore(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		
		List<FndCitys> citysNameList = mchService.MchCityRefDetail(mchantNo);
		request.setAttribute("citysNameList", citysNameList);
		request.setAttribute("mch_name", mchant.getMch_name());
		request.setAttribute("mchantNo", mchant.getMch_number());
		request.setAttribute("mchant", mchant);
		return SUCCESS;
	}
	//保存商户门店信息
	public String savemchantStore() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		MchMerchantBranch branch = new MchMerchantBranch();
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		if(branch_name!=null&&!"".equals(branch_name)){
			Date date = new Date();
			branch.setMch_number(mchantNo);
			branch.setCity_id(Integer.valueOf(city_id));
			branch.setBranch_name(branch_name);
			branch.setBiz_districts(biz_districts);
			if(use_note!=null&&!"".equals(use_note)){
				branch.setUse_note(use_note);
			}else{
				branch.setUse_note(mchant.getUse_note());
			}
//			branch.setBranch_pic_url(request.getParameter("mch_pic"));
			branch.setBranch_addr(branch_addr);
			branch.setTelephone(telephone);
			branch.setLongitude(longitude);
			branch.setLatitude(latitude);
			branch.setStatus("0");
			branch.setCreate_date(new Date());
			ActionContext actionContext = ActionContext.getContext();
	        Map session = actionContext.getSession();
			User user = (User) session.get("loginUser");
			branch.setCreate_person(user.getName());
			if(mchService.isBranch(mchantNo,branch_name)){
				throw new Exception("该门店已经存在！");
			}else{
				int branch_id = mchService.saveMchBranch(branch);
				JSONArray jsonArray = JSONArray.fromObject(storeAccountJosn);  
				List<MchAccountNumber> list = (List) JSONArray.toCollection(jsonArray,MchAccountNumber.class); 
				for(int i=0;i<list.size();i++){
					MchAccountNumber account = list.get(i);
					account.setMch_number(mchantNo);
					account.setBranch_id(branch_id);
					account.setSource("1");
					account.setStatus("2");
					account.setAccount_pass(MD5.GetMD5Code("123456"));
					account.setCreate_date(date);
					account.setCreate_person(user.getName());
					mchService.saveMchAccountNumber(account);
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
					UnZip.delAllFile(filePath);
					UnZip.unzipFile(filePath, branch_pic_url);
					
					File unzipFile = new File(filePath);
					String[] filelist = unzipFile.list();
					Map<Integer,MchPic> map = new HashMap<Integer,MchPic>();
			        for (int k = 0; k < filelist.length; k++) {
			        	File readfile = new File(unzipFile + "/" + filelist[k]);
						String pic_url_name = mchantNo+k+branch_id+"mch_branch_pic.png";//商户图片名字
						UpLoadPic.putObject(pic_url_name, readfile);
				        MchPic mpic = new MchPic();
				        mpic.setBranch_id(branch_id);
				        mpic.setSource("1");
				        mpic.setMch_number(mchantNo);
				        mpic.setPic_type("0");
				        mpic.setPic_url(url+"/"+dir+"/"+pic_url_name);
				        mpic.setStatus("0");
				        mpic.setCreate_date(date);
				        mpic.setCreate_person(user.getName());
						int index = readfile.getName().indexOf('.');
						String id = readfile.getName().substring(0,index);
						map.put(Integer.valueOf(id), mpic);
			        }
			        for(int j=1;j<map.size()+1;j++){
			        	mchService.saveMchPic(map.get(j));
			        }
			        UnZip.delAllFile(filePath);
				}else{
					Map<Integer,MchPic> map = new HashMap<Integer,MchPic>();
					List<MchPic> listMpic= mchService.MchPicDetail(mchantNo);
					for(int i=0;i<listMpic.size();i++){
						MchPic mpic = new MchPic();
				        mpic.setBranch_id(branch_id);
				        mpic.setSource("1");
				        mpic.setMch_number(mchantNo);
				        mpic.setPic_type("0");
				        mpic.setPic_url(listMpic.get(i).getPic_url());
				        mpic.setStatus("0");
				        mpic.setCreate_date(date);
				        mpic.setCreate_person(user.getName());
						map.put(i, mpic);
			        }
			        for(int j=0;j<map.size();j++){
			        	mchService.saveMchPic(map.get(j));
			        }
				}
			}
		}
		response.sendRedirect("mchantStoreDetail.jhtml?mchantNo="+mchantNo);
		return null;
	}
	//门店详情
	public String storeDetail(){
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
		request.setAttribute("mchpicUrl", mchpicUrl);
		request.setAttribute("branch", branch);
		request.setAttribute("mchant", mchant);
		return SUCCESS;
	}
	//门店删除
	public String storeDelete() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String mchantNo = request.getParameter("mchantNo");
		String branchId = request.getParameter("branchId");
		mchService.storeDelete(mchantNo, branchId);
		response.sendRedirect("mchantStoreDetail.jhtml?mchantNo="+mchantNo);
		return null;
	}
	//保存商户账号和储值卡类型
	public String savemchantCard() throws Exception{
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchaccount_count = request.getParameter("mchaccount_count");//商户账号数
		String mchcard_count = request.getParameter("mchcard_count");//储值卡类型数
		String mchantNo = request.getParameter("mchantNo");
		String mch_name = request.getParameter("mch_name");
		Date date = new Date();
		mchService.delMchAccount(mchantNo);
		for(int i=1;i<=Integer.valueOf(mchaccount_count);i++){
			MchAccountNumber account = new MchAccountNumber();
			account.setMch_number(mchantNo);
			String account_name = request.getParameter("account_number"+i);
			if(account_name!=null&&!"".equals(account_name)){
				account.setSource("0");
				account.setBranch_id(0);
				account.setAccount_number(account_name);
				account.setAccount_pass(MD5.GetMD5Code("123456"));
				account.setAuthority(request.getParameter("authority"+i));
				account.setStatus("2");
				account.setRemark(request.getParameter("remark"+i));
				account.setCreate_date(date);
				account.setCreate_person(user.getName());
				mchService.saveMchAccountNumber(account);
			}
		}
		mchService.delCard(mchantNo);
		for(int i=1;i<=Integer.valueOf(mchcard_count);i++){
			MchCardKinds card = new MchCardKinds();
			card.setMch_number(mchantNo);
			card.setMch_name(mch_name);
			String ck_name = request.getParameter("ck_name"+i);
			if(ck_name!=null&!"".equals(ck_name)){
				card.setCk_name(ck_name);
				card.setCk_type(request.getParameter("ck_type"+i));
				card.setCk_quota((int)(Double.valueOf(request.getParameter("ck_quota"+i))*100.00));
				card.setSales_amount((int)(Double.valueOf(request.getParameter("sales_amount"+i))*100));
				if(card.getCk_type().equals("0")){
					float dis = (float)card.getSales_amount()/card.getCk_quota();
					DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
					String dist = df.format(dis);
					card.setDiscount(dist);
				}else{
					float dis = Float.valueOf(request.getParameter("discount"+i));
					DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
					String dist = df.format(dis);
					card.setDiscount(dist);
				}
				
			
				card.setPeriod_validity("36");
				card.setStatus("0");
				card.setCreate_date(date);
				card.setCreate_person(user.getName());
				mchService.saveMchCardKinds(card);
			}
		}
		request.setAttribute("mchantNo", mchantNo);
        request.setAttribute("mch_name", mch_name);
		return SUCCESS;
	}
	
	//B类消费折扣分配
	public String savemchantbackAllot() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");//商户编号
		String mch_name = request.getParameter("mch_name");//商户名称
		String a_allot = request.getParameter("a_allot");//用户A分配(单位百分比)
		String b_allot = request.getParameter("b_allot");//用户B分配(单位百分比)
		String o_allot = request.getParameter("o_allot");//用户B分配(单位百分比)
		String status  = "0";//状态0正常 1失效 9删除
		mchService.delAllot(mchantNo);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		Date date = new Date();
		MchBackAllot mAllot = new MchBackAllot();
		mAllot.setMch_number(mchantNo);
		mAllot.setA_allot(a_allot);
		mAllot.setB_allot(b_allot);
		mAllot.setO_allot(o_allot);
		mAllot.setStatus(status);
		mAllot.setCreate_date(date);
		mAllot.setCreate_person(user.getName());
		//保存信息
		mchService.saveAllot(mAllot);
		request.setAttribute("mchantNo", mchantNo);
		request.setAttribute("mch_name", mch_name);
		return SUCCESS;
	}
	
	//商户结算信息配置
	public String savemchantStmConfig() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		Date date = new Date();
		String mch_number = request.getParameter("mchantNo");//商户编号
		String mch_name = request.getParameter("mch_name");//商户名称
		String mch_account_card = request.getParameter("mch_account_card");//结算银行账号
		String mch_account_name = request.getParameter("mch_account_name");//账户名称
		String mch_bank_open_bank = request.getParameter("mch_bank_open_bank");//开户行
		String mch_bank_open_bank_no = request.getParameter("mch_bank_open_bank_no");//支行行号
		String mch_bank_open_addr = request.getParameter("mch_bank_open_addr");//开户地址
		String mch_account_type = request.getParameter("mch_account_type");//开户地址
		String mch_account_person = request.getParameter("mch_account_person");//联系人
		String mch_account_phone = request.getParameter("mch_account_phone");//联系电话
		String mch_email = request.getParameter("mch_email");//邮箱
		String mch_setment_type = request.getParameter("mch_setment_type");//结算类型：1全额  2非全额
		String mch_settlement_period = request.getParameter("mch_settlement_period");// 结算周期类型1 2 3 
		String mch_advance_quota = request.getParameter("mch_advance_quota");//公司垫款限额，单位分
		mchService.delStmConfig(mch_number);
		MchStmConfig config = new MchStmConfig();
		config.setMch_number(mch_number);
		config.setMch_name(mch_name);
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
		config.setCreate_date(date);
		config.setCreate_person(user.getName());
		mchService.saveMchStmConfig(config);
		mchService.setMchOpenType(mchantNo, "1");//商户开通时不购卡
		mchService.updateMchMerchant(mchantNo);
		response.sendRedirect("mchant_list.jhtml");
//		List<MchCardKinds> listCards = mchService.MchCardKindsDetail(mch_number);
//		request.setAttribute("listCards", listCards);
//		request.setAttribute("mchantNo", mch_number);
//		request.setAttribute("mchantName", mch_name);
		return null;
	}
	
	//修改商户状态为上线
	public String updateMchantStatus() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
        Date date = new Date();
		User user = (User) session.get("loginUser");
		String mchantNo = request.getParameter("mchantNo");
		String mchantName = request.getParameter("mchantName");
		String ck_id = request.getParameter("ck_id");//储值卡种id
		String card_count = request.getParameter("card_count");//购买张数
		String card_amount = request.getParameter("card_amount");//购买金额
		if(card_amount==null||"".equals(card_amount)||"NaN".equals(card_amount)||Integer.valueOf(card_amount)==0){
			//购买失败
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("0");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			throw new Exception("商户开通时，购卡金额出现异常，请更换火狐或者谷歌浏览器重试或者联系管理员！");
		}
		if(card_count==null||"".equals(card_count)){
			//购买失败
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("0");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			throw new Exception("商户开通时，无法获取购卡数量，请联系管理员！");
		}else{
			if(Integer.valueOf(card_count)>0){
				mchService.setMchOpenType(mchantNo, "0");//商户开通时购卡
				MchOpenPurchase op = new MchOpenPurchase();
				op.setMch_number(mchantNo);
				op.setCk_id(Integer.valueOf(ck_id));
				op.setCard_count(Integer.valueOf(card_count));
				op.setSales_amount(Integer.valueOf(card_amount)*100);
				op.setCreate_date(date);
				op.setCreate_person(user.getName());
				mchService.setMchOpenPurchase(op);
			}else{
				mchService.setMchOpenType(mchantNo, "1");//商户开通时不购卡
			}
		}
		InputStream inStream = CashOutImportAction.class.getResourceAsStream("/buy_card.properties");
		Properties prop = new Properties();  
		try {
			prop.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
			//购买失败
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("0");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			return "exception";
		}  
		String url = prop.getProperty("url")+"?mchNumber="+mchantNo+"&mchName="
				+mchantName+"&ckId="+ck_id+"&ckCount="+card_count+"&coId=1";
		System.out.println(url);
		String result = "";
		try{
			result = sendGet(url);//发送客户端接口，通知用户
		}catch (Exception e){
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("0");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			throw new Exception("发送购卡请求异常");
		}
		System.out.println(result);
		if(result==null){
			//购买失败
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("0");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			throw new Exception("购卡失败,发送购卡请求异常");
		}
		JSONObject a = new JSONObject(result); 
	    String resultCode = (String) a.get("code");
	    String resultMessage = (String) a.get("message");
	    System.out.println(resultMessage);
		if(resultCode!=null&&resultCode.trim().equals("-1")){
		//购买失败
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("0");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			throw new Exception("购卡失败");
		}
		if(resultCode!=null&&resultCode.trim().equals("0")){
		//购买成功
			mchService.updateMchMerchant(mchantNo);
			
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("1");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			//生成每日售卡统计数据
			MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);//查询商户的结算手续费
			String sale_poundage = mchant.getSale_poundage();
			StmMerchant stm = new StmMerchant();
			stm.setDate(sdf.parse(sdf.format(date)));
			stm.setMch_number(mchantNo);
			stm.setMch_name(mchantName);
			stm.setSale_card_number(card_count);
			stm.setSale_card_amount(Integer.valueOf(card_amount)*100);
			Double salePoundage = Double.valueOf(sale_poundage);
			int sale_card_fee = (int) (stm.getSale_card_amount()*salePoundage);
			stm.setSale_card_fee(sale_card_fee);
			int settlement_amount = stm.getSale_card_amount() - sale_card_fee;
			stm.setSettlement_amount(settlement_amount);
			stm.setCreate_date(date);
			stm.setCreate_person("system");
			stm.setType("0");
			stm.setStatus("0");
			String batch_number = sdf.format(date);
			stm.setBatch_number(batch_number);
			settleService.setOpenPuerDate(stm);
			//生成结算数据
			StmMchDetail sm = new StmMchDetail();
			sm.setMch_number(mchantNo);
			sm.setType("0");
			sm.setStart_date(sdf.parse(sdf.format(date)));
			sm.setEnd_date(sdf.parse(sdf.format(date)));
			sm.setUpper_dps_balance(0);
			sm.setCur_add_dps_amout(Integer.valueOf(card_amount)*100);
//			sm.setUpper_dps_date(upper_dps_date);//没有上结算日
			sm.setUpper_prepay(0);
			sm.setCur_consume(0);
			sm.setCur_arrears(0);
			sm.setAll_dps_balance(0);
			sm.setLast_to_this_day(0+"");
			MchStmConfig config = mchService.detailMchStmConfig(mchantNo);
			int mch_settlement_period = config.getMch_settlement_period();//结算周期
			List<Integer> listDate = new ArrayList<Integer>();
			if(mch_settlement_period==1){
				listDate.add(1);
			}
			if(mch_settlement_period==2){
				listDate.add(1);
				listDate.add(16);
			}
			if(mch_settlement_period==3){
				listDate.add(1);
				listDate.add(11);
				listDate.add(21);
			}
			int this_to_next_day = LastDate.lastDate(date, listDate);
			sm.setThis_to_next_day(this_to_next_day+"");//距离下个结算日多少天
//			sm.setThis_to_next_day(0);//距下个结算日（天）
			sm.setCur_prepay(Integer.valueOf(card_amount)*100);//本期实际预付(付款）
			sm.setCur_fee(sale_card_fee);
			sm.setCur_prepay_net(settlement_amount);
			sm.setStatus("0");
			sm.setCreate_date(date);
			sm.setCreate_person(user.getName());
			settleService.setStmMchDetail(sm);
		}
		
		response.sendRedirect("mchant_list.jhtml");
		return null;
	
	}
	
	//发请购卡请求
	static String sendGet(String url) throws Exception {
        String result = "";
        BufferedReader in = null;
        String urlNameString = url;
        URL realUrl = new URL(urlNameString);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> map = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : map.keySet()) {
            System.out.println(key + "--->" + map.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (in != null) {
            in.close();
        }
        return result;
    }
	
	//商户删除
	public String mchantDel() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String mchantNo = request.getParameter("mchantNo");
		mchService.mchantDel(mchantNo);
		response.sendRedirect("mchant_list.jhtml");
		return null;
	}
	//查看商户详情
	public String mchantDetail() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		List<FndCitys> listCity = mchService.MchCityRefDetail(mchantNo);
		List<MchAccountNumber> listMchantAccount = mchService.MchAccountNumberDetail(mchantNo);
		List<MchCardKinds> listCards = mchService.MchCardKindsDetail(mchantNo);
		List<MchPic> mchPic = mchService.MchPicDetail(mchantNo);
		String mchpicUrl = "";
		for(int i=0;i<mchPic.size();i++){
			mchpicUrl = mchpicUrl + mchPic.get(i).getPic_url()+"--";
		}
		List<MchBackAllot> listAllot = mchService.MchAllotDetail(mchantNo);
		MchStmConfig config = mchService.detailMchStmConfig(mchantNo);
		request.setAttribute("mchant", mchant);
		request.setAttribute("listCity", listCity);
		request.setAttribute("listMchantAccount", listMchantAccount);
		request.setAttribute("listCards", listCards);
		request.setAttribute("mchPic", mchpicUrl);
		request.setAttribute("listAllot", listAllot);
		request.setAttribute("stmConfig", config);
		return SUCCESS;
	}
	//查看商户购卡详情
	public String mchOpenPuerDetail(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		MchOpenPurchase op = mchService.findMchOpenPurchase(mchantNo);
		request.setAttribute("openPurchase", op);
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		request.setAttribute("mchant", mchant);
		return SUCCESS;
	}
	//商户初次购卡失败，重新购买
	public String mchantRepay() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
        Date date = new Date();
		User user = (User) session.get("loginUser");
		String mchantNo = request.getParameter("mchantNo");
		MchOpenPurchase op = mchService.findMchOpenPurchase(mchantNo);
		MchMerchant mchant = mchService.MchMerchantDetail(mchantNo);
		
		String mchantName = mchant.getMch_name();
		int ck_id = op.getCk_id();//卡种id
		int card_count = op.getCard_count();//购买张数
		InputStream inStream = CashOutImportAction.class.getResourceAsStream("/buy_card.properties");
		Properties prop = new Properties();  
		try {
			prop.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
			return "exception";
		}  
		String url = prop.getProperty("url")+"?mchNumber="+mchantNo+"&mchName="
				+mchantName+"&ckId="+ck_id+"&ckCount="+card_count+"&coId=1";
		System.out.println(url);
		String result = "";
		try{
			result = sendGet(url);//发送客户端接口，通知用户
		}catch (Exception e){
			throw new Exception("发送购卡请求异常");
		}
		System.out.println(result);
		if("".equals(result)){
			//购买失败
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("0");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			throw new Exception("购卡失败,发送购卡请求异常");
		}
		JSONObject a = new JSONObject(result); 
		String resultCode = (String) a.get("code");
	    String resultMessage = (String) a.get("message");
	    System.out.println(resultMessage);
		if(resultCode!=null&&resultCode.trim().equals("-1")){
		//购买失败
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("0");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			throw new Exception("购卡失败");
		}
		if(resultCode!=null&&resultCode.trim().equals("0")){
			mchService.updateMchMerchant(mchantNo);
			//购买成功
			MchOpenPurchase ops = new MchOpenPurchase();
			ops.setStatus("1");
			ops.setUpdate_date(date);
			ops.setUpdate_person(user.getName());
			ops.setMch_number(mchantNo);
			mchService.updateMchOpenPurchase(ops);
			
			//生成每日售卡数据
			String batch_number = sdf.format(date);
			String sale_poundage = mchant.getSale_poundage();//商户手续费
			StmMerchant stm = new StmMerchant();
			stm.setDate(sdf.parse(sdf.format(date)));
			stm.setMch_number(mchantNo);
			stm.setMch_name(mchantName);
			stm.setSale_card_number(op.getCard_count()+"");
			stm.setSale_card_amount(Integer.valueOf(op.getSales_amount()));
			Double salePoundage = Double.valueOf(sale_poundage);
			int sale_card_fee = (int) (stm.getSale_card_amount()*salePoundage);
			stm.setSale_card_fee(sale_card_fee);
			int settlement_amount = stm.getSale_card_amount() - sale_card_fee;
			stm.setSettlement_amount(settlement_amount);
			stm.setCreate_date(date);
			stm.setCreate_person("system");
			stm.setType("0");
			stm.setStatus("0");
			stm.setBatch_number(batch_number);
			settleService.setOpenPuerDate(stm);
			//生成结算数据
			StmMchDetail sm = new StmMchDetail();
			sm.setMch_number(mchantNo);
			sm.setType("0");
			sm.setStart_date(sdf.parse(sdf.format(date)));
			sm.setEnd_date(sdf.parse(sdf.format(date)));
			sm.setUpper_dps_balance(0);
			sm.setCur_add_dps_amout(op.getSales_amount());
	//		sm.setUpper_dps_date(upper_dps_date);//没有上结算日
			sm.setUpper_prepay(0);
			sm.setCur_consume(0);
			sm.setCur_arrears(0);
			sm.setAll_dps_balance(0);
			sm.setLast_to_this_day(0+"");
			MchStmConfig config = mchService.detailMchStmConfig(mchantNo);
			int mch_settlement_period = config.getMch_settlement_period();//结算周期
			List<Integer> listDate = new ArrayList<Integer>();
			if(mch_settlement_period==1){
				listDate.add(1);
			}
			if(mch_settlement_period==2){
				listDate.add(1);
				listDate.add(16);
			}
			if(mch_settlement_period==3){
				listDate.add(1);
				listDate.add(11);
				listDate.add(21);
			}
			int this_to_next_day = LastDate.lastDate(date, listDate);
			sm.setThis_to_next_day(this_to_next_day+"");//距离下个结算日多少天
	//		sm.setThis_to_next_day(0);//距下个结算日（天）
			sm.setCur_prepay(op.getSales_amount());//本期实际预付(付款）
			sm.setCur_fee(sale_card_fee);
			sm.setCur_prepay_net(settlement_amount);
			sm.setStatus("0");
			sm.setCreate_date(date);
			sm.setCreate_person(user.getName());
			settleService.setStmMchDetail(sm);
			
			response.sendRedirect("mchOpenPuerDetail.jhtml?mchantNo="+mchantNo);
		}
		return null;
	}
	
	
	//查看图片
	public String mchantSeepic(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String url = request.getParameter("imgUrl");
		String[] urlArray = url.split("--");
		PrintWriter out;
		try {
			out = response.getWriter();
			for(int i=0;i<urlArray.length;i++){
				System.out.println("<img src=\""+urlArray[i]+"\" ></img>");
				out.write("<img src=\""+urlArray[i]+"\" ></img>");
				out.write("<br/>");
				out.write("<br/>");
				out.write("<br/>");
			}
			out.write("<input type=\"button\" value=\"返回\" onclick=\"javascript:history.back()\"/>");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}// 执行保存
		return null;
	} 

	//商户品牌信息列表
	public String mchantBrandList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		MchantFilter filter = new MchantFilter();
		String mchantName = request.getParameter("mchantName");
		String mchantNo = request.getParameter("mchantNo");
		filter.setMchantName(mchantName);
		filter.setMchantNo(mchantNo);
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);

		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = mchService.MchantBrandList(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//进入新增品牌信息
	public String addBrand(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		request.setAttribute("mchantNo",mchantNo);
		return SUCCESS;
	}
	
	//新增品牌信息保存
	public String addBrandCommit() throws IOException{
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		Map<String,String> map = new HashMap<String,String>();
		if(brandPic!=null){
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
			String filePath = uperRoot+"/pinpai/";
			UnZip.unzipFile(filePath, brandPic);
			
			File unzipFile = new File(filePath);
			String[] filelist = unzipFile.list();
			
	        for (int k = 0; k < filelist.length; k++) {
	        	File readfile = new File(unzipFile + "/" + filelist[k]);
				String pic_url_name = mchantNo+k+"mch_pinpai.png";//商户图片名字
				UpLoadPic.putObject(pic_url_name, readfile);
		        int index = readfile.getName().indexOf('.');
				String id = readfile.getName().trim().substring(0,index);
		        map.put(id, url+"/"+dir+"/"+pic_url_name);
	        }
	        UnZip.delAllFile(filePath);
		}
		
		String encode_info = input_info;
		for(Map.Entry<String, String> entry : map.entrySet()){    
			String key = entry.getKey();
			String url = entry.getValue();
			encode_info = encode_info.replace("<img"+key+">", "<img src='"+url+"' />");
		} 

		System.out.println(encode_info);
	    MchBrand mchb = new MchBrand();
	    mchb.setMch_number(mchantNo);
	    mchb.setBrand_info(brand_info);
	    mchb.setInput_info(input_info);
	    System.out.println(encode_info);
	    mchb.setEncode_info(encode_info);
	    mchb.setCreate_date(new Date());
	    mchService.saveBrand(mchb);
		response.sendRedirect("mchantBrandList.jhtml?mchantNo="+mchantNo);
		return null;
	}
	
	//删除品牌信息
	public String delBrand() throws IOException{
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchantNo = request.getParameter("mchantNo");
		mchService.delBrand(mchantNo.trim());
		response.sendRedirect("mchantBrandList.jhtml?mchantNo="+mchantNo);
		return null;
		
	}
	
	//查看品牌信息
	public String brandDetail(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchNumber = request.getParameter("mchantNo");
		MchBrand mchBrand = mchService.MchantBrand(mchNumber);
		request.setAttribute("MchantBrand", mchBrand);
		return SUCCESS;
	}
	
	public String cityList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String cityName = request.getParameter("cityName");
		String cityId = request.getParameter("cityId");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		CityFilter filter = new CityFilter();
		filter.setCityName(cityName);
		filter.setCityId(cityId);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);

		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = mchService.cityList(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	public String changeCity() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String cityId = request.getParameter("cityId");
		String flag = request.getParameter("flag");
		mchService.changeCity(cityId, flag);
		response.sendRedirect("cityList.jhtml?cityId="+cityId);
		return null;
	}
	
	public String getMch_name() {
		return mch_name;
	}

	public void setMch_name(String mch_name) {
		this.mch_name = mch_name;
	}

	public String getCityString() {
		return cityString;
	}

	public void setCityString(String cityString) {
		this.cityString = cityString;
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

	public String getCityNameString() {
		return cityNameString;
	}

	public void setCityNameString(String cityNameString) {
		this.cityNameString = cityNameString;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMch_group_id() {
		return mch_group_id;
	}

	public void setMch_group_id(String mch_group_id) {
		this.mch_group_id = mch_group_id;
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

	public String getMchantNo() {
		return mchantNo;
	}

	public void setMchantNo(String mchantNo) {
		this.mchantNo = mchantNo;
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

	public void setBrandPic(File brandPic) {
		this.brandPic = brandPic;
	}

	public void setBrand_info(String brand_info) {
		this.brand_info = brand_info;
	}

	public void setInput_info(String input_info) {
		this.input_info = input_info;
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

	public void setSale_card_type(String sale_card_type) {
		this.sale_card_type = sale_card_type;
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

	public void setCapita_consumption(String capita_consumption) {
		this.capita_consumption = capita_consumption;
	}
	
}
