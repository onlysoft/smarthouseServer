package cn.onlysoft.smarthouseweb.server.utils;

import java.util.HashMap;
import java.util.Map;

import cn.onlysoft.smarthouseweb.server.model.ControlPkg;
import cn.onlysoft.smarthouseweb.server.model.LoginOrOutPacket;
import cn.onlysoft.smarthouseweb.server.model.StatusReportPacket;

public class FoundationDate {
	public static final int REPORTSTATUSPACKET=0;
	public static final int LOGINOROUTPACKET=1;
	public static final int WEBCONTROLPACKET=2;
	public static Map<Integer, Class> typeAndClassMap=null;
	public static Map<Integer, Class> gettypeAndClassMap()
	{
		if(typeAndClassMap==null)
		{
			typeAndClassMap=new HashMap<Integer, Class>();
			typeAndClassMap.put(REPORTSTATUSPACKET, StatusReportPacket.class);
			typeAndClassMap.put(LOGINOROUTPACKET, LoginOrOutPacket.class);
			typeAndClassMap.put(WEBCONTROLPACKET, ControlPkg.class);
		}
		return typeAndClassMap;
	}
}
