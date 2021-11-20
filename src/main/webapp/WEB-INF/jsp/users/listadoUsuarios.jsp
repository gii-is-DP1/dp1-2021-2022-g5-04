<!-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="users">
    <h2>Users</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Username</th>
            <th style="width: 200px;">Email</th>
            <th style="width: 100px;">Enabled</th>
            <th style="width: 200px;">Password</th>
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <c:out value="${user.username}"/>
                </td>
                 <td>
                    <c:out value="${user.email}"/>
                </td>
				<td>
                    <c:out value="${user.enabled}"/>
                </td>
        		 <td>
                    <c:out value="${user.password}"/>
                </td>
                <td>
                
                	<spring:url value="/users/new" var="userUrl">
                    </spring:url>
                	<a href= "${fn:escapeXml(userUrl)}">Create</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</IdusMartii:layout> -->
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="users">
    <h2>Users</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Match name</th>
            <th style="width: 200px;">Turns</th>
            <th style="width: 120px">Rounds</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <spring:url value="/users/{userId}" var="userUrl">
                        <spring:param name="userId" value="${user.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(userUrl)}"><c:out value="${user.username}"/></a>
                </td>
                <td>
                    <!-- <c:out value="${users.username}"/> -->
                </td>
                <td>
                    <!-- <c:out value="${match.round}"/> -->
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
                	<spring:url value="/users/{id}/new" var="playerUrl">
                	         <spring:param name="id" value="${user.id}"/>
                	
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