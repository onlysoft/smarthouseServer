package cn.onlysoft.smarthouseweb.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;    
        HttpServletRequest req=(HttpServletRequest)request; 
        HttpSession session = req.getSession(true);  
        String username = (String) session.getAttribute("name");
        String url=req.getRequestURI();  
        if(username==null||username.equals(""))
        {
        	  if(url!=null && !url.equals("") && (url.indexOf("login")<0 && url.indexOf("register")<0))  
              {  
        		  resp.sendRedirect(req.getContextPath()+"/login.jsp");  
                  return ;  
            }             
        }
        chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
