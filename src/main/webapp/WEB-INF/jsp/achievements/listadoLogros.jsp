<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="achievements">
<h2>Logros</h2>

 <spring:url value="/achievements/new" var="achievementUrl">
                
                    </spring:url>
                    
                	<a href= "${fn:escapeXml(achievementUrl)}">Nuevo Logro</a>

    <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th >Nombre</th>
            <th ></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievement">
            <tr>
                
                <td>
                    <c:out value="${achievement.name}"/>
                </td>
                <td>
                	<c:out value="${user.getAchievements().contains(achievement) ? 'COMPLETADO' : 'NO COMPLETADO'}"/>	
                	
                </td>
                <td>
                <spring:url value="/achievements/{id}/edit" var="achievementsUrl">
                   <spring:param name="id" value="${achievement.id}"/>
                
                    </spring:url>
                    
                	<a href= "${fn:escapeXml(achievementsUrl)}">Editar</a>
                	</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</IdusMartii:adminLayout>
                	</c:if>

<c:if test="${admin eq false}">
<IdusMartii:layout pageName="achievements">
    <h2>Logros</h2>

    <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th >Nombre</th>
            <th ></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${achievements}" var="achievement">
            <tr>
                
                <td>
                    <c:out value="${achievement.name}"/>
                </td>
                <td>
                    <c:out value="${achievement.description}"/>
                </td>
                <td>
                	<c:out value="${user.getAchievements().contains(achievement) ? 'COMPLETADO' : 'NO COMPLETADO'}"/>	
                	
                </td>
                
                	
            </tr>
        </c:forEach>
        </tbody>
    </table>
</IdusMartii:layout>
</c:if>
