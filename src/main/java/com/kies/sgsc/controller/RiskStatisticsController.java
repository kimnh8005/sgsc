package com.kies.sgsc.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.schedule.SgscScheduler;
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
import com.kies.sgsc.service.RiskStatisticsService;
import com.kies.sgsc.service.RiskTrhldManageService;
import com.kies.sgsc.service.RiskTypeService;

@RestController
@RequestMapping("sts")
public class RiskStatisticsController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RiskStatisticsService riskStatisticsService;
	 
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
	RiskTrhldManageService riskTrhldManageService;
	
	@Autowired
	RiskTypeService typeService;
	
	@Autowired
	EquipmentService equipmentService;
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	FacilityService facilityService;
	
	@Autowired
	SgscScheduler sgscScheduler;
	
	static String [] tmpRisk = new String[] {
		"WR0001:경고:12",
		"NR0005:정상5단계:6",
		"NR0004:정상4단계:5",
		"NR0003:정상3단계:4",
		"NR0002:정상2단계:3",
		"NR0001:정상1단계:2",
		"NR0000:정상:1:5",
		"CR0001:주의1단계:7",
		"CR0002:주의2단계:8",
		"CR0003:주의3단계:9",
		"CR0004:주의4단계:10",
		"CR0005:주의5단계:11",
		"DG0001:위험:13"
	};
	
	static String [] tmpErrorRisk = new String[] {
			"NR0000:정상:1",
		"ER0001:고장/발생:2"
		
	};
	
	//시뮬레이션 테스트
	@GetMapping(value = "/simul")
	public String simul(@RequestParam Map<String, String> inMap){
		sgscScheduler.makeSimulData();
		return "ok";
	}
	
	
	
	
	//시설 ################################
	@GetMapping(value = "/th_facty_risk_anay_rtme_load_min")
	public String th_facty_risk_anay_rtme_load_min(@RequestParam Map<String, String> inMap){
		
//202010030000
		LocalDateTime dt = LocalDateTime.of(2020, 10, 03, 00, 01);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
//		dt = dt.plusMinutes(1);
//		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = facilityService.listFacility(eqMap);
		
		List<Map> riskTypeList = new ArrayList<Map>();
		eqMap.clear();
		eqMap.put("risk_type_id", "KIES101"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		riskTypeList = riskTypeList.stream().filter(p->{
			return !((String)p.get("risk_type_id")).startsWith("KIES2");
		}).collect(Collectors.toList());
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
						
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		for(int i=0;i<((60*24)*16 );i++) {
			dt = dt.plusMinutes(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymdhi",anys_ymdhi);
				logger.debug("########################################### eqmt_id:"+eMap.get("facty_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("facty_id",eMap.get("facty_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					factyRiskAnayRtmeLoadMinService.insertFactyRiskAnayRtmeLoadMin(eqmtRiskAnayRtmeLoadMinMap);
				}
			}
		}
		
		return "ok";
	}
	
	//위험도 예측
	@GetMapping(value = "/th_facty_risk_anay_pract_load_hourly")
	public String th_facty_risk_anay_pract_load_hourly(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 10, 10, 00, 00);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
//		dt = dt.plusMinutes(1);
//		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = facilityService.listFacility(eqMap);
		
		List<Map> riskTypeList = new ArrayList<Map>();
		eqMap.clear();
		eqMap.put("risk_type_id", "KIES201"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1:5",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		for(int i=0;i<(24*50);i++) {
			dt = dt.plusHours(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymdh",anys_ymdhi);
				logger.debug("########################################### facty_id:"+eMap.get("facty_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("facty_id",eMap.get("facty_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					factyRiskAnayPractLoadHourlyService.insertFactyRiskAnayPractLoadHourly(eqmtRiskAnayRtmeLoadMinMap);
				}
			}
		}
		
		return "ok";
	}
	
	//위험도 예측
	@GetMapping(value = "/th_facty_risk_anay_pract_load_daily")
	public String th_facty_risk_anay_pract_load_daily(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 10, 10, 00, 00);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
//			dt = dt.plusMinutes(1);
//			System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = facilityService.listFacility(eqMap);
		
		List<Map> riskTypeList = new ArrayList<Map>();
		eqMap.clear();
		eqMap.put("risk_type_id", "KIES201"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1:5",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		for(int i=0;i<50;i++) {
			dt = dt.plusDays(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymd",anys_ymdhi);
				logger.debug("########################################### facty_id:"+eMap.get("facty_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("facty_id",eMap.get("facty_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					factyRiskAnayPractLoadDailyService.insertFactyRiskAnayPractLoadDaily(eqmtRiskAnayRtmeLoadMinMap);
				}
			}
		}
		
		return "ok";
	}
	
	
	//생산공정 ########################################################
	@GetMapping(value = "/th_proces_risk_anay_rtme_load_hourly")
	public String makeRiskProcsHH(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 9, 28, 12, 0);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
//			dt = dt.plusMinutes(1);
//			System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = processService.listProcess(eqMap);
		
		eqMap.put("use_yn","");
		List<Map> riskTypeList = new ArrayList<Map>();
		//eqMap.clear();
		//eqMap.put("risk_type_id", "KIES102"); //통합공정위험도(실시간)
		eqMap.put("risk_cls_cd", "RC0001"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		eqMap.put("risk_cls_cd", "RC0002"); //이상원인
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		eqMap.put("risk_cls_cd", "RC0003"); //설비위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap)); 
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		
		for(int i=0;i<(24*50);i++) {
			dt = dt.plusHours(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymdh",anys_ymdhi);
				logger.debug("########################################### procs_id:"+eMap.get("procs_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("procs_id",eMap.get("procs_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100D);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					procesRiskAnayRtmeLoadHourlyService.insertProcesRiskAnayRtmeLoadHourly(eqmtRiskAnayRtmeLoadMinMap);
				}
			}
		}
		
		return "ok";
	}
	
	
	
	@GetMapping(value = "/th_proces_risk_anay_rtme_load_min")
	public String th_proces_risk_anay_rtme_load_min(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 10, 8, 0, 1);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
//			dt = dt.plusMinutes(1);
//			System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = processService.listProcess(eqMap);
		
		eqMap.put("use_yn","");
		List<Map> riskTypeList = new ArrayList<Map>();
		//eqMap.clear();
		//eqMap.put("risk_type_id", "KIES102"); //통합공정위험도(실시간)
		eqMap.put("risk_cls_cd", "RC0001"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		eqMap.put("risk_cls_cd", "RC0002"); //이상원인
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		eqMap.put("risk_cls_cd", "RC0003"); //설비위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap)); 
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		
		for(int i=0;i<60*(24*10);i++) {  //5일
			dt = dt.plusMinutes(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
			
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymdhi",anys_ymdhi);
				logger.debug("########################################### procs_id:"+eMap.get("procs_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("procs_id",eMap.get("procs_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100D);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					procesRiskAnayRtmeLoadMinService.insertProcesRiskAnayRtmeLoadMin(eqmtRiskAnayRtmeLoadMinMap);
				}
			}
		}
		return "ok";
	}
	
	@GetMapping(value = "/th_proces_risk_anay_pract_load_hourly")
	public String th_proces_risk_anay_pract_load_hourly(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 9, 28, 00, 0);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
//		dt = dt.plusMinutes(1);
//		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = processService.listProcess(eqMap);
		
		List<Map> riskTypeList = new ArrayList<Map>();
		eqMap.clear();
		eqMap.put("risk_cls_cd", "RC0001"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		eqMap.put("risk_cls_cd", "RC0002"); //이상원인
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		eqMap.put("risk_cls_cd", "RC0003"); //위험도보조데이터
		riskTypeList.addAll(typeService.listRiskType(eqMap)); 
		
		eqMap.put("risk_cls_cd", "RC0006"); //설비위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		
		for(int i=0;i<(24*50);i++) {
			dt = dt.plusHours(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymdh",anys_ymdhi);
				logger.debug("########################################### procs_id:"+eMap.get("procs_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("procs_id",eMap.get("procs_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100D);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					procesRiskAnayPractLoadHourlyService.insertProcesRiskAnayPractLoadHourly(eqmtRiskAnayRtmeLoadMinMap);
				}
			}
		}
		
		return "ok";
	}	
	
	
	
	@GetMapping(value = "/th_proces_risk_anay_pract_load_daily")
	public String th_proces_risk_anay_pract_load_daily(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 9, 28, 00, 0);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
//		dt = dt.plusMinutes(1);
//		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = processService.listProcess(eqMap);
		
		List<Map> riskTypeList = new ArrayList<Map>();
		eqMap.clear();
		eqMap.put("risk_cls_cd", "RC0001"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		eqMap.put("risk_cls_cd", "RC0002"); //이상원인
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		eqMap.put("risk_cls_cd", "RC0003"); //위험도보조데이터
		riskTypeList.addAll(typeService.listRiskType(eqMap)); 
		
		eqMap.put("risk_cls_cd", "RC0006"); //설비위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		
		for(int i=0;i<(50);i++) {
			dt = dt.plusDays(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymd",anys_ymdhi);
				logger.debug("########################################### procs_id:"+eMap.get("procs_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("procs_id",eMap.get("procs_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100D);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					procesRiskAnayPractLoadDailyService.insertProcesRiskAnayPractLoadDaily(eqmtRiskAnayRtmeLoadMinMap);
				}
			}
		}
		
		return "ok";
	}	
	
	
	//설비 ########################################################	
	@GetMapping(value = "/th_eqmt_risk_anay_rtme_load_min")
	public String makeRisk(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 10, 1, 02, 00);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
//		dt = dt.plusMinutes(1);
//		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = equipmentService.listEquipment(eqMap);
		
		List<Map> riskTypeList = new ArrayList<Map>();
		eqMap.clear();
		eqMap.put("risk_cls_cd", "RC0001"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		eqMap.put("risk_cls_cd", "RC0002"); //이상원인
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		eqMap.put("risk_cls_cd", "RC0003"); //설비위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		riskTypeList = riskTypeList.stream().filter(p->{
			return !((String)p.get("risk_type_id")).startsWith("KIES2");
		}).collect(Collectors.toList());
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1:5",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		for(int i=0;i<(60*48);i++) {
			dt = dt.plusMinutes(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymdhi",anys_ymdhi);
				logger.debug("########################################### eqmt_id:"+eMap.get("eqmt_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("eqmt_id",eMap.get("eqmt_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					eqmtRiskAnayRtmeLoadMinService.insertEqmtRiskAnayRtmeLoadMin(eqmtRiskAnayRtmeLoadMinMap);
				}
			}
		}
		
		return "ok";
	}
	
	
	
	
	@GetMapping(value = "/th_eqmt_risk_anay_rtme_load_hourly")
	public String makeRTimeRiskHH(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 9, 28, 12, 0);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
