package com.kies.sgsc.schedule;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.service.EqmtRiskAnayPractLoadDailyService;
import com.kies.sgsc.service.EqmtRiskAnayPractLoadHourlyService;
import com.kies.sgsc.service.EqmtRiskAnayRtmeLoadHourlyService;
import com.kies.sgsc.service.EqmtRiskAnayRtmeLoadMinService;
import com.kies.sgsc.service.EquipmentService;
import com.kies.sgsc.service.FactyRiskAnayPractLoadDailyService;
import com.kies.sgsc.service.FactyRiskAnayPractLoadHourlyService;
import com.kies.sgsc.service.FactyRiskAnayRtmeLoadHourlyService;
import com.kies.sgsc.service.FactyRiskAnayRtmeLoadMinService;
import com.kies.sgsc.service.GasRiskPrpsService;
import com.kies.sgsc.service.ProcesRiskAnayPractLoadDailyService;
import com.kies.sgsc.service.ProcesRiskAnayPractLoadHourlyService;
import com.kies.sgsc.service.ProcesRiskAnayRtmeLoadHourlyService;
import com.kies.sgsc.service.ProcesRiskAnayRtmeLoadMinService;
import com.kies.sgsc.service.RiskAnayLoadService;
import com.kies.sgsc.service.RiskNotiService;
import com.kies.sgsc.service.RiskTrhldManageService;
import com.kies.sgsc.service.RiskTypeService;
import com.kies.sgsc.servlet.SessionUtil;
import com.kies.sgsc.socket.SgscWebSocketListener;
import com.kies.sgsc.socket.SgscWebSocketManager;

