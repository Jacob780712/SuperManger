package cn.bc.task.services.impl;

import cn.bc.task.dao.QrCodeStatTaskDao;
import cn.bc.task.services.QrCodeStatTaskService;

/**
 * Created by xxsoul on 2016/3/10.
 */
public class QrCodeStatTaskServiceImpl implements QrCodeStatTaskService {
    private QrCodeStatTaskDao qcTaskDao;

    public void setQcTaskDao(QrCodeStatTaskDao qcTaskDao) {
        this.qcTaskDao = qcTaskDao;
    }

    public void createChannelDailyStm() {
        this.qcTaskDao.createQrCodeStm();
    }
}
