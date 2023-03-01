package com.kies.sgsc.controller;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kies.sgsc.comm.exception.ErrorResponse;
/**
 * 권한관리
 * @author leeju
 *
 */
@Controller
//@RestController
public class CustomErrorController implements ErrorController{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String ERROR_PATH = "/error";
	 
    public String getErrorPath() {
        return ERROR_PATH;
    }
    
    @RequestMapping(value="/error", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String handleError(HttpServletRequest request,HttpServletResponse response, Model model,Exception exception) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        logger.info("httpStatus : "+httpStatus.toString());
        model.addAttribute("code", status.toString());
        model.addAttribute("msg", httpStatus.getReasonPhrase());
        model.addAttribute("timestamp", new Date());
        //Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        logger.info("model : "+model);
       // response.setStatus(Integer.valueOf(status.toString()));
        return  "{\"resultCode\": \"ERR901\",\"resultMessage\": \"["+status.toString()+"]"+httpStatus.getReasonPhrase()+"\",\"status\":\""+status.toString()+"\",\"detail\":[\""+exception.toString()+ErrorResponse.getExceptionString(exception)+"\"]}"; //\""+exception.toString()+"\n"+ErrorResponse.getExceptionString(exception)+"\"
    }
    
    
    @RequestMapping(value="/error")
    public String handleError1(HttpServletRequest request,HttpServletResponse response, Model model,Exception exception) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        logger.info("httpStatus : "+httpStatus.toString()+"  "+status.toString()+"나와라 ##################");
        model.addAttribute("code", status.toString());
        model.addAttribute("msg", httpStatus.getReasonPhrase());
        model.addAttribute("timestamp", new Date());
        //Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        logger.info("model : "+model);
        if("404".equals(status.toString())) {
        	logger.info("포워드 : "+model);
        	return "forward:/index.html";
        }
       // response.setStatus(Integer.valueOf(status.toString()));
        return  "forward:/index.html";
    }

}