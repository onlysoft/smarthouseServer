package cn.onlysoft.smarthouseweb.db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import cn.onlysoft.smarthouseweb.db.dao.GateDao;
import cn.onlysoft.smarthouseweb.domain.Gate;

public class GateService {
	private GateDao gateDao;
	public GateService() {
		this.gateDao = new GateDao();
	}
	public String findAllGate(long userId, int page, int rows){
		List<Gate> list = gateDao.getAllByUserId(userId,page,rows);
		long allCount = gateDao.getAllCountByUserId(userId);
		Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
        jsonMap.put("total", allCount);//total键 存放总记录数，必须的  
        jsonMap.put("rows", list);//rows键 存放每页记录 list  
		Gson gson=new Gson();
		return gson.toJson(jsonMap);
	}
	public boolean saveGate(Gate gate) {
		return gateDao.addGate(gate)>0?true:false;
	}
	public boolean stop(Long id) {
		return gateDao.updateGateStatus(id)>0?true:false;
	}
	public boolean updateGate(long id, String name) {
		return gateDao.updateGate(id,name)>0?true:false;
	}
	public boolean findGate(Long gateId) {
		return gateDao.findGateById(gateId)>0?true:false;
	}
	public boolean activateGate(Gate gate) {
		return gateDao.activateGateById(gate)>0?true:false;
	}
	public String findAllGateByAdmin(int start, int rows) {
		List<Gate> list = gateDao.getAll(start,rows);
		long allCount = gateDao.getAllCount();
		Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
        jsonMap.put("total", allCount);//total键 存放总记录数，必须的  
        jsonMap.put("rows", list);//rows键 存放每页记录 list  
		Gson gson=new Gson();
		return gson.toJson(jsonMap);
	}
}
