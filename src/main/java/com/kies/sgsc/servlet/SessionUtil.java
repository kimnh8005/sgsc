package com.kies.sgsc.servlet;

import java.util.HashMap;
import java.util.Map;

import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.Utils;

public class SessionUtil {

	private static ThreadLocal<KiesSessionDto> SESSION_LOCALMAP = new ThreadLocal<KiesSessionDto>();
	private static ThreadLocal<String> SCHDULER_MAP = new ThreadLocal<String>();
	
	public static void setUserSession(KiesSessionDto sessionDto) {
		SESSION_LOCALMAP.set(sessionDto);
	}
	
	public static void setSchduler(String yn) {
		SCHDULER_MAP.set(yn);
	}
	
	public static Map getUserSession() {
		KiesSessionDto sessionDto= SESSION_LOCALMAP.get();
		if(sessionDto!=null) {
        	return sessionDto.getData();
        }else {
        	String scherYn = (String)SCHDULER_MAP.get();
        	if(scherYn==null || scherYn.equals("N"))
        		throw new BusinessException(CodeContrants.ERR_NOT_LOGIN,"로그인하지 않은 사용자 입니다. 다시 로그인을 해주세요");
        	else {
        		Map<String,String> userMData = new HashMap();
            	userMData.put("user_id","schduler");
            	userMData.put("user_nm","스케줄러");
            	userMData.put("auth_sid","100");
            	return userMData;
        	}
        }
	}
	
	 //사용자ID
    public static String getSnUsrId(){
    	return Utils.NVL(getUserSession().get("user_id"));
    }
    //사용자명
    public static String getSnUserNm() {
    	return Utils.NVL(getUserSession().get("user_nm"));
    }
    //권한코드
    public static String getSnUsrAuthCd() {
    	return Utils.NVL(getUserSession().get("auth_sid"));
    }
}
