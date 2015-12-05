package cn.onlysoft.smarthouseweb.web.websocket;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
/**
 * 到本地服务器socket管理类（为了以后扩展）
 */
public class ToServerSocketManager {
	private static Logger logger = Logger.getLogger(ToServerSocketManager.class);
	private static ToServerSocketManager serverSocketManager;
	protected  IoSession session;
	ExecutorService threadPool = Executors.newCachedThreadPool();
	public static ToServerSocketManager getserverSocketManager() {
		if(serverSocketManager==null)
		{
			serverSocketManager=new ToServerSocketManager();
		}
		return serverSocketManager;
	}
	public  void connecting(final String cmd, final String workFlowId, final WebSocketConnectionManager connectionManager)
	{
		new Thread(){
			public void run() {
				try {
					NioSocketConnector connector = new NioSocketConnector();
					DefaultIoFilterChainBuilder chain = connector.getFilterChain();
					chain.addLast("codec", new ProtocolCodecFilter((ProtocolCodecFactory) new TextLineCodecFactory(Charset.forName("UTF-8"))));
					connector.setHandler(new ToServerIoHandler());
					connector.setConnectTimeout(30);
					ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1",10000));
					cf.awaitUninterruptibly();
					session = cf.getSession();
					session.write(cmd);
					session.getCloseFuture().awaitUninterruptibly();
					connector.dispose();
					logger.info("socket启动成功！");
				} catch (Exception e) {
					logger.error(e);
					connectionManager.getConnection(workFlowId).sendMsg("");//连接不上本地服务器提示用户失败
				}
			};
		}.start();
	}
	public void sendMsg(String msg, String workFlowId, WebSocketConnectionManager connectionManager)
	{
		//TODO 发送失败用户的提示
		threadPool.execute(new WorkThread(msg));
	}
	private class WorkThread extends Thread
	{
		private String pkg;
		
		public WorkThread(String pkg) {
			super();
			this.pkg = pkg;
		}
		@Override
		public void run() {
			super.run();
			if(session==null)
			{
				return;
			}
			synchronized (session) {
				session.write(pkg);
			}
		}
	}
	public boolean  isConnected()
	{
		return session==null?false:true;
	}
	
}