//		dt = dt.plusMinutes(1);
//		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = equipmentService.listEquipment(eqMap);
		
		List<Map> riskTypeList = new ArrayList<Map>();
		eqMap.clear();
		eqMap.put("risk_cls_cd", "RC0001"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		eqMap.put("risk_cls_cd", "RC0002"); //이상원인
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		eqMap.put("risk_cls_cd", "RC0003"); //위험도보조데이터
		riskTypeList.addAll(typeService.listRiskType(eqMap)); 
		
		eqMap.put("risk_cls_cd", "RC0006"); //설비위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		
		for(int i=0;i<(24*3);i++) {
			dt = dt.plusHours(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymdh",anys_ymdhi);
				logger.debug("########################################### eqmt_id:"+eMap.get("eqmt_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("eqmt_id",eMap.get("eqmt_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100D);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					eqmtRiskAnayRtmeLoadHourlyService.insertEqmtRiskAnayRtmeLoadHourly(eqmtRiskAnayRtmeLoadMinMap);
				}
				
			}
		}
		
		
		return "ok";
	}	
	
	@GetMapping(value = "/th_eqmt_risk_anay_pract_load_hourly")
	public String makeRiskPreHH(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 10, 10, 0, 0);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
//		dt = dt.plusMinutes(1);
//		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = equipmentService.listEquipment(eqMap);
		
		List<Map> riskTypeList = new ArrayList<Map>();
		eqMap.clear();
		eqMap.put("risk_type_id", "KIES203"); //위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		
		for(int i=0;i<(24*50);i++) {
			dt = dt.plusHours(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymdh",anys_ymdhi);
				logger.debug("########################################### eqmt_id:"+eMap.get("eqmt_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("eqmt_id",eMap.get("eqmt_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100D);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					eqmtRiskAnayPractLoadHourlyService.insertEqmtRiskAnayPractLoadHourly(eqmtRiskAnayRtmeLoadMinMap);
				}
				
			}
		}
		
		return "ok";
	}
	
	//
	@GetMapping(value = "/th_eqmt_risk_anay_pract_load_daily")
	public String th_eqmt_risk_anay_pract_load_daily(@RequestParam Map<String, String> inMap){
		
		LocalDateTime dt = LocalDateTime.of(2020, 10, 10, 0, 0);
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//		dt = dt.plusMinutes(1);
//		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
				
		Map eqMap = new HashMap();
		eqMap.put("use_yn","Y");
		List<Map> eqList = equipmentService.listEquipment(eqMap);
		
		List<Map> riskTypeList = new ArrayList<Map>();
		eqMap.clear();
		eqMap.put("risk_type_id", "KIES203"); //위험도
		eqMap.put("use_yn","Y");
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		
		for(Map rMap:riskTypeList) {
			System.out.println(rMap);
		}
		
		List<Map<String,String>> riskStepList = new ArrayList<Map<String,String>>();
		
		Map<String,String> tmpMap = new HashMap();
		
		String [] tmpRisk = new String[] {
			"WR0001:경고:12",
			"NR0005:정상5단계:6",
			"NR0004:정상4단계:5",
			"NR0003:정상3단계:4",
			"NR0002:정상2단계:3",
			"NR0001:정상1단계:2",
			"NR0000:정상:1",
			"CR0001:주의1단계:7",
			"CR0002:주의2단계:8",
			"CR0003:주의3단계:9",
			"CR0004:주의4단계:10",
			"CR0005:주의5단계:11",
			"DG0001:위험:13"
		};
		
		String [] tmpErrorRisk = new String[] {
			"ER0001:고장/발생:2",
			"NR0000:정상:1"
		};
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		
		for(int i=0;i<(60);i++) {
			dt = dt.plusDays(1);
			anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			
			Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
			for(Map eMap : eqList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_ymd",anys_ymdhi);
				logger.debug("########################################### eqmt_id:"+eMap.get("eqmt_id") );
				eqmtRiskAnayRtmeLoadMinMap.put("eqmt_id",eMap.get("eqmt_id"));
				
				for(Map riskTypeMap : riskTypeList) {
					eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
					String[] riskAry = null;
					if(riskTypeMap.get("use_yn").equals("N")) {
						int rdn = new Random().nextInt(2);
						String riskVal = tmpErrorRisk[rdn];
						riskAry = riskVal.split(":");
						eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					}else {
						int rdn = new Random().nextInt(12);
						String riskVal = tmpRisk[rdn];
						riskAry = riskVal.split(":");
						double riskNum = 7.69 * Integer.parseInt(riskAry[2]) + (new Random().nextInt(99)/100D);
						riskNum += new Random().nextInt(20) - new Random().nextInt(10) ;
						eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					}
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
					
					eqmtRiskAnayPractLoadDailyService.insertEqmtRiskAnayPractLoadDaily(eqmtRiskAnayRtmeLoadMinMap);
				}
				
			}
		}
		
		return "ok";
	}
	
	
	/**
	 * 위험도 높은 설비 모바일 -실시간위험도 높은 설비 5
	 * @param inMap
	 * @return
	 */
