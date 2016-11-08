package core.drivercash;

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
public class GetDriverAccount {
    public RequestHeaderConfig headerConfig = new RequestHeaderConfig(GetOuthToken.getOuthToken());
    public HttpUtils httpUtils;
    public DriverInfo driverInfo;
    public Map<String, String> parms;
    public String url;
    public Map<String, String> headers;
    public String response;

    public GetDriverAccount() {
        headers = headerConfig.getHeaderMapWithToken();
        httpUtils = HttpUtils.getIntance();
        driverInfo = new DriverInfo().getDiverInfoAnd();
        parms = new HashMap<>();
        url = Constant.api_GetDriverAccount;

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
        parms.put("access_token", GetOuthToken.Oauth_token);
        parms.put("city", "北京市");
        parms.put("channel_source", "");
    }

    @Test(description = "我的钱包-获取司机可提现金额")
    public void test_getDriverAccount() {
        response=httpUtils.doSendGetOAuth(url+"?"+ HttpUtils.getPostParameter(parms)+headerConfig.get_userAgent_and(), headers).toString();
        JSONObject jsonObject = JSONObject.fromString(response);
        System.out.println(response);
        Assert.assertEquals(jsonObject.getInt("code"), 200, response + "---" + "参数如下：" + HttpUtils.getPostParameter(parms));
    }

}

