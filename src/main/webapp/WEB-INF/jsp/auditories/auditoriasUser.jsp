<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:adminLayout pageName="userAuditory">
    <h2>Auditoria de usuarios</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Nombre</th>
            <th style="width: 200px;">Fecha Creación</th>
            <th style="width: 200px;">Creador</th>
            <th style="width: 200px;">Última Modificación</th>
            <th style="width: 200px;">Modificador</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                	<c:out value="${user.username}"/>
                </td>
                <td>
                	<c:out value="${user.createdDate}"/>
                </td>
                <td>
                	<c:out value="${user.creator}"/>
                </td>
                <td>
                    <c:out value="${user.lastModifiedDate}"/>
                </td>
                <td>
                    <c:out value="${user.modifier}"/>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</IdusMartii:adminLayout>