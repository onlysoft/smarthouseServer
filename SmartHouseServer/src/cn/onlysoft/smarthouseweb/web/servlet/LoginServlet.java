package cn.onlysoft.smarthouseweb.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.onlysoft.smarthouseweb.db.service.UserService;
import cn.onlysoft.smarthouseweb.domain.User;
import cn.onlysoft.smarthouseweb.utils.Md5Utils;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("username");
		String pwd = request.getParameter("password");
		UserService service=new UserService();
		pwd=Md5Utils.encrypt(pwd);
		User user = service.login(name,pwd);
		if(user!=null)
		{
			request.getSession().setAttribute("name",name);
			request.getSession().setAttribute("userId",user.getId());
			request.getSession().setAttribute("type",user.isType());
			response.sendRedirect(request.getContextPath()+"/main.jsp");
		}else {
			request.getSession().setAttribute("error", "用户名或密码错误，请重试!");
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
