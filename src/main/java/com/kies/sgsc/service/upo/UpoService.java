package com.kies.sgsc.service.upo;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.upo.UpoDAO;

@Service
public class UpoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	UpoDAO UpoDAO;

	public List<Map> get_transmitter_info() throws Exception{
		return UpoDAO.get_transmitter_info();
	}
	
	public int insertControlInfoStatus(Map inMap) {
		return UpoDAO.insertControlInfoStatus(inMap);
	}

}