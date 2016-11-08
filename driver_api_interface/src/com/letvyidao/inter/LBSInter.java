package com.letvyidao.inter;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.letvyidao.init.BaseRequest;
import com.letvyidao.utils.HttpUtils;

import net.sf.json.JSONObject;

public class LBSInter extends BaseRequest{
public static Logger logger = LoggerFactory.getLogger(LBSInter.class);
@Test(description="乘客端上车点名称推荐 ")
public void testV1Around(){
   String url = constant.lbs_around+"?";
   Map<String,String> requestParams = new HashMap<String,String>();
//out_coord_type=baidu&area_km=10&max_show_near_car_num=100&user_id=16471381&pretty=true
   requestParams.put("lat", "36.9022");
   requestParams.put("lng", "100.1522");
   requestParams.put("in_coord_type", "mars");
   requestParams.put("out_coord_type", "baidu");
   requestParams.put("car_type_ids", "3");
   requestParams.put("area_km", "10");
   requestParams.put("max_show_near_car_num", "3");
  String getParamsStr = interfaceUtils.doGetParamsStr(requestParams);
   url += getParamsStr;
   Object rs = HttpUtils.getIntance().doSendGet(url);
   jsonObj = JSONObject.fromString(rs.toString());
   Assert.assertEquals(jsonObj.getInt("code"), 200,"乘客端上车点名称推荐 接口：/api/v1/around返回码 非200，返回结果如下："+rs.toString());
   Assert.assertEquals(jsonObj.getString("status"), "ok","乘客端上车点名称推荐 接口：/api/v1/around异常，返回结果如下："+rs.toString());
   Assert.assertTrue(jsonObj.getJSONObject("results").getInt("count")>=0,"乘客端上车点名称推荐 接口：/api/v1/around异常，返回结果如下："+rs.toString());
}
@Test(description="乘客端上下车点搜索  /api/v1/place")
public void testV1Place(){
	String url = constant.lbs_place+"?";
	 Map<String,String> requestParams = new HashMap<String,String>();
	 requestParams.put("lat", "39.98791");
	   requestParams.put("lng", "116.31286");
	   requestParams.put("city", "北京市");
	   requestParams.put("map_type", "2");
	   requestParams.put("in_coord_type", "mars");
	   requestParams.put("out_coord_type", "mars");
	   requestParams.put("place_type", "2");
	   String getParamsStr = interfaceUtils.doGetParamsStr(requestParams);
	   url += getParamsStr;
	   Object rs = HttpUtils.getIntance().doSendGet(url);
	   jsonObj = JSONObject.fromString(rs.toString());
	   System.out.println(rs.toString());
	   Assert.assertEquals(jsonObj.getInt("ret_code"), 200," /api/v1/place接口返回值非200，接口返回值如下："+rs.toString());
	   Assert.assertEquals(jsonObj.getString("status"), "ok","/api/v1/place异常，返回结果如下："+rs.toString());
	   Assert.assertTrue(jsonObj.getJSONArray("results").length()>=1,"/api/v1/place异常，返回结果如下："+rs.toString());
	   String poi_id = jsonObj.getJSONArray("results").getJSONObject(0).getString("poi_id");
	   String name = jsonObj.getJSONArray("results").getJSONObject(0).getString("name");
	   System.out.println("name:"+name);
	   Assert.assertTrue(poi_id.matches("b_.*")&&name.matches("北京大学"),"/api/v1/place异常，返回结果如下："+rs.toString());
}

@Test(description="乘客端上下车点搜索  /api/v1/place")
public void testV1PlaceKeyWorld(){
	String url = constant.lbs_place+"?";
	 Map<String,String> requestParams = new HashMap<String,String>();
	 requestParams.put("keywords", "银科大厦");
	   requestParams.put("city", "北京市");
	   requestParams.put("map_type", "1");
	   requestParams.put("in_coord_type", "mars");
	   requestParams.put("out_coord_type", "mars");
	   requestParams.put("place_type", "1");
	   String getParamsStr = interfaceUtils.doGetParamsStr(requestParams);
	   url += getParamsStr;
	   Object rs = HttpUtils.getIntance().doSendGet(url);
	   jsonObj = JSONObject.fromString(rs.toString());
	   System.out.println(rs.toString());
	   Assert.assertEquals(jsonObj.getInt("ret_code"), 200," /api/v1/place接口返回值非200，接口返回值如下："+rs.toString());
	   Assert.assertEquals(jsonObj.getString("status"), "ok","/api/v1/place异常，返回结果如下："+rs.toString());
	   Assert.assertTrue(jsonObj.getJSONArray("results").length()>=1,"/api/v1/place异常，返回结果如下："+rs.toString());
	   String poi_id = jsonObj.getJSONArray("results").getJSONObject(0).getString("poi_id");
	   String name = jsonObj.getJSONArray("results").getJSONObject(0).getString("name");
	   Assert.assertTrue(poi_id.matches("a_.*")&&name.matches("银科大厦"),"/api/v1/place异常，返回结果如下："+rs.toString());
}
@Test(description="乘客端上下车点搜索  /api/v1/suggest")
public void testV1Suggest(){
	String url = constant.lbs_suggest+"?";
	 Map<String,String> requestParams = new HashMap<String,String>();
	 requestParams.put("lat", "39.98791");
	   requestParams.put("lng", "116.31286");
	   requestParams.put("city", "北京市");
	   requestParams.put("map_type", "2");
	   requestParams.put("out_coord_type", "world");
	   requestParams.put("keywords", "银科");
	   String getParamsStr = interfaceUtils.doGetParamsStr(requestParams);
	   url += getParamsStr;
	   Object rs = HttpUtils.getIntance().doSendGet(url);
	   jsonObj = JSONObject.fromString(rs.toString());
	   System.out.println(rs.toString());
	   Assert.assertEquals(jsonObj.getInt("ret_code"), 200,rs.toString());
	   Assert.assertEquals(jsonObj.getString("status"), "ok",rs.toString());
	   Assert.assertTrue(jsonObj.getJSONArray("results").length()>=1,rs.toString());
	   String poi_id = jsonObj.getJSONArray("results").getJSONObject(0).getString("poi_id");
	   String name = jsonObj.getJSONArray("results").getJSONObject(0).getString("name");
	   System.out.println("name:"+name);
	   Assert.assertTrue(poi_id.matches("b_.*")&&name.matches("银科大厦"),rs.toString());
}

@Test(description="乘客端上下车点搜索  /api/v1/suggest")
public void testV1Rgeo(){
	String url = constant.lbs_rgeo+"?";
	 Map<String,String> requestParams = new HashMap<String,String>();
	 //lat=39.975928&lng=116.3129&coord_type=mars
	 requestParams.put("lat", "39.98791");
	   requestParams.put("lng", "116.31286");
	   requestParams.put("coord_type", "mars");
	   String getParamsStr = interfaceUtils.doGetParamsStr(requestParams);
	   url += getParamsStr;
	   Object rs = HttpUtils.getIntance().doSendGet(url);
	   jsonObj = JSONObject.fromString(rs.toString());
	   System.out.println(rs.toString());
	   Assert.assertEquals(jsonObj.getInt("code"), 200,rs.toString());
	   Assert.assertEquals(jsonObj.getString("status"), "ok",rs.toString());
	   Assert.assertEquals(jsonObj.getJSONObject("results").getString("aoi_name"),"北京大学",rs.toString());

}
@Test(description="乘客端上下车点搜索  /api/v1/suggest")
public void testV1Estimate(){
	String url = constant.lbs_estimate;
	 Map<String,String> requestParams = new HashMap<String,String>();
	 requestParams.put("origin", "\"origin\":{\"lat\":39.98791,\"lng\":116.31286 }");
	 requestParams.put("destination", "\"destination\": { \"lat\": 40.07968,\"lng\": 116.43490 }");
	 requestParams.put("in_coord_type", "world");
	   Object rs = HttpUtils.getIntance().doSendPost(url, requestParams);
	   jsonObj = JSONObject.fromString(rs.toString());
	   System.out.println(rs.toString());
	   Assert.assertEquals(jsonObj.getInt("code"), 200,rs.toString());
//	   Assert.assertEquals(jsonObj.getString("status"), "ok",rs.toString());
//	   Assert.assertEquals(jsonObj.getJSONObject("results").getString("aoi_name"),"北京大学",rs.toString());

}
}
