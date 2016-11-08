package com.letvyidao.inter;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.letvyidao.init.DriverAPIBase;
import com.letvyidao.utils.HttpUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DriverInter extends DriverAPIBase{
public JSONObject jsonObj = null;
public Map<String,String> params = null;
public static void main(String [] args){
	//accessToken:{"code":512,"msg":"\u8ba4\u8bc1\u672a\u901a\u8fc7,\u8bf7\u91cd\u65b0\u4e0b\u8f7dapp"}
	String rs =  URLDecoder.decode("\u975e\u5de5\u4f5c\u65e5\u4e0d\u5141\u8bb8\u63d0\u73b0");
	System.out.println(rs);//accessToken:{"code":512,"msg":"\u8ba4\u8bc1\u672a\u901a\u8fc7,\u8bf7\u91cd\u65b0\u4e0b\u8f7dapp"}
}
@BeforeMethod
public void initParams(){
	params = new HashMap<String,String>();
	params.put("version", driver.driverAppVersion);
	params.put("imei", driver.imei);
	params.put("x_auth_mode", "client_auth");
	params.put("is_gzip", "1");	
}
@Test(description="getDriverAccountDetail 我的账户详情")
public void testGetDriverAccountDetail(){
	String url = constant.appdriver_GetDriverAccountDetail+"?";
	params.put("reason", "10,18");
	params.put("page_num", "1");
	url +=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);	
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"/V2/Driver/GetDriverAccountDetai 我的账户详情接口报错，返回信息如下："+rs.toString());
    Assert.assertEquals(jsonObj.getJSONObject("msg").getInt("ret_code"),201,"/V2/Driver/GetDriverAccountDetai 我的账户详情接口报错，返回信息如下："+rs.toString());	
}
@Test(description="GetInBalanceList 结算中")
public void testGetInBalanceList(){
	String url = constant.appdriver_GetInBalanceList;
	params.put("page_num", "5");
	params.put("version", "80");
	Object  rs = HttpUtils.getIntance().doSendPostOAuth(url, params, headerParams);	
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	//{"msg":"true","code":200}
	Assert.assertEquals(jsonObj.getInt("code"), 200,"/V4/Driver/GetInBalanceList结算中接口报错，返回信息如下："+rs.toString());
//    Assert.assertEquals(jsonObj.getJSONObject("msg").getInt("ret_code"),499,"/v4/Driver/SetCarType 获取司机可变更的车型列表接口报错，返回信息如下："+rs.toString());	
}
@Test(description="GetLowCarType 获取司机可变更的车型列表")
public void testGetLowCarType(){
	String url = constant.appdriver_GetLowCarType+"?";
	url +=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);	
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"/V4/Driver/GetLowCarType 获取司机可变更的车型列表接口报错，返回信息如下："+rs.toString());
    Assert.assertTrue(jsonObj.getJSONObject("msg").getJSONArray("car_type_list").length()>=0, "/V4/Driver/GetLowCarType 获取司机可变更的车型列表接口报错，返回信息如下："+rs.toString());
}
@Test(description="GetDriverEvalute 好评率接口")
public void testGetDriverEvalute(){
	String url = constant.appdriver_GetDriverEvalute+"?";
	url +=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);	
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"V1/Driver/GetDriverEvalute  好评率接口报错，返回信息如下："+rs.toString());
    Assert.assertEquals(jsonObj.getJSONObject("msg").getInt("good_comment_count"), 0,"V1/Driver/GetDriverEvalute  好评率接口报错,返回信息如下："+rs.toString());
}
@Test(description="WithdrawDriverCash 司机申请提现")
public void testWithdrawDriverCash(){
	String url = constant.appdriver_WithdrawDriverCash;
	params.put("amount", "1");
	Object rs = HttpUtils.getIntance().doSendPostOAuth(url, params, headerParams);	
	//{"msg":"true","code":200}
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"/Driver/WithdrawDriverCash 司机申请提现接口报错，返回信息如下："+rs.toString());
    Assert.assertEquals(jsonObj.getJSONObject("msg").getString("ret_msg"), "提现金额必须大于2","/Driver/WithdrawDriverCash 司机申请提现接口报错,返回信息如下："+rs.toString());
}
@Test(description="getDriverLevelDetailDay 司机日经验值明细")
public void testgetDriverLevelDetailDay(){
	String url = constant.appdriver_getDriverLevelDetailDay+"?";
	params.put("date", "20161011");
	url+=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);	
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"V4/Driver/getDriverLevelDetailDay 司机日经验值明细接口报错，返回信息如下："+rs.toString());
	int sum = jsonObj.getJSONObject("msg").getInt("sum");
	JSONArray details = jsonObj.getJSONObject("msg").getJSONArray("detail");
	int actualSum = 0;
	for(int i=0;i<details.length();i++){
	   actualSum+= details.getJSONObject(i).getInt("score");
	}
	Assert.assertTrue(jsonObj.getJSONObject("msg").getString("sum").matches("[0-9]*"),"V4/Driver/getDriverLevelDetailDay 司机日经验值明细接口报错，返回信息如下："+rs.toString());
    Assert.assertEquals(actualSum, sum,"V4/Driver/getDriverLevelDetailDay 司机日经验值明细接口报错，返回信息如下："+rs.toString());
}
@Test(description="getDriverLevelDetailMonth 司机经验值明细")
public void testgetDriverLevelDetailMonth(){
	String url = constant.appdriver_getDriverLevelDetailMonth+"?";
	url+=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);	
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"/V4/Driver/getDriverLevelDetailMonth 司机经验值明细接口报错，返回信息如下："+rs.toString());
	Assert.assertTrue(jsonObj.getJSONObject("msg").getString("sum").matches("[0-9]*"),"/V4/Driver/getDriverLevelDetailMonth 司机经验值明细接口报错，返回信息如下："+rs.toString());
}
@Test(description="GetDriverLevelInfo 司机等级")
public void testGetDriverLevelInfo(){
	String url = constant.appdriver_GetDriverLevelInfo+"?";
	url+=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);	
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"/V4/Driver/GetDriverLevelInfo 司机等级接口报错，返回信息如下："+rs.toString());
	Assert.assertEquals(jsonObj.getJSONObject("msg").getInt("next_level"), jsonObj.getJSONObject("msg").getInt("driver_level")+1,"/V4/Driver/GetDriverLevelInfo 司机等级接口报错，返回信息如下："+rs.toString());
}
@Test(description="member 获取司机信息")
public void testMember(){
	String url = constant.appdriver_member+"?";
	url+=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);	
	jsonObj = JSONObject.fromString(rs.toString());
