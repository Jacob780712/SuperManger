package cn.bc.business.action;

import cn.bc.business.po.QrCodeInfo;
import cn.bc.business.services.QrCodeServices;
import cn.bc.business.vo.QrCodeFilter;
import cn.bc.common.support.page.Page;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.util.qrcode.TwoDimensionCode;
import cn.common.security.vo.User;

import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xxsoul on 2016/3/8.
 */
public class QrCodeAction extends BaseAction {
    private QrCodeServices qcService;

    public void setQcService(QrCodeServices qcService) {
        this.qcService = qcService;
    }

    //加载新增二维码页面
    public String loadAddQrCodePage() {
        return SUCCESS;
    }

    //查询二维码列表
    public String qrCodeList() {
        HttpServletRequest request = (HttpServletRequest) super.getContextParam(ServletActionContext.HTTP_REQUEST);
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String channel = request.getParameter("channel");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");

        QrCodeFilter qcFilter = new QrCodeFilter();
        qcFilter.setBeginTime(beginTime != null && beginTime.length() > 0 ? beginTime : null);
        qcFilter.setEndTime(endTime != null && endTime.length() > 0 ? endTime : null);
        qcFilter.setChannel(channel != null && channel.length() > 0 ? channel : null);
        qcFilter.setPageNo(pageNo != null && pageNo.length() > 0 ? Integer.valueOf(pageNo) : 1);
        qcFilter.setPageSize(pageSize != null && pageSize.length() > 0 ? Integer.valueOf(pageSize) : 20);

        Page result = qcService.getQrCodeLiset(qcFilter);
        request.setAttribute("page", result);
        request.setAttribute("param", qcFilter);
        return SUCCESS;
    }

    //新增二维码
    public String addQrCodeInfo() {
        HttpServletRequest request = (HttpServletRequest) super.getContextParam(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
        String channel = request.getParameter("channel");
        String remark = request.getParameter("remark");
        String qrHref = request.getParameter("qrHref");
        Map session = ActionContext.getContext().getSession();
        User user = (User) session.get("loginUser");

        QrCodeInfo qcInfo = new QrCodeInfo();
        qcInfo.setQrChannelName(channel);
        qcInfo.setQrCreateDate(new Date());
        qcInfo.setQrCreatePerson(user.getName());
        qcInfo.setQrRemark(remark);
        qcInfo.setQrStatus("0");
        try{
            qcInfo = qcService.saveQrCodeInfo(qcInfo);
            if(qcInfo == null){
                request.setAttribute("errorMessage", "channel already exist");
                try{
                    response.getWriter().write("");
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    return ERROR;
                }
                return NONE;
            }
            
            //保存二维码图片
            InputStream inStream = CashOutImportAction.class.getResourceAsStream("/mch_pic_url.properties");
    		Properties prop = new Properties();  
    		try {
    			prop.load(inStream);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}  
    		String projectName=prop.getProperty("projectName").trim();//工程名
    		String root = ServletActionContext.getServletContext().getRealPath("/").trim();//项目路径
    		String uperRoot = root.substring(0,root.lastIndexOf(projectName));//上级目录
    		String filePath = uperRoot+"qrimg";
    		Date date = new Date();
    		String content = TwoDimensionCode.encodeQrCode(qcInfo.getQrPicUrl(),date.getTime()+".jpg",filePath);
    		if("error".equals(content)){
    			return ERROR;
    		}
    		if(content!=null&&!"".equals(content.trim())){
    			qcInfo.setQrContent(content);
        		qcInfo.setQrHref(qrHref);
        		qcService.saveQrCode(qcInfo);
        		JSONObject jobj = JSONObject.fromObject(qcInfo);
                response.getWriter().write(jobj.toString());
    		}else{
    			response.getWriter().write("alert('获取二维码内容失败')");
    		}
           
        }
        catch (Exception ex){
            ex.printStackTrace();
            try {
				response.getWriter().write(ex.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return NONE;
        }
        return NONE;
    }
    
    //删除二维码
    public String deleteQrCodeInfo() {
        HttpServletRequest request = (HttpServletRequest) super.getContextParam(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
        String qrId = request.getParameter("qrId");
        if(qrId == null || qrId.length() < 1)
            return ERROR;

        QrCodeInfo qcInfo = new QrCodeInfo();
        qcInfo.setId(Integer.valueOf(qrId));
        try {
            boolean res = qcService.deleteQrCodeInfo(qcInfo);
            if(res)
                response.getWriter().write("true");
            else
                response.getWriter().write("false");
        }
        catch(Exception ex){
            ex.printStackTrace();
            return ERROR;
        }
        return NONE;
    }
}
