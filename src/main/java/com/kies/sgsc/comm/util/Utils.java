package com.kies.sgsc.comm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.kies.sgsc.comm.exception.CodeContrants;
import com.kies.sgsc.servlet.SessionUtil;

public class Utils {
	private static final Random random = new Random();
	
	public static void addSuccessMsg(Map retMap) {
		retMap.put(CodeContrants.RESULT_CODE_LABEL,CodeContrants.NOR_CODE);
		retMap.put(CodeContrants.RESULT_MSG_LABEL,CodeContrants.NOR_MESSAGE);
	}
	
	public static void addSuccessMsg(Map retMap, String message) {
		retMap.put(CodeContrants.RESULT_CODE_LABEL,CodeContrants.NOR_CODE);
		retMap.put(CodeContrants.RESULT_MSG_LABEL,message);
	}
	
	//타입아이디별로 
	public static String makeGroupTypeIdQuery(List<Map> typeList) {
		StringBuilder causeQuery = new StringBuilder();
		Map inputMap = new HashMap();
		for(Map toMap : typeList) {
			String minMax = Utils.NVL(toMap.get("grp_cal_cd"),"max");
			String defVal = Utils.NVL(toMap.get("default_val"),"");
			causeQuery.append(",").append(minMax).append("(CASE WHEN b.RISK_CLS_CD = 'RC0001' AND b.risk_type_id='").append(toMap.get("risk_type_id")).append("' then \n")
			.append("  concat(lpad(a.RISK_NUM,5,'0'),':' ,(SELECT \n")
			.append("		c.CD_NM \n")
			.append(" FROM tc_common_code c \n")
			.append(" WHERE CD_GRP_ID='RISK_STEP_CD' \n")
			.append(" AND c.cd_id = a.RISK_STEP_CD))\n")
			.append("WHEN USE_YN='Y' and b.risk_type_id='").append(toMap.get("risk_type_id")).append("' THEN a.RISK_NUM\n")
			.append("when b.risk_type_id='").append(toMap.get("risk_type_id")).append("' then  case when a.RISK_VAL ='' then '"+defVal+"' ELSE ifnull(a.RISK_VAL,'"+defVal+"') end  END) as ").append(toMap.get("risk_type_id")).append("\n");
		}
		return causeQuery.toString();
	}
	
