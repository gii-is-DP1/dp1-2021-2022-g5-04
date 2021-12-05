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
           		<tr>
                    <h1>Tu carta de faccion1 actual es:  ${player.card1}</h1>
                    <h1>Tu carta de faccion2 actual es:  ${player.card2}</h1>
                </tr>
                
                <h2>${usuario_votado}</h2>
                
                <c:if test="${match.c eq 0}">
           			<c:if test="${player.role eq 'EDIL'}">
           				<c:if test="${usuario_votado != current}">
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
    				</c:if>
    			</c:if>
    			<c:if test="${match.c eq 1}">
        			<c:if test="${player.role eq 'CONSUL'}" >
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
				</c:if>
				<c:if test="${match.c eq 2}">
				
				
				
   				
   					<c:if test ="${player.role eq 'CONSUL'}">
   					
   					<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/NuevoTurno" >
        						<div class="form-group has-feedback">
          
        						</div>
        						<div class="form-group">
            						<div class="col-sm-offset-2 col-sm-10">
               
                        				<button class="btn btn-default" type="submit">Contar Votos (Aun no los cuenta, solo pasa el turno) </button>
    
      						</form:form>
   				   				<c:if test = "${match.round eq 1}">
   				
        				<c:if test= "${player_actual.card2 != 'DROPPED'}" >     
    						<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/${player.card1}/ElegirCartaFacci�n1" >
        						<div class="form-group has-feedback">
          
        						</div>
        						<div class="form-group">
            						<div class="col-sm-offset-2 col-sm-10">
               
                        				<button class="btn btn-default" type="submit">Elegir ${player.card1} </button>
    
      						</form:form>
         					<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/${player.card2}/ElegirCartaFacci�n2" >
        						<div class="form-group has-feedback">
          
        						</div>
        						<div class="form-group">
            						<div class="col-sm-offset-2 col-sm-10">
               
                       					<button class="btn btn-default" type="submit">Elegir ${player.card2}</button>
    
      						</form:form>
   						</c:if>
   					</c:if>
   							   					</c:if>
   									
   									
   									
   									
   					
   						<c:if test = "${match.round eq 1} && ${ player_actual.role eq 'CONSUL'}">
   								<c:forEach var="pla" items="${match.players}">
   									<c:if test = "${current != pla.user.username}">
   									<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${pla.id}/${id}/asignarEdil">
                                	<div class="form-group has-feedback">

                                	</div>
                                	<div class="form-group">
                                    	<div class="col-sm-offset-2 col-sm-10">
                                           	<button class="btn btn-default" type="submit">Asignar a ${pla.user.username} como Edil</button>
                                    	</div>
                               		</div>
									</form:form>
									<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${pla.id}/${id}/asignarPretor">
                                		<div class="form-group has-feedback">

                                		</div>
                                		<div class="form-group">
                                    		<div class="col-sm-offset-2 col-sm-10">
                                           		<button class="btn btn-default" type="submit">Asignar a ${pla.user.username} como Pretor</button>
                                    		</div>
                               			</div>
									</form:form>
									</c:if>
   								</c:forEach>
   								   					</c:if>
   								   					</c:if>
   								
   						</c:if>
</c:forEach> 
</IdusMartii:layout>