package com.letvyidao.utils;

import java.util.HashMap;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.letvyidao.init.Constant;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class GetOuthToken {

    public String url;
    public RequestHeaderConfig header = new RequestHeaderConfig();
    public HttpRequest request;
    public String response;
    public int code;
    public String hearders;
    public DriverInfo driver;
    public HashMap<String, String> params;
    public static   String Access_token;
    public static   String Oauth_token;







    {
        hearders = header.getHeaderString();
        url = Constant.api_CreateAuthCode;
        driver = new DriverInfo().getDiverInfoAnd();
        params = new HashMap<>();
    }


    public String login() {
        params.put("cellphone", driver.cellPhone);
        params.put("area_code", "86");
        params.put("imei", driver.imei);
        params.put("version", driver.driverAppVersion);
        params.put("x_auth_mode", "client_auth");
        params.put("is_gzip", "1");
        params.put("device_type", "1");
        params.put("os_name", driver.os_name);
        params.put("os_version", driver.os_version);
        params.put("city", "北京市");
        params.put("channel_source", "");
        request = HttpRequest.post(url).authorization(hearders).userAgent(header.get_userAgent_and()).acceptEncoding("gzip,deflate,sdch").uncompress(true).form(params);
        code = request.code();
        response = request.body();
        String password = JsonPath.read(response, "$.msg.auth_code").toString();

        url = Constant.api_Login;
        params.put("cellphone", driver.cellPhone);
        params.put("area_code", "86");
        params.put("imei", driver.imei);
        params.put("version", driver.driverAppVersion);
        params.put("x_auth_mode", "client_auth");
        params.put("is_gzip", "1");
        params.put("device_type", "1");
        params.put("os_name", driver.os_name);
        params.put("os_version", driver.os_version);
        params.put("city", "北京市");
        params.put("channel_source", "");
        params.put("x_auth_username", "16820160309");
        params.put("x_auth_password", password);
        params.put("access_token", "");
        params.put("client_secret", "84e1c410ee5796bf026b0179f4a15a98");
        params.put("client_id", "car_master");
        params.put("channel_source", "");

        request = HttpRequest.post(url).authorization(hearders).userAgent(header.getUser_Agent()).acceptEncoding("gzip,deflate,sdch").uncompress(true).form(params);
        response = request.body();
        Access_token = JsonPath.read(response, "$.msg.oauth2_token.access_token");
        Oauth_token = JsonPath.read(response, "$.msg.oauth_token.oauth_token");
        System.out.println(response);
        System.out.println("Oauth_token-->"+Oauth_token+"\r\n"+"Access_token-->"+Access_token);
        return  Oauth_token;
    }

	public static String getOuthToken() {
		if (Oauth_token == null) {
            Oauth_token = new GetOuthToken().login();
		}
		return Oauth_token;
	}




}
