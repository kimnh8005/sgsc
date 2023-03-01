package com.kies.sgsc.comm.exception;

public class CodeContrants {
	
	public static final String ERR_DUPLICATE = "ERR100"; //중복오류
	public static final String ERR_INSERT = "ERR101"; //등록오류
	public static final String ERR_SELECT = "ERR102"; //조회오류
	public static final String ERR_UPDATE = "ERR103"; //수정오류
	public static final String ERR_PARAMETER = "ERR104"; //입력 파라미터 오류
	public static final String ERR_DELETE = "ERR105"; //삭제오류
	
	
	public static final String ERR_NOT_PROC = "ERR106"; //처리된건이 존재하지 않음.
	public static final String ERR_PROCESS = "ERR107"; //처리중 오류
	public static final String ERR_NOTEXIST = "ERR108"; //해당건이 존재하지 않음.
	public static final String ERR_FILE = "ERR109"; //파일처리중오류
	
	public static final String ERR_HANDLE_ACCESS_DENIED = "ERR110"; //접근 권한 없음 
	public static final String ERR_TYPE_MISS = "ERR111"; //TYPE MISS ERROR
	public static final String ERR_SYSTEM = "ERR112"; //시스템 오류
	public static final String ERR_SENDMESSAGE = "ERR113"; //메시지처리오류
		
	public static final String ERR_LOGIN_FAIL = "ERR500"; //로그인오류
	public static final String ERR_NOT_LOGIN = "ERR501"; //로그인하지 않은 사용자
	public static final String ERR_NOT_USER = "ERR502";	//존재하지 않는 사용자
	public static final String ERR_PASSWORD = "ERR503";	//패스워드오류 
	public static final String ERR_LOGIN_USR = "ERR504"; //이미로그인한 사용자
	public static final String ERR_NOAUTH_USR = "ERR505"; //권한이 없는 계정

	public static final String ERR_SERVER = "ERR901";	//서버에러
	public static final String ERR_METHOD_NOT_ALLOWED = "ERR902"; //허용불가능 
	public static final String NOR_CODE = "NRL001";	//정상처리 
	public static final String NOR_MESSAGE = "정상처리되었습니다";
	
	public static final String SUCCESS = "200";
	
	public static final String RESULT_CODE_LABEL = "resultCode";
	public static final String RESULT_MSG_LABEL  = "resultMessage";
	
	public enum ErrorCode {
		// Common
	    INVALID_INPUT_VALUE(400, ERR_PARAMETER, " Invalid Input Value"),
	    METHOD_NOT_ALLOWED(405, ERR_METHOD_NOT_ALLOWED, " Invalid Input Value"),
	   
	    HANDLE_ACCESS_DENIED(403, ERR_HANDLE_ACCESS_DENIED, "Access is Denied"),

	    TYPE_MISS_ERROR(400,ERR_TYPE_MISS,"TYPE_MISS"),
	    
	    // Member
	    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
	    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

	    INTERNAL_SERVER_ERROR(500,ERR_SERVER,"SERVER ERROR"),
	    
	    
	    USER_EXCEPTION(405, ERR_PROCESS,"BUSSINE ERROR")
	    ;
	    private final String code;
	    private final String message;
	    private int status;

	    ErrorCode(final int status, final String code, final String message) {
	        this.status = status;
	        this.message = message;
	        this.code = code;
	    }

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getResultCode() {
			return code;
		}

		public String getResultMessage() {
			return message;
		}
	    
	}
}


/*
200	정상	정상

400	Bad request!	파라메터 값이 유효하지 않음
404	HTTP 404 Not Found!	잘못된 URL 호출
500	Internal Server Error!	서버 내부 오류
		
1000	SQLException!	
1001	Data not found!	데이터가 없음
1002	Duplicate data!	입력값이 중복
		
1100	User not found!	사용자 정보가 없음
1101	Incorrect information	로그인 정보 일치하지 않음
1102	Unavailable accessToken!	access token 이 유효하지 않음
*/