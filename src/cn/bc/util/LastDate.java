package cn.bc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LastDate {
	public static int lastDate(Date date,List<Integer> list){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		try {
			date = df.parse(df.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar=Calendar.getInstance();  
		calendar.setTime(date);  
		int year = calendar.get(Calendar.YEAR);
	    int month =calendar.get(Calendar.MONTH)+1;
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
	    int result =0;
		for(int i=0;i<list.size();i++){
			if(list.size()==1){
				calendar.set(year,month,list.get(i));
				long time2 = calendar.getTimeInMillis();
				result = (int) ((time2-date.getTime())/(1000*60*60*24));
			}
			if(list.size()==2){
				if(day<16){
					result = 16-day;
				}
				if(day>=16){
					calendar.set(year,month,1);
					long time2 = calendar.getTimeInMillis();
					result = (int) ((time2-date.getTime())/(1000*60*60*24));
				}
			}
			if(list.size()==3){
				if(day<11){
					result = 11-day;
				}
				if(day>=11&&day<21){
					result = 21-day;
				}
				if(day>=21){
					calendar.set(year,month,1);
					long time2 = calendar.getTimeInMillis();
					result = (int) ((time2-date.getTime())/(1000*60*60*24));
				}
			}
		}
		return result;
	}
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");  
		Date date = df.parse("2016-01-22");
		List<Integer> list = new ArrayList<Integer>();
//		list.add(1);
		
//		list.add(1);
//		list.add(16);
//		
		list.add(1);
		list.add(11);
		list.add(21);
		System.out.println(lastDate(date,list));
	}
}
