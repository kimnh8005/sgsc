package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface SendRefugeDAO {

	public int insertSendRefuge(Map inMap);
	public int deleteSendRefuge(Map inMap);
	public int updateSendRefuge(Map inMap);
	public List<Map> listSendRefuge_user_id(Map inMap);
	public List<Map> listSendRefuge_send_rnd(Map inMap);
	public Map getSendRefuge_send_sid(Map inMap);
	public List<Map> listSendRefuge(Map inMap);
	public List<Map> pageSendRefuge(Map inMap);
	public int countSendRefuge(Map inMap);
}