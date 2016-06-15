package cn.bc.task;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.bc.business.services.BusinessServices;
/*
 * spring 定时任务
 */
public class TaskAnalysisList extends QuartzJobBean{
	
	private int timeout;
	
	private BusinessServices businessServices;

	

	public void setBusinessServices(BusinessServices businessServices) {
		this.businessServices = businessServices;
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
		Date date = new Date();
		businessServices.taskGetAnalyis(date);
	}
	
}
