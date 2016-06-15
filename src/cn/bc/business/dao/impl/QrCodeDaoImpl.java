package cn.bc.business.dao.impl;

import cn.bc.business.dao.QrCodeDao;
import cn.bc.business.po.QrCodeInfo;
import cn.bc.business.vo.QrCodeFilter;
import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.common.support.page.Page;
import cn.bc.util.StringToDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxsoul on 2016/3/8.
 */
public class QrCodeDaoImpl extends BaseHibernateDao implements QrCodeDao {
    private String format = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Page getQrCodeList(QrCodeFilter qcFilter) {
        String hql = "from QrCodeInfo qci where ";
        String condetion = "1=1";
        List listParamter = new ArrayList();

        if(qcFilter.getBeginTime() != null && qcFilter.getEndTime() != null){
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += "qci.qrCreateDate BETWEEN ? AND ? ";
            listParamter.add(StringToDate.toDate(format,qcFilter.getBeginTime().trim()+" 00:00:00"));
            listParamter.add(StringToDate.toDate(format,qcFilter.getEndTime().trim()+" 00:00:00"));

        }
        else if(qcFilter.getBeginTime() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " qci.qrCreateDate >= ?";
            listParamter.add(StringToDate.toDate(format,qcFilter.getBeginTime().trim()+" 00:00:00"));
        }
        else if(qcFilter.getEndTime() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " qci.qrCreateDate <=  ?";
            listParamter.add(StringToDate.toDate(format,qcFilter.getEndTime().trim()+" 00:00:00"));
        }

        if(qcFilter.getChannel() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " qci.qrChannelName LIKE ?";
            listParamter.add("%"+qcFilter.getChannel()+"% ");
        }

        if(qcFilter.getTicket() != null && !qcFilter.getTicket().isEmpty()) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " qci.qrTicket = ?";
            listParamter.add(qcFilter.getTicket());
        }

        condetion += " and qci.qrStatus = ?";
        listParamter.add("0");
        hql += condetion + " order by qci.qrCreateDate desc";

        Page res = super.pagedQuery(hql, qcFilter.getPageNo(), qcFilter.getPageSize(), listParamter.toArray());
        return res;
    }

    @Override
    public QrCodeInfo saveQrCodeInfo(QrCodeInfo qcInfo) {
        this.save(qcInfo);
        return qcInfo;
    }

    @Override
    public boolean deleteQrCodeInfo(QrCodeInfo qcInfo) {
        return false;
    }

    @Override
    public QrCodeInfo updateQrCodeInfo(QrCodeInfo qcInfo) throws Exception {
        qcInfo = (QrCodeInfo) this.update(qcInfo);
        return qcInfo;
    }

    @Override
    public QrCodeInfo getQrCodeById(Integer qcId) {
        String hql = "FROM QrCodeInfo qci WHERE qci.id=?";
        List<Object> args = new ArrayList<>();
        args.add(qcId);
        try {
            List<QrCodeInfo> result = super.find(hql, args.toArray());
            if (result == null || result.size() < 1)
                return null;
            return result.get(0);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
