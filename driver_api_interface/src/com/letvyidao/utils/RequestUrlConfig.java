package com.letvyidao.utils;

public class RequestUrlConfig {

	public String hostName;
	public String path;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public RequestUrlConfig(String path) {
		this.hostName = "http://testing.driver-api.yongche.org";
		this.path = path;
	}

	public RequestUrlConfig(String hostName, String path) {
		this.hostName = hostName;
		this.path = path;
	}

	public String getUrl() {
		return this.hostName + this.path;
	}
}
