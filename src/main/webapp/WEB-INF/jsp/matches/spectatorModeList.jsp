<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="matches">
    <h2>Partidas para espectar</h2>

    <table id="matchesTable" class="table table-striped">
        <thead>
        <tr>
            
            <th style="width: 150px;">Nombre de la partida</th>
            <th style="width: 30px;">Ronda</th>
            <th style="width: 150px">Faccion ganadora</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${matches}" var="match">
            <tr>
                <td>
                    <spring:url value="/matches/${match.id}/spectator" var="matchUrl"></spring:url>
                    <a href= "${fn:escapeXml(matchUrl)}">
                        <c:out value="${match.name}"/>
                    </a>
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
    <spring:url value="/matches/new" var="matchUrl">
    </spring:url>
    <a href= "${fn:escapeXml(matchUrl)}">Crear</a>
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="matches">
    <h2>Partidas para espectar</h2>

    <table id="matchesTable" class="table table-striped">
        <thead>
        <tr>
            
            <th style="width: 150px;">Nombre de la partida</th>
            <th style="width: 30px;">Ronda</th>
            <th style="width: 150px">Faccion ganadora</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${matches}" var="match">
            <tr>
                <td>
                    <spring:url value="/matches/${match.id}/spectator" var="matchUrl"></spring:url>
                    <a href= "${fn:escapeXml(matchUrl)}">
                        <c:out value="${match.name}"/>
                    </a>
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
    <spring:url value="/matches/new" var="matchUrl">
    </spring:url>
    <a href= "${fn:escapeXml(matchUrl)}">Crear</a>
</IdusMartii:layout>
</c:if>