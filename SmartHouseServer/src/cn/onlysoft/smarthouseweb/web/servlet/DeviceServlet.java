package cn.onlysoft.smarthouseweb.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.onlysoft.smarthouseweb.db.service.DeviceService;
import cn.onlysoft.smarthouseweb.domain.Device;

public class DeviceServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if("online".equals(method)){
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
			DeviceService deviceService=new DeviceService();
			List<Device> devices=deviceService.getOnLine(start,rows);
			Gson gson=new Gson();
			String devicesListStr = gson.toJson(devices);
			response.getWriter().print(devicesListStr);
		}else if ("offline".equals(method)) {
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
			DeviceService deviceService=new DeviceService();
			List<Device> devices=deviceService.getOffLine(start,rows);
			Gson gson=new Gson();
			String devicesListStr = gson.toJson(devices);
			response.getWriter().print(devicesListStr);
		}else if("update".equals(method)){
			DeviceService deviceService=new DeviceService();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			boolean b=deviceService.updateDevice(Long.valueOf(id),name);
			if(b)
			{
				response.getWriter().print("{success:200}");
			}else {
				response.getWriter().print("{errorMsg:'修改失败'}");
			}
		}
	}

}
