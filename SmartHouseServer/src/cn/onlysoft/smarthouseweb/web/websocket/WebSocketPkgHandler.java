package cn.onlysoft.smarthouseweb.web.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;

import cn.onlysoft.smarthouseweb.server.model.Packet;
import cn.onlysoft.smarthouseweb.web.websocket.domain.ControlPkg;

import com.google.gson.Gson;

public class WebSocketPkgHandler extends MessageInbound {
	private Gson gson;
	private WebSocketConnectionManager connectionManager;
	private ToServerSocketManager serverSocketManager;
	public WebSocketPkgHandler(Gson gson) {
		this.gson=gson;
	}

	public WebSocketPkgHandler(Gson gson,WebSocketConnectionManager connectionManager,ToServerSocketManager serverSocketManager) {
		this.gson=gson;
		this.connectionManager=connectionManager;
		this.serverSocketManager=serverSocketManager;
	}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {

	}

	@Override
	protected void onTextMessage(CharBuffer charBuffer) throws IOException {
		String cmd = charBuffer.toString();
		Packet pack = gson.fromJson(cmd, Packet.class);
		String workFlowId = pack.getWorkFlowId();
		connectionManager.addConnection(connectionManager.createConnection(workFlowId, this));
		if(!serverSocketManager.isConnected())
		{
			serverSocketManager.connecting(cmd,workFlowId,connectionManager);
		}else {
			serverSocketManager.sendMsg(cmd,workFlowId,connectionManager);
		}
	}

}
