<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="Friend Invitations">
    <h2>Solicitudes de Amistad</h2>

    <table id="friendInvitationsTable" class="table table-striped">
		<tbody>
			<c:forEach items="${friendInvitations}" var="friendInvitation">
				<tr>
					<td>
						${friendInvitation.userRequester} te ha enviado una solicitud de amistad.
					</td>
					<td>
						<form:form modelAttribute="friendInvitation" class="form-horizontal" id="add-invitation-form" action="/friendInvitations/${friendInvitation.id}/accept">
										<div class="form-group">
											<div class="col-sm-offset-2">
												<button class="btn btn-success" type="submit">Aceptar</button>
											</div>
										</div>
						</form:form>
					</td>
					<td>
						<form:form modelAttribute="friendInvitation" class="form-horizontal" id="add-invitation-form" action="/friendInvitations/${friendInvitation.id}/decline">
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
   <h2>Solicitudes de Amistad</h2>

    <table id="friendInvitationsTable" class="table table-striped">
		<tbody>
			<c:forEach items="${friendInvitations}" var="friendInvitation">
				<tr>
					<td>
						${friendInvitation.userRequester} te ha enviado una solicitud de amistad.
					</td>
					<td>
						<form:form modelAttribute="friendInvitation" class="form-horizontal" id="add-invitation-form" action="/friendInvitations/${friendInvitation.id}/accept">
										<div class="form-group">
											<div class="col-sm-offset-2">
												<button class="btn btn-success" type="submit">Aceptar</button>
											</div>
										</div>
						</form:form>
					</td>
					<td>
						<form:form modelAttribute="friendInvitation" class="form-horizontal" id="add-invitation-form" action="/friendInvitations/${friendInvitation.id}/decline">
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