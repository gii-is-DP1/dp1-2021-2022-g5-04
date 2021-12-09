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
            <th>Turn</th>
            <td><c:out value="${match.turn}"/></td>
        </tr>
        <tr>
            <th>Round</th>
            <td><c:out value="${match.round}"/></td>
        </tr>
        <tr>
            <th>Votos a favor</th>
            <td><c:out value="${match.votoaFavor}"/></td>
            <th>Votos en Contra</th>
            <td><c:out value="${match.votoenContra}"/></td>
            <td><c:out value="Tu voto: ${player_actual.vote}"/></td>
        </tr>
        <tr>
            <th>Host</th>
            <td><c:out value="${match.players[0].user.username}"/></td>
        </tr>
        <tr>
            <th>Players</th>
            <td><c:forEach var="x" items="${match.players}">
                		<li>${x.user.username} ---> ${x.role}</li>
                </c:forEach></td>
        </tr>
        
      </table>
<c:forEach items="${match.players}" var="player">
         <c:if test= "${player.user.username eq current}" >
                <tr>
                    <h1>Tu rol actual es:  ${player.role}</h1>
                 
                </tr>
			<c:if test="${enseñarCartas}">
           		<tr>
                    <h1>Tus cartas de facción son: ${player.card1}, ${player.card2}</h1>
                </tr>
			</c:if>
			<c:if test="${enseñarCartas eq false}">
				<tr>
					<h1>Tu facción es: ${player.card1}</h1>
				</tr>
			</c:if>
                <c:if test="${votar}">
           			<div class="col-sm-offset-2 col-sm-10">
        				<form:form modelAttribute="player_actual" class="form-horizontal" id="add-match-form" action="/players/${player.id}/${id}/${votos[0]}">
            				
           					<div class="form-group">
                				<div class="col-sm-offset-2 col-sm-10">
									
									
									<button class="btn btn-default" type="submit">Votar a favor</button>
								</div>
							</div>
        							
        				</form:form>
        				<form:form modelAttribute="player_actual" class="form-horizontal" id="add-match-form" action="/players/${player.id}/${id}/${votos[1]}">
            				
           					<div class="form-group">
                				<div class="col-sm-offset-2 col-sm-10">
									
									
									<button class="btn btn-default" type="submit">Votar en contra</button>
								</div>
							</div>
        				</form:form>
        				<c:if test="${votos[2] eq 'YELLOW'}">
        					<form:form modelAttribute="player_actual" class="form-horizontal" id="add-match-form" action="/players/${player.id}/${id}/${votos[2]}">
            				
           						<div class="form-group">
                					<div class="col-sm-offset-2 col-sm-10">
									
									
										<button class="btn btn-default" type="submit">Votar nulo</button>
									</div>
								</div>
        							
        					</form:form>
        				</c:if>
        			</div>
    			</c:if>
    			<c:if test="${revisarVoto}">
        				<c:forEach var="p" items="${match.players}">
        					<c:if test="${p.role eq 'EDIL'}">
        						<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${p.id}/${id}/revisar" method="GET">
                    				<div class="form-group has-feedback"></div>
                        			<div class="form-group">
                        				<div class="col-sm-offset-2 col-sm-10">
                            				<button class="btn btn-default" type="submit">Revisar voto de ${p.user.username}</button>
                           			</div>
                       			</div>
								</form:form>
							</c:if>
						</c:forEach>
				</c:if>
   				<c:if test="${elegirFaccion}">     
    				<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/${player.card1}/ElegirCartaFaccion1" >
        				<div class="form-group has-feedback">
          
        				</div>
        				<div class="form-group">
            				<div class="col-sm-offset-2 col-sm-10">
               
                        		<button class="btn btn-default" type="submit">Elegir ${player.card1} </button>
    
      				</form:form>
      				<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/${player.card2}/ElegirCartaFaccion2" >
        				<div class="form-group has-feedback">
          
        				</div>
        				<div class="form-group">
            				<div class="col-sm-offset-2 col-sm-10">
               
                       			<button class="btn btn-default" type="submit">Elegir ${player.card2}</button>
    
      				</form:form>
         		</c:if>
         		<c:if test="${elegirFaccion eq false}">
         			<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${id}/NuevoTurno" >
        				<div class="form-group has-feedback">
          
        				</div>
        				<div class="form-group">
            				<div class="col-sm-offset-2 col-sm-10">
               
                        		<button class="btn btn-default" type="submit">Contar Votos</button>
    
      				</form:form>
         		</c:if> 
   		</c:if>
 
</c:forEach> 
</IdusMartii:layout>