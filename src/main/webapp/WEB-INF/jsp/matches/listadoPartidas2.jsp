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
                            	<c:out value="${player.id}"/>
                            	 <td>
                <input type="hidden" name="userId" value="${player.id}">  
                  <spring:url value="/matches/{id}" var="ownerUrl">
                        <spring:param name="id" value="${player.id}"/>
                                        <spring:param name="contador" value="${player.contador}"/>
                        
                    </spring:url>
                	
                	<a href= "${fn:escapeXml(ownerUrl)}">Create</a>
                </td>
            
             
               
            </tr>
        </c:forEach>
        </tbody>
    </table>
</IdusMartii:layout>
