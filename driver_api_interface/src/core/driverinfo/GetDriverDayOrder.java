package core.driverinfo;

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
 * Created by yongche on 16/11/3.
 */
public class GetDriverDayOrder {

    public RequestHeaderConfig headerConfig = new RequestHeaderConfig(GetOuthToken.getOuthToken());
    public HttpUtils httpUtils;
    public DriverInfo driverInfo;
    public Map<String, String> parms;
    public String url;
    public Map<String, String> headers;
    public String response;

    public GetDriverDayOrder() {
        headers = headerConfig.getHeaderMapWithToken();
        httpUtils = HttpUtils.getIntance();
        driverInfo = new DriverInfo().getDiverInfoAnd();
        parms = new HashMap<>();
        url = Constant.api_GetDriverDayOrder;

    }

    @BeforeMethod
    public void init() {

        parms.put("imei", driverInfo.imei);
        parms.put("version", driverInfo.driverAppVersion);
        parms.put("x_auth_mode", driverInfo.x_auth_mode);
        parms.put("is_gzip", driverInfo.is_zip);
        parms.put("device_type", driverInfo.device_type);
        parms.put("os_name", driverInfo.os_name);
        parms.put("os_version", driverInfo.os_version);
        parms.put("access_token", GetOuthToken.Access_token);
        parms.put("city", "北京市");
        parms.put("channel_source", "");
    }

    @Test(description = "司机今日收入")
    public void test_getDriverDayOrder() {
        response=httpUtils.doSendGetOAuth(url + "?" + HttpUtils.getPostParameter(parms), headers).toString();
        JSONObject jsonObject = JSONObject.fromString(response);
        System.out.println(response);
        Assert.assertTrue((jsonObject.getInt("code") == 200) || (jsonObject.getInt("code") == 201), "/V1/Driver/GetDriverDayOrder接口返回码非200、非201，接口返回信息：" + response.toString());
//        Assert.assertEquals(jsonObject.getInt("code"), 200, response + "---" + "参数如下：" + HttpUtils.getPostParameter(parms));
    }
}
