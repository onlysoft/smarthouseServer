package cn.onlysoft.smarthouseweb.server.server;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import cn.onlysoft.smarthouseweb.server.utils.Constant;


public class Server {
	private static Server instance;
	private boolean isStart=false;
	private IoAcceptor acceptor;
	private static Logger logger = Logger.getLogger(Server.class);
	private String port;
	private Server(){
		if(acceptor==null)
		{
			acceptor=new NioSocketAcceptor();
		}
	}
	public static Server getInstance()
	{
		if(instance==null)
		{
			instance=new Server();
		}
		return instance;
	}
	public boolean  start()
	{
		if(acceptor==null||isStart)
		{
			return false;
		}
		try {
			acceptor=new NioSocketAcceptor();
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter((ProtocolCodecFactory) new TextLineCodecFactory(Charset.forName("UTF-8"))));
			acceptor.getFilterChain().addLast("exceutor", new ExecutorFilter());
			// 设置读取数据的缓冲区大小
			acceptor.getSessionConfig().setReadBufferSize(2048);
			// 读写通道10秒内无操作进入空闲状态
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);
			try {
				//获得配置的端口号
				FileInputStream inStream = new FileInputStream("config.properties");
				Properties properties = new Properties();
				properties.load(inStream);
				port = properties.getProperty("serverport", String.valueOf(Constant.DEFAULTPORT));
			} catch (Exception e) {
				port=String.valueOf(Constant.DEFAULTPORT);
			}
			
			//绑定逻辑处理器
			acceptor.setHandler(new ServerIoHandle());
			// 绑定端口 
			acceptor.bind(new InetSocketAddress(Integer.valueOf(port)));
			isStart=true;
			return true;
		} catch (Exception e) {
			logger.error("服务端启动异常....",e);
			e.printStackTrace();
			return false;
		}
	}
	public void shutdown()
	{
		if(isStart)
		{
			
		}
	}
}
