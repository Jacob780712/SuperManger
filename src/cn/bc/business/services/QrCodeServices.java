package cn.bc.business.services;

import cn.bc.business.po.QrCodeInfo;
import cn.bc.business.vo.QrCodeFilter;
import cn.bc.common.support.page.Page;

/**
 * Created by xxsoul on 2016/3/8.
 */
public interface QrCodeServices {
    public Page getQrCodeLiset(QrCodeFilter qcFilter);
    public QrCodeInfo saveQrCodeInfo(QrCodeInfo qcInfo) throws Exception;
    public boolean deleteQrCodeInfo(QrCodeInfo qcInfo) throws Exception;
    public QrCodeInfo updateQrCodeInfo(QrCodeInfo qcInfo) throws Exception;
    public void saveQrCode(QrCodeInfo qcInfo);
}
