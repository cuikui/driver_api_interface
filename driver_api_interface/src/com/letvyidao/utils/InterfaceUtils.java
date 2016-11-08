package com.letvyidao.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InterfaceUtils {
    private JSONObject jsonObj = null;

    public String decode(String decodeStr) {
        String decoders = URLDecoder.decode(decodeStr);
        return decoders;
    }

    public void wait(int millsecond) {
        try {
            Thread.sleep(millsecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encode(String encodeStr) {
        String encoders = URLEncoder.encode(encodeStr);
        return encoders;
    }


    public String doGetParamsStr(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!"".equals(entry.getValue()) && !"null".equals(entry.getValue())) {
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    public Map<String, String> createAnOrder() {
        JSONObject jsonObj = null;
        Map<String, String> orderParams = null;
        String orderId = "";
        try {
            //测试环境
//		    String [] uids = {"13025137","13025135","13025134"};
//			String [] phones={"16809340982","16820161007","16899000094"};
            //线上环境
            String[] uids = {"43276378", "43277106", "43277118"};
            String[] phones = {"16871051234", "16871051235", "16871051236"};
            JSONArray car_list = null;
            boolean acceptRS = false;
            int carNumbr = 0;
            for (int i = 0; i < 3; i++) {
                HttpUtils.getIntance().request.data = "";
                String startTime = (System.currentTimeMillis() / 1000 + 600) + "";
                HttpUtils.getIntance().request.service_uri = "state/createOrder?user_id=" + uids[i] + "&corporate_id=0&passenger_phone=" + phones[i] + "&passenger_name=testpassager&passenger_number=1&city=bj&product_type_id=1&fixed_product_id=0&car_type_id=3&car_type_ids=3&source=20000001&expect_start_time=" + startTime + "&in_coord_type=baidu&expect_end_latitude=36.9021&expect_end_longitude=100.1521&expect_start_latitude=36.9022&expect_start_longitude=100.1522&start_position=testaddr&start_address=testaddr&end_position=testaddr&end_address=testaddr&flight_number=0&is_asap=1&app_version=iWeidao/6.2.5 D/877035&media_id=1&sms=passenger&time_span=0&has_custom_decision=1&is_need_manual_dispatch=0&is_auto_dispatch=1&estimate_price=0&device_id=0&corporate_dept_id=0&estimate_price=100.0&estimate_info=D123,T3700&flag=2&create_order_longitude=36.9022&create_order_latitude=36.9022&ip=10.1.7.202&order_port=60428&dispatch_type=2&time_length=1800";
                String response = HttpUtils.getIntance().doPSFRequest("order");
                System.out.println("----" + response);
                jsonObj = JSONObject.fromString(response);
                orderId = jsonObj.getJSONObject("result").getString("service_order_id");
                HttpUtils.getIntance().request.data = "";
                String params = "order_id=" + orderId + "&out_coord_type=baidu&filter_driver_ids=0&count=5";
                System.out.println("orderid:" + params);
                HttpUtils.getIntance().request.service_uri = "Dispatch/getAcceptCars?" + params;
                for (int j = 0; j < 3; j++) {
                    Thread.sleep(3000);
                    response = HttpUtils.getIntance().doPSFRequest("dispatch");
                    System.out.println(response.toString());
                    jsonObj = JSONObject.fromString(response);
                    if (jsonObj.getInt("ret_code") == 498) {
                        break;
                    } else if (jsonObj.getInt("ret_code") == 200) {
                        carNumbr = jsonObj.getJSONArray("car_list").length();
                        System.out.println("carNumbr" + carNumbr);
                        if (carNumbr > 0) {
                            car_list = jsonObj.getJSONArray("car_list");
                            orderParams = new HashMap<String, String>();
                            orderParams.put("order_id", orderId);
                            String car_id = car_list.getJSONObject(0).getString("car_id");
                            ;
                            String driverId = car_list.getJSONObject(0).getString("driver_id");
                            orderParams.put("driverId", driverId);
                            orderParams.put("car_id", car_id);
                            orderParams.put("startTime", startTime);
                            System.out.println("order_id:" + orderId + ",--car_id:" + car_id);
                            HttpUtils.getIntance().request.data = "";
                            String param = "service_order_id=" + orderId + "&driver_id=" + driverId + "&coupon_member_id=0&third_party_coupon=0";
                            System.out.println("userDecision:" + param);
                            HttpUtils.getIntance().request.service_uri = "Dispatch/userDecision?" + param;
                            HttpUtils.getIntance().doPSFRequest("dispatch");
                            acceptRS = true;
                            break;
                        }
                    }
                }
                //如果司机抢单成功，则跳出循环，否则则再次创建订单，重复创建订单3次还抢单抢单失败则报警
                if (acceptRS) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderParams;
    }
}