//	String name = jsonObj.getJSONObject("msg").getString("name");
	System.out.println(jsonObj.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"/driver/member 获取司机信息接口报错，返回信息如下："+rs.toString());
	Assert.assertEquals(jsonObj.getJSONObject("msg").getJSONObject("member_info").getString("login_name"),"压测司机995","/driver/member 获取司机信息接口报错，返回信息如下："+rs.toString());	
}
@Test(description="GetDriverAccount 我的账户首页")
public void testGetDriverAccount(){
	String url = constant.appdriver_GetDriverAccount+"?";
	url+=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	
	jsonObj = JSONObject.fromString(rs.toString());
	String name = jsonObj.getJSONObject("msg").getString("name");
	System.out.println(jsonObj.toString()+"====="+name);
	Assert.assertEquals(jsonObj.getInt("code"), 200,"v4/Driver/GetDriverAccount 我的账户首页接口报错，返回信息如下："+rs.toString());
	Assert.assertEquals(jsonObj.getJSONObject("msg").getString("name"),"压测司机995","v4/Driver/GetDriverAccount 我的账户首页接口报错，返回信息如下："+rs.toString());
}
@Test(description="Driver/GetContributionOrder 取得司机参与分相关订单信息")
public void testGetContributionOrder(){
	String url = constant.appdriver_GetContributionOrder+"?";
	url+=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	System.out.println(rs.toString());
	jsonObj = JSONObject.fromString(rs.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 499,"Driver/GetContributionOrder 取得司机参与分相关订单信息接口报错，返回信息如下："+rs.toString());
	Assert.assertEquals(jsonObj.getString("msg"), "无法取得司机参与分相关的订单信息！","Driver/GetContributionOrder 取得司机参与分相关订单信息接口报错，返回信息如下："+rs.toString());
//	Assert.assertEquals(jsonObj.getJSONObject("msg").getJSONArray("list").length(), jsonObj.getJSONObject("msg").getInt("count"),"Driver/GetContributionOrder 取得司机参与分相关订单信息接口报错，返回信息如下："+rs.toString());
}
@Test(description="DriverGetDriverIncome 司机端获取历史收入的接口")
public void testGetDriverIncome(){
 	String url = constant.appdriver_GetDriverIncome+"?";
 	params.put("type", "month");
 	url += interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	System.out.println(rs.toString());
	jsonObj = JSONObject.fromString(rs.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"DriverGetDriverIncome 司机端获取历史收入的接口返回值非200，接口返回内容如下："+rs.toString());
	Assert.assertTrue(StringUtils.isNotEmpty(jsonObj.getString("msg")),"DriverGetDriverIncome 司机端获取历史收入的接口报错，报错内容："+rs.toString());
    params.put("type", "month_history");    
    url =constant.appdriver_GetDriverIncome+"?"+interfaceUtils.doGetParamsStr(params);
	 rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	System.out.println(rs.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"DriverGetDriverIncome 司机端获取历史收入的接口返回值非200，接口返回内容如下："+rs.toString());
	params.put("type", "day");
	 url =constant.appdriver_GetDriverIncome+"?"+interfaceUtils.doGetParamsStr(params);
	rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	System.out.println(rs.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 200,"DriverGetDriverIncome 司机端获取历史收入的接口返回值非200，接口返回内容如下："+rs.toString());
}
@Test(description="GetDriverIncomeOrder 取得司机收入相关订单明细")
public void testGetDriverIncomeOrder(){
  	String url = constant.appdriver_GetDriverIncomeOrder+"?";
	params.put("type", "month");
	params.put("timeflag", "2016-02");
	url += interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	System.out.println(rs.toString());
	jsonObj = JSONObject.fromString(rs.toString());
	Assert.assertEquals(jsonObj.getInt("code"), 404,"GetDriverIncomeOrder 取得司机收入相关订单明细接口报错，报错信息如下："+rs.toString());
	Assert.assertEquals(jsonObj.getString("msg"), "没有任何结果！","GetDriverIncomeOrder 取得司机收入相关订单明细接口报错，报错信息如下："+rs.toString());
}
@Test(description="/Order/GetItemOrder 获取订单详情")
public void testGetItemOrder(){
	String url = constant.appdriver_OrderGetItemOrder+"?";
	params.put("order_id", "2005817779");
	params.put("out_coord_type", "baidu");
	url += interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	Assert.assertTrue((jsonObj.getInt("code")==200),"/Order/GetItemOrder接口返回码非200，接口返回信息："+rs.toString());
//	Assert.assertTrue(jsonObj.getJSONObject("msg").getString("today_income").matches("[0-9]*"),"/V5/Driver/GetIndex接口报错，接口返回信息："+rs.toString());
}
@Test(description="GetIndex 首页信息")
public void testGetIndex(){
	String url = constant.appdriver_DriverGetIndex+"?";
	url += interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	System.out.println(rs.toString());
	jsonObj = JSONObject.fromString(rs.toString());
	Assert.assertTrue((jsonObj.getInt("code")==200),"/V5/Driver/GetIndex接口返回码非200，接口返回信息："+rs.toString());
	Assert.assertTrue(jsonObj.getJSONObject("msg").getString("today_income").matches("[0-9]*"),"/V5/Driver/GetIndex接口报错，接口返回信息："+rs.toString());
}
@Test(description="MemberStat 司机工作状态")
public void testMemberStat(){
	String url = constant.appdriver_DriverMemberStat+"?";
	url += interfaceUtils.doGetParamsStr(params);	
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(jsonObj.toString());
	Assert.assertTrue((jsonObj.getInt("code")==200),"/Driver/MemberStat接口返回码非200，接口返回信息："+rs.toString());
	System.out.println(jsonObj.getString("msg"));
	Assert.assertTrue(jsonObj.getString("msg").matches("..nobusy.*"),"/Driver/MemberStat接口报错，接口返回信息："+rs.toString());
}
@Test(description="CurrentVersion 获取版本")
public void testCurrentVersion(){
	String url = constant.appdriver_CurrentVersion+"?";
//	http://testing2.driver-api.yongche.org/global/currentVersion?imei=868568025241420&version=80&x_auth_mode=client_auth&is_gzip=true
	Map<String,String> params =  new HashMap<String,String>();	
	params.put("imei",driver.imei);
	params.put("version",driver.driverAppVersion);
	params.put("x_auth_mode","client_auth");
	params.put("device_type", "1");
	url += interfaceUtils.doGetParamsStr(params);	
	Object rs = HttpUtils.getIntance().doSendGetOAuth(url, headerParams);
	jsonObj = JSONObject.fromString(rs.toString());
	System.out.println(rs.toString());
	Assert.assertTrue((jsonObj.getInt("code")==200)||(jsonObj.getInt("code")==412),"/Global/CurrentVersion接口返回码非200、非412，接口返回信息："+rs.toString());
	Assert.assertTrue(jsonObj.getJSONObject("msg").getString("current_version").matches("[1-9][0-9]*"),"/Global/CurrentVersion接口报错，接口返回信息："+rs.toString());
}
@Test(description="CreateDriverPassword 获取验证码")
public void testDriverCreateDriverPassword(){
	String url = constant.appdriver_CreateDriverPassword+"?";
	params.put("cellphone", driver.cellPhone);	
	params.put("vehicle_number", driver.vehicle_number);	
	params.put("area_code", "86");
	url+=interfaceUtils.doGetParamsStr(params);
	Object rs = HttpUtils.getIntance().doSendGet(url);
	jsonObj = JSONObject.fromString(rs.toString());
    System.out.println(rs.toString());
    Assert.assertEquals(jsonObj.getInt("code"), 200,"/Driver/CreateDriverPassword接口返回码非200，接口返回值如下:"+rs.toString());
}

