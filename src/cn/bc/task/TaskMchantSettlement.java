package cn.bc.task;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.bc.task.services.MchantStmService;
/*
 * spring 定时任务
 */
public class TaskMchantSettlement extends QuartzJobBean{
	protected Log logger = LogFactory.getLog(TaskMchantSettlement.class);
	
	private int timeout;
	
	private MchantStmService service;

	public void setService(MchantStmService service) {
		this.service = service;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	//生成商户结算信息
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		Date date = new Date();
		try {
			//微信结算数据
			service.setStmWechat(date);
			//每日售卡统计
			service.createMchantStm(date);
			//商户结算数据
			service.createMchantPayment(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
