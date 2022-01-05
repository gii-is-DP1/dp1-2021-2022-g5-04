<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${admin}">
<IdusMartii:adminLayout pageName="home">
     <spring:url value="/resources/images/fondo.jpg" htmlEscape="true" var="fondoImage"/>
    
   
    <div class="row" style="background-image: url(${fondoImage}); background-size: cover;"> 
        <div class="col-sm-6">
            <ul></ul>
            <a href="http://localhost:8080/matches/new">
                <button class ="btn btn-success"style="margin: 50px; margin-top: 250px; width:500px;height:100px;FONT-SIZE: 36pt; " >Crear una partida</button>
              </a> 
        </div>
        <div class="col-md-6">
            <spring:url value="/resources/images/icon-idus.png" htmlEscape="true" var="iconImage"/>
            <img class="img-responsive" src="${iconImage}" style="height: 600px; width: 600px;"/>
        </div>
        
        
    </div>
    <div class="row">
        <h2> Project ${title}</h2>
        <p><h2> Group ${group}</h2></p>
        <p><ul>
        <c:forEach var="x" items="${persons}">
            <li>${x.firstName} ${x.lastName}</li>
        </c:forEach>
        </ul></p>
   </div>
    
    
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="home">
    <!-- <h2><fmt:message key="welcome"/></h2> -->
    <spring:url value="/resources/images/fondo.jpg" htmlEscape="true" var="fondoImage"/>
    
   
    <div class="row" style="background-image: url(${fondoImage}); background-size: cover;"> 
        <div class="col-sm-6">
            <ul></ul>
            <a href="http://localhost:8080/matches/new">
                <button class ="btn btn-success"style="margin: 50px; margin-top: 250px; width:500px;height:100px;FONT-SIZE: 36pt; " >Crear una partida</button>
              </a> 
        </div>
        <div class="col-md-6">
            <spring:url value="/resources/images/icon-idus.png" htmlEscape="true" var="iconImage"/>
            <img class="img-responsive" src="${iconImage}" style="height: 600px; width: 600px;"/>
        </div>
        
        
    </div>
    <div class="row">
        <h2> Project ${title}</h2>
        <p><h2> Group ${group}</h2></p>
        <p><ul>
        <c:forEach var="x" items="${persons}">
            <li>${x.firstName} ${x.lastName}</li>
        </c:forEach>
        </ul></p>
   </div>
    
   <!-- <h2>${now}></h2> -->
    
    
</IdusMartii:layout>
</c:if>