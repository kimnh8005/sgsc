package com.kies.sgsc.socket;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint(value = "/websocket")  //서버가 바인딩된 주소를 뜻함.
public class SgscWebSocketListener {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private Session session;
	private String user_id;
	private Instant connectInstant;
    private static volatile int onlineCount = 0;

    	
    @OnOpen //클라이언트가 소켓에 연결되때 마다 호출
    public void onOpen(Session session) {
        onlineCount++;
        this.session = session;
        SgscWebSocketManager.addListener(this);
        logger.info("onOpen called, userCount:" + onlineCount);
    }
    
    @OnClose //클라이언트와 소켓과의 연결이 닫힐때 (끊길떄) 마다 호
    public void onClose(Session session) {
        onlineCount--;
        SgscWebSocketManager.removeListener(this);
        logger.info("onClose called, userCount:" + onlineCount);
    }
    
    @OnMessage
    public void onMessage(String message) {
    	//acc_user:user_id
    	//예) connect_user : user_id;
    	logger.info("onMessage called, message:" + message);
    	if( message.contains(":") ) {
    		String[] str = message.split(":");
    		if("connect_user".equals(str[0].trim())){
    			this.setUser_id( str[1].trim());
    			sendMessage("[S]"+getUser_id()+" 웹소켓 접속 완료");
    		}else {
    			sendMessage("[F]사용자 입력 패턴에 맞게 등록하세요 ex) connect_user : user_id");
    		}
    	}else {
    		sendMessage("[F]사용자 입력 패턴에 맞게 등록하세요 ex) connect_user : user_id");
    	}
    	sendMessage(message);
    }
    
    @OnError //의도치 않은 에러 발생
    public void onError(Session session, Throwable throwable) {
    	logger.debug("onClose called, error:" + throwable.getMessage());
    	SgscWebSocketManager.removeListener(this);
        onlineCount--;
    }
    
    
    public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
        	logger.error("Caught exception while sending message to Session " + this.session.getId() + "error:" + e.getMessage());
        }
    }
    
    //private String user_id;
   public String toString() {
	   return session.toString()+"["+getUser_id()+"]";
   }
}
