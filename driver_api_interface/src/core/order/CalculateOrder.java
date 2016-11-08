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
public class CalculateOrder {
    public RequestHeaderConfig headerConfig = new RequestHeaderConfig(GetOuthToken.getOuthToken());
    public HttpUtils httpUtils;
    public DriverInfo driverInfo;
    public Map<String, String> parms;
    public String url;
    public Map<String, String> headers;
    public String response;

    public CalculateOrder() {
        headers = headerConfig.getHeaderMapWithToken();
        httpUtils = HttpUtils.getIntance();
        driverInfo = new DriverInfo().getDiverInfoAnd();
        parms = new HashMap<>();
        url = Constant.api_OperateOrder_calculateOrder;

    }

    @BeforeMethod
    public void init() {
        parms.put("end_time","1478155957");
        parms.put("highway_amount","0.0");
        parms.put("start_time","1478159490");
        parms.put("order_id","6348631238523899699");
        parms.put("system_distance","0.0");
        parms.put("input_distance","0.11");
        parms.put("mileage","0.0");
        parms.put("parking_amount","0.0");
        parms.put("airport_service_count","0.0");
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

    @Test(description = "修改公里数")
    public void test_calculateOrder() {
        response=httpUtils.doSendPostOAuth(url,parms,headers).toString();
        JSONObject jsonObject = JSONObject.fromString(response);
        System.out.println(response);
        Assert.assertEquals(jsonObject.getInt("code"), 200, response + "---" + "参数如下：" + HttpUtils.getPostParameter(parms));
    }

}
