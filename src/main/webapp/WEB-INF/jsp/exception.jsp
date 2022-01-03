<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="error">

    <spring:url value="/resources/images/error.png" var="error"/>
    <img src="${error}" style="width:300px;height:300px"/>

    <h2>Algo ha occurrido...</h2>

    <p>${exception.message}</p>

</IdusMartii:layout>
