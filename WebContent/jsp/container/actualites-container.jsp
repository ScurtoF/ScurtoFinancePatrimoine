<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
    
<!-- Heading -->
<div id="heading-professionnels" class="actualites" >
    <h1>>Les Actualités</h1>
</div>

<!-- Rapid nav -->
<div class="rapid-nav">
    <p><a href="index.jsp">Accueil</a> > Actualités</p>
</div>
<!-- Main -->

<section id="main" class="wrapper">
    <div class="inner">
        <div class="content"> 
            <form>
            	<label for="choiceActus">Choix actualités : </label>
            	<select id="actu" name="choiceActus" onchange="changeActus(this);" id="choice">
            		<c:forEach var="actu" items="${ actus }">
            			<c:choose>
	            			<c:when test="${ actu.id == selectedActu.id }">
	            				<option value="${ actu.id }" selected="selected"><c:out value="${ actu.titre }"/></option>
	            			</c:when>
	            			<c:otherwise>
	            				<option value="${ actu.id }"><c:out value="${ actu.titre }"/></option>
	            			</c:otherwise>
	            		</c:choose>
            		</c:forEach>
            	</select>
            </form>
            <hr />
		    <article id="article-actu">
	            <c:import url="/jsp/container/actualites-article.jsp"/>
            </article>
            <hr />
        </div>
    </div>
</section>
<script>
	function changeActus(elt){
		var choiceActu = elt.options[elt.selectedIndex].value;
		$("#article-actu").load("actualites", { choix : choiceActu });
	}

	function reloadActu(){
		console.log("dans le reload actus")
		var search =  document.getElementById('actu').value;
		var choiceActu = elt.options[elt.selectedIndex].value;	
		setTimeout(function(){
	   		$('#article').load("actualite?article="+search);
		}, 500);
	}
</script>
