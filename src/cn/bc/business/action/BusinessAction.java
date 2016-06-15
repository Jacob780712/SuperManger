package cn.bc.business.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.bc.business.po.IncomeAnalysisConfig;
import cn.bc.business.po.OnReChange;
import cn.bc.business.services.BusinessServices;
import cn.bc.business.vo.AnalysDetailListBo;
import cn.bc.business.vo.AnalysFilter;
import cn.bc.business.vo.AnalysTongJiBo;
import cn.bc.business.vo.OnReChangeFilter;
import cn.bc.common.support.page.Page;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.mchant.services.MchantServices;
import cn.bc.query.services.QueryServices;
import cn.bc.query.vo.Filter;
import cn.common.security.vo.User;

public class BusinessAction extends BaseAction{
	private BusinessServices bser;

	public void setBser(BusinessServices bser) {
		this.bser = bser;
	}
	
	
	public String investmentAnalysis(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchName = request.getParameter("mchName");
		String orderFlag = request.getParameter("orderFlag");//0 按照商户名称倒序 ， 00 按照商户名称顺序
															 //1 按照返现周期倒序，11 按照返现周期顺序
															 //2 按照月化收益倒序，22 按照月化收益顺序
		String status = request.getParameter("status");//卡种状态，0上架  1下架
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		AnalysFilter filter = new AnalysFilter();
		if(status!=null&&!"all".equals(status)){
			filter.setStatus(status);
		}
		filter.setMchName(mchName);
		filter.setOrderFlag(orderFlag);
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		
		IncomeAnalysisConfig config = bser.getIncomeAnalysisConfig();
		int range = config.getRanges();
		Page page = bser.getAnalyis(filter,range);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	public String investmentAnalysisDetail(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchNumber = request.getParameter("mchNumber");
		String mchName = request.getParameter("detailmchName");
		AnalysFilter filter = new AnalysFilter();
		filter.setMchNumber(mchNumber);
		Map<String, Object> map = bser.getAnalyisDetail(filter);
		List<AnalysDetailListBo> listTotalResult = (List<AnalysDetailListBo>) map.get("listTotalResult");
		AnalysTongJiBo bo = (AnalysTongJiBo) map.get("tongji"); 
		int zhangshu = 0;
		int miane = 0;
		int saleAmount = 0;
		int balance = 0;
		int acturlAmout = 0;
		for(int i=0;i<listTotalResult.size();i++){
			AnalysDetailListBo bbs = listTotalResult.get(i);
			zhangshu = zhangshu + bbs.getZhangshu();
			miane = miane + + bbs.getAllMiane();
			saleAmount = saleAmount + bbs.getAllSaleAmount();
			balance = balance + bbs.getBalance();
			acturlAmout = acturlAmout + bbs.getFanxian();
		}
		bo.setZhangshu(zhangshu);
		bo.setMiane(miane);
		bo.setSaleAmount(saleAmount);
		bo.setBalance(balance);
		bo.setActurlAmout(acturlAmout);
		
		request.setAttribute("listTotalResult", listTotalResult);//列表数据
		request.setAttribute("bo", bo);//列表数据
		request.setAttribute("mchName", mchName);
		return SUCCESS;
	}
	
	//储值收益计算配置
	public String investmentAnalysisConfig(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String type = request.getParameter("type");
		if(type==null||"".equals(type)){
			type="select";
		}
		IncomeAnalysisConfig config = bser.getIncomeAnalysisConfig();
		if(config!=null){
			request.setAttribute("config", config);
		}else{
			request.setAttribute("config", new IncomeAnalysisConfig());
		}
		request.setAttribute("type", type);
		return SUCCESS;
	}
	public String investmentAnalysisConfigSave() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String type = request.getParameter("type");
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		IncomeAnalysisConfig config =  new IncomeAnalysisConfig();
		Date date = new Date();
		String ranges_str = request.getParameter("ranges");
		int ranges = Integer.valueOf(ranges_str);
		config.setRanges(ranges);
		config.setCreate_date(date);
		config.setCreate_person(user.getName());
		config.setUpdate_date(date);
		config.setUpdate_person(user.getName());
		bser.saveIncomeAnalysisConfig(config);
		request.setAttribute("config", config);
		request.setAttribute("type", "select");
		return SUCCESS;
	}
	
	public String rechangeList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		OnReChangeFilter filter = new OnReChangeFilter();
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String mobilePhone = request.getParameter("mobilePhone");
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		filter.setMobilePhone(mobilePhone);
		Page page = bser.reChangeList(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	public String gotoRechange(){
		return SUCCESS;
	}
	
	public String rechangeSubmit() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		String mobile = request.getParameter("mobile");
		String amount = request.getParameter("amount");
		String type = request.getParameter("type");
		String remark = request.getParameter("remark");
		OnReChange onReChange = new OnReChange();
		onReChange.setMobile(mobile);
		int amountInt = (int)(Double.valueOf(amount)*100.00);
		onReChange.setAmount(amountInt);
		onReChange.setType(type);
		onReChange.setRemark(remark);
		onReChange.setCreate_date(new Date());
		onReChange.setCreate_person(user.getName());
		bser.reChange(onReChange);
		response.sendRedirect("rechangeList.jhtml?mobilePhone="+mobile);
		return null;
	}
}
