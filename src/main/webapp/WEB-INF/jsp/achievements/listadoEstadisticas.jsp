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
<h2>Estadisticas</h2>



<table class="table table-striped">
    <tr>
        <th>Partidas jugadas</th>
        <td><b><c:out value="${statistics.get(0)} " /></b></td>
    </tr>
    <tr>
        <th>Partidas ganadas</th>
        <td><c:out value="${statistics.get(1)}" /></td>
    </tr>
    <tr>
        <th>Partidas perdidas</th>
        <td><c:out value="${statistics.get(2)}" /></td>
    </tr>
    <tr>
        <th>Porcentaje de victorias</th>
        <td><c:out value="${statistics.get(3)} %" /></td>
    </tr>
    <tr>
        <th>Porcentaje de derrotas</th>
        <td><c:out value="${statistics.get(3)} %" /></td>
    </tr>
    
    

</table>
</IdusMartii:adminLayout>
                	</c:if>

<c:if test="${admin eq false}">
<IdusMartii:layout pageName="achievements">
    <h2>Estadisticas</h2>



<table class="table table-striped">
    <tr>
        <th>Partidas jugadas</th>
        <td><b><c:out value="${statistics.get(0)} " /></b></td>
    </tr>
    <tr>
        <th>Partidas ganadas</th>
        <td><c:out value="${statistics.get(1)}" /></td>
    </tr>
    <tr>
        <th>Partidas perdidas</th>
        <td><c:out value="${statistics.get(2)}" /></td>
    </tr>
    <tr>
        <th>Porcentaje de victorias</th>
        <td><c:out value="${statistics.get(3)} %" /></td>
    </tr>
    <tr>
        <th>Porcentaje de derrotas</th>
        <td><c:out value="${statistics.get(3)} %" /></td>
    </tr>
    
    

</table>
</IdusMartii:layout>
                	</c:if>
