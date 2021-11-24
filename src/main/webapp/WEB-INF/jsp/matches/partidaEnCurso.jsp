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
            <th>Host</th>
            <td><c:out value="${match.players[0].user.username}"/></td>
        </tr>
        <tr>
            <th>Players</th>
            <td>${match.players[0].user.username}</td>
        </tr>
        <tr>
            <th></th>
            <td>${match.players[0].user.username}</td>
        </tr>

    </table>
    <form:form modelAttribute="match" class="form-horizontal" id="add-match-form" action="/matches/${id}/game/save" >
        <!-- <div class="form-group has-feedback">
           <p> Turno: ${match.turn}</p>
            <p> Ronda: ${match.round}</p>
             <p> Votos a favor: ${match.votoaFavor}</p>
            <p> Votos en Contra: ${match.votoenContra}</p>
        </div> -->
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-default" type="submit">Comenzar partida</button>
            </div>
        </div>
    </form:form>
    <c:if test= "${match.round==1}" >
    <form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form" action="/matches/${id}/saven" >
        <div class="form-group has-feedback">
          
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-default" type="submit">Nulo</button>
    
      </form:form>
</c:if>
    
     <form:form modelAttribute="match" class="form-horizontal" id="add-mathch-form" action="/matches/${id}/saver" >
        <div class="form-group has-feedback">
          
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-default" type="submit">En contra</button>
    
      </form:form>
      
  

    
    
</IdusMartii:layout>