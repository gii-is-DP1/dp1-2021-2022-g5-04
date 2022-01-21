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
<IdusMartii:adminLayout pageName="friends">
    <h2>Listado de amigos</h2>
    <table id="friendsTable" class="table table-striped">
    	<thead>
    		<tr>
    			<th style="width: 150px;">Nombre de usuario</th>
    		</tr>
   		</thead>
    	<tbody>
        	<c:forEach items="${friends}" var="friend">
            	<tr>
            		<td>
                     	<p><c:out value="${friend.username}"/></p>
                	</td>
                	<td>
						<spring:url value="/users/delete/{username}" var="friendUrl">
		    			<spring:param name="username" value="${friend.username}"/>
						</spring:url> 
                    		<a href="${fn:escapeXml(friendUrl)}">Eliminar</a>
					</td>
            	</tr>
        	</c:forEach>
    	</tbody>
    </table>
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="friends">
    <h2>Listado de amigos</h2>
    <table id="friendsTable" class="table table-striped">
    	<thead>
    		<tr>
    			<th style="width: 150px;">Nombre de usuario</th>
    		</tr>
   		</thead>
    	<tbody>
        	<c:forEach items="${friends}" var="friend">
            	<tr>
            		<td>
                     	<p><c:out value="${friend.username}"/></p>
                	</td>
                	<td>
						<spring:url value="/users/delete/{username}" var="friendUrl">
		    			<spring:param name="username" value="${friend.username}"/>
						</spring:url> 
                    		<a href="${fn:escapeXml(friendUrl)}">Eliminar</a>
					</td>
            	</tr>
        	</c:forEach>
    	</tbody>
    </table>
</IdusMartii:layout>
</c:if>