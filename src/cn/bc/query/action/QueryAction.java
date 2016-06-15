package cn.bc.query.action;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.bc.business.po.OnCashOuts;
import cn.bc.business.po.OnUser;
import cn.bc.business.vo.OnCashOutsFilter;
import cn.bc.common.support.page.Page;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.query.bo.PlatFormRE;
import cn.bc.query.bo.PlatShuoru;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.CoAccountBill;
import cn.bc.query.po.CoDepositCard;
import cn.bc.query.po.OnBankCard;
import cn.bc.query.po.OnDepositCard;
import cn.bc.query.po.OnSvcSpendBill;
import cn.bc.query.po.PayCardCount;
import cn.bc.query.services.QueryServices;
import cn.bc.query.vo.CoAccountBillFilter;
import cn.bc.query.vo.DepositCardFilter;
import cn.bc.query.vo.Filter;
import cn.bc.query.vo.OrderPayBillFilter;
import cn.bc.query.vo.OrderSubPayCardFilter;
import cn.bc.query.vo.PrepaidCardDetails;
import cn.bc.query.vo.SvcSpendBillFilter;
import cn.bc.query.vo.TransDataBo;
import cn.bc.query.vo.TransDataFilter;
import cn.bc.query.vo.TransactionDetail;
import cn.bc.query.vo.UserFilter;
import cn.bc.settlement.vo.StmMerchantFilter;
import cn.bc.util.ExportExl;
import cn.common.security.vo.User;

public class QueryAction extends BaseAction{
	private QueryServices querySer;
	
