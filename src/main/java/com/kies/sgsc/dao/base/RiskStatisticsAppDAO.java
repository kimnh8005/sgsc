package com.kies.sgsc.dao.base;

import java.util.List;
import java.util.Map;

public interface RiskStatisticsAppDAO {
	public String lastDateYmdHI(Map inMap);
	public String lastDateYmdH(Map inMap); 
	
	public Map allRtimeFactyRateRate(Map inMap);
	public List<Map> listRiskHigtFacty(Map inMap);
	
	public List<Map> listRiskHigtProcs(Map inMap);
	
	public List<Map> listRiskHigtEqmt(Map inMap);
	
	//위험도 높은 시설 >슬라이스 > 시나리오
	public List<Map> listRiskHigtEqmtForScno(Map inMap);
	
	public Map getRiskCauseEqmt(Map inMap);
	
	public List<Map> rtimeEqmtHistory(Map inMap);
	
	//실시간위험도-설비분석데이터
	public List<Map> listEqmtRiskCrig(Map inMap);
	
	//실시간위험도-설비분석 및 이상원인 모두
	public List<Map> listEqmtRiskCrigApp(Map inMap);
	
	//실시간  위험도-예지보전위험도
	public List<Map> listEqmtRiskPARTDB(Map inMap);
	
	//실시간  위험도-예지보전위험도 앱
	public List<Map> listEqmtRiskPARTDBApp(Map inMap); 
	
	//실시간  위험도-예지보전위험도
	public List<Map> listEqmtRiskPARTDBAppforscno(Map inMap);  
	
	
	//실시간  위험도-영상데이터분석위험도
	public List<Map> listEqmtRiskKSEC(Map inMap);
	
	//실시간  위험도-영상데이터분석위험도
	public List<Map> listEqmtRiskKSECApp(Map inMap);
	
	
	//실시간  위험도-영상데이터분석위험도 시나리오 
	public List<Map> listEqmtRiskKSECAppforscno(Map inMap);
	
	//예측 위험도 높은 설비
	public List<Map> listPractEqmtRiskTop5(Map inMap);
	
	//위험도예측>설비>리스트 >시간조회
	public List<Map> pagePractEqmtRiskHourMax(Map inMap);			
	
	//위험도예측>설비>리스트 >시간총건수
	public int countPractEqmtRiskHourMax(Map inMap);
	
	//위험도예측>설비>리스트 >일간조회
	public List<Map> pagePractEqmtRiskDayMax(Map inMap);			
	
	//위험도예측>설비>리스트 >일간총건수
	public int countPractEqmtRiskDayMax(Map inMap);
		
		
	//위험도 예측>설비>  24시간 3시간 주기 예측
	public List<Map> listPractEqmtRiskHour24(Map inMap);
	//위험도 예측 -7일
	public List<Map> listPractEqmtRiskDay7(Map inMap); 
	//위험도 예측-4주 7일씩 합계
	public Map getPractEqmtRiskDay7forWeek(Map inMap); 
	
	//위험도 이력- 생산공정조회
	public List<Map> listRisktProcsHistory(Map inMap);
	
	//위험도 이력- 생산공정조회 페이징
	public List<Map> pageRisktProcsHistory(Map inMap);
	////위험도 이력- 생산공정조회 건수
	public int countRisktProcsHistory(Map inMap);
	
	
	//위험도 이력 -통합위험도 생산공정(시간별)
	public List<Map> listRiskProcs3HourHistory(Map inMap);  
	
	//위험도 이력 -통합위험도 생산공정(일별)
	public List<Map> listRiskProcsDayHistory(Map inMap);
	
	//위험도이력 설비별 상세 3시간
	public List<Map> listRiskEqmt3HourHistory(Map inMap);  
	
	//위험도이력 설비별 상세 일별
	public List<Map> listRiskEqmtDayHistory(Map inMap);   
	
	//위험도이력 - 센서데이터 분석 위험도 3시간
	public List<Map> listRiskCRIG3HourHistory(Map inMap);  
	
