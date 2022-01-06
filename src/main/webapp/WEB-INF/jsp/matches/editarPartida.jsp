<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="matches">
    <h2>
    </h2>
    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${match.name} "/></b></td>
        </tr>
        <tr>
            <th>Host</th>
            <td><c:out value="${match.players[0].user.username}"/></td>
        </tr>
        <tr>
            <th>Players</th>
            <td><c:forEach var="x" items="${match.players}">
                <li>${x.user.username} </li>
                <c:if test= "${player.user.username ne current}" >
		    	<form:form modelAttribute="match" class="form-horizontal" id="add-match-form" action="/players/${x.id}/${match.id}/expulsar" >
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                        
                                    <button class="btn btn-danger" type="submit">X</button>
                        </div>
                    </div>
    	        </form:form>  
		    </c:if>
                </c:forEach></td>
        </tr>     
    </table> 
    
    <c:if test= "${match.players[0].user.username eq current}" >
        <c:if test="${hideInvitationButton}">
    		<form:form modelAttribute="user" class="form-horizontal" id="add-user-form" action="/invitations/${match.id}/save" >
            	<div class="form-group">
                	<div class="col-md-6">
						<IdusMartii:inputField label="Username" name="username"/>
			   
						
					</div>
					<div class="col-md-3">
				
	   
						<button class="btn btn-warning" type="submit">Enviar invitacion</button>
					</div>
				</div>
    		</form:form>   
        </c:if>
    </c:if>
    <div class="col-md-3">
        <c:if test="${startMatch}">
            <form:form modelAttribute="match" class="form-horizontal" id="add-match-form" action="/matches/${id}/game/save" >
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button class="btn btn-success" type="submit">Comenzar partida</button>
                        </div>
                    </div>
            </form:form>
    </c:if>
</div>

</IdusMartii:layout>