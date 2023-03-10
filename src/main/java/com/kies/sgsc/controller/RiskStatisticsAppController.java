package com.kies.sgsc.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.service.EqmtRiskAnayPractLoadDailyService;
import com.kies.sgsc.service.EqmtRiskAnayPractLoadHourlyService;
import com.kies.sgsc.service.EqmtRiskAnayRtmeLoadHourlyService;
import com.kies.sgsc.service.EqmtRiskAnayRtmeLoadMinService;
import com.kies.sgsc.service.EquipmentService;
import com.kies.sgsc.service.FacilityService;
import com.kies.sgsc.service.FactyRiskAnayPractLoadDailyService;
import com.kies.sgsc.service.FactyRiskAnayPractLoadHourlyService;
import com.kies.sgsc.service.FactyRiskAnayRtmeLoadHourlyService;
import com.kies.sgsc.service.FactyRiskAnayRtmeLoadMinService;
import com.kies.sgsc.service.ProcesRiskAnayPractLoadDailyService;
import com.kies.sgsc.service.ProcesRiskAnayPractLoadHourlyService;
import com.kies.sgsc.service.ProcesRiskAnayRtmeLoadHourlyService;
import com.kies.sgsc.service.ProcesRiskAnayRtmeLoadMinService;
import com.kies.sgsc.service.ProcessService;
import com.kies.sgsc.service.RiskStatisticsAppService;
import com.kies.sgsc.service.RiskTypeService;