@Test(description="v4/Driver/Appeal申诉内容提交   /v4/Driver/GetAppealContent 申诉机会次数获取 流程监控")
public void testAppealAndGetAppealContent(){
	try{
		jsonObj = driverUtils.getAppealContent();
		String appealBefore = jsonObj.toString();
		System.out.println("appealBefore:"+appealBefore);
		Assert.assertEquals(jsonObj.getInt("code"), 200,"/v4/Driver/GetAppealContent接口返回状态码非200，返回内容如下："+appealBefore);
		int appeal_count = jsonObj.getJSONObject("msg").getInt("appeal_count");
		 jsonObj = driverUtils.appeal();
		 String appeal = jsonObj.toString();
		 System.out.println("appeal:"+appeal);
		 Assert.assertEquals(jsonObj.getInt("code"), 200,"/v4/Driver/Appeal申诉内容提交接口返回状态码非200  返回内容如下："+appeal);
		boolean appealRS = jsonObj.getJSONObject("msg").getBoolean("result");
		String ret_msg = "";
		if(!appealRS){
			ret_msg=jsonObj.getJSONObject("msg").getString("ret_msg");
		}	 
		jsonObj = driverUtils.getAppealContent();
		String appealAfter = jsonObj.toString();
		System.out.println(appealAfter);
		int appeal_count2 = jsonObj.getJSONObject("msg").getInt("appeal_count");;
		if(StringUtils.isNotEmpty(ret_msg)){
			ret_msg=interfaceUtils.decode(ret_msg);
		}
		if(appeal_count-appeal_count2==0){
			Assert.assertEquals(ret_msg, "你没有申诉机会了!","司机调用申诉接口后，申诉次数数量没有减少！,申诉前--申诉--申诉后返回内容如下：；"+appealBefore+","+appeal+","+appealAfter);
		}else if(appeal_count-appeal_count2==1){
		 Assert.assertEquals(appeal_count-appeal_count2, 1,"司机调用申诉接口后，申诉次数数量没有减少！,申诉前--申诉--申诉后返回内容如下：；"+appealBefore+","+appeal+","+appealAfter);	
		}	
	}catch(Exception e){
		e.printStackTrace();
		Assert.assertTrue(false,"申诉前--申诉--申诉后流程报错，报错内容："+e.getMessage());
	}
	
}
//@Test(description="V6CarmasterGetcarlist 获取车辆列表（车主）")
//public void V6CarmasterGetcarlist(){
//	try{
//		String url = "http://xiepeijie.dev-driverapi.yongche.org/V6/Carmaster/Getcarlist";
////		Map<String,String>headerParams = driverUtils.getRequestHeader(6);	
//	}catch(Exception e){
//		e.printStackTrace();
//	}
//	
////	Object rs = doSendGet(url, headerParams);//
////	System.out.println(rs.toString());
//}
//@Test(description="获取首页")
//public void testV6CarMasterGetHomePage(){	
//	//device_type=1是android 2是ios
////	String url = "http://xiepeijie.dev-driverapi.yongche.org/V6/CarMaster/GetHomePage?city=bj&device_type=1&version=202&is_gzip=true&os_name=Coolpad-Coolpad 9190L&os_version=4.3&channel_source=";
////	Map<String,String>headerParams = interfaceUtils.getRequestHeader(6);
////	Object rs = doSendGet(url, headerParams);//
////	System.out.println(rs.toString());
//}
/*
 * 根据条件查询司机信息：位置筛选系统
 */