	//查询用户列表
	public String UserList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		UserFilter filter = new UserFilter();
		String full_name = request.getParameter("full_name");
		String mobile_phone = request.getParameter("mobile_phone");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String orderFlag = request.getParameter("orderFlag");
		filter.setFull_name(full_name);
		filter.setMobile_phone(mobile_phone);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setOrderFlag(orderFlag);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.listUser(filter);
		List<Object> list = querySer.listUserBalance(filter);
		BigDecimal bal = (BigDecimal) list.get(0);
		int accounts_balance = bal.intValue();
		request.setAttribute("accounts_balance", accounts_balance);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//用户绑卡信息
	public String bankCardList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String user_id = request.getParameter("user_id");
		List<OnBankCard> list = new ArrayList<OnBankCard>();
		if(user_id!=null&&!"".equals(user_id)){
			list = querySer.listBankCard(user_id.trim());
		}
		request.setAttribute("cardList",list);
		return SUCCESS;
	}
	
	//个人储值卡列表
	public String onDepositCardList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		DepositCardFilter filter = new DepositCardFilter();
		String full_name = request.getParameter("full_name");
		String mobile_phone = request.getParameter("mobilePhone");
		String svc_number = request.getParameter("svc_number");
		String mch_name = request.getParameter("mch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setFull_name(full_name);
		filter.setMobile_phone(mobile_phone);
		filter.setSvc_number(svc_number);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMch_name(mch_name);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.listDepositCard(filter);
		List<OnDepositCard> list = querySer.DepositCard(filter);
		int balance = 0;
		for(int i=0;i<list.size();i++){
			balance = balance + list.get(i).getSvc_balance();
		}
		request.setAttribute("balance", balance);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//平台储值卡列表
	public String coDepositCardList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		DepositCardFilter filter = new DepositCardFilter();
		String full_name = request.getParameter("full_name");
		String mobile_phone = request.getParameter("mobilePhone");
		String svc_number = request.getParameter("svc_number");
		String mch_name = request.getParameter("mch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setFull_name(full_name);
		filter.setMobile_phone(mobile_phone);
		filter.setSvc_number(svc_number);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMch_name(mch_name);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.listCoDepositCard(filter);
		List<CoDepositCard> list = querySer.coDepositCard(filter);
		int amount = 0;
		for(int i=0;i<list.size();i++){
			amount = amount + list.get(i).getSvc_balance();
		}
		request.setAttribute("amount", amount);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//储值卡交易
	public String onSpendBillList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		SvcSpendBillFilter filter = new SvcSpendBillFilter();
		String full_name = request.getParameter("full_name");
		String mobile_phone = request.getParameter("mobilePhone");
		String svc_number = request.getParameter("svc_number");
		String mch_name = request.getParameter("mch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String type = request.getParameter("type");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setFull_name(full_name);
		filter.setMobile_phone(mobile_phone);
		filter.setSvc_number(svc_number);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMch_name(mch_name);
		filter.setType(type);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		List<OnSvcSpendBill> list = querySer.OnSvcSpendBillList(filter);
		int amount = 0;
		for(int i=0;i<list.size();i++){
			amount = amount + list.get(i).getSpend_amount();
		}
		
		Page page = querySer.listSvcSpendBill(filter);
		request.setAttribute("amount", amount);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//售卡统计
	public String saleCardTotal(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String detailDate = request.getParameter("detailDate");
		String mch_name = request.getParameter("mch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		OrderSubPayCardFilter filter = new OrderSubPayCardFilter();
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMch_name(mch_name);
		filter.setDetailDate(detailDate);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		//售卡统计
		if(detailDate==null||"".equals(detailDate)){
			filter.setFlag("a");
			Page page = querySer.listOrderPayCard(filter);
			List<PayCardCount> list = querySer.listTotle(filter);
			int count = 0;
			int amount = 0;
			int ck_quate = 0;
			for(int i=0;i<list.size();i++){
				count = count + list.get(i).getAllCount().intValue();
				amount = amount + list.get(i).getAmout().intValue();
				ck_quate = ck_quate + list.get(i).getAllck_ck_quota().intValue();
			}
			request.setAttribute("count", count);
			request.setAttribute("amount", amount);
			request.setAttribute("ck_quate", ck_quate);
			request.setAttribute("page", page);
			return SUCCESS;
		}else{
		//查询售卡详情
			filter.setFlag("b");
			filter.setDetailDate(detailDate);
			Page page = querySer.listOrderPayCard(filter);
			request.setAttribute("page", page);
			return "datail";
		}
	}
	//储值用户交易
	public String listApayBill(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mobile_phone = request.getParameter("mobile_phone");
		String mch_name = request.getParameter("mch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		OrderPayBillFilter filter = new OrderPayBillFilter();
		filter.setMobile_phone(mobile_phone);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMch_name(mch_name);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		
		Page page = querySer.listApayBill(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}

	//普通用户交易
	public String listBpayBill() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mobile_phone = request.getParameter("mobile_phone");
		String mch_name = request.getParameter("mch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String firstFlag = request.getParameter("firstFlag");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		OrderPayBillFilter filter = new OrderPayBillFilter();
		filter.setMch_name(mch_name);
		filter.setMobile_phone(mobile_phone);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if(firstFlag!=null&&!"all".equals(firstFlag)){
			filter.setFirstFlag(firstFlag);
		}
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.listBpayBill(filter);
//		List<OrderPayBill> list2 =  querySer.listpayBill(filter);
//		List<OrderPayBill> list = (List<OrderPayBill>) page.getResult();
//		for(int i=0;i<list.size();i++){
//			OrderPayBill opb = list.get(i);
//			opb.setFirstFlag("1");
//			for(int j=0;j<list2.size();j++){
//				if(opb.getMobile_phone().equals(list2.get(j).getMobile_phone())&&
//						opb.getMch_number().equals(list2.get(j).getMch_number())&&
//						Long.valueOf(opb.getOrder_number().trim())>Long.valueOf(list2.get(j).getOrder_number().trim())){
//					opb.setFirstFlag("0");
//					break;
//				}
//			}
//		}
//		page.setData(list);
		request.setAttribute("page", page);
		return SUCCESS;
		
	}
	
	//普通用户消费详情
	public String detailBpayBill(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String orderNumber = request.getParameter("orderNumber");
		String orderType = request.getParameter("orderType");
		String mobile = request.getParameter("mobile");
		List<OnSvcSpendBill> list = querySer.DetailBpayBill(orderNumber,mobile);
		request.setAttribute("list", list);
		request.setAttribute("orderNumber", orderNumber);
		request.setAttribute("orderType", orderType);
		return SUCCESS;
	}
	
	public String listCoAccountBill(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		CoAccountBillFilter filter = new CoAccountBillFilter();
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.listCoAccountBill(filter);
		CoAccount bill = querySer.CoAccountBillTotal();
		request.setAttribute("bill", bill);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	public String listCoAccountBill2(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		CoAccountBillFilter filter = new CoAccountBillFilter();
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.listCoAccountBill(filter);
		CoAccount bill = querySer.CoAccountBillTotal();
		request.setAttribute("bill", bill);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//商户每日购卡统计
	public String listStmMerchant(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		StmMerchantFilter filter =new StmMerchantFilter();
		String mchantNo = request.getParameter("mchantNo");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setMch_number(mchantNo);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.listStmMerchant(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//查询平台收支明细
	public String listPlatform() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		Filter filter =new Filter();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.listPlatform(filter);
		List<PlatFormRE> list = querySer.platFormTotle(filter);
		int weixin = 0;//微信总手续费
		int tixian = 0;//提现总手续费
		int zc = 0;//总支出 
		int sh = 0;//商户总手续费
		int pt = 0;//平台总返现
		int sr = 0;//平台总收入
		int jsr = 0 ;//平台总净收入=总收入-总支出
		for(int i=0;i<list.size();i++){
			weixin = weixin + list.get(i).getWeixinAmount();
			tixian = tixian + list.get(i).getTixianAmount();
			sh = sh + list.get(i).getMachantAmount();
			pt = pt + list.get(i).getPlatAmount();
		}
		zc = zc + weixin + tixian;
		sr = sr + sh + pt;
		jsr = sr - zc;
		PlatFormRE pfr = new PlatFormRE();
		pfr.setWeixinAmount(weixin);
		pfr.setTixianAmount(tixian);
		pfr.setZhichu(zc);
		pfr.setMachantAmount(sh);
		pfr.setPlatAmount(pt);
		pfr.setShouru(sr);
		pfr.setAll(jsr);
		request.setAttribute("PlatFormRE", pfr);
		request.setAttribute("page", page);
		return SUCCESS;
	}

	//公司账户收支统计
	public String listCoAccount() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		Filter filter =new Filter();
		String type = request.getParameter("type");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		filter.setType(type);
		Page page = querySer.listCoAccountBill(filter);
		List<CoAccountBill> list = querySer.listCoAccount(filter);
		int sr = 0;//收入
		int zc = 0;//支出
		for(int i=0;i<list.size();i++){
			if("0".equals(list.get(i).getType())){
				sr = sr + list.get(i).getAmount();
			}
			if("1".equals(list.get(i).getType())){
				zc = zc + list.get(i).getAmount();
			}
		}
		CoAccount co = querySer.CoAccountBillTotal();
		request.setAttribute("sr", sr);
		request.setAttribute("zc", zc);
		request.setAttribute("page", page);
		request.setAttribute("balance", co.getAccount_balance());
		return SUCCESS;
	}
	
	//公司账户收支统计详情
	public String coAccountDetail(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String date = request.getParameter("date");
		String type = request.getParameter("type");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		Filter filter =new Filter();
		filter.setBeginTime(date);
		filter.setType(type);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.listCoAccountDetail(filter);
		request.setAttribute("date", date);
		request.setAttribute("type", type);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	//公司账户充值
	public String addCoAccountBlance() throws Exception{
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String amountStr = request.getParameter("amount");
		int amount = Integer.valueOf(amountStr);
		querySer.addCoAccountBlance(amount*100,user.getName());
		response.sendRedirect("listCoAccountCz.jhtml");
		return null;
	}
	
	public String listCoAccountCz() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		Filter filter =new Filter();
		String type = request.getParameter("type");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		filter.setType(type);
		Page page = querySer.listCoAccountCz(filter);
		List<CoAccountBill> list = querySer.CoAccountCz(filter);
		int cz = 0;//收入
		for(int i=0;i<list.size();i++){
			cz = cz + list.get(i).getAmount();
		}
		request.setAttribute("cz", cz);
		request.setAttribute("page", page);
		CoAccount coAccount = querySer.CoAccountBillTotal();
		request.setAttribute("coAccount", coAccount);
		return SUCCESS;
	}
	
	//用户买单信息
	public String userPayInfo(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		Filter filter =new Filter();
		String user_id = request.getParameter("user_id");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.userPayInfo(filter, user_id);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//交易统计
	public String transData(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		TransDataFilter filter = new TransDataFilter();
		String mchName = request.getParameter("mchName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMchName(mchName);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		
		List<TransDataBo> list =  querySer.transDataList(filter);
		int renshu = 0;
		int bishu = 0;
		int bussiness_amount = 0;
		int svc_pay_amount = 0;
		int account_pay_amount=0;
		int pay_amount = 0;
		int o_return  =0;
		int acturl_amout =0;
		TransDataBo bo = new TransDataBo();
		for(int i=0;i<list.size();i++){
			renshu = renshu + list.get(i).getRenshu();
			bishu = bishu + list.get(i).getBishu();
			bussiness_amount = bussiness_amount + list.get(i).getBussiness_amount();
			svc_pay_amount = svc_pay_amount + list.get(i).getSvc_pay_amount();
			account_pay_amount = account_pay_amount + list.get(i).getAccount_pay_amount();
			pay_amount = pay_amount + list.get(i).getPay_amount(); 
			o_return = o_return + list.get(i).getCofanxian();
			acturl_amout = acturl_amout + list.get(i).getFanxian();
		}
		bo.setAllrenshu(renshu);
		bo.setTwomd(querySer.getData(filter).getTwomd());
		bo.setAllBishu(bishu);
		bo.setAll_bussiness_amount(bussiness_amount);
		bo.setSvc_pay_amount(svc_pay_amount);
		bo.setAccount_pay_amount(account_pay_amount);
		bo.setAll_pay_amount(pay_amount);
		bo.setCofanxian(o_return);
		bo.setFanxian(acturl_amout);
		
		Page page = querySer.transData(filter);
		request.setAttribute("page", page);
		request.setAttribute("TransDataBo", bo);
		return SUCCESS;
	}
	
	
	//购卡统计
	public String userBuyCard(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		TransDataFilter filter = new TransDataFilter();
		String mchName = request.getParameter("mchName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMchName(mchName);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		List<TransDataBo> list =  querySer.userBuyCardList(filter);
		Page page = querySer.userBuyCard(filter);
		request.setAttribute("page", page);
		TransDataBo bo = new TransDataBo();
		if(list!=null&&list.size()>0){
			bo = list.get(0);
		}
		request.setAttribute("TransDataBo", bo);
		request.setAttribute("page", page);
		
		return SUCCESS;
	}
		
	//公司购卡统计
	public String gsBuyCard(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		TransDataFilter filter = new TransDataFilter();
		String mchName = request.getParameter("mchName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMchName(mchName);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		List<TransDataBo> list =  querySer.gsBuyCardList(filter);
		int bishu = 0;
		int bussiness_amount = 0;
		int pay_amount = 0;
		TransDataBo bo = new TransDataBo();
		for(int i=0;i<list.size();i++){
			bishu = bishu + list.get(i).getBishu();
			bussiness_amount = bussiness_amount + list.get(i).getBussiness_amount();
			pay_amount = pay_amount + list.get(i).getPay_amount();
		}
		bo.setAllBishu(bishu);
		bo.setAll_bussiness_amount(bussiness_amount);
		bo.setAll_pay_amount(pay_amount);
		Page page = querySer.gsBuyCard(filter);
		request.setAttribute("page", page);
		request.setAttribute("TransDataBo", bo);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//用户行为统计
	public String userList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		TransDataFilter filter = new TransDataFilter();
		String mchName = request.getParameter("mchName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMchName(mchName);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		List<TransDataBo> list =  querySer.userTransList(filter);
		int weixin = 0;//微信关注总数
		int qg = 0;//取消关注总数
		int twomd = 0;//买单2次及以上人数
		int twomk = 0;//购卡2张及以上的人数
		int bishu = 0;//注册数
		int bussiness_amount = 0;//买单总数
		int pay_amount = 0;//买卡人数
		TransDataBo bo = new TransDataBo();
		for(int i=0;i<list.size();i++){
			weixin = weixin + list.get(i).getWeixin();
			qg = qg + list.get(i).getQg();
			twomd = twomd + list.get(i).getTwomd();
			twomk = twomk + list.get(i).getTwomk();
			bishu = bishu + list.get(i).getBishu();
			bussiness_amount = bussiness_amount + list.get(i).getBussiness_amount();
			pay_amount = pay_amount + list.get(i).getPay_amount();
		}
		bo.setWeixin(weixin);
		bo.setQg(qg);
		bo.setTwomd(twomd);
		bo.setTwomk(twomk);
		bo.setBishu(bishu);
		bo.setBussiness_amount(bussiness_amount);
		bo.setPay_amount(pay_amount);
		
		Page page = querySer.userTrans(filter);
		request.setAttribute("page", page);
		request.setAttribute("TransDataBo", bo);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	/*
	 * 查询统计修改
	 * 交易明细
	 */
	public String transactionDetail(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mobile_phone = request.getParameter("mobile_phone");
		String mch_name = request.getParameter("mch_name");
		String branch_name = request.getParameter("branch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String firstFlag = request.getParameter("firstFlag");
		String status = request.getParameter("status");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		OrderPayBillFilter filter = new OrderPayBillFilter();
		if(status!=null&&!"all".equals(status)){
			filter.setStatus(status);
		}
		filter.setMch_name(mch_name);
		filter.setBranch_name(branch_name);
		filter.setMobile_phone(mobile_phone);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if(firstFlag!=null&&!"all".equals(firstFlag)){
			filter.setFirstFlag(firstFlag);
		}
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.transactionDetail(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//交易明细导出
	public String transactionDetailExport() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
		String mobile_phone = request.getParameter("mobile_phone");
		String mch_name = request.getParameter("mch_name");
		String branch_name = request.getParameter("branch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String firstFlag = request.getParameter("firstFlag");
		String status = request.getParameter("status");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		OrderPayBillFilter filter = new OrderPayBillFilter();
		if(status!=null&&!"all".equals(status)){
			filter.setStatus(status);
		}
		filter.setMch_name(mch_name);
		filter.setBranch_name(branch_name);
		filter.setMobile_phone(mobile_phone);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if(firstFlag!=null&&!"all".equals(firstFlag)){
			filter.setFirstFlag(firstFlag);
		}
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		List<TransactionDetail> list = querySer.transactionDetailExport(filter);
		
		DateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
		OutputStream os = null;
		try {
			HSSFWorkbook wb = ExportExl.exportTransData(list);
			String getTime = format2.format(new Date());
			String filename = getTime + "交易明细.xls";
			os = response.getOutputStream();
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename="+new String(filename.getBytes("gb2312"),"iso8859-1"));
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出提现文件失败！");
		}
		finally{
			if(os!=null)
				os.close();
		}
		return null;
	}
	
	/*
	 * 储值明细
	 */
	public String prepaidCardDetails(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		DepositCardFilter filter = new DepositCardFilter();
		String full_name = request.getParameter("full_name");
		String mobile_phone = request.getParameter("mobilePhone");
		String svc_number = request.getParameter("svc_number");
		String mch_name = request.getParameter("mch_name");
		String branch_name = request.getParameter("branch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize"); 
		String firstFlag = request.getParameter("firstFlag"); 
		if(firstFlag!=null&&!"all".equals(firstFlag)){
			filter.setFirstFlag(firstFlag);
		}
		filter.setFull_name(full_name);
		filter.setMobile_phone(mobile_phone);
		filter.setSvc_number(svc_number);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMch_name(mch_name);
		filter.setBranch_name(branch_name);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		
		Page page = querySer.prepaidCardDetails(filter);
		request.setAttribute("page", page);
		int balance = Integer.valueOf(querySer.cardBalance());
		request.setAttribute("balance", balance);
		return SUCCESS;
	}
	
	//储值明细导出
	public String prepaidCardDetailsExport() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
		DepositCardFilter filter = new DepositCardFilter();
		String full_name = request.getParameter("full_name");
		String mobile_phone = request.getParameter("mobilePhone");
		String svc_number = request.getParameter("svc_number");
		String mch_name = request.getParameter("mch_name");
		String branch_name = request.getParameter("branch_name");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize"); 
		String firstFlag = request.getParameter("firstFlag"); 
		if(firstFlag!=null&&!"all".equals(firstFlag)){
			filter.setFirstFlag(firstFlag);
		}
		filter.setFull_name(full_name);
		filter.setMobile_phone(mobile_phone);
		filter.setSvc_number(svc_number);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setMch_name(mch_name);
		filter.setBranch_name(branch_name);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		List<PrepaidCardDetails> list =  querySer.prepaidCardDetailsExport(filter);
		DateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
		OutputStream os = null;
		try {
			HSSFWorkbook wb = ExportExl.prepaidCardDetailsExport(list);
			String getTime = format2.format(new Date());
			String filename = getTime + "储值明细.xls";
			os = response.getOutputStream();
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename="+new String(filename.getBytes("gb2312"),"iso8859-1"));
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出提现文件失败！");
		}
		finally{
			if(os!=null)
				os.close();
		}
		return null;
	}
	
	public String cardUserDetail(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String fullName = request.getParameter("fullName");
		String mobile = request.getParameter("mobile");
		String mchName = request.getParameter("mchName");
		String ck_name = request.getParameter("ck_name");
		String cardNumber = request.getParameter("cardNumber");
		String sales_amount = request.getParameter("sales_amount");
		Filter filter = new Filter();
		filter.setCardNumber(cardNumber);
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize"); 
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.cardUserDetail(filter);
		request.setAttribute("fullName", fullName);
		request.setAttribute("mobile", mobile);
		request.setAttribute("mchName", mchName);
		request.setAttribute("ck_name", ck_name);
		request.setAttribute("cardNumber", cardNumber);
		request.setAttribute("sales_amount", sales_amount);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//平台收入每日统计导出数据
	public String listPlatformExport() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
		Filter filter =new Filter();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		
		List<PlatFormRE> list = querySer.listPlatformExport(filter);
		DateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
		OutputStream os = null;
		try {
			HSSFWorkbook wb = ExportExl.listPlatformExport(list);
			String getTime = format2.format(new Date());
			String filename = getTime + "平台收入每日统计.xls";
			os = response.getOutputStream();
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename="+new String(filename.getBytes("gb2312"),"iso8859-1"));
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出提现文件失败！");
		}
		finally{
			if(os!=null)
				os.close();
		}
		return null;
	}
	
	//平台收入统计数据导出
	public String platShouru(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		Filter filter =new Filter();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = querySer.platShouru(filter);
		
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//平台收入统计导出
	public String listPlatformShouruExport() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
		Filter filter =new Filter();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		List<PlatShuoru> list = querySer.platShouruList(filter);
		
		DateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
		OutputStream os = null;
		try {
			HSSFWorkbook wb = ExportExl.listPlatListExport(list);
			String getTime = format2.format(new Date());
			String filename = getTime + "平台收入每日统计.xls";
			os = response.getOutputStream();
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename="+new String(filename.getBytes("gb2312"),"iso8859-1"));
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出提现文件失败！");
		}
		finally{
			if(os!=null)
				os.close();
		}
		return null;
	}
	
	
	public void setQuerySer(QueryServices querySer) {
		this.querySer = querySer;
	}
	
}
