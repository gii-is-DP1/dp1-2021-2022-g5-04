<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="matches">
    <h2>Listado de partidas ${CorP}</h2>

    <table id="matchesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 100px;">Nombre de la partida</th>
            <th style="width: 50px">Creador</th>
            <th style="width: 50px">Faccion ganadora</th>
            <th style="width: 150px">Jugadores</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${matches}" var="match">
            <tr>
            <c:if test="${match.round == 0}">
                <td>
                <spring:url value="/matches/{id}/new" var="matchUrl">
   					<spring:param name="id" value="${match.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(matchUrl)}"><c:out value="${match.name}"/></a>
                </td>
            </c:if>
            <c:if test="${match.round == 1 || match.round == 2}">
            	<td>
                <spring:url value="/matches/{id}/match" var="matchUrl">
   					<spring:param name="id" value="${match.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(matchUrl)}"><c:out value="${match.name}"/></a>
              	  </td>
            </c:if>
            <c:if test="${match.round == 3}">
            	<td>
                     <c:out value="${match.name}"/>
                </td>
            </c:if>
            	<td>
            		<c:out value="${match.players[0].user.username}"/>
            	</td>
             
                <td>
                    <c:out value="${match.winner}"/>
                </td>
                <td>
                    <c:forEach items="${match.players}" var="player">
                        <c:out value=" ${player.user.username},    "/>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
               
        </tbody>
    </table>
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="matches">
      <h2>Listado de partidas ${CorP}</h2>

    <table id="matchesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre de la partida</th>
            <th style="width: 100px">Creador</th>
            <th style="width: 30px;">Ronda</th>
            <th style="width: 150px">Faccion ganadora</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${matches}" var="match">
            <tr>
            <c:if test="${match.round == 0}">
                <td>
                <spring:url value="/matches/{id}/new" var="matchUrl">
   					<spring:param name="id" value="${match.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(matchUrl)}"><c:out value="${match.name}"/></a>
                </td>
            </c:if>
            <c:if test="${match.round == 1 || match.round == 2}">
            	<td>
                <spring:url value="/matches/{id}/match" var="matchUrl">
   					<spring:param name="id" value="${match.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(matchUrl)}"><c:out value="${match.name}"/></a>
              	  </td>
            </c:if>
            <c:if test="${match.round == 3}">
            	<td>
                     <c:out value="${match.name}"/>
                </td>
            </c:if>
            	<td>
            		<c:out value="${match.players[0].user.username}"/>
            	</td>
                <td>
                    <c:out value="${match.round}"/>
                </td>
                <td>
                    <c:out value="${match.winner}"/>
                </td>
            </tr>
        </c:forEach>
               
        </tbody>
    </table>
</IdusMartii:layout>
</c:if>