package com.kies.sgsc.socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SgscWebSocketManager {

	private static final Logger logger = LoggerFactory.getLogger(SgscWebSocketManager.class.getSimpleName());
	
	//public static Set<SgscWebSocketListener> listeners = new CopyOnWriteArraySet<>();
	//public static ConcurrentLinkedQueue<SgscWebSocketListener> listeners = new ConcurrentLinkedQueue<SgscWebSocketListener>();
	public static List<SgscWebSocketListener> listeners =  Collections.synchronizedList(new ArrayList<SgscWebSocketListener>());
	
	public static List<SgscWebSocketListener> getListeners() {
		return listeners;
	}

	public static void addListener(SgscWebSocketListener session) {
		listeners.add(session);
	}
	
	public static void removeListener(SgscWebSocketListener session) {
		listeners.remove(session);
	}
	
	//모두에게 전송.
	public static void broadcast(String message) {
        for (SgscWebSocketListener listener : listeners) {
            listener.sendMessage(message);
        }
    }
	
	//특정인원한테 전송.
	public static void sendMessage(String user_id, String message) {
		
		Optional<SgscWebSocketListener> sgscWebSocketOption = listeners.stream().filter(l->l.getUser_id().equals(user_id)).findFirst();
		if(!sgscWebSocketOption.isEmpty()) {
			sgscWebSocketOption.get().sendMessage(message);
		}else {
			logger.error("WEBSOCKET 미존재함 "+user_id);
		}
		
	}
	
	public static String getListenerUserId(SgscWebSocketListener listener) {
		return listener.getUser_id();
	}
}
