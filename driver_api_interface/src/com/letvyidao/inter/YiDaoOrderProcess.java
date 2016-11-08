package com.letvyidao.inter;

import com.letvyidao.init.BaseRequest;

public class YiDaoOrderProcess extends BaseRequest{
	 public static void main(String[] args){
//		 PSFClient psfclient =null;
//		 PSFClient.PSFRPCRequestData request = null;
//		 String[] serviceCenter = {"10.0.11.71:5201","10.0.11.72:5201"};
//		String content = "报错方法：com.letvyidao.inter.Order.testOrder_qlcNoCustomDecision报错.用例监控接口：全流程监控，监控流程：创建非用户决策订单（createOrder)-->司机抢单(/Dispatch/driverResponse)-->获取订单状态(45s内每3s循环调用一次order/getStatus)-->取消订单(state/cancel)  接口返回内容及调用信息：order/getStatus获取派单司机列表接口错误，错误信息：{'ret_code':200,'ret_msg':'success','result':{'status':'8','flag':'34361835776','user_id':'13025137','driver_id':'0'}} 创建订单id:6341787521883327650 expected [true] but found [false]";		
		String a = "北京大学";		
		System.out.println(a);
		System.out.println(a.matches("北京大学"));
//		System.out.println(URLDecoder.decode(a));
//		
//		        String [] phones = {"13671126358"};
//    try{
//	   psfclient = new PSFClient(serviceCenter);	
//  	   request = new PSFClient.PSFRPCRequestData(); 
//        request.service_uri="event/sendWarning";
//        for(int i=0;i<phones.length;i++){
//           request.data="{\"CELLPHONE\": \""+phones[i]+"\",\"CONTENT\": \""+(content.replaceAll("\"", "'"))+"\",\"FLAG\": 14,\"__NO_ASSEMBLE\": 1,\"__EVENT_ID__\": 46}";
//           String response = psfclient.call("atm2", request);
//           System.out.println("event/sendWarning:"+response);
//        }
//	    }catch(Exception e){
//	    	e.printStackTrace();
//	    }
}
}
