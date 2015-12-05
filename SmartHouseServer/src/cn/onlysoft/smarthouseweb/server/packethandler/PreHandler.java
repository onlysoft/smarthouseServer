package cn.onlysoft.smarthouseweb.server.packethandler;


import cn.onlysoft.smarthouseweb.server.model.ControlPkg;
import cn.onlysoft.smarthouseweb.server.model.LoginOrOutPacket;
import cn.onlysoft.smarthouseweb.server.model.Packet;
import cn.onlysoft.smarthouseweb.server.net.Connection;
import cn.onlysoft.smarthouseweb.server.net.SessionManager;
import cn.onlysoft.smarthouseweb.server.utils.FoundationDate;
import cn.onlysoft.smarthouseweb.server.utils.ObjectPool;

import com.google.gson.Gson;

public class PreHandler {
	private Connection connection;
	private StatusReportPacketHandler statusReportHandler;
	private LoginOrOutPacketHandler loginOrOutPacketHandler;
	private boolean isCreateSession=false;
	private Gson gson;
	public PreHandler(Connection connection)
	{
		this.connection=connection;
		statusReportHandler=new StatusReportPacketHandler(connection);
		loginOrOutPacketHandler=new LoginOrOutPacketHandler(connection);
	}
	public void process(String msg) throws Exception
	{
		gson = ObjectPool.getGsonInstance();
		Packet packet =gson.fromJson(msg, Packet.class);
		int type = packet.getType();
		if(!isCreateSession)
		{
			if(type==2)
			{
				createSession("24");
			}else {
				createSession(packet.getGateId());
			}
		}
		switch (type) {
		case FoundationDate.LOGINOROUTPACKET:
			processLoginOrOutPacket(packet);
			break;
		case 2:
			processWebControl(packet);
			break;
		}
	}
	
	private void processWebControl(Packet packet) {
		connection.sendMsg("{workFlowId:'12',gateId:'0',type:2,content:'cmd'}");
		
	}
	//创建session
	private void createSession(String gateId) {
		if(gateId!=null&&gateId.trim()!="")
		{
			SessionManager.getInstance().createSession(connection, gateId);
		}
	}
	private void processLoginOrOutPacket(Packet packet)
	{
		Gson gson = ObjectPool.getGsonInstance();
		Class clazz = FoundationDate.gettypeAndClassMap().get(FoundationDate.REPORTSTATUSPACKET);
		LoginOrOutPacket loginOrOutPacket = gson.fromJson(packet.getContent(), clazz);
		loginOrOutPacketHandler.process(loginOrOutPacket);
	}
	
}
