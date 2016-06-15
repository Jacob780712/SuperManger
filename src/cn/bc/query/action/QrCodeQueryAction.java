package cn.bc.query.action;

import cn.bc.business.po.QrCodeStatis;
import cn.bc.common.support.page.Page;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.query.services.QrCodeQueryService;
import cn.bc.query.vo.QrCodeQueryFilter;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by xxsoul on 2016/3/10.
 */
public class QrCodeQueryAction extends BaseAction {
    private QrCodeQueryService qcStmService;

    public void setQcStmService(QrCodeQueryService qcStmService) {
        this.qcStmService = qcStmService;
    }

    //获取二维码每日统计明细
    public String qrCodeDailySettlementDetialList() {
        HttpServletRequest request = (HttpServletRequest) super.getContextParam(ServletActionContext.HTTP_REQUEST);
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String channelName = request.getParameter("channel");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");

        QrCodeQueryFilter stmQCFilter = new QrCodeQueryFilter();
        stmQCFilter.setBeginTime(beginTime != null && beginTime.length() > 0 ? beginTime : null);
        stmQCFilter.setEndTime(endTime != null && endTime.length() > 0 ? endTime : null);
        stmQCFilter.setChannelName(channelName != null && channelName.length() > 0 ? channelName : null);
        stmQCFilter.setPageNo(pageNo != null && pageNo.length() > 0 ? Integer.valueOf(pageNo) : 1);
        stmQCFilter.setPageSize(pageSize != null && pageSize.length() > 0 ? Integer.valueOf(pageSize) : 20);

        Page result = this.qcStmService.QrCodeStmDailyDetilList(stmQCFilter);
        request.setAttribute("page", result);
        request.setAttribute("param", stmQCFilter);
        return SUCCESS;
    }

    //获取二维码每日统计
    public String qrCodeDailySettlementList() {
        HttpServletRequest request = (HttpServletRequest) super.getContextParam(ServletActionContext.HTTP_REQUEST);
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");

        QrCodeQueryFilter stmQCFilter = new QrCodeQueryFilter();
        stmQCFilter.setBeginTime(beginTime != null && beginTime.length() > 0 ? beginTime : null);
        stmQCFilter.setEndTime(endTime != null && endTime.length() > 0 ? endTime : null);
        stmQCFilter.setPageNo(pageNo != null && pageNo.length() > 0 ? Integer.valueOf(pageNo) : 1);
        stmQCFilter.setPageSize(pageSize != null && pageSize.length() > 0 ? Integer.valueOf(pageSize) : 20);

        Page result = this.qcStmService.QrCodeStmDailyList(stmQCFilter);
        request.setAttribute("page", result);
        QrCodeStatis stmQC = this.qcStmService.QrCodeTotal();
        request.setAttribute("total", stmQC);
        return SUCCESS;
    }

    //获取渠道统计
    public String qrStatList(){
        HttpServletRequest request = (HttpServletRequest) super.getContextParam(ServletActionContext.HTTP_REQUEST);
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String channelName = request.getParameter("channel");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");

        QrCodeQueryFilter stmQCFilter = new QrCodeQueryFilter();
        stmQCFilter.setBeginTime(beginTime != null && beginTime.length() > 0 ? beginTime : null);
        stmQCFilter.setEndTime(endTime != null && endTime.length() > 0 ? endTime : null);
        stmQCFilter.setChannelName(channelName != null && channelName.length() > 0 ? channelName : null);
        stmQCFilter.setPageNo(pageNo != null && pageNo.length() > 0 ? Integer.valueOf(pageNo) : 1);
        stmQCFilter.setPageSize(pageSize != null && pageSize.length() > 0 ? Integer.valueOf(pageSize) : 20);

        Map total = this.qcStmService.QrCodeStatisTotal();
        request.setAttribute("total", total);
        Page result = this.qcStmService.QrCodeStatisDetial(stmQCFilter);
        request.setAttribute("page", result);
        return  SUCCESS;
    }
}
