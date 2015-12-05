package cn.onlysoft.smarthouseweb.server.packethandler;

import cn.onlysoft.smarthouseweb.server.model.LoginOrOutPacket;
import cn.onlysoft.smarthouseweb.server.net.Connection;

public class LoginOrOutPacketHandler {
	private Connection connection;
	public LoginOrOutPacketHandler(Connection connection)
	{
		this.connection=connection;
	}
	public void process(LoginOrOutPacket packet) {
		
	}
}
