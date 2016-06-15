package cn.bc.business.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

import cn.bc.business.po.OnCashOuts;
import cn.bc.business.po.OnUser;
import cn.bc.business.po.YhNoticeLogs;
import cn.bc.business.services.CashServices;
import cn.bc.business.services.MessageLogsService;
import cn.bc.business.vo.OnCashOutsFilter;
import cn.bc.common.support.page.Page;
import cn.bc.common.util.DateUtils;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.query.po.OnBankCard;
import cn.bc.supervalue.outer.service.impl.SMSNotifyServiceImpl;
import cn.bc.supervalue.outer.vo.SMSNotifyResponse;
import cn.bc.util.SendMsg;
import cn.common.security.vo.User;

public class CashAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CashServices cashSer;
	private MessageLogsService messageLogsService;
	//提现查询列表
	public String cashOutList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		OnCashOutsFilter filter = new OnCashOutsFilter();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String full_name = request.getParameter("full_name");
		String mobilePhone = request.getParameter("mobilePhone");
		String card_number = request.getParameter("card_number");
		String batch_number = request.getParameter("batch_number");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		filter.setFull_name(full_name);
		filter.setMobilePhone(mobilePhone);
		filter.setCard_number(card_number);
		filter.setBatch_number(batch_number);
		String status = request.getParameter("status");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if(status!=null&&!"".equals(status)){
			filter.setStatus(status);
		}
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		
		Page page = cashSer.list(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//人工驳回
	public String cashOutReject() throws Exception{
		DateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		User user = (User) session.get("loginUser");
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String id = request.getParameter("rejectId").trim();
		OnCashOuts onCashOuts = new OnCashOuts();
		String batch_number = format.format(new Date())+"rp";
		onCashOuts.setId(Integer.valueOf(id));
		onCashOuts.setRemark("人工驳回");
		onCashOuts.setBatch_number(batch_number);
		onCashOuts.setUpdate_date(new Date());
		onCashOuts.setStatus("2");
		onCashOuts.setUpdate_person(user.getName());
		cashSer.reject(onCashOuts);
		InputStream inStream = CashOutImportAction.class.getResourceAsStream("/cash_out.properties");
		Properties prop = new Properties();  
		prop.load(inStream);
		String url = prop.getProperty("url")+"?batchNumber="+batch_number;
		System.out.println(url);
		String resultMesg = "";
		resultMesg = CashOutImportAction.sendGet(url);//发送客户端接口，通知用户
		System.out.println(resultMesg);
		if(resultMesg==null||"".equals(resultMesg)){
			onCashOuts.setStatus("0");
			onCashOuts.setRemark("人工驳回，通知用户失败");
			cashSer.reject(onCashOuts);
			throw new Exception("提现结果通知用户失败");
		}else{
			JSONObject a = new JSONObject(resultMesg); 
		    String resultCode = (String) a.get("code");
		    String resultMessage = (String) a.get("message");
		    System.out.println(resultMessage);
			if(resultCode!=null&&resultCode.trim().equals("-1")){
				onCashOuts.setStatus("0");
				onCashOuts.setRemark("人工驳回，通知用户失败");
				cashSer.reject(onCashOuts);
				throw new Exception("提现结果通知用户失败");
			}else{
				OnCashOuts onCashOuts2 = new OnCashOuts();
				onCashOuts2.setId(Integer.valueOf(id));
				onCashOuts2.setBatch_number(batch_number);
				onCashOuts2.setUpdate_date(new Date());
				onCashOuts2.setRemark("人工驳回");
				onCashOuts2.setUpdate_person(user.getName());
				onCashOuts2.setStatus("2");
				cashSer.reject(onCashOuts2);
			}
		}
		response.sendRedirect("cashOutList.jhtml");
		return null;
	}
	
	//导出提现文件
	public String cashOutExport()throws Exception{
		
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
		Date date = new Date();
        Map session = ctx.getSession();
		User user = (User) session.get("loginUser");
		DateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
		OutputStream os = null;
		try {
			OnCashOutsFilter filter = new OnCashOutsFilter();
			String status = "0";
			filter.setStatus(status);
			String batch_number = format2.format(date);
			filter.setBatch_number(batch_number);
			filter.setUpdate_date(date);
			filter.setUpdate_person(user.getName());
			List<OnCashOuts> list = (List<OnCashOuts>) cashSer.search(filter);
			HSSFWorkbook wb = exportListToAllExport(list);
			String getTime = format2.format(new Date());
			String filename = getTime + "提现申请列表.xls";
			os = response.getOutputStream();
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename="+new String(filename.getBytes("gb2312"),"iso8859-1"));
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出提现文件失败！");
		}
		finally{
			if(os!=null)
				os.close();
		}
		return null;
	}

	//生成导出excel
	@SuppressWarnings("deprecation")
	public HSSFWorkbook exportListToAllExport(List<OnCashOuts> vec_data) throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			int num = vec_data.size();

			HSSFSheet sheet = wb.createSheet("sheet0");
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			int rows = 0;
			int column = 0;
			//序号	证件号码	卡号	金额	付款结果（y或n）	描述
			HSSFRow row = sheet.createRow(rows);
			HSSFCell cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("序号");
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("批次号");
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("身份证号");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("卡号");

			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("金额");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("开户名");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("清算联行号");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("款项用途");

			for (int mn = 0; mn < num; mn++) {
				OnCashOuts onCashOuts = null;
				for (int i = 0; i < num; i++) {
					column = 0;
					onCashOuts = (OnCashOuts) vec_data.get(i);
					row = sheet.createRow(i + 1);
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(onCashOuts.getBatch_number());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(onCashOuts.getId_card());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(onCashOuts.getCard_number());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					System.out.println((double)onCashOuts.getAmount()/100);
					cell.setCellValue((double)onCashOuts.getAmount()/100);
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(onCashOuts.getFull_name());
					
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue("");
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue("代付费用");
					
					column = 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return wb;
	}	
	
	//导入提现列表页面
	public String importExl(){
		return SUCCESS;
	}
	
	//提现统计表
	public String cashOutStatis() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		OnCashOutsFilter filter = new OnCashOutsFilter();
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		filter.setBeginTime(beginTime);
		filter.setEndTime(endTime);
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		
		Page page = cashSer.cashOutStatis(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//查询新添加的未提现过的银行卡列表
	public String listBankCard(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		OnCashOutsFilter filter = new OnCashOutsFilter();
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);
		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		
		Page page = cashSer.listBankCard(filter);
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	//人工驳回新添加的银行卡
	public String rejectBank() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String cardNumber  = request.getParameter("cardNumber");
		cashSer.reject(cardNumber);
		sendmsg(cardNumber);
		response.sendRedirect("listBankCard.jhtml");
		return null;
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

	//导出银行卡验证的数据
	public String exportVerification() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
		Date date = new Date();
        Map session = ctx.getSession();
		User user = (User) session.get("loginUser");
		DateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
		OutputStream os = null;
		try {
			OnCashOutsFilter filter = new OnCashOutsFilter();
			String status = "0";
			filter.setStatus(status);
			String batch_number = format2.format(date);
			filter.setBatch_number(batch_number);
			filter.setUpdate_date(date);
			filter.setUpdate_person(user.getName());
			List<OnBankCard> list = cashSer.exportBankCard();
			HSSFWorkbook wb = exportBankCardWb(list);
			String getTime = format2.format(new Date());
			String filename = getTime + "银行卡验证列表.xls";
			os = response.getOutputStream();
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename="+new String(filename.getBytes("gb2312"),"iso8859-1"));
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出提现文件失败！");
		}
		finally{
			if(os!=null)
				os.close();
		}
		return null;
	}

	//生成银行卡验证的文件
	public HSSFWorkbook exportBankCardWb(List<OnBankCard> vec_data) throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			int num = vec_data.size();

			HSSFSheet sheet = wb.createSheet("sheet0");
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			int rows = 0;
			int column = 0;
			//序号	证件号码	卡号	金额	付款结果（y或n）	描述
			HSSFRow row = sheet.createRow(rows);
			HSSFCell cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("序号");
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("证件号码");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("银行卡卡号");

			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("金额");

			for (int mn = 0; mn < num; mn++) {
				OnBankCard onCashOuts = null;
				for (int i = 0; i < num; i++) {
					column = 0;
					onCashOuts = (OnBankCard) vec_data.get(i);
					row = sheet.createRow(i + 1);
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(onCashOuts.getId_card());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(onCashOuts.getCard_number());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue("0.01");
					column = 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return wb;
	}	
	
	//导入银行卡验证列表页面
	public String importVerfiBankCardList(){
		return SUCCESS;
	}
	
	//进入客服处理设置用户信息
	public String goSetUserInfo(){
		return SUCCESS;
	}
	//设置用户信息
	public String setUserInfo() throws Exception{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String userName = request.getParameter("userName");
		String mobliePhone = request.getParameter("mobliePhone");
		String cardNumber = request.getParameter("cardNumber");
		String idCard = request.getParameter("idCard");
		OnUser user = new OnUser();
		user.setFull_name(userName.trim());
		user.setMobile_phone(mobliePhone.trim());
		user.setId_card(idCard.trim());
		if(!cashSer.checkUserInfo(user)){
			throw new Exception("用户的身份信息错误，请重试");
		}
		if(cashSer.checkCard(cardNumber)){
			throw new Exception("该银行卡已经被添加，请重试");
		}
		HttpServletResponse response = (HttpServletResponse) super.getContextParam(ServletActionContext.HTTP_RESPONSE);
		OutputStream os = null;
		try {
			List<OnBankCard> list = new ArrayList<OnBankCard>();
			OnBankCard card = new OnBankCard();
			card.setCard_number(cardNumber.trim());
			card.setId_card(idCard.trim());
			list.add(card);
			HSSFWorkbook wb = exportBankCardWb(list);
			String filename = userName+"的银行卡验证文件.xls";
			os = response.getOutputStream();
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment; filename="+new String(filename.getBytes("gb2312"),"iso8859-1"));
			wb.write(os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出文件失败！");
		}
		finally{
			if(os!=null)
				os.close();
		}
		return null;
	}
	//导入XX的银行卡验证结果页面
	public String importUserBankCard(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String userName = request.getParameter("userName");
		String mobliePhone = request.getParameter("mobliePhone");
		String cardNumber = request.getParameter("cardNumber");
		String idCard = request.getParameter("idCard");
		request.setAttribute("userName", userName);
		request.setAttribute("mobliePhone", mobliePhone);
		request.setAttribute("cardNumber", cardNumber);
		request.setAttribute("idCard", idCard);
		return SUCCESS;
	}
	
	public void setMessageLogsService(MessageLogsService messageLogsService) {
		this.messageLogsService = messageLogsService;
	}

	public void setCashSer(CashServices cashSer) {
		this.cashSer = cashSer;
	}
	
}
