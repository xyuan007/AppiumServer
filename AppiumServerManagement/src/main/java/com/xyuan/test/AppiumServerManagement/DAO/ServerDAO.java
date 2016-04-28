package com.xyuan.test.AppiumServerManagement.DAO;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.xyuan.test.AppiumServerManagement.DTO.Device;

public interface ServerDAO {
	public void insert(@Param("pid")String pid,@Param("udid")String udid);
	
	public void EmptyUDID(@Param("pid")String pid);
	
	public void deleteByPID(@Param("pid")String pid);
	
	public void deleteAllServer();
	
	public void updateStatusRunning(@Param("pid")String pid);
	
	public void updateStatusComplete(@Param("pid")String pid);
	
	public void updateCaseNumByPID(@Param("pid")String pid,@Param("casenum")int casenum);
	
	public String getUDIDByPid(@Param("pid")String pid);
	
	public Device getFreeDevice();
	
	public int getFreeDeviceCount();
	
	public void updateDeviceStatus(@Param("status")int status,@Param("udid")String udid);
	
	public void updateAllDeviceStatus();	
	
	public ArrayList<String> getSavedPids();
	
	public ArrayList<String> getBeKilledPids(@Param("num")int num);
}