//	@GetMapping(value = "/apprtimetop5")
//	public Map mainRtimeTop5byMobile(@RequestParam Map<String, String> inMap){
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
//			//top5Map.put("eqmt" , riskStatisticsService.listRiskHigtEqmtTop5(inMap));
//			
//			//top5Map.put("eqmt" , riskStatisticsService.listRiskHigtEqmtIncludeCause(inMap));
//			
//			//top5Map.put("eqmt" , riskStatisticsService.listRiskHigtEqmtIncludeCauseApp(inMap));
//			Utils.addSuccessMsg(top5Map);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return top5Map;
//	}
	
	
	//모바일 생산공정별 top3
//	@GetMapping(value = "/appproctopeqmt3")
//	public Map appproctopeqmt3(@RequestParam Map<String, String> inMap){
//		logger.debug("appproctopeqmt3:"+inMap);
//		
//		Map top5Map = new HashMap();
//		try {
//			String lastymdHi = riskStatisticsService.lastDateYmdHI(inMap);
//			inMap.put("anys_ymdhi", lastymdHi);
//			List<Map> tmpMap = riskStatisticsService.listRiskHigtEqmtIncludeCauseApp(inMap);
//			for(Map oMap : tmpMap) {
//				String riskStr = Utils.NVL(oMap.get("risk_cause"));
//				if(riskStr.startsWith(",")) {
//					riskStr = riskStr.substring(1,riskStr.length());
//				}
//				if(riskStr.endsWith(",")) {
//					riskStr = riskStr.substring(0,riskStr.length()-1);
//				}
//				oMap.put("risk_cause",riskStr);
//			}
//			top5Map.put("list" ,tmpMap);
//			Utils.addSuccessMsg(top5Map);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return top5Map;
//	}
	
	
	
	
	/**
	 * 위험도 높은 설비 -실시간위험도 높은 설비 5
	 * @param inMap
	 * @return
	 */
	@GetMapping(value = "/mainrtimetop5")
	public Map mainRtimeTop5(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskHigtEqmtIncludeCause:"+inMap);
		
		Map top5Map = new HashMap();
		try {
			
			String lastymdHi = riskStatisticsService.lastDateYmdHI(inMap);
			inMap.put("anys_ymdhi", lastymdHi);

			top5Map.put("all" , riskStatisticsService.allRtimeFactyRateRate(inMap));
			top5Map.put("facty" , riskStatisticsService.listRiskHigtFactyTop5(inMap));
			top5Map.put("procs" , riskStatisticsService.listRiskHigtProcsTop5(inMap));
			top5Map.put("eqmt" , riskStatisticsService.listRiskHigtEqmtIncludeCause(inMap));
			Utils.addSuccessMsg(top5Map);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return top5Map;
	}
	
	
	
	/**
	 * <<<시나리오용>>> 위험도 높은 설비 -실시간위험도 높은 설비 5
	 * @param inMap
	 * @return
	 */
	@GetMapping(value = "/mainrtimetop5ForScno")
	public Map mainRtimeTop5ForScno(@RequestParam Map<String, String> inMap){
		logger.debug("===>>>>> mainRtimeTop5ForScno 시작:"+inMap);
		
		Map top5Map = new HashMap();
		try {
			
			String lastymdHi = riskStatisticsService.lastDateYmdHI(inMap);
			inMap.put("anys_ymdhi", lastymdHi);
			inMap.put("topidx","5");
			
			logger.debug("mainRtimeTop5ForScno_inMap:"+inMap); 
			
			top5Map.put("all" , riskStatisticsService.allRtimeFactyRateRate(inMap));
			top5Map.put("facty" , riskStatisticsService.listRiskHigtFactyTop5(inMap));
			top5Map.put("procs" , riskStatisticsService.listRiskHigtProcsTop5(inMap));
			top5Map.put("eqmt" , riskStatisticsService.listRiskHigtEqmtForSnroTop5(inMap));
			Utils.addSuccessMsg(top5Map);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return top5Map;
	}
	
	//실시간 분석 데이터- 센서데이터 분석위험도
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
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return retMap;
//	}
	
	
	//시나리오 실시간 위험도 > 설비분석데이터
	@GetMapping(value = "/rtimeeqmtcrigforsnro/{eqmt_id}")
	public Map listEqmtRiskCrigForSnro(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskCrig:"+inMap);
		List<Map> retList = new ArrayList();
		Map retMap = new HashMap();
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
			
			logger.debug("listEqmtRiskCrigForSnro_map:"+inMap);
			
			
			List<Map> list =  riskStatisticsService.listEqmtRiskCrigForSnro(inMap);
			retMap.put("list",list);
			/*String preYmd="";
			String preQTime = "";
			Map innerMap = new HashMap();
			for(Map<String,String> tMap : list) {
				String curYmd = tMap.get("ymd");
				String curQTime = tMap.get("qtime");
				
				if(!preYmd.equals(curYmd) || !preQTime.equals(curQTime)) {
					innerMap = new HashMap();
					retList.add(innerMap);
					innerMap.put("ymd", curYmd);  //날짜
					innerMap.put("qtime", curQTime); //시간
				}
				innerMap.put(tMap.get("risk_type_id"), tMap.get("risk_rate")); //시간
				
				preYmd = curYmd;
				preQTime = curQTime;
			}
			
			retMap.put("list",retList);*/
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	@GetMapping(value = "/rtimeeqmtcrig/{eqmt_id}")
	public Map listEqmtRiskCrig(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskCrig:"+inMap);
		List<Map> retList = new ArrayList();
		Map retMap = new HashMap();
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
			
			List<Map> list =  riskStatisticsService.listEqmtRiskCrig(inMap);
			
			String preYmd="";
			String preQTime = "";
			Map innerMap = new HashMap();
			for(Map<String,String> tMap : list) {
				String curYmd = tMap.get("ymd");
				String curQTime = tMap.get("qtime");
				
				if(!preYmd.equals(curYmd) || !preQTime.equals(curQTime)) {
					innerMap = new HashMap();
					retList.add(innerMap);
					innerMap.put("ymd", curYmd);  //날짜
					innerMap.put("qtime", curQTime); //시간
				}
				innerMap.put(tMap.get("risk_type_id"), tMap.get("risk_rate")); //시간
				
				preYmd = curYmd;
				preQTime = curQTime;
			}
			
			retMap.put("list",retList);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
	//실시간 위험도 > 설비상세>이상원인(팝업) 7일출력(모바일)
//	@GetMapping(value = "/app/rtimeeqmtcrig/{eqmt_id}")
//	public Map listEqmtRiskCrigApp(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
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
//			dt = dt.minusDays(7);
//			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//			
//			inMap.put("anys_ymdh", lastymdH);
//			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
//			inMap.put("eqmt_id", eqmt_id);
//			
//			List<Map> list =  riskStatisticsService.listEqmtRiskCrigApp(inMap);
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
//					innerMap.put("ymd", curYmd);  //날짜
//					innerMap.put("qtime", curQTime); //시간
//				}
//				innerMap.put(tMap.get("risk_type_id"), tMap.get("risk_rate")); //시간
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
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return retMap;
//	}
	
	//실시간 분석 데이터- 센서데이터 분석위험도 맵형식
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return resultMap;
	}
	
	//실시간 분석 데이터- 센서데이터 분석위험도
	@GetMapping(value = "/rtimeeqmtpartdb/{eqmt_id}")
	public Map listEqmtRiskPARTDB(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskPARTDB:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			inMap.put("strt_anys_ymdh", lastymdH);
			inMap.put("anys_ymdh", lastymdH);
			inMap.put("eqmt_id", eqmt_id);
			
			list = riskStatisticsService.listEqmtRiskPARTDB(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//<!-- 시나리오 용  실시간 위험도 > 진단예지 위험도 -->
	@GetMapping(value = "/rtimeeqmtpartdbforsnro/{eqmt_id}")
	public Map listEqmtRiskPARTDBForSnro(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskPARTDBForSnro:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			int iyear = Integer.parseInt(lastymdH.substring(0,4));
			int imonth = Integer.parseInt(lastymdH.substring(4,6));
			int iday = Integer.parseInt(lastymdH.substring(6,8));
			int ihour = Integer.parseInt(lastymdH.substring(8,10));
			
			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
			dt = dt.minusHours(21);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
			inMap.put("anys_ymdh", lastymdH);
			inMap.put("eqmt_id", eqmt_id);
			
			logger.debug("listEqmtRiskPARTDBForSnro_map:"+inMap);
			
			list = riskStatisticsService.listEqmtRiskPARTDBForSnro(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	} 
	
	
	
	
	
	//실시간 분석 데이터- 센서데이터 분석위험도 앱용
//	@GetMapping(value = "/app/rtimeeqmtpartdb/{eqmt_id}")
//	public Map listEqmtRiskPARTDBApp(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
//		logger.debug("listEqmtRiskPARTDBApp:"+inMap);
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
//			dt = dt.minusDays(7);
//			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//			
//			inMap.put("anys_ymdh", lastymdH);
//			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
//			inMap.put("eqmt_id", eqmt_id);
//			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
//			inMap.put("anys_sys_cd", "PARTDB");
//			
//			list = riskStatisticsService.listEqmtRiskPARTDBApp(inMap);
//			System.out.println("list:"+list);
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return retMap;
//	}
	
	//실시간 분석 데이터- 센서데이터 분석위험도 앱용
//	@GetMapping(value = "/app/rtimeeqmtpartdbcause/{eqmt_id}/{anys_ymd}")
//	public Map listEqmtRiskPARTDBAppCause(@PathVariable("eqmt_id") String eqmt_id,@PathVariable("anys_ymd") String anys_ymd,@RequestParam Map<String, String> inMap){
//		logger.debug("listEqmtRiskPARTDBAppCause:"+inMap);
//		List<Map> list = null;
//		Map retMap = new HashMap();
//		try {
//			//String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
//			inMap.put("strt_anys_ymdh", anys_ymd+"00");
//			inMap.put("anys_ymdh", anys_ymd+"24");
//			inMap.put("eqmt_id", eqmt_id);
//			inMap.put("risk_cls_cds", "'RC0002'");
//			inMap.put("anys_sys_cd", "PARTDB"); 
//			list = riskStatisticsService.listEqmtRiskPARTDBApp(inMap);
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return retMap;
//	}
	
	//실시간 분석 데이터- 영상데이터분석위험도
	@GetMapping(value = "/rtimeeqmtksec/{eqmt_id}")
	public Map listEqmtRiskKSEC(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskKSEC:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			inMap.put("anys_ymdh", lastymdH);
			inMap.put("eqmt_id", eqmt_id);
			
			list = riskStatisticsService.listEqmtRiskKSEC(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//<!-- 시나리오 용  실시간 위험도 > 영상데이터 분석 위험도 -->
	@GetMapping(value = "/rtimeeqmtksecforsnro/{eqmt_id}")
	public Map listEqmtRiskKSECForSnro(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskKSECForSnro:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
			int iyear = Integer.parseInt(lastymdH.substring(0,4));
			int imonth = Integer.parseInt(lastymdH.substring(4,6));
			int iday = Integer.parseInt(lastymdH.substring(6,8));
			int ihour = Integer.parseInt(lastymdH.substring(8,10));
			
			LocalDateTime dt = LocalDateTime.of(iyear, imonth, iday, ihour, 0);
			dt = dt.minusHours(21);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
			inMap.put("anys_ymdh", lastymdH);
			inMap.put("eqmt_id", eqmt_id);
			
			logger.debug("listEqmtRiskKSECForSnro_map:"+inMap);
			
			list = riskStatisticsService.listEqmtRiskKSECForSnro(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	} 
	
	
	//시나리오 팝업 실시간 분단위 위험도 > 영상 데이터 분석 위험도 
	/*사고시나리오 실시간 분 팝업- 위험율 
	 * (anys_sys_cd: 통합위험율: KIES, KSEC:위험율,  PARTDB:고장확율, CRIG:발생확율
		anys_type_cd- AT1000:실시간  AT2000:예측 
		factory_hrrc_cd : 'FH1000':계통(시설), FH1000:FH3000 FH2000:시설(공정)
		factory_id : 각 아이디
	*/
	@GetMapping(value = "/lisscnrsminreal/{anys_sys_cd}/{factory_id}/{anys_ymdh}")
	public Map lisScnrsMinDescForReal(
			@PathVariable("anys_sys_cd") String anys_sys_cd,
			@PathVariable("factory_id") String factory_id,
			@PathVariable("anys_ymdh") String anys_ymdh,
			@RequestParam Map<String, String> inMap){
		logger.debug("lisScnrsMinDescForReal:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			inMap.put("anys_type_cd", "AT1000");
			inMap.put("anys_sys_cd", anys_sys_cd);
			inMap.put("anys_ymdh", anys_ymdh);
			inMap.put("factory_id", factory_id);
			
			list = riskStatisticsService.lisScnrsMinDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
	//시나리오 팝업 예측 분단위 위험도 > 영상 데이터 분석 위험도 
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
			
			list = riskStatisticsService.lisScnrsMinDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	} 
	
	
	//시나리오 팝업 실시간 시간단위 위험도 > 영상 데이터 분석 위험도 
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
			
			list = riskStatisticsService.lisScnrsHourDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
	//시나리오 팝업 예측 시간단위 위험도 > 영상 데이터 분석 위험도 
	@GetMapping(value = "/lisscnrshourpract/{anys_sys_cd}/{factory_id}/{anys_ymdh}")
	public Map lisScnrsHourDescForPract(
			@PathVariable("anys_sys_cd") String anys_sys_cd,
			@PathVariable("factory_id") String factory_id,
			@PathVariable("anys_ymdh") String anys_ymdh,
			@RequestParam Map<String, String> inMap){
		logger.debug("lisScnrsHourDescForPract:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			inMap.put("anys_type_cd", "AT2000");
			inMap.put("anys_sys_cd", anys_sys_cd);
			inMap.put("anys_ymdh", anys_ymdh);
			inMap.put("factory_id", factory_id);
			
			list = riskStatisticsService.lisScnrsHourDesc(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	} 
		
	
	//실시간 분석 데이터- 영상데이터분석위험도 앱용
//	@GetMapping(value = "/app/rtimeeqmtksec/{eqmt_id}")
//	public Map listEqmtRiskKSECApp(@PathVariable("eqmt_id") String eqmt_id,@RequestParam Map<String, String> inMap){
//		logger.debug("listEqmtRiskKSECApp:"+inMap);
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
//			dt = dt.minusDays(7);
//			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//			
//			inMap.put("anys_ymdh", lastymdH);
//			inMap.put("strt_anys_ymdh", strt_anys_ymdh);
//			inMap.put("eqmt_id", eqmt_id);
//			
//			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
//			inMap.put("anys_sys_cd", "KSEC");
//			
//			list = riskStatisticsService.listEqmtRiskKSECApp(inMap);
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return retMap;
//	}
	
	
	//실시간 분석 데이터- 영상데이터분석위험도 앱용 이상원인
//	@GetMapping(value = "/app/rtimeeqmtkseccause/{eqmt_id}/{anys_ymd}")
//	public Map listEqmtRiskKSECAppCause(@PathVariable("eqmt_id") String eqmt_id,
//			@PathVariable("anys_ymd") String anys_ymd, @RequestParam Map<String, String> inMap){
//		logger.debug("listEqmtRiskKSECAppCause:"+inMap);
//		List<Map> list = null;
//		Map retMap = new HashMap();
//		try {
//			/*
//			 * String lastymdH = riskStatisticsService.lastDateYmdH(inMap);
//			 * inMap.put("anys_ymdh", lastymdH);
//			 */
//			inMap.put("strt_anys_ymdh", anys_ymd+"00");
//			inMap.put("anys_ymdh", anys_ymd+"24");
//			inMap.put("eqmt_id", eqmt_id);
//			
//			inMap.put("risk_cls_cds", "'RC0002'");
//			inMap.put("anys_sys_cd", "KSEC");
//			list = riskStatisticsService.listEqmtRiskKSECApp(inMap);
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return retMap;
//	}
	
	
	//위험도 예측 24시간
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
			
			//24시간
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractEqmtRiskTop5(inMap);
			returnMap.put("hour24",list);
			
			//7일
			LocalDateTime day = dt.plusHours(24*7);
			inMap.put("end_anys_ymdh", day.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractEqmtRiskTop5(inMap);
			returnMap.put("day7",list);
			
			//4주
			LocalDateTime week = dt.plusHours(24*7*4);
			inMap.put("end_anys_ymdh", week.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractEqmtRiskTop5(inMap);
			returnMap.put("week4",list);
			
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return returnMap;
	}
	
	
	
	//위험도 예측 24시간
	@GetMapping(value = "/practeqmtrisktop10forsnro")
	public Map listPreEqmtRiskTop5forsnro(@RequestParam Map<String, String> inMap){
		logger.debug("listEqmtRiskKSEC:"+inMap);
		List<Map> list = null;
		Map<String,List<Map>> returnMap = new HashMap(); 
		try {
			
			String lastymdH = Utils.NVL( inMap.get("anys_ymdh") );
			LocalDateTime dt = LocalDateTime.now();
			
			//테스트용 
			if(lastymdH.length()>9) {
				int yy = Integer.parseInt(lastymdH.substring(0,4));
				int mm = Integer.parseInt(lastymdH.substring(4,6));
				int dd = Integer.parseInt(lastymdH.substring(6,8));
				int hh = Integer.parseInt(lastymdH.substring(8,10));
				logger.debug("yy:"+yy +"  mm:"+mm+" dd:"+dd+"  hh:"+hh);
				
				dt = LocalDateTime.of(yy, mm, dd, hh, 0);
			}
			
			LocalDateTime hourse = dt.plusHours(21);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			//24시간
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("topidx", "10");
			
			list = riskStatisticsService.listPractEqmtRiskTop5(inMap);
			returnMap.put("hour24",list);
			
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
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
	
	
	//위험도 예측 > 설비> 24  페이징리스트practeeqmtHour24
	@GetMapping(value = "/pagemaxpracteeqmtHour")
	public Map pagePractEqmtRiskHourMax(
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
		LocalDateTime hourse = dt.plusHours(21);
		String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		searchMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
		searchMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
		
		logger.debug("searchMap param:"+searchMap);
		try {
			List<Map> list =  riskStatisticsService.pagePractEqmtRiskHourMax(searchMap);
			restulMap.put("list", list);
			restulMap.put("totalcount", riskStatisticsService.countPractEqmtRiskHourMax(searchMap));
			restulMap.put("pagesize", pagesize);
			restulMap.put("pageindex", pageindex);
			Utils.addSuccessMsg(restulMap);
			
		}catch(Throwable e) {
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}
	
	//위험도 예측 > 설비> 7일>  페이징리스트
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
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}
	
	
	//위험도 예측 > 설비> 4주>  페이징리스트
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
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}
		
	//위험도 예측> 설비> 24시간
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
				throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
			}
			return retMap;
		}
	
	
	//위험도 예측> 설비> 24시간 시나리오
	@GetMapping(value = "/practeeqmtHour24forsnro")
	public Map listPractEqmtRiskHour24forsnro(@RequestParam Map<String, String> inMap){
		logger.debug("practeeqmtHour24:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			LocalDateTime dt = LocalDateTime.now();
			
			String lastymdH = Utils.NVL( inMap.get("anys_ymdh") );
			//테스트용 
			if(lastymdH.length()>9) {
				int yy = Integer.parseInt(lastymdH.substring(0,4));
				int mm = Integer.parseInt(lastymdH.substring(4,6));
				int dd = Integer.parseInt(lastymdH.substring(6,8));
				int hh = Integer.parseInt(lastymdH.substring(8,10));
				logger.debug("yy:"+yy +"  mm:"+mm+" dd:"+dd+"  hh:"+hh);
				dt = LocalDateTime.of(yy, mm, dd, hh, 0);
			}
			
			LocalDateTime hourse = dt.plusHours(21);
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractEqmtRiskHour24ForSnro(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//위험도 예측> 설비> 7일
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//위험도 예측> 설비> 4주
	@GetMapping(value = "/practeeqmtWeek4")
	public Map listPractEqmtRiskWeek4(@RequestParam Map<String, String> inMap){
		logger.debug("listPractEqmtRiskWeek4:"+inMap);
		List<Map> list = new ArrayList<Map>();
		Map retMap = new HashMap();
		try {
			//일주일씩 4번 호출.
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime dayHour = dt.plusDays(6);			
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list.add(riskStatisticsService.getPractEqmtRiskDay7forWeek(inMap));
			
			dt = dayHour.plusHours(1);
			dayHour = dayHour.plusDays(7);		
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list.add(riskStatisticsService.getPractEqmtRiskDay7forWeek(inMap));
			
			dt = dayHour.plusHours(1);
			dayHour = dayHour.plusDays(7);		
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list.add(riskStatisticsService.getPractEqmtRiskDay7forWeek(inMap));
			
			dt = dayHour.plusHours(1);
			dayHour = dayHour.plusDays(7);		
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list.add(riskStatisticsService.getPractEqmtRiskDay7forWeek(inMap));
			
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
	//위험도 이력> 생산공정목록
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
			dt = dt.minusDays(1); //30일 이전까지
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
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}
	
	//위험도 이력 - 통합위험도 생산공정-시간별
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
	
	//위험도 이력 - 통합위험도 생산공정(일별)
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
		
	
	
	
	//위험도 이력 - 통합위험도 생산공정 맵형식
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return resultMap;
	}
	
	//위험도이력 설비별 상세 3시간
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//위험도이력 설비별 상세 일별
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//위험도이력 설비별 상세 3시간 맵형식
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return resultMap;
	}
	
	
	//위험도이력 - 센서데이터 분석 위험도 3시간 시나리오
		@GetMapping(value = "/listriskteqmtcrighisforsnro")
		public Map listriskteqmtcrighisforsnro(@RequestParam Map<String, String> inMap){
			logger.debug("listRiskCRIG3HourHistory:"+inMap);
			List<Map> retList = new ArrayList();
			Map retMap = new HashMap();
			try {
				List<Map> list = riskStatisticsService.listRiskCRIG3HourHistoryForSnro(inMap);
				
//				String preYmd="";
//				String preQTime = "";
//				Map innerMap = new HashMap();
//				for(Map<String,String> tMap : list) {
//					String curYmd = tMap.get("ymd");
//					String curQTime = tMap.get("qtime");
//					
//					if(!preYmd.equals(curYmd) || !preQTime.equals(curQTime)) {
//						innerMap = new HashMap();
//						retList.add(innerMap);
//						innerMap.put("ymd", curYmd);  //날짜
//						innerMap.put("qtime", curQTime); //시간
//					}
//					innerMap.put(tMap.get("risk_type_id"), tMap.get("risk_rate")); //시간
//					
//					preYmd = curYmd;
//					preQTime = curQTime;
//				}
				
				retMap.put("list",list);
				Utils.addSuccessMsg(retMap);
			}catch(Throwable e) { 
				if(e instanceof BusinessException) throw e;
				e.printStackTrace();
				throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
			}
			return retMap;
		}
		
	
	//위험도이력 - 센서데이터 분석 위험도 3시간
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
					innerMap.put("ymd", curYmd);  //날짜
					innerMap.put("qtime", curQTime); //시간
				}
				innerMap.put(tMap.get("risk_type_id"), tMap.get("risk_rate")); //시간
				
				preYmd = curYmd;
				preQTime = curQTime;
			}
			
			retMap.put("list",retList);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//위험도이력 - 센서데이터 분석 위험도 3시간 맵형식
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return resultMap;
	}
		
		
	
	//위험도 이력 - 예지보전 위험도
	@GetMapping(value = "/listriskteqmtpartdbhis")
	public Map listRiskPARTDB3HourHistory(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskPARTDB3HourHistory:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			list = riskStatisticsService.listRiskPARTDB3HourHistory(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
	
	//위험도 이력 - 예지보전 위험도 시나리오
	@GetMapping(value = "/listriskteqmtpartdbhisforsnro")
	public Map listRiskPARTDB3HourHistoryforsnro(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskPARTDB3HourHistoryforsnro:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			list = riskStatisticsService.listRiskPARTDB3HourHistoryForsnro(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
	//위험도 이력 - 예지보전 위험도 앱용
//	@GetMapping(value = "/app/listriskteqmtpartdbhis")
//	public Map listRiskPARTDB3HourHistoryApp(@RequestParam Map<String, String> inMap){
//		logger.debug("listRiskPARTDB3HourHistory:"+inMap);
//		List<Map> list = null;
//		Map retMap = new HashMap();
//		try {
//			
//			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
//			inMap.put("anys_sys_cd", "PARTDB");
//			
//			//end_anys_ymd 추가
//			list = riskStatisticsService.listRiskPARTDB3HourHistoryApp(inMap);
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return retMap;
//	}
	
	//위험도 이력 - 영상데이터 분석 위험도
	@GetMapping(value = "/listriskteqmtksec3his")
	public Map listRiskKSEC3HourHistory(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskKSEC3HourHistory:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			list = riskStatisticsService.listRiskKSEC3HourHistory(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//위험도 이력 - 영상데이터 분석 위험도 시나리오
	@GetMapping(value = "/listriskteqmtksec3hisforsnro")
	public Map listRiskKSEC3HourHistoryforsnro(@RequestParam Map<String, String> inMap){
		logger.debug("listRiskKSEC3HourHistoryforsnro:"+inMap);
		List<Map> list = null;
		Map retMap = new HashMap();
		try {
			list = riskStatisticsService.listRiskKSEC3HourHistoryForsnro(inMap);
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//위험도 이력 - 영상데이터 분석 위험도 앱용
//	@GetMapping(value = "/app/listriskteqmtksec3his")
//	public Map listRiskKSEC3HourHistoryApp(@RequestParam Map<String, String> inMap){
//		logger.debug("listRiskKSEC3HourHistory:"+inMap);
//		List<Map> list = null;
//		Map retMap = new HashMap();
//		try {
//			
//			inMap.put("risk_cls_cds", "'RC0003','RC0001'");
//			inMap.put("anys_sys_cd", "KSEC");
//			
//			list = riskStatisticsService.listRiskKSEC3HourHistoryApp(inMap);
//			retMap.put("list",list);
//			Utils.addSuccessMsg(retMap);
//		}catch(Throwable e) { 
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
//		}
//		return retMap;
//	}
	
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
//			restulMap.put("indexlist", "하단 인덱스 정보.");
//		}catch(Throwable e) {
//			if(e instanceof BusinessException) throw e;
//			e.printStackTrace();
//			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
//		}
//		return restulMap;
//	}
	
	
	//위험도 예측 > 시설>24시설전체
	@GetMapping(value = "/practfactyriskhour24")
	public Map getPractFactyRiskHour24(@RequestParam Map<String, String> inMap){
		logger.debug("practfactyriskhour24:"+inMap);
		Map returnMap = new HashMap(); 
		try {
			
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusHours(23);
			//2일시간
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			returnMap.put("all", riskStatisticsService.getPractFactyRiskAll(inMap));
			returnMap.put("list",riskStatisticsService.listPractFactyRiskHour24(inMap));
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return returnMap;
	}
	
	//위험도 예측 > 시설>7일 전체
	@GetMapping(value = "/practfactyriskday7")
	public Map getPractFactyRiskDay7(@RequestParam Map<String, String> inMap){
		logger.debug("practfactyriskday7:"+inMap);
		Map returnMap = new HashMap(); 
		try {
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusDays(7);
			//2일시간
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"00");
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"24");
			returnMap.put("all", riskStatisticsService.getPractFactyRiskAll(inMap));
			returnMap.put("list",riskStatisticsService.listPractFactyRiskDay7(inMap));
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return returnMap;
	}
	
	
	//위험도 예측 >  시설> 4주 전체
	@GetMapping(value = "/practfactyriskweek4") 
	public Map getPractFactyRiskDay7forWeek(@RequestParam Map<String, String> inMap){
		logger.debug("practfactyriskweek4:"+inMap);
		List<Map> list = new ArrayList<Map>();
		Map returnMap = new HashMap(); 
		try {
			//일주일씩 4번 호출.
			LocalDateTime dt =LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
			String startHH = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			LocalDateTime dayHour = dt.plusDays(7);			
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			list.add(riskStatisticsService.getPractFactyRiskDay7forWeek(inMap));
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			list.add(riskStatisticsService.getPractFactyRiskDay7forWeek(inMap));
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			list.add(riskStatisticsService.getPractFactyRiskDay7forWeek(inMap));
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			list.add(riskStatisticsService.getPractFactyRiskDay7forWeek(inMap));
			
			inMap.put("anys_ymd", startHH.substring(0,8));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			returnMap.put("all", riskStatisticsService.getPractFactyRiskAll(inMap));
			returnMap.put("list", list);
			Utils.addSuccessMsg(returnMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return returnMap;
	}
	
	//위험도 예측 > 생산공정> 리스트 >24시간
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
		//2일시간
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
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}
	
	
	//위험도 예측 > 생산공정> 리스트 >7일
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
		//2일시간
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
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}
		
	
	//위험도 예측 > 생산공정> 리스트 >4주
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
		//2일시간
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
			throw new BusinessException(CodeContrants.ERR_SELECT," 페이징 조회 오류",e.toString());
		}
		return restulMap;
	}
		
	
	
		
	
	
	//위험도 예측 > 생산공정> 24시설전체
	@GetMapping(value = "/practprocsriskhour24")
	public Map listPractProcsRiskHour24(@RequestParam Map<String, String> inMap){
		logger.debug("practfactyriskhour24:"+inMap);
		List<Map> list = new ArrayList(); 
		Map retMap = new HashMap();
		try {
			
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusHours(23);
			//2일시간
			inMap.put("anys_ymdh", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			inMap.put("end_anys_ymdh", hourse.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
			list = riskStatisticsService.listPractProcsRiskHour24(inMap);
			
			
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	//위험도 예측 >생산공정>  7일 전체
	@GetMapping(value = "/practprocsriskday7")
	public Map getPractProcsRiskDay7(@RequestParam Map<String, String> inMap){
		logger.debug("practprocsriskday7:"+inMap);
		List<Map> list = new ArrayList(); 
		Map retMap = new HashMap();
		try {
			LocalDateTime dt = LocalDateTime.now();
			LocalDateTime hourse = dt.plusDays(7);
			//2일시간
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
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
	//위험도 예측 > 생산공정> 4주 전체
	@GetMapping(value = "/practprocsriskweek4") 
	public Map getPractProcsRiskDay7forWeek(@RequestParam Map<String, String> inMap){
		logger.debug("practprocsriskweek4:"+inMap);
		List<Map> list = new ArrayList(); 
		Map retMap = new HashMap();
		try {
			//일주일씩 4번 호출.
			LocalDateTime dt =LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
			String startHH = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			
			LocalDateTime dayHour = dt.plusDays(7);			
			String strt_anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			list.add(riskStatisticsService.getPractProcsRiskDay7forWeek(inMap));
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			list.add(riskStatisticsService.getPractProcsRiskDay7forWeek(inMap));
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			list.add(riskStatisticsService.getPractProcsRiskDay7forWeek(inMap));
			
			dt = dayHour.plusDays(1);
			dayHour = dayHour.plusDays(7);		
			inMap.put("anys_ymd", dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			inMap.put("end_anys_ymd", dayHour.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			list.add(riskStatisticsService.getPractProcsRiskDay7forWeek(inMap));
			
			retMap.put("list",list);
			Utils.addSuccessMsg(retMap);
		}catch(Throwable e) { 
			if(e instanceof BusinessException) throw e;
			e.printStackTrace();
			throw new BusinessException(CodeContrants.ERR_SELECT,"리스트조회오류",e.toString());
		}
		return retMap;
	}
	
	
}