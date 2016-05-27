package com.gintong.android.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;  

@SuppressWarnings("deprecation")
public class http {
	
	private HttpResponse response;	
	private String username="";
	private String password="";
	private String host="";
	private String sessionID="";
	private String knowledgeInfoUrl="";
	private boolean proxyflag=false;
	private long startTime = 0L;  
	
	
	
	public void setUsername(String username){
		this.username=username;		
	}	
	
	public String getUsername(){		
		return this.username;		
	}
	
	
	public void setPassword(String password){
		this.password=password;		
	}	
	
	public String getPassword(){		
		return this.password;		
	}
	
	public void setHost(String host){
		this.host=host;		
	}	
	
	public String getHost(){		
		return this.host;		
	}
	

	public  boolean setProxyflag(boolean flag)
	{
		proxyflag=flag;
		return proxyflag;		
	}
	
	
	
	@SuppressWarnings({ "resource" })
	public String sendText(JSONObject userInfoJson,String mucID,String text){
		
//		初始化
		String Response="";	
		HttpClient httpClient = new DefaultHttpClient(); 
//		HttpPost method = new HttpPost("http://jtim.gintong.com:4446/mobile/im/sendMessage.action"); 
		HttpPost method = new HttpPost("http://test.online.gintong.com/crossServerNIM//mobile/im/sendMessage.action"); 
//		set header
		
		method.setHeader("sessionID", userInfoJson.getString("sessionID"));
		method.setHeader("jtUserID", userInfoJson.getString("id"));
		method.setHeader("Host", "jtim.gintong.com:4446");
		method.setHeader("Connection", "Keep-Alive");
		method.setHeader("Content-Type", "text/plain; charset=UTF-8");
		method.setHeader("Accept-Language", "zh,zh-cn");
		
//		System.out.println("初始化信息发送的请求...");
      		
//		Header[] headers1 = method.getAllHeaders();
//		  for (Header h : headers1)
//              System.out.println("head   :"+h.getName() + "------------ " + h.getValue());		
		  
		Date now = new Date();
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    JsonArray arry = new JsonArray(); 
        JsonObject bodyjson = new JsonObject();  
        
//        bodyjson.addProperty("ats", "[]"); 
        bodyjson.addProperty("fromIndex",""); 
        bodyjson.addProperty("fromTime", time.format(now)); 
        bodyjson.addProperty("messageID", getRandomString.genMessageID(userInfoJson.getString("id"))); 
        bodyjson.addProperty("mucID", mucID); 
        bodyjson.addProperty("senderName", userInfoJson.getString("name")); 
        bodyjson.addProperty("text", text); 
        bodyjson.addProperty("type", "0"); 

//        System.out.println(bodyjson);
        
       
        arry.add(bodyjson);  
       String body=arry.toString();
       if(proxyflag){  	    	 
			HttpHost proxy = new HttpHost("127.0.0.1", 8888);  	
   	    method.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);   
   	    }  
		
