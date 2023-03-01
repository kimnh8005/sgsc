package com.kies.sgsc.comm.exception;

import java.util.ArrayList;
import java.util.List;

import com.kies.sgsc.comm.exception.CodeContrants.ErrorCode;

public class BusinessException extends RuntimeException{
	
	private String code="";
	private int status = ErrorCode.USER_EXCEPTION.getStatus();
	private List<String> detail = new ArrayList();
	
	
	public BusinessException(String code) {
		this.code = code;
	}
	
	public BusinessException(String code,String message) {
		super(message);
		this.code = code;
	}
	public BusinessException(String code,String message, String detail){
		super(message);
		this.code = code;
		this.detail.add(detail);
	}
	
	public BusinessException(String code,String message, List<String> detail){
		super(message);
		this.code = code;
		this.detail = detail;
	}

	public String getResultCode() {
		return code;
	}

	public void setResultCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getDetail() {
		return detail;
	}

	public void addDetail(String detailStr) {
		detail.add(detailStr);
	}

	
}
