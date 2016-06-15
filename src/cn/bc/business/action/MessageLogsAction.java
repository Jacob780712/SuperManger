package cn.bc.business.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.bc.business.po.YhNoticeLogs;
import cn.bc.business.services.MessageLogsService;
import cn.bc.business.vo.YhNoticeLogsFilter;
import cn.bc.common.support.page.Page;
import cn.bc.common.util.DateUtils;
import cn.bc.common.util.HttpClientUtil;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.supervalue.outer.service.impl.SMSNotifyServiceImpl;
import cn.bc.supervalue.outer.vo.SMSNotifyResponse;
import cn.bc.util.SendMsg;

public class MessageLogsAction extends BaseAction{
	
	private MessageLogsService messageLogsService;
	
	private String sendSmsUrl = "http://222.73.117.158/msg/HttpBatchSendSM";
	private String userName = "Dxwl888";
	private String userPassWord = "Dxwlabcd123,";
	private String serviceCode;

	public String messageLogList()throws Exception{
		
		HttpServletRequest request = (HttpServletRequest) super
		.getContextParam(ServletActionContext.HTTP_REQUEST);
		
		YhNoticeLogsFilter filter = new YhNoticeLogsFilter();
		
		String toAddress = request.getParameter("mobilePhone");
		String noticeType = request.getParameter("sentType");
		String beginDateStr = request.getParameter("beginTime");
		String endDateStr = request.getParameter("endTime");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");

		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);

		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		filter.setToAddress(toAddress);
		filter.setNoticeType(noticeType);
		
		if(StringUtils.isEmpty(beginDateStr)){
			beginDateStr = DateUtils.toDateTimeStr(DateUtils.addMonth(new Date(), -1),"yyyy-MM-dd");
		}
		
		filter.setBeginDate(DateUtils.strToSysDate(beginDateStr,"yyyy-MM-dd"));
		
		if(StringUtils.isEmpty(endDateStr)){
			Date date1 = new Date();
			endDateStr = DateUtils.toDateTimeStr(new Date(date1.getTime() + 24*60*60*1000),"yyyy-MM-dd");
		}
		
		filter.setEndDate(DateUtils.strToSysDate(endDateStr,"yyyy-MM-dd"));

		Page page = messageLogsService.searchPage(filter);
		
		request.setAttribute("beginTime", beginDateStr);
		request.setAttribute("endTime", endDateStr);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	public String gotoSend(){
		return SUCCESS;
	}
	

	
	public String sendmsg(){
		HttpServletRequest request = (HttpServletRequest) super
				.getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse reponse = (HttpServletResponse) super
				.getContextParam(ServletActionContext.HTTP_RESPONSE);
		String mobile = request.getParameter("mobile");
		String msg = request.getParameter("msg");
		String desc = request.getParameter("desc");
		SMSNotifyServiceImpl impl = new SMSNotifyServiceImpl();
		try {
			SendMsg.setMobile(mobile.trim());
			SendMsg.setMsg(msg);
			SMSNotifyResponse sr = SendMsg.send();
			YhNoticeLogs yhmsg = new YhNoticeLogs();
			Date date = new Date();
	        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			yhmsg.setCstUserId(0);
			yhmsg.setToAddress(mobile.trim());
			yhmsg.setNoticeFlag("y");
			yhmsg.setNoticeType("sms/mt");yhmsg.setCreateDate(DateUtils.strToSqlDate(format.format(date)));
			yhmsg.setNoticeDate(DateUtils.strToSqlDate(format.format(date)));
			yhmsg.setNoticeResultDesc("");
			yhmsg.setNoticeContent(msg.trim());
			yhmsg.setDeliveryDesc(desc);
			yhmsg.setTransactionId(0);
			yhmsg.setNoticeResult(sr.getSendResult());
			yhmsg.setMessageId(sr.getMessageId());
			messageLogsService.save(yhmsg);
			reponse.sendRedirect("messageLogs.jhtml");
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
		return null;
	}
	
	
	
	
	public void setMessageLogsService(MessageLogsService messageLogsService) {
		this.messageLogsService = messageLogsService;
	}
	
	
}
