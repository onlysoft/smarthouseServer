package cn.onlysoft.smarthouseweb.server.console;

import org.apache.log4j.Logger;

import cn.onlysoft.smarthouseweb.server.server.Server;


public class Console {
	private static Logger logger = Logger.getLogger(Server.class);
	public static void main(String[] args) {
		Server server = Server.getInstance();
		if(server.start())
		{
			logger.info("服务端启动成功！");
		}else {
			logger.info("服务端启动失败！");
		}
	}
}
