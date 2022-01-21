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
    <h2>Modo espectador de la partida: ${match.name}</h2>
    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${match.name} " /></b></td>
        </tr>
        <tr>
            <th>Turno</th>
            <td><c:out value="${match.turn}" /></td>
        </tr>
        <tr>
            <th>Ronda</th>
            <td><c:out value="${match.round}" /></td>
        </tr>
        <tr>
            <th>Votos a favor</th>
            <td><c:out value="${match.votesInFavor}" /></td>
        </tr>
        <tr>
            <th>Votos en Contra</th>
            <td><c:out value="${match.votesAgainst}" /></td>
        </tr>
        <tr>
            <th>Creador</th>
            <td><c:out value="${match.players[0].user.username}" /></td>
        </tr>
        <tr>
            <th>Jugadores</th>
            <td><c:forEach var="x" items="${match.players}">
                    <li>${x.user.username}---> ${x.role}</li>

                </c:forEach></td>
        </tr>

    </table>
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="matches">
    <h2>Modo espectador de la partida: ${match.name}</h2>
    <table class="table table-striped">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${match.name} " /></b></td>
        </tr>
        <tr>
            <th>Turno</th>
            <td><c:out value="${match.turn}" /></td>
        </tr>
        <tr>
            <th>Ronda</th>
            <td><c:out value="${match.round}" /></td>
        </tr>
        <tr>
            <th>Votos a favor</th>
            <td><c:out value="${match.votesInFavor}" /></td>
        </tr>
        <tr>
            <th>Votos en Contra</th>
            <td><c:out value="${match.votesAgainst}" /></td>
        </tr>
        <tr>
            <th>Creador</th>
            <td><c:out value="${match.players[0].user.username}" /></td>
        </tr>
        <tr>
            <th>Jugadores</th>
            <td><c:forEach var="x" items="${match.players}">
                    <li>${x.user.username}---> ${x.role}</li>

                </c:forEach></td>
        </tr>

    </table>
</IdusMartii:layout>
</c:if>