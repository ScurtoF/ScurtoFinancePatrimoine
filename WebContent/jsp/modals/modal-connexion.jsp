<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Modal -->
<div class="modal fade" id="connexionModalCenter" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="Title">Connexion</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				  <span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
			    <div class="row justify-content-md-center">
			        <!--Grid column-->
			        <div class="col-md-10">
			            <form id="contact-form" name="contact-form" method="POST">
			                <!--Grid row-->
			                <div class="row">
			                    <!--Grid column-->
			                    <div class="col-md-11">
			                        <div class="md-form mb-0">
			                            <input type="email" id="mailConnexion" name="mail">
			                            <label for="mail" class="">Email</label>
			                        </div>
			                    </div>
			                </div>
			                <div class="row">
			                    <!--Grid column-->
			                    <div class="col-md-11">
			                        <div class="md-form mb-0">
			                            <input type="password" id="passConnexion" name="pass">
			                            <label for="pass" class="">Password</label>
			                        </div>
			                    </div>
			                </div>
			                <input id="token-connexion" type="hidden" class="token" name="token" value=""/>
			            </form>
			            <div id="messageConnexion"></div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
	            <div class="text-center text-md-left">
	            	<button type="button" class="closed-modal" 
						data-dismiss="modal">Fermer</button>
	                <button id="btn-connexion" class="send-connexion"
						onclick="connexionForm();">Envoi</button>
	            </div>
	        </div>
		</div>
	</div>
</div>