package com.letvyidao.utils;


public class DriverFactory {
	public DriverInfo driver;
	/**
	 * 获取离线司机信息ios
	 *
	 * @return
	 */

	public DriverInfo getOffLineDriver_ios() {
		driver = new DriverInfo("16810531947", "嘀嗒");
		driver.vehicle_number = "0000";
		driver.driverAppVersion = "95";
		return driver;

	}
	/**
	 * 获取离线司机信息 android
	 *
	 * @return
	 */
	public DriverInfo getOffLineDriver_android() {
//线上账号
//		 driver = new DriverInfo("16811122233", "嘀嗒");
//		 driver.vehicle_number = "3458";
//线上其他接口司机端账号：
		 driver = new DriverInfo("16868680994", "嘀嗒");
		 driver.vehicle_number = "0995";
//测试环境账号
//		driver = new DriverInfo("16820160990", "钢镚");
//		driver.vehicle_number = "9467";
		driver.driverAppVersion = "95";
		return driver;

	}

	public DriverInfo getOffLineDriver_android(String phone,String vehicle_number) {
		//线上其他接口司机端账号：
				driver = new DriverInfo(phone, "嘀嗒");
				driver.vehicle_number = vehicle_number;
				driver.driverAppVersion = "95";
				return driver;

			}
	/**
	 * 获取离线司机信息及imei信息
	 *
	 * @return
	 */
	public DriverInfo getOffLineDriverWithImei() {
		this.getOffLineDriver_android();

		// driver.imei = "f65a29c133d63d758e809e534c655963";
        driver.imei="yongche14666953816558";
		//driver.imei = "f65a29c133d63d758e809e534c655963wfonlinemonitor";
		return driver;
	}
	/**
	 * 获取离线司机信息及imei信息
	 *
	 * @return
	 */
	public DriverInfo getOffLineDriverWithImei(String imei,String phone,String vehicle_number) {
		this.getOffLineDriver_android(phone,vehicle_number);
        driver.imei=imei;
		return driver;
	}

}
