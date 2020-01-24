<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Heading -->
<div id="heading-professionnels" class="contact" >
    <h1>Contactez-nous</h1>
</div>

<!-- Rapid nav -->
<div class="rapid-nav">
    <p><a href="index.jsp">Accueil</a> > Contactez-nous</p>
</div>
<!-- Main -->

<section id="main" class="wrapper">
    <div class="inner">
        <div class="content">
            <header>
                <h2>Besoin d'informations ?</h2>
            </header>
           <!--  <p>Certains secteurs d'activité doivent souscrire une assurance professionnelle obligatoire, mais aucune entreprise n'est à l'abri de risques liés à son activité, il est donc important de souscrire une assurance entreprise.
                    Cette assurance permet de couvrir les risques qui ne peuvent pas être supportés par la trésorerie de l'entreprise et qui pourraient entrainer la faillite de l'entreprise.</p>
          -->
			<section>
			    <div class="row justify-content-md-center">
			        <!--Grid column-->
			        <div class="col-md-10">
			            <form id="contact-form" name="contact-form" method="POST">
			                <!--Grid row-->
			                <div class="row">
			                    <!--Grid column-->
			                    <div class="col-md-6">
			                        <div class="md-form mb-0">
			                            <input type="text" id="nom" name="nom">
			                            <label for="nom" class="">Nom</label>
			                        </div>
			                    </div>
			                    <div class="col-md-6">
			                        <div class="md-form mb-0">
			                            <input type="text" id="prenom" name="prenom">
			                            <label for="prenom" class="">Prénom</label>
			                        </div>
			                    </div>
			                </div>
			                <div class="row">
			                    <!--Grid column-->
			                    <div class="col-md-6">
			                        <div class="md-form mb-0">
			                            <input type="email" id="mail" name="mail">
			                            <label for="mail" class="">Email</label>
			                        </div>
			                    </div>
			                    <div class="col-md-6">
			                        <div class="md-form mb-0">
			                            <input type="tel" id="tel" name="tel" class="form-control">
			                            <label for="tel" class="">Téléphone</label>
			                        </div>
			                    </div>
			                </div>
			                <div class="row">
			                    <div class="col-md-12">
			                        <div class="md-form">
			                            <textarea id="message" name="message" rows="4" class="form-control md-textarea"></textarea>
			                            <label for="message">Votre message</label>
			                        </div>
			                        <input type="hidden" id="captchatoken" class="token" name="captchatoken" value=""/>
			                    </div>
			                </div>			
			            </form>
			
			            <div class="text-center text-md-left">
			                <button id="btn-send" style="border-color: #365e8847; background-color: #2d476238;"
								onclick="validateForm();">Envoi</button>
			            </div>
			            <div id="status"></div>
			        </div>
		        </div>
			</section>
		</div>
    </div>
</section>

