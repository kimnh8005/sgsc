package com.kies.sgsc.dao.upo;

import java.util.List;
import java.util.Map;

public interface UpoDAO {
	public List<Map> get_transmitter_info() throws Exception;
	public int insertControlInfoStatus(Map inMap);

}

