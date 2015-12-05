package cn.onlysoft.smarthouseweb.server.model;

import java.util.List;

public class LoginOrOutPacket {
	private String gateId;
	private List<Device> deviceList;
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public List<Device> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
	
}
