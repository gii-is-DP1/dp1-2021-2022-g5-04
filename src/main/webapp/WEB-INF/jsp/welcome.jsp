<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->

<IdusMartii:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
    <h2> Project ${title}</h2>
   <p><h2> Group ${group}</h2></p>
   <p><ul>
   <!-- <c:forEach var="x" items="${persons}"> -->
        <li>${persons[0].firstName} ${persons[0].lastName}</li>
        <li>${persons[1].firstName} ${persons[1].lastName}</li>
        <li>${persons[2].firstName} ${persons[2].lastName}</li>
        <li>${persons[3].firstName} ${persons[3].lastName}</li>
        <li>${persons[4].firstName} ${persons[4].lastName}</li>
        <li>${persons[5].firstName} ${persons[5].lastName}</li>
   <!-- </c:forEach> -->
   </ul></p>
   </div>
   <h2>${now}></h2>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/logoPNG_3.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
    </div>
</IdusMartii:layout>