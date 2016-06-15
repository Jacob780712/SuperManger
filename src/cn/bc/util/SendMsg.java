package cn.bc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;


import cn.bc.common.util.HttpClientUtil;
import cn.bc.supervalue.outer.vo.SMSNotifyResponse;

public class SendMsg {
	
	private static String sendSmsUrl = "xxx";
	private static String userName = "xxx";
	private static String userPassWord = "xxx,";
	private static String serviceCode;
	private static String mobile;
	private static String msg;
	private static SMSNotifyResponse res;
	private static String sn="xxx";
	private static String pwd="xxx";
	public static SMSNotifyResponse sendSms() throws Exception {
		SMSNotifyResponse smsNotifyResponse = new SMSNotifyResponse();
		
		String sRet = "";
		NameValuePair[] data = { new NameValuePair("account", userName),
				new NameValuePair("pswd", userPassWord),
				new NameValuePair("mobile", mobile),
				new NameValuePair("msg", msg),
				new NameValuePair("needstatus", "true"),
				new NameValuePair("product", ""),
				new NameValuePair("extno", serviceCode) };

		sRet = postUrl(data, sendSmsUrl);
		sRet = StringUtils.replace(sRet, "\n", ",");
		String[] ary = StringUtils.split(sRet, ",");
		if(ary.length>1)
			smsNotifyResponse.setSendResult(ary[1]);
		if(ary.length>2)
			smsNotifyResponse.setMessageId(ary[2]);
		return smsNotifyResponse;
	}

	public static String postUrl(NameValuePair[] data, String postUrl)
			throws Exception {

		String sRet = "";
		Map<String, String> map = null;
		if (data != null) {
			map = new HashMap<String, String>();

			for (NameValuePair nameValuePair : data) {
				map.put(nameValuePair.getName(), nameValuePair.getValue());
			}
		}
		sRet = HttpClientUtil.post(postUrl, map);

		return sRet;
	}
	
	public static SMSNotifyResponse sendMsg(String url){
		SMSNotifyResponse re = new SMSNotifyResponse();
		try {
			Client client = new Client(sn,pwd);
			if(url!=null&&!"".equals(url)){
				client.setServiceURL(url);
			}
			//短信发送
			String message = msg.trim()+"【这儿有卡】";//短信内容加上签名,短信显示----【这儿有卡】：xxxx
	        String   content   =   java.net.URLEncoder.encode(message,  "utf-8");  
	        String result_mt = client.mdgxsend(mobile, content, "", "", "", "");
			re.setSendResult("0");
			re.setMessageId(result_mt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	
	//发送短信
	public static SMSNotifyResponse send(){
		Callable<String> send = new Callable<String>() {  
			public String call() throws Exception { 
				try {
					res = sendMsg("");
	        	} catch (Exception e) {
	     			e.printStackTrace();
	     		}
	            return "线程执行完成.";
	         }
		};
		Callable<String> sendTimeOut = new Callable<String>() {  
			public String call() throws Exception { 
				try {
					res = sendMsg("http://sdk2.entinfo.cn:8061/webservice.asmx");
	        	} catch (Exception e) {
	     			e.printStackTrace();
	     		}
	            return "线程执行完成.";
	         }
		};
		Callable<String> call = new Callable<String>() {  
			public String call() throws Exception { 
				try {
					res = sendSms();
	        	} catch (Exception e) {
	     			e.printStackTrace();
	     		}
	            return "线程执行完成.";
	         }
		};
		
		final ExecutorService exec = Executors.newFixedThreadPool(1);  
		Future<String> future;
	    future = exec.submit(call);  
	    try {  
	    	//调用漫道短信接口 http://sdk.entinfo.cn:8061/webservice.asmx?wsdl
	    	String obj = future.get(1000 * 1, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒  
	        System.out.println("任务成功返回1:" + obj);  
	    } catch (Exception ex) {  
	        System.out.println("处理失败第一次....");  
	        // 关闭线程池  
	        future.cancel(true);
		    exec.shutdown();
		    final ExecutorService execTimeOut = Executors.newFixedThreadPool(1);  
		    Future<String> futureTimeOut;
		    futureTimeOut = execTimeOut.submit(send);  
	        try {
	        	//调用漫道短信接口 http://sdk2.entinfo.cn:8061/webservice.asmx?wsdl
	        	String obj = futureTimeOut.get(1000 * 1, TimeUnit.MILLISECONDS);
				System.out.println("任务成功返回2:" + obj); 
	         } catch (Exception e) {
				System.out.println("处理超时第二次....");
				futureTimeOut.cancel(true);
				execTimeOut.shutdown();
			    final ExecutorService execTimeOutThree = Executors.newFixedThreadPool(1);  
			    Future<String> futureTimeOutThree;
			    futureTimeOutThree = execTimeOutThree.submit(sendTimeOut);  
			    try {
			    	//调用其他短信接口  http://222.73.117.156/msg/HttpBatchSendSM
		        	String obj = futureTimeOutThree.get(1000 * 1, TimeUnit.MILLISECONDS);
					System.out.println("任务成功返回:" + obj); 
	         	}catch(Exception es){
	         		futureTimeOutThree.cancel(true);
	 			    execTimeOutThree.shutdown();
	         		System.out.println("处理超时第san次....");  
			    	es.printStackTrace();
			    }
				e.printStackTrace();
	         } 
	     } 
		return res;
	}
	
	
	public static void setUrl(String url){
		sendSmsUrl = url;
	}
	
	public static String getUrl(){
		return sendSmsUrl;
	}

	public static void setMobile(String mobile) {
		SendMsg.mobile = mobile;
	}

	public static void setMsg(String msg) {
		SendMsg.msg = msg;
	}

	public static SMSNotifyResponse getRes() {
		return res;
	}

	public static void setRes(SMSNotifyResponse res) {
		SendMsg.res = res;
	}
	
}
