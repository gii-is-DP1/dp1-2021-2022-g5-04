<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="matches">
    <h2>Players</h2>

    <table id="playersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${players}" var="player" >
            <tr>
            <td>
            <c:if test="${player.idm==match}">
                            	<c:out value="${player.name}"/>
                            	 <td>
             
                	<spring:url value="/players/new" var="playerUrl">
                    </spring:url>
                	<a href= "${fn:escapeXml(playerUrl)}">Create</a>
                </td>
            
             </c:if>
               
            </tr>
        </c:forEach>
        </tbody>
    </table>
</IdusMartii:layout>
