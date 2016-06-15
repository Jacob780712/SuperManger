package cn.bc.task;

import cn.bc.task.services.QrCodeStatTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by xxsoul on 2016/3/10.
 */
public class TaskQrCodeStatistics extends QuartzJobBean {
    protected Log logger = LogFactory.getLog(TaskMchantSettlement.class);
    private QrCodeStatTaskService qcStmService;

    public void setQcStmService(QrCodeStatTaskService qcStmService) {
        this.qcStmService = qcStmService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        qcStmService.createChannelDailyStm();
    }
}
