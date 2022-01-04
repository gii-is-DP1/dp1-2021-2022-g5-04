<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="achievementAuditory">
    <h2>Auditoria de logros</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Id</th>
            <th style="width: 200px;">Nombre</th>
            <th style="width: 200px;">Fecha Creación</th>
            <th style="width: 200px;">Creador</th>
            <th style="width: 200px;">Última Modificación</th>
            <th style="width: 200px;">Modificador</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievement">
            <tr>
                <td>
                	<c:out value="${achievement.id}"/>
                </td>
                <td>
                	<c:out value="${achievement.name}"/>
                </td>
                <td>
                	<c:out value="${achievement.createdDate}"/>
                </td>
                <td>
                	<c:out value="${achievement.creator}"/>
                </td>
                <td>
                    <c:out value="${achievement.lastModifiedDate}"/>
                </td>
                <td>
                    <c:out value="${achievement.modifier}"/>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</IdusMartii:layout>