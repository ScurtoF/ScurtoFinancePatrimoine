<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    

<c:set var="itemAccueil" value="" />
<c:set var="itemPros" value=""/>
<c:set var="itemEntreprises" value="" />
<c:set var="itemLiens" value="" />
<c:choose>
	<c:when test="${page == 'index'}">
		<c:set var="itemAccueil" value="activate"/>
	</c:when>
	<c:when test="${page == 'professionnels'}">
		<c:set var="itemPros" value="activate"/>		
	</c:when>
	<c:when test="${page == 'entreprises'}">
		<c:set var="itemEntreprises" value="activate" />
	</c:when>
	<c:when test="${page == 'liens-utiles'}">
		<c:set var="itemLiens" value="activate" />
	</c:when>
</c:choose>

<!-- Header -->
<header id="header">
	<a class="logo" href="index.jsp">Scurto Finance <span class="and">&amp;</span> Patrimoine</a>
	<nav style="display:flex;">
		<a href="#menu">Menu</a>
		<span id="icon-connexion">
			<c:import url="/jsp/nav/nav-connexion.jsp"/>
		</span>
	</nav>
</header>
<!-- Nav -->
<nav id="menu">
	<ul class="links">
		<li class="${ itemAccueil }" ><a href="index.jsp">Accueil</a></li>
		<li>
			<a class="${ itemPros }" href="professionnels.jsp">Professionnels</a>
			<div class="submenu">
				<ul class="links">
					<li><a href="construction.jsp">Protection de l'activité</a></li>
					<li><a href="construction.jsp">Protection du dirigeant</a></li>
					<li><a href="construction.jsp">Protection par domaine d'activité</a></li>
				</ul>
			</div>
		</li>
		<li>
			<a class="${ itemEntreprises }" href="entreprises.jsp">Entreprises</a>
			<div class="submenu">
				<ul class="links">
					<li><a href="construction.jsp">Protection de l'activité</a></li>
					<li><a href="construction.jsp">Protection des salariés</a></li>
					<li><a href="construction.jsp">Protection du dirigeant</a></li>
					<li><a href="construction.jsp">Protection par domaine d'activité</a></li>
				</ul>
			</div>
		</li>
		<li><a href="construction.jsp">Particuliers</a></li>
		<li><a href="construction.jsp">Actualités</a></li>
		<li class="${ itemLiens }"><a  href="liens-utiles.jsp">Liens utiles</a></li>
	</ul>
</nav>
<!-- Modal -->
<c:import url="/jsp/modals/modal-connexion.jsp"/>
<!-- 
<script src="https://www.google.com/recaptcha/api.js?render=6LeO2scUAAAAANedX4uaOwkFSODqpzEsc1fWxFLR"></script> -->
<script src="https://www.google.com/recaptcha/api.js?render=6LcKGckUAAAAAN0ApZ0gAFdEkMVxmGZ-TViLLPkv"></script>