package com.letvyidao.inter;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.letvyidao.utils.HttpUtils;
import com.letvyidao.utils.PSFClient;
import com.letvyidao.utils.ReadFileUtils;

import net.sf.json.JSONObject;

public class DispatchSeries {
	public ReadFileUtils readFileUtils = null;
	public JSONObject jsonObject = null;
	private String bidding_id = null;
	
	@BeforeClass
	public void beforeClass()throws Exception{
		readFileUtils = new ReadFileUtils();
	}
	
	
	@Test(description="获取车牌是否限行")
	public void dispatchIsVehicleNumberProhibit() throws Exception{
		   HttpUtils.getIntance().request.data = "{\"city\":'bj',\"carNumber\":\"京N88008\"}";
		   HttpUtils.getIntance().request.service_uri = "Dispatch/isVehicleNumberProhibit?city=bj&carNumber=京N88008";
		   String response = HttpUtils.getIntance().doPSFRequest("dispatch");
		   JSONObject jsonObject = JSONObject.fromString(response);
		   System.out.println(response);		
  	       Assert.assertEquals(jsonObject.getInt("ret_code"), 200,response+"---"+"参数如下："+HttpUtils.getIntance().request.service_uri);
	}
	@Test(description="获取司机指派率")
	public void dispatchGetCurrentAcceptRate() throws Exception{
		 HttpUtils.getIntance().request.data = "{\"driverId\":50058151}";
		 HttpUtils.getIntance().request.service_uri = "Dispatch/getCurrentAcceptRate?driver_id=50058151";
		   String response = HttpUtils.getIntance().doPSFRequest("dispatch");
		   JSONObject jsonObject = JSONObject.fromString(response);
		   System.out.println(response);		
		   Assert.assertEquals(jsonObject.getInt("ret_code"), 200,response+"---"+"参数如下："+ HttpUtils.getIntance().request.service_uri);
	}

	@Test(description="获取接单司机数量")
	public void dispatchGetAcceptDriverCount() throws Exception{
		 HttpUtils.getIntance().request.data = "{\"service_order_id\":6331192421478333199}";
		 HttpUtils.getIntance().request.service_uri = "Dispatch/getAcceptDriverCount?service_order_id=6331192421478333199";
	   String response =  HttpUtils.getIntance().doPSFRequest("dispatch");
	   JSONObject jsonObject = JSONObject.fromString(response);
	   System.out.println(response);		
	   Assert.assertEquals(jsonObject.getInt("ret_code"), 200,response+"---"+"参数如下："+HttpUtils.getIntance().request.service_uri);
	}
	
	@Test(description="取消司机日程接口")
	public void dispatchCancelDriverCalendar() throws Exception{
		long time=System.currentTimeMillis();
		 HttpUtils.getIntance().request.data = "{\"driver_id\":50058150,\"service_order_id\":6331192421478333199,\"start_time\":"+(time+1000)+",\"time_length\":3600}";
		 HttpUtils.getIntance().request.service_uri = "Dispatch/cancelDriverCalendar?driver_id=50058150";
	   String response =  HttpUtils.getIntance().doPSFRequest("dispatch");
	   JSONObject jsonObject = JSONObject.fromString(response);
	   System.out.println(response);		
	   Assert.assertEquals(jsonObject.getInt("ret_code"), 200,response+"---"+"参数如下："+ HttpUtils.getIntance().request.service_uri);
	}
	@Test(description="修改司机日程")
	public void dispatchUpdateDriverCalendar() throws Exception{	
		   long time = System.currentTimeMillis();
		   HttpUtils.getIntance().request.data = "{\"driver_id\":50058150,\"start_time\":"+(time+3600)+",\"service_order_id\":6331192421478333199,\"time_length\":3600}";
		   HttpUtils.getIntance().request.service_uri = "Dispatch/updateDriverCalendar?driver_id=50058150";
		   String response = HttpUtils.getIntance().doPSFRequest("dispatch");
		   JSONObject jsonObject = JSONObject.fromString(response);
		   System.out.println(response);		
		   Assert.assertEquals(jsonObject.getInt("ret_code"), 200,response+"---"+"参数如下："+HttpUtils.getIntance().request.service_uri);
	}
	
	@Test(description="接受加价")
	public void dispatchConfirmBidding() throws Exception{		
		String response = new String();
		HttpUtils.getIntance().request.data = "{\"service_order_id\"=6331192421478333199,\"bidding_id\":6332244364010005057}";
		HttpUtils.getIntance().request.service_uri = "/Dispatch/confirmBidding?service_order_id=6331192421478333199&bidding_id=6332244364010005057";
	    response =HttpUtils.getIntance().doPSFRequest("dispatch");
	    System.out.println(response.toString());
	    JSONObject jsonObject = JSONObject.fromString(response);
	    Assert.assertEquals(jsonObject.getInt("ret_code"), 400,response+"---"+"参数如下："+HttpUtils.getIntance().request.service_uri);
	}

	
}
