package com.kies.sgsc.comm.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FcmMsgUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FcmMsgUtil.class.getClass().getSimpleName());
	
	private static final String ACCESS_KEY = "AAAAs7NiwL4:APA91bGE0sSZgpf1vNj5el8vndq8i1HTDH4TXUZHUcGd2EgEgJI28IMba55LfNhKurTNNEQS0fo6ZCk_ZJBT3MXfP5Q6nJfUsAOGM9vMV6dyJCovc2nzpxdu6fUWI2nRCqTDVQh6SDVl";
	private static final String FIREBASE_UTL = "https://fcm.googleapis.com/fcm/send";
	private static final Executor executor = Executors.newCachedThreadPool();
	
	/**
	 * 다건 전송(병렬처리).
	 * @param inMap
	 * @return
	 */
	public static List<Map<String,String>> sendMessage(List<String> tokens, String title, String body) { 
	
		List<CompletableFuture<Map<String,String>>> resultList = 
			tokens.stream().map(token-> CompletableFuture.supplyAsync(()->{ 
				return fcmSendMessage(token,title,body);},executor)).collect(Collectors.toList());
		
		return resultList.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

	/**
	 * 단건 전송(병렬처리).
	 * @param token
	 * @param title
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static String sendMessage( String token, String title, String body) throws Exception{
		
		CompletableFuture<Map<String,String>> sendResult = 
				CompletableFuture.supplyAsync(()->{ return fcmSendMessage(token,title,body);},executor);
		
		Map<String,String> retMap = sendResult.get();
	    return retMap.toString();
	}

	
	/**
	 * 개별건 전송.
	 * @param token
	 * @param title
	 * @param body
	 * @return
	 */
	public static Map<String,String> fcmSendMessage(String token, String title, String body){
		Map<String,String> retMap = new HashMap();
		
		try {
			URL url = new URL(FIREBASE_UTL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Authorization", "key=" + ACCESS_KEY);
	
	        conn.setDoOutput(true);
	        
	        //String userId =(String) request.getSession().getAttribute("ssUserId");
	
	        // 이렇게 보내면 주제를 ALL로 지정해놓은 모든 사람들한테 알림을 날려준다.
	        //String input = "{\"notification\" : {\"title\" : \"여기다 제목 넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\"/topics/ALL\"}";
	        
	        // 이걸로 보내면 특정 토큰을 가지고있는 어플에만 알림을 날려준다  위에 둘중에 한개 골라서 날려주자
	        String input = "{\"notification\" : {\"title\" : \""+title+"\", \"body\" : \""+body+"\"}, \"to\":\""+token+"\"}";
	
	        OutputStream os = conn.getOutputStream();
	        
	        // 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
	        os.write(input.getBytes("UTF-8"));
	        os.flush();
	        os.close();
	
	        int responseCode = conn.getResponseCode();
	        logger.debug("Sending 'POST' request to URL : " + url);
	        logger.debug("Post parameters : " + input);
	        logger.debug("Response Code : " + responseCode);
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        // print result
	        logger.debug(response.toString());
	        retMap.put("resultMessage",response.toString());
	        retMap.put("resultCode","S");
		}catch(Throwable e) {
			retMap.put("resultCode","F");
			retMap.put("resultMessage",e.toString());
			e.printStackTrace();
		}
	    return retMap;
	}
	

}
