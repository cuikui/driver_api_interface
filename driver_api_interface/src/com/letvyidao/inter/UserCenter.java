//package com.letvyidao.inter;
//
//import org.testng.Assert;
//import org.testng.annotations.Listeners;
//import org.testng.annotations.Test;
//
//import com.letvyidao.BaseRequest;
//
//public class UserCenter extends BaseRequest{
///*
// * 用户中心:Level 用户级别管理接口\获取单个级别的信息
// * param:level_id MUST 级别ID
// * param:bool $cache ##1## 默认从缓存中获取数据，如果 $cache = false， 直接从DB中获取
// */
// @Test
// public void testGet(){
//	 String url = "http://user.lan.yongche.com/Level/get?level_id=1&cache=false";
//	 Object rs = doSendGet(url);
//	 System.out.println(rs.toString());
//	 if(rs.toString().matches(".*code\":200,.*")){
//		 Assert.assertTrue(true);
//	 }else{
//		 Assert.assertTrue(false, rs.toString());
//	 }
// }
// /*
//  * 用户中心：用户地址信息\获取用户的常用地址数量
//  * int $user_id MUST 用户ID  string $city 城市简码
//  */
// @Test
// public void testGetAddressCount(){
//	String url = "http://user.lan.yongche.com/Address/getAddressCount?user_id=295777&city="; 
//	Object rs = doSendGet(url);
//	if(rs!=null &&rs.toString().matches(".*code\":200,.*")){
//		 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
// }
// /*
//  *用户中心： Contact (用户联系人信息)\获取常用联系人数量
//  */
//@Test
//public void testContactCount(){
//  String url = "http://user.lan.yongche.com/Contact/getContactCount?user_id=3045738";	
//	Object rs = doSendGet(url);
//	if(rs!=null &&rs.toString().matches(".*code\":200,.*")){
//		 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//}
///*
// * 用户中心：Corporate\获取企业名下所有的用户
// */
//@Test
//public void testGetCorpUserList(){
//	String url = "http://user.lan.yongche.com/Corporate/getCorpUserList?corporate_id=1&type=0&user_id=3045738&corporate_dept_id=1&limit=10&offset=11";
//    Object rs = doSendGet(url);
//    if(rs!=null &&rs.toString().matches(".*code\":200,.*")){
//		 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//}
///*
// * 用户中心：User (用户信息)\获得的用户折扣
// *  int $user_id MUST 逗号分隔user_id ##5569##
// */
//@Test
//public void testGetDiscount(){
//	String url = "http://user.lan.yongche.com/User/getDiscount?user_id=5569";
//    Object rs = doSendGet(url);
//    if(rs!=null &&rs.toString().matches(".*code\":200,.*")){
//		 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//}
//
///*
// * 用于中心：CouponMember (用户优惠券)\获取下单后用户可用的优惠券
// */
////@Test
//public void testGetAfterOrderAvailableCouponMemberList(){
//	String url = "http://user.lan.yongche.com/CouponMember/getAfterOrderAvailableCouponMemberList?"+
//    "user_id_list=3045738&product_type_id=1&car_type_id=1&start_time=1459850400&city=bj&platform=1&order_id=2005769433"+
//	"&app_version=&is_face_pay=&passenger_phone=&app_id=&uuid=&device_id=&business_type=&order_coupon_member_id="+
//	"&can_use_coupon=&field_list=restricted_product_type,car_type,city_list,source_list";	
//	 Object rs = doSendGet(url);
//	 System.out.println(rs.toString());
//	 if(rs!=null &&rs.toString().matches(".*code\":200,.*")){
//		 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//}
///*
// *用户中心： User (用户信息)\获取用户的指定的 Key
// */
//@Test
//public void testUserGet(){
//	String url = "http://user.lan.yongche.com/User/get?user_id=3045738&status=0&is_all_level_flag=0&keys=screen_name";
//			System.out.println(url);
//			 Object rs = doSendGet(url);
//			 if(rs!=null &&rs.toString().matches(".*code\":200,.*")){
//				 Assert.assertTrue(true);
//				 }else{
//					 Assert.assertTrue(false, rs.toString());
//				 }	
//}
///*
// * 用户中心： CouponMember (用户优惠券)\获取用户的优惠券的统计信息
// */
//@Test
//public void testGetUserCouponMemberSummary(){
//	String url = "http://user.lan.yongche.com/CouponMember/getUserCouponMemberSummary?user_id=3045738&status=";
//	 Object rs = doSendGet(url);
//	 if(rs!=null &&rs.toString().matches(".*code\":200,.*")){
//		 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//}
///*
// * 用户中心：User (用户信息)\根据 user_id 批量获取用户的基本信息
// */
//@Test
//public void testGetByUserIds(){
//	String url = "http://user.lan.yongche.com/User/getByUserIds?user_ids=3045739,149&keys=";
//	 Object rs = doSendGet(url);
//	 if(rs!=null &&rs.toString().matches(".*code\":200,.*")){
//		 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//}
///*
// * 用户中心：检查企业账户是否可用
// */
//@Test
//public void testCheckUserCorporateActive(){
//	String url = "http://testing.user.yongche.org/Corporate/checkUserCorporateActive?user_ids=3045739";
//	 Object rs = doSendGet(url);
//	 System.out.println(rs.toString());
//	 if(rs!=null &&rs.toString().matches(".*code\":200,.*")){
//		 Assert.assertTrue(true);
//		 }else{
//			 Assert.assertTrue(false, rs.toString());
//		 }
//}
///*
// * USER模块：修改用户指定的Key
// */
//@Test
//public void testUserSet(){
//	String url = "http://testing.user.yongche.org/User/set";
//	
//	
//}
//}
