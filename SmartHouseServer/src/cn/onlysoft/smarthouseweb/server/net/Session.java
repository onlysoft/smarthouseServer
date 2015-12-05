package cn.onlysoft.smarthouseweb.server.net;

public class Session {
	/**
	 * The session status when closed
	 */
	public static final int STATUS_CLOSED = 0;

	/**
	 * The session status when connected
	 */
	public static final int STATUS_CONNECTED = 1;
	private Connection connection;
	private String address;
	private int status;

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Session(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Close the session including associated socket connection.
	 */
	public void close() {
		if (connection != null) {
			connection.close();
		}
	}

}
