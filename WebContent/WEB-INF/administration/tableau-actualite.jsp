<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table>
	<caption>Récapitulatif des actualités</caption>
	<thead>
		<tr>
			<th scope="col">Date de création</th>
			<th scope="col">Titre actualité</th>
			<th scope="col">Sous-titre</th>
			<th scope="col">Position</th>
			<th scope="col">Modification</th>
			<th scope="col">Suppression</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="actu" items="${actus}">
			<tr>
				<th scope="row"><c:out value="${actu.date}" /></th>
				<td><b><c:out value="${actu.titre}" /></b></td>
				<td><c:out value="${actu.titreAccueil}" /></td>
				<td>
					<c:if test="${actu.placement != 0}">
						<b><c:out value="${ actu.placement }"/></b>
					</c:if>
				</td>
				<td>
					<button data-toggle="modal" data-target="#actu-${actu.id}"
						style="box-shadow: none !important;">
						<i class="fa fa-pencil-square-o" aria-hidden="true"></i>
					</button>
				</td>
				<td>
					<button id="del-${actu.id}" onclick="removeActu(this);" style="box-shadow: none !important;">
						<i class="fa fa-close"></i>
					</button>
				</td>
			</tr>
			<!-- Modal -->
			<div class="modal fade" id="actu-${actu.id}" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalLabel"
				aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Modification actualité</h5>
							<button type="button" id="close-${ actu.id }" 
								class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<form>
								<input type="text" id="titre-${actu.id}"
									name="titre-${actu.id}" required minlength="0" maxlength="64" size="50" value="${ actu.titre }">
								<label for="titre-${actu.id}">Titre</label> 
								<input type="text" id="sous-titre-${actu.id}"
									name="sous-titre-${actu.id}" required minlength="0" maxlength="64" size="50" value="${ actu.titreAccueil }">
								<label for="sous-titre-${actu.id}">Sous-titre accueil</label>
								<textarea id="text-actus-${actu.id}" name="text-actus-${actu.id}" rows="10" maxlength="600" class="form-control md-textarea"><c:out value="${actu.text}"/></textarea>
			                    <label for="text-actus-${actu.id}">Text ( 600 caractères maximum )</label>
			                	<div style="display:flex; padding:20px;" >
			                		<c:choose>
				                		<c:when test="${ actu.placement != null }">
					                		<input onclick="changeVisibility(this);" id="visibility-${actu.id}" style="-webkit-appearance: checkbox;opacity:1;" type="checkbox" checked/>
					                		<label for="visibility-${actu.id}">Visible sur l'accueil</label>
					                	</c:when>
					                	<c:otherwise>
					                		<input onclick="changeVisibility(this);" id="visibility-${actu.id}" style="-webkit-appearance: checkbox;opacity:1;" type="checkbox"/>
					                		<label for="visibility-${actu.id}">Visible sur l'accueil</label>
					                	</c:otherwise>            
				                	</c:choose>
			                	</div>
		                		<div>
			                		<c:choose>
				                		<c:when test="${ actu.placement != null }">
				                			<select name="" id="place-${actu.id}">
					                			<c:set var="nb" value="0"/>
												<option value="0"><c:out value=""/></option>
					               				<c:forEach var="actu_accueil" items="${actus_accueil}">
							                		<c:set var="select" value=""/>
						                			<c:set var="nb" value="${nb + 1}"/>
					                				<c:if test="${ actu.placement == nb }">
					                					<c:set var="select" value="selected"/>
					                				</c:if>
										           <option value="${ nb }" ${select}><c:out value="${nb}"/></option>
									           </c:forEach>
									       </select>
								      	</c:when>
			                			<c:otherwise>
							           		<select name="" id="place-${actu.id}">
					                			<c:set var="nb" value="0"/>
												<option value="0"><c:out value=""/></option>
					               				<c:forEach var="actu_accueil" items="${actus_accueil}">
							                		<c:set var="select" value=""/>
						                			<c:set var="nb" value="${nb + 1}"/>
					                				<c:if test="${ actu.placement == nb }">
					                					<c:set var="select" value="selected"/>
					                				</c:if>
										           <option value="${ nb }" ${select}><c:out value="${nb}"/></option>
									           </c:forEach>
									       </select>
									   	</c:otherwise>            
				                	</c:choose>
		                		</div>
			                </form>
						</div>
						<div class="modal-footer" style="display:flex;">
							<div id="status-${actu.id}"></div>
							<button type="button" id="${actu.id}" onclick="validateModification(this);" class="send-connexion">Sauvegarder</button>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<th scope="row" colspan="2">Total actualités</th>
			<td colspan="2"><c:out value="${actus.size()}" /></td>
		</tr>
	</tfoot>
</table>
<script>
	function changeVisibility(obj){
		if(obj.checked){
			var id = $(obj).attr('id').substring(11);
			document.getElementById("place-"+id).disabled = false;
		}else{
			var id =  $(obj).attr('id').substring(11);
			document.getElementById("place-"+id).disabled = true;
		}
	}
</script>