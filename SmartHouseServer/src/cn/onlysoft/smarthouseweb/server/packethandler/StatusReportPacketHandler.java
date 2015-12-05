package cn.onlysoft.smarthouseweb.server.packethandler;

import cn.onlysoft.smarthouseweb.server.model.StatusReportPacket;
import cn.onlysoft.smarthouseweb.server.net.Connection;

public class StatusReportPacketHandler {
	private Connection connection;
	public StatusReportPacketHandler(Connection connection)
	{
		this.connection=connection;
	}
	public void process(StatusReportPacket message)
	{
		
	}
}
