package com.letvyidao.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import com.letvyidao.init.Constant;

import net.sf.json.JSONObject;

public class Driver_APIUtils {
public DriverFactory driverFactory = null;
public DriverInfo driver = null;
public   Map<String,String>headerParams =null;
private Constant constant = null;
private JSONObject jsonObj = null;
public InterfaceUtils interfaceUtils;

    public Driver_APIUtils() {
		driverFactory = new DriverFactory();
		driver = driverFactory.getOffLineDriverWithImei();
		constant = new Constant();			
		interfaceUtils = new InterfaceUtils();
		try{
			headerParams = getRequestHeader(5, true);		
		}catch(Exception e){
			e.printStackTrace();
		}		
}

public String getERPLoginPassord(String cellphone){
	Map<String,String> mapParams = new HashMap<>();
	mapParams.put("app_id", "3");
	mapParams.put("cn", "E");
	mapParams.put("done", "https://platform.yongche.com/");
	mapParams.put("login", "wangbingwei");
	mapParams.put("password", "Password01!");
     Object rs =HttpUtils.getIntance().doSendPost("https://sso.yongche.com/auth/login", mapParams);
	System.out.println("/auth/login:"+rs.toString());
	Header [] headers = HttpUtils.getIntance().headers;
	Map<String,String> erpheaderParams = new HashMap<>();
	String cookie ="";
	String cookie1="";
	String cookie2="";
	for(int i=0;i<headers.length;i++){
		if(headers[i].getValue().matches("B=.*")){				
			cookie1=headers[i].getValue().split(";")[0]+";";
		}else if(headers[i].getValue().matches("E_3=.*")){
			cookie2=headers[i].getValue().split(";")[0];
		}
	}
	cookie=cookie1+cookie2;
	erpheaderParams.put("Upgrade-Insecure-Requests", "1");
	System.out.println("cookie:"+cookie);
	erpheaderParams.put("Cookie", cookie);
	rs = HttpUtils.getIntance().doSendGet("https://platform.yongche.com/driver/test?p="+cellphone, erpheaderParams);
	String password = rs.toString();
	System.out.println("driver/test:"+rs.toString());
	int start = password.indexOf(":");
	int endindex = password.indexOf("imei");
	if(endindex==-1){
		password = password.split(":")[1].trim();
	}else{
		password = password.substring(start+1, endindex).trim();
	}
	System.out.println("password:"+password);
    return password;
}

public void testGetItemOrder(){
	String url = constant.appdriver_OrderGetItemOrder+"?";
	Map<String,String> params =  new HashMap<String,String>();	
	params.put("order_id", "2005817779");
	params.put("out_coord_type", "baidu");
	params.put("version", driver.driverAppVersion);
	params.put("imei", driver.imei);
	params.put("version", driver.driverAppVersion);
	params.put("x_auth_mode", "client_auth");
	params.put("is_gzip", "1");
	url += interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);	
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
//	Assert.assertTrue((jsonObj.getInt("code")==200),"/V5/Driver/GetIndex接口返回码非200，接口返回信息："+rs.toString());
//	Assert.assertTrue(jsonObj.getJSONObject("msg").getString("today_income").matches("[0-9]*"),"/V5/Driver/GetIndex接口报错，接口返回信息："+rs.toString());
}
private Map<String,String> getV15Token()throws Exception {	
	String url = constant.appdriver_VerifyCooperaStatus+"?";
	 HashMap<String, String> prameters = new HashMap<String, String>();
	prameters.put("vehicle_number", driver.vehicle_number);
	prameters.put("is_gzip", "1");
	prameters.put("cellphone", driver.cellPhone);
	prameters.put("area_code", "86");
	prameters.put("version", "93");
	prameters.put("imei", driver.imei);
	prameters.put("x_auth_mode", "client_auth");
	System.out.println(interfaceUtils==null);
	System.out.println(prameters==null);
	url +=interfaceUtils.doGetParamsStr(prameters);
	Object rs  = HttpUtils.getIntance().doSendGet(url);
	System.out.println("VerifyCooperaStatus:"+rs.toString());
	jsonObj = JSONObject.fromString(rs.toString());
	String password = jsonObj.getJSONObject("msg").getString("password");
	url = constant.appdriver_accessToken;
	prameters.put("imei", driver.imei);
	prameters.put("x_auth_username", driver.cellPhone);
	prameters.put("x_auth_password", password);	
	Map<String,String> withOutTokenMap = getRequestHeaderWithOutToken();
	rs =HttpUtils.getIntance().doSendPostOAuth(url, prameters, withOutTokenMap);
	System.out.println("accessToken:"+rs.toString());
    jsonObj = JSONObject.fromString(rs.toString());
    Map<String,String> tokenMap = new HashMap<String,String>();	 		    
    String oauth_token = jsonObj.getJSONObject("msg").getString("oauth_token");
    String oauth_token_secret = jsonObj.getJSONObject("msg").getString("oauth_token_secret");
    tokenMap.put("oauth_token",oauth_token );
    tokenMap.put("oauth_token_secret", oauth_token_secret);
    return tokenMap;
}
private Map<String,String> getV6Token() throws Exception{	  	
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
	headerParams = getRequestHeaderWithOutToken();
	Object rs = HttpUtils.getIntance().doSendPostOAuth(url, params, headerParams);
	System.out.println("/V6/CarMaster/CreateAuthCode:"+rs.toString());
	jsonObj = JSONObject.fromString(rs.toString());
	 String password = getERPLoginPassord("");
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
		System.out.println("/V6/CarMaster/Login:"+rs.toString());
	    jsonObj = JSONObject.fromString(rs.toString());
	    Map<String,String> tokenMap = new HashMap<>();
	    tokenMap.put("oauth_token", jsonObj.getJSONObject("msg").getJSONObject("oauth_token").getString("oauth_token"));
	    tokenMap.put("oauth_token_secret", jsonObj.getJSONObject("msg").getJSONObject("oauth_token").getString("oauth_token_secret"));
	    return tokenMap;	
}
public Map<String,String> getRequestHeaderWithOutToken() throws Exception{
	Map<String,String> headerMap = new HashMap<String,String>();
	headerMap.put("oauth_signature_method", "PLAINTEXT");
	long oauth_timestamp = System.currentTimeMillis() / 1000;
	headerMap.put("oauth_timestamp", String.valueOf(oauth_timestamp));
	headerMap.put("oauth_nonce", String.valueOf(oauth_timestamp+ 1000));
	headerMap.put("oauth_version", "1.0");
	headerMap.put("oauth_signature", "5sARLGoVkNAPhh5wq1Hl95crWIk");
	headerMap.put("oauth_consumer_key", "4821726c1947cdf3eebacade98173939");
	return headerMap;
}
public  Map<String,String> getRequestHeader(int version,boolean hasToken) throws Exception{
	Map<String,String> headerMap = new HashMap<String,String>();
	headerMap.put("oauth_signature_method", "PLAINTEXT");
	long oauth_timestamp = System.currentTimeMillis() / 1000;
	headerMap.put("oauth_timestamp", String.valueOf(oauth_timestamp));
	headerMap.put("oauth_nonce", String.valueOf(oauth_timestamp+ 1000));
	headerMap.put("oauth_version", "1.0");
//	headerMap.put("oauth_signature", "5sARLGoVkNAPhh5wq1Hl95crWIk");
//	headerMap.put("oauth_consumer_key", "2afdd89f5c6dbdc34542ab04933a091004eba18e2");
//	headerMap.put("oauth_token", "727fed71fd48773bc065feefccb77397057ff6791");
//	headerMap.put("oauth_token_secret", "da53c46ad1eafb891420727b056bee05");
//线上司机账号属性
	headerMap.put("oauth_consumer_key", "4821726c1947cdf3eebacade98173939");
	headerMap.put("oauth_signature", "5sARLGoVkNAPhh5wq1Hl95crWIk");
	headerMap.put("oauth_token", "44e32a2812f8fca3ba97b15585e5cc770580d8695");
	headerMap.put("oauth_token_secret", "2936d42df49d5f825375e4a1fc4da418");
	return headerMap;
}
public JSONObject appeal() throws Exception{	
	String url = constant.Api_Post_Appeal;
	HashMap<String, String> appealParams = new HashMap<String, String>();
	appealParams.put("imei", driver.imei);
	appealParams.put("verison", driver.driverAppVersion);
	appealParams.put("appeal_content", "dj");
	appealParams.put("x_auth_mode", "client_auth");
	appealParams.put("appeal_type", "1");
	appealParams.put("is_gzip", "1");	
	Object rs = HttpUtils.getIntance().doSendPostOAuth(url,appealParams , headerParams);
	return JSONObject.fromString(rs.toString());
}
public JSONObject getAppealContent() throws Exception{
	String url = constant.Api_Get_GetAppealContent+"?";
	HashMap<String, String> getAppealParams = new HashMap<String, String>();
	DriverInfo driver = driverFactory.getOffLineDriverWithImei();
	getAppealParams.put("imei", driver.imei);
	getAppealParams.put("version", driver.driverAppVersion);
	getAppealParams.put("x_auth_mode", "client_auth");
	getAppealParams.put("is_gzip", "1");	
    url +=interfaceUtils.doGetParamsStr(getAppealParams);
    System.out.println(url);
	Object rs= HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	System.out.println(rs.toString());
	return JSONObject.fromString(rs.toString());
}
}
