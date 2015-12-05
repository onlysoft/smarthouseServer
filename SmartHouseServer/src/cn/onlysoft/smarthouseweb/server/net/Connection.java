package cn.onlysoft.smarthouseweb.server.net;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

public class Connection {
	private static Logger logger = Logger.getLogger(Connection.class);
	private IoSession ioSession;
	private boolean isClosed;
	private Session session;
	private ConnectionCloseListener closeListener;

	public Connection(IoSession ioSession) {
		this.ioSession = ioSession;
		this.isClosed = false;
	}

	public void close() {
		boolean closedSuccessfully = false;
		synchronized (this) {
			if (!isClosed()) {
				try {
					 //TODO 发送让客户端断开的命令
				} catch (Exception e) {
					// Ignore
				}
				if (session != null) {
					session.setStatus(Session.STATUS_CLOSED);
				}
				ioSession.close(false);
				isClosed = true;
				closedSuccessfully = true;
			}
		}
		if (closedSuccessfully) {
			notifyCloseListeners();
		}
	}

	private void notifyCloseListeners() {
		if (closeListener != null) {
			try {
				closeListener.onConnectionClose(session);
			} catch (Exception e) {
				logger.error("Error notifying listener: " + closeListener, e);
			}
		}

	}

	private boolean isClosed() {
		 if (session == null) {
	            return isClosed;
	        }
		 return session.getStatus() == Session.STATUS_CLOSED;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * Registers a listener for close event notification.
	 * 
	 * @param listener
	 *            the listener to register for close events.
	 */
	public void registerCloseListener(ConnectionCloseListener listener) {
		if (closeListener != null) {
			throw new IllegalStateException("Close listener already configured");
		}
		if (isClosed()) {
			listener.onConnectionClose(session);
		} else {
			closeListener = listener;
		}
	}
	
	public void sendMsg(String msg)
	{
		ioSession.write(msg);
	}

}
