package cn.bc.settlement.services.impl;

import java.text.DecimalFormat;
import java.util.*;

import cn.bc.settlement.po.PaymentInfo;
import cn.bc.common.support.page.Page;
import cn.bc.mchant.dao.MchantDao;
import cn.bc.mchant.po.MchStmConfig;
import cn.bc.query.po.CoAccount;
import cn.bc.query.po.CoAccountBill;
import cn.bc.settlement.dao.SettlementDao;
import cn.bc.settlement.po.BatchSettlePayment;
import cn.bc.settlement.po.SettlePayment;
import cn.bc.settlement.po.StmMchDetail;
import cn.bc.settlement.po.StmMerchant;
import cn.bc.settlement.services.SettlementService;
import cn.bc.settlement.vo.StmMchDetailFilter;
import cn.bc.settlement.vo.StmWechatFilter;
import org.apache.poi.hssf.usermodel.*;

public class SettlementServiceImpl implements SettlementService {
	SettlementDao dao;
	MchantDao mchDao;

	@Override
	public Page StmWechatList(StmWechatFilter filter) {
		return dao.StmWechatList(filter);
	}
	
	
	public void setDao(SettlementDao dao) {
		this.dao = dao;
	}

	public void setMchDao(MchantDao mchDao) {
		this.mchDao = mchDao;
	}

	@Override
	public void StmWechatUpdate(List<StmWechatFilter> filter){
		dao.StmWechatUpdate(filter);
	}
	
	@Override
	public Page listStmMchDetail(StmMchDetailFilter filter) {
		return dao.listStmMchDetail(filter);
	}

	@Override
	public void updateStmMchDetail(StmMchDetailFilter filter) {
		dao.updateStmMchDetail(filter);
	}


	@Override
	public void setOpenPuerDate(StmMerchant stm) {
		dao.setOpenPuerDate(stm);
	}


	@Override
	public void setStmMchDetail(StmMchDetail stm) {
		dao.setStmMchDetail(stm);
	}


	@Override
	public void updateCoAccountBill(CoAccountBill coBill) {
		dao.updateCoAccountBill(coBill);
	}


	@Override
	public void updateCoAccount(CoAccount co) {
		dao.updateCoAccount(co);
	}

	@Override
	public HSSFWorkbook createSettlementPayExcel(StmMchDetailFilter filter, PaymentInfo paymentInfo) {
		List items = this.dao.stmMchDetailList(filter);//查询当前显示的结算数据
		BatchSettlePayment batchSettlePayment = new BatchSettlePayment();
		Map<String,MchStmConfig> configDictionary = new HashMap<String,MchStmConfig>();

		for (Object obj : items){
			//遍历取值
			StmMchDetail mchStm = (StmMchDetail)obj;
			if(mchStm == null)
				continue;

			MchStmConfig config = configDictionary.get(mchStm.getMch_number());
			if(config == null);{
				config = this.mchDao.detailMchStmConfig(mchStm.getMch_number());
				if(config == null)
					continue;
				configDictionary.put(mchStm.getMch_number(), config);
			}

			SettlePayment stmPayment = this.createSettlePayment(config, mchStm, paymentInfo);
			if(stmPayment == null)
				continue;
			batchSettlePayment.addSettltPayment(stmPayment);
		}
		if(batchSettlePayment.getPaymentList().size() < 1)
			return null;
		return this.createSettlementPayExcelTitleAndValue(batchSettlePayment);
	}

	protected SettlePayment createSettlePayment(MchStmConfig config, StmMchDetail stmDetail, PaymentInfo paymentInfo){
		/*if(config.getMch_bank_open_bank_no() == null || config.getMch_bank_open_bank_no().isEmpty() || config.getMch_bank_open_bank()== null || config.getMch_bank_open_bank().isEmpty())
			return null;*/
		String payBankNoAndName = config.getMch_bank_open_bank_no() + "&" + config.getMch_bank_open_bank();
		SettlePayment stmPayment = new SettlePayment(payBankNoAndName);

		//设置客户号和付款账号
		stmPayment.setCustomerId(paymentInfo.getCustomerId());
		stmPayment.setPayAccount(paymentInfo.getPaymentAccount());

		stmPayment.setPayAmountByCoin(stmDetail.getCur_prepay_net());//设置交易金额为本期预付净额

		stmPayment.setCollectionAccount(config.getMch_account_card());
		stmPayment.setCollectionName(config.getMch_account_name());
		if(config.getMch_account_type().equals("0"))
			stmPayment.setCollectionAccountType("1");//设置收款账户类型为对私账户
		else if(config.getMch_account_type().equals("1"))
			stmPayment.setCollectionAccountType("0");//设置收款账户类型为对公账户
		else
			return null;

		//判断是否为跨行转账
		if(payBankNoAndName.contains(paymentInfo.getBankName()))
			stmPayment.setSingleType("1");
		else{
			stmPayment.setSingleType("2");
			if(stmPayment.getPayAmount() >= 50000.0f)
				stmPayment.setRemitting("7");
			stmPayment.setRemitting("6");
		}
		return stmPayment;
	}

