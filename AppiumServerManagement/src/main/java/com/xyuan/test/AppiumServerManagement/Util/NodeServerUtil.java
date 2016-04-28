package com.xyuan.test.AppiumServerManagement.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeServerUtil {
	//启动一个APPIUM的SERVER
	public static synchronized void startServer(String port,String bport,String udid) throws Exception{
		String file = "log" + String.valueOf((new Date()).getTime());
		String cmd = String.format("cmd /c \"\"%s\" \"%s\" -p %s -bp %s -U %s > log\\%s.txt\"",CONSTUtil.INSTALLPATH_NODE,CONSTUtil.INSTALLPATH_APPIUM, port,bport,udid,file);
		System.out.println(cmd);
		Process proc = Runtime.getRuntime().exec(cmd);
		Thread.sleep(10000);
	}
	
	public static void killAllNode() throws Exception{
		int runNum = 0;
		String cmd = "cmd /c \"taskkill /F /IM node.exe\"";
		Process proc = Runtime.getRuntime().exec(cmd);
		while(proc.waitFor() != 0 && runNum++ < 3){
			Thread.sleep(2000);
			proc = Runtime.getRuntime().exec(cmd);
		}
		Thread.sleep(2000);
	}
	
	public static void killNodeByPID(String pid) throws Exception{
		int runNum = 0;
		String cmd = "cmd /c \"taskkill /F /PID " + pid + "\"";
		if(pid == null)
			return;
		
		Process proc = Runtime.getRuntime().exec(cmd);
		while(proc.waitFor() != 0 && runNum++ < 3){
			Thread.sleep(2000);
			proc = Runtime.getRuntime().exec(cmd);
		}
		Thread.sleep(2000);
	}
	

	//获得所有的NODE的PID
	public static  ArrayList<String> getAllNodePid() throws IOException{
		ArrayList<String> res = null;
		BufferedReader reader = null;
		try{
			res = new ArrayList<String>();
			Process p = Runtime.getRuntime().exec("cmd /c \"tasklist -fi \"imagename eq node.exe\"\"");
			reader = new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
			String line = null;
			while((line = reader.readLine())!=null){
				if(line.contains("没有运行的任务匹配指定标准")){
					return null;
				}
				else{
					if(line.contains("node.exe")){
						Pattern pattern = Pattern.compile(".*node.exe(.*)Console.*"); 
						Matcher m = pattern.matcher(line); 
						if(m.find())
							res.add(m.group(1).trim());
					}
				}
			}
		}catch(Exception e){}
		finally{reader.close();}

		return res;
	}
	
}