@RestController
@RequestMapping("sts/app")
public class RiskStatisticsAppController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RiskStatisticsAppService riskStatisticsService;
	
	@Autowired
	EqmtRiskAnayRtmeLoadMinService eqmtRiskAnayRtmeLoadMinService;
	
	@Autowired
	EqmtRiskAnayRtmeLoadHourlyService eqmtRiskAnayRtmeLoadHourlyService;
	
	@Autowired
	EqmtRiskAnayPractLoadHourlyService eqmtRiskAnayPractLoadHourlyService;
	
	@Autowired
	EqmtRiskAnayPractLoadDailyService eqmtRiskAnayPractLoadDailyService;
	
	@Autowired
	ProcesRiskAnayRtmeLoadHourlyService procesRiskAnayRtmeLoadHourlyService;
	@Autowired
	ProcesRiskAnayRtmeLoadMinService procesRiskAnayRtmeLoadMinService; 
	
	@Autowired
	ProcesRiskAnayPractLoadHourlyService procesRiskAnayPractLoadHourlyService;
	@Autowired
	ProcesRiskAnayPractLoadDailyService procesRiskAnayPractLoadDailyService;
	
	@Autowired
	FactyRiskAnayRtmeLoadMinService factyRiskAnayRtmeLoadMinService;
	
	@Autowired
	FactyRiskAnayRtmeLoadHourlyService factyRiskAnayRtmeLoadHourlyService;
	
	@Autowired
	FactyRiskAnayPractLoadHourlyService factyRiskAnayPractLoadHourlyService;
	
	@Autowired
	FactyRiskAnayPractLoadDailyService factyRiskAnayPractLoadDailyService;
	
	
	
	@Autowired
	RiskTypeService typeService;
	
	@Autowired
	EquipmentService EquipmentService;
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	FacilityService facilityService;
	
	
	
	
	/**
	 * ????????? ?????? ?????? ????????? -?????????????????? ?????? ?????? 5
	 * @param inMap
	 * @return
	 */
	@GetMapping(value = "/apprtimetop5")
	public Map mainRtimeTop5byMobile(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskHigtEqmtIncludeCause:"+inMap);
		
		Map top5Map = new HashMap();
		try {
			
			String lastymdHi = riskStatisticsService.lastDateYmdHI(inMap);
			inMap.put("anys_ymdhi", lastymdHi);

			top5Map.put("all" , riskStatisticsService.allRtimeFactyRateRate(inMap));
			top5Map.put("facty" , riskStatisticsService.listRiskHigtFactyTop5(inMap));
			top5Map.put("procs" , riskStatisticsService.listRiskHigtProcsTop5(inMap));
			//top5Map.put("eqmt" , riskStatisticsService.listRiskHigtEqmtTop5(inMap));
			
			//top5Map.put("eqmt" , riskStatisticsService.listRiskHigtEqmtIncludeCause(inMap));
			
			//top5Map.put("eqmt" , riskStatisticsService.listRiskHigtEqmtIncludeCauseApp(inMap));
			Utils.addSuccessMsg(top5Map);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return top5Map;
	}
	
	
	//????????? ??????????????? top3
	@GetMapping(value = "/appproctopeqmt3")
	public Map appproctopeqmt3(@RequestParam Map<String, String> inMap){
		logger.debug("appproctopeqmt3:"+inMap);
		
		Map top5Map = new HashMap();
		try {
			String lastymdHi = riskStatisticsService.lastDateYmdHI(inMap);
			inMap.put("anys_ymdhi", lastymdHi);
			List<Map> tmpMap = riskStatisticsService.listRiskHigtEqmtIncludeCauseApp(inMap);
			for(Map oMap : tmpMap) {
				String riskStr = Utils.NVL(oMap.get("risk_cause"));
				if(riskStr.startsWith(",")) {
					riskStr = riskStr.substring(1,riskStr.length());
				}
				if(riskStr.endsWith(",")) {
					riskStr = riskStr.substring(0,riskStr.length()-1);
				}
				oMap.put("risk_cause",riskStr);
			}
			top5Map.put("list" ,tmpMap);
			Utils.addSuccessMsg(top5Map);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return top5Map;
	}
	
	
	
	//????????? ??????????????? top3 ???????????? ????????????
	@GetMapping(value = "/appproctopeqmt3forscno")
	public Map appproctopeqmt3ForScno(@RequestParam Map<String, String> inMap){
		logger.debug("appproctopeqmt3forscno:"+inMap);
		
		Map top5Map = new HashMap();
		try {
			String lastymdHi = riskStatisticsService.lastDateYmdHI(inMap);
			inMap.put("anys_ymdhi", lastymdHi);
			List<Map> tmpMap = riskStatisticsService.listRiskHigtEqmtForScno(inMap);
			
			top5Map.put("list" ,tmpMap);
			Utils.addSuccessMsg(top5Map);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return top5Map;
	}
	
	
	/**
	 *  ??? ?????? ?????? -?????????????????? ?????? ?????? 5
	 * @param inMap
	 * @return
	 */
//	@GetMapping(value = "/mainrtimetop5")
//	public Map mainRtimeTop5(@RequestParam Map<String, String> inMap){
//		logger.debug("listRiskHigtEqmtIncludeCause:"+inMap);
//		
//		Map top5Map = new HashMap();
//		try {
//			
//			String lastymdHi = riskStatisticsService.lastDateYmdHI(inMap);
//			inMap.put("anys_ymdhi", lastymdHi);
//
//			top5Map.put("all" , riskStatisticsService.allRtimeFactyRateRate(inMap));
//			top5Map.put("facty" , riskStatisticsService.listRiskHigtFactyTop5(inMap));
//			top5Map.put("procs" , riskStatisticsService.listRiskHigtProcsTop5(inMap));
//			top5Map.put("eqmt" , riskStatisticsService.listRiskHigtEqmtIncludeCause(inMap));
//			Utils.addSuccessMsg(top5Map);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
//		}
//		return top5Map;
//	}
	
	//????????? ?????? ?????????- ??????????????? ???????????????
//	@GetMapping(value = "/rtimeeqmtcrig/{eqmt_id}")
//	public Map listEqmtRiskCrig(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
//		logger.debug("listEqmtRiskCrig:"+inMap);
//		List<Map> list = null;
//		Map retMap = new HashMap();
//		try {
//			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
//			
//			int iyear = Integer.parseInt(lastymdH.substring(0,4));
//			int imonth = Integer.parseInt(lastymdH.substring(4,6));
//			int iday = Integer.parseInt(lastymdH.substring(6,8));
//			int ihour = Integer.parseInt(lastymdH.substring(8,10));
//			
//			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
//			dt = dt.minusHours(21);
//			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//			
//			inMap.put("anys_ymdh", lastymdH);
//			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
//			inMap.put("eqmt_id", eqmt_id);
//			
//			list = riskStatisticsService.listEqmtRiskCrig(inMap);
//			
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
//		}
//		return retMap;
//	}
	
//	@GetMapping(value = "/rtimeeqmtcrig/{eqmt_id}")
//	public Map listEqmtRiskCrig(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
//		logger.debug("listEqmtRiskCrig:"+inMap);
//		List<Map> retList = new ArrayList();
//		Map retMap = new HashMap();
//		try {
//			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
//			
//			int iyear = Integer.parseInt(lastymdH.substring(0,4));
//			int imonth = Integer.parseInt(lastymdH.substring(4,6));
//			int iday = Integer.parseInt(lastymdH.substring(6,8));
//			int ihour = Integer.parseInt(lastymdH.substring(8,10));
//			
//			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
//			dt = dt.minusHours(21);
//			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//			
//			inMap.put("anys_ymdh", lastymdH);
//			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
//			inMap.put("eqmt_id", eqmt_id);
//			
//			List<Map> list =  riskStatisticsService.listEqmtRiskCrig(inMap);
//			
//			String preYmd="";
//			String preQTime = "";
//			Map innerMap = new HashMap();
//			for(Map<String,String> tMap : list) {
//				String curYmd = tMap.get("ymd");
//				String curQTime = tMap.get("qtime");
//				
//				if(!preYmd.equals(curYmd) || !preQTime.equals(curQTime)) {
//					innerMap = new HashMap();
//					retList.add(innerMap);
//					innerMap.put("ymd", curYmd);  //??????
//					innerMap.put("qtime", curQTime); //??????
//				}
//				innerMap.put(tMap.get("risk_type_id"), tMap.get("risk_rate")); //??????
//				
//				preYmd = curYmd;
//				preQTime = curQTime;
//			}
//			
//			retMap.put("list",retList);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
//		}
//		return retMap;
//	}
	
	
	//????????? ????????? > ????????????  > ???????????? (???????????? ,?????? ,???????????? ,??????)
	@GetMapping(value = "/rtime/eqmt/anly/hazard/history")
	public Map rtimeEqmtHistory(@RequestParam Map<String, String> inMap){
		List<Map> retList = new ArrayList();
		Map retMap = new HashMap();
		try {		
			List<Map> list =  riskStatisticsService.rtimeEqmtHistory(inMap);					
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	
	//????????? ????????? > ????????????>????????????(??????) 7?????????(?????????)
	@GetMapping(value = "/rtimeeqmtcrig")
	public Map listEqmtRiskCrigApp(@RequestParam Map<String, String> inMap){
		List<Map> retList = new ArrayList();
		Map retMap = new HashMap();
		try {
//			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
//			
//			int iyear = Integer.parseInt(lastymdH.substring(0,4));
//			int imonth = Integer.parseInt(lastymdH.substring(4,6));
//			int iday = Integer.parseInt(lastymdH.substring(6,8));
//			int ihour = Integer.parseInt(lastymdH.substring(8,10));
//			
//			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
//			dt = dt.minusDays(7);
//			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//			
//			inMap.put("anys_ymdh", lastymdH);
//			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
//			inMap.put("eqmt_id", eqmt_id);
			
			List<Map> list =  riskStatisticsService.listEqmtRiskCrigApp(inMap);
			
//			String preYmd="";
//			String preQTime = "";
//			Map innerMap = new HashMap();
//			for(Map<String,String> tMap : list) {
//				String curYmd = tMap.get("ymd");
//				String curQTime = tMap.get("qtime");
//				
//				if(!preYmd.equals(curYmd) || !preQTime.equals(curQTime)) {
//					innerMap = new HashMap();
//					retList.add(innerMap);
//					innerMap.put("ymd", curYmd);  //??????
//					innerMap.put("qtime", curQTime); //??????
//				}
//				innerMap.put(tMap.get("risk_type_id"), tMap.get("risk_rate")); //??????
//				
//				preYmd = curYmd;
//				preQTime = curQTime;
//			}
			
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//????????? ?????? ?????????- ??????????????? ??????????????? ?????????
	@GetMapping(value = "/rtimeeqmtcrigmap/{eqmt_id}")
	public Map listEqmtRiskCrigMap(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskCrigMap:"+inMap);
		List cols = new ArrayList() ;
		List<Map> dataList = new ArrayList();
		Map resultMap = new HashMap();
		Map tmpMap = null;
		try {
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			
			int iyear = Integer.parseInt(lastymdH.substring(0,4));
			int imonth = Integer.parseInt(lastymdH.substring(4,6));
			int iday = Integer.parseInt(lastymdH.substring(6,8));
			int ihour = Integer.parseInt(lastymdH.substring(8,10));
			
			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
			dt = dt.minusHours(21);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			inMap.put("anys_ymdh", lastymdH);
			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
			inMap.put("eqmt_id", eqmt_id);
			
			List<Map> list = riskStatisticsService.listEqmtRiskCrig(inMap);
			String risk_type_nm ="";
			String risk_cls_nm = "";
			boolean isColDate = false;
			for(Map tMap : list) {
				
				if(!risk_type_nm.equals(tMap.get("risk_type_nm"))) {
					risk_type_nm = (String)tMap.get("risk_type_nm");
					risk_cls_nm = (String)tMap.get("risk_cls_nm");
					
					tmpMap = new HashMap();
					tmpMap.put("date",tMap.get("ymd"));
					tmpMap.put("risk_type_nm",risk_type_nm);
					tmpMap.put("risk_cls_nm",risk_cls_nm);
					dataList.add(tmpMap);
				}
				if(!cols.contains(tMap.get("qtime"))) {
					if(!isColDate) {
						cols.add("date");
						cols.add("risk_type_nm");
						cols.add("risk_cls_nm");
						isColDate = true;
					}
					cols.add(tMap.get("qtime"));
				}
				
				tmpMap.put(tMap.get("qtime"),tMap.get("risk_rate"));
			}
			
			resultMap.put("cols",cols);
			resultMap.put("data",dataList);
			Utils.addSuccessMsg(resultMap);
			
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return resultMap;
	}
	
	//????????? ?????? ?????????- ??????????????? ???????????????
//	@GetMapping(value = "/rtimeeqmtpartdb/{eqmt_id}")
//	public Map listEqmtRiskPARTDB(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
//		logger.debug("listEqmtRiskPARTDB:"+inMap);
//		List<Map> list = null;
//		Map retMap = new HashMap();
//		try {
//			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
//			inMap.put("anys_ymdh", lastymdH);
//			inMap.put("eqmt_id", eqmt_id);
//			
//			list = riskStatisticsService.listEqmtRiskPARTDB(inMap);
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
//		}
//		return retMap;
//	}
	
	//????????? ?????? ?????????- ??????????????? ??????????????? ??????
	@GetMapping(value = "/rtimeeqmtpartdb/{eqmt_id}")
	public Map listEqmtRiskPARTDBApp(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskPARTDBApp:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			
			int iyear = Integer.parseInt(lastymdH.substring(0,4));
			int imonth = Integer.parseInt(lastymdH.substring(4,6));
			int iday = Integer.parseInt(lastymdH.substring(6,8));
			int ihour = Integer.parseInt(lastymdH.substring(8,10));
			
			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
			dt = dt.minusDays(7);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			inMap.put("anys_ymdh", lastymdH);
			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
			inMap.put("eqmt_id", eqmt_id);
			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
			inMap.put("anys_sys_cd", "PARTDB");
			
			list = riskStatisticsService.listEqmtRiskPARTDBApp(inMap);
			System.out.println("list:"+list);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	//????????? ???????????? - ????????????
	@GetMapping(value = "/rtimeeqmtpartdbforscno/{eqmt_id}")
	public Map listEqmtRiskPARTDBAppforscno(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskPARTDBAppforscno:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			
			int iyear = Integer.parseInt(lastymdH.substring(0,4));
			int imonth = Integer.parseInt(lastymdH.substring(4,6));
			int iday = Integer.parseInt(lastymdH.substring(6,8));
			int ihour = Integer.parseInt(lastymdH.substring(8,10));
			
			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
			dt = dt.minusDays(2);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			inMap.put("anys_ymdh", lastymdH);
			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
			inMap.put("eqmt_id", eqmt_id);
			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
			inMap.put("anys_sys_cd", "PARTDB");
			
			list = riskStatisticsService.listEqmtRiskPARTDBAppforscno(inMap);
			System.out.println("list:"+list);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
		
		
	
	//????????? ?????? ?????????- ??????????????? ??????????????? ??????
	@GetMapping(value = "/rtimeeqmtpartdbcause/{eqmt_id}/{anys_ymd}")
	public Map listEqmtRiskPARTDBAppCause(@PathVariable("eqmt_id") String eqmt_id,@PathVariable("anys_ymd") String anys_ymd,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskPARTDBAppCause:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			//String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			inMap.put("strt_anys_ymdh", anys_ymd+"00");
			inMap.put("anys_ymdh", anys_ymd+"24");
			inMap.put("eqmt_id", eqmt_id);
			inMap.put("risk_cls_cds", "'RC0002'");
			inMap.put("anys_sys_cd", "PARTDB"); 
			list = riskStatisticsService.listEqmtRiskPARTDBApp(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//????????? ?????? ?????????- ??????????????????????????????
//	@GetMapping(value = "/rtimeeqmtksec/{eqmt_id}")
//	public Map listEqmtRiskKSEC(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
//		logger.debug("listEqmtRiskKSEC:"+inMap);
//		List<Map> list = null;
//		Map retMap = new HashMap();
//		try {
//			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
//			inMap.put("anys_ymdh", lastymdH);
//			inMap.put("eqmt_id", eqmt_id);
//			
//			list = riskStatisticsService.listEqmtRiskKSEC(inMap);
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
//		}
//		return retMap;
//	}
	
	//????????? ?????? ?????????- ?????????????????????????????? ??????
	@GetMapping(value = "/rtimeeqmtksec/{eqmt_id}")
	public Map listEqmtRiskKSECApp(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskKSECApp:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			
			int iyear = Integer.parseInt(lastymdH.substring(0,4));
			int imonth = Integer.parseInt(lastymdH.substring(4,6));
			int iday = Integer.parseInt(lastymdH.substring(6,8));
			int ihour = Integer.parseInt(lastymdH.substring(8,10));
			
			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
			dt = dt.minusDays(7);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			inMap.put("anys_ymdh", lastymdH);
			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
			inMap.put("eqmt_id", eqmt_id);
			
			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
			inMap.put("anys_sys_cd", "KSEC");
			
			list = riskStatisticsService.listEqmtRiskKSECApp(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	//???????????? 
	//????????? ?????? ?????????- ???????????? ?????????????????????????????? ??????
	@GetMapping(value = "/rtimeeqmtksecforscno/{eqmt_id}")
	public Map listEqmtRiskKSECAppforscno(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskKSECAppforscno:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			
			int iyear = Integer.parseInt(lastymdH.substring(0,4));
			int imonth = Integer.parseInt(lastymdH.substring(4,6));
			int iday = Integer.parseInt(lastymdH.substring(6,8));
			int ihour = Integer.parseInt(lastymdH.substring(8,10));
			
			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
			dt = dt.minusDays(7);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			inMap.put("anys_ymdh", lastymdH);
			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
			inMap.put("eqmt_id", eqmt_id);
			
			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
			inMap.put("anys_sys_cd", "KSEC");
			
			list = riskStatisticsService.listEqmtRiskKSECAppforscno(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
		
		
	
	
	//????????? ?????? ?????????- ?????????????????????????????? ?????? ????????????
	@GetMapping(value = "/rtimeeqmtkseccause/{eqmt_id}/{anys_ymd}")
	public Map listEqmtRiskKSECAppCause(@PathVariable("eqmt_id") String eqmt_id,
			@PathVariable("anys_ymd") String anys_ymd, @RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskKSECAppCause:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			/*
			 * String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			 * inMap.put("anys_ymdh", lastymdH);
			 */
			inMap.put("strt_anys_ymdh", anys_ymd+"00");
			inMap.put("anys_ymdh", anys_ymd+"24");
			inMap.put("eqmt_id", eqmt_id);
			
			inMap.put("risk_cls_cds", "'RC0002'");
			inMap.put("anys_sys_cd", "KSEC");
			list = riskStatisticsService.listEqmtRiskKSECApp(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	//????????? ?????? 24??????
	@GetMapping(value = "/practeqmtrisktop5")
	public Map listPreEqmtRiskTop5(@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskKSEC:"+inMap);
		List<Map> list = null;
		Map<String,List<Map>> returnMap = new HashMap(); 
		try {
			
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			
			LocalDateTime dt = LocalDateTime.now();
			
			LocalDateTime hourse = dt.plusHours(21);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			//24??????
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractEqmtRiskTop5(inMap);
			returnMap.put("hour24",list);
			
			//7???
			LocalDateTime day = dt.plusHours(24*7);
			inMap.put("end_anys_ymdh", day.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractEqmtRiskTop5(inMap);
			returnMap.put("day7",list);
			
			//4???
			LocalDateTime week = dt.plusHours(24*7*4);
			inMap.put("end_anys_ymdh", week.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractEqmtRiskTop5(inMap);
			returnMap.put("week4",list);
			
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return returnMap;
	}
	
//	String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
//	
//	LocalDateTime dt = LocalDateTime.now();
//	if(lastymdH !=null && !lastymdH.equals("")) {
//		dt = LocalDateTime.of(
//				Integer.parseInt(lastymdH.substring(0,4)),
//				Integer.parseInt(lastymdH.substring(4,6)),
//				Integer.parseInt(lastymdH.substring(6,8)),
//				Integer.parseInt(lastymdH.substring(8,10)),0);
//	}
	
	
	//????????? ?????? > ??????> 24  ??????????????????practeeqmtHour24
	@GetMapping(value = "/pagemaxpracteeqmtHour")
	public Map pagePractEqmtRiskHourMax(
		@RequestParam("pagesize") String pagesize,
		@RequestParam("pageindex") String pageindex,
		@RequestParam Map<String, String> param) {
		logger.debug("pageUser pagesize:"+pagesize);
		logger.debug("pageUser pageindex:"+pageindex);
		Map contion = new HashMap();
		Map restulMap = new HashMap();
		System.out.println("====1");
		int ipagesize = param.get("pagesize")==null?0:Integer.parseInt(pagesize);
		System.out.println("====2");
		int ipageindex = param.get("pageindex")==null?0:Integer.parseInt(pageindex);
		System.out.println("====3");
		Map inMap = new HashMap();
		Map searchMap= new HashMap();
		searchMap.putAll(param);
		searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize));
		searchMap.put("endRow", ipagesize);//String.valueOf(ipageindex * ipagesize) );
		
		LocalDateTime dt = LocalDateTime.now();
		LocalDateTime hourse = dt.plusHours(21);
		String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		searchMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
		searchMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
		
		logger.debug("searchMap param:"+searchMap);
		try {
			System.out.println("1111");
			List<Map> list =  riskStatisticsService.pagePractEqmtRiskHourMax(searchMap);
			restulMap.put("list", list);
			System.out.println("2222");
			restulMap.put("totalcount", riskStatisticsService.countPractEqmtRiskHourMax(searchMap));
			restulMap.put("pagesize", pagesize);
			restulMap.put("pageindex", pageindex);
			Utils.addSuccessMsg(restulMap);
			
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," ????????? ?????? ??????",e.toString());
		}
		return restulMap;
	}
	
	//????????? ?????? > ??????> 7???>  ??????????????????
	@GetMapping(value = "/pagemaxpracteeqmtd7")
	public Map pagePractEqmtRiskDay7Max(
		@RequestParam("pagesize") String pagesize,
		@RequestParam("pageindex") String pageindex,
		@RequestParam Map<String, String> param) {

		logger.debug("pageUser pagesize:"+pagesize);
		logger.debug("pageUser pageindex:"+pageindex);
		Map contion = new HashMap();
		Map restulMap = new HashMap();
		int ipagesize = param.get("pagesize")==null?0:Integer.parseInt(pagesize);
		int ipageindex = param.get("pageindex")==null?0:Integer.parseInt(pageindex);
		Map inMap = new HashMap();
		Map searchMap= new HashMap();
		searchMap.putAll(param);
		searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize));
		searchMap.put("endRow", ipagesize);//String.valueOf(ipageindex * ipagesize) );
		
		LocalDateTime dt = LocalDateTime.now();
		LocalDateTime hourse = dt.plusDays(7);
		String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		searchMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		searchMap.put("end_anys_ymd", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		
		logger.debug("searchMap param:"+searchMap);
		try {
			List<Map> list =  riskStatisticsService.pagePractEqmtRiskDayMax(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", riskStatisticsService.countPractEqmtRiskDayMax(searchMap));
			restulMap.put("pagesize", pagesize);
			restulMap.put("pageindex", pageindex);
			Utils.addSuccessMsg(restulMap);
			
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," ????????? ?????? ??????",e.toString());
		}
		return restulMap;
	}
	
	
	//????????? ?????? > ??????> 4???>  ??????????????????
	@GetMapping(value = "/pagemaxpracteeqmtw4")
	public Map pagePractEqmtRiskWeek4Max(
		@RequestParam("pagesize") String pagesize,
		@RequestParam("pageindex") String pageindex,
		@RequestParam Map<String, String> param) {

		logger.debug("pageUser pagesize:"+pagesize);
		logger.debug("pageUser pageindex:"+pageindex);
		Map contion = new HashMap();
		Map restulMap = new HashMap();
		int ipagesize = param.get("pagesize")==null?0:Integer.parseInt(pagesize);
		int ipageindex = param.get("pageindex")==null?0:Integer.parseInt(pageindex);
		Map inMap = new HashMap();
		Map searchMap= new HashMap();
		searchMap.putAll(param);
		searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize));
		searchMap.put("endRow", ipagesize);//String.valueOf(ipageindex * ipagesize) );
		
		LocalDateTime dt = LocalDateTime.now();
		LocalDateTime hourse = dt.plusDays(7*4);
		String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		searchMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		searchMap.put("end_anys_ymd", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		
		logger.debug("searchMap param:"+searchMap);
		try {
			List<Map> list =  riskStatisticsService.pagePractEqmtRiskDayMax(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", riskStatisticsService.countPractEqmtRiskDayMax(searchMap));
			restulMap.put("pagesize", pagesize);
			restulMap.put("pageindex", pageindex);
			Utils.addSuccessMsg(restulMap);
			
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," ????????? ?????? ??????",e.toString());
		}
		return restulMap;
	}
		
		
	
	
	//????????? ??????> ??????> 24??????
	@GetMapping(value = "/practeeqmtHour24")
	public Map listPractEqmtRiskHour24(@RequestParam Map<String, String> inMap){
		logger.debug("practeeqmtHour24:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {

			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusHours(21);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractEqmtRiskHour24(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//????????? ??????> ??????> 7???
	@GetMapping(value = "/practeeqmtDay7")
	public Map listPractEqmtRiskDay7(@RequestParam Map<String, String> inMap){
		logger.debug("listPractEqmtRiskDay7:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {

			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusDays(6);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractEqmtRiskDay7(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//????????? ??????> ??????> 4???
	@GetMapping(value = "/practeeqmtWeek4")
	public Map listPractEqmtRiskWeek4(@RequestParam Map<String, String> inMap){
		logger.debug("listPractEqmtRiskWeek4:"+inMap);
		List<Map> list = new ArrayList<Map>();
		Map retMap = new HashMap();
		List<Map> tmpList = new ArrayList();
		try {
			//???????????? 4??? ??????.
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime dayHour = dt.plusDays(6);			
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			HashMap paramMap = new HashMap();
			paramMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			paramMap.put("end_anys_ymdh", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			tmpList.add(paramMap);
			//list.add(riskStatisticsService.getPractEqmtRiskDay7forWeek(inMap));
			
			dt = dayHour.plusHours(1);
			dayHour = dayHour.plusDays(7);		
			paramMap = new HashMap();
			paramMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			paramMap.put("end_anys_ymdh", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			tmpList.add(paramMap);
			
			dt = dayHour.plusHours(1);
			dayHour = dayHour.plusDays(7);		
			paramMap = new HashMap();
			paramMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			paramMap.put("end_anys_ymdh", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			tmpList.add(paramMap);
			
			dt = dayHour.plusHours(1);
			dayHour = dayHour.plusDays(7);		
			paramMap = new HashMap();
			paramMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			paramMap.put("end_anys_ymdh", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			tmpList.add(paramMap);
			
			for(int idx=tmpList.size()-1;0<=idx;idx--) {
				Map inParamMap = tmpList.get(idx);
				inParamMap.putAll(inMap);
				list.add(riskStatisticsService.getPractEqmtRiskDay7forWeek(inParamMap));
			}
			
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	//????????? ??????> ??????????????????
	@GetMapping(value = "/listrisktprocshis")
	public Map listRisktProcsHistory(@RequestParam("pagesize") String pagesize,
			@RequestParam("pageindex") String pageindex,
			@RequestParam Map<String, String> param){
		logger.debug("listRisktProcsHistory:"+param);
		Map retMap = new HashMap();
		
		List<String> inList = new ArrayList<String>();
		logger.debug("pageUser pagesize:"+pagesize);
		logger.debug("pageUser pageindex:"+pageindex);
		Map contion = new HashMap();
		Map restulMap = new HashMap();
		int ipagesize = param.get("pagesize")==null?0:Integer.parseInt(pagesize);
		int ipageindex = param.get("pageindex")==null?0:Integer.parseInt(pageindex);
		Map inMap = new HashMap();
		Map searchMap= new HashMap();
		searchMap.putAll(param);
		searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize));
		searchMap.put("endRow", ipagesize);//String.valueOf(ipageindex * ipagesize) );
		//inList.add("");
		try {
			String lastymdHi = riskStatisticsService.lastDateYmdHI(param);
			
			int iyear = Integer.parseInt(lastymdHi.substring(0,4));
			int imonth = Integer.parseInt(lastymdHi.substring(4,6));
			int iday = Integer.parseInt(lastymdHi.substring(6,8));
			int ihour = Integer.parseInt(lastymdHi.substring(8,10));
			int imin = Integer.parseInt(lastymdHi.substring(10,12));			
			
			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, imin);
			dt = dt.minusDays(1); //30??? ????????????
			String start_anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			inMap.put("anys_ymdhi", start_anys_ymdhi);
			inMap.put("end_anys_ymdhi", lastymdHi);
			searchMap.putAll(inMap);
			
			searchMap.put("risk_step_cd", inList);
			if(param.get("risk_step_cd")!=null) {
				String strrisk_step_cd = param.get("risk_step_cd");
				if(strrisk_step_cd !=null || !strrisk_step_cd.equals("")) {
					inList.addAll(Arrays.asList(strrisk_step_cd.split(",")));
				}
			}
			
			logger.debug("searchMap param:"+searchMap);
			List<Map> list =  riskStatisticsService.pageRisktProcsHistory(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", riskStatisticsService.countRisktProcsHistory(searchMap));
			restulMap.put("pagesize", pagesize);
			restulMap.put("pageindex", pageindex);
			Utils.addSuccessMsg(restulMap);
			
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," ????????? ?????? ??????",e.toString());
		}
		return restulMap;
	}
	
	//????????? ?????? - ??????????????? ????????????-?????????
	@GetMapping(value = "/listrisktprocsdeschis")
	public Map listRiskProcs3HourHistory(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskProcs3HourHistory:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {

//			LocalDateTime dt = LocalDateTime.now();
//			LocalDateTime hourse = dt.plusDays(6);
//			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//			inMap.put("strt_anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
//			inMap.put("end_anys_ymd", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listRiskProcs3HourHistory(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	
	//????????? ?????? - ??????????????? ????????????(??????)
	@GetMapping(value = "/listrisktprocsdescdayhis")
	public Map listRiskProcsDayHistory(@RequestParam Map<String, String> inMap){
		logger.debug("listrisktprocsdescdayhis:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {

//				LocalDateTime dt = LocalDateTime.now();
//				LocalDateTime hourse = dt.plusDays(6);
//				String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//				inMap.put("strt_anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
//				inMap.put("end_anys_ymd", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listRiskProcsDayHistory(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
		
	
	
	
	//????????? ?????? - ??????????????? ???????????? ?????????
	@GetMapping(value = "/listrisktprocsdeschismap")
	public Map listRiskProcs3HourHistoryMap(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskProcs3HourHistoryMap:"+inMap);
		List cols = new ArrayList() ;
		List<Map> dataList = new ArrayList();
		
		Map resultMap = new HashMap();
		Map tmpMap = null;
		try {

			List<Map> list = riskStatisticsService.listRiskProcs3HourHistory(inMap);
			String ymd ="";
			boolean isColDate = false;
			for(Map tMap : list) {
				
				if(!ymd.equals(tMap.get("ymd"))) {
					ymd = (String)tMap.get("ymd");
					
					tmpMap = new HashMap();
					tmpMap.put("date",ymd);
					dataList.add(tmpMap);
				}
				if(!cols.contains(tMap.get("qtime"))) {
					if(!isColDate) {
						cols.add("date");
						isColDate = true;
					}
					cols.add(tMap.get("qtime"));
				}
				tmpMap.put(tMap.get("qtime"),tMap.get("risk_rate"));
			}
			
			resultMap.put("cols",cols);
			resultMap.put("data",dataList);
			Utils.addSuccessMsg(resultMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return resultMap;
	}
	
	//??????????????? ????????? ?????? 3??????
	@GetMapping(value = "/listriskteqmtdeschis")
	public Map listRiskEqmt3HourHistory(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskProcs3HourHistory:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {

			list = riskStatisticsService.listRiskEqmt3HourHistory(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//??????????????? ????????? ?????? ??????
	@GetMapping(value = "/listriskteqmtdescdayhis")
	public Map listRiskEqmtDayHistory(@RequestParam Map<String, String> inMap){
		logger.debug("listriskteqmtdescdayhis:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			list = riskStatisticsService.listRiskEqmtDayHistory(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//??????????????? ????????? ?????? 3?????? ?????????
	@GetMapping(value = "/listriskteqmtdeschismap")
	public Map listRiskEqmt3HourHistoryMap(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskEqmt3HourHistoryMap:"+inMap);
		List cols = new ArrayList() ;
		List<Map> dataList = new ArrayList();
		
		Map resultMap = new HashMap();
		Map tmpMap = null;
		try {

			List<Map> list = riskStatisticsService.listRiskEqmt3HourHistory(inMap);
			String ymd ="";
			boolean isColDate = false;
			for(Map tMap : list) {
				
				if(!ymd.equals(tMap.get("ymd"))) {
					ymd = (String)tMap.get("ymd");
					
					tmpMap = new HashMap();
					tmpMap.put("date",ymd);
					dataList.add(tmpMap);
				}
				if(!cols.contains(tMap.get("qtime"))) {
					if(!isColDate) {
						cols.add("date");
						isColDate = true;
					}
					cols.add(tMap.get("qtime"));
				}
				tmpMap.put(tMap.get("qtime"),tMap.get("risk_rate"));
			}
			
			resultMap.put("cols",cols);
			resultMap.put("data",dataList);
			Utils.addSuccessMsg(resultMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return resultMap;
	}
	
	
	
	//??????????????? - ??????????????? ?????? ????????? 3??????
	@GetMapping(value = "/listriskteqmtcrighis")
	public Map listRiskCRIG3HourHistory(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskCRIG3HourHistory:"+inMap);
		List<Map> retList = new ArrayList();
		Map retMap = new HashMap();
		try {
			List<Map> list = riskStatisticsService.listRiskCRIG3HourHistoryApp(inMap);
			
			
			String preYmd="";
			String preQTime = "";
			Map innerMap = new HashMap();
			for(Map<String,String> tMap : list) {
				String curYmd = tMap.get("ymd");
				String curQTime = tMap.get("qtime");
				
				if(!preYmd.equals(curYmd) || !preQTime.equals(curQTime)) {
					innerMap = new HashMap();
					retList.add(innerMap);
					innerMap.put("ymd", curYmd);  //??????
					innerMap.put("qtime", curQTime); //??????
				}
				innerMap.put(tMap.get("risk_type_id"), tMap.get("risk_rate")); //??????
				
				preYmd = curYmd;
				preQTime = curQTime;
			}
			
			retMap.put("list",retList);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//??????????????? - ??????????????? ?????? ????????? 3?????? ?????????
	@GetMapping(value = "/listriskteqmtcrighismap")
	public Map listRiskCRIG3HourHistoryMap(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskCRIG3HourHistoryMap:"+inMap);
		List cols = new ArrayList() ;
		List<Map> dataList = new ArrayList();
		
		Map resultMap = new HashMap();
		Map tmpMap = null;
		try {

			List<Map> list = riskStatisticsService.listRiskCRIG3HourHistory(inMap);
			String risk_type_nm ="";
			String risk_cls_nm = "";
			boolean isColDate = false;
			for(Map tMap : list) {
				
				if(!risk_type_nm.equals(tMap.get("risk_type_nm"))) {
					risk_type_nm = (String)tMap.get("risk_type_nm");
					risk_cls_nm = (String)tMap.get("risk_cls_nm");
					
					tmpMap = new HashMap();
					tmpMap.put("date",tMap.get("ymd"));
					tmpMap.put("risk_type_nm",risk_type_nm);
					tmpMap.put("risk_cls_nm",risk_cls_nm);
					dataList.add(tmpMap);
				}
				if(!cols.contains(tMap.get("qtime"))) {
					if(!isColDate) {
						cols.add("date");
						cols.add("risk_type_nm");
						cols.add("risk_cls_nm");
						isColDate = true;
					}
					cols.add(tMap.get("qtime"));
				}
				
				tmpMap.put(tMap.get("qtime"),tMap.get("risk_rate"));
			}
			
			resultMap.put("cols",cols);
			resultMap.put("data",dataList);
			Utils.addSuccessMsg(resultMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return resultMap;
	}
		
		
	
	//????????? ?????? - ???????????? ????????? ??????
	@GetMapping(value = "/listriskteqmtpartdbhis")
	public Map listRiskPARTDB3HourHistoryApp(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskPARTDB3HourHistory:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			
			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
			inMap.put("anys_sys_cd", "PARTDB");
			
			//end_anys_ymd ??????
			list = riskStatisticsService.listRiskPARTDB3HourHistoryApp(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	//????????? ?????? - ???????????? ????????? ??????
	@GetMapping(value = "/listriskteqmtpartdbhisforscno")
	public Map listRiskPARTDB3HourHistoryAppforscno(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskPARTDB3HourHistory:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			
			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
			inMap.put("anys_sys_cd", "PARTDB");
			
			//end_anys_ymd ??????
			list = riskStatisticsService.listRiskPARTDB3HourHistoryAppforscno(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//????????? ?????? - ??????????????? ?????? ?????????
//	@GetMapping(value = "/listriskteqmtksec3his")
//	public Map listRiskKSEC3HourHistory(@RequestParam Map<String, String> inMap){
//		logger.debug("listRiskKSEC3HourHistory:"+inMap);
//		List<Map> list = null;
//		Map retMap = new HashMap();
//		try {
//			list = riskStatisticsService.listRiskKSEC3HourHistory(inMap);
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
//		}
//		return retMap;
//	}
	
	//????????? ?????? - ??????????????? ?????? ????????? ??????
	@GetMapping(value = "/listriskteqmtksec3his")
	public Map listRiskKSEC3HourHistoryApp(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskKSEC3HourHistory:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			
			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
			inMap.put("anys_sys_cd", "KSEC");
			
			list = riskStatisticsService.listRiskKSEC3HourHistoryApp(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//????????? ?????? - ????????????  ??????????????? ?????? ????????? ??????
	@GetMapping(value = "/listriskteqmtksec3hisforscno")
	public Map listRiskKSEC3HourHistoryAppforscno(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskKSEC3HourHistoryAppforscno:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			
			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
			inMap.put("anys_sys_cd", "KSEC");
			
			list = riskStatisticsService.listRiskKSEC3HourHistoryAppforscno(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//????????? ?????? - ???????????? - TAB(?????? , ???????????? , ??????)
	@GetMapping(value = "/listToEqmtRiskAnalysisHistoryByAnlySysCd")
	public Map listToEqmtRiskAnalysisHistoryByAnlySysCd(
			@RequestParam Map<String, String> inMap){
		logger.debug("listToEqmtRiskAnalysisHistoryByAnlySysCd:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			if(inMap.get("anys_sys_cd").equalsIgnoreCase("CRIG")) {
				inMap.put("risk_type_id", "'CRIG101','CRIG102'");
				inMap.put("anys_sys_cd", "CRIG");
			}else if(inMap.get("anys_sys_cd").equalsIgnoreCase("PARTDB")) {
				inMap.put("risk_type_id", "'PART101'");
				inMap.put("anys_sys_cd", "PARTDB");
			}else if(inMap.get("anys_sys_cd").equalsIgnoreCase("KSEC")) {
				inMap.put("risk_type_id", "'KSEC101'");
				inMap.put("anys_sys_cd", "KSEC");
			}else if(inMap.get("anys_sys_cd").equalsIgnoreCase("KIES")) {
				inMap.put("risk_type_id", "'KIES101','KIES102','KIES103'");
				inMap.put("anys_sys_cd", "KIES");
			}else {
				retMap.put("errorMsg","anys_sys_cd is null");
				return retMap;
			}
			list = riskStatisticsService.listToEqmtRiskAnalysisHistoryByAnlySysCd(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
//	@GetMapping(value = "/pageproposegasrisk")
//	public Map pageEquipment(
//		@RequestParam("pagesize") String pagesize,
//		@RequestParam("pageindex") String pageindex,
//		@RequestParam Map<String, String> param) {
//
//		logger.debug("pageUser pagesize:"+pagesize);
//		logger.debug("pageUser pageindex:"+pageindex);
//		Map contion = new HashMap();
//		Map restulMap = new HashMap();
//		int ipagesize = param.get("pagesize")==null?0:Integer.parseInt(pagesize);
//		int ipageindex = param.get("pageindex")==null?0:Integer.parseInt(pageindex);
//		Map inMap = new HashMap();
//		Map searchMap= new HashMap();
//		searchMap.putAll(param);
//		searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize));
//		searchMap.put("endRow", String.valueOf(ipageindex * ipagesize) );
//		logger.debug("searchMap param:"+searchMap);
//		try {
//			List<Map> list =  riskStatisticsService.pageProposeGasRisk(searchMap);
//			restulMap.put("list", list);
//			restulMap.put("totalcount", riskStatisticsService.countProposeGasRisk(searchMap));
//			restulMap.put("indexlist", "?????? ????????? ??????.");
//		}catch(Throwable e) {
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT," ????????? ?????? ??????",e.toString());
//		}
//		return restulMap;
//	}
	
	
	//????????? ?????? > ??????>24????????????
	@GetMapping(value = "/practfactyriskhour24")
	public Map getPractFactyRiskHour24(@RequestParam Map<String, String> inMap){
		logger.debug("practfactyriskhour24:"+inMap);
		Map returnMap = new HashMap(); 
		try {
			
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusHours(23);
			//2?????????
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			returnMap.put("all", riskStatisticsService.getPractFactyRiskAll(inMap));
			returnMap.put("list",riskStatisticsService.listPractFactyRiskHour24(inMap));
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return returnMap;
	}
	
	//????????? ?????? > ??????>7??? ??????
	@GetMapping(value = "/practfactyriskday7")
	public Map getPractFactyRiskDay7(@RequestParam Map<String, String> inMap){
		logger.debug("practfactyriskday7:"+inMap);
		Map returnMap = new HashMap(); 
		try {
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusDays(7);
			//2?????????
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			
//			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"00");
//			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"24");
			returnMap.put("all", riskStatisticsService.getPractFactyRiskAll(inMap));
			returnMap.put("list",riskStatisticsService.listPractFactyRiskDay7(inMap));
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return returnMap;
	}
	
	
	//????????? ?????? >  ??????> 4??? ??????
	@GetMapping(value = "/practfactyriskweek4") 
	public Map getPractFactyRiskDay7forWeek(@RequestParam Map<String, String> inMap){
		logger.debug("practfactyriskweek4:"+inMap);
		List<Map> list = new ArrayList<Map>();
		Map returnMap = new HashMap(); 
		
	
		List<Map> tmpList = new ArrayList();
		try {
			//???????????? 4??? ??????.
			LocalDateTime dt =LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
			String startHH = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			LocalDateTime dayHour = dt.plusDays(7);			
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			
			HashMap paramMap = new HashMap();
			paramMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			paramMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			tmpList.add(paramMap);
			
			//list.add(riskStatisticsService.getPractFactyRiskDay7forWeek(inMap));
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);	
			paramMap = new HashMap();
			paramMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			paramMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			tmpList.add(paramMap);
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			paramMap = new HashMap();
			paramMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			paramMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			tmpList.add(paramMap);
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			paramMap = new HashMap();
			paramMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			paramMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			tmpList.add(paramMap);
			
			for(int idx=tmpList.size()-1;0<=idx;idx--) {
				Map inParamMap = tmpList.get(idx);
				inParamMap.putAll(inMap);
				list.add(riskStatisticsService.getPractFactyRiskDay7forWeek(inParamMap));
			}
			
			inMap.put("anys_ymd", startHH.substring(0,8));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			returnMap.put("all", riskStatisticsService.getPractFactyRiskAll(inMap));
			returnMap.put("list", list);
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return returnMap;
	}
	
	//????????? ?????? > ????????????> ????????? >24??????
	@GetMapping(value = "/pagemaxpractprocsrisk")
	public Map pagePractProcsRiskHourMax(
		@RequestParam("pagesize") String pagesize,
		@RequestParam("pageindex") String pageindex,
		@RequestParam Map<String, String> param) {

		logger.debug("pageUser pagesize:"+pagesize);
		logger.debug("pageUser pageindex:"+pageindex);
		Map contion = new HashMap();
		Map restulMap = new HashMap();
		int ipagesize = param.get("pagesize")==null?0:Integer.parseInt(pagesize);
		int ipageindex = param.get("pageindex")==null?0:Integer.parseInt(pageindex);
		Map inMap = new HashMap();
		Map searchMap= new HashMap();
		searchMap.putAll(param);
		searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize));
		searchMap.put("endRow", ipagesize);//String.valueOf(ipageindex * ipagesize) );
		
		LocalDateTime dt = LocalDateTime.now();
		LocalDateTime hourse = dt.plusHours(23);
		//2?????????
		searchMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
		searchMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
		
		logger.debug("searchMap param:"+searchMap);
		try {
			List<Map> list =  riskStatisticsService.pagePractProcsRiskHourMax(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", riskStatisticsService.countPractProcsRiskHourMax(searchMap));
			restulMap.put("pagesize", pagesize);
			restulMap.put("pageindex", pageindex);
			Utils.addSuccessMsg(restulMap);
			
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," ????????? ?????? ??????",e.toString());
		}
		return restulMap;
	}
	
	
	//????????? ?????? > ????????????> ????????? >7???
	@GetMapping(value = "/pagemaxpractprocsriskd7")
	public Map pagePractProcsRiskHourMaxD7(
		@RequestParam("pagesize") String pagesize,
		@RequestParam("pageindex") String pageindex,
		@RequestParam Map<String, String> param) {

		logger.debug("pageUser pagesize:"+pagesize);
		logger.debug("pageUser pageindex:"+pageindex);
		Map contion = new HashMap();
		Map restulMap = new HashMap();
		int ipagesize = param.get("pagesize")==null?0:Integer.parseInt(pagesize);
		int ipageindex = param.get("pageindex")==null?0:Integer.parseInt(pageindex);
		Map inMap = new HashMap();
		Map searchMap= new HashMap();
		searchMap.putAll(param);
		searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize));
		searchMap.put("endRow", ipagesize);//String.valueOf(ipageindex * ipagesize) );
		
		LocalDateTime dt = LocalDateTime.now();
		LocalDateTime hourse = dt.plusDays(7);
		//2?????????
		searchMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		searchMap.put("end_anys_ymd", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		
		logger.debug("searchMap param:"+searchMap);
		try {
			List<Map> list =  riskStatisticsService.pagePractProcsRiskDayMax(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", riskStatisticsService.countPractProcsRiskDayMax(searchMap));
			restulMap.put("pagesize", pagesize);
			restulMap.put("pageindex", pageindex);
			Utils.addSuccessMsg(restulMap);
			
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," ????????? ?????? ??????",e.toString());
		}
		return restulMap;
	}
		
	
	//????????? ?????? > ????????????> ????????? >4???
	@GetMapping(value = "/pagemaxpractprocsriskw4")
	public Map pagePractProcsRiskHourMaxWeek4(
		@RequestParam("pagesize") String pagesize,
		@RequestParam("pageindex") String pageindex,
		@RequestParam Map<String, String> param) {

		logger.debug("pageUser pagesize:"+pagesize);
		logger.debug("pageUser pageindex:"+pageindex);
		Map contion = new HashMap();
		Map restulMap = new HashMap();
		int ipagesize = param.get("pagesize")==null?0:Integer.parseInt(pagesize);
		int ipageindex = param.get("pageindex")==null?0:Integer.parseInt(pageindex);
		Map inMap = new HashMap();
		Map searchMap= new HashMap();
		searchMap.putAll(param);
		searchMap.put("startRow", String.valueOf((ipageindex-1) * ipagesize));
		searchMap.put("endRow", ipagesize);//String.valueOf(ipageindex * ipagesize) );
		
		LocalDateTime dt = LocalDateTime.now();
		LocalDateTime hourse = dt.plusDays(7*4);
		//2?????????
		searchMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		searchMap.put("end_anys_ymd", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		
		logger.debug("searchMap param:"+searchMap);
		try {
			List<Map> list =  riskStatisticsService.pagePractProcsRiskDayMax(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", riskStatisticsService.countPractProcsRiskDayMax(searchMap));
			restulMap.put("pagesize", pagesize);
			restulMap.put("pageindex", pageindex);
			Utils.addSuccessMsg(restulMap);
			
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," ????????? ?????? ??????",e.toString());
		}
		return restulMap;
	}
			
	
	//????????? ?????? > ????????????> 24????????????
	@GetMapping(value = "/practprocsriskhour24")
	public Map listPractProcsRiskHour24(@RequestParam Map<String, String> inMap){
		logger.debug("practfactyriskhour24:"+inMap);
		List<Map> list = new ArrayList(); 
		Map retMap = new HashMap();
		try {
			
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusHours(23);
			//2?????????
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractProcsRiskHour24(inMap);
			
			
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	//????????? ?????? >????????????>  7??? ??????
	@GetMapping(value = "/practprocsriskday7")
	public Map getPractProcsRiskDay7(@RequestParam Map<String, String> inMap){
		logger.debug("practprocsriskday7:"+inMap);
		List<Map> list = new ArrayList(); 
		Map retMap = new HashMap();
		try {
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusDays(7);
			//2?????????
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"00");
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"24");
			list = riskStatisticsService.listPractProcsRiskDay7(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	//????????? ?????? > ????????????> 4??? ??????
	@GetMapping(value = "/practprocsriskweek4") 
	public Map getPractProcsRiskDay7forWeek(@RequestParam Map<String, String> inMap){
		logger.debug("practprocsriskweek4:"+inMap);
		List<Map> list = new ArrayList(); 
		Map retMap = new HashMap();
		List<Map> tmpList = new ArrayList();
		try {
			//???????????? 4??? ??????.
			LocalDateTime dt =LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
			String startHH = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			LocalDateTime dayHour = dt.plusDays(7);			
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			HashMap paramMap = new HashMap();
			paramMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			paramMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			tmpList.add(paramMap);
			
			//list.add(riskStatisticsService.getPractProcsRiskDay7forWeek(inMap));
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			paramMap = new HashMap();
			paramMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			paramMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			tmpList.add(paramMap);
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			paramMap = new HashMap();
			paramMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			paramMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			tmpList.add(paramMap);
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			paramMap = new HashMap();
			paramMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			paramMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			tmpList.add(paramMap);
			
			for(int idx=tmpList.size()-1;0<=idx;idx--) {
				Map inParamMap = tmpList.get(idx);
				inParamMap.putAll(inMap);
				list.add(riskStatisticsService.getPractProcsRiskDay7forWeek(inParamMap));
			}
			
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	//???????????? ?????? ????????? ????????? ????????? > ?????? ????????? ?????? ????????? 
	/*?????????????????? ????????? ??? ??????- ????????? 
	 * (anys_sys_cd: ???????????????: KIES, KSEC:?????????,  PARTDB:????????????, CRIG:????????????
		anys_type_cd- AT1000:?????????  AT2000:?????? 
		factory_hrrc_cd : 'FH1000':??????(??????), FH1000:FH3000 FH2000:??????(??????)
		factory_id : ??? ?????????
	*/
	///sgsc/sts/app/lisscnrsminreal/KIES/t102/202107161800
	@GetMapping(value = "/lisscnrsminreal")
	public Map lisScnrsMinDescForReal(
			//@PathVariable("anys_sys_cd") String anys_sys_cd,
			//@PathVariable("factory_id") String factory_id,
			//@PathVariable("anys_ymdhi") String anys_ymdhi,
			@RequestParam Map<String, String> inMap){
		logger.debug("lisScnrsMinDescForReal:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			//if(inMap.get("anys_type_cd").equals("")|| inMap.get("anys_sys_cd").equals("") || inMap.get("anys_ymdhi").equals("") || inMap.get("factory_id").equals("") )   {			
				//retMap.put("msg","???????????? ????????????"); // ?????????  msg ?????? 
			//}
			//inMap.put("anys_type_cd", "AT1000");
			//inMap.put("anys_sys_cd", anys_sys_cd);
			//inMap.put("anys_ymdhi", anys_ymdhi);
			//inMap.put("factory_id", factory_id);	
			list = riskStatisticsService.lisScnrsHourlyDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}

	

	
	
	//???????????? ?????? ?????? ????????? ????????? > ?????? ????????? ?????? ????????? 
	@GetMapping(value = "/lisscnrsminpract/{anys_sys_cd}/{factory_id}/{anys_ymdhi}")
	public Map lisScnrsMinDescForPract(
			@PathVariable("anys_sys_cd") String anys_sys_cd,
			@PathVariable("factory_id") String factory_id,
			@PathVariable("anys_ymdhi") String anys_ymdhi,
			@RequestParam Map<String, String> inMap){
		logger.debug("lisScnrsMinDescForPract:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			inMap.put("anys_type_cd", "AT2000");
			inMap.put("anys_sys_cd", anys_sys_cd);
			inMap.put("anys_ymdhi", anys_ymdhi);
			inMap.put("factory_id", factory_id);
			
			list = riskStatisticsService.lisScnrsHourlyDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	} 
		
		
	//???????????? ?????? ????????? ???????????? ????????? > ?????? ????????? ?????? ????????? 
	@GetMapping(value = "/lisscnrshourreal/{anys_sys_cd}/{factory_id}/{anys_ymdhi}")
	public Map lisScnrsHourDescForReal(
		@PathVariable("anys_sys_cd") String anys_sys_cd,
		@PathVariable("factory_id") String factory_id,
		@PathVariable("anys_ymdh") String anys_ymdh,
		@RequestParam Map<String, String> inMap){
		logger.debug("lisScnrsHourDescForReal:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			inMap.put("anys_type_cd", "AT1000");
			inMap.put("anys_sys_cd", anys_sys_cd);
			inMap.put("anys_ymdh", anys_ymdh);
			inMap.put("factory_id", factory_id);
			
			list = riskStatisticsService.lisScnrsHourlyDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	//??????????????? | 24?????? ?????? ????????? ?????? ?????? |??????????????? (????????????) | ??????(????????????)
	@GetMapping(value = "/lisscnrshourpract")
	public Map lisScnrsHourDescForPract(
			@RequestParam Map<String, String> inMap){
		logger.debug("lisScnrsHourDescForPract:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {	
			list = riskStatisticsService.lisScnrsHourlyDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	} 
	
	
	//??????  -  ???????????? ????????? (??????)
	@GetMapping(value = "/commLisScnrsDailyDesc")
	public Map commLisScnrsDailyDesc(

		@RequestParam Map<String, String> inMap){
		logger.debug("commLisScnrsDailyDesc:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			
			if(inMap.get("anys_type").equalsIgnoreCase("real")) {
				inMap.put("anys_type_cd", "'AT1000'");		
			}else if(inMap.get("anys_type").equalsIgnoreCase("pred")) {
				inMap.put("anys_type_cd", "'AT2000'");
			}else if(inMap.get("anys_type").equalsIgnoreCase("all")){
				inMap.put("anys_type_cd", "'AT2000' , 'AT1000'");
			}else {
				retMap.put("errorMsg" , "anys_type is null");
				return retMap;
			}
			list = riskStatisticsService.commLisScnrsDailyDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
	
	
	
	//??????  -  ???????????? ????????? (?????????)
	@GetMapping(value = "/commLisScnrsHourlyDesc")
	public Map commLisScnrsHourlyDesc(

		@RequestParam Map<String, String> inMap){
		logger.debug("commLisScnrsHourlyDesc:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {	
			if(inMap.get("anys_type").equalsIgnoreCase("real")) {
				inMap.put("anys_type_cd", "'AT1000'");		
			}else if(inMap.get("anys_type").equalsIgnoreCase("pred")) {
				inMap.put("anys_type_cd", "'AT2000'");
			}else if(inMap.get("anys_type").equalsIgnoreCase("all")){
				inMap.put("anys_type_cd", "'AT2000' , 'AT1000'");
			}else {
				retMap.put("errorMsg" , "anys_type is null");
				return retMap;
			}
			list = riskStatisticsService.commLisScnrsHourlyDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"?????????????????????",e.toString());
		}
		return retMap;
	}
			
}