package com.letvyidao.utils;

public class DriverInfo {
    public String cellPhone;
    public String vehicle_number;
    public String driverID;
    public String name;
    public String imei;
    public String accessToken;
    public String driverAppVersion;
    public String os_name;
    public String os_version;
    public String x_auth_mode;
    public String is_zip;
    public String device_type;
    public DriverInfo driverInfo;


    public DriverInfo() {
    }

    public DriverInfo(String cellPhone, String name) {
        this.cellPhone = cellPhone;
        this.name = name;
    }

    public DriverInfo(String cellPhone, String name, String imei) {
        this.cellPhone = cellPhone;
        this.name = name;
    }


    public DriverInfo(String name, String cellPhone, String vehicle_number, String imei, String driverAppVersion) {
        this.name = name;
        this.cellPhone = cellPhone;
        this.vehicle_number = vehicle_number;
        this.imei = imei;
        this.driverAppVersion = driverAppVersion;
    }

    public DriverInfo(String cellPhone, String vehicle_number, String name, String imei, String driverAppVersion, String os_name, String os_version, String x_auth_mode, String is_zip, String device_type) {
        this.cellPhone = cellPhone;
        this.vehicle_number = vehicle_number;
        this.name = name;
        this.imei = imei;
        this.driverAppVersion = driverAppVersion;
        this.os_name = os_name;
        this.os_version = os_version;
        this.x_auth_mode = x_auth_mode;
        this.is_zip = is_zip;
        this.device_type = device_type;
    }


    public DriverInfo getDiverInfoIos() {
        driverInfo = new DriverInfo("16820160309", "易到测试司机16820160309", "0309", "223ee6dccaefdd8e4d96c85a40462931d5fa6019", "207", "samsung-SM-G900F", "6.0.1", "client_auth", "1", "1");
        return driverInfo;
    }

    public DriverInfo getDiverInfoAnd() {
        driverInfo = new DriverInfo("16820160309", "易到测试司机16820160309", "0309", "353222063475100", "207", "samsung-SM-G900F", "6.0.1", "client_auth", "1", "1");
        return driverInfo;
    }


}
