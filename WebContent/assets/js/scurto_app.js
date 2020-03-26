/**
 *  Méthode Ajax permettant l'envoie de requête et l'affichage de réponse asynchrone
 */
function changeContainer(elt){
	$(elt).parent().addClass('active').siblings().removeClass('active');
/* Méthode de rechargement ajax lente */
$('#container').fadeOut('normal', function suivante(){
	$(this).load($(elt).attr('jsp/container/professionnels-container.jsp'));
	$(this).fadeIn('slow');
	});
}

function redirect(elt){
	var idStr = elt.id;
	window.location.href = idStr+".jsp";
}

current_img = 0;
arrImg = ['images/equipe2.jpg','images/equipe4.jpg','images/equipe5.jpg', 'images/equipe6.jpg', 'images/equipe7.jpg']
	
	
function defilImg(){
	if(current_img == arrImg.length)
	current_img = 0;
	document.getElementById('defil-img').src = arrImg[current_img++];
}

grecaptcha.ready(function() {
	grecaptcha.execute('6LeO2scUAAAAANedX4uaOwkFSODqpzEsc1fWxFLR', {action: 'register'}).then(function(token) {
		var elms = document.getElementsByClassName('token');
        for (var i = 0; i < elms.length; i++) {
           elms[i].setAttribute("value", token);
        }
	});
});

////LOCAL
//grecaptcha.ready(function() {
//	grecaptcha.execute('6LcKGckUAAAAAN0ApZ0gAFdEkMVxmGZ-TViLLPkv', {action: 'register'}).then(function(token) {
//        var elms = document.getElementsByClassName('token');
//        for (var i = 0; i < elms.length; i++) {
//           elms[i].setAttribute("value", token);
//        }
//	});
//});

function openModal(){
	$('#connexionModalCenter').modal('show');
}
function closeModal(){
	$('#connexionModalCenter').modal('hide');
}
function connexionForm() {
   	var email =  document.getElementById('mailConnexion').value;
   	console.log(email);
   	if (email == "") {
   		$('#messageConnexion').css('color', '#ff0000c2');
   	    document.getElementById('messageConnexion').innerHTML = "Le champ email ne doit pas être vide";
   	    return false;
   	} else {
   	    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
   	    if(!re.test(email)){
   	        document.getElementById('messageConnexion').innerHTML = "Le format de l'email est invalide";
   	        return false;
   	    }
   	}
   	var passwordText =  document.getElementById('passConnexion').value;
   	if (passwordText == "") {
   		$('#messageConnexion').css('color', '#ff0000c2');
   	    document.getElementById('messageConnexion').innerHTML = "Le champ mot de passe ne doit pas être vide";
   	    return false;
   	}
   	var token = document.getElementById('token-connexion').value;
	
   	
   	/* var token = document.getElementById('captchatoken').value; */
   	$('#messageConnexion').css('color', '#fcb10ce0');
   	document.getElementById('messageConnexion').innerHTML = "Envoi en cours";
   
	$.ajax({
		type: 'POST',
		url: 'authenticate',
		data: { mail: email, password: passwordText, captchatoken:token },
		timeout:20000,
		success: function(data){
			console.log(data);
			if(data === 'valide'){
		    	$('#btn-connexion').css('background-color', 'rgba(0, 248, 0, 1)');
		    	$('#btn-connexion').html('Message envoyé');
		    	$('#btn-connexion').prop('disabled', true);	
		    	$('#messageConnexion').css('color', 'rgb(99, 99, 99)');    	
				closeModal();
				$('#icon-connexion').fadeOut('normal', function suivante(){
					$(this).load('authenticate');
					$(this).fadeIn('slow');
				});
//				$('#box-log').fadeOut('normal', function suivante(){
//					$(this).load('jsp/nav/boxLog.jsp');
//					$(this).fadeIn('slow');
//				});
			}else{
		    	$('#messageConnexion').css('color', '#ff0000c2');
				document.getElementById('messageConnexion').innerHTML = 'Email ou mot de passe incorrect';
			}
			return false;
			},
	    error: function() {
	    	$('#messageConnexion').css('color', '#ff0000c2');
	    	document.getElementById('messageConnexion').innerHTML = 'La requête n\'a pas abouti'; 
	    	return false;
	    }
	});
}


function validateForm() {
   	var name =  document.getElementById('nom').value;
   	if (name == "") {
   		$('#status').css('color', '#ff0000c2');
   	    document.getElementById('status').innerHTML = "Le champ nom ne doit pas être vide";
   	    return false;
   	}
   	var email =  document.getElementById('mail').value;
   	if (email == "") {
   		$('#status').css('color', '#ff0000c2');
   	    document.getElementById('status').innerHTML = "Le champ email ne doit pas être vide";
   	    return false;
   	} else {
   	    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
   	    if(!re.test(email)){
   	        document.getElementById('status').innerHTML = "Le format de l'email est invalide";
   	        return false;
   	    }
   	}
   	var prenomText =  document.getElementById('prenom').value;
   	if (prenomText == "") {
   		$('#status').css('color', '#ff0000c2');
   	    document.getElementById('status').innerHTML = "Le champ prenom ne doit pas être vide";
   	    return false;
   	}
   	var telText =  document.getElementById('tel').value;
   	if (telText == "") {
   		$('#status').css('color', '#ff0000c2');
   	    document.getElementById('status').innerHTML = "Le champ telephone ne doit pas être vide";
   	    return false;
   	}
   	var messageText =  document.getElementById('message').value;
   /* 	if (messageText == "") {
       	$('#status').css('color', '#ff0000c2');
   	    document.getElementById('status').innerHTML = "* Le champ message ne doit pas être vide";
   	    return false;
   	} */
   	var token = document.getElementById('captchatoken').value;
   	$('#status').css('color', '#fcb10ce0');
   	document.getElementById('status').innerHTML = "Envoi en cours";
   
   	
	$.ajax({
		type: 'POST',
		url: 'contact',
		data: { message: messageText, tel:telText , prenom:prenomText, nom:name, captchatoken:token, mail:email },
		timeout:20000,
		success: function(data){
	    	$('#btn-send').css('background-color', 'rgba(0, 248, 0, 1)');
	    	$('#btn-send').html('Message envoyé');
	    	$('#btn-send').html('Message envoyé');
	    	$('#btn-send').prop('disabled', true);	
	    	$('#status').css('color', 'rgb(99, 99, 99)');    	
			document.getElementById('status').innerHTML = "Merci pour votre message, nous essaierons de vous répondre rapidement.";
			return false;
			},
	    error: function() {
	    	$('#status').css('color', '#ff0000c2');
	    	document.getElementById('status').innerHTML = 'La requête n\'a pas abouti'; 
	    	return false;
	    }
	});
}

function openLogSpace(){
	$('#box-log').css('display', 'block');
}
function closeLogSpace(){
	$('#box-log').css('display', 'none');
}

function adminActus(){
	$.ajax({
		url: 'monEspace',
		timeout:20000,
		success: function(data){
			console.log(data);
            // data.redirect contains the string URL to redirect to
            window.location.href = data.redirect;      
		}
	});
}