<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="players">
<h2>
    </h2>
    <table class="table table-striped">
        <tr>
            <td><c:out value="Voto del jugador ${player.user.username}: ${player.vote}"/></td>
        </tr>
        	<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${idMatch}/cambiarVoto">
                 <div class="form-group has-feedback">

                 </div>
                 <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                                  <button class="btn btn-default" type="submit">Cambiar voto de ${player.user.username}</button>
                        </div>
                 </div>
			</form:form>
			<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${idMatch}/noCambiarVoto">
                 <div class="form-group has-feedback">

                 </div>
                 <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                                  <button class="btn btn-default" type="submit">No cambiar</button>
                        </div>
                 </div>
			</form:form>
		
    </table>
    
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="players">
<h2>
    </h2>
    <table class="table table-striped">
        <tr>
            <td><c:out value="Voto del jugador ${player.user.username}: ${player.vote}"/></td>
        </tr>
        	<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${idMatch}/cambiarVoto">
                 <div class="form-group has-feedback">

                 </div>
                 <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                                  <button class="btn btn-default" type="submit">Cambiar voto de ${player.user.username}</button>
                        </div>
                 </div>
			</form:form>
			<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${idMatch}/noCambiarVoto">
                 <div class="form-group has-feedback">

                 </div>
                 <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                                  <button class="btn btn-default" type="submit">No cambiar</button>
                        </div>
                 </div>
			</form:form>
		
    </table>
    
</IdusMartii:layout>
</c:if>
  