       System.out.println("发送信息...");
		try{ 	 
	    	   
			method.setEntity(new StringEntity(body.substring(1,body.length()-1), Charset.forName("UTF-8")));  
    	    startTime = System.currentTimeMillis();
//    	    System.out.println(startTime);       	 
    	   
    	    response = httpClient.execute(method);      	   
            Response = EntityUtils.toString(response.getEntity());
//            System.out.println("发送信息返回   Response:"+Response+"\r\n");       	
            }catch(Exception e){
            	System.out.println("err2:-------------"+e.toString());
			    e.printStackTrace();     
			    }	
//		 System.out.println("信息已发送...");
		return Response;		
	}
	

	@SuppressWarnings({ "resource" })
	public String sendKnowledge(JSONObject userInfoJson,String mucID,String externalUrl){
		
//		初始化
	
		String Response="";	
		HttpClient httpClient = new DefaultHttpClient(); 
//		HttpPost method = new HttpPost("http://jtim.gintong.com:4446/mobile/im/sendMessage.action"); 
		HttpPost method = new HttpPost("http://test.online.gintong.com/crossServerNIM//mobile/im/sendMessage.action"); 
//		set header
		
		method.setHeader("sessionID", userInfoJson.getString("sessionID"));
		method.setHeader("jtUserID", userInfoJson.getString("id"));
		method.setHeader("Host", "jtim.gintong.com:4446");
		method.setHeader("Connection", "Keep-Alive");
		method.setHeader("Content-Type", "text/plain; charset=UTF-8");
		method.setHeader("Accept-Language", "zh,zh-cn");
		
		System.out.println("初始化知识的发送请求...");
      		
//		Header[] headers1 = method.getAllHeaders();
//		  for (Header h : headers1)
//              System.out.println("head   :"+h.getName() + "------------ " + h.getValue());		
		  
		Date now = new Date();
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    JsonArray arry = new JsonArray(); 
        JsonObject bodyjson = new JsonObject();  
        JsonArray  ar = new JsonArray();
        JSONObject knowledge=this.getKnowledgeInfo(userInfoJson,externalUrl);
//        System.out.println("print knowledgeinfojson "+knowledge);
        
        JsonObject jtFileJson=new JsonObject();
        jtFileJson.addProperty("fileSize", "0");
        jtFileJson.addProperty("id", "0");
        jtFileJson.addProperty("moduleType", "0");
        jtFileJson.addProperty("mType", "0");
        jtFileJson.addProperty("reserved1", "1");
        jtFileJson.addProperty("reserved2", knowledge.get("title").toString());
        jtFileJson.addProperty("suffixName", knowledge.get("title").toString());
        jtFileJson.addProperty("taskId", knowledge.get("id").toString());
        jtFileJson.addProperty("type", "11");
        jtFileJson.addProperty("url", (String) knowledge.get("pic"));
        
        ar.add(jtFileJson);
        String jtFile=ar.toString();       
        jtFile=jtFile.substring(1, jtFile.length()-1).replace("\\", "");
//        System.out.println("jtfile: "+jtFile);
      
//        bodyjson.addProperty("ats", "[]"); 
       
        bodyjson.addProperty("fromIndex",""); 
        bodyjson.addProperty("fromTime", time.format(now)); 
        bodyjson.addProperty("messageID", getRandomString.genMessageID(userInfoJson.getString("id"))); 
        bodyjson.addProperty("mucID", mucID); 
        bodyjson.addProperty("senderName", userInfoJson.getString("name")); 
        bodyjson.addProperty("text", knowledge.get("title").toString()); 
        bodyjson.addProperty("type", "11"); 
        bodyjson.add("jtFile", jtFileJson);


        
       
        arry.add(bodyjson);  
       String body=arry.toString();
       if(proxyflag){  	    	 
			HttpHost proxy = new HttpHost("127.0.0.1", 8888);  	
   	    method.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);   
   	    }  
		
//       System.out.println("发送知识信息...");
		try{ 	 
	    	   
			method.setEntity(new StringEntity(body.substring(1,body.length()-1), Charset.forName("UTF-8")));  
    	    startTime = System.currentTimeMillis();
//    	    System.out.println(startTime);       	 
    	   
    	    response = httpClient.execute(method);      	   
            Response = EntityUtils.toString(response.getEntity());
//            System.out.println("发送信息返回   Response:"+Response+"\r\n");       	
            }catch(Exception e){
            	System.out.println("发送知识失败 err:-------------"+e.toString());
			    e.printStackTrace();     
			    }	
//		 System.out.println("知识已发送...");
		return Response;		
	}
	
