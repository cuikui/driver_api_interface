package com.letvyidao.inter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.letvyidao.init.BaseRequest;
import com.letvyidao.utils.HttpUtils;
import com.letvyidao.utils.InterfaceUtils;
import com.letvyidao.utils.PSFClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Listeners({ com.letvyidao.listener.RetryListener.class})
public class Order extends BaseRequest{
private  static Logger logger = LoggerFactory.getLogger(Order.class); 

@Test(description="全流程监控，监控流程：创建非用户决策订单（createOrder)-->司机抢单(/Dispatch/driverResponse)-->获取订单状态(45s内每3s循环调用一次order/getStatus)-->取消订单(state/cancel)")
public void testOrder_qlcNoCustomDecision(){
	String orderId = "";
	String errorMsg = "";
	try{
       //测试环境, uids需要找一个用户端的数据和手机号
//		String [] uids = {"13025137","13025135","13025134"};
//		String [] phones={"16809340982","16820161007","16899000094"};
//线上环境，uid和phones
      String [] uids = {"43276378","43277106","43277118"};
      String [] phones={"16871051234","16871051235","16871051236"};
		JSONArray car_list = null;
	     boolean acceptRS = false;
	     int carNumbr = 0;
		for(int i=0;i<1;i++){
			HttpUtils.getIntance().request.data="";
			HttpUtils.getIntance().request.service_uri = "state/createOrder?user_id="+uids[i]+"&corporate_id=0&passenger_phone="+phones[i]+"&passenger_name=testpassager&passenger_number=1&city=hlbe&product_type_id=1&fixed_product_id=0&car_type_id=3&car_type_ids=3&source=20000001&expect_start_time="+(System.currentTimeMillis()/1000+600)+"&in_coord_type=baidu&expect_end_latitude=36.9021&expect_end_longitude=100.1521&expect_start_latitude=36.9022&expect_start_longitude=100.1522&start_position=testaddr&start_address=testaddr&end_position=testaddr&end_address=testaddr&flight_number=0&is_asap=1&app_version=iWeidao/6.2.5 D/877035&media_id=1&sms=passenger&time_span=0&has_custom_decision=0&is_need_manual_dispatch=0&is_auto_dispatch=1&estimate_price=0&device_id=0&corporate_dept_id=0&estimate_price=100.0&estimate_info=D123,T3700&flag=0&create_order_longitude=36.9022&create_order_latitude=36.9022&ip=10.1.7.202&order_port=60428&dispatch_type=2&time_length=1800";
			String response = HttpUtils.getIntance().doPSFRequest("order");
//		    logger.info("createOrder:"+response+"request.uri:"+request.service_uri);
		    jsonObj = JSONObject.fromString(response);		    
		    orderId = jsonObj.getJSONObject("result").getString("service_order_id");		    
		    HttpUtils.getIntance().request.data = "{\"order_id\":"+orderId+"}";
		    String params = "order_id="+orderId;
		    HttpUtils.getIntance().request.service_uri = "order/getStatus?"+params;
			 for(int j=0;j<15;j++){
				 Thread.sleep(3000); 
				  response =HttpUtils.getIntance().doPSFRequest("order");   
//			     logger.info("order/getStatus:"+response+"request.uri:"+request.service_uri);
			     jsonObj = JSONObject.fromString(response);
			     int status=jsonObj.getJSONObject("result").getInt("status");
			     System.out.println(response);
			     if(status==4){
			    	 acceptRS=true;
			    	 break;
			     }			    
			 }	
			 //如果司机抢单成功，则跳出循环，否则则再次创建订单，重复创建订单3次还抢单抢单失败则报警
			 if(acceptRS){
				 break;
			 }else{
				 errorMsg=response;
			 }
	}
		//获取司机端列表
		if(acceptRS){
			String driverId=jsonObj.getJSONObject("result").getString("driver_id");
		 //决策成功，取消订单    不同订单状态的含义???
			HttpUtils.getIntance().request.data ="";
		     String param="order_id="+orderId+"&return_min=0"+driverId+"&reason_id=1&extension=testcancel&user_confirmed";
		     HttpUtils.getIntance().request.service_uri = "state/cancel?"+param;	
		     String response = HttpUtils.getIntance().doPSFRequest("order");
//		     logger.info("state/cancel:"+response+"request.uri:"+request.service_uri);
		     System.out.println("state/cancel:"+response);
		     jsonObj = JSONObject.fromString(response);
		     Assert.assertEquals(jsonObj.getInt("ret_code"), 200,"取消订单接口失败,接口返回信息:"+response+" 订单id:"+orderId);
		}
		Assert.assertTrue(acceptRS,"order/getStatus获取派单司机列表接口错误，错误信息："+errorMsg+" 创建订单id:"+orderId);
	}catch(Exception e){
		e.printStackTrace();
		Assert.assertTrue(false,"方法异常，异常信息"+e.getMessage());
	}
	
}
@Test(description="创建订单到乘客端获取司机端列表接口功能是否正确")
public void testOrder_qlcCancel(){
	String orderId = "";
	String errorMsg = "";
	try{
       //测试环境, uids需要找一个用户端的数据和手机号，因为害怕和我们的账号冲突，所以就不给你了
		String [] uids = {"13025137","13025135","13025134"};
		String [] phones={"16809340982","16820161007","16899000094"};
//线上环境16871051234（43276378）、16871051235（43277106）、16871051236（43277118）
//		String [] uids = {"43276378","43277106","43277118"};
//		String [] phones={"16871051234","16871051235","16871051236"};
		JSONArray car_list = null;
	     boolean acceptRS = false;
	     int carNumbr = 0;
		for(int i=0;i<1;i++){
			HttpUtils.getIntance().request.data="";
			HttpUtils.getIntance().request.service_uri = "state/createOrder?user_id="+uids[i]+"&corporate_id=0&passenger_phone="+phones[i]+"&passenger_name=testpassager&passenger_number=1&city=hlbe&product_type_id=1&fixed_product_id=0&car_type_id=3&car_type_ids=3&source=20000001&expect_start_time="+(System.currentTimeMillis()/1000+600)+"&in_coord_type=baidu&expect_end_latitude=36.9021&expect_end_longitude=100.1521&expect_start_latitude=36.9022&expect_start_longitude=100.1522&start_position=testaddr&start_address=testaddr&end_position=testaddr&end_address=testaddr&flight_number=0&is_asap=1&app_version=iWeidao/6.2.5 D/877035&media_id=1&sms=passenger&time_span=0&has_custom_decision=1&is_need_manual_dispatch=0&is_auto_dispatch=1&estimate_price=0&device_id=0&corporate_dept_id=0&estimate_price=100.0&estimate_info=D123,T3700&flag=2&create_order_longitude=36.9022&create_order_latitude=36.9022&ip=10.1.7.202&order_port=60428&dispatch_type=2&time_length=1800";
			String response = HttpUtils.getIntance().doPSFRequest("order");
		    System.out.println("createOrder:"+response+"request.uri:"+HttpUtils.getIntance().request.service_uri);
		    jsonObj = JSONObject.fromString(response);		    
		    orderId = jsonObj.getJSONObject("result").getString("service_order_id");		    
		    HttpUtils.getIntance().request.data = "";
		    String params = "order_id="+orderId+"&out_coord_type=baidu&filter_driver_ids=0&count=5";
		    HttpUtils.getIntance().request.service_uri = "Dispatch/getAcceptCars?"+params;
			 for(int j=0;j<10;j++){
				 Thread.sleep(3000); 
				  response = HttpUtils.getIntance().doPSFRequest("dispatch");   
                  System.out.println("Dispatch/getAcceptCars:"+response+"request.uri:"+HttpUtils.getIntance().request.service_uri);
			     jsonObj = JSONObject.fromString(response);
			     if(jsonObj.getInt("ret_code")==498){
			    	 errorMsg=response ;
			    	 System.out.println("Dispatch/getAcceptCars: 498  获取司机端列表失败， response:"+response+"----service_order_id:"+orderId);
			    	 break;
			     }else if(jsonObj.getInt("ret_code")==200){
			    	 carNumbr = jsonObj.getJSONArray("car_list").length();
			    	 System.out.println("[testOrder_qlcCancel]  info 获取司机端列表成功，response:"+response+"----service_order_id:"+orderId);
			    	 System.out.println(" info 获取司机端列表成功，response:"+response+"----service_order_id:"+orderId);
			    	 if(carNumbr>0){
			    		 car_list = jsonObj.getJSONArray("car_list");
				    	 acceptRS=true;
				    	 break;	 
			    	 }
			     }
			 }	
			 //如果司机抢单成功，则跳出循环，否则则再次创建订单，重复创建订单3次还抢单抢单失败则报警
			 if(acceptRS){
				 break;
			 }
	}
		//获取司机端列表
		if(acceptRS){			
			 String car_id = car_list.getJSONObject(0).getString("car_id");
			 String driverId=car_list.getJSONObject(0).getString("driver_id");
			 HttpUtils.getIntance().request.data = "";
			String param="service_order_id="+orderId+"&driver_id="+driverId+"&coupon_member_id=0&third_party_coupon=0";		  
			HttpUtils.getIntance().request.service_uri = "Dispatch/userDecision?"+param;	
		     String response = HttpUtils.getIntance().doPSFRequest("dispatch");
//		     logger.info("Dispatch/userDecision:"+response+"request.uri:"+request.service_uri);
		     jsonObj = JSONObject.fromString(response);
		     Assert.assertEquals(jsonObj.getInt("ret_code"), 200,"用户决策接口失败,接口返回信息："+response+" 订单id:"+orderId);
		 //决策成功，取消订单    不同订单状态的含义???
		     HttpUtils.getIntance().request.data ="";
		     param="order_id="+orderId+"&return_min=0"+driverId+"&reason_id=1&extension=testcancel&user_confirmed";
		     HttpUtils.getIntance().request.service_uri = "state/cancel?"+param;	
		     response = HttpUtils.getIntance().doPSFRequest("order");
//		     logger.info("state/cancel:"+response+"request.uri:"+request.service_uri);
		     jsonObj = JSONObject.fromString(response);
		     Assert.assertEquals(jsonObj.getInt("ret_code"), 200,"取消订单接口失败,接口返回信息:"+response+" 订单id:"+orderId);
		}
		Assert.assertTrue(acceptRS,"派单流程失败，错误信息："+errorMsg+" 创建订单id:"+orderId);
	}catch(Exception e){
		e.printStackTrace();
		Assert.assertTrue(false,"方法异常，异常信息"+e.getMessage());
	}
	
}
@Test(description="司机到达,服务开始,订单结束,确认账单,当面付妥收确认" )
public void testOrder_qlcPay() throws Exception{
	InterfaceUtils utils = new InterfaceUtils();
	Map<String,String> orderInfos = utils.createAnOrder();
	if(orderInfos==null){
		Assert.assertTrue(false,"创建有效订单失败，请查询原因");
	}
//	Map<String,String> orderInfos = new HashMap<>();
//	orderInfos.put("order_id", "6339741884593446108");
//	orderInfos.put("car_id", "1");
//	orderInfos.put("startTime", (System.currentTimeMillis()/1000+600)+"");
	String orderId = orderInfos.get("order_id");
	String car_id = orderInfos.get("car_id");
	String startTime = orderInfos.get("startTime");
	int endTime=0;
	Map<String,String> orderParams = readFileUtils.getParameterFromFileAsMap("interfaceSummary");
	for (String parameterList : orderParams.get("Parameter_file_name").split(",")) {
			String param = orderParams.get(parameterList);
			if(parameterList.equals("driveArrived")){
				param = param.replace("arrive_time=1465181999", "arrive_time="+startTime);
			}else if(parameterList.equals("startTiming")){
				param = param.replace("start_time=1465181999", "start_time="+startTime);	
			}else if(parameterList.equals("endTiming")){
				 endTime = Integer.parseInt(startTime)+600;
				 param = param.replace("end_time=1465181999", "end_time="+endTime);	
			}else if (parameterList.equals("confirmCharge")){
				param = param.replace("start_time=1465181999", "start_time="+startTime).replace("end_time=1465185555", "end_time="+endTime);	
			}
			System.out.println(parameterList+"----param:"+param);
			PSFClient.PSFRPCRequestData request = new PSFClient.PSFRPCRequestData();
			request.data ="";
			if(!parameterList.equals("confirmCharge")&&!parameterList.equals("receiveCash")){
				request.service_uri = param+"&order_id="+orderId+"&car_id="+car_id;
			}else{
				request.service_uri = param+"&order_id="+orderId;
			}
			String response = HttpUtils.getIntance().doPSFRequest("order");
			JSONObject jsonObject = JSONObject.fromString(response);
			System.out.println(parameterList+"---"+response);	
			if(parameterList.equals("confirmCharge")){
				 Assert.assertEquals(jsonObject.getInt("ret_code"), 499);
			}else{
				 Assert.assertEquals(jsonObject.getInt("ret_code"), 200);
			}
		}
	}

@Test(description="派单回调接口")
public void testOrder_DispatchInterface() throws Exception{	
	   Map<String,String> orderParams = readFileUtils.getParameterFromFileAsMap("dispatchInterface.txt");
	   orderParams.put("service_order_id", "6332683664401483003");
	   HttpUtils.getIntance().request.data = JSONObject.fromMap(orderParams).toString();
	   HttpUtils.getIntance().request.service_uri = "state/DispatchInterface";
	   String response =HttpUtils.getIntance().doPSFRequest("order");
	   JSONObject jsonObject = JSONObject.fromString(response);
	   System.out.println(response);		
	   Assert.assertEquals(jsonObject.getInt("ret_code"), 200);
}

@Test(description="通过订单ID获取指定的字段并返回")
public void testOrder_fetchOrderById() throws Exception{	
	String order_id = "6331580291384900589";
	HttpUtils.getIntance().request.data = "{\"out_coord_type\":\"baidu\",\"order_id\":"+order_id+"}";
	HttpUtils.getIntance().request.service_uri = "order/fetchOrderById?out_coord_type=baidu&order_id="+order_id;
	   String response = HttpUtils.getIntance().doPSFRequest("order");
	   String substring = response.substring(1, response.length()-1);
	   JSONObject jsonObject = JSONObject.fromString(substring);
	   System.out.println(response);		
	   Assert.assertEquals(jsonObject.getString("service_order_id"), order_id,"接口返回内容:"+response);
}

@Test(description="获取订单卡片信息")
public void testOrder_getServiceOrderCart() throws Exception{	
	String order_id = "6331580291384900589";
	Map<String,String> orderParams = readFileUtils.getParameterFromFileAsMap("getServiceOrderCart.txt");
	HttpUtils.getIntance().request.data = JSONObject.fromMap(orderParams).toString();
	HttpUtils.getIntance().request.service_uri = "order/getServiceOrderCart?service_order_cart_id=6291941624972837060&fields=corporate_id,user_id,service_order_id,city,product_type_id,car_type_id,expect_start_time&force_master=0";
	String response = HttpUtils.getIntance().doPSFRequest("order");
	JSONObject jsonObject = JSONObject.fromString(response);
	System.out.println(response);		
	Assert.assertEquals(jsonObject.getInt("ret_code"), 200);
}
@Test(description="获取付费详情")
public void testOrder_getPayDetails() throws Exception{	
	String order_id = "6331580291384900589";
	HttpUtils.getIntance().request.data = "{\"service_order_id\":"+order_id+"}";
	HttpUtils.getIntance().request.service_uri = "order/getPayDetails?service_order_id="+order_id;
	String response = HttpUtils.getIntance().doPSFRequest("order");
	JSONObject jsonObject = JSONObject.fromString(response);
	System.out.println(response);		
	Assert.assertEquals(jsonObject.getInt("ret_code"), 200);
}
@Test(description="获取历史订单列表")
public void testOrder_getHistoryOrderList() throws Exception{	
	Map<String,String> orderParams = readFileUtils.getParameterFromFileAsMap("getHistoryOrderList.txt");
	HttpUtils.getIntance().request.data = JSONObject.fromMap(orderParams).toString();
	HttpUtils.getIntance().request.service_uri = "order/getHistoryOrderList?user_id=8060&fields=service_order_id,user_id&out_coord_type=baidu&force_master=0";
	String response = HttpUtils.getIntance().doPSFRequest("order");
	JSONObject jsonObject = JSONObject.fromString(response);
	System.out.println(response);		
	Assert.assertEquals(jsonObject.getInt("ret_code"), 200);
}
@Test(description="判断是否为YOP服务")
public void testOrder_isYOPService() throws Exception{	
	int source =5000000;
	HttpUtils.getIntance().request.data = "{\"source\":"+source+"}";
	HttpUtils.getIntance().request.service_uri = "order/isYOPService?source="+source;
	String response = HttpUtils.getIntance().doPSFRequest("order");
	JSONObject jsonObject = JSONObject.fromString(response);
	System.out.println(response);		
	Assert.assertEquals(jsonObject.getInt("ret_code"), 200);
}
@Test(description="是否允许手动派单")
public void testOrder_allowManualDispatch() throws Exception{	
	String order_id = "6331580291384900589";
	HttpUtils.getIntance().request.data = "{\"order_id\":"+order_id+"}";
	HttpUtils.getIntance().request.service_uri = "order/allowManualDispatch?order_id="+order_id;
	String response = HttpUtils.getIntance().doPSFRequest("order");
	JSONObject jsonObject = JSONObject.fromString(response);
	System.out.println(response);		
	Assert.assertEquals(jsonObject.getInt("ret_code"), 200);
}
@Test(description="风控查询账户")
public void testOrder_accountCheck() throws Exception{	
	int user_id = 8060;
	HttpUtils.getIntance().request.data = "{\"user_id\":"+user_id+"}";
	HttpUtils.getIntance().request.service_uri = "order/accountCheck?user_id="+user_id;
	String response = HttpUtils.getIntance().doPSFRequest("order");
	JSONObject jsonObject = JSONObject.fromString(response);
	System.out.println(response);		
	Assert.assertEquals(jsonObject.getInt("ret_code"), 200);
}
}
