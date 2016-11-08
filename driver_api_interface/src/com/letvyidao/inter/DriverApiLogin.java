package com.letvyidao.inter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.letvyidao.init.Constant;
import com.letvyidao.init.DriverAPIBase;
import com.letvyidao.utils.DriverFactory;
import com.letvyidao.utils.DriverInfo;
import com.letvyidao.utils.HttpUtils;

import net.sf.json.JSONObject;

public class DriverApiLogin extends DriverAPIBase {
private  static Logger logger = LoggerFactory.getLogger(DriverApiLogin.class); 
	@BeforeClass
	public void initData(){
		 headerParams.remove("oauth_token");
		 headerParams.remove("oauth_token_secret");
	}
	@Test(description="/Driver/VerifyCooperaStatus Oauth/AccessToken获取Token 流程是否正确")
	public void testV15VerifyCooperaStatusAndAccessToken(){
		String url = constant.appdriver_VerifyCooperaStatus+"?";
		HashMap<String, String> prameters = new HashMap<String, String>();
		prameters.put("vehicle_number", driver.vehicle_number);
		prameters.put("is_gzip", "1");
		prameters.put("cellphone", driver.cellPhone);
		prameters.put("area_code", "86");
		prameters.put("version", "93");
		prameters.put("imei", driver.imei);
		prameters.put("x_auth_mode", "client_auth");
		url +=interfaceUtils.doGetParamsStr(prameters);
		Object rs  = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
		System.out.println("VerifyCooperaStatus:"+rs.toString());
		logger.info("VerifyCooperaStatus:"+rs.toString());
		jsonObj = JSONObject.fromString(rs.toString());
		 Assert.assertEquals(jsonObj.getInt("code"), 200,"/Driver/VerifyCooperaStatus接口返回值非200，接口返回值："+rs.toString());
		String password = driverUtils.getERPLoginPassord("16868680994");
		System.out.println("mypassword:"+password);
		url = constant.appdriver_accessToken;
		prameters.put("imei", driver.imei);
		prameters.put("x_auth_username", driver.cellPhone);
		prameters.put("x_auth_password", password);	
		rs =HttpUtils.getIntance().doSendPostOAuth(url, prameters,headerParams);
		logger.info("accessToken:"+rs.toString());
	    jsonObj = JSONObject.fromString(rs.toString());	   
	    Assert.assertEquals(jsonObj.getInt("code"), 200,"/Oauth/AccessToken接口报错，接口返回值："+rs.toString());
	    String oauth_token = jsonObj.getJSONObject("msg").getString("oauth_token");
	    Assert.assertTrue(StringUtils.isNotEmpty(oauth_token),"/Oauth/AccessToken接口报错，接口返回值："+rs.toString());
	}
	
	@Test(description="/V6/CarMaster/CreateAuthCode 获取验证码 /V6/CarMaster/Login 登录流程是否正确 /Driver/Unbind 司机设备解绑接口")
	public void testV6CarMasterCreateAuthCodeAndLogin(){
		DriverFactory driverFactory = new DriverFactory();
//		DriverInfo driver = driverFactory.getOffLineDriverWithImei();
		DriverInfo driver = driverFactory.getOffLineDriverWithImei("f65a29c133d63d758e809e534c655963wfonlinemonitor","16811122233","3458");
		constant = new Constant();		
	    String url = Constant.api_CreateAuthCode;
	    Map<String,String> params =  new HashMap<String,String>();	
		params.put("area_code", "86");
		params.put("x_auth_mode", "client_auth");
		params.put("is_gzip", "1");
		params.put("device_type", "1");
		params.put("os_name", "Huawei-HUAWEI C8817E");
		params.put("os_version", "4.4.4");
		params.put("city", "北京市");
		params.put("channel_source", "");
		 params.put("cellphone", driver.cellPhone);
		 params.put("imei", driver.imei);
		 params.put("version", driver.driverAppVersion);		
		 Object rs = HttpUtils.getIntance().doSendPostOAuth(url, params, headerParams);
		 logger.info("/V6/CarMaster/CreateAuthCode:"+rs.toString());
		 jsonObj = JSONObject.fromString(rs.toString());
		 Assert.assertEquals(jsonObj.getInt("code"), 200,"/V6/CarMaster/CreateAuthCode接口报错，接口返回值："+rs.toString());
		 String password = driverUtils.getERPLoginPassord("16811122233");
//		rs = HttpUtils.getIntance().doSendGet("http://doauth2.yongche.com/oauth2/getAuthCode?cellphone=16811122233");
//		 jsonObj = JSONObject.fromString(rs.toString());
//		 String password = jsonObj.getString("result");
		 url = constant.V6Login;
			params.put("client_secret", "9a9c7d97429b8737bf604d0a56aee505");
			params.put("client_id", "car_master");
			params.put("x_auth_password", password);
			params.put("x_auth_username", driver.cellPhone);
			params.put("area_code", "86");
			params.put("imei", driver.imei);
			params.put("version", driver.driverAppVersion);
			params.put("x_auth_mode", "client_auth");
			params.put("is_gzip", "1");
			params.put("device_type", "1");
			params.put("os_name", "Huawei-HUAWEI C8817E");
			params.put("os_version", "4.4.4");
			params.put("access_token", "");
			params.put("city", "北京市");
			params.put("channel_source", "");
			rs =HttpUtils.getIntance().doSendPostOAuth(url, params, headerParams);
			 logger.info("/V6/CarMaster/Login:"+rs.toString());
		    System.out.println(rs.toString());
		    jsonObj = JSONObject.fromString(rs.toString());
		    Assert.assertEquals(jsonObj.getInt("code"), 200,"/V6/CarMaster/Login接口报错，接口返回值："+rs.toString());
		    String oauth_token = jsonObj.getJSONObject("msg").getJSONObject("oauth_token").getString("oauth_token");
		    String oauth_token_secret = jsonObj.getJSONObject("msg").getJSONObject("oauth_token").getString("oauth_token_secret");
		    Assert.assertTrue(StringUtils.isNotEmpty(oauth_token),"/V6/CarMaster/Login接口报错，接口返回值："+rs.toString());	
		    headerParams.put("oauth_token", oauth_token);
			headerParams.put("oauth_token_secret",oauth_token_secret);
//		    url = constant.appdriver_GetDriverAccountDetail+"?";
//			params.put("reason", "10,18");
//			params.put("page_num", "1");
//			url +=interfaceUtils.doGetParamsStr(params);
//			rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);			    
//		    System.out.println("/V2/Driver/GetDriverAccountDetail:"+rs.toString());		    
		    url = constant.appdriver_DriverUnbind;
		    params =  new HashMap<String,String>();	
			params.put("imei",driver.imei);
			params.put("version",driver.driverAppVersion);
			params.put("x_auth_mode","client_auth");
			params.put("device_type", "1");	
			rs = HttpUtils.getIntance().doSendPostOAuth(url, params, headerParams);
			System.out.println(rs.toString());
			jsonObj = JSONObject.fromString(rs.toString());
			Assert.assertEquals(jsonObj.getInt("code"), 200,"/Driver/Unbind 司机设备解绑接口返回码非200，接口返回信息："+rs.toString());
			Assert.assertEquals(jsonObj.getBoolean("msg"), true,"/Driver/Unbind 司机设备解绑接口返回码非200，接口返回信息："+rs.toString());
	}
}
