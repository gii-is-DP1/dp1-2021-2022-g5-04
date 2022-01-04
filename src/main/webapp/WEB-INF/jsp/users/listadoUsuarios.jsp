<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="users">
    <h2>Users</h2>
${admin}

${temp}

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 200px;">Username</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                	<c:out value="${user.username}"/>
                </td>
                <td>
              
                
                	<spring:url value="/users/new" var="userUrl">
                    </spring:url>
                                    <c:if test="${admin eq true}">
                    
                	<a href= "${fn:escapeXml(userUrl)}">Editar</a>
                	</c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</IdusMartii:layout>