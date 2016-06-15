package cn.bc.task.dao.impl;

import cn.bc.business.po.QrCodeStatis;
import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.task.dao.QrCodeStatTaskDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SQLQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xxsoul on 2016/3/10.
 */
public class QrCodeStatTaskDaoImpl extends BaseHibernateDao implements QrCodeStatTaskDao {
    @Override
    public boolean createQrCodeStm() {
        //获取前一天的日期
        Calendar tarCale = Calendar.getInstance();
        tarCale.setTime(new Date());
        tarCale.set(Calendar.DAY_OF_MONTH, tarCale.get(Calendar.DAY_OF_MONTH) - 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setCalendar(tarCale);
        //获取当前日期前一天的各渠道的关注数，注册数，首次买单数，首次购卡数，取消关注数
        String followeeSql = "SELECT QCI.qr_channel_name AS channelName, QCI.qr_ticket, "
        + "IFNULL(COUNT(WXSUB.id),0) AS folCou, IFNULL(COUNT(UIF.uid),0) AS regCou, IFNULL(COUNT(ORDBIL.uid),0) AS billCou, "
        + "IFNULL(COUNT(ORDCAD.uid),0) AS cardCou, IFNULL(COUNT(WXUSUB.id),0) AS unfolCou FROM qr_code_info AS QCI "
        + "LEFT JOIN (SELECT id, sub_date, open_id, qr_ticket FROM wechat_sub_info WHERE DATE(sub_date)=DATE('%s')) AS WXSUB ON WXSUB.qr_ticket = QCI.qr_ticket"
        + " LEFT JOIN (SELECT id, unsub_date, qr_ticket FROM wechat_sub_info WHERE DATE(unsub_date)=DATE('%s')) AS WXUSUB ON WXUSUB.qr_ticket = QCI.qr_ticket"
        + " LEFT JOIN (SELECT user_id AS uid, create_date, open_id FROM on_users WHERE DATE(create_date)=DATE('%s')) AS UIF ON UIF.open_id = WXSUB.open_id "
        + " LEFT JOIN (SELECT user_id AS uid FROM order_pay_bill WHERE NOT EXISTS (SELECT user_id FROM order_pay_bill WHERE status='1' AND DATE(create_date)<DATE('%s')) AND status='1') AS ORDBIL ON ORDBIL.uid=UIF.uid "
        + " LEFT JOIN (SELECT user_id AS uid FROM order_pay_card WHERE NOT EXISTS (SELECT user_id FROM order_pay_card WHERE status='1' AND DATE(create_date)<DATE('%s')) AND status='1') AS ORDCAD ON ORDCAD.uid=UIF.uid "
        + " WHERE QCI.qr_status=0 GROUP BY qr_ticket";
        followeeSql = followeeSql.replaceAll("%s", sdf.format(tarCale.getTime()));
        SQLQuery sqlQuery = getSession().createSQLQuery(followeeSql);
        List result = sqlQuery.list();
        if(result.size() < 1)
            return false;
        for(int i= 0; i < result.size(); i++){
            JSONArray jObj = JSONArray.fromObject(result.get(i));
            QrCodeStatis sqc = new QrCodeStatis();
            sqc.setFolloweeNum(jObj.getInt(2));
            sqc.setChannelName(jObj.getString(0));
            sqc.setRegisterNum(jObj.getInt(3));
            sqc.setFirstPayBillNum(jObj.getInt(4));
            sqc.setBuyCardNum(jObj.getInt(5));
            sqc.setUnfolloweeNum(jObj.getInt(6));
            sqc.setStmDate(tarCale.getTime());
            sqc.setCreate_date(new Date());
            sqc.setCreate_person("system");
            super.save(sqc);
        }
        return true;
    }
}
