//package com.letvyidao.inter;
//
//import java.util.List;
//
//import org.apache.http.message.BasicNameValuePair;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.letvyidao.BaseRequest;
//
//public class DangerControl extends BaseRequest{
//	/**
//	 * 下单：下单前风控订单检查
//	 */
//	@Test
//	public void fk_preOrder(){
//		List<BasicNameValuePair> list = readFileUtils.getParameterFromFileAsList("checkpreorder.txt");
//	   Object rs = doSendPost("http://172.17.0.175:80/order.php", list);
//	   System.out.println(rs.toString());
//		 if(rs.toString().matches(".*code\":200,.*")){
//			 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//	}
//	/*
//	 * 下单：下单前风控账号检查；
//	 */
//	@Test
//	public void fk_checkAccount(){
//		Object rs = doSendGet("http://10.0.11.41:8868/order.php?user_id=13011409&method=checkAccount");	
//	    System.out.println(rs.toString());
//		 if(rs.toString().matches(".*ret_code\":200,.*")){
//			 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//	}
//	
//	/*
//	 * 测试派单拦截接口
//	 */
//	@Test
//	public void fk_dispatch(){
//		List<BasicNameValuePair> list = readFileUtils.getParameterFromFileAsList("dispatch_filter.txt");	
//		Object rs = doSendPost("http://10.0.11.41:8868/dispatch.php", list);
//		if(rs!=null&&rs.toString().matches("(\\{|\\[).*(\\}|\\])")){
//			 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//	}
//	/*
//	 * 充返
//	 */
//	@Test
//	public void fk_isJailbreak(){
//		List<BasicNameValuePair> list = readFileUtils.getParameterFromFileAsList("isJailbreak.txt");	
//		Object rs = doSendPost("http://10.0.11.41:8868/rebate.php", list);
//		 System.out.println(rs.toString());
//		 if(rs.toString().equals("false")){
//			 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//	}
//	
//	@Test
//	public void fk_hasPriviledge(){
//		
//	}
//	/*
//	 * 检查用户状态接口
//	 */
//	@Test
//	public void fk_checkStatus(){
//		List<BasicNameValuePair> list = readFileUtils.getParameterFromFileAsList("checkstatus.txt");	
//		Object rs = doSendPost("http://10.0.11.41:8868/passenger.php?user_id=13011409&db_only=0", list);	
//		System.out.println(rs.toString());	
//		 if(rs.toString().matches(".*ret_code\":200,.*")){
//			 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//	}
//}
