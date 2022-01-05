<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="playerAuditory">
    <h2>Auditoria de Players</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 50px;">Id</th>
            <th style="width: 200px;">Usuario</th>
            <th style="width: 200px;">Partida</th>
            <th style="width: 200px;">Fecha Creación</th>
            <th style="width: 200px;">Creador</th>
            <th style="width: 200px;">Última Modificación</th>
            <th style="width: 200px;">Modificador</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${players}" var="player">
            <tr>
                <td>
                	<c:out value="${player.id}"/>
                </td>
                <td>
                	<c:out value="${player.user.username}"/>
                </td>
                <td>
                	<c:out value="${player.match}"/>
                </td>
                <td>
                	<c:out value="${player.createdDate}"/>
                </td>
                <td>
                	<c:out value="${player.creator}"/>
                </td>
                <td>
                    <c:out value="${player.lastModifiedDate}"/>
                </td>
                <td>
                    <c:out value="${player.modifier}"/>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</IdusMartii:layout>