<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="matches">
    <h2>Matches</h2>

    <table id="matchesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Match name</th>
            <th style="width: 200px;">Turns</th>
            <th style="width: 120px">Rounds</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${matches}" var="match">
            <tr>
                <td>
                    <spring:url value="/matches/{matchId}" var="matchUrl">
   <spring:param name="id" value="${match.id}"/>
                	     <input type="hidden" id="a" name="a" value="${match.turn}">
                	                     	     <input type="hidden" id="b" name="b" value="${match.round}">
                	     

                    </spring:url>
                    <a href="${fn:escapeXml(matchUrl)}"><c:out value="${match.name}"/></a>
                </td>
                <td>
                    <c:out value="${match.turn}"/>
                </td>
                <td>
                    <c:out value="${match.round}"/>
                </td>
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
   <td>
                	<spring:url value="/matches/{id}/new" var="playerUrl">
                	        <spring:param name="id" value="${match.id}"/>
                	     <input type="hidden" id="a" name="a" value="${match.turn}">
                	                     	     <input type="hidden" id="b" name="b" value="${match.round}">
                	
                    </spring:url>
                	<a href= "${fn:escapeXml(playerUrl)}">Editar</a>
                </td>
            </tr>
        </c:forEach>
        
                	<spring:url value="/matches/new" var="playerUrl">
                	
                    </spring:url>
                	<a href= "${fn:escapeXml(playerUrl)}">Crear</a>
        </tbody>
    </table>
</IdusMartii:layout>