//@Test
//public void  testGetDriverSelect(){
//	String url = "http://172.17.0.248:8888/api/v1/drivers/select";
//	String entityStr = "{\"filters\": [{\"ext\": {\"lng\": 100.152,\"lat\":36.902},\"key\": \"distance\",\"op\": \"<\",\"type\": \"float\",\"val\": 50}, {\"key\": \"work_status\",\"op\" : \"in\",\"type\": \"array\",\"val\" : [1,0]}],\"limit\": 300}";
//	Object rs = HttpUtils.getIntance().doSendPost(url,entityStr);
//	System.out.println(rs.toString());
//	 if(rs!=null &&rs.toString().matches(".*ret_code\":200,.*")){
//		 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//	}
//}
///*
// * 根据订单信息获取乘客端计费结果(ok)
// */
//@Test
//public void testCustomerRating(){
// String url = "http://quhongli.dev-charge.yongche.org/v1/Charge/cRating";
// List<BasicNameValuePair> list = readFileUtils.getParameterFromFileAsList("customerracing.txt");
// Object rs = doSendPost(url, list);
// System.out.println(rs.toString());
// if(rs!=null && rs.toString().matches(".*ret_code\":200,.*")){
//	 Assert.assertTrue(true);
// }else{
//	 Assert.assertTrue(false, rs.toString());
//}
//}
/*
 * 根据订单信息获取司机端订单计费结果
 */
