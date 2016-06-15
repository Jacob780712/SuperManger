package cn.bc.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.bc.business.po.YhNoticeLogs;
import cn.bc.business.services.MessageLogsService;
import cn.bc.common.util.DateUtils;
import cn.bc.query.po.CoAccount;
import cn.bc.query.services.QueryServices;
import cn.bc.supervalue.outer.service.impl.SMSNotifyServiceImpl;
import cn.bc.supervalue.outer.vo.SMSNotifyResponse;
import cn.bc.task.services.MchantStmService;
import cn.bc.util.SendMsg;
/*
 * spring 定时任务
 */
public class TaskSendMesage extends QuartzJobBean{
	protected Log logger = LogFactory.getLog(TaskSendMesage.class);
	
	private int timeout;
	
	private QueryServices service;

	private MessageLogsService messageLogsService;
	
	public void setService(QueryServices service) {
		this.service = service;
	}
	
	public void setMessageLogsService(MessageLogsService messageLogsService) {
		this.messageLogsService = messageLogsService;
	}


	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	//发送公司账户余额提醒
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		CoAccount coAccount = service.CoAccountDetail();
		int blance = coAccount.getAccount_balance();
//		String mobile = "18901111447";
		String mobile = "13671033483";
		String msg = "这儿有卡公司账户余额提醒，当前公司账户余额："+(double)blance/100.00+"元";
		String desc = "短信通知公司账户余额";
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
//			reponse.sendRedirect("messageLogs.jhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
