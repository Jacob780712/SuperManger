package cn.bc.business.po;

import java.util.Date;

/**
 * Created by xxsoul on 2016/3/11.
 */
public class QrCodeStatis {
    private int id;
    private int followeeNum;
    private String channelName;
    private int registerNum;
    private int firstPayBillNum;
    private int buyCardNum;
    private int unfolloweeNum;
    private Date stmDate;
    private Date create_date;//二维码统计时间
    private String create_person;//创建人
    private Date update_date;//二维码统计更新时间
    private String update_person;//更新人

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
    }

    public int getFirstPayBillNum() {
        return firstPayBillNum;
    }

    public void setFirstPayBillNum(int firstPayBillNum) {
        this.firstPayBillNum = firstPayBillNum;
    }

    public int getBuyCardNum() {
        return buyCardNum;
    }

    public void setBuyCardNum(int buyCardNum) {
        this.buyCardNum = buyCardNum;
    }

    public int getFolloweeNum() {
        return followeeNum;
    }

    public void setFolloweeNum(int followeeNum) {
        this.followeeNum = followeeNum;
    }

    public int getUnfolloweeNum() {
        return unfolloweeNum;
    }

    public void setUnfolloweeNum(int unfolloweeNum) {
        this.unfolloweeNum = unfolloweeNum;
    }

    public Date getStmDate() {
        return stmDate;
    }

    public void setStmDate(Date stmDate) {
        this.stmDate = stmDate;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getCreate_person() {
        return create_person;
    }

    public void setCreate_person(String create_person) {
        this.create_person = create_person;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public String getUpdate_person() {
        return update_person;
    }

    public void setUpdate_person(String update_person) {
        this.update_person = update_person;
    }
}