//@Test
//public void testDriverRating(){
//	 String url = "http://quhongli.dev-charge.yongche.org/v1/Charge/dRating";
//	 List<BasicNameValuePair> list = readFileUtils.getParameterFromFileAsList("driverracing.txt");
//	 Object rs = doSendPost(url, list);
//	 System.out.println(rs.toString());
//	 if(rs!=null && rs.toString().matches(".*ret_code\":200,.*")){
//		 Assert.assertTrue(true);
//	 }else{
//		 Assert.assertTrue(false, rs.toString());
//	}	
//}
/*
 * 查找账户
 */
//@Test
//public void testGetAccountInfo(){
//	String url = "http://sandbox.account.yongche.org/v1/account/find?account_id=19000000582";
//	Object rs = doSendGet(url);
//	System.out.println(rs.toString());
//	 if(rs!=null && rs.toString().matches(".*ret_code\":200,.*")){
//		 Assert.assertTrue(true);
//	 }else{
//		 Assert.assertTrue(false, rs.toString());
//	}
//}
/*
 * 用户优惠计算
 */
//@Test
//public void testCalculateRate(){
//	List<BasicNameValuePair> list = readFileUtils.getParameterFromFileAsList("c_calculaterate.txt");
//	String url = "http://user.lan.yongche.com/Preference/guess";
//	Object rs =HttpUtils.getIntance().doSendPost(url, list);
//	System.out.println((rs==null)+"rs=========="+rs.toString()+"============");
//	 if(rs!=null && rs.toString().matches(".*code\":200,.*")){
//		 System.out.println("测试成功");
////		 Assert.assertTrue(true);
//	 }else{
//		 System.out.println("测试失败");
//	}
//}
/*
 * 获取用户信息接口
 */
//@Test
//public void testGetByUserIds(){
// String url = "http://user.lan.yongche.com/User/getByUserIds?user_ids=5569,617";
// Object rs = HttpUtils.getIntance().doSendGet(url);
// System.out.println(rs.toString());
// if(rs!=null && rs.toString().matches(".*code\":200,.*")){
//	 Assert.assertTrue(true);
// }else{
//	 Assert.assertTrue(false, rs.toString());
//}
//}

}
