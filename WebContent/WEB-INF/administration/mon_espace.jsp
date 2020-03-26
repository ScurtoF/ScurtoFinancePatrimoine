<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Scurto Finance &amp; Patrimoine</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width" />
<meta name="description" content="Scurto frédéric finance et patrimoine" />
<meta name="keywords"
	content="scurto, retraite, madelin, assurance, assurance-vie, prévoyance, patrimoine, perp" />
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link
	href="https://fonts.googleapis.com/css?family=Lobster&amp;display=swap"
	rel="stylesheet">
</head>
<body class="is-preload">
	<c:import url="/jsp/nav/navigation.jsp" />
	<div
		style="height: 100px; background-color: #102e4d; margin: 0px 0px 20px 0px;">
		<div
			style="color: rgba(255, 255, 255, 0.5); text-align: center; padding-top: 35px;">
			<h4 style="color:#ffffffa3">
				<c:out value="${ titre }" />
			</h4>
		</div>
	</div>
	<div>
		<div style="display:flex;justify-content: space-between;padding:20px;">
			<div style="display:flex;">
				<input type="text" id="recherche"
					name="recherche" required minlength="0" maxlength="64" size="50" placeholder="Recherche ..."/>
				<button onclick="reloadActu();">Rechercher</button>
			</div>
			<div>
				<button data-toggle="modal" data-target="#ajout-actu">Ajouter une actualité</button>
			</div>
		</div>
	</div>
	<div id="table" style="margin-top: 20px;">
		<c:import url="tableau-actualite.jsp" />
	</div>
		<!-- Modal -->
	<div class="modal fade" id="ajout-actu" tabindex="-1"
		role="dialog" aria-labelledby="ajouterActualité"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Ajouter une actualité</h5>
					<button type="button" id="close-ajout-actu" 
						class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">	
					<input type="text" id="titre-ajout-actu"
						name="titre-ajout-actu" required minlength="0" maxlength="64" size="50">
					<label for="titre-ajout-actu">Titre</label> 
					<input type="text" id="sous-titre-ajout-actu"
						name="sous-titre-ajout-actu" required minlength="0" maxlength="64" size="50">
					<label for="sous-titre-ajout-actu">Sous-titre accueil</label>
					<textarea id="text-actus-ajout-actu" name="text-actus-ajout-actu" rows="10" maxlength="600" class="form-control md-textarea"></textarea>
                    <label for="text-actus-ajout-actu">Text ( 600 caractères maximum )</label>     
				</div>
				<div class="modal-footer" style="display:flex;">
					<div id="status-ajout-actu"></div>
					<button type="button" id="ajout-actu" onclick="addActu(this);" class="send-connexion">Sauvegarder</button>
				</div>
			</div>
		</div>
	</div>
	<script>
	document.addEventListener("DOMContentLoaded", function(event) { 
			reloadActu();
	});

	function addActu(obj) {
		var type = $(obj).attr('id');
		console.log(type);
	   	var titre =  document.getElementById('titre-'+type).value;
	   	if (titre == "") {
	   		$('#status-'+type).css('color', '#ff0000c2');
	   	    document.getElementById('status-'+type).innerHTML = "Le champ titre ne peut être vide";
	   	    return false;
	   	}
	   	var sousTitre =  document.getElementById('sous-titre-'+type).value;
	   	var text =  document.getElementById('text-actus-'+type).value;
	   	if (text == "") {
	   		$('#status-'+type).css('color', '#ff0000c2');
	   	    document.getElementById('status-'+type).innerHTML = "Le champ text ne doit pas être vide";
	   	    return false;
	   	}	
		$.ajax({
			type: 'POST',
			url: 'modification-actu',
			data: { titre: titre, sousTitre:sousTitre, textActu:text , type:type},
			timeout:20000,
			success: function(data){
				if(data == 'success'){	
					document.getElementById('titre-'+type).value = "";
					document.getElementById('sous-titre-'+type).value = "";
					document.getElementById('text-actus-'+type).value = "";
					$('#close-'+type).click();
					reloadActu();
					return false;
				}else{
					$('#status').css('color', '#ff0000c2');
			    	document.getElementById('status-'+type).innerHTML = 'Erreur lors de l\'enregistrement'; 
					}
				return false;
				},
		    error: function() {
		    	$('#status').css('color', '#ff0000c2');
		    	document.getElementById('status-'+type).innerHTML = 'La requête n\'a pas abouti'; 
		    	return false;
		    }
		}); 
	}

	
	function validateModification(obj) {
		var id = $(obj).attr('id');
	   	var titre =  document.getElementById('titre-'+id).value;
	   	if (titre == "") {
	   		$('#status-'+id).css('color', '#ff0000c2');
	   	    document.getElementById('status-'+id).innerHTML = "Le champ titre ne peut être vide";
	   	    return false;
	   	}
	   	var sousTitre =  document.getElementById('sous-titre-'+id).value;
	   	var text =  document.getElementById('text-actus-'+id).value;
	   	if (text == "") {
	   		$('#status-'+id).css('color', '#ff0000c2');
	   	    document.getElementById('status-'+id).innerHTML = "Le champ text ne doit pas être vide";
	   	    return false;
	   	}

		var visibility = document.getElementById('visibility-'+id);
		var place = "0";
		if( visibility.checked ){
		   	place = document.getElementById('place-'+id).value;
		   	if (place == "") {
				place = "0";
		   	}
		}
	   	console.log(place)
	   /* 	if (place == "") {
	   		$('#status-'+id).css('color', '#ff0000c2');
	   	    document.getElementById('status-'+id).innerHTML = "Le champ text ne doit pas être vide";
	   	    return false;
	   	}		 */
 		$.ajax({
			type: 'POST',
			url: 'modification-actu',
			data: { idActu: id , titre: titre, sousTitre:sousTitre, textActu:text , placement:place, type:'modification'},
			timeout:20000,
			success: function(data){
				if(data == 'success'){
			    	$('#'+id).css('background-color', 'rgba(0, 248, 0, 1)');
			    	$('#'+id).html('Message envoyé');
			    	$('#'+id).html('Message envoyé');
			    	$('#'+id).prop('disabled', true);	
			    	$('#status'+id).css('color', 'rgb(99, 99, 99)');    	
					document.getElementById('status-'+id).innerHTML = "Modification confirmé";
					$('#close-'+id).click();
					reloadActu();
					return false;
				}else{
					$('#status').css('color', '#ff0000c2');
			    	document.getElementById('status-'+id).innerHTML = 'Erreur lors de l\'enregistrement'; 
					}
				return false;
				},
		    error: function() {
		    	$('#status').css('color', '#ff0000c2');
		    	document.getElementById('status-'+id).innerHTML = 'La requête n\'a pas abouti'; 
		    	return false;
		    }
		}); 
	}

	function removeActu(elt){
		var id= $(elt).attr('id').substring(4);
		console.log(id);
		$.ajax({
			type: 'POST',
			url: 'modification-actu',
			data: { idActu: id , type:'delete'},
			timeout:20000,
			success: function(data){
				if(data == 'success'){
					reloadActu();
						
				}return false;
			}
		}); 
	}
	
	function reloadActu(){
		console.log("dans le reload actus")
		var search =  document.getElementById('recherche').value;	
		setTimeout(function(){
	   		$('#table').load("modification-actu?search="+search);
		}, 500);
	}

	</script>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/browser.min.js"></script>
	<script src="assets/js/breakpoints.min.js"></script>
	<script src="assets/js/util.js"></script>
	<script src="assets/js/main.js"></script>
	<script src="assets/js/scurto_app.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>
	<!-- Scripts -->
</body>
</html>