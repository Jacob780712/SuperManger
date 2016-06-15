package cn.bc.business.action;

import java.io.File;













import cn.bc.business.po.OnBankCardVerifi;
import cn.bc.business.po.OnCashOuts;
import cn.bc.business.po.OnUser;
import cn.bc.business.po.YhNoticeLogs;
import cn.bc.business.services.CashServices;
import cn.bc.business.services.MessageLogsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;

import cn.bc.common.exception.BusinessException;
import cn.bc.common.util.DateUtils;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.query.po.OnBankCard;
import cn.bc.supervalue.outer.service.impl.SMSNotifyServiceImpl;
import cn.bc.supervalue.outer.vo.SMSNotifyResponse;
import cn.bc.util.SendMsg;
import cn.common.security.vo.User;

import com.opensymphony.xwork2.ActionContext;
public class CashOutImportAction  extends BaseAction{
	private CashServices cashSer;
	private MessageLogsService messageLogsService;
	private File[] upload;
	private String uploadFileName; // 上传的文件名称
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String infoMessage ;
	
	private String userName;//用户姓名
	private String mobliePhone;//用户手机号
	private String cardNumber;//用户银行卡号
	private String idCard;//用户身份证号

	//导入提现数据
	public String listToAllImport() throws Exception {
		HttpServletRequest request = (HttpServletRequest) super.getContextParam(ServletActionContext.HTTP_REQUEST);
		List<OnCashOuts> onCashOuts = null;
		List<OnCashOuts> resList = new ArrayList<OnCashOuts>();
		Date date = new Date();
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		User user = (User) session.get("loginUser");
		if (user == null) {
			throw new Exception("用户没有登录");
		}
		int idx = uploadFileName.lastIndexOf(".");   
        String fileType = uploadFileName.substring(idx).toLowerCase(); //将扩展名转化为小写，因为上传的文件扩展名可能有的是大写,如##.CHM,
        //大小 类型 验证
        if(!(".xls".equalsIgnoreCase(fileType)||".xlsx".equalsIgnoreCase(fileType))){
        	infoMessage = "文件扩展名不是xls或xlsx！";
        	throw new BusinessException("错误的文件格式【"+fileType+"】");
        }
		File[] fileUp = this.getUpload();
		int result = 0;
		if (fileUp.length > 0 && fileUp != null) {
			// 根据文件路径得到文件中的参数值集合
			onCashOuts = readxls(fileUp[0]);
			if (onCashOuts != null && onCashOuts.size() > 0) {
				cashSer.saveImportInfo(onCashOuts,date,user.getName());
				infoMessage = "成功导入"+onCashOuts.size()+"条数据！";
				resList = cashSer.importResult(onCashOuts.get(0).getBatch_number().trim());
			} else {
				throw new Exception("导入数据为空，请重试");
			}
		}
	
		InputStream inStream = CashOutImportAction.class.getResourceAsStream("/cash_out.properties");
		Properties prop = new Properties();  
		try {
			prop.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		String url = prop.getProperty("url")+"?batchNumber="+onCashOuts.get(0).getBatch_number();
		System.out.println(url);
		String resultMesg = sendGet(url);//发送客户端接口，通知用户
		System.out.println(resultMesg);
		request.setAttribute("resList", resList);
		return SUCCESS;
	}
	
	static String sendGet(String url) {
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
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
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
	
	
	//读取提现excel
	private List<OnCashOuts> readxls(File file) throws Exception {
		List<OnCashOuts> list = new ArrayList<OnCashOuts>();
		OnCashOuts cstCashOutInfo = null;
		try {
			// 打开文件
			Workbook book = Workbook.getWorkbook(file);
			// 得到第一个工作表对象
			Sheet sheet = book.getSheet(0);
			// 得到第一个工作表中的总行数
			int rowCount = sheet.getRows();
			// 循环取出Excel中的内容
			for (int i = 1; i < rowCount; i++) {
				Cell[] cells = sheet.getRow(i);
				cstCashOutInfo = new OnCashOuts();
				cstCashOutInfo.setBatch_number(cells[0].getContents().trim());//批次号
				cstCashOutInfo.setId_card(cells[2].getContents().trim()); //证件号码
				cstCashOutInfo.setCard_number(cells[3].getContents().trim()); //卡号
				Double d = new Double(cells[4].getContents());
				cstCashOutInfo.setAmount((int)(float)(d*100));       //金额
				String str =cells[6].getContents().trim();
				
				if("成功".equalsIgnoreCase(str))
				{
					cstCashOutInfo.setStatus("1");//付款结果
				}else
					cstCashOutInfo.setStatus("2");//付款结果
				cstCashOutInfo.setRemark(str);
				list.add(cstCashOutInfo);
				
			}
			return list;
		} catch (Exception e) {
			throw new Exception("读取excel错误，请检查导入文件是否正确");
		}
	}
	
	//导入银行卡验证数据
	public String importVerfiData() throws Exception{
		HttpServletRequest request = (HttpServletRequest) super.getContextParam(ServletActionContext.HTTP_REQUEST);
		List<OnBankCard> onBankCardList = null;
		Date date = new Date();
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		User user = (User) session.get("loginUser");
		if (user == null) {
			throw new Exception("用户没有登录");
		}
		int idx = uploadFileName.lastIndexOf(".");   
        String fileType = uploadFileName.substring(idx).toLowerCase(); //将扩展名转化为小写，因为上传的文件扩展名可能有的是大写,如##.CHM,
        //大小 类型 验证
        if(!(".xls".equalsIgnoreCase(fileType)||".xlsx".equalsIgnoreCase(fileType))){
        	infoMessage = "文件扩展名不是xls或xlsx！";
        	throw new BusinessException("错误的文件格式【"+fileType+"】");
        }
		File[] fileUp = this.getUpload();
		int result = 0;
		if (fileUp.length > 0 && fileUp != null) {
			// 根据文件路径得到文件中的参数值集合
			onBankCardList = readVerfixls(fileUp[0]);
			for(int i=0;i<onBankCardList.size();i++){
				cashSer.setStatus(onBankCardList.get(i));
				OnBankCardVerifi onBankCardVerifi = new OnBankCardVerifi();
				if(onBankCardList.get(i).getStatus().equals("0")){
					onBankCardVerifi.setStatus("1");
				}else{
					onBankCardVerifi.setStatus("0");
					sendmsg(onBankCardList.get(i).getCard_number());
				}
				
				onBankCardVerifi.setCreate_date(date);
				onBankCardVerifi.setCardNumber(onBankCardList.get(i).getCard_number());
				cashSer.setVerifiResult(onBankCardVerifi);
			}
		} else {
			throw new Exception("导入数据为空，请重试");
		}
		request.setAttribute("result", onBankCardList);
		return SUCCESS;
	}
	
	//读取银行卡验证的excel
	private List<OnBankCard> readVerfixls(File file) throws Exception {
		List<OnBankCard> list = new ArrayList<OnBankCard>();
		OnBankCard card = null;
		try {
			// 打开文件
			Workbook book = Workbook.getWorkbook(file);
			// 得到第一个工作表对象
			Sheet sheet = book.getSheet(0);
			// 得到第一个工作表中的总行数
			int rowCount = sheet.getRows();
			// 循环取出Excel中的内容
			for (int i = 1; i < rowCount; i++) {
				Cell[] cells = sheet.getRow(i);
				card = new OnBankCard();
				card.setCard_number(cells[1].getContents().trim()); //卡号
				String str =cells[10].getContents().trim();
				if("成功".equalsIgnoreCase(str))
				{
					card.setStatus("0");//付款结果
				}else{
					card.setStatus("2");//付款结果
				}
				list.add(card);
			}
			return list;
		} catch (Exception e) {
			throw new Exception("读取excel错误，请检查导入文件是否正确");
		}
	}
	
	
	//对于失败的银行卡进行短信提醒
	public void sendmsg(String cardNumber){
		OnBankCard onBankCard = cashSer.getUser(cardNumber);
		String mobile = onBankCard.getMobile();
		String msg = "您的尾号为"+cardNumber.substring(cardNumber.length()-4,cardNumber.length())
				+"的提现银行卡未能通过校验，可能是卡号错误或我们暂不支持该银行，请重新设置。";
		String desc = "银行卡验证失败提醒";
		SMSNotifyServiceImpl impl = new SMSNotifyServiceImpl();
		try {
			SendMsg.setMobile(mobile.trim());
			SendMsg.setMsg(msg);
			SMSNotifyResponse sr = SendMsg.send();
			YhNoticeLogs yhmsg = new YhNoticeLogs();
			Date date = new Date();
	        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			yhmsg.setCstUserId(0);
			yhmsg.setToAddress(mobile.trim());
			yhmsg.setNoticeFlag("y");
			yhmsg.setNoticeType("sms/mt");yhmsg.setCreateDate(DateUtils.strToSqlDate(format.format(date)));
			yhmsg.setNoticeDate(DateUtils.strToSqlDate(format.format(date)));
			yhmsg.setNoticeResultDesc("");
			yhmsg.setNoticeContent(msg.trim());
			yhmsg.setDeliveryDesc(desc);
			yhmsg.setTransactionId(0);
			yhmsg.setNoticeResult(sr.getSendResult());
			yhmsg.setMessageId(sr.getMessageId());
			messageLogsService.save(yhmsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//导入用户信息设置的银行卡验证结果
	public String importResultUserBankCard() throws Exception{
		HttpServletRequest request = (HttpServletRequest) super.getContextParam(ServletActionContext.HTTP_REQUEST);
		List<OnBankCard> onBankCardList = null;
		Date date = new Date();
		int idx = uploadFileName.lastIndexOf(".");   
        String fileType = uploadFileName.substring(idx).toLowerCase(); //将扩展名转化为小写，因为上传的文件扩展名可能有的是大写,如##.CHM,
        //大小 类型 验证
        if(!(".xls".equalsIgnoreCase(fileType)||".xlsx".equalsIgnoreCase(fileType))){
        	infoMessage = "文件扩展名不是xls或xlsx！";
        	throw new BusinessException("错误的文件格式【"+fileType+"】");
        }
		File[] fileUp = this.getUpload();
		String flag = "0";//验证结果 1成功 0失败
		if (fileUp.length > 0 && fileUp != null) {
			// 根据文件路径得到文件中的参数值集合
			onBankCardList = readVerfixls(fileUp[0]);
			for(int i=0;i<onBankCardList.size();i++){
				if(!onBankCardList.get(i).getCard_number().equals(cardNumber)){
					throw new Exception("导入文件中的银行卡号与输入的银行卡号不符");
				}
				OnBankCardVerifi onBankCardVerifi = new OnBankCardVerifi();
				if(onBankCardList.get(i).getStatus().equals("0")){
					onBankCardVerifi.setStatus("1");
					//验证成功，设置用户信息
					OnUser user = new OnUser();
					user.setFull_name(userName.trim());
					user.setMobile_phone(mobliePhone.trim());
					user.setId_card(idCard.trim());
					cashSer.setUserBankCard(user,cardNumber);
					flag = "1";
				}else{
					onBankCardVerifi.setStatus("0");
					sendmsgImport(onBankCardList.get(i).getCard_number(),mobliePhone);
				}
				
				onBankCardVerifi.setCreate_date(date);
				onBankCardVerifi.setCardNumber(onBankCardList.get(i).getCard_number());
				cashSer.setVerifiResult(onBankCardVerifi);
			}
		} else {
			throw new Exception("导入数据为空，请重试");
		}
		request.setAttribute("flag", flag);
		request.setAttribute("userName", userName);
		request.setAttribute("mobliePhone", mobliePhone);
		request.setAttribute("cardNumber", cardNumber);
		request.setAttribute("idCard", idCard);
		return SUCCESS;
	}
	
	public void sendmsgImport(String cardNumber,String mobile){
		String msg = "您的尾号为"+cardNumber.substring(cardNumber.length()-4,cardNumber.length())
				+"的提现银行卡未能通过校验，可能是卡号错误或我们暂不支持该银行，请重新设置。";
		String desc = "银行卡验证失败提醒";
		SMSNotifyServiceImpl impl = new SMSNotifyServiceImpl();
		try {
			SendMsg.setMobile(mobile.trim());
			SendMsg.setMsg(msg);
			SMSNotifyResponse sr = SendMsg.send();
			YhNoticeLogs yhmsg = new YhNoticeLogs();
			Date date = new Date();
	        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			yhmsg.setCstUserId(0);
			yhmsg.setToAddress(mobile.trim());
			yhmsg.setNoticeFlag("y");
			yhmsg.setNoticeType("sms/mt");yhmsg.setCreateDate(DateUtils.strToSqlDate(format.format(date)));
			yhmsg.setNoticeDate(DateUtils.strToSqlDate(format.format(date)));
			yhmsg.setNoticeResultDesc("");
			yhmsg.setNoticeContent(msg.trim());
			yhmsg.setDeliveryDesc(desc);
			yhmsg.setTransactionId(0);
			yhmsg.setNoticeResult(sr.getSendResult());
			yhmsg.setMessageId(sr.getMessageId());
			messageLogsService.save(yhmsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File[] getUpload() {
		return upload;
	}

	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}
	
	
	public void setMessageLogsService(MessageLogsService messageLogsService) {
		this.messageLogsService = messageLogsService;
	}

	public void setCashSer(CashServices cashSer) {
		this.cashSer = cashSer;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setMobliePhone(String mobliePhone) {
		this.mobliePhone = mobliePhone;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	
	
}	
