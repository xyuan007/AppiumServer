package com.xyuan.test.AppiumServerManagement;

import java.util.ArrayList;
import com.xyuan.test.AppiumServerManagement.DTO.Device;
import com.xyuan.test.AppiumServerManagement.Util.CONSTUtil;
import com.xyuan.test.AppiumServerManagement.Util.MyLog;
import com.xyuan.test.AppiumServerManagement.Util.NodeServerUtil;

public class AppiumServer{
	static MyLog loger = MyLog.getLoger();
	
	public static void main(String[] args) throws Exception{
		AppiumServer.killAllServer();
		int deviceCount = DataManagement.getFreeDeviceCount();
		for(int i=0;i<deviceCount;i++){
			Device device = DataManagement.getFreeDevice();
			AppiumServer.startNewServer(device);
		}
		
		loger.info("start");
		while(true){
			Thread.sleep(CONSTUtil.INTEVAL);
			ArrayList<String> killedServer = AppiumServer.getBeKilledServer();
			if(killedServer != null){
				for(int i=0;i<killedServer.size();i++){
					String pid = killedServer.get(i);
					System.out.println("killed Node:" + pid);
					AppiumServer.killServerByPid(pid);
					Device device = DataManagement.getFreeDevice();
					AppiumServer.startNewServer(device);
				}
			}
		}
	}
	
	//删除所有SERVER
	public static  void killAllServer() throws Exception{
		NodeServerUtil.killAllNode();
		DataManagement.deleteAllServer();
	}
	
	//删除某个SERVER
	public static void killServerByPid(String pid) throws Exception{
		NodeServerUtil.killNodeByPID(pid);
		DataManagement.deleteServerByPID(pid);
	}

	//新起一个SERVER
	public static String startNewServer(Device device) throws Exception{
		String pid = null;
		NodeServerUtil.startServer(device.getPort(), device.getBport(),device.getUdid());
		ArrayList<String> allPid = NodeServerUtil.getAllNodePid();
		ArrayList<String> savedPid = DataManagement.getSavedPids();
		allPid.removeAll(savedPid);
		if(allPid.size() > 0)
			pid = allPid.get(0);
		
		DataManagement.insertServerAndUpdateDevice(pid, device.getUdid());
		return pid;
	}
	
	//待删除SERVER（执行了N个测试用例的SERVER）
	public static ArrayList<String> getBeKilledServer(){
		return DataManagement.getBeKilledPids();
	}
	
}
