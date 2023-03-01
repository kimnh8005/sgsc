package com.kies.sgsc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.base.RiskStatisticsDAO;
import com.kies.sgsc.dao.base.RiskTypeDAO;

@Service
public class RiskStatisticsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RiskStatisticsDAO riskStatisticsDAO;
	
	@Autowired
	RiskTypeDAO riskTypeDAO;

	public String lastDateYmdHI(Map inMap){
		logger.debug("lastDateYmdHI:"+inMap);
		return riskStatisticsDAO.lastDateYmdHI(inMap);
	}
	
	public String lastDateYmdH(Map inMap){
		logger.debug("lastDateYmdH:"+inMap);
		return riskStatisticsDAO.lastDateYmdH(inMap);
	}

	public Map allRtimeFactyRateRate(Map inMap){
		logger.debug("allRtimeFactyRateRate:"+inMap);
		return riskStatisticsDAO.allRtimeFactyRateRate(inMap);
	}

	public List<Map> listRiskHigtFactyTop5(Map inMap){
		logger.debug("listRiskHigtFacty:"+inMap);
		return riskStatisticsDAO.listRiskHigtFacty(inMap);
	}

	public List<Map> listRiskHigtProcsTop5(Map inMap){
		logger.debug("listRiskHigtProcs:"+inMap);
		return riskStatisticsDAO.listRiskHigtProcs(inMap);
	}
	
	public List<Map> listRiskHigtEqmtTop5(Map inMap){
		logger.debug("listRiskHigtEqmtTop5:"+inMap);
		return riskStatisticsDAO.listRiskHigtEqmt(inMap);
	}
	
	//<!-- 시나리오 용. 실시간 위험도 > 위험도 높은 설비<실시간 위험도 높은 설비>  -->
	public List<Map> listRiskHigtEqmtForSnroTop5(Map inMap){
		logger.debug("listRiskHigtEqmtForSnroTop5:"+inMap);
		return riskStatisticsDAO.listRiskHigtEqmtForSnro(inMap);
	}
	
	public Map getRiskCauseEqmt(Map inMap){
		logger.debug("getRiskCauseEqmt:"+inMap);
		return riskStatisticsDAO.getRiskCauseEqmt(inMap);
	}
	
	public List<Map> listRiskHigtEqmtIncludeCause(Map inMap){
		logger.debug("listRiskHigtEqmtIncludeCause:"+inMap);
		
		Map tinMap = new HashMap();
		tinMap.put("risk_cls_cd", "RC0002"); //이상원인
		String causeQuery = Utils.makeCauseNumQuery(riskTypeDAO.listRiskType(tinMap));
		inMap.put("causequery",causeQuery);
		logger.debug("causeQuery:"+causeQuery);
		inMap.put("causequery",causeQuery);
		
		inMap.put("topidx",5);
		List<Map> resEqumtTop5 = listRiskHigtEqmtTop5(inMap);
		
		return resEqumtTop5;
	}
	
	public List<Map> listRiskHigtEqmtIncludeCauseApp(Map inMap){
		logger.debug("listRiskHigtEqmtIncludeCause:"+inMap);
		
		Map tinMap = new HashMap();
		tinMap.put("risk_cls_cd", "RC0002"); //이상원인
		String causeQuery = Utils.makeCauseQuery(riskTypeDAO.listRiskType(tinMap));
		
		inMap.put("causequery",causeQuery);
		//logger.debug("causeQuery:"+causeQuery);
		inMap.put("topidx",3);
		List<Map> resEqumtTop5 = listRiskHigtEqmtTop5(inMap);
		
		return resEqumtTop5;
	}
	
	public List<Map> listEqmtRiskCrig(Map inMap){
		logger.debug("listEqmtRiskCrig:"+inMap);
		return riskStatisticsDAO.listEqmtRiskCrig(inMap);
	}
	
	//<!-- 시나리오 실시간 위험도 > 설비분석데이터 <실시간 위험도 높은 설비의 24시간분석데이터> -->
	public List<Map> listEqmtRiskCrigForSnro(Map inMap){
		logger.debug("listEqmtRiskCrigForSnro:"+inMap);
		return riskStatisticsDAO.listEqmtRiskCrigForSnro(inMap);
	}
		
	
	public List<Map> listEqmtRiskCrigApp(Map inMap){
		logger.debug("listEqmtRiskCrigApp:"+inMap);
		return riskStatisticsDAO.listEqmtRiskCrigApp(inMap);
	}
	
	public List<Map> listEqmtRiskPARTDB(Map inMap){
		logger.debug("listEqmtRiskPARTDB:"+inMap);
		return riskStatisticsDAO.listEqmtRiskPARTDB(inMap);
	} 
	
	//<!-- 시나리오 용  실시간 위험도 > 진단예지 위험도 -->
	public List<Map> listEqmtRiskPARTDBForSnro(Map inMap){
		logger.debug("listEqmtRiskPARTDBForSnro:"+inMap);
		return riskStatisticsDAO.listEqmtRiskPARTDBForSnro(inMap);
	}
		
	
	
	public List<Map> listEqmtRiskPARTDBApp(Map inMap){
		logger.debug("listEqmtRiskPARTDBApp:"+inMap);
		//inMap.put("risk_cls_cds", "'RC0003','RC0001'");
		String groupQuery = Utils.makeGroupTypeIdQuery(riskTypeDAO.listRiskType(inMap));
		logger.debug("groupQuery:"+groupQuery);
		inMap.put("groupQuery",groupQuery);	
		
		return riskStatisticsDAO.listEqmtRiskPARTDBApp(inMap);
	}
	
	public List<Map> listEqmtRiskKSEC(Map inMap){
		logger.debug("listEqmtRiskKSEC:"+inMap);
		return riskStatisticsDAO.listEqmtRiskKSEC(inMap);
	}
	
	//<!-- 시나리오 용  실시간 위험도 > 영상데이터 분석 위험도 -->
	public List<Map> listEqmtRiskKSECForSnro(Map inMap){
		logger.debug("listEqmtRiskKSECForSnro:"+inMap);
		return riskStatisticsDAO.listEqmtRiskKSECForSnro(inMap);
	}
	
	public List<Map> listEqmtRiskKSECApp(Map inMap){
		logger.debug("listEqmtRiskKSECApp:"+inMap);
		String groupQuery = Utils.makeGroupTypeIdQuery(riskTypeDAO.listRiskType(inMap));
		logger.debug("groupQuery:"+groupQuery);
		inMap.put("groupQuery",groupQuery);	
		return riskStatisticsDAO.listEqmtRiskKSECApp(inMap);
	}
	
	
	/*사고시나리오 실시간 분 팝업- 위험율 
	 * (통합위험율: KIES01, KSEC01:위험율,  PARTDB:고장확율, CRIG:발생확율
		anys_type_cd- AT1000:실시간  AT2000:예측 
		factory_hrrc_cd : 'FH1000':계통(시설), FH1000:FH3000 FH2000:시설(공정)
		factory_id : 각 아이디
	*/
	public List<Map> lisScnrsMinDesc(Map inMap){
		logger.debug("lisScnrsMinDesc:"+inMap);
		return riskStatisticsDAO.lisScnrsMinDesc(inMap);
	}
	
	/*사고시나리오 실시간 시간 팝업- 위험율 
	 * (통합위험율: KIES01, KSEC01:위험율,  PARTDB:고장확율, CRIG:발생확율
		anys_type_cd- AT1000:실시간  AT2000:예측 
		factory_hrrc_cd : 'FH1000':계통(시설), FH1000:FH3000 FH2000:시설(공정)
		factory_id : 각 아이디
	*/
	public List<Map> lisScnrsHourDesc(Map inMap){
		logger.debug("lisScnrsHourDesc:"+inMap);
		return riskStatisticsDAO.lisScnrsHourDesc(inMap);
	}
	
	
	
	public List<Map> listPractEqmtRiskTop5(Map inMap){
		logger.debug("listPreEqmtRiskTop5:"+inMap);
		inMap.put("topidx", Utils.NVL(inMap.get("topidx"),"5"));
		return riskStatisticsDAO.listPractEqmtRiskTop5(inMap);
	}
	
	//위험도 예측 24시간 3시간 주기 예측
	public List<Map> listPractEqmtRiskHour24(Map inMap){
		logger.debug("listPreEqmtRiskHour24:"+inMap);
		return riskStatisticsDAO.listPractEqmtRiskHour24(inMap);
	}
	
	
	//<!--시나리오 위험도 예측>설비예측> 24시간 예측  -->
	public List<Map> listPractEqmtRiskHour24ForSnro(Map inMap){
		logger.debug("listPractEqmtRiskHour24ForSnro:"+inMap);
		return riskStatisticsDAO.listPractEqmtRiskHour24ForSnro(inMap);
	}
		

	//위험도 예측> 설비>  리스트> 시간 페이징
	public List<Map> pagePractEqmtRiskHourMax(Map inMap){
		logger.debug("pagePractEqmtRiskHourMax:"+inMap);
		return riskStatisticsDAO.pagePractEqmtRiskHourMax(inMap);
	}
	
	//위험도 예측> 설비>  리스트> 시간 건수
	public int countPractEqmtRiskHourMax(Map inMap){
		logger.debug("countPractEqmtRiskHourMax:"+inMap);
		return riskStatisticsDAO.countPractEqmtRiskHourMax(inMap);
	}
	
	//위험도 예측> 설비>  리스트> 일간 페이징
	public List<Map> pagePractEqmtRiskDayMax(Map inMap){
		logger.debug("pagePractEqmtRiskDayMax:"+inMap);
		return riskStatisticsDAO.pagePractEqmtRiskDayMax(inMap);
	}
	
	//위험도 예측> 설비>  리스트> 일간 건수
	public int countPractEqmtRiskDayMax(Map inMap){
		logger.debug("countPractEqmtRiskDayMax:"+inMap);
		return riskStatisticsDAO.countPractEqmtRiskDayMax(inMap);
	}
		

	//위험도 예측 7일
	public List<Map> listPractEqmtRiskDay7(Map inMap){
		logger.debug("listPractEqmtRiskDay7:"+inMap);
		return riskStatisticsDAO.listPractEqmtRiskDay7(inMap);
	}
	
	//위험도 예측 4주를 위한 7일 합계
	public Map getPractEqmtRiskDay7forWeek(Map inMap){
		logger.debug("listPractEqmtRiskDay7forWeek:"+inMap);
		return riskStatisticsDAO.getPractEqmtRiskDay7forWeek(inMap);
	}
	
	//위험도 이력- 생산공정조회
	public List<Map> listRisktProcsHistory(Map inMap){
		logger.debug("listRisktProcsHistory:"+inMap);
		return riskStatisticsDAO.listRisktProcsHistory(inMap);
	}
	
	
	//위험도 이력- 생산공정조회 페이징
	public List<Map> pageRisktProcsHistory(Map inMap){
		logger.debug("pageRisktProcsHistory:"+inMap);
		List<Map> resEqumtTop5 = riskStatisticsDAO.pageRisktProcsHistory(inMap);
		return resEqumtTop5;
	}

	//위험도 이력- 생산공정조회 건수
	public int countRisktProcsHistory(Map inMap) {
		logger.debug("countRisktProcsHistory:"+inMap);
		return riskStatisticsDAO.countRisktProcsHistory(inMap);
	}
		
	
	//위험도 이력 -통합위험도 생산공정(시간별)
	public List<Map> listRiskProcs3HourHistory(Map inMap){
		logger.debug("listRiskProcs3HourHistory:"+inMap);
		return riskStatisticsDAO.listRiskProcs3HourHistory(inMap);
	}
	
	//위험도 이력 -통합위험도 생산공정(일별)
	public List<Map> listRiskProcsDayHistory(Map inMap){
		logger.debug("listRiskProcsDayHistory:"+inMap);
		return riskStatisticsDAO.listRiskProcsDayHistory(inMap);
	}
	

	//위험도이력 설비별 상세 3시간
	public List<Map> listRiskEqmt3HourHistory(Map inMap){
		logger.debug("listRiskEqmt3HourHistory:"+inMap);
		return riskStatisticsDAO.listRiskEqmt3HourHistory(inMap);
	}
	//위험도이력 설비별 상세 일별
	public List<Map> listRiskEqmtDayHistory(Map inMap){
	    logger.debug("listRiskEqmtDayHistory:"+inMap);
		return riskStatisticsDAO.listRiskEqmtDayHistory(inMap);
	} 
	
	
	//위험도이력 - 센서데이터 분석 위험도 3시간
	public List<Map> listRiskCRIG3HourHistory(Map inMap){
		logger.debug("listRiskCRIG3HourHistory:"+inMap);
		return riskStatisticsDAO.listRiskCRIG3HourHistory(inMap);
	}
	//위험도이력 - 센서데이터 분석 위험도 3시간 시나리오 포함
	public List<Map> listRiskCRIG3HourHistoryForSnro(Map inMap){
		logger.debug("listRiskCRIG3HourHistoryForSnro:"+inMap);
		return riskStatisticsDAO.listRiskCRIG3HourHistoryForSnro(inMap);
	}
	
	
	
	
	//위험도이력 - 센서데이터 분석 위험도 3시간 앱용
	public List<Map> listRiskCRIG3HourHistoryApp(Map inMap){
		logger.debug("listRiskCRIG3HourHistoryApp:"+inMap);
		return riskStatisticsDAO.listRiskCRIG3HourHistoryApp(inMap);
	} 
		
	//위험도 이력 - 예지보전 위험도
	public List<Map> listRiskPARTDB3HourHistory(Map inMap){
		logger.debug("listRiskPARTDB3HourHistory:"+inMap);
		return riskStatisticsDAO.listRiskPARTDB3HourHistory(inMap);
	}
	
	
	//위험도 이력 - 예지보전 위험도 시나리오
	public List<Map> listRiskPARTDB3HourHistoryForsnro(Map inMap){
		logger.debug("listRiskPARTDB3HourHistoryForsnro:"+inMap);
		return riskStatisticsDAO.listRiskPARTDB3HourHistoryForsnro(inMap);
	}
	
	//위험도 이력 - 예지보전 위험도 APP
	public List<Map> listRiskPARTDB3HourHistoryApp(Map inMap){
		logger.debug("listRiskPARTDB3HourHistory:"+inMap);
		
		String groupQuery = Utils.makeGroupTypeIdQuery(riskTypeDAO.listRiskType(inMap));
		logger.debug("groupQuery:"+groupQuery);
		inMap.put("groupQuery",groupQuery);	
		
		return riskStatisticsDAO.listRiskPARTDB3HourHistoryApp(inMap);
	}
	
	//위험도 이력 - 영상데이터 분석 위험도
	public List<Map> listRiskKSEC3HourHistory(Map inMap){
		logger.debug("listRiskKSEC3HourHistory:"+inMap);
		return riskStatisticsDAO.listRiskKSEC3HourHistory(inMap);
	}
	
	//위험도 이력 - 영상데이터 분석 시나리오
	public List<Map> listRiskKSEC3HourHistoryForsnro(Map inMap){
		logger.debug("listRiskKSEC3HourHistoryForsnro:"+inMap);
		return riskStatisticsDAO.listRiskKSEC3HourHistoryForsnro(inMap);
	} 
	
	
	
	//위험도 이력 - 영상데이터 분석 위험도 앱용
	public List<Map> listRiskKSEC3HourHistoryApp(Map inMap){
		logger.debug("listRiskKSEC3HourHistoryApp:"+inMap);
		
		String groupQuery = Utils.makeGroupTypeIdQuery(riskTypeDAO.listRiskType(inMap));
		logger.debug("groupQuery:"+groupQuery);
		inMap.put("groupQuery",groupQuery);	
		return riskStatisticsDAO.listRiskKSEC3HourHistoryApp(inMap);
	} 
	
	//가스위험성 제안
