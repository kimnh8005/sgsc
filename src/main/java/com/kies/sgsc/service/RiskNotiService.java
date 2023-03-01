package com.kies.sgsc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.comm.util.FcmMsgUtil;
import com.kies.sgsc.comm.util.Utils;
import com.kies.sgsc.dao.base.CommonCodeDAO;
import com.kies.sgsc.dao.base.RiskNotiDAO;
import com.kies.sgsc.dao.base.RiskSendDAO;
import com.kies.sgsc.dao.base.SendRefugeDAO;
import com.kies.sgsc.dao.base.UserDAO;
import com.kies.sgsc.dao.base.UserDevicesDAO;
import com.kies.sgsc.socket.SgscWebSocketManager;

@Service
public class RiskNotiService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	RiskNotiDAO riskNotiDAO;
	
	@Autowired
	RiskSendDAO riskSendDAO;
	
	@Autowired
	SendRefugeDAO sendRefugeDAO;
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	CommonCodeDAO codeDao;
	
	@Autowired
	UserDevicesDAO userDevicesDAO;

	/**
	 * 메시지 전송
	 * @param inMap
	 * @return
	 */
	public String sendMessage(Map inMap) {
		String retStr = "";
		logger.debug("inMap:"+inMap);
		
		//SEND_TYPE_CD발송타입코드: SD0001:실시간위험도경고,SD0002:비상대피알림,
		//          SD0003:가스누출위발행,SD0004:가스제어필요,SD0005:가스자동제어완료,SD0006:위험성제안
		//SEND_STAT_CD 전송상태코드 SS0001:전송중,SS0002:전송완료
		
		boolean isAll = true; 
		List<String> authSidList = (List<String>)inMap.get("auth_sid");
		
		logger.debug("authSidList:"+authSidList);
		if(authSidList!=null && authSidList.size()>0) isAll = false;
		
		String dept_cd = Utils.NVL(inMap.get("dept_cd"),"");  
		List<Map> sendUserList = new ArrayList<Map>();
		Map userInput = new HashMap(); 
		userInput.put("dept_cd",dept_cd);
		//userInput.put("mobile_push_yn","Y");
		if(isAll) {
			sendUserList = userDevicesDAO.listUserDevices(userInput);
			//sendUserList = userDao.listUserDevice(new HashMap());
		 }else {
			 for(String authSid : authSidList) {
				 userInput.put("auth_sid",authSid);
				 sendUserList.addAll(userDevicesDAO.listUserDevices(userInput));
			 }
		}
		
		logger.debug("sendUserList:"+sendUserList);
		if(sendUserList==null || sendUserList.size()==0) {
			logger.debug("전송대상이 없으므로 전송하지 않음.");
			throw new BusinessException(CodeContrants.ERR_SENDMESSAGE,"전송대상이 없으므로 전송하지 않음");
			//return "F";
		}
		
		String eqmt_id = Utils.NVL(inMap.get("eqmt_id"),""); 
		String procs_id = Utils.NVL(inMap.get("procs_id"),""); 
		String facty_id = Utils.NVL(inMap.get("facty_id"),""); 
		String sendTypeCd = Utils.NVL(inMap.get("send_type_cd"),"SD0002"); //1.발송타입코드
		String send_stat_cd = Utils.NVL(inMap.get("send_stat_cd"),"SS0002"); 
		String risk_step_cd = Utils.NVL(inMap.get("risk_step_cd"),"");  //위험도타입.
		
		String cbm_fixd_rate ="0.0"; //위험율.
		if(inMap.get("cbm_fixd_rate")!=null) {
			cbm_fixd_rate = String.valueOf(inMap.get("cbm_fixd_rate"));
		}
		String send_msg = Utils.NVL(inMap.get("send_msg"),"");  //메시지.
		
		Map cMap = new HashMap();
		cMap.put("cd_grp_id","SEND_TYPE_CD"); //발송타입코드
		cMap.put("cd_id",sendTypeCd); 
		cMap = codeDao.getCommonCode_cd_id(cMap);
		logger.debug("cMap:"+cMap);
		String title = Utils.NVL(cMap.get("cd_nm"),"");  //2.제목
		
		///////////위험도 전송 마스터 ///////////.
		Map riskNotiMap = new HashMap();
		riskNotiMap.put("send_type_cd",sendTypeCd);
		riskNotiMap.put("eqmt_id",eqmt_id);
		riskNotiMap.put("procs_id",procs_id);
		riskNotiMap.put("facty_id",facty_id);
		riskNotiMap.put("facty_id",facty_id);
		riskNotiMap.put("send_stat_cd",send_stat_cd); 
		riskNotiMap.put("send_msg",send_msg);
		riskNotiMap.put("cbm_fixd_rate",cbm_fixd_rate);
		/////////// 위험도 전송 회차 ////////////////
		
		Map riskSendMap = new HashMap();
			
		if(sendTypeCd.equals("SD0001")) { //실시간위험도경고(모든 사용자)
			//설비의 위험도 단계가 정상이 아니고 같은 위험도 단계로 push 발송 받은 적 없는 경우
			//title: 실시간 위험도 경고
			//생산공정B1 >  TANK-09-03 화재 위험도가 경고 32.7% 상태입니다.
			title = "실시간 위험도 경고";
			riskNotiMap.put("send_stat_cd","SS0002"); //전송완료
			
		}else if(sendTypeCd.equals("SD0002")) { //비상대피알림 (설정된 발송 대상자)
			//관리자 권한의 사용자가 비상 대피 메시지를 전송한 경우
			//title: 비상 대피 알림
			//생산공정A1에서 가스 누출이 발생했습니다. 긴급 조치가 필요합니다.
			title = "비상 대피 알림";
			riskNotiMap.put("send_stat_cd","SS0002"); //전송완료
			
		}else if(sendTypeCd.equals("SD0003")) { //가스누출위발행
			//누출위험이 있는 사고 시뮬레이션 결과가 분석되었을 경우
			//title: 가스 누출 위험 발생
			//생산공정A1에서 가스 누출이 발생했습니다. 긴급 조치가 필요합니다.
			title = "가스 누출 위험 발생";
			riskNotiMap.put("send_stat_cd","SS0002"); //전송완료
			
		}else if(sendTypeCd.equals("SD0004")) { //가스제어필요
			//해당 생산공정의 위험도가 경고 이상일 경우
			//title: 가스 제어 필요
			//생산공정A1의 가스 밸브의 제어가 필요합니다.
			title = "가스 제어 필요";
			riskNotiMap.put("send_stat_cd","SS0002"); //전송완료
		}else if(sendTypeCd.equals("SD0005")) { //가스자동제어완료
			//title: 가스 자동제어 완료
			//생산공정A1의 가스 밸브가 자동 차단 되었습니다.
			title = "가스 자동제어 완료";
			riskNotiMap.put("send_stat_cd","SS0002"); //전송완료
		}else if(sendTypeCd.equals("SD0006")) { //위험성 제안
			//위험성 제안 발생시 발송(모든 사용자)
			title = "위험성 제안 발생";
			riskNotiMap.put("send_stat_cd","SS0002"); //전송완료
		}else {
			throw new BusinessException(CodeContrants.ERR_SENDMESSAGE,"사용불가능한 발송타입코드 입니다. ");
		}
			
		riskNotiMap.put("title",title);
		
		riskNotiDAO.insertRiskNoti(riskNotiMap);  //위험도알림 전송 마스터
		
		int send_sid = (int)riskNotiMap.get("send_sid");
		riskSendMap.put("send_sid", send_sid);
		riskSendMap.put("send_rnd",1);
		riskSendMap.put("send_ymdhis",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		riskSendDAO.insertRiskSend(riskSendMap); // 위험도 전송회차
		
		/////////////사용자 전송 내역 ////////////////////
		List<String> iosSendLIst = new ArrayList<String>();
		List<String> andSendLIst = new ArrayList<String>();
		for(Map userMap : sendUserList) {
			Map sendRefugeMap = new HashMap();
			sendRefugeMap.put("send_sid", send_sid);
			sendRefugeMap.put("send_rnd",riskSendMap.get("send_rnd"));
			sendRefugeMap.put("push_yn",userMap.get("mobile_push_yn"));  //알림화면에는 N 인 사용자만 보여줌.
			sendRefugeMap.put("user_id",userMap.get("user_id"));
			sendRefugeMap.put("user_cfm_yn","N");
			sendRefugeDAO.insertSendRefuge(sendRefugeMap);
						
			//전송 허용자에게만 전송.
			if("Y".equals(Utils.NVL(userMap.get("mobile_push_yn"),"N"))){
				String mobile_os_type_cd = Utils.NVL(userMap.get("mobile_os_type_cd"));
				if(mobile_os_type_cd.equalsIgnoreCase("IOS")) {
					iosSendLIst.add((String)userMap.get("mobile_token"));
				}else {
					andSendLIst.add((String)userMap.get("mobile_token"));
				}
			}
		}
		try {
			logger.debug("IOS 전송대상:"+iosSendLIst);
			FcmMsgUtil.sendMessage(iosSendLIst, title, title+":"+send_msg);
		}catch(Throwable e) {
			throw new BusinessException(CodeContrants.ERR_SENDMESSAGE,"ios 메시지 전송 오류");
		}
		try {
			logger.debug("안드로이드 전송대상:"+andSendLIst);
			FcmMsgUtil.sendMessage(andSendLIst, title, send_msg);
		}catch(Throwable e) {
			throw new BusinessException(CodeContrants.ERR_SENDMESSAGE,"안드로이드 메시지 전송 오류");
		}
		
		//실시간 위험도는 화면으로 알람 표시
		if(sendTypeCd.equals("SD0001")) {
			try {
				SgscWebSocketManager.broadcast(send_msg);
			}catch(Throwable e) {
				throw new BusinessException(CodeContrants.ERR_SENDMESSAGE,"웹소켓전송 오류");
			}
		}
		
		return "S";
	}
	
	public int insertRiskNoti(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("insertRiskNoti:"+inMap);
		return riskNotiDAO.insertRiskNoti(inMap);
	}

	public String getRiskNotiKey() {
		return riskNotiDAO.getRiskNotiKey();
	}

	public int deleteRiskNoti(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("deleteRiskNoti:"+inMap);
		return riskNotiDAO.deleteRiskNoti(inMap);
	}

	public int updateRiskNoti(Map inMap){
		Utils.settingModifyId(inMap);
		logger.debug("updateRiskNoti:"+inMap);
		return riskNotiDAO.updateRiskNoti(inMap);
	}

	public Map getRiskNoti_send_sid(Map inMap){
		logger.debug("getRiskNoti_send_sid:"+inMap);
		return riskNotiDAO.getRiskNoti_send_sid(inMap);
	}

	public List<Map> listRiskNoti(Map inMap){
		logger.debug("listRiskNoti:"+inMap);
		return riskNotiDAO.listRiskNoti(inMap);
	}

	public List<Map> pageRiskNoti(Map inMap){
		logger.debug("pageRiskNoti:"+inMap);
		return riskNotiDAO.pageRiskNoti(inMap);
	}

	public int countRiskNoti(Map inMap){
		logger.debug("countRiskNoti:"+inMap);
		return riskNotiDAO.countRiskNoti(inMap);
	}

}