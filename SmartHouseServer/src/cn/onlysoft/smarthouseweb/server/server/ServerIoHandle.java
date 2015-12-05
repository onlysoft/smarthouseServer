package cn.onlysoft.smarthouseweb.server.server;


import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import cn.onlysoft.smarthouseweb.server.net.Connection;
import cn.onlysoft.smarthouseweb.server.packethandler.PreHandler;

public class ServerIoHandle extends IoHandlerAdapter {
	private static Logger logger = Logger.getLogger(ServerIoHandle.class);
	private static final String CONNECTION = "CONNECTION";
	private static final String PRE_HANDLER = "PRE_HANDLER";
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		logger.info("客户端与服务器端连接创建");
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		logger.info("客户端与服务器端连接打开");
		Connection connection=new Connection(session);
		session.setAttribute(CONNECTION, connection);
		session.setAttribute(PRE_HANDLER,new PreHandler(connection));
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		logger.info("接收到客户端消息"+message);
		try {
			PreHandler preHandler = (PreHandler) session.getAttribute(PRE_HANDLER);
			preHandler.process(message.toString());
		} catch (Exception e) {
			logger.error("Closing connection due to error while processing message: "+ message, e);
			 Connection connection = (Connection) session.getAttribute(CONNECTION);
	         connection.close();
		}
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		logger.info("数据发送成功"+message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		logger.info("客户端与服务器端连接断开");
	}


	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
//		logger.info("客户端与服务器端连接空闲"+session.getIdleCount(status));
		
	}

	
}