	//위험도이력 - 센서데이터 분석 위험도 3시간 앱용
	public List<Map> listRiskCRIG3HourHistoryApp(Map inMap);  
	
	//위험도 이력 - 예지보전 위험도
	public List<Map> listRiskPARTDB3HourHistory(Map inMap);  
	
	//위험도 이력 - 예지보전 위험도 앱용
	public List<Map> listRiskPARTDB3HourHistoryApp(Map inMap);
	
	//위험도 이력 - 시나리오 예지보전 위험도
	public List<Map> listRiskPARTDB3HourHistoryAppforscno(Map inMap);
	
	//위험도 이력 - 영상데이터 분석 위험도
	public List<Map> listRiskKSEC3HourHistory(Map inMap);  
	
	//위험도 이력 - 영상데이터 분석 위험도 앱용
	public List<Map> listRiskKSEC3HourHistoryApp(Map inMap); 
	 
	//위험도 이력- 영상데이터 분석 위험도 시나리오
	public List<Map> listRiskKSEC3HourHistoryAppforscno(Map inMap);  
	
	//가스위험성제안
	public List<Map> listProposeGasRisk(Map inMap);  
	
	//가스위험성제안-페이징
	public List<Map> pageProposeGasRisk(Map inMap);
		
	//가스위험성제안-카운팅
	public int countProposeGasRisk(Map inMap);
	
	//위험도예측>시설전체
	public Map getPractFactyRiskAll(Map inMap);  
	
	//위험도예측>시설24시간
	public List<Map> listPractFactyRiskHour24(Map inMap);
	//위험도예측>시설247일
	public List<Map> listPractFactyRiskDay7(Map inMap); 
	//위험도 예측-시설  4주 7일씩 합계
	public Map getPractFactyRiskDay7forWeek(Map inMap); 
	
	
	//위험도예측>생산공정>24시간
	public List<Map> listPractProcsRiskHour24(Map inMap);
	//위험도예측>생산공정>247일
	public List<Map> listPractProcsRiskDay7(Map inMap); 
	//위험도 예측-생산공정> 4주 7일씩 합계
	public Map getPractProcsRiskDay7forWeek(Map inMap); 
	
	//위험도예측>생산공정>리스트 > 시간조회
	public List<Map> pagePractProcsRiskHourMax(Map inMap);			
	
	//위험도예측>생산공정>리스트 > 시간총건수
	public int countPractProcsRiskHourMax(Map inMap);
	
	//위험도예측>생산공정>리스트> 일간조회
	public List<Map> pagePractProcsRiskDayMax(Map inMap);			
	
	//위험도예측>생산공정>리스트> 일간총건수
	public int countPractProcsRiskDayMax(Map inMap);
	
	/*사고시나리오 실시간 분 팝업- 위험율 
	 * (통합위험율: KIES01, KSEC01:위험율,  PARTDB:고장확율, CRIG:발생확율
		anys_type_cd- AT1000:실시간  AT2000:예측 
		factory_hrrc_cd : 'FH1000':계통(시설), FH1000:FH3000 FH2000:시설(공정)
		factory_id : 각 아이디
	*/
	//public List<Map> lisScnrsHourlyDesc(Map inMap);
	
	/*사고시나리오 실시간 시간 팝업- 위험율 
	 * (통합위험율: KIES01, KSEC01:위험율,  PARTDB:고장확율, CRIG:발생확율
		anys_type_cd- AT1000:실시간  AT2000:예측 
		factory_hrrc_cd : 'FH1000':계통(시설), FH1000:FH3000 FH2000:시설(공정)
		factory_id : 각 아이디
	*/
	public List<Map> lisScnrsHourlyDesc(Map inMap);
	
	public List<Map> commLisScnrsHourlyDesc(Map inMap);
	
	public List<Map> commLisScnrsDailyDesc(Map inMap);
	
	public List<Map> listToEqmtRiskAnalysisHistoryByAnlySysCd(Map inMap);
}

