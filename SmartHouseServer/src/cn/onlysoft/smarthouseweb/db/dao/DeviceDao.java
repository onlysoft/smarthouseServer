package cn.onlysoft.smarthouseweb.db.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.onlysoft.smarthouseweb.domain.Device;
import cn.onlysoft.smarthouseweb.domain.Gate;
import cn.onlysoft.smarthouseweb.utils.JdbcUtils;

public class DeviceDao {

	public List<Device> findDeviceByStatus(boolean b, int start, int rows) {
		List<Device> devices=null;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from devices where status=? limit ?,?";
			devices = (List<Device>) runner.query(sql, new BeanListHandler(Device.class),new Object[]{b,start,rows});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return devices;
	}

	public int updateById(long id, String name) {
		int num=0;
		try {
			QueryRunner runner=new QueryRunner(JdbcUtils.getDataSource());
			String sql="update devices set name=? where id=?";
			num=runner.update(sql, new Object[]{name,id});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return num;
	}


}
