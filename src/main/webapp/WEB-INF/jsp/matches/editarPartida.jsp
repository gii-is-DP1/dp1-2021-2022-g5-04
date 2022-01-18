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
	<IdusMartii:adminLayout pageName="matches">
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
				<td><c:forEach var="x" items="${noHostPlayers}">
				
				
					<li>${x.user.username} </li>
					<c:if test= "${isHost}" >
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
		<c:if test= "${isHost}" >
			<div class="col-md-9">
				<form:form modelAttribute="user" class="form-horizontal" id="add-user-form" action="/invitations/${match.id}/save" >
					<div class="form-group has-feedback">
						<div class="col-md-7">
							<IdusMartii:inputField label="Username" name="username"/>
						</div>
						<div class="col-md-3">
							<button class="btn btn-warning" type="submit">Enviar invitacion</button>
						</div>
					</div>
				</form:form>
			</div>
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
		</c:if>
	</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">



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

            <td><c:forEach var="x" items="${noHostPlayers}">
            
            
                <li>${x.user.username} </li>
                <c:if test= "${isHost}" >
		    		<form:form modelAttribute="match" class="form-horizontal" id="add-match-form" action="/players/${x.id}/${match.id}/expulsar" >
                    	<div class="form-group">
                        	<div class="col-sm-offset-2 col-sm-10">
                            	<button class="btn btn-danger" type="submit">X</button>
                        	</div>
                    	</div>
    	        	</form:form>  
    	    	</c:if>
            </c:forEach></td>
            

            <td><c:forEach var="x" items="${match.players}">
                <li>${x.user.username} </li>
                <c:if test= "${x.user.username ne current}" >
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
           	<div>
           		<form:form modelAttribute="user" class="form-horizontal" id="add-user-form" action="/invitations/${match.id}/save" >	
	               	<div class="col-md-6">   		    		               	
						<IdusMartii:inputField label="Username" name="username"/>
			   
						
					</div>
					<div class="col-md-3">
				
	   
						<button class="btn btn-warning" type="submit">Enviar invitacion</button>
					</div>
				</form:form>   
				
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
			</div>
    </c:if>

</IdusMartii:layout>
</c:if>

