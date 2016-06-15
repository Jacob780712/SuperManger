package cn.bc.query.services;

import cn.bc.business.po.QrCodeStatis;
import cn.bc.common.support.page.Page;
import cn.bc.query.vo.QrCodeQueryFilter;

import java.util.Map;

/**
 * Created by xxsoul on 2016/3/11.
 */
public interface QrCodeQueryService {
    public Page QrCodeStmDailyDetilList(QrCodeQueryFilter qrStmFilter);
    public Page QrCodeStmDailyList(QrCodeQueryFilter qrStmFilter);
    public QrCodeStatis QrCodeTotal();

    public Page QrCodeStatisDetial(QrCodeQueryFilter qrCodeQueryFilter);
    public Map QrCodeStatisTotal();
}
