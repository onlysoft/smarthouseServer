package cn.onlysoft.smarthouseweb.server.utils;



import com.google.gson.Gson;

public class ObjectPool {
	private static Gson gson;
	public static Gson getGsonInstance()
	{
		if(gson==null)
		{
			gson=new Gson();
		}
		return gson;
	}
}
