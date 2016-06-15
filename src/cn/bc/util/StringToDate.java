package cn.bc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {
	public static Date toDate(String format,String str){
		 SimpleDateFormat sdf = new SimpleDateFormat(format);                
		 try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			try {
				throw new Exception("字符串转日期异常");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		} 
	}
}