	public static String makeCauseQuery(List<Map> typeList) {
		
		StringBuilder causeQuery = new StringBuilder();
		Map inputMap = new HashMap();
		for(Map toMap : typeList) {
			String defVal = Utils.NVL(toMap.get("default_val"),"");
			inputMap.put("anys_sys_cd", toMap.get("anys_sys_cd"));
			if("Y".equals(toMap.get("use_yn"))) {
				causeQuery.append(",(SELECT\n")
				.append("\tt.cd_nm\n")
				.append("FROM th_eqmt_risk_anay_rtme_load_min z , tc_common_code t \n")
				.append("where  z.anys_ymdhi = a.anys_ymdhi \n")
				.append("and  z.anys_sys_cd = '"+toMap.get("anys_sys_cd")+"' \n")
				.append("and z.risk_type_id = '"+toMap.get("risk_type_id")+"' \n")	
				.append("and  z.eqmt_id =a.eqmt_id \n")
				.append("and  t.CD_GRP_ID='RISK_STEP_CD' \n")
				.append("AND t.cd_id = z.risk_step_cd) as \"").append(toMap.get("risk_type_id")+"\"").append("\n");
			}else { 
				causeQuery.append(",(SELECT\n")
				.append("\t  case when z.risk_val ='' then '"+defVal+"' ELSE ifnull( z.risk_val,'"+defVal+"') end \n")
				.append("FROM th_eqmt_risk_anay_rtme_load_min z \n")
				.append("where  z.anys_ymdhi = a.anys_ymdhi \n")
				.append("and  z.anys_sys_cd = '"+toMap.get("anys_sys_cd")+"' \n")
				.append("and z.risk_type_id = '"+toMap.get("risk_type_id")+"' \n")	
				.append("and  z.eqmt_id =a.eqmt_id) as \"").append(toMap.get("risk_type_id")+"\"").append("\n");
			}
		}
		return causeQuery.toString();
	}
	
	
	public static String makeCauseNumQuery(List<Map> typeList) {
		
		StringBuilder causeQuery = new StringBuilder();
		Map inputMap = new HashMap();
		for(Map toMap : typeList) {
			String defVal = Utils.NVL(toMap.get("default_val"),"");
			inputMap.put("anys_sys_cd", toMap.get("anys_sys_cd"));
			if("Y".equals(toMap.get("use_yn"))) {
				causeQuery.append(",(SELECT\n")
				.append("\tconcat(lpad(z.RISK_NUM,5,0),':',t.cd_nm)\n")
				.append("FROM th_eqmt_risk_anay_rtme_load_min z , tc_common_code t \n")
				.append("where  z.anys_ymdhi = a.anys_ymdhi \n")
				.append("and  z.anys_sys_cd = '"+toMap.get("anys_sys_cd")+"' \n")
				.append("and z.risk_type_id = '"+toMap.get("risk_type_id")+"' \n")	
				.append("and  z.eqmt_id =a.eqmt_id \n")
				.append("and  t.CD_GRP_ID='RISK_STEP_CD' \n")
				.append("AND t.cd_id = z.risk_step_cd) as \"").append(toMap.get("risk_type_id")+"\"").append("\n");
			}else { 
				causeQuery.append(",(SELECT\n")
				.append("\t  case when z.risk_val ='' then '"+defVal+"' ELSE ifnull( z.risk_val,'"+defVal+"') end \n")
				.append("FROM th_eqmt_risk_anay_rtme_load_min z \n")
				.append("where  z.anys_ymdhi = a.anys_ymdhi \n")
				.append("and  z.anys_sys_cd = '"+toMap.get("anys_sys_cd")+"' \n")
				.append("and z.risk_type_id = '"+toMap.get("risk_type_id")+"' \n")	
				.append("and  z.eqmt_id =a.eqmt_id) as \"").append(toMap.get("risk_type_id")+"\"").append("\n");
			}
		}
		return causeQuery.toString();
	}

	
	public static String makeConCatCauseQuery(List<Map> typeList, String columnnm) {
		
		StringBuilder causeQuery = new StringBuilder();
		Map inputMap = new HashMap();
		boolean isnotFist = false;
		
		int tSize = typeList.size();
		if(tSize>0 ) causeQuery.append(",concat(\n");
		
		for(int idx=0;idx<tSize;idx++ ) {
			Map toMap = typeList.get(idx);
			inputMap.put("anys_sys_cd", toMap.get("anys_sys_cd"));
			
			if("Y".equals(toMap.get("use_yn"))) {
				causeQuery.append("ifnull((SELECT\n")
				.append("\tt.cd_nm\n")
				.append("FROM th_eqmt_risk_anay_rtme_load_min z , tc_common_code t \n")
				.append("where  z.anys_ymdhi = a.anys_ymdhi \n")
				.append("and  z.anys_sys_cd = '"+toMap.get("anys_sys_cd")+"' \n")
				.append("and z.risk_type_id = '"+toMap.get("risk_type_id")+"' \n")	
				.append("and  z.eqmt_id =a.eqmt_id \n")
				.append("and  t.CD_GRP_ID='RISK_STEP_CD' \n")
				.append("AND t.cd_id = z.risk_step_cd) ,'') ").append("\n");
				
				if(idx < tSize-1) causeQuery.append(" ,");
			}else {
				causeQuery.append("ifnull((SELECT\n")
				.append("\tz.risk_val\n")
				.append("FROM th_eqmt_risk_anay_rtme_load_min z \n")
				.append("where  z.anys_ymdhi = a.anys_ymdhi \n")
				.append("and  z.anys_sys_cd = '"+toMap.get("anys_sys_cd")+"' \n")
				.append("and z.risk_type_id = '"+toMap.get("risk_type_id")+"' \n")	
				.append("and  z.eqmt_id =a.eqmt_id) ,'') ").append("\n");
				
				if(idx < tSize-1) causeQuery.append(",");
			}
			if(idx < tSize-1) causeQuery.append(" ',', \n");
			isnotFist = true;
		}
		
		if(typeList.size()>0 )  causeQuery.append(" ) as ").append(columnnm).append("\n");
		return causeQuery.toString();
	}

