package cn.onlysoft.smarthouseweb.web.websocket;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import cn.onlysoft.smarthouseweb.server.model.Packet;
import cn.onlysoft.smarthouseweb.web.websocket.domain.ControlPkg;

import com.google.gson.Gson;

public class ToServerIoHandler extends IoHandlerAdapter {
	private WebSocketConnectionManager connectionManager=WebSocketConnectionManager.getInstatnce();
	private Gson gson=new Gson();
	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String s = (String) message;
		Packet controlPkg = gson.fromJson(s, Packet.class);
		String workFlowId = controlPkg.getWorkFlowId();
		WebSocketConnection connection = connectionManager.getConnection(workFlowId);
		if(connection.sendMsg(s))
		{
			connectionManager.removeConnection(workFlowId);
		}
	}

	@Override
	public void messageSent(IoSession session, Object arg1) throws Exception {
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {

	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1)
			throws Exception {
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}
}
