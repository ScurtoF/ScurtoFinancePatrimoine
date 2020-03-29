<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<c:if test="${ selectedActu != null }">  
    <h3 style="margin:0;font-weight:300;font-size: 2.2rem;"><c:out value="${ selectedActu.titre }"/></h3>
    <p style="font-size:0.8rem"><c:out value="Modifié le ${ selectedActu.date }"/></p>
    <p><c:out value="${ selectedActu.text }"/></p>
</c:if>