package com.letvyidao.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.letvyidao.utils.AlarmRecordUtil;
import com.letvyidao.utils.HttpUtils;
import com.letvyidao.utils.MailTest;
import com.letvyidao.utils.PSFClient;

public class TestRetryAnalyzer implements IRetryAnalyzer {
	private  static Logger logger = LoggerFactory.getLogger(TestRetryAnalyzer.class); 
    private static MailTest sendMail = new MailTest(true);
    private String content = "易道监控报警邮件   接口：";
	private int currentTry = 0;
	private int m_maxRetries=0;
	public  int failTimes=0;
	@Override
    public synchronized boolean retry(ITestResult result) {
		String maxRetriesStr = result.getTestContext().getSuite().getParameter("maxRetries"); 
		m_maxRetries = Integer.parseInt(maxRetriesStr);
	    try {
  		System.out.println(result.getMethod().getMethodName()+"-----"+result.getName());
  		 if ( result.isSuccess() ){
  			 AlarmRecordUtil.setAlarmRecordProperties(result.getName(),0);
  			 return false;
  	        }else{  	        
  	         failTimes=AlarmRecordUtil.getAlarmRecordProperties(result.getName());
  	         System.out.println("----failTime:"+failTimes);
  	         logger.info("----failTime:"+failTimes);
  	     	 AlarmRecordUtil.setAlarmRecordProperties(result.getName(), failTimes+1);
			 if(failTimes==0){
				 currentTry =0;
			 }
              if( failTimes>1&& failTimes<7){   
            	  System.out.println("error and message");
             	  sendsms(result);
                  return false;
              }
              if(failTimes>=7){
            	  System.out.println("error but on message");
            	  return false;
              }
        	  if ( currentTry < m_maxRetries){
	               ++currentTry;	
	               Thread.sleep(10000);
	              System.out.println(" FAILED, " + "Retrying " + currentTry + " time");
	              return true;
	          }
  	        }    
        }catch(Exception e){
        	e.printStackTrace();
        }
	       System.out.println("i am the end");
	        return false;	
	}
private void sendsms(ITestResult result){
	try{
		Throwable throwable = result.getThrowable();
		  String methodName = "报错方法："+result.getTestClass().getName()+"."+result.getName();
		  String message =methodName+".用例监控接口："+result.getMethod().getDescription()+"  接口返回内容及调用信息："+ throwable.getMessage().replaceAll("\"", "'");
		  System.out.println("message:"+message);
		  String [] phones = {"13671126358"};
		//String [] phones = {"13671126358","15910470744","13910879160","15101537885","13699253876","15652200316","13811990045"};  
		  HttpUtils.getIntance().request.service_uri="event/sendWarning";
		   for(int i=0;i<phones.length;i++){
			   HttpUtils.getIntance().request.data="{\"CELLPHONE\": \""+phones[i]+"\",\"CONTENT\": \""+(content+message)+"\",\"FLAG\": 14,\"__NO_ASSEMBLE\": 1,\"__EVENT_ID__\": 46}";
		  String response = HttpUtils.getIntance().doPSFRequest("atm2");
		  System.out.println("event/sendWarning:"+response);
		   }
		  sendMail.doSendHtmlEmail("易到接口解控报警邮件", message, "wangfang6@le.com", null);
	}catch(Exception e){
		e.printStackTrace();
	} 
//sendMail.doSendHtmlEmail("易到接口解控报警邮件", message, "wangfang6@le.com,wb-weilingjie@letv.com,liaozaixue@yongche.com,jiangyang1@yongche.com,gaowenrui@yongche.com,zhangfan1@yongche.com,huangyang@yongche.com,zhenganquan@yongche.com", null);
}
}