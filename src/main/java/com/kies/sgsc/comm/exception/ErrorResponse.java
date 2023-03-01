package com.kies.sgsc.comm.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.validation.BindingResult;

import com.kies.sgsc.comm.exception.CodeContrants.ErrorCode;
import com.kies.sgsc.comm.util.Utils;

public class ErrorResponse {
	
    private String resultMessage;
    private int status;
    private List<String> detail;
    private String resultCode;
    
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String message) {
		this.resultMessage = message;
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
	public void setDetail(List<String> detail) {
		this.detail = detail;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String code) {
		this.resultCode = code;
	}
	
	public static ErrorResponse of(ErrorCode err ) {
    	ErrorResponse res =  new ErrorResponse();
    	res.setResultCode(err.getResultCode());
    	res.setStatus(err.getStatus());
    	res.setResultMessage(err.getResultMessage());
    	return res;
    }
	
	
	public static ErrorResponse of(ErrorCode err ,Exception exception ) {
    	ErrorResponse res =  new ErrorResponse();
    	res.setResultCode(err.getResultCode());
    	res.setStatus(err.getStatus());
    	res.setResultMessage(exception.toString());
    	res.setDetail(getExceptionStringList(exception));
    	return res;
    }
    
    public static ErrorResponse of(ErrorCode err ,List<String> msg) {
    	ErrorResponse res = of(err);
    	res.setResultCode(Utils.NVL(err.getResultCode(),CodeContrants.ERR_SERVER));
    	res.setResultMessage(err.getResultMessage());
    	
    	res.setDetail(msg);
    	return res;
    }
    
    public static ErrorResponse of(ErrorCode err ,BindingResult msg ,Exception exception) {
    	ErrorResponse res = of(err);
    	res.setResultCode(Utils.NVL(err.getResultCode(),CodeContrants.ERR_SERVER));
    	res.setResultMessage(exception.toString());
    	
    	res.setDetail(getExceptionStringList(exception));
    	return res;
    }
    
    public static ErrorResponse of(ErrorCode err ,BindingResult msg) {
    	ErrorResponse res = of(err);
    	res.setResultCode(Utils.NVL(err.getResultCode(),CodeContrants.ERR_SERVER));
    	res.setResultMessage(err.getResultMessage());
    	
    	List<String> tmpList = new ArrayList();
    	tmpList.add(msg.toString());
    	res.setDetail(tmpList);
    	return res;
    }
    
    public static ErrorResponse of(BusinessException err ) {
    	ErrorResponse res =  new ErrorResponse();
    	res.setResultCode(Utils.NVL(err.getResultCode(),CodeContrants.ERR_SERVER));
    	res.setStatus(err.getStatus());
    	res.setResultMessage(err.getMessage());
    	res.setDetail(err.getDetail());
    	return res;
    }
    
    
    public static ErrorResponse of(MethodArgumentTypeMismatchException err ) {
    	ErrorResponse res =  of(ErrorCode.TYPE_MISS_ERROR);
    	res.setResultCode(CodeContrants.ERR_PROCESS);
    	res.setResultMessage(err.getMessage());
        res.setDetail(getExceptionStringList(err));
    	return res;
    }
    
    
    public static List<String> getExceptionStringList(Throwable err) {
    	List<String> tmpList = new ArrayList();
        if(err!=null) {
        	StringBuilder bdl = new StringBuilder();
        	StackTraceElement[] errStack = err.getStackTrace();
        	for(int idx=0;idx<errStack.length;idx++) {
        		StackTraceElement trace = errStack[idx];
        		bdl.append("["+trace.getLineNumber()+"] "+trace.getClassName()+" "+ trace.getMethodName()).append("\n");
        		if(idx>10) break;
        	}
        	tmpList.add(bdl.toString());
        }
        return tmpList;
    }
    
    
    public static String getExceptionString(Throwable err) {
    	StringBuilder bdl = new StringBuilder();
        if(err!=null) {
        	StackTraceElement[] errStack = err.getStackTrace();
        	for(int idx=0;idx<errStack.length;idx++) {
        		StackTraceElement trace = errStack[idx];
        		bdl.append("("+trace.getLineNumber()+") "+trace.getClassName()+" "+ trace.getMethodName()).append(",");
        		if(idx>10) break;
        	}
        }
        return bdl.toString();
    }
}
