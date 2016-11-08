package core.order;

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
public class ChangeDispatch {
    public RequestHeaderConfig headerConfig = new RequestHeaderConfig(GetOuthToken.getOuthToken());
    public HttpUtils httpUtils;
    public DriverInfo driverInfo;
    public Map<String, String> parms;
    public String url;
    public Map<String, String> headers;
    public String response;

    public ChangeDispatch() {
        headers = headerConfig.getHeaderMapWithToken();
        httpUtils = HttpUtils.getIntance();
        driverInfo = new DriverInfo().getDiverInfoAnd();
        parms = new HashMap<>();
        url = Constant.api_ChangeDispatch;

    }

    @BeforeMethod
    public void init() {

        parms.put("imei", driverInfo.imei);
        parms.put("version", driverInfo.driverAppVersion);
        parms.put("x_auth_mode", driverInfo.x_auth_mode);
        parms.put("is_gzip", driverInfo.is_zip);
        parms.put("device_type", driverInfo.device_type);
        parms.put("os_name", driverInfo.os_name);
        parms.put("order_id", "6348673836009541878");
        parms.put("os_version", driverInfo.os_version);
        parms.put("access_token", GetOuthToken.Access_token);
        parms.put("city", "北京市");
        parms.put("channel_source", "");
    }

    @Test(description = "改派订单,code:499 订单不支持改派; code:498 请校准时间重试" )
    public void test_changeDispatch() {
        response=httpUtils.doSendPostOAuth(url,parms, headers).toString();
        JSONObject jsonObject = JSONObject.fromString(response);
        System.out.println(response);
        Assert.assertEquals((jsonObject.getInt("code") == 200) || (jsonObject.getInt("code") == 499) || (jsonObject.getInt("code") == 498), "/v4/Driver/ChangeDispatch接口返回码非200、非499、非498，接口返回信息： 结果如下：" + response.toString());
//        Assert.assertTrue((jsonObject.getInt("code") == 200) || (jsonObject.getInt("code") == 412), "/Global/CurrentVersion接口返回码非200、非412，接口返回信息：" + response.toString());
        //TODO
    }

}