	//根据批量打款对象生成excel文件
	protected HSSFWorkbook createSettlementPayExcelTitleAndValue(BatchSettlePayment batchSettlePayment){
		HSSFWorkbook wb = new HSSFWorkbook();
		DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		int curRow = 0;
		int curCol = 0;
		try{
			HSSFSheet sheet = wb.createSheet("sheet0");
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			HSSFRow row = this.createHSRow(sheet, curRow++);
			//1行1列
			HSSFCell cell = this.createHSCell(row,curCol++,cellStyle,"审核方式（文件类型）");
			//1行2列
			cell = createHSCell(row,curCol--,cellStyle,batchSettlePayment.getMonitorialMode());
			//2行1列
			row = this.createHSRow(sheet, curRow++);
			cell = this.createHSCell(row,curCol++,cellStyle,"总金额");
			//2行2列
			cell =this.createHSCell(row,curCol--,cellStyle,decimalFormat.format(batchSettlePayment.getTotalAmount()));
			//3行1列
			row = this.createHSRow(sheet, curRow++);
			cell = this.createHSCell(row,curCol++,cellStyle,"总笔数");
			//3行2列
			cell = this.createHSCell(row,curCol--,cellStyle,String.valueOf(batchSettlePayment.getTotalCount()));
			//4行表头
			row = this.createHSRow(sheet, curRow++);
			this.createHSCell(row, curCol++,cellStyle,"制单类型");
			this.createHSCell(row, curCol++,cellStyle,"企业自制凭证号");
			this.createHSCell(row, curCol++,cellStyle,"客户号");
			this.createHSCell(row, curCol++,cellStyle,"预约标志");
			this.createHSCell(row, curCol++,cellStyle,"付款账号	");
			this.createHSCell(row, curCol++,cellStyle,"交易金额");
			this.createHSCell(row, curCol++,cellStyle,"收款账号");
			this.createHSCell(row, curCol++,cellStyle,"收款人姓名");
			this.createHSCell(row, curCol++,cellStyle,"收款账户类型");
			this.createHSCell(row, curCol++,cellStyle,"子客户号");
			this.createHSCell(row, curCol++,cellStyle,"子付款账号");
			this.createHSCell(row, curCol++,cellStyle,"子付款账户名");
			this.createHSCell(row, curCol++,cellStyle,"子付款账户开户行名");
			this.createHSCell(row, curCol++,cellStyle,"用途");
			this.createHSCell(row, curCol++,cellStyle,"汇路");
			this.createHSCell(row, curCol++,cellStyle,"是否通知收款人");
			this.createHSCell(row, curCol++,cellStyle,"手机号码");
			this.createHSCell(row, curCol++,cellStyle,"邮箱");
			this.createHSCell(row, curCol++,cellStyle,"支付行号&支付行名称");

			for(SettlePayment payment : batchSettlePayment.getPaymentList()){
				curCol = 0;
				row = this.createHSRow(sheet, curRow++);
				this.createHSCell(row, curCol++,cellStyle,payment.getSingleType());
				this.createHSCell(row, curCol++,cellStyle,payment.getSelfCertificate());
				this.createHSCell(row, curCol++,cellStyle,payment.getCustomerId());
				this.createHSCell(row, curCol++,cellStyle,payment.getAppointmentFlag());
				this.createHSCell(row, curCol++,cellStyle,payment.getPayAccount());
				this.createHSCell(row, curCol++,cellStyle,decimalFormat.format(payment.getPayAmount()));
				this.createHSCell(row, curCol++,cellStyle,payment.getCollectionAccount());
				this.createHSCell(row, curCol++,cellStyle,payment.getCollectionName());
				this.createHSCell(row, curCol++,cellStyle,payment.getCollectionAccountType());
				this.createHSCell(row, curCol++,cellStyle,payment.getSubCustomerId());
				this.createHSCell(row, curCol++,cellStyle,payment.getSubPayAccount());
				this.createHSCell(row, curCol++,cellStyle,payment.getSubAccountName());
				this.createHSCell(row, curCol++,cellStyle,payment.getSubPayAccountCreateBankName());
				this.createHSCell(row, curCol++,cellStyle,payment.getUsage());
				this.createHSCell(row, curCol++,cellStyle,payment.getRemitting());
				this.createHSCell(row, curCol++,cellStyle,payment.getNotifyCollection());
				this.createHSCell(row, curCol++,cellStyle,payment.getCollectionMobilePhoneNo());
				this.createHSCell(row, curCol++,cellStyle,payment.getCollectionMail());
				this.createHSCell(row, curCol++,cellStyle,payment.getPayBankNoAndName());
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		return wb;
	}

	protected HSSFRow createHSRow(HSSFSheet sheet, int index){
		return sheet.createRow(index);
	}

	protected HSSFCell createHSCell(HSSFRow row, int index, HSSFCellStyle style, String valueStr){
		HSSFCell cell = row.createCell(index,HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(style);
		cell.setCellValue(valueStr);
		return cell;
	}

}
