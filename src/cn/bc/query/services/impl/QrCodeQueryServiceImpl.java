package cn.bc.query.services.impl;

import cn.bc.business.po.QrCodeStatis;
import cn.bc.common.support.page.Page;
import cn.bc.query.dao.QrCodeQueryDao;
import cn.bc.query.services.QrCodeQueryService;
import cn.bc.query.vo.QrCodeQueryFilter;

import java.util.Map;

/**
 * Created by xxsoul on 2016/3/11.
 */
public class QrCodeQueryServiceImpl implements QrCodeQueryService {
    private QrCodeQueryDao qcStmDao;

    public void setQcStmDao(QrCodeQueryDao qcStmDao) {
        this.qcStmDao = qcStmDao;
    }

    @Override
    public Page QrCodeStmDailyDetilList(QrCodeQueryFilter qrStmFilter) {
        return this.qcStmDao.QrCodeStmDailyDetilList(qrStmFilter);
    }

    @Override
    public Page QrCodeStmDailyList(QrCodeQueryFilter qrStmFilter) {
        return this.qcStmDao.QrCodeStmDailyList(qrStmFilter);
    }

    @Override
    public QrCodeStatis QrCodeTotal() {
        return this.qcStmDao.QrCodeTotal();
    }

    @Override
    public Page QrCodeStatisDetial(QrCodeQueryFilter qrCodeQueryFilter) {
        return this.qcStmDao.QrCodeStatDetial(qrCodeQueryFilter);
    }

    @Override
    public Map QrCodeStatisTotal() {
        return this.qcStmDao.QrCodeStatTotal();
    }
}
