package cn.onlysoft.smarthouseweb.web.websocket;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class WebSocketServer extends WebSocketServlet {
	private static Logger logger = Logger.getLogger(WebSocketServer.class);
	private Gson gson=new Gson();
	private WebSocketConnectionManager connectionManager=WebSocketConnectionManager.getInstatnce();
	private ToServerSocketManager serverSocketManager=ToServerSocketManager.getserverSocketManager();
	@Override
	protected StreamInbound createWebSocketInbound(String arg0,HttpServletRequest request) {
		return new WebSocketPkgHandler(gson,connectionManager,serverSocketManager);
	}
}
