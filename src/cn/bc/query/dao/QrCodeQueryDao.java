package cn.bc.query.dao;

import cn.bc.business.po.QrCodeStatis;
import cn.bc.common.support.page.Page;
import cn.bc.query.vo.QrCodeQueryFilter;

import java.util.List;
import java.util.Map;

/**
 * Created by xxsoul on 2016/3/11.
 */
public interface QrCodeQueryDao {
    public Page QrCodeStmDailyDetilList(QrCodeQueryFilter qrStmFilter);
    public Page QrCodeStmDailyList(QrCodeQueryFilter qrStmFilter);
    public QrCodeStatis QrCodeTotal();
    public Map QrCodeStatTotal();
    public Page QrCodeStatDetial(QrCodeQueryFilter qrStmFilter);
}
