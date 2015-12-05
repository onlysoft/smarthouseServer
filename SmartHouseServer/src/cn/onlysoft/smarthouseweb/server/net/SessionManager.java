package cn.onlysoft.smarthouseweb.server.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


public class SessionManager {
	private static Logger logger = Logger.getLogger(SessionManager.class);
	private static SessionManager sessionManager;
	// 这个地方采用了ConcurrentHashMap 具体原因参见http://marlonyao.iteye.com/blog/344876
	private Map<String, Session> clientSessions = new ConcurrentHashMap<String, Session>();
	private ClientSessionListener clientSessionListener = new ClientSessionListener();

	public static SessionManager getInstance() {
		if (sessionManager == null) {
			synchronized (SessionManager.class) {
				sessionManager = new SessionManager();
			}
		}
		return sessionManager;
	}

	public Session createSession(Connection connection, String address) {
		Session session = new Session(connection);
		session.setAddress(address);
		connection.setSession(session);
		connection.registerCloseListener(clientSessionListener);
		//新建完直接加入
		addSession(session);
		return session;
	}

	/**
	 * A listner to handle a session that has been closed.
	 */
	private class ClientSessionListener implements ConnectionCloseListener {

		public void onConnectionClose(Object handback) {
			try {
				Session session = (Session) handback;
				removeSession(session);
			} catch (Exception e) {
				logger.error("Could not close socket", e);
			}
		}
	}

	public boolean removeSession(Session session) {
		   if (session == null ) {
	            return false;
	        }
	        String gateId = session.getAddress();
	        boolean clientRemoved = clientSessions.remove(gateId) != null;
	        return clientRemoved;
	}
	
	/**
     * Adds a new session 
     *  
     * @param session the session
     */
    public void addSession(Session session) {
    	if(session==null)
    	{
    		return;
    	}
        clientSessions.put(session.getAddress(), session);
    }
	
	/**
     * Returns the session associated with the gateID.
     * 
     */
    public Session getSession(String gateId) {
        if (gateId == null) {
            return null;
        }
        return clientSessions.get(gateId);
    }
    

}
