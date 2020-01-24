<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<c:set var="actus" value="${actus}"/>
<!-- Banner -->
<div class="inner banner-carousel">
	<div id="carouselBannerIndicators" class="carousel slide" data-ride="carousel">
		<ol class="carousel-indicators">
			<c:set var="i" value="0"/>
			<c:forEach var="map" items="${actus}">
				<li data-target="#carouselBannerIndicators" data-slide-to="${ i }" <c:if test="${ i == '0' }">class="active"</c:if>></li>
				<c:set var="i" value="${ i+1 }"/>
			</c:forEach>
		</ol>
		<div class="carousel-inner">
			<c:set var="i" value="1"/>
			<c:forEach var="actu" items="${ actus }">
				<c:set var="active" value=""/>
				<c:if test="${ i=='1'}">
					<c:set var="active" value="active"/>
				</c:if>
				<div class="carousel-item ${ active }">
					<section class="banner" id="banner${ i }">
						<div class="inner">
							<h1><c:out value="${ actu.titre }"/></h1>
							<p><c:out value="${ actu.titreAccueil }"/></p>
						</div>
					</section>
				</div>
				<c:set var="i" value="${ i+1 }"/>
			</c:forEach>
		</div>
	</div>
</div>

<!-- Highlights -->
<section class="wrapper">
	<div class="inner">
		<header class="special">
			<h2>Nos services</h2>
			<hr class="trans--grow"/>
			<p class=max-size>Chefs d'entreprises, Artisans , Commerçants, Professions Libérales ...
				Les meilleures solutions adaptées à votre activité et vos besoins avec des cotisations spécialement
				conçues pour vous.</p>
		</header>
		<div class="highlights">
			<section>
				<div id="professionnels" onclick="redirect(this);" class="content">
					<header>
						<img class="selection-services" alt="Professionnels assurances" src="images/professionnel.jpg"/>
					</header>
					<div class="body-cards" >
						<h3>Professionnels</h3>
						<hr/>
						<p style="text-align: center;">Nous vous proposons des solutions adaptees aux spécificités de votre activité en tenant compte votre situation personnelle. La bonne marche de votre entreprise dépend essentiellement de votre protection sociale.</p>
					</div>
				</div>
			</section>
			<section>
				<div id="entreprises" onclick="redirect(this);" class="content">
					<header>
						<img class="selection-services" alt="Chef d'entreprise" src="images/entreprise.jpg"/>
					</header>
					<div class="body-cards">
						<h3>Entreprises</h3>
						<hr/>
						<p style="text-align: center;">Un certain nombre d'obligations vous incombe en tant que chef d'entreprise.
						<br/>Nous vous accompagnons sur la partie sociale et l'optimisation fiscale de votre entreprise.
						</p>
					</div>
				</div>
			</section>
			<section>
				<div id="construction" onclick="redirect(this);" class="content">
					<header>
						<img class="selection-services" alt="contrats d'assurance" src="images/retraite.jpeg"/>
					</header>
					<div class="body-cards">
						<h3>Particuliers</h3>
						<hr/>
						<p style="text-align: center;">Chaque situation nécessite des solutions particulières pour sécuriser votre patrimoine personnel et vos proches.</p>
					</div>
				</div>
			</section>
		</div>
	</div>
</section>

<!-- CTA -->
<section class="cta wrapper">
	<div>
		<h2>" Plus qu’une sensation d’être protégé "</h2>
	</div>
</section>

