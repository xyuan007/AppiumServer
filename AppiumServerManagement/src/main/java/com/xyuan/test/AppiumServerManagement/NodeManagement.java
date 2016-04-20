package com.xyuan.test.AppiumServerManagement;

import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.xyuan.test.AppiumServerManagement.DAO.ServerDAO;

public class NodeManagement {
	//UDID和PID的对应关�?
	private static volatile HashMap<String,String> pidDevMap = new HashMap<String,String>();
	private static ArrayList<String> deviceList = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception{
		String resource = "E:\\eclipse\\workspace\\AppiumServerManagement\\src\\config.xml";
//		Reader  is = Resources.getResourceAsReader(resource);
		Reader is = new FileReader(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//        sessionFactory.getConfiguration().addMapper(ServerDAO.class);
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        
        ServerDAO serverDao = session.getMapper(ServerDAO.class);
        
        serverDao.updateStatusComplete("2222");
        session.commit();
//        System.out.println(serverDao.getUDIDByPid("1111"));
        
	}

	public static synchronized void createAppiumServer(){
		
	}
}
