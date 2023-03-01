const API_HOST = 'http://222.233.76.214:8080';
const KSEC_API_HOST = 'http://118.129.135.145:2143';

const API_URL = {
	LOGIN: API_HOST + '/sgsc/sys/login',
	LOGOUT: API_HOST + '/sgsc/sys/logout',
	GET_MENU: API_HOST + '/sgsc/comm/menu?prt_tgt_cd=WORKER',
	
	// 공통코드 조회
	COMMON_CODE: API_HOST + '/sgsc/comm/code',
	// 권한 조회
	COMMON_AUTH: API_HOST + '/sgsc/comm/auth',
	
	// 실시간 위험도
	REALTIME_RISK: API_HOST + '/sgsc/sts/mainrtimetop5ForScno',
	// 실시간위험도>설비분석데이터>센서 데이터 분석 위험도
	REALTIME_EQUIPMENT_CRIG: API_HOST + '/sgsc/sts/rtimeeqmtcrigforsnro',
	// 실시간 위험도 > 사고 시나리오 팝업
	REALTIME_ACCIDENT_SCENARIO_POPUP: API_HOST + '/sgsc/sts/lisscnrsminreal',
	// 위험도예측 > 24시간 예측 (3시간 주기)
	PREDICT_EQUIPMENT_HOUR24: API_HOST + '/sgsc/sts/practeeqmtHour24forsnro',
	// 위험도예측 > 7일 예측
	PREDICT_EQUIPMENT_DAY7: API_HOST + '/sgsc/sts/practeeqmtDay7',
	// 위험도예측 > 4주예측
	PREDICT_EQUIPMENT_WEEK7: API_HOST + '/sgsc/sts/practeeqmtWeek4',
	// 예지보전 위험도
	REALTIME_EQUIPMENT_PART_DB: API_HOST + '/sgsc/sts/rtimeeqmtpartdbforsnro',
	// 영상 데이터 분석 위험도
	REALTIME_EQUIPMENT_KSEC: API_HOST + '/sgsc/sts/rtimeeqmtksecforsnro',
	// 위험도 이력
	RISK_HISTORY: API_HOST + '/sgsc/sts/listrisktprocshis',
	// 위험도 이력 >공정 상세 > 통합위험도|생산공정 (cols 형식)
	RISK_PROCESS_HISTORY_MAP: API_HOST + '/sgsc/sts/listrisktprocsdeschismap',
	// 위험도 이력 >공정 상세 > 설비별 위험도 (cols형식)
	RISK_EQUIPMENT_HISTORY_MAP: API_HOST + '/sgsc/sts/listriskteqmtdeschismap',
	// 위험도 이력 >공정 상세 > 설비상세>  센서데이터 분석위험도
	RISK_EQUIPMENT_CRIG_HISTORY: API_HOST + '/sgsc/sts/listriskteqmtcrighisforsnro',
	// 위험도 이력 >공정 상세 > 설비상세> 예지보전 위험도
	RISK_EQUIPMENT_PART_DB_HISTORY: API_HOST + '/sgsc/sts/listriskteqmtpartdbhisforsnro',
	// 위험도 이력 >공정 상세 > 설비상세> 영상데이터 분석 위험도
	RISK_EQUIPMENT_KSEC3_HISTORY: API_HOST + '/sgsc/sts/listriskteqmtksec3hisforsnro',
	// 위험도 예측 > 예측 위험도 높은 설비
	PREDICT_EQUIPMENT_RISK_TOP10: API_HOST + '/sgsc/sts/practeqmtrisktop10forsnro',
	// 생산공정 조건별 조회
	PROCESS: API_HOST + '/sgsc/comm/process',
	// 설비 조건별 조회
	EQUIPMENT: API_HOST + '/sgsc/comm/equipment', // ?procs_id=P00001 형식으로 조회

	// 가스 확산분석 목록 조회
	GAS_DIFFUSION_LIST: KSEC_API_HOST + '/api/selGasDiffsList',

	// 사고대응메뉴얼 페이징조회
	PAGE_INCIDENT_MANUAL: API_HOST + '/sgsc/comm/pageindtmual',
	// 사고대응메뉴얼 단건 조회
	INCIDENT_MANUAL: API_HOST + '/sgsc/comm/indtmual',
	// 비상연락망 페이징조회
	PAGE_EMERGENCY_CONTACT: API_HOST + '/sgsc/comm/pageemerycontact',
	// 비상연락망 단건조회
	EMERGENCY_CONTACT: API_HOST + '/sgsc/comm/emerycontact',
	// 비상대피메시지 전송
	SEND_MESSAGE: API_HOST + '/sgsc/comm/sendmessage',

	// 가스 위험성 제안 리스트
	GAS_RISK_PROPOSAL_LIST: API_HOST + '/sgsc/work/pagegasriskprps',
	// 작업자 오류 제안(= 휴먼에러제안) 리스트
	HUMAN_ERROR_PROPOSAL_LIST: API_HOST + '/sgsc/work/pagehumanerrprps',
	// 금일 작업 오류율
	HUMAN_ERROR_DAY_RATE: API_HOST + '/sgsc/work/dayrate',
	// 7일 작업 오류율
	HUMAN_ERROR_DAY_7_RATE: API_HOST + '/sgsc/work/day7rate',
	// 위험도 알림이력 목록
	RISK_NOTICE_HISTORY_LIST: API_HOST + '/sgsc/comm/pagerisknoti',
	// 위험도 알림이력
	RISK_NOTICE_HISTORY: API_HOST + '/sgsc/comm/risknoti',
	// 작업 일지 리스트
	WORK_JOURNAL_LIST: API_HOST + '/sgsc/work/pageworkjnl',
	// 작업 일지 단건 조회/수정/등록/삭제
	WORK_JOURNAL: API_HOST + '/sgsc/work/workjnl',

	// 공지사항 목록
	NOTICE_LIST: API_HOST + '/sgsc/comm/pagenotice',
	// 공지사항 단건 조회/수정/등록
	NOTICE: API_HOST + '/sgsc/comm/notice',
	// 사용자 등록/수정
	USER: API_HOST + '/sgsc/comm/user',
	// 비밀번호 변경
	CHANGE_PASSWORD: API_HOST + '/sgsc/comm/changepassword',
};
export default API_URL;