<!-- Vos Projets -->
<section class="wrapper">
	<div style="width:80%;margin:auto;" class="inner">
		<header class="special">
			<h2>Au service de vos projets</h2>
			<hr class="trans--grow"/>
			
		</header>
		<div class="vos-projet">
			<section class="img-hover-zoom expert">
				<div class="container-projet text-projet">
					<header><h3>Une équipe pour vous accompagner</h3></header>
					<hr/>
					<p>Spécialistes dans les domaines de la prévoyance, de la santé, de la dépendance, du patrimonial, de l’épargne-retraite et de l’entreprise, nous mettons nos connaissances  au service de la réalisation de vos projets.
						Nous nous appuyons sur un réseau d’expertise comptant des acteurs tels que Drouot Estate, SOGAREP, Juridica, AXA Gestion Privée ou encore la prestigieuse association d’assurés qu’est l’AGIPI.
						</p>
				</div>
				<div class="container-projet image-projet max-size">
					<img alt="Calatayud Sander" src="images/equipe3.jpg"/>
				</div>
			</section>
			<section class="img-hover-zoom expert" style="margin-top:30px;">
				<div class="container-projet text-projet">
					<header><h3>Des experts pour vous conseiller</h3></header>
					<hr/>
					<p>Demandez votre Bilan qui vous permettra de connaître précisement votre situation actuelle"Sociale et Patrimoniale".
							Comme eux, vous pourrez alors prendre les bonnes décisions pour sécuriser votre Entreprise et protéger votre Famille.
							Votre Protection, votre Avenir, votre Réussite : cela nous concerne, c'est  notre métier.
							100 % de nos clients sont satisfaits du service que nous leur apportons et de notre présence à leur côté, au quotidien et dans chaque difficulté..</p>
				</div>
				<div class="container-projet image-projet max-size">
						<img alt="Assureur Scurto Frédéric" src="images/equipe1.jpg"/>
				</div>
			</section>
			<section class="img-hover-zoom expert" style="margin-top:30px;">
				<div class="container-projet text-projet">
					<header><h3>Une relation humaine</h3></header>
					<hr/>
					<p>Nous vous devons honnêteté, intégrité, loyauté et confidentialité. Vous nous confiez des pans entiers de votre vie, de celle de vos proches ainsi que le fruit de votre travail.
						Nous sommes des professionnels indépendants soucieux de vous délivrer une analyse globale répondant précisément à vos besoins.</p>
				</div>
				<div class="container-projet image-projet max-size" >
					<img onmouseout="defilImg()" id="defil-img" alt="Partenaire Scurto Finance et Patrimoine" src="images/equipe2.jpg"/>
				</div>
			</section>
		</div>
	</div>
</section>

<!-- CTA -->
<section class="cta wrapper">
	<div class="inner">
		<h2>" Seul dans le passé ensemble dans le futur "</h2>
	</div>
</section>

<!-- Testimonials -->
<section class="wrapper">
	<div class="inner">
		<header class="special">
			<h2>Vous nous faites confiance</h2>
			<hr/>
			<p class="max-size">Nous nous impliquons au quotidien et dans la durée pour vous et votre avenir, afin de vous apporter pleinement satisfaction.</p>
		</header>
		<div class="testimonials">
			<section>
				<div class="content">
					<blockquote>
						<p>Je recommande Frédéric pour son sérieux professionnel. Frédéric est une personne très réactive et de confiance.</p>
					</blockquote>
					<div class="author">
						<div class="image">
							<a href="https://www.lhms.fr/"><img src="images/partenaires/LHMS.png" alt="societe LHMS" /></a>
						</div>
						<p class="credit">- <strong>Philippe LEONETTI</strong><span> - LHMS</span></p>
					</div>
				</div>
			</section>
			<section>
				<div class="content">
					<blockquote>
						<p>Cabinet  humain et à l'écoute. Des personnes trés présentes pour leurs clients.
								Surprise par la qualité du service qu'ils m'ont rendu. 
								Je recommande !</p>
					</blockquote>
					<div class="author">
						<div class="image">
							<img src="images/partenaires/unknown_icon.png" alt="icon client" />
						</div>
						<div>
						<p class="credit">- <strong>Aude EUSEBI</strong></p>
						</div>
					</div>
				</div>
			</section>	
			<section>
				<div class="content">
					<blockquote>
						<p>J'ai la chance de connaitre M SCURTO depuis des années et je dois dire que je n'ai jamais connu quelqu'un de plus humain et plus professionnel que lui. Dans le monde des assurances, nous avons souvent tendance à nous méfier et bien sachez qu'avec M SCURTO vous pouvez y aller les yeux fermés. Vous serez toujours bien conseillé et les produits proposés sont réellement en phase avec vos besoins.</p>
					</blockquote>
					<div class="author">
						<div class="image">
							<img src="images/partenaires/unknown_icon.png" alt="icon client" />
						</div>
						<p class="credit">- <strong>Christophe DONATI</strong></p>
					</div>
				</div>
			</section>
		</div>
		<section class="partenaire">
			<header><h4>Nos partenaires</h4></header>
			<div class="container-logo-partenaire">
				<div class="box-partenaire"><img class="d-block w-100" src="images/partenaires/agipi.png" alt="agipi"></div>
				<div class="box-partenaire"><img class="d-block w-100" src="images/partenaires/aprs.png" alt="aprs"></div>	
				<div class="box-partenaire"><img class="d-block w-100" src="images/partenaires/axa.jpg" alt="axa"></div>
				<div class="box-partenaire"><img class="d-block w-100" src="images/partenaires/LHMS.png" alt="lhms"></div>
			</div>
		</section>
	</div>
</section>