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
                    <td><c:out value="${match.players[0].vote} "/></td>
        </tr>
        
        <tr>
            <th>Players</th>
            <td><c:forEach var="x" items="${match.players}">
                <li>${x.user.username} </li>
                </c:forEach></td>
        </tr>
        
      </table>
  
<c:forEach items="${match.players}" var="player">
         <c:if test= "${player.user.username eq current}" >
            
                <tr>
                    <h1>Tu rol actual es:  ${player.role}</h1>
                 
                </tr>
           
     	         <c:if test= "${player.role eq 'EDIL'}" >     
     	                 
     	                     
    <c:if test= "${match.round==1}" >
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
  </c:if>
</c:forEach>
    
    
</IdusMartii:layout>