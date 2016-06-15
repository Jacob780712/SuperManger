package cn.bc.settlement.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bc.settlement.po.PaymentInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.bc.common.support.page.Page;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.settlement.services.SettlementService;
import cn.bc.settlement.vo.StmMchDetailFilter;
import cn.bc.settlement.vo.StmWechatFilter;
import cn.common.security.vo.User;

public class SettlementAction extends BaseAction{
	SettlementService service;

	//查询微信结算
	public String setWechatList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		StmWechatFilter filter = new StmWechatFilter();
		String batch_number = request.getParameter("batch_number");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		filter.setBatch_number(batch_number);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setStatus(status);
		if(status!=null&&!"".equals(status)){
			filter.setStatus(status);
		}
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		
		Page page = service.StmWechatList(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	//微信结算
	public String setWechatSetmed() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		List<StmWechatFilter> filter = new ArrayList<StmWechatFilter>();
		String idstr = request.getParameter("idstr");
		String[] strs = idstr.split(",");
		String batch_number = request.getParameter("batchNumber");
		String aut_fee = request.getParameter("aut_fee");
		String aut_amount = request.getParameter("aut_amount");
		Date date = new Date();
		for(int i=0;i<strs.length;i++){
			if(strs[i]!=null&&!"".equals(strs[i])){
				StmWechatFilter st = new StmWechatFilter();
				st.setId(Integer.valueOf(strs[i].trim()));
				st.setBatch_number(batch_number);
				st.setAut_business_fee((int) (Double.valueOf(aut_fee.trim())*100));
				st.setAut_settlement_amount((int)(Double.valueOf(aut_amount.trim())*100));
				st.setUpdate_date(date);
				st.setUpdate_person(user.getName());
				filter.add(st);
			}
		}
		service.StmWechatUpdate(filter);
		response.sendRedirect("setWechatList.jhtml?batch_number="+batch_number);
		return null;
	}
	
	//商户结算列表
	public String listStmMchDetail(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		StmMchDetailFilter filter = new StmMchDetailFilter();
		String clmid = request.getParameter("clmid");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String status = request.getParameter("status");
		String mch_name = request.getParameter("mch_name");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if(clmid!=null&!"".equals(clmid)){
			filter.setId(Integer.valueOf(clmid));
		}
		filter.setMch_name(mch_name);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setStatus(status);
		if(status!=null&&!"".equals(status)){
			filter.setStatus(status);
		}
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = service.listStmMchDetail(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//更改商户结算状态为已结算
	public String updateStmMchDetail() throws IOException{
		Date date = new Date();
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		String clmid = request.getParameter("clmid");//id
		String fkdate = request.getParameter("fkdate");//fkdate
		StmMchDetailFilter filter = new StmMchDetailFilter();
		filter.setId(Integer.valueOf(clmid));
		filter.setStatus("1");
		filter.setUpdate_date(date);
		filter.setUpdate_person(user.getName());
		filter.setFkdate(fkdate);
		service.updateStmMchDetail(filter);
		response.sendRedirect("listStmMchDetail.jhtml?clmid="+clmid);
		return null;
	}

	//根据查询条件生成批量付款列表
	public String createBatchPaymentExcel() throws Exception {
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
		StmMchDetailFilter filter = new StmMchDetailFilter();
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

		String clmid = request.getParameter("clmid");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String status = "0";
		String mch_name = request.getParameter("mch_name");

		if(clmid!=null&!"".equals(clmid)){
			filter.setId(Integer.valueOf(clmid));
		}
		filter.setMch_name(mch_name);
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setStatus(status);
		if(status!=null&&!"".equals(status)){
			filter.setStatus(status);
		}

		PaymentInfo pi = new PaymentInfo();
		pi.setCustomerId("2200621236");
		pi.setBankName("民生银行");
		pi.setPaymentAccount("696906056");

		HSSFWorkbook wb = service.createSettlementPayExcel(filter, pi);
		if(wb == null)
			throw new Exception("没有导出Excel文件！");

		String getTime = format2.format(new Date());
		String filename = getTime + ".批量支付上传.xls";
		try {
			OutputStream os = null;
			os = response.getOutputStream();
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));
			wb.write(os);
			os.flush();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public void setService(SettlementService service) {
		this.service = service;
	}
	
	
}
