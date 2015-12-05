package cn.onlysoft.smarthouseweb.db.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.onlysoft.smarthouseweb.domain.Gate;
import cn.onlysoft.smarthouseweb.utils.JdbcUtils;

public class GateDao {
	@SuppressWarnings("unchecked")
	public List<Gate> getAllByUserId(long userId, int page, int rows)
	{
		List<Gate> gateList=null;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from gates where userId=? limit ?,?";
			gateList = (List<Gate>) runner.query(sql, new BeanListHandler(Gate.class),new Object[]{userId,page,rows});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return gateList;
	}
	public long getAllCountByUserId(long userId){
		long num=0;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from gates where userId=?";
			num = (Long) runner.query(sql, new ScalarHandler(1),new Object[]{userId});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return num;
	}
	public int addGate(Gate gate) {
		int num = 0;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into gates (id,name,userId,status) values (?,?,?,?)";
			num = runner.update(sql, new Object[] { gate.getId(), gate.getName(), gate.getUserId(),gate.isStatus()});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return num;
	}

	public int updateGateStatus(Long id) {
		int num=0;
		try {
			QueryRunner runner=new QueryRunner(JdbcUtils.getDataSource());
			String sql="update gates set status=ABS(status-1) where id=?";
			num=runner.update(sql, new Object[]{id});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return num;
	}

	public int updateGate(long id, String name) {
		int num=0;
		try {
			QueryRunner runner=new QueryRunner(JdbcUtils.getDataSource());
			String sql="update gates set name=? where id=?";
			num=runner.update(sql, new Object[]{name,id});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return num;
	}
	public long findGateById(Long gateId) {
		long num=0;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from gates where id=? and status=?";
			num = (Long) runner.query(sql, new ScalarHandler(1),new Object[]{gateId,true});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return num;
	}
	public int activateGateById(Gate gate) {
		int num=0;
		try {
			QueryRunner runner=new QueryRunner(JdbcUtils.getDataSource());
			String sql="update gates set name=? ,status=?,userId=? where id=?";
			num=runner.update(sql, new Object[]{gate.getName(),true,gate.getUserId(),gate.getId()});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return num;
	}
	public long getAllCount() {
		long num=0;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select count(*) from gates";
			num = (Long) runner.query(sql, new ScalarHandler(1));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return num;
	}
	public List<Gate> getAll(int start, int rows) {
		List<Gate> gateList=null;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from gates  limit ?,?";
			gateList = (List<Gate>) runner.query(sql, new BeanListHandler(Gate.class),new Object[]{start,rows});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return gateList;
	}
}
