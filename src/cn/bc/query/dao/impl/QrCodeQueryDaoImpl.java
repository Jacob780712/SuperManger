package cn.bc.query.dao.impl;

import cn.bc.business.po.QrCodeStatis;
import cn.bc.common.dao.impl.BaseHibernateDao;
import cn.bc.common.support.page.Page;
import cn.bc.query.dao.QrCodeQueryDao;
import cn.bc.query.vo.QrCodeQueryFilter;
import cn.bc.util.StringToDate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SQLQuery;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xxsoul on 2016/3/11.
 */
public class QrCodeQueryDaoImpl extends BaseHibernateDao implements QrCodeQueryDao {
    private String format = "yyyy-MM-dd HH:mm:ss";
    private String formatDate = "yyyy-MM-dd";
    @Override
    public Page QrCodeStmDailyDetilList(QrCodeQueryFilter qrStmFilter) {
        String sql = "SELECT QCI.qr_channel_name AS channelName, QCI.qr_ticket, "
                +" IFNULL(WXSUB.id,0) AS folCou, IFNULL(UIF.uid,0) AS regCou, "
                +" IFNULL(ORDBIL.uid,0) AS billCou, IFNULL(ORDCAD.uid,0) AS cardCou, "
                +" IFNULL(WXUSUB.id,0) AS unfolCou, QCI.date "
                +" FROM (SELECT * FROM sys_date CROSS JOIN qr_code_info) AS QCI "
                +" LEFT JOIN ( SELECT COUNT(id) AS id, DATE(sub_date) AS date, qr_ticket  "
                +" FROM wechat_sub_info WHERE status='0' GROUP BY DATE(sub_date), qr_ticket "
                +" ) AS WXSUB ON DATE(WXSUB.date)=DATE(QCI.date) AND WXSUB.qr_ticket=QCI.qr_ticket "
                +" LEFT JOIN ( SELECT COUNT(id) AS id, DATE(unsub_date) AS date, qr_ticket  "
                +" FROM wechat_sub_info WHERE status='1'  GROUP BY DATE(unsub_date), qr_ticket "
                +" ) AS WXUSUB ON DATE(WXUSUB.date)=DATE(QCI.date) AND WXUSUB.qr_ticket=QCI.qr_ticket "
                +" LEFT JOIN ( SELECT COUNT(user_id) AS uid, DATE(on_users.create_date) AS date, WXSUB.qr_ticket "
                +" FROM on_users  JOIN (SELECT DATE(sub_date) AS date, open_id, qr_ticket "
                +" FROM wechat_sub_info GROUP BY DATE(sub_date), open_id ) AS WXSUB ON WXSUB.date = DATE(on_users.create_date)  "
                +" AND WXSUB.open_id = on_users.open_id GROUP BY date, WXSUB.qr_ticket "
                +" ) AS UIF ON UIF.qr_ticket = QCI.qr_ticket "
                +" AND UIF.date = QCI.date "
                +" LEFT JOIN ( SELECT COUNT(user_id) AS uid, DATE(create_date) AS date, UIF.qr_ticket "
                +" FROM order_pay_bill JOIN (SELECT user_id AS uid, WXSUB.qr_ticket  "
                +" FROM on_users JOIN (SELECT open_id, qr_ticket FROM wechat_sub_info "
                +" ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_bill.user_id = UIF.uid "
                +" WHERE order_pay_bill.status='1' GROUP BY date,UIF.qr_ticket  "
                +" ) AS ORDBIL ON DATE(ORDBIL.date)=DATE(QCI.date) AND ORDBIL.qr_ticket = QCI.qr_ticket "
                +" LEFT JOIN ( "
                +" SELECT COUNT(user_id) AS uid, DATE(create_date) AS date, UIF.qr_ticket "
                +" FROM order_pay_card JOIN (SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users "
                +" JOIN (SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id "
                +" ) AS UIF ON order_pay_card.user_id = UIF.uid "
                +" WHERE order_pay_card.status='1' GROUP BY date,UIF.qr_ticket  "
                +" ) AS ORDCAD ON DATE(ORDCAD.date)=DATE(QCI.date) AND ORDCAD.qr_ticket = QCI.qr_ticket ";
        String condetion = " WHERE qr_status=0 ";
        String group = " GROUP BY qr_ticket,date ";

        if(qrStmFilter.getBeginTime() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " QCI.date >= '" + StringToDate.toDate(format,qrStmFilter.getBeginTime().trim()+" 00:00:00") + "'";
        }
        if(qrStmFilter.getEndTime() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " QCI.date <=  '" + StringToDate.toDate(format,qrStmFilter.getEndTime().trim()+" 00:00:00") + "'";
        }
        if(qrStmFilter.getChannelName() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " QCI.qr_channel_name LIKE '%" + qrStmFilter.getChannelName() + "%'";
        }
        sql += condetion + group + " ORDER BY date DESC" ;
        sql += " LIMIT " + (qrStmFilter.getPageNo() - 1) * qrStmFilter.getPageSize() + "," + qrStmFilter.getPageSize() ;

        List result = this.getSession().createSQLQuery(sql).list();
        List<QrCodeStatis> qcsRes = new ArrayList<QrCodeStatis>();
        try {
            for (Object obj : result) {
                Object[] objRes = (Object[]) obj;
                QrCodeStatis sqc = new QrCodeStatis();
                sqc.setChannelName(objRes[0].toString());
                sqc.setFolloweeNum(Integer.valueOf(objRes[2].toString()));
                sqc.setRegisterNum(Integer.valueOf(objRes[3].toString()));
                sqc.setFirstPayBillNum(Integer.valueOf(objRes[4].toString()));
                sqc.setBuyCardNum(Integer.valueOf(objRes[5].toString()));
                sqc.setUnfolloweeNum(Integer.valueOf(objRes[6].toString()));
                sqc.setStmDate(StringToDate.toDate(formatDate,objRes[7].toString()));
                qcsRes.add(sqc);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        String couSql = "SELECT COUNT(*) FROM sys_date,qr_code_info";
        List countRes = this.getSession().createSQLQuery(couSql).list();
        Integer count = Integer.valueOf(countRes.get(0).toString());

        Page res = new Page((qrStmFilter.getPageNo() - 1) * qrStmFilter.getPageSize(), count, qrStmFilter.getPageSize(), qcsRes);

        return res;
    }

    @Override
    public Page QrCodeStmDailyList(QrCodeQueryFilter qrStmFilter) {
        String sql = "SELECT IFNULL(WXSUB.id,0) AS folCou, IFNULL(UIF.uid,0) AS regCou, "
                +" IFNULL(ORDBIL.uid,0) AS billCou, IFNULL(ORDCAD.uid,0) AS cardCou, "
                +" IFNULL(WXUSUB.id,0) AS unfolCou, QCI.date "
                +" FROM (SELECT * FROM sys_date CROSS JOIN qr_code_info) AS QCI "
                +" LEFT JOIN (SELECT COUNT(id) AS id, DATE(sub_date) AS date FROM wechat_sub_info WHERE status='0' GROUP BY DATE(sub_date)) AS WXSUB ON DATE(WXSUB.date)=DATE(QCI.date) "
                +" LEFT JOIN (SELECT COUNT(id) AS id, DATE(unsub_date) AS date FROM wechat_sub_info WHERE status='1' GROUP BY DATE(unsub_date)) AS WXUSUB ON DATE(WXUSUB.date)=DATE(QCI.date) "
                +" LEFT JOIN ( SELECT COUNT(user_id) AS uid, DATE(on_users.create_date) AS date "
                +" 	FROM on_users JOIN (SELECT DATE(sub_date) AS date, open_id "
                +" 	FROM wechat_sub_info GROUP BY DATE(sub_date) , open_id) AS WXSUB ON WXSUB.date = DATE(on_users.create_date) "
                +" 	AND WXSUB.open_id = on_users.open_id GROUP BY DATE(on_users.create_date) ) AS UIF ON DATE(UIF.date)=DATE(QCI.date) "
                +" LEFT JOIN ( SELECT COUNT(user_id) AS uid, DATE(create_date) AS date "
                +" 	FROM order_pay_bill JOIN ( SELECT user_id AS uid, DATE(on_users.create_date), on_users.open_id "
                +" 	FROM on_users JOIN ( SELECT DATE(sub_date) AS date, open_id "
                +" 	FROM wechat_sub_info GROUP BY DATE(sub_date) , open_id) AS WXSUB ON WXSUB.date = DATE(on_users.create_date) "
                +" 	AND WXSUB.open_id = on_users.open_id) AS UIF ON order_pay_bill.user_id = UIF.uid "
                +" 	WHERE order_pay_bill.status='1' GROUP BY date ) AS ORDBIL ON DATE(ORDBIL.date)=DATE(QCI.date) "
                +" LEFT JOIN ( SELECT COUNT(user_id) AS uid, DATE(create_date) AS date "
                +" 	FROM order_pay_card JOIN ( SELECT user_id AS uid, DATE(on_users.create_date), on_users.open_id "
                +" 	FROM on_users JOIN ( SELECT DATE(sub_date) AS date, open_id FROM wechat_sub_info "
                +" 	GROUP BY DATE(sub_date) , open_id) AS WXSUB ON WXSUB.date = DATE(on_users.create_date) "
                +" 	AND WXSUB.open_id = on_users.open_id) AS UIF ON order_pay_card.user_id = UIF.uid "
                +" 	WHERE order_pay_card.status='1' GROUP BY date ) AS ORDCAD ON DATE(ORDCAD.date)=DATE(QCI.date) ";
        String condetion = " WHERE 1=1 ";
        String group = " GROUP BY date ";

        if(qrStmFilter.getBeginTime() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " QCI.date >= '" + StringToDate.toDate(format,qrStmFilter.getBeginTime().trim()+" 00:00:00") + "'";
        }
        if(qrStmFilter.getEndTime() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " QCI.date <=  '" + StringToDate.toDate(format,qrStmFilter.getEndTime().trim()+" 00:00:00") + "'";
        }
        sql += condetion + group + " ORDER BY date DESC" ;
        sql += " LIMIT " + (qrStmFilter.getPageNo() - 1) * qrStmFilter.getPageSize() + "," + qrStmFilter.getPageSize();

        List result = this.getSession().createSQLQuery(sql).list();
        List<QrCodeStatis> qcsRes = new ArrayList<QrCodeStatis>();
        try {
            for (Object obj : result) {
                Object[] objRes = (Object[]) obj;
                QrCodeStatis sqc = new QrCodeStatis();
                sqc.setFolloweeNum(Integer.valueOf(objRes[0].toString()));
                sqc.setRegisterNum(Integer.valueOf(objRes[1].toString()));
                sqc.setFirstPayBillNum(Integer.valueOf(objRes[2].toString()));
                sqc.setBuyCardNum(Integer.valueOf(objRes[3].toString()));
                sqc.setUnfolloweeNum(Integer.valueOf(objRes[4].toString()));
                sqc.setStmDate(StringToDate.toDate(formatDate,objRes[5].toString()));
                qcsRes.add(sqc);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        String couSql = "SELECT COUNT(*) FROM sys_date";
        List countRes = this.getSession().createSQLQuery(couSql).list();
        Integer count = Integer.valueOf(countRes.get(0).toString());

        Page res = new Page((qrStmFilter.getPageNo() - 1) * qrStmFilter.getPageSize(), count, qrStmFilter.getPageSize(), qcsRes);

        return res;

    }

    @Override
    public QrCodeStatis QrCodeTotal() {
        //String hql = "SELECT SUM(followee_num) followeeNum, SUM(register_num) registerNum, SUM(first_pay_bill_num) firstPayBillNum,SUM(buy_card_num) buyCardNum, SUM(unfollowee_num) unfolloweeNum FROM stm_qr_code";
        String sql = "SELECT SUM(total.folCou), SUM(total.regCou), SUM(total.billCou), "
                +" SUM(total.cardCou), SUM(total.unfolCou) "
                +" FROM ( SELECT IFNULL(WXSUB.id,0) AS folCou, IFNULL(UIF.uid,0) AS regCou, "
                +" IFNULL(ORDBIL.uid,0) AS billCou, IFNULL(ORDCAD.uid,0) AS cardCou, "
                +" IFNULL(WXUSUB.id,0) AS unfolCou, QCI.date  "
                +" FROM (SELECT * FROM sys_date CROSS JOIN qr_code_info) AS QCI "
                +" LEFT JOIN (SELECT COUNT(id) AS id, DATE(sub_date) AS date FROM wechat_sub_info WHERE status='0' GROUP BY DATE(sub_date) "
                +" ) AS WXSUB ON DATE(WXSUB.date)=DATE(QCI.date) "
                +" LEFT JOIN (SELECT COUNT(id) AS id, DATE(unsub_date) AS date FROM wechat_sub_info WHERE status='1' GROUP BY DATE(unsub_date) "
                +" ) AS WXUSUB ON DATE(WXUSUB.date)=DATE(QCI.date) "
                +" LEFT JOIN ( SELECT COUNT(user_id) AS uid, DATE(on_users.create_date) AS date FROM on_users  "
                +" JOIN (SELECT DATE(sub_date) AS date, open_id FROM wechat_sub_info GROUP BY DATE(sub_date) ,open_id) AS WXSUB  "
                +" ON WXSUB.date = DATE(on_users.create_date) AND WXSUB.open_id = on_users.open_id  "
                +" GROUP BY DATE(on_users.create_date) ) AS UIF ON DATE(UIF.date)=DATE(QCI.date) "
                +" LEFT JOIN ( SELECT COUNT(user_id) AS uid,DATE(create_date) AS date "
                +" FROM order_pay_bill  JOIN ( SELECT  user_id AS uid, DATE(on_users.create_date), on_users.open_id  "
                +" FROM on_users JOIN ( SELECT DATE(sub_date) AS date, open_id FROM wechat_sub_info GROUP BY DATE(sub_date) ,open_id "
                +" ) AS WXSUB ON WXSUB.date = DATE(on_users.create_date) AND WXSUB.open_id = on_users.open_id  "
                +" ) AS UIF ON order_pay_bill.user_id = UIF.uid WHERE order_pay_bill.status='1' GROUP BY date "
                +" ) AS ORDBIL ON DATE(ORDBIL.date)=DATE(QCI.date) "
                +" LEFT JOIN ( SELECT COUNT(user_id) AS uid,DATE(create_date) AS date "
                +" FROM order_pay_card  JOIN ( SELECT  user_id AS uid, DATE(on_users.create_date), on_users.open_id  "
                +" FROM on_users JOIN ( SELECT DATE(sub_date) AS date, open_id FROM wechat_sub_info GROUP BY DATE(sub_date) ,open_id "
                +" ) AS WXSUB ON WXSUB.date = DATE(on_users.create_date) AND WXSUB.open_id = on_users.open_id  "
                +" ) AS UIF ON order_pay_card.user_id = UIF.uid WHERE order_pay_card.status='1' GROUP BY date "
                +" ) AS ORDCAD ON DATE(ORDCAD.date)=DATE(QCI.date) GROUP BY date ) AS total ";
        try{
            SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
            List res = sqlQuery.list();
            if(res == null || res.size() < 1)
                return null;
            Object[] array = (Object[])res.get(0);
            QrCodeStatis sqc = new QrCodeStatis();
            sqc.setFolloweeNum(Integer.valueOf(array[0].toString()));
            sqc.setRegisterNum(Integer.valueOf(array[1].toString()));
            sqc.setFirstPayBillNum(Integer.valueOf(array[2].toString()));
            sqc.setBuyCardNum(Integer.valueOf(array[3].toString()));
            sqc.setUnfolloweeNum(Integer.valueOf(array[4].toString()));
            return sqc;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Map QrCodeStatTotal() {
        String sql = "SELECT COUNT(QCI.id) AS channelCou, IFNULL(WXSUB.id,0) AS folCou, IFNULL(UIF.uid,0) AS regCou, (IFNULL(ORDBIL.billCou,0) + IFNULL(OPC.courCou,0)) AS billCou, (IFNULL(ORDBIL.uBillCou,0) + IFNULL(OPC.uCourCou,0)) AS billUCou, " +
                " CAST((IFNULL(ORDBIL.bilTotAmount,0) + IFNULL(OPC.courTotAmount,0)) / 100 AS DECIMAL(18,2)) AS billTotAmount, IFNULL(ORDCAD.uCarCou,0) AS uCarCou, IFNULL(ORDCAD.uPurCount,0) AS cardCou, " +
                " CAST(IFNULL(ORDCAD.totQuota,0) / 100 AS DECIMAL(18,2)) AS cardTotQuo, CAST(IFNULL(ORDCAD.uPurAmount,0) / 100 AS DECIMAL(18,2)) AS cardTotPur, " +
                " CAST((IFNULL(ORDCAD.uAccAmount,0) + IFNULL(ORDBIL.bilAccTotAmount,0)) / 100 AS DECIMAL(18,2)) AS accountTotAmount, CAST((IFNULL(ORDCAD.cardWCAmount,0) + IFNULL(ORDBIL.billWCAmount,0)) / 100 AS DECIMAL(18,2)) AS wcAmount, " +
                " CAST((IFNULL(ORDBIL.bilORetAmount,0) + IFNULL(OPC.courORetAmount,0)) / 100 AS DECIMAL(18,2)) AS oRetAmount, IFNULL(OPBT.twoBillCou, 0) AS twoBillCount, IFNULL(OPCT.twoCardCou, 0) AS twoCardCount FROM qr_code_info AS QCI " +
                " LEFT JOIN ( SELECT COUNT(id) AS id FROM wechat_sub_info WHERE status='0' ) AS WXSUB ON 1=1 LEFT JOIN ( SELECT COUNT(user_id) AS uid FROM on_users " +
                " JOIN ( SELECT DATE(sub_date) AS date, open_id FROM wechat_sub_info GROUP BY DATE(sub_date) , open_id ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON 1=1 " +
                " LEFT JOIN ( SELECT COUNT(id) AS billCou, COUNT(DISTINCT user_id) AS uBillCou, SUM(business_amount) AS bilTotAmount, SUM(account_pay_amount) AS bilAccTotAmount, SUM(o_return_amount) AS bilORetAmount, " +
                " IFNULL(OPBP.billWCAmount,0) AS billWCAmount FROM order_pay_bill JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id " +
                " ) AS UIF ON order_pay_bill.user_id = UIF.uid LEFT JOIN ( SELECT SUM(pay_amount) AS billWCAmount,mch_number FROM order_pay_bill JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users " +
                " JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_bill.user_id = UIF.uid WHERE status='1' AND pay_source='1' " +
                " ) AS OPBP ON 1=1 WHERE order_pay_bill.status='1' ) AS ORDBIL ON 1=1 LEFT JOIN ( SELECT COUNT(DISTINCT user_id) AS uCarCou, SUM(purchase_amount) AS uPurAmount, SUM(purchase_number) AS uPurCount, " +
                " SUM(account_pay_amount) AS uAccAmount, OSPC.totQuota, IFNULL(OPCP.cardWCAmount,0) AS cardWCAmount FROM order_pay_card JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket " +
                " FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_card.user_id = UIF.uid JOIN ( SELECT SUM(svc_quota) AS totQuota,order_number FROM order_sub_pay_card WHERE status='1' " +
                " ) AS OSPC ON 1=1 LEFT JOIN ( SELECT SUM(pay_amount) AS cardWCAmount,mch_number FROM order_pay_card JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info " +
                " ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_card.user_id = UIF.uid WHERE status='1' AND pay_source='1' ) AS OPCP ON 1=1 WHERE order_pay_card.status='1' ) AS ORDCAD ON 1=1 " +
                " LEFT JOIN ( SELECT COUNT(id) AS courCou, COUNT(DISTINCT user_id) AS uCourCou, SUM(purchase_amount) AS courTotAmount, SUM(account_pay_amount) AS courAccTotAmount, SUM(o_return_amount) AS courORetAmount, " +
                " IFNULL(OPCP.courWCAmount,0) AS courWCAmount FROM order_pay_courtesy JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id " +
                " ) AS UIF ON order_pay_courtesy.user_id = UIF.uid LEFT JOIN ( SELECT SUM(pay_amount) AS courWCAmount FROM order_pay_courtesy JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket " +
                " FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_courtesy.user_id = UIF.uid WHERE status='1' AND pay_source='1' ) AS OPCP ON 1=1 WHERE order_pay_courtesy.status='1' ) AS OPC ON 1=1 " +
                " LEFT JOIN (SELECT COUNT(OPBT.bilCou) twoBillCou FROM ( SELECT COUNT(user_id) AS bilCou FROM order_pay_bill JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info " +
                " ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_bill.user_id = UIF.uid WHERE status='1' ) AS OPBT WHERE OPBT.bilCou > 1 ) AS OPBT ON 1=1 LEFT JOIN ( SELECT COUNT(OPC.cardCou) twoCardCou " +
                " FROM ( SELECT COUNT(user_id) AS cardCou FROM order_pay_card JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id " +
                " ) AS UIF ON order_pay_card.user_id = UIF.uid WHERE status='1' ) AS OPC WHERE OPC.cardCou > 1 ) AS OPCT ON 1=1 WHERE QCI.qr_status='0'";
        try{
            SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
            sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List res = sqlQuery.list();
            if(res == null || res.size() < 1)
                return null;
            Map<String, Object> result = (Map<String, Object>)res.get(0);
            return result;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Page QrCodeStatDetial(QrCodeQueryFilter qrStmFilter) {
        String sql = "SELECT QCI.qr_channel_name AS channelName, QCI.qr_ticket, IFNULL(WXSUB.id,0) AS folCou, IFNULL(UIF.uid,0) AS regCou, (IFNULL(ORDBIL.billCou,0) + IFNULL(OPC.courCou,0)) AS billCou, (IFNULL(ORDBIL.uBillCou,0) + IFNULL(OPC.uCourCou,0)) AS billUCou, " +
                " CAST((IFNULL(ORDBIL.bilTotAmount, 0) + IFNULL(OPC.courTotAmount,0)) / 100 AS DECIMAL(18,2)) AS billTotAmount, IFNULL(ORDCAD.uCarCou,0) AS uCarCou, IFNULL(ORDCAD.uPurCount,0) AS cardCou, " +
                " CAST(IFNULL(ORDCAD.totQuota,0) / 100 AS DECIMAL(18,2)) AS cardTotQuo, CAST(IFNULL(ORDCAD.uPurAmount,0) / 100 AS DECIMAL(18,2)) AS cardTotPur, CAST((IFNULL(ORDCAD.uAccAmount,0) + IFNULL(ORDBIL.bilAccTotAmount,0)) / 100 AS DECIMAL(18,2)) AS accountTotAmount, " +
                " CAST((IFNULL(ORDCAD.cardWCAmount,0) + IFNULL(ORDBIL.billWCAmount,0)) / 100 AS DECIMAL(18,2)) AS wcAmount, CAST((IFNULL(ORDBIL.bilORetAmount,0) + IFNULL(OPC.courORetAmount,0)) / 100 AS DECIMAL(18,2)) AS oRetAmount, IFNULL(OPCF.fOPCCount, 0) AS fOPCCount, " +
                " IFNULL(OPBF.fOPBCount, 0) AS fOPBCount, QCI.date FROM (SELECT * FROM sys_date CROSS JOIN qr_code_info) AS QCI LEFT JOIN ( SELECT COUNT(id) AS id, DATE(sub_date) AS date, qr_ticket FROM wechat_sub_info WHERE status='0' " +
                " GROUP BY DATE(sub_date), qr_ticket ) AS WXSUB ON DATE(WXSUB.date)=DATE(QCI.date) AND WXSUB.qr_ticket=QCI.qr_ticket LEFT JOIN ( SELECT COUNT(user_id) AS uid, DATE(on_users.create_date) AS date, WXSUB.qr_ticket FROM on_users JOIN ( SELECT DATE(sub_date) AS date, open_id, qr_ticket " +
                " FROM wechat_sub_info GROUP BY DATE(sub_date) , open_id ) AS WXSUB ON WXSUB.date = DATE(on_users.create_date) AND WXSUB.open_id = on_users.open_id GROUP BY DATE(on_users.create_date) ) AS UIF ON DATE(UIF.date)=DATE(QCI.date) AND UIF.qr_ticket=QCI.qr_ticket " +
                " LEFT JOIN ( SELECT COUNT(id) AS billCou, COUNT(DISTINCT user_id) AS uBillCou, SUM(business_amount) AS bilTotAmount, SUM(account_pay_amount) AS bilAccTotAmount, IFNULL(OPBP.billWCAmount,0) AS billWCAmount, " +
                " SUM(o_return_amount) AS bilORetAmount, UIF.qr_ticket, DATE(create_date) AS date FROM order_pay_bill JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info " +
                " ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_bill.user_id = UIF.uid LEFT JOIN ( SELECT SUM(pay_amount) AS billWCAmount,mch_number,DATE(create_date) AS date FROM order_pay_bill " +
                " JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_bill.user_id = UIF.uid " +
                " WHERE status='1' AND pay_source='1' GROUP BY mch_number,DATE(create_date) ) AS OPBP ON OPBP.mch_number=order_pay_bill.mch_number AND OPBP.date=DATE(order_pay_bill.create_date) WHERE order_pay_bill.status='1' GROUP BY date,UIF.qr_ticket " +
                " ) AS ORDBIL ON DATE(ORDBIL.date)=DATE(QCI.date) AND ORDBIL.qr_ticket=QCI.qr_ticket LEFT JOIN ( SELECT COUNT(DISTINCT user_id) AS uCarCou, SUM(purchase_amount) AS uPurAmount, SUM(purchase_number) AS uPurCount, " +
                " SUM(account_pay_amount) AS uAccAmount, OSPC.totQuota, IFNULL(OPCP.cardWCAmount,0) AS cardWCAmount, UIF.qr_ticket, DATE(create_date) AS date FROM order_pay_card JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users " +
                " JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_card.user_id = UIF.uid JOIN ( SELECT SUM(svc_quota) AS totQuota,order_number FROM order_sub_pay_card " +
                " WHERE status='1' GROUP BY order_number ) AS OSPC ON OSPC.order_number = order_pay_card.order_number LEFT JOIN ( SELECT SUM(pay_amount) AS cardWCAmount,mch_number,DATE(create_date) AS date FROM order_pay_card " +
                " JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_card.user_id = UIF.uid " +
                " WHERE status='1' AND pay_source='1' GROUP BY mch_number,DATE(create_date) ) AS OPCP ON OPCP.mch_number=order_pay_card.mch_number AND OPCP.date=DATE(order_pay_card.create_date) WHERE order_pay_card.status='1' GROUP BY date,UIF.qr_ticket " +
                " ) AS ORDCAD ON DATE(ORDCAD.date)=DATE(QCI.date) AND ORDCAD.qr_ticket=QCI.qr_ticket LEFT JOIN ( SELECT COUNT(id) AS courCou, COUNT(DISTINCT user_id) AS uCourCou, SUM(purchase_amount) AS courTotAmount, " +
                " SUM(account_pay_amount) AS courAccTotAmount, IFNULL(OPCP.courWCAmount,0), SUM(o_return_amount) AS courORetAmount, UIF.qr_ticket, DATE(create_date) AS date FROM order_pay_courtesy JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket " +
                " FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_courtesy.user_id = UIF.uid " +
                " LEFT JOIN ( SELECT SUM(pay_amount) AS courWCAmount,mch_number,DATE(create_date) AS date FROM order_pay_courtesy JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket FROM on_users JOIN ( SELECT open_id, qr_ticket " +
                " FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_courtesy.user_id = UIF.uid WHERE status='1' AND pay_source='1' GROUP BY mch_number,DATE(create_date) " +
                " ) AS OPCP ON OPCP.mch_number=order_pay_courtesy.mch_number AND OPCP.date=DATE(order_pay_courtesy.create_date) WHERE order_pay_courtesy.status='1' GROUP BY date,UIF.qr_ticket ) AS OPC ON DATE(OPC.date)=DATE(QCI.date) AND OPC.qr_ticket=QCI.qr_ticket " +
                " LEFT JOIN ( SELECT COUNT(T1.user_id) fOPCCount,T1.fDate AS date,T1.ticket AS qr_ticket FROM ( SELECT user_id,MIN(create_date) fDate,ticket FROM order_pay_card JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket AS ticket " +
                " FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_card.user_id = UIF.uid GROUP BY user_id) AS T1 GROUP BY DATE(T1.fDate),T1.ticket " +
                " ) AS OPCF ON DATE(OPCF.date)=DATE(QCI.date) AND OPCF.qr_ticket=QCI.qr_ticket LEFT JOIN ( SELECT COUNT(T1.user_id) fOPBCount,T1.fDate AS date,T1.ticket AS qr_ticket FROM ( SELECT user_id,MIN(create_date) fDate,ticket " +
                " FROM order_pay_bill JOIN ( SELECT user_id AS uid, WXSUB.qr_ticket AS ticket FROM on_users JOIN ( SELECT open_id, qr_ticket FROM wechat_sub_info ) AS WXSUB ON WXSUB.open_id = on_users.open_id ) AS UIF ON order_pay_bill.user_id = UIF.uid GROUP BY user_id" +
                " ) AS T1 GROUP BY DATE(T1.fDate),T1.ticket ) AS OPBF ON DATE(OPBF.date)=DATE(QCI.date) AND OPBF.qr_ticket=QCI.qr_ticket ";
        String condetion = " WHERE QCI.qr_status='0' ";
        String group = " GROUP BY qr_ticket,date ";

        if(qrStmFilter.getBeginTime() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " QCI.date >= '" + qrStmFilter.getBeginTime() + "'";
        }
        if(qrStmFilter.getEndTime() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            condetion += " QCI.date <=  '" + qrStmFilter.getEndTime() + "'";
        }
        if(qrStmFilter.getChannelName() != null) {
            condetion += condetion.length() > 0 ? " and " : "";
            //condetion += " QCI.qr_channel_name = '" + qrStmFilter.getChannelName() + "'";//停用全字匹配
            condetion += " QCI.qr_channel_name LIKE '%" + qrStmFilter.getChannelName() + "%'";//使用模糊匹配
        }
        sql += condetion + group + " ORDER BY date DESC" ;
        sql += " LIMIT " + (qrStmFilter.getPageNo() - 1) * qrStmFilter.getPageSize() + "," + qrStmFilter.getPageSize() ;
        Page res = null;
        try {
            SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
            sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List qcsRes = sqlQuery.list();
            String couSql = "SELECT COUNT(*) FROM (SELECT * FROM sys_date CROSS JOIN qr_code_info) AS QCI  " + condetion;
            List countRes = this.getSession().createSQLQuery(couSql).list();
            Integer count = Integer.valueOf(countRes.get(0).toString());

            res = new Page((qrStmFilter.getPageNo() - 1) * qrStmFilter.getPageSize(), count, qrStmFilter.getPageSize(), qcsRes);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return res;
    }
}
