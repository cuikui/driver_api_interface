package core.login;

import com.letvyidao.init.Constant;
import com.letvyidao.utils.DriverInfo;
import com.letvyidao.utils.HttpUtils;
import com.letvyidao.utils.RequestHeaderConfig;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yongche on 16/11/1.
 */
public class Login {

    public RequestHeaderConfig headerConfig = new RequestHeaderConfig();
    public HttpUtils httpUtils;
    public DriverInfo driverInfo;
    public Map<String, String> params;
    public String url;
    public Map<String, String> headers;
    public String response;
    public JSONObject jsonObject = null;


    public Login() {
        headers = headerConfig.getHeaderMapWithToken();
        httpUtils = HttpUtils.getIntance();
        driverInfo = new DriverInfo().getDiverInfoAnd();
        params = new HashMap<>();
        url = Constant.api_CreateAuthCode;



    }


    @BeforeMethod
    public void init() {
        params.put("cellphone", driverInfo.cellPhone);
        params.put("area_code", "86");
        params.put("imei", driverInfo.imei);
        params.put("version", driverInfo.driverAppVersion);
        params.put("x_auth_mode", driverInfo.x_auth_mode);
        params.put("is_gzip", driverInfo.is_zip);
        params.put("device_type", "1");
        params.put("os_name", driverInfo.os_name);
        params.put("os_version", driverInfo.os_version);
        params.put("city", "北京市");
        params.put("channel_source", "");

    }


    @Test(description = "车主端-司机登陆")
    public   void  test_login(){
        response = httpUtils.doSendPostOAuth(url, params, headers).toString();
        JSONObject  jsonObj = JSONObject.fromString(response.toString());
        String password = jsonObj.getJSONObject("msg").getString("auth_code");
        System.out.println(password);
        url = Constant.api_Login;
        params.put("cellphone", driverInfo.cellPhone);
        params.put("area_code", "86");
        params.put("imei", driverInfo.imei);
        params.put("version", driverInfo.driverAppVersion);
        params.put("x_auth_mode", "client_auth");
        params.put("is_gzip", "1");
        params.put("device_type", "1");
        params.put("os_name", driverInfo.os_name);
        params.put("os_version", driverInfo.os_version);
        params.put("city", "北京市");
        params.put("channel_source", "");
        params.put("x_auth_username", "16820160309");
        params.put("x_auth_password", password);
        params.put("access_token", "");
        params.put("client_secret", "84e1c410ee5796bf026b0179f4a15a98");
        params.put("client_id", "car_master");
        params.put("channel_source", "");
        response= httpUtils.doSendPostOAuth(url, params, headers).toString();
        JSONObject jsonObject = JSONObject.fromString(response);
        System.out.println(response);
        Assert.assertEquals(jsonObject.getInt("code"), 200, response + "---" + "参数如下：" + HttpUtils.getPostParameter(params));


    }

}
