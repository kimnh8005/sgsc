<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
//String code = request.getAttribute("code")==null?"":(String)request.getAttribute("code");
//String msg = request.getAttribute("msg")==null?"":(String)request.getAttribute("msg");
//String date = request.getAttribute("date")==null?"":request.getAttribute("date").toString();
//System.out.println("codecodecodecode:"+request.getAttribute("code"));
%>
<!DOCTYPE html> 
<html>
<div layout:fragment="content">
    <h1>Error Page</h1>
    error code : ${code}
    <br>error msg : ${msg} 
    <br>timestamp : ${timestamp} 
  </div>	 
</html>
