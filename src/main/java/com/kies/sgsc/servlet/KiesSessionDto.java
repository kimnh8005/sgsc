package com.kies.sgsc.servlet;

import java.util.List;
import java.util.Map;

public class KiesSessionDto {

	private long loginTime;
	private long lastAccessTime;
	private String token;
	private Map data;  //사용자정보
	private String deptId; //소속
	private String device; //접속 장비
	
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	public long getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptid) {
		this.deptId = deptid;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getUser_id(KiesSessionDto dto) {
		return (String)dto.getData().get("user_id");
	}
	
	public String getDept_id(KiesSessionDto dto) {
		return (String)dto.getData().get("dept_id");
	}
	
}
