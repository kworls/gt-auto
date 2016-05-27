package com.gintong.android.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class getRandomString {
	
	public   getRandomString(){
		
	}
	
	 public static String getRandomString(int length){
	     String str="01234567890123456789012345678901234567890123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890123456789";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(96);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
	 }
	 //copied from JTandroid 
	 public static String genMessageID(String uid) {
		 String msgID = null;
		 try {
		 Date now = new Date();
		 String userid = uid;
		 Random ran = new Random(System.currentTimeMillis());
		 msgID = userid + now + ran.nextLong();
		 msgID = getRandomString.getMD5Str(msgID);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
		 return msgID;
		 }
	 
	 
	 private static String getMD5Str(String str) {  
	        MessageDigest messageDigest = null;  
	        try {  
	            messageDigest = MessageDigest.getInstance("MD5");  
	            messageDigest.reset();  
	            messageDigest.update(str.getBytes("UTF-8"));  
	        } catch (NoSuchAlgorithmException e) {  
	            System.out.println("NoSuchAlgorithmException caught!");  
	            System.exit(-1);  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	        byte[] byteArray = messageDigest.digest();  
	        StringBuffer md5StrBuff = new StringBuffer();  
	        for (int i = 0; i < byteArray.length; i++) {  
	            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
	                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
	            else  
	                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
	        }  
	        return md5StrBuff.toString();  
	    }  
	 
	 
	 
	 

}
