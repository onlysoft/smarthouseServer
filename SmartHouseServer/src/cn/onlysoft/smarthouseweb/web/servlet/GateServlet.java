package cn.onlysoft.smarthouseweb.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.onlysoft.smarthouseweb.db.dao.UserDao;
import cn.onlysoft.smarthouseweb.db.service.GateService;
import cn.onlysoft.smarthouseweb.db.service.UserService;
import cn.onlysoft.smarthouseweb.domain.Gate;
import cn.onlysoft.smarthouseweb.domain.User;
import cn.onlysoft.smarthouseweb.utils.Md5Utils;

public class GateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if("getAll".equals(method)){
			GateService service=new GateService();
			int page = Integer.valueOf(request.getParameter("page"));
			int rows = Integer.valueOf(request.getParameter("rows"));
			 //当前页  
	        if(page==0){
	        	page=1;
	        }
	        //每页显示条数  
	        if(rows==0)
	        {
	        	rows=10;
	        }
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (page-1)*rows;  
			String allGate = service.findAllGate(Long.valueOf(request.getSession().getAttribute("userId").toString()),start,rows);
			response.getWriter().print(allGate);
		}
		else if("save".equals(method))
		{
			GateService service=new GateService();
			Long gateId = Long.valueOf(request.getParameter("id"));
			boolean b=service.findGate(gateId);
			if(b){
				response.getWriter().print("{\'errorMsg\':\'该设备已激活,请检查您的设备Id\'}");
				return;
			}
			Gate gate=new Gate();
			gate.setName(request.getParameter("name"));
			gate.setId(gateId);
			gate.setUserId(Long.valueOf(request.getSession().getAttribute("userId").toString()));
			gate.setStatus(true);
			boolean saveGate = service.activateGate(gate);
			if(saveGate)
			{
				response.getWriter().print("{success:200}");
			}else {
				response.getWriter().print("{errorMsg:'新建失败,可能是您的网关编号无效，检查后再试，无法解决请联系管理员'}");
			}
		}
		else if("stop".equals(method))
		{
			GateService service=new GateService();
			boolean stop = service.stop(Long.valueOf(request.getParameter("id").toString()));
			if(stop)
			{
				response.getWriter().print("{success:200}");
			}else {
				response.getWriter().print("{errorMsg:状态改变失败}");
			}
		}
		else if("update".equals(method)){
			long id=Long.valueOf(request.getParameter("id"));
			String name = request.getParameter("name");
			GateService service=new GateService();
			boolean b=service.updateGate(id,name);
			if(b){
				response.getWriter().print("{success:200}");
			}else {
				response.getWriter().print("{errorMsg:修改失败}");
			}
		}else if("getAllfromAdmin".equals(method)){
			GateService service=new GateService();
			int page = Integer.valueOf(request.getParameter("page"));
			int rows = Integer.valueOf(request.getParameter("rows"));
			 //当前页  
	        if(page==0){
	        	page=1;
	        }
	        //每页显示条数  
	        if(rows==0)
	        {
	        	rows=10;
	        }
	        //每页的开始记录  第一页为1  第二页为number +1   
	        int start = (page-1)*rows;  
			String allGate = service.findAllGateByAdmin(start,rows);
			response.getWriter().print(allGate);
		}else if("input".equals(method)){
			boolean register=true;
			long userId=Long.valueOf(request.getSession().getAttribute("userId").toString());
			GateService service=new GateService();
			Long gateId = Long.valueOf(request.getParameter("id"));
			boolean b=service.findGate(gateId);
			if(b){
				response.getWriter().print("{\'errorMsg\':\'该设备已激活,请检查您的设备Id\'}");
				return;
			}
			String isCreate = request.getParameter("isCreate");
			System.out.println("-----------"+isCreate);
			boolean is = "on".equals(isCreate);
			if(is)
			{
				UserService userService=new UserService();
				User user=new User();
				userId = System.currentTimeMillis();
				user.setId(userId);
				user.setName(gateId+"");
				user.setPwd(Md5Utils.encrypt(gateId+""));
				user.setType(false);
				register = userService.register(user);
			}
			if(register)
			{
				Gate gate=new Gate();
				gate.setName(request.getParameter("name"));
				gate.setId(gateId);
				gate.setUserId(userId);
				if(is){
					gate.setStatus(true);
				}else {
					gate.setStatus(false);
				}
				boolean saveGate = service.saveGate(gate);
				if(saveGate)
				{
					response.getWriter().print("{success:200}");
				}else {
					response.getWriter().print("{errorMsg:'新建失败,可能是您的网关编号无效，检查后再试，无法解决请联系管理员'}");
				}
			}else {
				response.getWriter().print("{errorMsg:'新建用户失败'}");
			}
			
		}
	}

}