@Component
public class SgscScheduler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	EquipmentService equipmentService;
	
	@Autowired
	RiskTypeService typeService;
	
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
	GasRiskPrpsService gasRiskPrpsService;
	
	@Autowired
	RiskNotiService riskNotiService;
	
	@Autowired
	RiskAnayLoadService riskAnayLoadService;
	
	static volatile boolean isTrue = true;
	static volatile int CheckNum = 0;
	
	public static Map<Integer,Integer[]> NUM_TABLE = new HashMap<Integer,Integer[]>();
	static {
		for(int i= 0;i<95;i++) NUM_TABLE.put(i,new Integer[] {0,3});		
		for(int i=95;i<96;i++) NUM_TABLE.put(i,new Integer[] {3,3});		
		for(int i=96;i<97;i++) NUM_TABLE.put(i,new Integer[] {9,3});		
		for(int i=97;i<98;i++) NUM_TABLE.put(i,new Integer[] {12,3});		
//		for(int i=94;i<95;i++) NUM_TABLE.put(i,new Integer[] {15,3});		
//		for(int i=95;i<96;i++) NUM_TABLE.put(i,new Integer[] {18,3});		
//		for(int i=96;i<97;i++) NUM_TABLE.put(i,new Integer[] {21,3});		
//		for(int i=97;i<98;i++) NUM_TABLE.put(i,new Integer[] {24,3});		
		for(int i=98;i<99;i++) NUM_TABLE.put(i,new Integer[] {15,5});		
		for(int i=99;i<100;i++) NUM_TABLE.put(i,new Integer[] {20,30});
		for(int i=100;i<=100;i++) NUM_TABLE.put(i,new Integer[] {50,48});
	}
	
	static String [] tmpErrorRisk = new String[] {
		"NR0000:정상:1",
		"ER0001:고장/발생:2"
	};
	
	
	/*
	 * *           *　　　　　　*　　　　　　*　　　　　　*　　　　　　*
	초(0-59)   분(0-59)　　시간(0-23)　　일(1-31)　　월(1-12)　　요일(0-7) 
	각 별 위치에 따라 주기를 다르게 설정 할 수 있다.
	순서대로 초-분-시간-일-월-요일 순이다. 그리고 괄호 안의 숫자 범위 내로 별 대신 입력 할 수도 있다.
	요일에서 0과 7은 일요일이며, 1부터 월요일이고 6이 토요일이다.
	 */
	//@Scheduled(cron = "0 0/1 * * * *")
	public void cronJobSch() {
		
		SessionUtil.setSchduler("Y");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdf.format(now);
		System.out.println("Java cron job expression:: " + strDate);
		
		//웹소켓 테스트--------------
		isTrue = !isTrue;
		CheckNum ++;
		List<SgscWebSocketListener> sgscWebSocketListener = SgscWebSocketManager.getListeners();
		try {
//			sgscWebSocketListener.stream().forEach(a->{
//				if(a.getUser_id()==null) return;
//				String user_id = a.getUser_id();
//				String lastNum = user_id.substring(user_id.length()-1);
//				
//				int intNum = Integer.parseInt(lastNum);
//				if(isTrue && intNum%2 == 0) {
//					SgscWebSocketManager.sendMessage(user_id, "user_id:["+user_id+"]"+ strDate);
//				}else if(!isTrue && intNum%2 == 1) {
//					SgscWebSocketManager.sendMessage(user_id, "user_id:["+user_id+"]"+ strDate);
//				}
//			});
//			
//			if(CheckNum%10 ==0) {
//				SgscWebSocketManager.broadcast("이건 전체 메시지야");
//			}
			
			makeSimulData();
			
		}catch(Throwable e) {
			e.addSuppressed(e);
			StringWriter writer = new StringWriter();
			PrintWriter pwriter = new PrintWriter(writer);
			e.printStackTrace(pwriter);
			logger.error(writer.toString());
		}
		//웹소켓 테스트--------------
	}
	
	
	/**시뮬레이션 데이터 생성**/
	public void makeSimulData() {
		
		long startTime = System.currentTimeMillis();
		
		LocalDateTime dt = LocalDateTime.now();
		//dt =dt.minusYears(1);
		Map paramMap = new HashMap();
		
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
		eqMap.put("risk_cls_cd", "RC0006"); //통합설비위험도
		riskTypeList.addAll(typeService.listRiskType(eqMap));
		
		riskTypeList = riskTypeList.stream().filter(p->{
			return !((String)p.get("risk_type_id")).startsWith("KIES2") 
			  && !((String)p.get("risk_type_id")).startsWith("KSEC");  //내일 분석해서 세팅 처리 확인.
		}).collect(Collectors.toList());
		
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		String anys_ymdh = anys_ymdhi.substring(0,10);
		
		//1.실시간 설비 분단위 적재
		Map eqmtRiskAnayRtmeLoadMinMap = new HashMap();
		Map eqmtRiskAnayRtmeLoadHourlyMap = new HashMap();
		
		Map eqmtRiskAnayPractLoadHourMap = new HashMap();
		Map eqmtRiskAnayPractLoadDayMap = new HashMap();
		
		for(Map eMap : eqList) {
			eqmtRiskAnayRtmeLoadMinMap.clear();
			eqmtRiskAnayRtmeLoadHourlyMap.clear();
			
			paramMap.clear();
			paramMap.put("target_type_cd","TT0003");//설비
			paramMap.put("risk_type_id","KIES103");  //실시간  203 예측
			paramMap.put("target_id",eMap.get("eqmt_id"));
			List<Map> thrlMap = riskTrhldManageService.listRiskTrhldManage(paramMap);
			if(thrlMap==null||thrlMap.size()==0) {
				paramMap.clear();
				paramMap.put("target_type_cd","TT0999");
				paramMap.put("risk_type_id","DEFAULT");  
				paramMap.put("target_id","DEFAULT");
			}
			
			eqmtRiskAnayRtmeLoadMinMap.put("anys_ymdhi",anys_ymdhi);
			logger.debug("########################################### eqmt_id:"+eMap.get("eqmt_id") );
			eqmtRiskAnayRtmeLoadMinMap.put("eqmt_id",eMap.get("eqmt_id"));
			
			double maxRiskNum = 0D;
			String maxRiskStepCd = "";
			int riskLevel = 0;
			String risk_step_nm = "";
			for(Map riskTypeMap : riskTypeList) {
				eqmtRiskAnayRtmeLoadMinMap.put("anys_sys_cd",riskTypeMap.get("anys_sys_cd"));
				eqmtRiskAnayRtmeLoadMinMap.put("risk_type_id",riskTypeMap.get("risk_type_id"));
				eqmtRiskAnayRtmeLoadMinMap.put("risk_unit",riskTypeMap.get("unit"));
				
				eqmtRiskAnayRtmeLoadMinMap.put("risk_val","");
				eqmtRiskAnayRtmeLoadMinMap.put("risk_num",null);
				
				String[] riskAry = null;
				int rdn = new Random().nextInt(100);
				if(riskTypeMap.get("use_yn").equals("N")) {
					if(rdn>95) riskAry = tmpErrorRisk[1].split(":");
					else riskAry = tmpErrorRisk[0].split(":");
					
					if("PART103".equals(eqmtRiskAnayRtmeLoadMinMap.get("risk_type_id"))){ //잔여수명
						LocalDateTime p103 = dt.plusDays(200+ new Random().nextInt(800));
						riskAry[1] = p103.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
					}
					
					if("KSEC104".equals(eqmtRiskAnayRtmeLoadMinMap.get("risk_type_id"))){ //항공사진
						riskAry[1] = "http://118.129.135.145:2041/api/getFile/jpg/1604366273445";
					} 
					
					eqmtRiskAnayRtmeLoadMinMap.put("risk_val",riskAry[1]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskAry[0]);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskAry[2]);
				}else {
					double riskNum = 0;
					if( ((String)eqmtRiskAnayRtmeLoadMinMap.get("risk_type_id")).startsWith("KIES") ) {
						//logger.error(eMap.get("eqmt_id")+"_"+anys_ymdhi+" "+riskTypeMap.get("risk_type_id") + ",riskNum:"+riskNum+", maxRiskNum:"+maxRiskNum);
						riskNum = maxRiskNum;
					}else {
						Integer[] cRiskNum = SgscScheduler.NUM_TABLE.get(rdn);
						//logger.error("rdn:"+rdn+"  "+ (cRiskNum!=null?Arrays.asList(cRiskNum):"na"));
						riskNum = (cRiskNum[0] + (cRiskNum[1]>0?new Random().nextInt(cRiskNum[1]):0) +0D) + (new Random().nextInt(99)/100D);
						
						if(maxRiskNum < riskNum) {
							maxRiskNum = riskNum;
						}
						//logger.error(eMap.get("eqmt_id")+"_"+anys_ymdhi+" "+riskTypeMap.get("risk_type_id") + ", riskNum:"+riskNum+", maxRiskNum:"+maxRiskNum);
					}
					eqmtRiskAnayRtmeLoadMinMap.put("risk_num",riskNum);
					paramMap.put("risk_num",riskNum);
					Map calMap = riskTrhldManageService.getCalcRiskTrhld(paramMap);
					riskLevel = calMap.get("risk_step_lvl")==null?0:(Integer)calMap.get("risk_step_lvl");
					String riskStepCd = Utils.NVL(calMap.get("risk_step_cd"),"");
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",riskStepCd);
					eqmtRiskAnayRtmeLoadMinMap.put("risk_step_lvl",riskStepCd.length()>0?riskStepCd.substring(riskStepCd.length()-1):"");
					
					if( ((String)eqmtRiskAnayRtmeLoadMinMap.get("risk_type_id")).startsWith("KIES") ) {
						risk_step_nm = Utils.NVL( calMap.get("risk_step_nm"),"");
						maxRiskStepCd = Utils.NVL( calMap.get("risk_step_cd"),"");
					}
				}
				
				eqmtRiskAnayRtmeLoadMinService.insertEqmtRiskAnayRtmeLoadMin(eqmtRiskAnayRtmeLoadMinMap);
				
				//가스위험성 제안
				if( ((String)eqmtRiskAnayRtmeLoadMinMap.get("risk_type_id")).startsWith("KIES") ) {
					//logger.error("risk_type_id:"+ eqmtRiskAnayRtmeLoadMinMap.get("risk_type_id")+"  riskLevel:"+riskLevel);
					if(riskLevel > 5) {
						eqmtRiskAnayRtmeLoadMinMap.put("props_ymdhis", dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
						gasRiskPrpsService.insertGasRiskPrps(eqmtRiskAnayRtmeLoadMinMap);
					
						//실시간 위험도 메시지전송
						eqmtRiskAnayRtmeLoadMinMap.put("procs_id",eMap.get("procs_id"));
						eqmtRiskAnayRtmeLoadMinMap.put("facty_id",eMap.get("facty_id"));
						eqmtRiskAnayRtmeLoadMinMap.put("auth_sid",null); //전체전송.
						eqmtRiskAnayRtmeLoadMinMap.put("send_type_cd","SD0001"); //발송타입코드
						eqmtRiskAnayRtmeLoadMinMap.put("cbm_fixd_rate",maxRiskNum); //위험율.
						eqmtRiskAnayRtmeLoadMinMap.put("risk_step_cd",maxRiskStepCd); //위험도.
						
						
						//생산공정B1 >  TANK-09-03 화재 위험도가 경고 32.7% 상태입니다.
						StringBuffer msgBuffer = new StringBuffer();
						msgBuffer.append((String)eMap.get("procs_nm")).append(" > ").append((String)eMap.get("eqmt_nm"));
						msgBuffer.append(" 통합 위험도가 ").append(risk_step_nm).append(" ").append( maxRiskNum).append("% 상태입니다.");
						eqmtRiskAnayRtmeLoadMinMap.put("send_msg",msgBuffer.toString()); //메시지.
						try {
							logger.error("전송메시지:"+msgBuffer.toString());
							riskNotiService.sendMessage(eqmtRiskAnayRtmeLoadMinMap);
						}catch(Throwable e) {
							e.printStackTrace();
						}
					}
					
				}

				
				//2.실시간 설비 시단위 적재
				eqmtRiskAnayRtmeLoadHourlyMap.putAll(eqmtRiskAnayRtmeLoadMinMap); 
				eqmtRiskAnayRtmeLoadHourlyMap.put("anys_ymdh",anys_ymdh);
				Map houseMap = eqmtRiskAnayRtmeLoadHourlyService.getEqmtRiskAnayRtmeLoadHourly_eqmt_id(eqmtRiskAnayRtmeLoadHourlyMap);
				if(houseMap==null||houseMap.size()==0) {
					eqmtRiskAnayRtmeLoadHourlyService.insertEqmtRiskAnayRtmeLoadHourly(eqmtRiskAnayRtmeLoadHourlyMap);
				}else {
					String oldVal = "";
					String newVal =	"";
					if(riskTypeMap.get("use_yn").equals("N")) {
						oldVal = (String)houseMap.get("risk_val");  
						newVal = (String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_val");
					}else {
						oldVal = String.valueOf(houseMap.get("risk_num"));
						newVal = String.valueOf(eqmtRiskAnayRtmeLoadHourlyMap.get("risk_num"));
					}
					if("MAX".equals(riskTypeMap.get("grp_cal_cd")) ){
						if(oldVal.compareTo(newVal)  < 0) {
							eqmtRiskAnayRtmeLoadHourlyService.updateEqmtRiskAnayRtmeLoadHourly(eqmtRiskAnayRtmeLoadHourlyMap);
						}
					}else {
						if(oldVal.compareTo(newVal)  > 0) {
							eqmtRiskAnayRtmeLoadHourlyService.updateEqmtRiskAnayRtmeLoadHourly(eqmtRiskAnayRtmeLoadHourlyMap);
						}
					}
				}
				
				//3.예측 설비 시단위 적재
				eqmtRiskAnayPractLoadHourMap.clear();
				LocalDateTime w4dt = dt.plusHours(24*7*4); //4주 예측 건으로 세팅.
				String pract_anys_ymdh = w4dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
				eqmtRiskAnayPractLoadHourMap.putAll(eqmtRiskAnayRtmeLoadHourlyMap);
				eqmtRiskAnayPractLoadHourMap.put("anys_ymdh", pract_anys_ymdh);
				
				if( ((String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_type_id")).startsWith("KIES")) {
					eqmtRiskAnayPractLoadHourMap.put("risk_type_id","KIES203");
				}
				houseMap =  eqmtRiskAnayPractLoadHourlyService.getEqmtRiskAnayPractLoadHourly_eqmt_id(eqmtRiskAnayPractLoadHourMap);
				if(houseMap==null||houseMap.size()==0) {
					eqmtRiskAnayPractLoadHourlyService.insertEqmtRiskAnayPractLoadHourly(eqmtRiskAnayPractLoadHourMap);
				}else {
					String oldVal = "";
					String newVal =	"";
					if(riskTypeMap.get("use_yn").equals("N")) {
						oldVal = (String)houseMap.get("risk_val");  
						newVal = (String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_val");
					}else {
						oldVal = String.valueOf(houseMap.get("risk_num"));
						newVal = String.valueOf(eqmtRiskAnayRtmeLoadHourlyMap.get("risk_num"));
					}
					if("MAX".equals(riskTypeMap.get("grp_cal_cd")) ){
						if(oldVal.compareTo(newVal)  < 0) {
							eqmtRiskAnayPractLoadHourlyService.updateEqmtRiskAnayPractLoadHourly(eqmtRiskAnayPractLoadHourMap);
						}
					}else {
						if(oldVal.compareTo(newVal)  > 0) {
							eqmtRiskAnayPractLoadHourlyService.updateEqmtRiskAnayPractLoadHourly(eqmtRiskAnayPractLoadHourMap);
						}
					}
				}
				
				//4.예측 설비 일단위 적재
				eqmtRiskAnayPractLoadDayMap.clear();
				String pract_anys_ymd =pract_anys_ymdh.substring(0,8);
				eqmtRiskAnayPractLoadDayMap.putAll(eqmtRiskAnayRtmeLoadHourlyMap);
				eqmtRiskAnayPractLoadDayMap.put("anys_ymd", pract_anys_ymd);
				if( ((String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_type_id")).startsWith("KIES")) {
					eqmtRiskAnayPractLoadDayMap.put("risk_type_id","KIES203");
				}
				houseMap =  eqmtRiskAnayPractLoadDailyService.getEqmtRiskAnayPractLoadDaily_risk_type_id(eqmtRiskAnayPractLoadDayMap);
				if(houseMap==null||houseMap.size()==0) {
					eqmtRiskAnayPractLoadDailyService.insertEqmtRiskAnayPractLoadDaily(eqmtRiskAnayPractLoadDayMap);
				}else {
					String oldVal = "";
					String newVal =	"";
					if(riskTypeMap.get("use_yn").equals("N")) {
						oldVal = (String)houseMap.get("risk_val");  
						newVal = (String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_val");
					}else {
						oldVal = String.valueOf(houseMap.get("risk_num"));
						newVal = String.valueOf(eqmtRiskAnayRtmeLoadHourlyMap.get("risk_num"));
					}
					if("MAX".equals(riskTypeMap.get("grp_cal_cd")) ){
						if(oldVal.compareTo(newVal)  < 0) {
							eqmtRiskAnayPractLoadDailyService.updateEqmtRiskAnayPractLoadDaily(eqmtRiskAnayPractLoadDayMap);
						}
					}else {
						if(oldVal.compareTo(newVal)  > 0) {
							eqmtRiskAnayPractLoadDailyService.updateEqmtRiskAnayPractLoadDaily(eqmtRiskAnayPractLoadDayMap);
						}
					}
				}
				
				
				
//				////////////////////////////////////////////////////////////////////////
//				////////////////////////////// 임시로 사용 ////////////////////////////
//				//3.예측 설비 시단위 적재
//				eqmtRiskAnayPractLoadHourMap.clear();
//				w4dt = dt.plusDays(3); //3일 예측 건으로 세팅.
//				pract_anys_ymdh = w4dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//				eqmtRiskAnayPractLoadHourMap.putAll(eqmtRiskAnayRtmeLoadHourlyMap);
//				eqmtRiskAnayPractLoadHourMap.put("anys_ymdh", pract_anys_ymdh);
//				
//				if( ((String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_type_id")).startsWith("KIES")) {
//					eqmtRiskAnayPractLoadHourMap.put("risk_type_id","KIES203");
//				}
//				houseMap =  eqmtRiskAnayPractLoadHourlyService.getEqmtRiskAnayPractLoadHourly_eqmt_id(eqmtRiskAnayPractLoadHourMap);
//				if(houseMap==null||houseMap.size()==0) {
//					eqmtRiskAnayPractLoadHourlyService.insertEqmtRiskAnayPractLoadHourly(eqmtRiskAnayPractLoadHourMap);
//				}else {
//					String oldVal = "";
//					String newVal =	"";
//					if(riskTypeMap.get("use_yn").equals("N")) {
//						oldVal = (String)houseMap.get("risk_val");  
//						newVal = (String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_val");
//					}else {
//						oldVal = String.valueOf(houseMap.get("risk_num"));
//						newVal = String.valueOf(eqmtRiskAnayRtmeLoadHourlyMap.get("risk_num"));
//					}
//					
//					if("MAX".equals(riskTypeMap.get("grp_cal_cd")) ){
//						if(oldVal.compareTo(newVal)  < 0) {
//							//logger.error("MAX pract_anys_ymdh:"+eqmtRiskAnayPractLoadHourMap.get("anys_ymdh")+"["+eqmtRiskAnayPractLoadHourMap+"]  oldVal:"+oldVal+"  newVal:"+newVal);
//							eqmtRiskAnayPractLoadHourlyService.updateEqmtRiskAnayPractLoadHourly(eqmtRiskAnayPractLoadHourMap);
//						}
//					}else {
//						if(oldVal.compareTo(newVal)  > 0) {
//							//logger.error("MIN pract_anys_ymdh:"+eqmtRiskAnayPractLoadHourMap.get("anys_ymdh")+"["+eqmtRiskAnayPractLoadHourMap+"]  oldVal:"+oldVal+"  newVal:"+newVal);
//							eqmtRiskAnayPractLoadHourlyService.updateEqmtRiskAnayPractLoadHourly(eqmtRiskAnayPractLoadHourMap);
//						}
//					}
//				}
//				
//				//4.예측 설비 일단위 적재
//				eqmtRiskAnayPractLoadDayMap.clear();
//				pract_anys_ymd =pract_anys_ymdh.substring(0,8);
//				eqmtRiskAnayPractLoadDayMap.putAll(eqmtRiskAnayRtmeLoadHourlyMap);
//				eqmtRiskAnayPractLoadDayMap.put("anys_ymd", pract_anys_ymd);
//				if( ((String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_type_id")).startsWith("KIES")) {
//					eqmtRiskAnayPractLoadDayMap.put("risk_type_id","KIES203");
//				}
//				houseMap =  eqmtRiskAnayPractLoadDailyService.getEqmtRiskAnayPractLoadDaily_risk_type_id(eqmtRiskAnayPractLoadDayMap);
//				if(houseMap==null||houseMap.size()==0) {
//					eqmtRiskAnayPractLoadDailyService.insertEqmtRiskAnayPractLoadDaily(eqmtRiskAnayPractLoadDayMap);
//				}else {
//					String oldVal = "";
//					String newVal =	"";
//					if(riskTypeMap.get("use_yn").equals("N")) {
//						oldVal = (String)houseMap.get("risk_val");  
//						newVal = (String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_val");
//					}else {
//						oldVal = String.valueOf(houseMap.get("risk_num"));
//						newVal = String.valueOf(eqmtRiskAnayRtmeLoadHourlyMap.get("risk_num"));
//					}
//					if("MAX".equals(riskTypeMap.get("grp_cal_cd")) ){
//						if(oldVal.compareTo(newVal)  < 0) {
//							eqmtRiskAnayPractLoadDailyService.updateEqmtRiskAnayPractLoadDaily(eqmtRiskAnayPractLoadDayMap);
//						}
//					}else {
//						if(oldVal.compareTo(newVal)  > 0) {
//							eqmtRiskAnayPractLoadDailyService.updateEqmtRiskAnayPractLoadDaily(eqmtRiskAnayPractLoadDayMap);
//						}
//					}
//				}
//				//////////////////////////////임시로 사용 //////////////////////////////
//				////////////////////////////////////////////////////////////////////////
			}
		}
		
		//생산공정 실시간 분단위.
		//쿼리로 소속 설비중 최대 또는 최소 값으로 인서트 또는 업데이트 치도록 처리.
		paramMap.clear();
		paramMap.put("anys_ymdhi",anys_ymdhi);
		List<Map> listMap = procesRiskAnayRtmeLoadMinService.listEqmtLoadMinSimulRiskNum(paramMap);
		Map procsHourMap = new HashMap();
		Map procsPractHourMap = new HashMap();
		Map procsPractDayMap = new HashMap();
		for(Map procsMinMap : listMap) {
			Map resMap = procesRiskAnayRtmeLoadMinService.getProcesRiskAnayRtmeLoadMin_procs_id(procsMinMap);
			String strRiskRate = (String)procsMinMap.get("risk_rate");
			if("Y".equals(procsMinMap.get("use_yn"))){
				String[] tmpVal = strRiskRate.split(":");
				procsMinMap.put("risk_num",tmpVal[0]);
				procsMinMap.put("risk_step_lvl",tmpVal[1]);
				procsMinMap.put("risk_step_cd",tmpVal[2]);
			}else{
				procsMinMap.put("risk_val",strRiskRate);
			}
			if( ((String)procsMinMap.get("risk_type_id")).startsWith("KIES")) {
				procsMinMap.put("risk_type_id","KIES102");
			}
			
			if(resMap!=null && resMap.size()>0) {
				procesRiskAnayRtmeLoadMinService.updateProcesRiskAnayRtmeLoadMin(procsMinMap);
			}else {
				procesRiskAnayRtmeLoadMinService.insertProcesRiskAnayRtmeLoadMin(procsMinMap);
			}
			
			
			//생산공정 실시간 시간 단위
			//생산공정 실시간 분단위의 최대 또는 최소값으로 인서트 또는 업데이트 치도록 처리
			procsHourMap.clear();
			procsHourMap.putAll(procsMinMap); 
			procsHourMap.put("anys_ymdh",anys_ymdh);
			Map houseMap = procesRiskAnayRtmeLoadHourlyService.getProcesRiskAnayRtmeLoadHourly_procs_id(procsHourMap);
			if(houseMap==null||houseMap.size()==0) {
				procesRiskAnayRtmeLoadHourlyService.insertProcesRiskAnayRtmeLoadHourly(procsHourMap);
			}else {
				String oldVal = "";
				String newVal =	"";
				if("N".equals(procsMinMap.get("use_yn"))){
					oldVal = (String)houseMap.get("risk_val");  
					newVal = (String)procsHourMap.get("risk_val");
				}else {
					oldVal = String.valueOf(houseMap.get("risk_num"));
					newVal = String.valueOf(procsHourMap.get("risk_num"));
				}
				if("MAX".equals(procsHourMap.get("grp_cal_cd")) ){
					if(oldVal.compareTo(newVal)  < 0) {
						procesRiskAnayRtmeLoadHourlyService.updateProcesRiskAnayRtmeLoadHourly(procsHourMap);
					}
				}else {
					if(oldVal.compareTo(newVal)  > 0) {
						procesRiskAnayRtmeLoadHourlyService.updateProcesRiskAnayRtmeLoadHourly(procsHourMap);
					}
				}
			}
			
			//4주뒤 예측 정보시간단위도 세팅
			//4주위 일단위 예측 정보도 세팅.
			procsPractHourMap.clear();
			LocalDateTime w4dt = dt.plusHours(24*7*4); //4주 예측 건으로 세팅.
			String pract_anys_ymdh = w4dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			procsPractHourMap.putAll(procsHourMap);
			procsPractHourMap.put("anys_ymdh", pract_anys_ymdh);
			
			if( ((String)procsPractHourMap.get("risk_type_id")).startsWith("KIES")) {
				procsPractHourMap.put("risk_type_id","KIES202");
			}
			houseMap =  procesRiskAnayPractLoadHourlyService.getProcesRiskAnayPractLoadHourly_procs_id(procsPractHourMap);
			if(houseMap==null||houseMap.size()==0) {
				procesRiskAnayPractLoadHourlyService.insertProcesRiskAnayPractLoadHourly(procsPractHourMap);
			}else {
				String oldVal = "";
				String newVal =	"";
				if("N".equals(procsPractHourMap.get("use_yn"))){
					oldVal = (String)houseMap.get("risk_val");  
					newVal = (String)procsPractHourMap.get("risk_val");
				}else {
					oldVal = String.valueOf(houseMap.get("risk_num"));
					newVal = String.valueOf(procsPractHourMap.get("risk_num"));
				}
				if("MAX".equals(procsPractHourMap.get("grp_cal_cd")) ){
					if(oldVal.compareTo(newVal)  < 0) {
						procesRiskAnayPractLoadHourlyService.updateProcesRiskAnayPractLoadHourly(procsPractHourMap);
					}
				}else {
					if(oldVal.compareTo(newVal)  > 0) {
						procesRiskAnayPractLoadHourlyService.updateProcesRiskAnayPractLoadHourly(procsPractHourMap);
					}
				}
			}
			
			//공정 예측 일별 세팅. 
			procsPractDayMap.clear();
			String pract_anys_ymd =pract_anys_ymdh.substring(0,8);
			procsPractDayMap.putAll(procsPractHourMap);
			procsPractDayMap.put("anys_ymd", pract_anys_ymd);
			if( ((String)procsPractDayMap.get("risk_type_id")).startsWith("KIES")) {
				procsPractDayMap.put("risk_type_id","KIES202");
			}
			houseMap =  procesRiskAnayPractLoadDailyService.getProcesRiskAnayPractLoadDaily_procs_id(procsPractDayMap);
			if(houseMap==null||houseMap.size()==0) {
				procesRiskAnayPractLoadDailyService.insertProcesRiskAnayPractLoadDaily(procsPractDayMap);
			}else {
				String oldVal = "";
				String newVal =	"";
				if("N".equals(procsPractDayMap.get("use_yn"))){
					oldVal = (String)houseMap.get("risk_val");  
					newVal = (String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_val");
				}else {
					oldVal = String.valueOf(houseMap.get("risk_num"));
					newVal = String.valueOf(eqmtRiskAnayRtmeLoadHourlyMap.get("risk_num"));
				}
				if("MAX".equals(procsPractDayMap.get("grp_cal_cd")) ){
					if(oldVal.compareTo(newVal)  < 0) {
						procesRiskAnayPractLoadDailyService.updateProcesRiskAnayPractLoadDaily(procsPractDayMap);
					}
				}else {
					if(oldVal.compareTo(newVal)  > 0) {
						procesRiskAnayPractLoadDailyService.updateProcesRiskAnayPractLoadDaily(procsPractDayMap);
					}
				}
			}
			
			
			
//			//////////////////////////////임시로 사용 //////////////////////////////
//			////////////////////////////////////////////////////////////////////////
//			//4주뒤 예측 정보시간단위도 세팅
//			//4주위 일단위 예측 정보도 세팅.
//			procsPractHourMap.clear();
//			w4dt = dt.plusDays(3); //4주 예측 건으로 세팅.
//			pract_anys_ymdh = w4dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//			procsPractHourMap.putAll(procsHourMap);
//			procsPractHourMap.put("anys_ymdh", pract_anys_ymdh);
//			
//			if( ((String)procsPractHourMap.get("risk_type_id")).startsWith("KIES")) {
//				procsPractHourMap.put("risk_type_id","KIES202");
//			}
//			houseMap =  procesRiskAnayPractLoadHourlyService.getProcesRiskAnayPractLoadHourly_procs_id(procsPractHourMap);
//			if(houseMap==null||houseMap.size()==0) {
//				procesRiskAnayPractLoadHourlyService.insertProcesRiskAnayPractLoadHourly(procsPractHourMap);
//			}else {
//				String oldVal = "";
//				String newVal =	"";
//				if("N".equals(procsPractHourMap.get("use_yn"))){
//					oldVal = (String)houseMap.get("risk_val");  
//					newVal = (String)procsPractHourMap.get("risk_val");
//				}else {
//					oldVal = String.valueOf(houseMap.get("risk_num"));
//					newVal = String.valueOf(procsPractHourMap.get("risk_num"));
//				}
//				if("MAX".equals(procsPractHourMap.get("grp_cal_cd")) ){
//					if(oldVal.compareTo(newVal)  < 0) {
//						procesRiskAnayPractLoadHourlyService.updateProcesRiskAnayPractLoadHourly(procsPractHourMap);
//					}
//				}else {
//					if(oldVal.compareTo(newVal)  > 0) {
//						procesRiskAnayPractLoadHourlyService.updateProcesRiskAnayPractLoadHourly(procsPractHourMap);
//					}
//				}
//			}
//			
//			//공정 예측 일별 세팅. 
//			procsPractDayMap.clear();
//			pract_anys_ymd =pract_anys_ymdh.substring(0,8);
//			procsPractDayMap.putAll(procsPractHourMap);
//			procsPractDayMap.put("anys_ymd", pract_anys_ymd);
//			if( ((String)procsPractDayMap.get("risk_type_id")).startsWith("KIES")) {
//				procsPractDayMap.put("risk_type_id","KIES202");
//			}
//			houseMap =  procesRiskAnayPractLoadDailyService.getProcesRiskAnayPractLoadDaily_procs_id(procsPractDayMap);
//			if(houseMap==null||houseMap.size()==0) {
//				procesRiskAnayPractLoadDailyService.insertProcesRiskAnayPractLoadDaily(procsPractDayMap);
//			}else {
//				String oldVal = "";
//				String newVal =	"";
//				if("N".equals(procsPractDayMap.get("use_yn"))){
//					oldVal = (String)houseMap.get("risk_val");  
//					newVal = (String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_val");
//				}else {
//					oldVal = String.valueOf(houseMap.get("risk_num"));
//					newVal = String.valueOf(eqmtRiskAnayRtmeLoadHourlyMap.get("risk_num"));
//				}
//				if("MAX".equals(procsPractDayMap.get("grp_cal_cd")) ){
//					if(oldVal.compareTo(newVal)  < 0) {
//						procesRiskAnayPractLoadDailyService.updateProcesRiskAnayPractLoadDaily(procsPractDayMap);
//					}
//				}else {
//					if(oldVal.compareTo(newVal)  > 0) {
//						procesRiskAnayPractLoadDailyService.updateProcesRiskAnayPractLoadDaily(procsPractDayMap);
//					}
//				}
//			}
//			//////////////////////////////임시로 사용 //////////////////////////////
//			////////////////////////////////////////////////////////////////////////
			
			
			
		}
		
		
		//시설실시간분단위
		paramMap.clear();
		paramMap.put("anys_ymdhi",anys_ymdhi);
		listMap = factyRiskAnayRtmeLoadMinService.listProcsLoadMinSimulRiskNum(paramMap);
		Map factyHourMap = new HashMap();
		Map factyPractHourMap = new HashMap();
		Map factyPractDayMap = new HashMap();
		for(Map factyMinMap : listMap) {
			//시설 실시간 분단위
			Map resMap = factyRiskAnayRtmeLoadMinService.getFactyRiskAnayRtmeLoadMin_facty_id(factyMinMap);
			
			if(! ((String)factyMinMap.get("risk_type_id")).startsWith("KIES")) {
				continue;
			}
			
			String strRiskRate = (String)factyMinMap.get("risk_rate");
			if("Y".equals(factyMinMap.get("use_yn"))){
				String[] tmpVal = strRiskRate.split(":");
				factyMinMap.put("risk_num",tmpVal[0]);
				factyMinMap.put("risk_step_lvl",tmpVal[1]);
				factyMinMap.put("risk_step_cd",tmpVal[2]);
			}else{
				factyMinMap.put("risk_val",strRiskRate);
			}
			if( ((String)factyMinMap.get("risk_type_id")).startsWith("KIES")) {
				factyMinMap.put("risk_type_id","KIES101");
			}
			
			if(resMap!=null && resMap.size()>0) {
				factyRiskAnayRtmeLoadMinService.updateFactyRiskAnayRtmeLoadMin(factyMinMap);
			}else {
				factyRiskAnayRtmeLoadMinService.insertFactyRiskAnayRtmeLoadMin(factyMinMap);
			}
			
			//시설 실시간 시간 단위
			factyHourMap.clear();
			factyHourMap.putAll(factyMinMap); 
			factyHourMap.put("anys_ymdh",anys_ymdh);
			Map houseMap = factyRiskAnayRtmeLoadHourlyService.getFactyRiskAnayRtmeLoadHourly_facty_id(factyHourMap);
			if(houseMap==null||houseMap.size()==0) {
				factyRiskAnayRtmeLoadHourlyService.insertFactyRiskAnayRtmeLoadHourly(factyHourMap);
			}else {
				String oldVal = "";
				String newVal =	"";
				if("N".equals(factyMinMap.get("use_yn"))){
					oldVal = (String)houseMap.get("risk_val");  
					newVal = (String)factyHourMap.get("risk_val");
				}else {
					oldVal = String.valueOf(houseMap.get("risk_num"));
					newVal = String.valueOf(factyHourMap.get("risk_num"));
				}
				if("MAX".equals(factyHourMap.get("grp_cal_cd")) ){
					if(oldVal.compareTo(newVal)  < 0) {
						factyRiskAnayRtmeLoadHourlyService.updateFactyRiskAnayRtmeLoadHourly(factyHourMap);
					}
				}else {
					if(oldVal.compareTo(newVal)  > 0) {
						factyRiskAnayRtmeLoadHourlyService.updateFactyRiskAnayRtmeLoadHourly(factyHourMap);
					}
				}
			}
			
			
			//시설 4주위 일단위 예측 정보도 세팅.
			factyPractHourMap.clear();
			LocalDateTime w4dt = dt.plusHours(24*7*4);//4주 예측 건으로 세팅.
			String pract_anys_ymdh = w4dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
			factyPractHourMap.putAll(factyHourMap);
			factyPractHourMap.put("anys_ymdh", pract_anys_ymdh);
			
			if( ((String)factyPractHourMap.get("risk_type_id")).startsWith("KIES")) {
				factyPractHourMap.put("risk_type_id","KIES201");
			}
			houseMap =  factyRiskAnayPractLoadHourlyService.getFactyRiskAnayPractLoadHourly_facty_id(factyPractHourMap);
			if(houseMap==null||houseMap.size()==0) {
				factyRiskAnayPractLoadHourlyService.insertFactyRiskAnayPractLoadHourly(factyPractHourMap);
			}else {
				String oldVal = "";
				String newVal =	"";
				if("N".equals(factyPractHourMap.get("use_yn"))){
					oldVal = (String)houseMap.get("risk_val");  
					newVal = (String)factyPractHourMap.get("risk_val");
				}else {
					oldVal = String.valueOf(houseMap.get("risk_num"));
					newVal = String.valueOf(factyPractHourMap.get("risk_num"));
				}
				if("MAX".equals(factyPractHourMap.get("grp_cal_cd")) ){
					if(oldVal.compareTo(newVal)  < 0) {
						factyRiskAnayPractLoadHourlyService.updateFactyRiskAnayPractLoadHourly(factyPractHourMap);
					}
				}else {
					if(oldVal.compareTo(newVal)  > 0) {
						factyRiskAnayPractLoadHourlyService.updateFactyRiskAnayPractLoadHourly(factyPractHourMap);
					}
				}
			}
			
			
			//시설 예측 일별 세팅. 
			factyPractDayMap.clear();
			String pract_anys_ymd =pract_anys_ymdh.substring(0,8);
			factyPractDayMap.putAll(factyPractHourMap);
			factyPractDayMap.put("anys_ymd", pract_anys_ymd);
			if( ((String)factyPractDayMap.get("risk_type_id")).startsWith("KIES")) {
				factyPractDayMap.put("risk_type_id","KIES201");
			}
			houseMap =  factyRiskAnayPractLoadDailyService.getFactyRiskAnayPractLoadDaily_facty_id(factyPractDayMap);
			if(houseMap==null||houseMap.size()==0) {
				factyRiskAnayPractLoadDailyService.insertFactyRiskAnayPractLoadDaily(factyPractDayMap);
			}else {
				String oldVal = "";
				String newVal =	"";
				if("N".equals(factyPractDayMap.get("use_yn"))){
					oldVal = (String)houseMap.get("risk_val");  
					newVal = (String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_val");
				}else {
					oldVal = String.valueOf(houseMap.get("risk_num"));
					newVal = String.valueOf(eqmtRiskAnayRtmeLoadHourlyMap.get("risk_num"));
				}
				if("MAX".equals(factyPractDayMap.get("grp_cal_cd")) ){
					if(oldVal.compareTo(newVal)  < 0) {
						factyRiskAnayPractLoadDailyService.updateFactyRiskAnayPractLoadDaily(factyPractDayMap);
					}
				}else {
					if(oldVal.compareTo(newVal)  > 0) {
						factyRiskAnayPractLoadDailyService.updateFactyRiskAnayPractLoadDaily(factyPractDayMap);
					}
				}
			}
			
//			//////////////////////////////임시로 사용 //////////////////////////////
//			////////////////////////////////////////////////////////////////////////
//			factyPractHourMap.clear();
//			w4dt = dt.plusDays(3); //4주 예측 건으로 세팅.
//			pract_anys_ymdh = w4dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
//			factyPractHourMap.putAll(factyHourMap);
//			factyPractHourMap.put("anys_ymdh", pract_anys_ymdh);
//			
//			if( ((String)factyPractHourMap.get("risk_type_id")).startsWith("KIES")) {
//				factyPractHourMap.put("risk_type_id","KIES201");
//			}
//			houseMap =  factyRiskAnayPractLoadHourlyService.getFactyRiskAnayPractLoadHourly_facty_id(factyPractHourMap);
//			if(houseMap==null||houseMap.size()==0) {
//				factyRiskAnayPractLoadHourlyService.insertFactyRiskAnayPractLoadHourly(factyPractHourMap);
//			}else {
//				String oldVal = "";
//				String newVal =	"";
//				if("N".equals(factyPractHourMap.get("use_yn"))){
//					oldVal = (String)houseMap.get("risk_val");  
//					newVal = (String)factyPractHourMap.get("risk_val");
//				}else {
//					oldVal = String.valueOf(houseMap.get("risk_num"));
//					newVal = String.valueOf(factyPractHourMap.get("risk_num"));
//				}
//				if("MAX".equals(factyPractHourMap.get("grp_cal_cd")) ){
//					if(oldVal.compareTo(newVal)  < 0) {
//						factyRiskAnayPractLoadHourlyService.updateFactyRiskAnayPractLoadHourly(factyPractHourMap);
//					}
//				}else {
//					if(oldVal.compareTo(newVal)  > 0) {
//						factyRiskAnayPractLoadHourlyService.updateFactyRiskAnayPractLoadHourly(factyPractHourMap);
//					}
//				}
//			}
//			
//			
//			//시설 예측 일별 세팅. 
//			factyPractDayMap.clear();
//			pract_anys_ymd =pract_anys_ymdh.substring(0,8);
//			factyPractDayMap.putAll(factyPractHourMap);
//			factyPractDayMap.put("anys_ymd", pract_anys_ymd);
//			if( ((String)factyPractDayMap.get("risk_type_id")).startsWith("KIES")) {
//				factyPractDayMap.put("risk_type_id","KIES201");
//			}
//			houseMap =  factyRiskAnayPractLoadDailyService.getFactyRiskAnayPractLoadDaily_facty_id(factyPractDayMap);
//			if(houseMap==null||houseMap.size()==0) {
//				factyRiskAnayPractLoadDailyService.insertFactyRiskAnayPractLoadDaily(factyPractDayMap);
//			}else {
//				String oldVal = "";
//				String newVal =	"";
//				if("N".equals(factyPractDayMap.get("use_yn"))){
//					oldVal = (String)houseMap.get("risk_val");  
//					newVal = (String)eqmtRiskAnayRtmeLoadHourlyMap.get("risk_val");
//				}else {
//					oldVal = String.valueOf(houseMap.get("risk_num"));
//					newVal = String.valueOf(eqmtRiskAnayRtmeLoadHourlyMap.get("risk_num"));
//				}
//				if("MAX".equals(factyPractDayMap.get("grp_cal_cd")) ){
//					if(oldVal.compareTo(newVal)  < 0) {
//						factyRiskAnayPractLoadDailyService.updateFactyRiskAnayPractLoadDaily(factyPractDayMap);
//					}
//				}else {
//					if(oldVal.compareTo(newVal)  > 0) {
//						factyRiskAnayPractLoadDailyService.updateFactyRiskAnayPractLoadDaily(factyPractDayMap);
//					}
//				}
//			}			
//						
//						
//			//////////////////////////////임시로 사용 //////////////////////////////
//			////////////////////////////////////////////////////////////////////////
		}
		
		
		long execTime = System.currentTimeMillis() - startTime;
		logger.error("소요시간:"+execTime);
	}

	//@Scheduled(cron = "0 0/1 * * * *")// 1분
	public void insertAcdntScnrsAnayLoadmin() {
		
		System.out.println("==========================insertAcdntScnrsAnayLoadmin=========================");
		String[] anysSysCd = new String[]{"PARTDB","CRIG","KSEC","KIES" };
		//String[] anysSysCd = new String[]{"PARTDB","KSEC","KIES" };
		String[] EqmtId = new String[]{ "x101", "x102", "x103" ,"t101","t102","t103"};
		String[] ristStepCd = new String[]{ "NR0001", "NR0002", "NR0003","NR0004","NR0005","CR0001", "CR0002", "CR0003","CR0004","CR0005"};
		String[] scen_id = new String[] {"HH-TK101-01","HH-TK101-02","HH-TK102-01","HH-TK102-02","HH-TK103-01","HH-TK103-02","HZ-PP104-01","HZ-PP104-02","HZ-PP104-03","HZ-PP105-01","HZ-PP105-02","HZ-PP105-03","HZ-PP106-01","HZ-PP106-02","HZ-PP106-03","HZ-TK101-01","HZ-TK101-02","HZ-TK101-03","HZ-TK101-04","HZ-TK101-05","HZ-TK101-06","HZ-TK101-07","HZ-TK101-08","HZ-TK101-09","HZ-TK102-01","HZ-TK102-02","HZ-TK102-03","HZ-TK102-04","HZ-TK102-05","HZ-TK102-06","HZ-TK102-07","HZ-TK102-08","HZ-TK102-09","HZ-TK103-01","HZ-TK103-02","HZ-TK103-03","HZ-TK103-04","HZ-TK103-05","HZ-TK103-06","HZ-TK103-07","HZ-TK103-08","HZ-TK103-09","HZ-TK901-01","HZ-TK902-01","V-105-01-H","V-106-01-H"};

		LocalDateTime dt = LocalDateTime.now();
		String anys_ymdhi = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));	
		String anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));	
		String anys_ymd = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		Map scnrMinMap = new HashMap();
		Map scnrHourlyMap = new HashMap();
		Map scnrDailyMap = new HashMap();
		Random ran = new Random();
		
		//실시간 -	crig-설비-시나리오위험도(분단위)
		for (int i = 0; i < anysSysCd.length; i++) {					
			int scen_id_size_random = ran.nextInt(scen_id.length)+0;
			for (int k = 0; k < scen_id_size_random; k++) {
				Random ran2 = new Random();
				int scen_id_size_random2 = ran2.nextInt(scen_id.length-1)+0;
				
				//설비
				scnrMinMap.put("anys_type_cd","AT1000");
				scnrMinMap.put("factory_hrrc_cd","FH3000");
				scnrMinMap.put("anys_sys_cd",anysSysCd[i].toString());
				scnrMinMap.put("anys_ymdhi" , anys_ymdhi.toString());
				scnrMinMap.put("scen_id", scen_id[scen_id_size_random2]);
				scnrMinMap.put("risk_unit" ,"%");
				scnrMinMap.put("factory_id",EqmtId[ran2.nextInt(EqmtId.length-1)+0]);
				scnrMinMap.put("risk_step_cd",ristStepCd[ran.nextInt(ristStepCd.length-1)+0]);
				BigDecimal a = new BigDecimal(ran.nextInt(100)+1);
				scnrMinMap.put("risk_num",a);            	
				try { 
					riskAnayLoadService.insertThAcdntScnrsAnayLoadMin(scnrMinMap);
				}catch(Exception e){ 					  
		            Iterator<String> keys = scnrMinMap.keySet().iterator();
		            while (keys.hasNext()){
		                String key = keys.next();
		               // System.out.println(key + " : " + scnrMinMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
		            }
			    }	            
				scnrMinMap.clear();
				
				//공정
				if(anysSysCd[i].equals("KIES")) {					
					scnrMinMap.put("anys_type_cd","AT1000");
					scnrMinMap.put("factory_hrrc_cd","FH2000");
					scnrMinMap.put("anys_sys_cd","KIES");
					scnrMinMap.put("anys_ymdhi" , anys_ymdhi.toString());
					scnrMinMap.put("scen_id", scen_id[scen_id_size_random2]);
					scnrMinMap.put("risk_unit" ,"%");
					scnrMinMap.put("factory_id","P00000");
					scnrMinMap.put("risk_step_cd",ristStepCd[ran.nextInt(ristStepCd.length-1)+0]);
					BigDecimal a2 = new BigDecimal(ran.nextInt(100)+1);
					scnrMinMap.put("risk_num",a2);
					try { 
						riskAnayLoadService.insertThAcdntScnrsAnayLoadMin(scnrMinMap);
					}catch(Exception e){ 					  
			            Iterator<String> keys = scnrMinMap.keySet().iterator();
			            while (keys.hasNext()){
			                String key = keys.next();
			                System.out.println(key + " : " + scnrMinMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
			            }
				    }	            
					scnrMinMap.clear();
					
				}
				
				//시설
				if(anysSysCd[i].equals("KIES")) {					
					scnrMinMap.put("anys_type_cd","AT1000");
					scnrMinMap.put("factory_hrrc_cd","FH1000");
					scnrMinMap.put("anys_sys_cd","KIES");
					scnrMinMap.put("anys_ymdhi" , anys_ymdhi.toString());
					scnrMinMap.put("scen_id", scen_id[scen_id_size_random2]);
					scnrMinMap.put("risk_unit" ,"%");
					scnrMinMap.put("factory_id","F00000");
					scnrMinMap.put("risk_step_cd",ristStepCd[ran.nextInt(ristStepCd.length-1)+0]);
					BigDecimal a3 = new BigDecimal(ran.nextInt(100)+1);
					scnrMinMap.put("risk_num",a3);
					try { 
						riskAnayLoadService.insertThAcdntScnrsAnayLoadMin(scnrMinMap);
					}catch(Exception e){ 					  
			            Iterator<String> keys = scnrMinMap.keySet().iterator();
			            while (keys.hasNext()){
			                String key = keys.next();
			                //System.out.println(key + " : " + scnrMinMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
			            }
				    }	            
					scnrMinMap.clear();				
				}     					
			}
		} // end 실시간 -	crig-설비-시나리오위험도(분단위) 						
	}

	//@Scheduled(cron = "0 10 * * * *")
	public void insertAcdntScnrsAnayLoadHourly() {
		System.out.println("==========================insertAcdntScnrsAnayLoadHourly=========================");
		String[] anysSysCd = new String[]{"PARTDB","CRIG","KSEC","KIES" };
		String[] EqmtId = new String[]{ "x101", "x102", "x103" ,"t101","t102","t103"};
		String[] ristStepCd = new String[]{ "NR0001", "NR0002", "NR0003","NR0004","NR0005","CR0001", "CR0002", "CR0003","CR0004","CR0005"};
		String[] scen_id = new String[] {"HH-TK101-01","HH-TK101-02","HH-TK102-01","HH-TK102-02","HH-TK103-01","HH-TK103-02","HZ-PP104-01","HZ-PP104-02","HZ-PP104-03","HZ-PP105-01","HZ-PP105-02","HZ-PP105-03","HZ-PP106-01","HZ-PP106-02","HZ-PP106-03","HZ-TK101-01","HZ-TK101-02","HZ-TK101-03","HZ-TK101-04","HZ-TK101-05","HZ-TK101-06","HZ-TK101-07","HZ-TK101-08","HZ-TK101-09","HZ-TK102-01","HZ-TK102-02","HZ-TK102-03","HZ-TK102-04","HZ-TK102-05","HZ-TK102-06","HZ-TK102-07","HZ-TK102-08","HZ-TK102-09","HZ-TK103-01","HZ-TK103-02","HZ-TK103-03","HZ-TK103-04","HZ-TK103-05","HZ-TK103-06","HZ-TK103-07","HZ-TK103-08","HZ-TK103-09","HZ-TK901-01","HZ-TK902-01","V-105-01-H","V-106-01-H"};

		LocalDateTime dt = LocalDateTime.now();
		String anys_ymdh = dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
		Map scnrHourlyMap = new HashMap();
		Random ran = new Random();
		
		//실시간 -	crig-설비-시나리오위험도(분단위)
		for (int i = 0; i < anysSysCd.length; i++) {					
			int scen_id_size_random = ran.nextInt(scen_id.length)+0;
			//System.out.println("============================"+anysSysCd[i] +"####"+ scen_id_size_random);
			for (int k = 0; k < scen_id_size_random; k++) {
				Random ran2 = new Random();
				int scen_id_size_random2 = ran2.nextInt(scen_id.length-1)+0;
				
				//설비				
				scnrHourlyMap.put("scen_id", scen_id[scen_id_size_random2]);	
				scnrHourlyMap.put("factory_hrrc_cd","FH3000");
				scnrHourlyMap.put("factory_id",EqmtId[ran2.nextInt(EqmtId.length-1)+0]);
				scnrHourlyMap.put("anys_sys_cd",anysSysCd[i].toString());
				scnrHourlyMap.put("risk_step_cd",ristStepCd[ran.nextInt(ristStepCd.length-1)+0]);
				BigDecimal a = new BigDecimal(ran.nextInt(100)+1);
				scnrHourlyMap.put("risk_num",a);            	
				scnrHourlyMap.put("risk_unit" ,"%");
				try { 
					scnrHourlyMap.put("anys_ymdh" , anys_ymdh.toString());
					scnrHourlyMap.put("anys_type_cd","AT1000");
					riskAnayLoadService.insertThAcdntScnrsAnayLoadHourly(scnrHourlyMap);						
				}catch(Exception e){ 					  
		            Iterator<String> keys = scnrHourlyMap.keySet().iterator();
		            while (keys.hasNext()){
		                String key = keys.next();
		               // System.out.println(key + " : " + scnrHourlyMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
		            }
			    }
				
				try { 	
					//LocalDateTime w4dt = dt.plusHours(24*7*4); //4주 예측 건으로 세팅.
					LocalDateTime w4dt = dt.plusHours(2); //2시간 세팅.
					String pract_anys_ymdh = w4dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));			
					scnrHourlyMap.put("anys_ymdh" , pract_anys_ymdh.toString());
					scnrHourlyMap.put("anys_type_cd","AT2000");
					riskAnayLoadService.insertThAcdntScnrsAnayLoadHourly(scnrHourlyMap);							
				}catch(Exception e){ 					  
		            Iterator<String> keys = scnrHourlyMap.keySet().iterator();
		            while (keys.hasNext()){
		                String key = keys.next();
		               // System.out.println(key + " : " + scnrHourlyMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
		            }
			    }
				scnrHourlyMap.clear();
				
				//공정
				if(anysSysCd[i].equals("KIES")) {					
					scnrHourlyMap.put("factory_hrrc_cd","FH2000");
					scnrHourlyMap.put("anys_sys_cd","KIES");
					scnrHourlyMap.put("scen_id", scen_id[scen_id_size_random2]);
					scnrHourlyMap.put("risk_unit" ,"%");
					scnrHourlyMap.put("factory_id","P00000");
					scnrHourlyMap.put("risk_step_cd",ristStepCd[ran.nextInt(ristStepCd.length-1)+0]);
					BigDecimal a2 = new BigDecimal(ran.nextInt(100)+1);
					scnrHourlyMap.put("risk_num",a2);
					try { 
						//LocalDateTime w4dt = dt.plusHours(24*7*4); //4주 예측 건으로 세팅.
						LocalDateTime w4dt = dt.plusHours(2); //2시간 세팅.
						String pract_anys_ymdh = w4dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
						
						scnrHourlyMap.put("anys_ymdh" , pract_anys_ymdh.toString());
						scnrHourlyMap.put("anys_type_cd","AT2000");
						riskAnayLoadService.insertThAcdntScnrsAnayLoadHourly(scnrHourlyMap);
					}catch(Exception e){ 					  
			            Iterator<String> keys = scnrHourlyMap.keySet().iterator();
			            while (keys.hasNext()){
			                String key = keys.next();
			                //System.out.println(key + " : " + scnrHourlyMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
			            }
				    }	            
					scnrHourlyMap.clear();
				}
				
				//시설
				if(anysSysCd[i].equals("KIES")) {					
					scnrHourlyMap.put("factory_hrrc_cd","FH1000");
					scnrHourlyMap.put("anys_sys_cd","KIES");
					scnrHourlyMap.put("scen_id", scen_id[scen_id_size_random2]);
					scnrHourlyMap.put("risk_unit" ,"%");
					scnrHourlyMap.put("factory_id","F00000");
					scnrHourlyMap.put("risk_step_cd",ristStepCd[ran.nextInt(ristStepCd.length-1)+0]);
					BigDecimal a3 = new BigDecimal(ran.nextInt(100)+1);
					scnrHourlyMap.put("risk_num",a3);
					try { 
						//LocalDateTime w4dt = dt.plusHours(24*7*4); //4주 예측 건으로 세팅.
						LocalDateTime w4dt = dt.plusHours(2); //12시간 세팅.
						String pract_anys_ymdh = w4dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
						scnrHourlyMap.put("anys_ymdh" , pract_anys_ymdh.toString());
						scnrHourlyMap.put("anys_type_cd","AT2000");
						riskAnayLoadService.insertThAcdntScnrsAnayLoadHourly(scnrHourlyMap);
					}catch(Exception e){ 					  
			            Iterator<String> keys = scnrHourlyMap.keySet().iterator();
			            while (keys.hasNext()){
			                String key = keys.next();
			                //System.out.println(key + " : " + scnrHourlyMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
			            }
				    }	            
					scnrHourlyMap.clear();				
				}     					
			}
		} // end 실시간 -	crig-설비-시나리오위험도(분단위) 						
	}
	
	
	//@Scheduled(cron = "0 0 9 * * *")
	public void insertAcdntScnrsAnayLoadDaily() {
		System.out.println("==========================insertAcdntScnrsAnayLoadDaily=========================");
		String[] anysSysCd = new String[]{"PARTDB","CRIG","KSEC","KIES" };
		//String[] anysSysCd = new String[]{"PARTDB","KSEC","KIES" };
		String[] EqmtId = new String[]{ "x101", "x102", "x103" ,"t101","t102","t103"};
		String[] ristStepCd = new String[]{ "NR0001", "NR0002", "NR0003","NR0004","NR0005","CR0001", "CR0002", "CR0003","CR0004","CR0005"};
		String[] scen_id = new String[] {"HH-TK101-01","HH-TK101-02","HH-TK102-01","HH-TK102-02","HH-TK103-01","HH-TK103-02","HZ-PP104-01","HZ-PP104-02","HZ-PP104-03","HZ-PP105-01","HZ-PP105-02","HZ-PP105-03","HZ-PP106-01","HZ-PP106-02","HZ-PP106-03","HZ-TK101-01","HZ-TK101-02","HZ-TK101-03","HZ-TK101-04","HZ-TK101-05","HZ-TK101-06","HZ-TK101-07","HZ-TK101-08","HZ-TK101-09","HZ-TK102-01","HZ-TK102-02","HZ-TK102-03","HZ-TK102-04","HZ-TK102-05","HZ-TK102-06","HZ-TK102-07","HZ-TK102-08","HZ-TK102-09","HZ-TK103-01","HZ-TK103-02","HZ-TK103-03","HZ-TK103-04","HZ-TK103-05","HZ-TK103-06","HZ-TK103-07","HZ-TK103-08","HZ-TK103-09","HZ-TK901-01","HZ-TK902-01","V-105-01-H","V-106-01-H"};
		
		LocalDateTime dt = LocalDateTime.now();	
		String anys_ymd = dt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
				
		Map scnrDailyMap = new HashMap();
		Random ran = new Random();
		
		//실시간 -	crig-설비-시나리오위험도(분단위)
		for (int i = 0; i < anysSysCd.length; i++) {					
			int scen_id_size_random = ran.nextInt(scen_id.length)+0;
			for (int k = 0; k < scen_id_size_random; k++) {
				Random ran2 = new Random();
				int scen_id_size_random2 = ran2.nextInt(scen_id.length-1)+0;
				
				//설비			
				scnrDailyMap.put("factory_hrrc_cd","FH3000");
				scnrDailyMap.put("anys_sys_cd",anysSysCd[i].toString());		
				scnrDailyMap.put("scen_id", scen_id[scen_id_size_random2]);
				scnrDailyMap.put("risk_unit" ,"%");
				scnrDailyMap.put("factory_id",EqmtId[ran2.nextInt(EqmtId.length-1)+0]);
				scnrDailyMap.put("risk_step_cd",ristStepCd[ran.nextInt(ristStepCd.length-1)+0]);
				BigDecimal a = new BigDecimal(ran.nextInt(100)+1);
				scnrDailyMap.put("risk_num",a);            	
				try { 
					
					//실시간
					scnrDailyMap.put("anys_ymd" , anys_ymd.toString());
					scnrDailyMap.put("anys_type_cd","AT1000");
					//riskAnayLoadService.insertThAcdntScnrsAnayLoadDaily(scnrDailyMap);
					
					//예측
					String pract_anys_ymdh = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
					scnrDailyMap.put("anys_ymd" , pract_anys_ymdh.toString());
					scnrDailyMap.put("anys_type_cd","AT2000");
					riskAnayLoadService.insertThAcdntScnrsAnayLoadDaily(scnrDailyMap);							
					
				}catch(Exception e){ 					  
		            Iterator<String> keys = scnrDailyMap.keySet().iterator();
		            while (keys.hasNext()){
		                String key = keys.next();
		                System.out.println(key + " : " + scnrDailyMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
		            }
			    }	            
				scnrDailyMap.clear();
				
				//공정
				if(anysSysCd[i].equals("KIES")) {					
					scnrDailyMap.put("factory_hrrc_cd","FH2000");
					scnrDailyMap.put("anys_sys_cd","KIES");
					scnrDailyMap.put("scen_id", scen_id[scen_id_size_random2]);
					scnrDailyMap.put("risk_unit" ,"%");
					scnrDailyMap.put("factory_id","P00000");
					scnrDailyMap.put("risk_step_cd",ristStepCd[ran.nextInt(ristStepCd.length-1)+0]);
					BigDecimal a2 = new BigDecimal(ran.nextInt(100)+1);
					scnrDailyMap.put("risk_num",a2);
					try { 
						
						//실시간
						scnrDailyMap.put("anys_ymd" , anys_ymd.toString());
						scnrDailyMap.put("anys_type_cd","AT1000");
						//riskAnayLoadService.insertThAcdntScnrsAnayLoadDaily(scnrDailyMap);
						
						//예측
						String pract_anys_ymdh = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
						scnrDailyMap.put("anys_ymd" , pract_anys_ymdh.toString());
						scnrDailyMap.put("anys_type_cd","AT2000");
						riskAnayLoadService.insertThAcdntScnrsAnayLoadDaily(scnrDailyMap);
						
					}catch(Exception e){ 					  
			            Iterator<String> keys = scnrDailyMap.keySet().iterator();
			            while (keys.hasNext()){
			                String key = keys.next();
			                System.out.println(key + " : " + scnrDailyMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
			            }
				    }	            
					scnrDailyMap.clear();
					
				}
				
				//시설
				if(anysSysCd[i].equals("KIES")) {					
					scnrDailyMap.put("factory_hrrc_cd","FH1000");
					scnrDailyMap.put("anys_sys_cd","KIES");
					scnrDailyMap.put("scen_id", scen_id[scen_id_size_random2]);
					scnrDailyMap.put("risk_unit" ,"%");
					scnrDailyMap.put("factory_id","F00000");
					scnrDailyMap.put("risk_step_cd",ristStepCd[ran.nextInt(ristStepCd.length-1)+0]);
					BigDecimal a3 = new BigDecimal(ran.nextInt(100)+1);
					scnrDailyMap.put("risk_num",a3);
					try { 
						//실시간
						scnrDailyMap.put("anys_ymd" , anys_ymd.toString());
						scnrDailyMap.put("anys_type_cd","AT1000");
						//riskAnayLoadService.insertThAcdntScnrsAnayLoadDaily(scnrDailyMap);
						
						//예측
						String pract_anys_ymdh = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
						scnrDailyMap.put("anys_ymd" , pract_anys_ymdh.toString());
						scnrDailyMap.put("anys_type_cd","AT2000");
						riskAnayLoadService.insertThAcdntScnrsAnayLoadDaily(scnrDailyMap);
						
					}catch(Exception e){ 					  
			            Iterator<String> keys = scnrDailyMap.keySet().iterator();
			            while (keys.hasNext()){
			                String key = keys.next();
			                System.out.println(key + " : " + scnrDailyMap.get(key)); // Key2 , Key1, Key4, Key3, Key5
			            }
				    }	            
					scnrDailyMap.clear();				
				}     					
			}
		} // end 실시간 -	crig-설비-시나리오위험도(분단위) 						
	}
}
