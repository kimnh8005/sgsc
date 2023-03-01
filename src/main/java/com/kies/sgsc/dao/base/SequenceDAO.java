package com.kies.sgsc.dao.base;
import  java.util.List;
import java.util.Map;

public interface SequenceDAO {

	public int insertSequence(Map inMap);
	public String getSequenceKey();
	public int deleteSequence(Map inMap);
	public int updateSequence(Map inMap);
	public Map getSequence_seq_id(Map inMap);
	public List<Map> listSequence(Map inMap);
	public List<Map> pageSequence(Map inMap);
	public int countSequence(Map inMap);
}