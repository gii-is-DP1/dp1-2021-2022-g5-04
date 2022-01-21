<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="achievement">
    <h2>Rankings de Jugadores</h2>



    <table class="table table-striped">
        <tr>
            <th>Top partidas jugadas</th>
            <td><c:out value="${winners.get(0)}---------->${stats.get(0)}" /></td>
        </tr>
        <tr>
            <th>Top partidas ganadas</th>
            <td><c:out value="${winners.get(1)}---------->${stats.get(1)}" /></td>
        </tr>
        <tr>
            <th>Top partidas perdidas</th>
            <td><c:out value="${winners.get(2)}---------->${stats.get(2)}" /></td>
        </tr>
        <tr>
            <th>Top porcentaje de victorias</th>
            <td><c:out value="${winners.get(3)}---------->${stats.get(3)} %" /></td>
        </tr>
        <tr>
            <th>Top porcentaje de derrotas</th>
            <td><c:out value="${winners.get(4)}---------->${stats.get(4)} %" /></td>
        </tr>
        
        
    
    </table>
</IdusMartii:adminLayout>
                	</c:if>

<c:if test="${admin eq false}">
<IdusMartii:layout pageName="achievements">
    <h2>Rankings de Jugadores</h2>



    <table class="table table-striped">
        <tr>
            <th>Top partidas jugadas</th>
            <td><c:out value="${winners.get(0)}---------->${stats.get(0)}" /></td>
        </tr>
        <tr>
            <th>Top partidas ganadas</th>
            <td><c:out value="${winners.get(1)}---------->${stats.get(1)}" /></td>
        </tr>
        <tr>
            <th>Top partidas perdidas</th>
            <td><c:out value="${winners.get(2)}---------->${stats.get(2)}" /></td>
        </tr>
        <tr>
            <th>Top porcentaje de victorias</th>
            <td><c:out value="${winners.get(3)}---------->${stats.get(3)} %" /></td>
        </tr>
        <tr>
            <th>Top porcentaje de derrotas</th>
            <td><c:out value="${winners.get(4)}---------->${stats.get(4)} %" /></td>
        </tr>
        
        
    
    </table>
    
    
</IdusMartii:layout>
</c:if>
