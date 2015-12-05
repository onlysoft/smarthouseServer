package cn.onlysoft.smarthouseweb.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class CharacterFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 拦截所有的请求  解决全站中文乱码
		// 指定 request 和 response 的编码
		request.setCharacterEncoding("utf-8");	// 只对消息体有效
		response.setContentType("text/html;charset=utf-8");
		
		// 放行
		// 在放行时  应该给目标资源一个 假的request 让目标资源调用 getParameter时调到我们写的getParameter  
		// 对request进行包装
		CharacterRequest characterRequest = new CharacterRequest(request);
		chain.doFilter(characterRequest, response);

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}

// 针对 request 对象进行包装
// 继承 默认包装类HttpServletRequestWrapper
class CharacterRequest extends HttpServletRequestWrapper {

	public CharacterRequest(HttpServletRequest request) {
		super(request);
	}

	
	// 子类继承父类一定会覆写一些方法
	// 此处  我们要包装  getParamter
	@Override
	public String getParameter(String name) {
		// 调用 被包装对象的getParameter 方法  获得请求参数
		String value = super.getParameter(name);
		if(value==null)
			return null;
		// 判断请求方式
		String method = super.getMethod();
		if("get".equalsIgnoreCase(method)) {
			try {
				value = new String(value.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		// 解决乱码后 返回
		return value;
	}
	
	
}