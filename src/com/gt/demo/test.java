package com.gt.demo;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.gintong.android.utils.*;

public class test {

//	public static String host="www.gintong.com";
	
	//线上
//	public static String host="jtmobile.gintong.com";
	//仿真
	public static String host="test.online.gintong.com";
	
	
	 public static void main(String[] args) throws InterruptedException {  
		 
//		 获取sessionid
//		 String  username="18664900944";
//		 String  password="MTIxMjEy";
		 String text="测试输入：我正在使用金桐app，一款集商务社交、投融资项目对接、个人资源管理的商务应用神器！ 推荐给你，快来哦";
		 
		 String  username="44235475@qq.com";
		 
//		 String  password="MTExMTExMQ==";
		 
		 //仿真
		 String  password="MjIyMjIy";
		 
		 
		
		 http ht=new http();
		 ht.setProxyflag(true);
		 ht.setUsername(username);
		 ht.setPassword(password);
		 ht.setHost(host);
		 ht.setKnowledgeInfoUrl("http://test.online.gintong.com/cross/knowledge/fetchExternalKnowledgeUrl.json");
		
		 String url="http://news.ifeng.com/a/20160525/48841974_0.shtml";
		 JSONObject userInfoJson=ht.getUserInfo();

		 for(int i=1;i<200;i++){
			 
			 Date now = new Date();
			 SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			 String timeString=time.format(now);
			 String sendtext="发送时间"+timeString+"正在发送第";
			 System.out.println(sendtext+i+"条....................");
			 
			 ht.sendText(userInfoJson, "12103", sendtext+i+"条："+text);
//			 ht.sendText(ht.getUserInfo(), "12103", url);
			 Thread.sleep(5);
			
			 ht.sendKnowledge(userInfoJson, "12103", url);
			 Thread.sleep(5);
			 System.out.println("休息500ms......................");
		 }	 
		 
		
		 
	 }
}