	public static void settingModifyId(Map inMap) {
		inMap.put("regist_id", SessionUtil.getSnUsrId());
		inMap.put("update_id", SessionUtil.getSnUsrId());
	}
	
	
	public static String testMD5(String str){
		String MD5 = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes()); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			MD5 = null; 
		}
		return MD5;
	}

		
	public static boolean isNone(String str) {
		if(str==null ||str.length()==0) return true;
		else return false;
	}
	
	public static String NVL(String str, String def) {
		if(str==null ||str.length()==0) return def;
		else return str;
	}
	
	public static String NVL(String str) {
		return NVL(str,"");		
	}
	
	public static String NVL(Object str) {
		return NVL(str,"");		
	}
	
	public static String NVL(Object strobj, String def) {
		String str =(String)strobj;
		if(str==null ||str.length()==0) return def;
		else return str;
	}
	
	public static String makeImgKey() {
		long time = System.nanoTime();
		int rand = random.nextInt(999);
		String imgKey = time+"_";
		if(rand<10) imgKey+= "00"+rand;
		else if(rand<20) imgKey+= "0"+rand;
		else imgKey+=rand;
		return imgKey;
	}

	/**
	 * 페이지 네비게이션.
	 *
	 * @param	pageCnt 현재보여줄수있는인덱스 (totalRow/pageViewListCount) + (totalRow%pageViewListCount>0?1:0)
	 * @param	pageNo 현재인덱스 curIndex
	 * @param	listSize 출력인덱스총건수  viewIndexCount
	 * @return	String
	 * @author	이은준
	 * @since	2002-01-24
	 * @example Util.printPageNavi( (totalRow/pageViewListCount) + (totalRow%pageViewListCount>0?1:0)  ,Integer.parseInt(curIndex),viewIndexCount)
	 */
	public static String printPageNavi(int pageCnt, int pageNo, HttpServletRequest request) {
		StringBuffer sf = new StringBuffer();
		int listSize = 5;
		int startPageNo = ((pageNo - 1) / listSize) * listSize + 1;
		int endPageNo = startPageNo + listSize - 1;
		endPageNo = (pageCnt > endPageNo) ? endPageNo : pageCnt;
		int previousList = (startPageNo == 1) ? 0 : (startPageNo - 1);
		int nextList = (pageCnt > endPageNo) ? (endPageNo + 1) : 0;
		int previousPage = (pageNo == 1) ? 0 : (pageNo - 1);
		int nextPage = 0;

		if (pageCnt == 0) {
			nextPage = 0;
		} else {
			nextPage = (pageNo == pageCnt) ? 0 : (pageNo + 1);
		}
		
		if (previousList == 0) {
			sf.append("<li class='btn_arrow btn_prev_all'><img src='"+request.getContextPath()+"/resources/admin/images/comm/btn_arrow_all.png' alt='allPrev' /></li>");
		} else {
			//sf.append("<a href=\"javascript:jsSearch('1')\" class='paging__link paging__link_home fa fa-angle-double-left'>◀◀</a> ");
			sf.append("<a href='javascript:jsSearch(1)'><li class='btn_arrow btn_prev_all'><img src='"+request.getContextPath()+"/resources/admin/images/comm/btn_arrow_all.png' alt='allPrev'/></li></a>");
		}
		
		if (previousPage == 0) {
			//sf.append(" [이전] ");
			sf.append("<li class='btn_arrow btn_prev'><img src='"+request.getContextPath()+"/resources/admin/images/comm/btn_arrow.png' alt='prev' /></li>");
			
		} else {
			//sf.append(" <a href=\"javascript:jsSearch('" + previousPage + "')\">[이전]</a> ");
			sf.append("<a href='javascript:jsSearch(" + previousPage + ")'><li class='btn_arrow btn_prev'><img src='"+request.getContextPath()+"/resources/admin/images/comm/btn_arrow.png' alt='prev' /></li></a>");
		}

		/*if (previousPage == 0) {
			sf.append("<a class='paging__link paging__link_prev fa fa-angle-left'> ◀</a> ");
		} else {
			sf.append(" <a href=\"javascript:jsSearch('" + previousPage + "')\" class='paging__link paging__link_prev fa fa-angle-left'>◀</a> ");
		}*/

		for (int i = startPageNo; i <= endPageNo ; i++) {
			if (i == pageNo) {
				//sf.append("<a class='paging__link on'> " + i+"</a>");
				sf.append("<li class='btn_num btn_num_on'>" + i+"</li>");
			} else {
				//sf.append(" <a href=\"javascript:jsSearch('" + i + "');\" class='paging__link'>" + i + "</a> ");
				sf.append("<a href='javascript:jsSearch(" + i + ");'><li class='btn_num'> "+i+"</li></a>");
			}

			/*if (i < endPageNo) {
				sf.append("	ㅣ ");
			}*/
		}

		if (nextPage == 0) {
			//sf.append("<a class='paging__link paging__link_next fa fa-angle-right'>▶</a>");
			sf.append("<li class='btn_arrow btn_next'><img src='"+request.getContextPath()+"/resources/admin/images/comm/btn_arrow.png'/></li>");
		} else {
			//sf.append(" <a href='javascript:jsSearch('" + nextPage + "') ' class='paging__link paging__link_next fa fa-angle-right'>▶</a> ");
			sf.append("<a href='javascript:jsSearch(" + nextPage + ")'><li class='btn_arrow btn_next'><img src='"+request.getContextPath()+"/resources/admin/images/comm/btn_arrow.png'/></li></a>");
		}

		if (nextList == 0) {
			//sf.append("<a class='paging__link paging__link_end fa fa-angle-double-right'>▶▶</a>");
			sf.append("<li class='btn_arrow btn_next_all'><img src='"+request.getContextPath()+"/resources/admin/images/comm/btn_arrow_all.png' alt='nextAll'></li>");
		} else {
			//sf.append(" <a href='javascript:jsSearch('" + pageCnt + "')' class='paging__link paging__link_end fa fa-angle-double-right'>▶▶</a> ");
			sf.append("<a href='javascript:jsSearch(" + pageCnt + ")'><li class='btn_arrow btn_next_all'><img src='"+request.getContextPath()+"/resources/admin/images/comm/btn_arrow_all.png' alt='nextAll'></li></a>");
		}

		/*if (nextPage == 0) {
			sf.append(" [다음] ");
		} else {
			sf.append(" <a href=\"javascript:jsSearch('" + nextPage + "')\">[다음]</a> ");
		}*/

		return sf.toString();
	}
	
	
	public static String masterUserPageNavi(int pageCnt, int pageNo, HttpServletRequest request) {
		return masterUserPageNavi(pageCnt,pageNo,5,request);
	}
	
	
	public static String masterUserPageNavi(int pageCnt, int pageNo, int INDEX_SIZE, HttpServletRequest request) {
		StringBuffer sf = new StringBuffer();
		int listSize = INDEX_SIZE;
		int startPageNo = ((pageNo - 1) / listSize) * listSize + 1;
		int endPageNo = startPageNo + listSize - 1;
		endPageNo = (pageCnt > endPageNo) ? endPageNo : pageCnt;
		int previousList = (startPageNo == 1) ? 0 : (startPageNo - 1);
		int nextList = (pageCnt > endPageNo) ? (endPageNo + 1) : 0;
		int previousPage = (pageNo == 1) ? 0 : (pageNo - 1);
		int nextPage = 0;

		if (pageCnt == 0) {
			nextPage = 0;
		} else {
			nextPage = (pageNo == pageCnt) ? 0 : (pageNo + 1);
		}
		
		if (previousList == 0) {
			sf.append("<span class='first'><img src='"+request.getContextPath()+"/resources/master/common/images/comm/paging_first.png' alt='allPrev' /></span>");
		} else {
			sf.append("<a href='javascript:jsSearch(1)'><span class='first'><img src='"+request.getContextPath()+"/resources/master/common/images/comm/paging_first.png' alt='allPrev'/></span></a>");
		}
		
		if (previousPage == 0) {
			//sf.append(" [이전] ");
			sf.append("<span class='prev'><img src='"+request.getContextPath()+"/resources/master/common/images/comm/paging_prev.png' alt='prev'></span>");
		} else {
			//sf.append(" <a href=\"javascript:jsSearch('" + previousPage + "')\">[이전]</a> ");
			sf.append("<a href='javascript:jsSearch(" + previousPage + ")'><span class='prev'><img src='"+request.getContextPath()+"/resources/master/common/images/comm/paging_prev.png' alt='prev'></span></a>");
		}

		for (int i = startPageNo; i <= endPageNo ; i++) {
			if (i == pageNo) {
				sf.append("<a class='paging_on'>"+ i+"</a>");
			} else {
				sf.append("<a href='javascript:jsSearch(" + i + ");'>"+i+"</a>");
			}
		}

		if (nextPage == 0) {
			sf.append("<span class='next'><img src='"+request.getContextPath()+"/resources/master/common/images/comm/paging_next.png' alt='next'></span>");
		} else {
			sf.append("<a href='javascript:jsSearch(" + nextPage + ")'><span class='next'><img src='"+request.getContextPath()+"/resources/master/common/images/comm/paging_next.png' alt='next'></span></a>");
		}

		if (nextList == 0) {
			sf.append("<span class='last'><img src='"+request.getContextPath()+"/resources/master/common/images/comm/paging_last.png' alt='nextAll'></span>");
		} else {
			sf.append("<a href='javascript:jsSearch(" + pageCnt + ")'><span class='last'><img src='"+request.getContextPath()+"/resources/master/common/images/comm/paging_last.png' alt='nextAll'></span></a>");
		}

		return sf.toString();
	}
	

	
	
	public static String encSHA256(String str){
		String SHA = ""; 
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	}
	
	
	/**
	 * 세션정보 반환.
	 * @param request
	 * @return
	 */
	public static Map<String,String> getSessionMap(HttpServletRequest request){
		HttpSession session = request.getSession();
		Map<String,String> member = (Map<String,String>) session.getAttribute("member");
		System.out.println("member:"+member);
		return member;
	}
	
	/**
	 * 로그인 여부
	 * @param request
	 * @return
	 */
	public static boolean isLogin(HttpServletRequest request) {
		Map<String,String> member =getSessionMap(request);
		if(member==null || "".equals(Utils.NVL(member.get("USER_ID"))))
			return false;
		else return true;
	}
	
	public static String forwordLogin(ModelMap model,HttpServletRequest request) {
		request.setAttribute("stateCd", "-9");
    	return "redirect:/admin/login.do";
	}



		
}
