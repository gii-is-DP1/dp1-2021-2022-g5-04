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
            <td><c:out value="Tu voto: ${match.players[0].vote}"/></td>
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
                    <h1>Tu carta de facción1 actual es:  ${player.card1}</h1>
                    <h1>Tu carta de facción2 actual es:  ${player.card2}</h1>
                </tr>
           
     	         <c:if test= "${player.role eq 'EDIL'}" >     
     	                 
     	                     
    				<c:if test= "${match.round eq 1}" >
        				<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"   action="/players/${player.id}/${id}/guardarVotoNulo" >
            				<div class="form-group has-feedback">
            
            				</div>
           					<div class="form-group">
                				<div class="col-sm-offset-2 col-sm-10">
                
                            		<button class="btn btn-default" type="submit">Nulo</button>
        
        				</form:form>
    				</c:if>
    
    				<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/guardarVotoEnContra" >
        				<div class="form-group has-feedback">
          
        				</div>
        				<div class="form-group">
            				<div class="col-sm-offset-2 col-sm-10">
               
                        		<button class="btn btn-default" type="submit">En contra</button>
    
      				</form:form>
         			<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/guardarVotoAFavor" >
        				<div class="form-group has-feedback">
          
        				</div>
        				<div class="form-group">
            				<div class="col-sm-offset-2 col-sm-10">
               
                        		<button class="btn btn-default" type="submit">A favor</button>
    
      				</form:form>
      
  				</c:if>
        		<c:if test= "${player.role eq 'CONSUL'}" > 
        			<c:if test = "${match.round eq 0} && ${current != match.players[0].user.username}">    
   			 			<c:if test= "${player.card2 != 'DROPPED'}" >     
    						<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/${player.card1}/ElegirCartaFacción1" >
        						<div class="form-group has-feedback">
          
        						</div>
        						<div class="form-group">
            						<div class="col-sm-offset-2 col-sm-10">
               
                        				<button class="btn btn-default" type="submit">Elegir ${player.card1} </button>
    
      						</form:form>
         					<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/${player.card2}/ElegirCartaFacción2" >
        						<div class="form-group has-feedback">
          
        						</div>
        						<div class="form-group">
            						<div class="col-sm-offset-2 col-sm-10">
               
                       					<button class="btn btn-default" type="submit">Elegir ${player.card2}</button>
    
      						</form:form>
   						</c:if>
   					</c:if>
   					<c:if test = "${match.round eq 1} && ${current eq match.players[0].user.username}">
        				<c:if test= "${player.card2 != 'DROPPED'}" >     
    						<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/${player.card1}/ElegirCartaFacción1" >
        						<div class="form-group has-feedback">
          
        						</div>
        						<div class="form-group">
            						<div class="col-sm-offset-2 col-sm-10">
               
                        				<button class="btn btn-default" type="submit">Elegir ${player.card1} </button>
    
      						</form:form>
         					<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${player.id}/${id}/${player.card2}/ElegirCartaFacción2" >
        						<div class="form-group has-feedback">
          
        						</div>
        						<div class="form-group">
            						<div class="col-sm-offset-2 col-sm-10">
               
                       					<button class="btn btn-default" type="submit">Elegir ${player.card2}</button>
    
      						</form:form>
   						</c:if>
   					</c:if>
      			</c:if>
      			
      			<c:if test = "${player.role eq 'PRETOR'}">
      				<c:forEach var="p" items="${match.players}">
      					<c:if test = "${p.role eq 'EDIL'}">
      						<form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form"  action="/players/${p.id}/${id}/revisar">
                                <div class="form-group has-feedback">

                                </div>
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
  			
</c:forEach> 
</IdusMartii:layout>