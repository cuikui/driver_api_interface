package com.letvyidao.init;

import java.io.FileInputStream;

public class Constant {
    
    private boolean is_online=true;
    private  String  api;
	private static String  online_api = "http://driver-api.yongche.com";
	private static String  test_api = "http://testing.driver-api.yongche.org";

    public Constant() {
        api=is_online?test_api:online_api;
    }

    private static String lbsprifix = "http://10.0.11.239:9978";
    private static String lbsprifix1 = "http://10.0.11.239:9977";
    private static String lbsprifix2 = "http://10.0.11.239:9979";
    private static String lbsprifix4 = "http://10.0.11.239:9976";
    public String lbs_around=lbsprifix+"/api/v1/around";
    public String lbs_place=lbsprifix1+"/api/v1/place";
    public String lbs_suggest=lbsprifix1+"/api/v1/suggest";
    public String lbs_rgeo=lbsprifix2+"/api/v1/rgeo/rgeo";
    public String lbs_estimate=lbsprifix4+"/api/v1/estimate/dispatch/order";


    //登陆相关接口
    public static String api_InitInfo = test_api+"/Global/InitInfo";
	public static String api_CreateAuthCode = test_api+"/V6/CarMaster/CreateAuthCode";
	public static String api_Login = test_api+"/V6/CarMaster/Login";
	public static String api_GetSplash = test_api+"/V6/CarMaster/GetSplash";
	public static String api_GetPopScreen = test_api+"/V6/CarMaster/GetPopScreen";
	public static String api_GetHomePage = test_api+"/V6/CarMaster/GetHomePage";
	public static String api_GetConfigure = test_api+"/V2/Global/GetConfigure";
	public static String api_CurrentVersion = test_api+"/global/currentVersion";
	public static String api_VerifyCooperaStatus = test_api+"/Driver/VerifyCooperaStatus";
    public static String api_AccessToken = test_api+"/oauth/accessToken";
    public static String api_GetIndex = test_api+"/V5/Driver/GetIndex";
    public static String api_Member = test_api+"/driver/member";


    //司机信息
    public static String api_GetDriverDayOrder = test_api+"/V1/Driver/GetDriverDayOrder";
    public static String api_GetDriverTodayData = test_api+"/V1/Driver/GetDriverTodayData";
    public static String api_MemberStat = test_api+"/driver/memberStat";
    public static String api_GetContributionOrder = test_api+"/Driver/GetContributionOrder";
    public static String api_GetDriverLevelInfor = test_api+"/V4/Driver/GetDriverLevelInfo";
    public static String api_GetDriverLevelDetailMonth = test_api+"/V4/Driver/getDriverLevelDetailMonth";
    public static String api_GetDriverLevelDetailDay = test_api+"/V4/Driver/getDriverLevelDetailDay";
    public static String api_GetDriverEvalute = test_api+"/V1/Driver/GetDriverEvalute";


    //司机提现
    public static String api_GetDriverAccount = test_api+"/v4/Driver/GetDriverAccount";
    public static String api_GetDriverAccountDetail = test_api+"/V2/Driver/getDriverAccountDetail";
    public static String api_GetWithdrawCashBank = test_api+"/v4/Driver/GetWithdrawCashBank";
    public static String api_SetBankCard = test_api+"/v4/Driver/SetBankCard";
    public static String api_GetInBalanceList = test_api+"/V4/Driver/GetInBalanceList";


    //订单信息
    public static String api_GetOrderList = test_api+"/v4/order/getOrderList";
    public static String api_GetEstimatePrice = test_api+"/Order/GetEstimatePrice";
    public static String api_GetItemOrder = test_api+"/order/getItemOrder";
    public static String api_OperateOrder_bill_confirm = test_api+"/order/operateOrder";
    public static String api_OperateOrder_accept = test_api+"/order/operateOrder";
    public static String api_OperateOrder_depart = test_api+"/order/operateOrder";
    public static String api_OperateOrder_start = test_api+"/order/operateOrder";
    public static String api_OperateOrder_end = test_api+"/order/operateOrder";
    public static String api_OperateOrder_decline = test_api+"/order/operateOrder";
    public static String api_OperateOrder_calculateOrder = test_api+"/V2/Order/CalculateOrder";
    public static String api_OperateOrder_getDecisionStatus = test_api+"/order/GetDecisionStatus";
    public static String api_GetDispatchContent = test_api+"/v4/Driver/GetDispatchContent";
    public static String api_ChangeDispatch = test_api+"/v4/Driver/ChangeDispatch";
    public static String api_SendMessage = test_api+"/driver/SendMessage";





	public static String appdriver_VerifyCooperaStatus= test_api+"/Driver/VerifyCooperaStatus";
    public static String Api_Get_GetAppealContent = test_api+"/v4/Driver/GetAppealContent";
	public static String Api_Post_Appeal = test_api+"/v4/Driver/Appeal";
	public static String V6Login= test_api+"/V6/CarMaster/Login";
	public static String appdriver_accessToken=test_api+"/oauth/accessToken";
	public static String appdriver_CreateDriverPassword=test_api+"/Driver/CreateDriverPassword";
	public static String appdriver_CurrentVersion=test_api+"/Global/CurrentVersion";
	public static String appdriver_DriverMemberStat=test_api+"/Driver/MemberStat";
	public static String appdriver_DriverUnbind=test_api+"/Driver/Unbind";
	public static String appdriver_DriverGetIndex=test_api+"/V5/Driver/GetIndex";
	public static String appdriver_OrderGetItemOrder=test_api+"/Order/GetItemOrder";
	public static String appdriver_GetDriverIncomeOrder=test_api+"/Driver/GetDriverIncomeOrder";
	public static String appdriver_GetDriverIncome=test_api+"/Driver/GetDriverIncome";
	public static String appdriver_GetContributionOrder=test_api+"/Driver/GetContributionOrder";
	public static String appdriver_GetDriverAccount=test_api+"/v4/Driver/GetDriverAccount";
	public static String appdriver_member=test_api+"/driver/member";
	public static String appdriver_GetDriverLevelInfo=test_api+"/V4/Driver/GetDriverLevelInfo";
	public static String appdriver_getDriverLevelDetailMonth=test_api+"/V4/Driver/getDriverLevelDetailMonth";
	public static String appdriver_getDriverLevelDetailDay=test_api+"/V4/Driver/getDriverLevelDetailDay";
	public static String appdriver_WithdrawDriverCash=test_api+"/Driver/WithdrawDriverCash";
	public static String appdriver_GetDriverEvalute=test_api+"/V1/Driver/GetDriverEvalute";
	public static String appdriver_GetLowCarType=test_api+"/V4/Driver/GetLowCarType";
	public static String appdriver_SetCarType=test_api+"/v4/Driver/SetCarType";
	public static String appdriver_GetInBalanceList=test_api+"/V4/Driver/GetInBalanceList";
	public static String appdriver_GetDriverAccountDetail=test_api+"/V2/Driver/GetDriverAccountDetail";

	

}
