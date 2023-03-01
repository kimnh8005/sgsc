package com.kies.sgsc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.service.BeaconService;

/**
 * 
 * @author nhkim
 *  
 */
@RestController
@RequestMapping(value = "gw")
public class BecoanController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	BeaconService beaconService;

	@RequestMapping(value = "/{minew_id}/status", method = RequestMethod.POST)
	public void settest(@PathVariable("minew_id") String minew_id ,@RequestBody List<Map<String,String>> inMap) throws Throwable{		
		logger.info("kimnh8005:"+inMap);
		try {
			int cnt = 0;
			String gateway_mac = "";
			if (inMap.get(0).get("type").equals("Gateway"))  {
				gateway_mac = inMap.get(0).get("mac");
			}
			for(Map<String, String> value: inMap) {
				if(cnt != 0 ) {
					value.put("gateway_mac", gateway_mac);
					SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
					String time_trans_format = value.get("timestamp").replace("T"," ").replace("Z","");
					Date time_trans_date_type = sdfDate.parse(time_trans_format);
		            try {
		            	String strDate = sdfDate.format(time_trans_date_type);
		            	value.put("timestamp",strDate);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		            
					int resCNt = beaconService.insertBeaconInfo(value);
					if(resCNt==0) { 
						throw new BusinessException(CodeContrants.ERR_NOT_PROC,"처리되지 않았음 "); 
					}
				}
				cnt++;	
			}			
		}catch(DuplicateKeyException e) {
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_DUPLICATE,"",e.toString());
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_INSERT,"",e.toString());
		}
	}
}