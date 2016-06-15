package cn.bc.business.dao;

import cn.bc.business.po.QrCodeInfo;
import cn.bc.business.vo.QrCodeFilter;
import cn.bc.common.support.page.Page;

import java.util.List;

/**
 * Created by xxsoul on 2016/3/8.
 */
public interface QrCodeDao {
    public Page getQrCodeList(QrCodeFilter qcFilter);
    public QrCodeInfo saveQrCodeInfo(QrCodeInfo qcInfo);
    public boolean deleteQrCodeInfo(QrCodeInfo qcInfo);
    public QrCodeInfo updateQrCodeInfo(QrCodeInfo qcInfo) throws Exception;

    public QrCodeInfo getQrCodeById(Integer qcId);
}