//get user information	
@SuppressWarnings({ "resource" })
public JSONObject getUserInfo(String username,String password,String host){
	
	
	String Response="";	
	HttpClient httpClient = new DefaultHttpClient(); 
	//线上
//	HttpPost method = new HttpPost("http://"+host+":4445//login/loginConfiguration.json"); 
	//仿真
	HttpPost method = new HttpPost("http://"+host+"/cross//login/loginConfiguration.json "); 
		
	JsonArray arry = new JsonArray(); 
    JsonObject j = new JsonObject();
      
    j.addProperty("clientID", ""); 
    j.addProperty("clientPassword", ""); 
    j.addProperty("imei", "866769028859885"); 
    j.addProperty("version", "1.1.1 beta"); 
    j.addProperty("platform", ""); 
    j.addProperty("model", ""); 
    j.addProperty("resolution", ""); 
    j.addProperty("systemName", "android"); 
    j.addProperty("systemVersion", "22"); 
    j.addProperty("channelID", ""); 
    j.addProperty("loginString", username);  
    j.addProperty("password", password); 
       
        
    arry.add(j);
       
    String body=arry.toString();
       
   //set proxy 
    if(proxyflag){ 	    	 
			HttpHost proxy = new HttpHost("127.0.0.1", 8888); 
   	    
			method.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);   
    	}  
    
    //get response
	try{ 	 
 	   
		method.setEntity(new StringEntity(body.substring(1,body.length()-1), Charset.forName("UTF-8")));  
	    startTime = System.currentTimeMillis();
	    System.out.println(startTime);       	 
	   
	    response = httpClient.execute(method);      	   
        Response = EntityUtils.toString(response.getEntity());
        System.out.println("登陆返回     Response:"+Response+"\r\n");       	
        }catch(Exception e){
        	System.out.println("err2:-------------"+e.toString());
		    e.printStackTrace();     
		    }
	
	
	JSONObject resJson=JSON.parseObject(Response);
	JSONObject responseDataJson=JSON.parseObject(resJson.getString("responseData"));
	JSONObject jtMemberJson=JSON.parseObject(responseDataJson.getString("jtMember"));
	JSONObject personJson=JSON.parseObject(jtMemberJson.getString("person"));

	
	JSONObject userInfoJson=new JSONObject();
	userInfoJson.put("sessionID", responseDataJson.getString("sessionID"));
	userInfoJson.put("id", jtMemberJson.getString("id"));
	userInfoJson.put("name", personJson.getString("name"));
	
	System.out.println(userInfoJson);	
	return userInfoJson;
	
}


//translate url to KnowledgeInfo   
//parameter  externalUrl  : 知识网页地址
@SuppressWarnings({ "resource" })
public JSONObject getKnowledgeInfo(JSONObject userInfoJson,String externalUrl){
	
	
	String Response="";	
	HttpClient httpClient = new DefaultHttpClient(); 
	HttpPost method = new HttpPost(knowledgeInfoUrl); 
	
	method.setHeader("sessionID", userInfoJson.getString("sessionID"));
	method.setHeader("jtUserID", userInfoJson.getString("id"));
	method.setHeader("Host", "jtim.gintong.com:4446");
	method.setHeader("Connection", "Keep-Alive");
	method.setHeader("Content-Type", "text/plain; charset=UTF-8");
	method.setHeader("Accept-Language", "zh,zh-cn");
		
	JsonArray arry = new JsonArray(); 
    JsonObject j = new JsonObject();
      
    j.addProperty("externalUrl", externalUrl); 
    j.addProperty("isCreate", "true"); 
    
       
        
    arry.add(j);
       
    String body=arry.toString();
       
   //set proxy 
    if(proxyflag){ 	    	 
			HttpHost proxy = new HttpHost("127.0.0.1", 8888); 
   	    
			method.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);   
    	}  
    
    //get response
	try{ 	 
 	   
		method.setEntity(new StringEntity(body.substring(1,body.length()-1), Charset.forName("UTF-8")));  
	    startTime = System.currentTimeMillis();
//	    System.out.println(startTime);       	 
	   
	    response = httpClient.execute(method);      	   
        Response = EntityUtils.toString(response.getEntity());
//        System.out.println("登陆返回     Response:"+Response+"\r\n");       	
        }catch(Exception e){
        	System.out.println("获取知识信息失败  err2:-------------"+e.toString());
		    e.printStackTrace();     
		    }
	
	
	JSONObject resJson=JSON.parseObject(Response);
	JSONObject responseDataJson=JSON.parseObject(resJson.getString("responseData"));
	JSONObject knowledgeInfo=JSON.parseObject(responseDataJson.getString("knowledge2"));

	
	
//	System.out.println("got knowledge info"+knowledgeInfo);	
	return knowledgeInfo;
	
}