//	public List<Map> listProposeGasRisk(Map inMap){
//		logger.debug("listProposeGasRisk:"+inMap);
//		List<Map> resEqumtTop5 = riskStatisticsDAO.listProposeGasRisk(inMap);
//		
//		Map tinMap = new HashMap();
//		tinMap.put("risk_cls_cd", "RC0002"); //이상원인
//		List<Map> typeList = riskTypeDAO.listRiskType(tinMap); 
//		
//		Map inputMap = new HashMap();
//		for(Map top5Map : resEqumtTop5) {
//			inputMap.putAll(top5Map);
//			logger.debug("=================="+inputMap);
//			for(Map toMap : typeList) {
//				inputMap.put("anys_sys_cd", toMap.get("anys_sys_cd"));
//				inputMap.put("risk_type_id", toMap.get("risk_type_id"));
//				Map riskMap = getRiskCauseEqmt(inputMap);
//				
//				if(riskMap==null || riskMap.size()==0) {
//					top5Map.put(toMap.get("risk_type_nm"), "");
//				}else {
//					top5Map.put(toMap.get("risk_type_nm"), riskMap.get("risk_step_nm"));
//				}
//			}
//		}
//		return resEqumtTop5;
//	}
	
	
	//가스위험성제안-페이징
	public List<Map> pageProposeGasRisk(Map inMap){
		logger.debug("listProposeGasRisk:"+inMap);
		List<Map> resEqumtTop5 = riskStatisticsDAO.pageProposeGasRisk(inMap);
		
		Map tinMap = new HashMap();
		tinMap.put("risk_cls_cd", "RC0002"); //이상원인
		List<Map> typeList = riskTypeDAO.listRiskType(tinMap); 
		
		Map inputMap = new HashMap();
		for(Map top5Map : resEqumtTop5) {
			inputMap.putAll(top5Map);
			logger.debug("=================="+inputMap);
			for(Map toMap : typeList) {
				inputMap.put("anys_sys_cd", toMap.get("anys_sys_cd"));
				inputMap.put("risk_type_id", toMap.get("risk_type_id"));
				Map riskMap = getRiskCauseEqmt(inputMap);
				
				if(riskMap==null || riskMap.size()==0) {
					top5Map.put(toMap.get("risk_type_nm"), "");
				}else {
					top5Map.put(toMap.get("risk_type_nm"), riskMap.get("risk_step_nm"));
				}
			}
		}
		return resEqumtTop5;
	}

	//가스위험성제안-카운팅
	public int countProposeGasRisk(Map inMap) {
		logger.debug("countProcesRiskAnayPractLoadHourly:"+inMap);
		return riskStatisticsDAO.countProposeGasRisk(inMap);
	}
	

	//위험도예측>시설전체
	public Map getPractFactyRiskAll(Map inMap) {
		logger.debug("getPractFactyRiskAll:"+inMap);
		return riskStatisticsDAO.getPractFactyRiskAll(inMap);
		
	}
	//위험도예측>시설24시간
	public List<Map> listPractFactyRiskHour24(Map inMap){
		logger.debug("listPractFactyRiskHour24:"+inMap);
		return riskStatisticsDAO.listPractFactyRiskHour24(inMap);
	}
	//위험도예측>시설247일
	public List<Map> listPractFactyRiskDay7(Map inMap){
		logger.debug("listPractFactyRiskDay7:"+inMap);
		return riskStatisticsDAO.listPractFactyRiskDay7(inMap);
	}
	//위험도 예측-시설  4주 7일씩 합계
	public Map getPractFactyRiskDay7forWeek(Map inMap){
		logger.debug("getPractFactyRiskDay7forWeek:"+inMap);
		return riskStatisticsDAO.getPractFactyRiskDay7forWeek(inMap);
	}
	
	//위험도예측>생산공정>24시간
	public List<Map> listPractProcsRiskHour24(Map inMap){
		logger.debug("listPractProcsRiskHour24:"+inMap);
		return riskStatisticsDAO.listPractProcsRiskHour24(inMap);
	}
	//위험도예측>생산공정>247일
	public List<Map> listPractProcsRiskDay7(Map inMap){
		logger.debug("listPractProcsRiskDay7:"+inMap);
		return riskStatisticsDAO.listPractProcsRiskDay7(inMap);
	}
	//위험도 예측-생산공정> 4주 7일씩 합계
	public Map getPractProcsRiskDay7forWeek(Map inMap){
		logger.debug("getPractProcsRiskDay7forWeek:"+inMap);
		return riskStatisticsDAO.getPractProcsRiskDay7forWeek(inMap);
	}
	
	//위험도예측>생산공정>리스트> 시간조회
	public List<Map> pagePractProcsRiskHourMax(Map inMap){
		logger.debug("pagePractProcsRiskHourMax:"+inMap);
		List<Map> resEqumtTop5 = riskStatisticsDAO.pagePractProcsRiskHourMax(inMap);
		return resEqumtTop5;
	}

	//위험도예측>생산공정>리스트>시간총건수
	public int countPractProcsRiskHourMax(Map inMap) {
		logger.debug("countPractProcsRiskHourMax:"+inMap);
		return riskStatisticsDAO.countPractProcsRiskHourMax(inMap);
	}
	
	//위험도예측>생산공정>리스트> 일간조회
	public List<Map> pagePractProcsRiskDayMax(Map inMap){
		logger.debug("pagePractProcsRiskDayMax:"+inMap);
		List<Map> resEqumtTop5 = riskStatisticsDAO.pagePractProcsRiskDayMax(inMap);
		return resEqumtTop5;
	}

	//위험도예측>생산공정>리스트>일간총건수
	public int countPractProcsRiskDayMax(Map inMap) {
		logger.debug("countPractProcsRiskDayMax:"+inMap);
		return riskStatisticsDAO.countPractProcsRiskDayMax(inMap);
	}
}