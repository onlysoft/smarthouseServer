package cn.onlysoft.smarthouseweb.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.onlysoft.smarthouseweb.db.service.UserService;
import cn.onlysoft.smarthouseweb.domain.User;
import cn.onlysoft.smarthouseweb.utils.Md5Utils;

public class RegisterServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if("findName".equals(method))
		{
			String name = request.getParameter("username");
			UserService userService=new UserService();
			boolean b = userService.findUser(name);
			if(b)
			{
				response.getWriter().print("0,用户名已存在");
			}else {
				response.getWriter().print("1,用户名可用");
			}
		}else
		{
			String name = request.getParameter("usernamesignup");
			String email = request.getParameter("emailsignup");
			String pwd = request.getParameter("passwordsignup");
			pwd=Md5Utils.encrypt(pwd);
			long userId = System.currentTimeMillis();
			User user=new User(userId, name, pwd, email);
			UserService userService=new UserService();
			boolean register = userService.register(user);
			if(register)
			{
				request.getSession().setAttribute("name",name);
				request.getSession().setAttribute("userId",userId);
				request.getSession().setAttribute("type",user.isType());
				response.sendRedirect(request.getContextPath()+"/main.jsp");
			}else {
				request.getSession().setAttribute("user",user);
				response.sendRedirect(request.getContextPath()+"/login.jsp#toregister");
			}
		}
	}

}
