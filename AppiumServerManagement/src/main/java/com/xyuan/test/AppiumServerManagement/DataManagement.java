package com.xyuan.test.AppiumServerManagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.xyuan.test.AppiumServerManagement.DAO.ServerDAO;
import com.xyuan.test.AppiumServerManagement.DTO.Device;
import com.xyuan.test.AppiumServerManagement.Util.CONSTUtil;

public class DataManagement {

	private static SqlSession session = null;
	private static ServerDAO serverDao = null;
	
	public static void main(String[] args){
		ArrayList<String> res = DataManagement.getBeKilledPids();
		
		
		System.out.println(res.get(0));
	}
	
	static{
		String resource = CONSTUtil.MYBATIS_CONFIG;
		Reader is;
		try {
			is = new FileReader(resource);
	        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
	        session = sessionFactory.openSession();
	        serverDao = session.getMapper(ServerDAO.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized Device getFreeDevice(){
		Device  device = serverDao.getFreeDevice();
		session.commit();
		return device;
	}
	
	public  static synchronized void updateCaseNum(String pid,int casenum){
		serverDao.updateCaseNumByPID(pid, casenum);
		session.commit();
	}
	
	public static synchronized void updateStatusRunning(String pid){
		serverDao.updateStatusComplete(pid);
		session.commit();
	}
	
	public static synchronized void updateStatusComplete(String pid){
		serverDao.updateStatusComplete(pid);
		session.commit();
	}
	
	public static synchronized void insertServerAndUpdateDevice(String pid,String udid){
		serverDao.insert(pid, udid);
		serverDao.updateDeviceStatus(1, udid);
		session.commit();
	}
	
	public static synchronized void deleteServerByPID(String pid){
		String udid = serverDao.getUDIDByPid(pid);
		serverDao.deleteByPID(pid);
		serverDao.updateDeviceStatus(0, udid);
		session.commit();
	}
	
	public static synchronized void deleteAllServer(){
		serverDao.deleteAllServer();
		serverDao.updateAllDeviceStatus();
		session.commit();
	}
	
	public static synchronized String getUDIDByPid(String pid){
		String res = serverDao.getUDIDByPid(pid);
		session.commit();
		return res;
	}
	
	public static synchronized void updateDeviceStatus(int status,String udid){
		serverDao.updateDeviceStatus(status, udid);
		session.commit();
	}
	
	public static synchronized int getFreeDeviceCount(){
		int res = serverDao.getFreeDeviceCount();
		session.commit();
		return res;
	}
	
	public static synchronized ArrayList<String> getSavedPids(){
		ArrayList<String> res = serverDao.getSavedPids();
		session.commit();
		return res;
	}
	
	public static synchronized ArrayList<String> getBeKilledPids(){
		ArrayList<String> res =  serverDao.getBeKilledPids(CONSTUtil.KILLED_NUM);
		session.commit();
		return res;
	}
}
