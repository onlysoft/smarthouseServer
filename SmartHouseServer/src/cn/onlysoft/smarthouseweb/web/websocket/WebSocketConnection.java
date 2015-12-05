package cn.onlysoft.smarthouseweb.web.websocket;

import java.io.IOException;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.log4j.Logger;

public class WebSocketConnection {
	private static Logger logger = Logger.getLogger(WebSocketConnection.class);
	private String workflowId;
	private MessageInbound messageInbound;
	
	public WebSocketConnection() {
		super();
	}
	public WebSocketConnection(String workflowId, MessageInbound messageInbound) {
		super();
		this.workflowId = workflowId;
		this.messageInbound = messageInbound;
	}
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public MessageInbound getMessageInbound() {
		return messageInbound;
	}
	public void setMessageInbound(MessageInbound messageInbound) {
		this.messageInbound = messageInbound;
	}
	public void closedConn() throws IOException
	{
		messageInbound.getWsOutbound().close(0, null);
		logger.info(this+"关闭成功");
	}
	public boolean  sendMsg(String msg)
	{
		CharBuffer buffer = CharBuffer.wrap(msg);
		try {
			messageInbound.getWsOutbound().writeTextMessage(buffer);
			messageInbound.getWsOutbound().flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			try {
				messageInbound.getWsOutbound().close(0, null);
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.error(e1);
			}
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "websocket第"+workflowId;
	}
	
}
