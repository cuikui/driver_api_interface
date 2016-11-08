package com.letvyidao.init;

import java.util.Map;

import com.letvyidao.init.BaseRequest;

import com.letvyidao.utils.DriverInfo;
import com.letvyidao.utils.Driver_APIUtils;
import com.letvyidao.utils.InterfaceUtils;

public class DriverAPIBase extends BaseRequest {
public DriverInfo driver = null;
public   Map<String,String> headerParams =null;
protected Driver_APIUtils driverUtils = null;
public InterfaceUtils interfaceUtils  = null;


public DriverAPIBase(){
	super();	
		driverUtils = new Driver_APIUtils();
		driver = driverUtils.driver;;
		interfaceUtils = driverUtils.interfaceUtils;
        headerParams = driverUtils.headerParams;
}
}
