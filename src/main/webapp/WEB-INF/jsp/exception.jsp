<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="error">

    <spring:url value="/resources/images/error.png" var="error"/>
    <img src="${error}" style="width:300px;height:300px"/>

    <h2>Algo ha occurrido...</h2>

    <p>${exception.message}</p>

</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="error">

    <spring:url value="/resources/images/error.png" var="error"/>
    <img src="${error}" style="width:300px;height:300px"/>

    <h2>Algo ha occurrido...</h2>

    <p>${exception.message}</p>

</IdusMartii:layout>
</c:if>
