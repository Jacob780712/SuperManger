package cn.bc.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.bc.query.bo.PlatFormRE;
import cn.bc.query.bo.PlatShuoru;
import cn.bc.query.vo.PrepaidCardDetails;
import cn.bc.query.vo.TransactionDetail;

public class ExportExl {
	
	public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
	public static DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
	//生成导出excel
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook exportTransData(List<TransactionDetail> vec_data) throws Exception {
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
			cell.setCellValue("日期");
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("时间");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("订单号");

			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("订单状态");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("订单类型");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("手机号");

			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("是否为首次买单");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("商户名称");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("门店名称");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("订单金额");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("账户支付金额");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("微信支付金额");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("储值用户返现");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("平台返现");
			
			for (int mn = 0; mn < num; mn++) {
				TransactionDetail data = null;
				for (int i = 0; i < num; i++) {
					column = 0;
					data = (TransactionDetail) vec_data.get(i);
					row = sheet.createRow(i + 1);
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(format.format(data.getCreate_date()));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(format2.format(data.getCreate_date()));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getOrder_number());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					if(data.getStatus().equals("0")){
						cell.setCellValue("处理中");
					}
					if(data.getStatus().equals("1")){
						cell.setCellValue("成功");
					}
					if(data.getStatus().equals("2")){
						cell.setCellValue("失败");
					}
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					if("0".equals(data.getOrder_type())){
						cell.setCellValue("储值用户买单");
					}
					if("1".equals(data.getOrder_type())){
						cell.setCellValue("普通用户买单");
					}
					if(!"0".equals(data.getOrder_type())&&!"1".equals(data.getOrder_type())){
						cell.setCellValue("礼遇");
					}
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getMobile_phone());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					if("1".equals(data.getFirstFlag())){
						cell.setCellValue("是");
					}
					if("0".equals(data.getFirstFlag())){
						cell.setCellValue("否");
					}
					if(data.getFirstFlag()==null){
						cell.setCellValue("");
					}
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getMch_name());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getBranch_name());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getBusiness_amount())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getAccount_pay_amount())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getPay_amount())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getFanxian())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getO_return_amount())/100));
					
					column = 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return wb;
	}	
	
	//储值明细导出
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook prepaidCardDetailsExport(List<PrepaidCardDetails> vec_data) throws Exception {
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
			cell.setCellValue("日期");
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("时间");
		
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("商户名称");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("门店名称");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("卡种");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("类型");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("折扣");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("卡号");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("卡状态");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("储值面额");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("购买金额");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("当前余额");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("购卡人姓名");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("手机号");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("是否首次购卡");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("累计购卡张数");
			
			for (int mn = 0; mn < num; mn++) {
				PrepaidCardDetails data = null;
				for (int i = 0; i < num; i++) {
					column = 0;
					data = (PrepaidCardDetails) vec_data.get(i);
					row = sheet.createRow(i + 1);
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(format.format(data.getCreate_date()));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(format2.format(data.getCreate_date()));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getMch_name());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getBranch_name());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getCk_name());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					if("0".equals(data.getCk_type())){
						cell.setCellValue("买送");
					}
					if("1".equals(data.getCk_type())){
						cell.setCellValue("买折");
					}
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getCk_discount()+"折卡");

					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getSvc_number());

					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					if("0".equals(data.getStatus())){
						cell.setCellValue("正常");
					}
					if("1".equals(data.getStatus())){
						cell.setCellValue("用尽");
					}
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getCk_quota())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getSales_amount())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getSvc_balance())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getName());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getMobile());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					if("0".equals(data.getFirstFlag())){
						cell.setCellValue("否");
					}
					if("1".equals(data.getFirstFlag())){
						cell.setCellValue("是");
					}
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getZhangshu());
					
					column = 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return wb;
	}	
	
	//平台收入每日统计导出数据
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook listPlatformExport(List<PlatFormRE> vec_data) throws Exception {
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
			cell.setCellValue("日期");
		
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("微信手续费");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("提现手续费");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("总支出");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("商户储值佣金");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("平台返现");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("总收入");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("当日净收入");
			
			for (int mn = 0; mn < num; mn++) {
				PlatFormRE data = null;
				for (int i = 0; i < num; i++) {
					column = 0;
					data = (PlatFormRE) vec_data.get(i);
					row = sheet.createRow(i + 1);
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getDate());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getWeixinAmount())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getTixianAmount())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getZhichu())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getMachantAmount())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getPlatAmount())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getShouru())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getAll())/100));
					
					column = 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return wb;
	}	
	
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook 	listPlatListExport(List<PlatShuoru> vec_data) throws Exception {
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
			cell.setCellValue("日期");
		
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("储值移动支付（代收）");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("买单移动支付（代收）");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("代收合计");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("商商户结算费用");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("用户提现费用");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("代付合计");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("沉淀资金");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("微信手续费");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("提现手续费");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("总支出");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("商户佣金");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("用户佣金");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("总收入");
			
			cell = row.createCell((short) column++,
					HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("当日净收入");
			for (int mn = 0; mn < num; mn++) {
				PlatShuoru data = null;
				for (int i = 0; i < num; i++) {
					column = 0;
					data = (PlatShuoru) vec_data.get(i);
					row = sheet.createRow(i + 1);
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(i + 1);
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(data.getDate());
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getCz())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getMd())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getCz()+data.getMd())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getMjs())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getTx())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getMjs()+data.getTx())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getCd())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getWx())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getTxx())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getTxx()+data.getWx())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getYsh())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getYyh())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getYyh()+data.getYsh())/100));
					
					cell = row.createCell((short) column++,
							HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(df.format(Double.valueOf(data.getYyh()+data.getYsh()-data.getTxx()-data.getWx())/100));
					
					column = 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return wb;
	}
	
}
