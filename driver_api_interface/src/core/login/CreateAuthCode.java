package core.login;


import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.JsonPath;
import com.letvyidao.init.Constant;
import com.letvyidao.utils.DriverInfo;
import com.letvyidao.utils.GetOuthToken;
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
public class CreateAuthCode {
    public RequestHeaderConfig headerConfig = new RequestHeaderConfig(GetOuthToken.getOuthToken());
    public HttpUtils httpUtils;
    public DriverInfo driverInfo;
    public Map<String, String> params;
    public String url;
    public Map<String, String> headers;
    public String response;

    public CreateAuthCode() {
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

    @Test(description = "获取司机验证码")
    public void test_createAutCode() {
        response = httpUtils.doSendPostOAuth(url, params, headers).toString();
        JSONObject jsonObject = JSONObject.fromString(response);
        System.out.println(response);
        Assert.assertEquals(jsonObject.getInt("code"), 200, response + "---" + "参数如下：" + HttpUtils.getPostParameter(params));


    }


}
