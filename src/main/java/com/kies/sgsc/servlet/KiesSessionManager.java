package com.kies.sgsc.servlet;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kies.sgsc.comm.exception.BusinessException;
import com.kies.sgsc.servlet.interceptor.JwtInterceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class KiesSessionManager {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private static final String SALT =  "SGSC_SALT";
	
	/**
	 * 사용자 세션 관리
	 * 추후 디비로 관리 될수  있음.
	 * String token
	 */
	private Map<String , KiesSessionDto> GLOBAL_SESSION_MAP = new HashMap<String,KiesSessionDto>();
	
	//중복으로 토큰 마다 세팅되는것을 방지하기 위해 추가.
	private Map<String , Map> GLOBAL_USER_MAP = new HashMap<String,Map>();
	
	public String login(Map userinfo) {
		
		String userId = (String)userinfo.get("user_id");
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String prejwt = request.getHeader(JwtInterceptor.HEADER_AUTH);
		//사전로그인체크 ///
		if(prejwt!=null && isUsable(prejwt) && GLOBAL_SESSION_MAP.containsKey(prejwt)) {
			return prejwt;
		}
		
		Map claims = new HashMap();
		claims.put("user_id", userId);
		claims.put("user_nm", userinfo.get("user_nm"));
		
		//소멸시간 밤12시로 세팅
		String jwt = Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setHeaderParam("alg", SignatureAlgorithm.HS256)
			.setClaims(claims)
			.setSubject(userId)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60  * 180 )))  // 60분 1000 * 60 * 60 
			.signWith(SignatureAlgorithm.HS256, this.generateKey())
			.compact();  
		
		//최종정보로 세팅. userMap device 마다 로그인 처리를 다 하려면 userinfo 만 cache 처리해야한다.
		GLOBAL_USER_MAP.put(userId, userinfo); 
		
		KiesSessionDto sessionDto = new KiesSessionDto();
		sessionDto.setToken(jwt);
		sessionDto.setData(GLOBAL_USER_MAP.get(userId));
		sessionDto.setDeptId((String)userinfo.get("dept_cd")); //부서
		sessionDto.setDevice((String)userinfo.get("device")); //접속장비
		
		logger.debug("GLOBAL_USER_MAP:"+userId+":"+GLOBAL_USER_MAP.get(userId));
		GLOBAL_SESSION_MAP.put(jwt, sessionDto);
		logger.debug("GLOBAL_SESSION_MAP:"+jwt+":"+GLOBAL_SESSION_MAP.get(jwt));
		SessionUtil.setUserSession(sessionDto);
		
		return jwt;
	}
	
	private byte[] generateKey(){
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
		}
		return key;
	}
	
	
	/**
	 * 저장 세션 clear
	 * 테스트 필요함. 로그인 요청 및 로그아웃 요청이 올때 마다 clear 처리해줌.
	 */
	public void removeSessionObject() {
		
		//세션메니저에 존재 하는것들.
		logger.debug("\n########################### removeSessionObject ############################\nGLOBAL_SESSION_MAP:"+GLOBAL_SESSION_MAP);
		logger.debug("GLOBAL_USER_MAP:"+GLOBAL_USER_MAP.keySet());
		try {
			// JWT리스트중 세션이 만료된 건(사용중이 USERID) 가 하나라도 잇으면 처리 하지 않음.
			// 다 사용불가 상태인 USERID 들을 DISTINCT 하여 GLOBAL_SESSION_MAP 와 GLOBAL_USER_MAP에서 삭제처리.
			List<String> notUseJwtList = GLOBAL_SESSION_MAP.keySet().stream().peek(System.out::println).filter(this::isNotUsable).collect(Collectors.toList());
			//사용불가 jwtList 삭제		
			for(String notJwt : notUseJwtList) {
				logger.debug("사용불가 삭제시작"+notJwt);
				GLOBAL_SESSION_MAP.remove(notJwt);
				logger.debug("사용불가 삭제종료");
			}
			
			List liveList = GLOBAL_SESSION_MAP.values().stream().map(st->st.getData().get("user_id")).distinct().collect(Collectors.toList());
			List sessionList =  GLOBAL_USER_MAP.keySet().stream().collect(Collectors.toList());
			sessionList.removeAll(liveList);
			logger.debug("삭제사용자:"+sessionList);
			sessionList.forEach(key->GLOBAL_USER_MAP.remove(key));
		}catch(Throwable e) {
			e.printStackTrace();
		}
		logger.debug("END GLOBAL_SESSION_MAP:"+GLOBAL_SESSION_MAP);
		logger.debug("END GLOBAL_USER_MAP:"+GLOBAL_USER_MAP.keySet()+"\n####################### removeSessionObject ################################");
		
	}
	
	//로그아웃처리.
	public void logout(String jwt) {
		KiesSessionDto sessionDto =  GLOBAL_SESSION_MAP.get(jwt);
		logger.debug(sessionDto.getData()+"로그아웃 처리");
		GLOBAL_SESSION_MAP.remove(jwt);
	}
	
	public void logout() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader(JwtInterceptor.HEADER_AUTH);//"Authorization"
		GLOBAL_SESSION_MAP.remove(jwt);
	}
	
	public Claims getJwtToClaims(String jwt) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
					 .setSigningKey(SALT.getBytes("UTF-8"))
					 .parseClaimsJws(jwt);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
		return claims!=null?claims.getBody():null;
	}
	
	public boolean isNotUsable(String jwt) {
		return !isUsable( jwt);
	}
	
	public boolean isUsable(String jwt) {
		try{
			logger.debug("\nGLOBAL_SESSION_MAP.containsKey(jwt):"+GLOBAL_SESSION_MAP.containsKey(jwt));
			
			if(! GLOBAL_SESSION_MAP.containsKey(jwt) || getCliams(jwt).getExpiration().before(new Date())) {
				return false;
			}
			/*JwtParser parser = Jwts.parser().setSigningKey(this.generateKey());
			Jws<Claims> claimJws = parser.parseClaimsJws(jwt);
			Claims body = claimJws.getBody();*/
			
			return true;
		}catch (Throwable e) {
			e.printStackTrace();
			return false;
			
			/* 1) ExpiredJwtException : JWT를 생성할 때 지정한 유효기간 초과할 때.
				2) UnsupportedJwtException : 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때
				3) MalformedJwtException : JWT가 올바르게 구성되지 않았을 때
				4) SignatureException :  JWT의 기존 서명을 확인하지 못했을 때
				5) IllegalArgumentException*/
			/*
			 * if(e.getCause() !=null) { if(e.getCause() instanceof ExpiredJwtException) {
			 * throw new
			 * BusinessException("A001","계정 권한이 유효하지 않습니다.\\n다시 로그인을 해주세요",e.toString());
			 * }else if(e.getCause() instanceof UnsupportedJwtException) { throw new
			 * BusinessException("A001","계정 권한이 유효하지 않습니다.\\n다시 로그인을 해주세요",e.toString());
			 * }else if(e.getCause() instanceof MalformedJwtException) { throw new
			 * BusinessException("A001","계정 권한이 유효하지 않습니다.\\n다시 로그인을 해주세요",e.toString());
			 * }else if(e.getCause() instanceof SignatureException) { throw new
			 * BusinessException("A001","계정 권한이 유효하지 않습니다.\\n다시 로그인을 해주세요",e.toString());
			 * }else { throw new
			 * BusinessException("A001","계정 권한이 유효하지 않습니다.\\n다시 로그인을 해주세요",e.toString()); }
			 * } throw new
			 * BusinessException("A001","계정 권한이 유효하지 않습니다.\\n다시 로그인을 해주세요",e.toString());
			 */
		}
	}
	
	public Map<String, Object> getCliam(String key) {
		Claims claims = getCliams();
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (HashMap<String, Object>)claims.get(key);
		return value;
	}
	
	
	public Claims getCliams() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader(JwtInterceptor.HEADER_AUTH);//"Authorization"
		return getCliams(jwt);
	}
	
	
	public Claims getCliams(String jwt) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
					 .setSigningKey(SALT.getBytes("UTF-8"))
					 .parseClaimsJws(jwt);
			
			logger.debug("before:"+claims.getBody().getExpiration().before(new Date()));
			logger.debug("after:"+claims.getBody().getExpiration().after(new Date()));
			logger.debug("===>"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(claims.getBody().getExpiration()) +" "+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
		return claims!=null?claims.getBody():null;
	}
	
    //유효기간 확인
    private Boolean isTokenExpired() {
        return extractExpiration().before(new Date());
    }
    
    public Date extractExpiration() {
        return extractClaim(Claims::getExpiration);
    }
    
    public <T> T extractClaim(Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getCliams());
    }
    
    
    //글로벌세션정보를 로컬세션으로 세팅한다
    public void globalToLocalSession(String token) {
    	
    	
    	StringBuilder log = new StringBuilder()
    	.append("################### token ################################\n")
    	.append(GLOBAL_SESSION_MAP.get(token)).append("\n")
    	.append("#################################################################");
    	logger.debug(log.toString());
    	
    	SessionUtil.setUserSession(GLOBAL_SESSION_MAP.get(token));
    	
    	log.setLength(0);
    	log.append("################### USERSESSION ################################\n")
    	.append(SessionUtil.getUserSession()).append("\n")
    	.append("#################################################################");
    	logger.debug(log.toString());
    }
    
}
