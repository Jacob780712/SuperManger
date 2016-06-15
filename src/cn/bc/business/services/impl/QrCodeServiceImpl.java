package cn.bc.business.services.impl;

import cn.bc.business.action.CashOutImportAction;
import cn.bc.business.dao.QrCodeDao;
import cn.bc.business.po.QrCodeInfo;
import cn.bc.business.services.QrCodeServices;
import cn.bc.business.vo.QrCodeFilter;
import cn.bc.common.support.page.Page;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Properties;

/**
 * Created by xxsoul on 2016/3/8.
 */
public class QrCodeServiceImpl implements QrCodeServices {
    private QrCodeDao qcDao;

    public void setQcDao(QrCodeDao qcDao) {
        this.qcDao = qcDao;
    }

    @Override
    public Page getQrCodeLiset(QrCodeFilter qcFilter) {
        return this.qcDao.getQrCodeList(qcFilter);
    }

    public boolean isQrTicketExist(String ticket){
        QrCodeFilter qrCodeFilter = new QrCodeFilter();
        qrCodeFilter.setTicket(ticket);
        Page res = qcDao.getQrCodeList(qrCodeFilter);
        if(res.getTotalCount() > 0)
            return true;
        return false;
    }

    //保存二维码信息
    @Override
    public QrCodeInfo saveQrCodeInfo(QrCodeInfo qcInfo) throws Exception {
        qcInfo.setQrStatus("0");
//        qcInfo = qcDao.saveQrCodeInfo(qcInfo);
        if(qcInfo == null)
            return null;

        InputStream inStream = CashOutImportAction.class.getResourceAsStream("/qrcode_weixin_api.properties");
        Properties prop = new Properties();
        try {
            prop.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String url = prop.getProperty("url");
        JSONObject jobj = null;
        boolean whileCtr = true;
        int whileCount = 0;
        do {
            whileCount++;
            String apiRes = this.sendGet(url);
            jobj = JSONObject.fromObject(apiRes);
            if (!jobj.getString("resultCode").equals("1"))
                return qcInfo;
            jobj = jobj.getJSONObject("resultQrCode");
            whileCtr = this.isQrTicketExist(jobj.getString("ticketUrl"));
            if(whileCount > 100)
                throw new Exception("ticket has 100 exist!");
        }while(whileCtr);
        qcInfo.setQrTicket(jobj.getString("ticket"));
        qcInfo.setQrPicUrl(jobj.getString("ticketUrl"));
        qcInfo.setQrUpdatePerson("system");
        qcInfo.setQrUpdateDate(new Date());

//        try {
//            qcInfo = qcDao.updateQrCodeInfo(qcInfo);
//        }
//        catch (Exception ex){
//            ex.printStackTrace();
//            return null;
//        }
        return qcInfo;
    }

    //删除二维码信息，只做逻辑删除，不做物理删除
    @Override
    public boolean deleteQrCodeInfo(QrCodeInfo qcInfo) throws Exception {
        qcInfo = qcDao.getQrCodeById(qcInfo.getId());
        if(qcInfo == null)
            return false;
        qcInfo.setQrStatus("1");
        qcInfo.setQrUpdatePerson("system");
        qcInfo.setQrUpdateDate(new Date());
        qcDao.updateQrCodeInfo(qcInfo);
        return true;
    }

    //更新二维码信息
    @Override
    public QrCodeInfo updateQrCodeInfo(QrCodeInfo qcInfo) throws Exception {
        qcInfo.setQrUpdatePerson("system");
        qcInfo.setQrUpdateDate(new Date());
        qcInfo = qcDao.updateQrCodeInfo(qcInfo);
        return qcInfo;
    }

    //发送http请求到api接口
    private String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

	@Override
	public void saveQrCode(QrCodeInfo qcInfo) {
		// TODO Auto-generated method stub
		qcDao.saveQrCodeInfo(qcInfo);
	}
}
