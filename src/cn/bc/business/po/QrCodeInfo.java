package cn.bc.business.po;

import java.util.Date;

/**
 * Created by xxsoul on 2016/3/8.
 */
public class QrCodeInfo {
    private Integer Id;
    private String qrChannelName;
    private String qrPicUrl;
    private String qrTicket;
    private String qrStatus;
    private String qrRemark;
    private Date qrCreateDate;
    private String qrCreatePerson;
    private Date qrUpdateDate;
    private String qrUpdatePerson;
    private String qrContent;
    private String qrHref;
    

 

	public String getQrContent() {
		return qrContent;
	}

	public void setQrContent(String qrContent) {
		this.qrContent = qrContent;
	}

	public String getQrHref() {
		return qrHref;
	}

	public void setQrHref(String qrHref) {
		this.qrHref = qrHref;
	}

	public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getQrChannelName() {
        return qrChannelName;
    }

    public void setQrChannelName(String qrChannelName) {
        this.qrChannelName = qrChannelName;
    }

    public String getQrPicUrl() {
        return qrPicUrl;
    }

    public void setQrPicUrl(String qrPicUrl) {
        this.qrPicUrl = qrPicUrl;
    }

    public String getQrTicket() {
        return qrTicket;
    }

    public void setQrTicket(String qrTicket) {
        this.qrTicket = qrTicket;
    }

    public String getQrStatus() {
        return qrStatus;
    }

    public void setQrStatus(String qrStatus) {
        this.qrStatus = qrStatus;
    }

    public String getQrRemark() {
        return qrRemark;
    }

    public void setQrRemark(String qrRemark) {
        this.qrRemark = qrRemark;
    }

    public Date getQrCreateDate() {
        return qrCreateDate;
    }

    public void setQrCreateDate(Date qrCreateDate) {
        this.qrCreateDate = qrCreateDate;
    }

    public String getQrCreatePerson() {
        return qrCreatePerson;
    }

    public void setQrCreatePerson(String qrCreatePerson) {
        this.qrCreatePerson = qrCreatePerson;
    }

    public Date getQrUpdateDate() {
        return qrUpdateDate;
    }

    public void setQrUpdateDate(Date qrUpdateDate) {
        this.qrUpdateDate = qrUpdateDate;
    }

    public String getQrUpdatePerson() {
        return qrUpdatePerson;
    }

    public void setQrUpdatePerson(String qrUpdatePerson) {
        this.qrUpdatePerson = qrUpdatePerson;
    }
}
