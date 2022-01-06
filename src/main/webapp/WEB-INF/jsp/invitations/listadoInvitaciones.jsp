<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="invitations">
    <h2>Invitaciones</h2>

    <table id="invitationsTable" class="table table-striped">
		<tbody>
			<c:forEach items="${invitations}" var="invitation">
				<tr>
					<td>
						${invitation.match.players[0].user.username} te ha enviado una invitacion para unirte a la partida ${invitation.match}
					</td>
					<td>
						<form:form modelAttribute="invitation" class="form-horizontal" id="add-invitation-form" action="/matches/${invitation.match.id}/${invitation.id}/aceptar">
										<div class="form-group">
											<div class="col-sm-offset-2">
												<button class="btn btn-success" type="submit">Aceptar</button>
											</div>
										</div>
						</form:form>
					</td>
					<td>
						<form:form modelAttribute="invitacion" class="form-horizontal" id="add-invitation-form" action="/invitations/${invitation.id}/rechazar">
										<div class="form-group">
											<div class="col-sm-offset-2">
												<button class="btn btn-danger" type="submit">Rechazar</button>
											</div>
										</div>
						</form:form>
					</td>
				</tr> 
			</c:forEach>
		
		</tbody>
    </table>
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="invitations">
    <h2>Invitaciones</h2>

    <table id="invitationsTable" class="table table-striped">
		<tbody>
			<c:forEach items="${invitations}" var="invitation">
				<tr>
					<td>
						${invitation.match.players[0].user.username} te ha enviado una invitacion para unirte a la partida ${invitation.match}
					</td>
					<td>
						<form:form modelAttribute="invitation" class="form-horizontal" id="add-invitation-form" action="/matches/${invitation.match.id}/${invitation.id}/aceptar">
										<div class="form-group">
											<div class="col-sm-offset-2">
												<button class="btn btn-success" type="submit">Aceptar</button>
											</div>
										</div>
						</form:form>
					</td>
					<td>
						<form:form modelAttribute="invitacion" class="form-horizontal" id="add-invitation-form" action="/invitations/${invitation.id}/rechazar">
										<div class="form-group">
											<div class="col-sm-offset-2">
												<button class="btn btn-danger" type="submit">Rechazar</button>
											</div>
										</div>
						</form:form>
					</td>
				</tr> 
			</c:forEach>
		
		</tbody>
    </table>
</IdusMartii:layout>
</c:if>