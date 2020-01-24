<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<c:set var="session" value="${sessionScope.user_scurto}"/>
<c:if test="${empty session}"><img onclick="openModal();" class="logo-connexion" src="icon/account.png"></c:if>
<c:if test="${not empty session}">
	<img onclick="openLogSpace();" class="logo-connexion" src="icon/${session.getIcon()}"/>
	<div class="col-lg-12">
	<c:set var="user" value="${ sessionScope.user_scurto }"/>
	<div id="box-log" class="box-log">
		<div><a class="close" style="position: absolute;right: 10px;" onclick="closeLogSpace()"></a></div>
		<div style="padding: 20px;">
			<h5><c:out value="${ user.getCompleteName() }"/></h5>
			<p><c:out value="${ user.email}"/></p>
		<!-- 	<button class="gerer-compte"><p>Gérer votre compte</p></button> -->
			<hr/>
			<a href="monEspace" class="button-action">
				Gérer les actualités
			</a>
			<button class="button-action">
				Gérer les événement
			</button>
			<button class="button-action">
				Gérer les demandes
			</button>
		</div>
	</div>
</div>
</c:if>