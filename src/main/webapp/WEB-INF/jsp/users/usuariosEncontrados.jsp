<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="users">
    <h2>Usuarios encontrados</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre de usuario</th>
        </tr>
        </thead>
        <tbody>
      
           <c:forEach items="${users}" var="user">
            <tr>
                <td>
                	<c:out value="${user.username}"/>
                </td>
              <td>
				<spring:url value="/users/{id}/edit" var="userUrl">
		    	<spring:param name="id" value="${user.username}"/>
				</spring:url>
                	<a href= "${fn:escapeXml(userUrl)}">Editar</a>
               </td>
               <td> 
  				<spring:url value="/users/{username}/delete" var="userrUrl">
		    	<spring:param name="username" value="${user.username}"/>
				</spring:url> 
                    <a href="${fn:escapeXml(userrUrl)}">Eliminar</a>
           	   </td>
           	   <td> 
  				<spring:url value="/friendInvitations/{usernameRequester}/{usernameRequested}/save" var="friendUrl">
		    	<spring:param name="usernameRequester" value="${current.username}"/>
		    	<spring:param name="usernameRequested" value="${user.username}"/>
				</spring:url> 
                    <a href="${fn:escapeXml(friendUrl)}">Enviar solicitud de amistad</a>
           	   </td>
                                   	
            </tr>       
           </c:forEach>
              
                
                	
        </tbody>
    </table>
    <c:forEach items="${numberOfPagesList}" var="numberOfPage">
    	<spring:url value="/users/find?page={pageNumber}&username=${username}" var="numberUrl">
    	<spring:param name="pageNumber" value="${numberOfPage}"/>
    	</spring:url>
    		<a href="${fn:escapeXml(numberUrl)}">${numberOfPage}</a>
    </c:forEach>
    
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="users">
<h2>Usuarios encontrados</h2>

    <table id="usersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre de usuario</th>
        </tr>
        </thead>
        <tbody>
      		<c:forEach items="${users}" var="user">
           		<tr>
               		<td>
                		<c:out value="${user.username}"/>
               		</td>
               		<c:if test="${current != user}">
           	   			<td> 
  							<spring:url value="/friendInvitations/{usernameRequester}/{usernameRequested}/save" var="friendUrl">
		    				<spring:param name="usernameRequester" value="${current.username}"/>
		    				<spring:param name="usernameRequested" value="${user.username}"/>
							</spring:url> 
                    			<a href="${fn:escapeXml(friendUrl)}">Enviar solicitud de amistad</a>
           	   			</td>
                    </c:if>            	
            	</tr>       
           	</c:forEach>  	
        </tbody>
    </table>
    <c:forEach items="${numberOfPagesList}" var="numberOfPage">
    	<spring:url value="/users/find?page={pageNumber}" var="numberUrl">
    	<spring:param name="pageNumber" value="${numberOfPage}"/>
    	</spring:url>
    		<a href="${fn:escapeXml(numberUrl)}">${numberOfPage}</a>
    </c:forEach>
</IdusMartii:layout>
</c:if>