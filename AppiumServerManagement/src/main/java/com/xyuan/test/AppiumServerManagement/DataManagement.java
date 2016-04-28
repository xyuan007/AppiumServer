package com.xyuan.test.AppiumServerManagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.xyuan.test.AppiumServerManagement.DAO.ServerDAO;
import com.xyuan.test.AppiumServerManagement.DTO.Device;
import com.xyuan.test.AppiumServerManagement.Util.CONSTUtil;
import com.xyuan.test.AppiumServerManagement.Util.MyLog;

public class DataManagement {
	static MyLog loger = MyLog.getLoger();
	private static SqlSession session = null;
	private static ServerDAO serverDao = null;
	private static SqlSessionFactory sessionFactory = null;	
	
	static{
//		String resource = CONSTUtil.MYBATIS_CONFIG;
		Reader is;
		try {
			is = new FileReader(System.getProperty("user.dir") + "\\config.xml");
	        sessionFactory = new SqlSessionFactoryBuilder().build(is);
	        session = sessionFactory.openSession();
	        serverDao = session.getMapper(ServerDAO.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void reSession(){
		session.close();
        session = sessionFactory.openSession();
        serverDao = session.getMapper(ServerDAO.class);
	}
	
	public static Device getFreeDevice(){
		Device  device = serverDao.getFreeDevice();
		session.commit();
		reSession();
		return device;
	}
	
	public  static void updateCaseNum(String pid,int casenum){
		serverDao.updateCaseNumByPID(pid, casenum);
		session.commit();
	}
	
	public static void updateStatusRunning(String pid){
		serverDao.updateStatusComplete(pid);
		session.commit();
	}
	
	public static void updateStatusComplete(String pid){
		serverDao.updateStatusComplete(pid);
		session.commit();
	}
	
	public static void insertServerAndUpdateDevice(String pid,String udid){
		serverDao.insert(pid, udid);
		serverDao.updateDeviceStatus(1, udid);
		session.commit();
	}
	
	public static void deleteServerByPID(String pid){
		String udid = serverDao.getUDIDByPid(pid);
		serverDao.deleteByPID(pid);
		serverDao.updateDeviceStatus(0, udid);
		session.commit();
	}
	
	public static void deleteAllServer(){
		serverDao.deleteAllServer();
		serverDao.updateAllDeviceStatus();
		session.commit();
	}
	
	public static String getUDIDByPid(String pid){
		String res = serverDao.getUDIDByPid(pid);
		session.commit();
		reSession();
		return res;
	}
	
	public static void updateDeviceStatus(int status,String udid){
		serverDao.updateDeviceStatus(status, udid);
		session.commit();
	}
	
	public static int getFreeDeviceCount(){
		int res = serverDao.getFreeDeviceCount();
		session.commit();
		reSession();
		return res;
	}
	
	public static ArrayList<String> getSavedPids(){
		ArrayList<String> res = serverDao.getSavedPids();
		session.commit();
		reSession();
		return res;
	}
	
	public static ArrayList<String> getBeKilledPids(){
		ArrayList<String> res =  serverDao.getBeKilledPids(CONSTUtil.KILLED_NUM);
		session.commit();
		reSession();
		return res;
	}
}
