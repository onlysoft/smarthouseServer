package cn.onlysoft.smarthouseweb.web.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.log4j.Logger;
/**
 * 连接管理类
 */
public class WebSocketConnectionManager {
	private  static Map<String, WebSocketConnection> webSocketMap = null;
	private static WebSocketConnectionManager instance;
	private static Logger logger = Logger.getLogger(WebSocketConnectionManager.class);
	public static WebSocketConnectionManager getInstatnce()
	{
		if(instance==null)
		{
			instance=new WebSocketConnectionManager();
			webSocketMap = new ConcurrentHashMap<String, WebSocketConnection>();
		}
		return instance;
	}
	
	public boolean addConnection(WebSocketConnection socketConnection)
	{
		WebSocketConnection connection = webSocketMap.put(socketConnection.getWorkflowId(), socketConnection);
		if(connection!=null)
		{
			return false;
		}
		logger.info(socketConnection+"成功添加到队列！");
		logger.info("websocket队列"+webSocketMap);
		return true;
	}
	public WebSocketConnection createConnection(String workflowId,MessageInbound inbound)
	{
		return new WebSocketConnection(workflowId, inbound);
	}
	public WebSocketConnection getConnection(String workflowId)
	{
		synchronized (webSocketMap) {
			return webSocketMap.get(workflowId);
		}
	}
	public boolean removeConnection(String workflowId)
	{
		WebSocketConnection socketConnection = webSocketMap.remove(workflowId);
		if(socketConnection!=null)
		{
			logger.info(socketConnection+"成功从队列删除成功！");
			logger.info("websocket队列"+webSocketMap);
//			try {
//				socketConnection.closedConn();
//				logger.info(socketConnection+"关闭成功");
//			} catch (IOException e) {
//				e.printStackTrace();
//				logger.error(socketConnection+"关闭失败");
//				return false;
//			}
			return true;
		}
		
		return false;
	}
}