@SuppressWarnings("resource")
public JSONObject getUserInfo(){
	
	
	String Response="";	
	HttpClient httpClient = new DefaultHttpClient(); 
	HttpPost method = new HttpPost("http://"+this.host+":4445//login/loginConfiguration.json"); 
	
		
	JsonArray arry = new JsonArray(); 
    JsonObject j = new JsonObject();
      
    j.addProperty("clientID", ""); 
    j.addProperty("clientPassword", ""); 
    j.addProperty("imei", "866769028859885"); 
    j.addProperty("version", "1.1.1 beta"); 
    j.addProperty("platform", ""); 
    j.addProperty("model", ""); 
    j.addProperty("resolution", ""); 
    j.addProperty("systemName", "android"); 
    j.addProperty("systemVersion", "22"); 
    j.addProperty("channelID", ""); 
    j.addProperty("loginString", this.username);  
    j.addProperty("password", this.password); 
       
        
    arry.add(j);
       
    String body=arry.toString();
       
   //set proxy 
    if(proxyflag){ 	    	 
			HttpHost proxy = new HttpHost("127.0.0.1", 8888); 
   	    
			method.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);   
    	}  
    
    //get response
	try{ 	 
 	   
		method.setEntity(new StringEntity(body.substring(1,body.length()-1), Charset.forName("UTF-8")));  
	    startTime = System.currentTimeMillis();
//	    System.out.println(startTime);       	 
	   
	    response = httpClient.execute(method);      	   
        Response = EntityUtils.toString(response.getEntity());
//        System.out.println("登陆返回     Response:"+Response+"\r\n");       	
        }catch(Exception e){
        	System.out.println("登陆失败 err:-------------"+e.toString());
		    e.printStackTrace();     
		    }
	
	
	JSONObject resJson=JSON.parseObject(Response);
	JSONObject responseDataJson=JSON.parseObject(resJson.getString("responseData"));
	JSONObject jtMemberJson=JSON.parseObject(responseDataJson.getString("jtMember"));
	JSONObject personJson=JSON.parseObject(jtMemberJson.getString("person"));

	
	JSONObject userInfoJson=new JSONObject();
	userInfoJson.put("sessionID", responseDataJson.getString("sessionID"));
	userInfoJson.put("id", jtMemberJson.getString("id"));
	userInfoJson.put("name", personJson.getString("name"));
	
//	System.out.println("got user info: "+userInfoJson);	
	return userInfoJson;
	
}



//abandoned
@SuppressWarnings("resource")
public String getSessionID(String username,String password,String host){	
	
	
	String Response="";	
		
//		http://jtmobile.gintong.com:4445//login/loginConfiguration.json 
	
	HttpClient httpClient = new DefaultHttpClient(); 
	HttpPost method = new HttpPost("http://"+host+":4445//login/loginConfiguration.json"); 
		
	
	  
	JsonArray arry = new JsonArray(); 
    JsonObject j = new JsonObject();  
        
    j.addProperty("clientID", ""); 
        j.addProperty("clientPassword", ""); 
        j.addProperty("imei", "866769028859885"); 
        j.addProperty("version", "1.1.1 beta"); 
        j.addProperty("platform", ""); 
        j.addProperty("model", ""); 
        j.addProperty("resolution", ""); 
        j.addProperty("systemName", "android"); 
        j.addProperty("systemVersion", "22"); 
        j.addProperty("channelID", ""); 
        j.addProperty("loginString", username);  
        j.addProperty("password", password); 
       
        arry.add(j);  
       String body=arry.toString();
       if(proxyflag){  	    	 
			HttpHost proxy = new HttpHost("127.0.0.1", 8888);  	
   	    method.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);   
   	    }  
		
		try{ 	 
	    	   
			method.setEntity(new StringEntity(body.substring(1,body.length()-1), Charset.forName("UTF-8")));  
    	    startTime = System.currentTimeMillis();
//    	    System.out.println(startTime);       	 
    	   
    	    response = httpClient.execute(method);      	   
            Response = EntityUtils.toString(response.getEntity());
//            System.out.println("登陆返回     Response:"+Response+"\r\n");       	
            }catch(Exception e){
            	System.out.println("err2:-------------"+e.toString());
			    e.printStackTrace();     
			    }
		int beginIndex = Response.indexOf("sessionID");
		sessionID=Response.substring(beginIndex+12, beginIndex+56);
//		sessionID=this.getSessionStr(Response);
//		System.out.println("Response:----"+Response);
		System.out.println("sessionID:----"+sessionID);
		return sessionID;
		
	}

public String getKnowledgeInfoUrl() {
	return knowledgeInfoUrl;
}

public void setKnowledgeInfoUrl(String knowledgeInfoUrl) {
	this.knowledgeInfoUrl = knowledgeInfoUrl;
}
	
	
	

}
