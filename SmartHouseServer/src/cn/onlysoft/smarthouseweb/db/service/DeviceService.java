package cn.onlysoft.smarthouseweb.db.service;

import java.util.List;

import cn.onlysoft.smarthouseweb.db.dao.DeviceDao;
import cn.onlysoft.smarthouseweb.domain.Device;

public class DeviceService {
	private DeviceDao deviceDao;
	public DeviceService(){
		this.deviceDao=new DeviceDao();
	}
	public List<Device> getOnLine(int start, int rows) {
		return deviceDao.findDeviceByStatus(true,start,rows);
	}
	public List<Device> getOffLine(int start, int rows) {
		return deviceDao.findDeviceByStatus(false,start,rows);
	}
	public boolean updateDevice(long id, String name) {
		return deviceDao.updateById(id,name)>0?true:false;
	}
	

}
