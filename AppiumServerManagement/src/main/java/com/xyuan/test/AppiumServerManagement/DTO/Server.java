package com.xyuan.test.AppiumServerManagement.DTO;

public class Server {
	private String pid;
	private String udid;
	private int status;
	private int casenumber;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCasenumber() {
		return casenumber;
	}
	public void setCasenumber(int casenumber) {
		this.casenumber = casenumber;
	}

}
