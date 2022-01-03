<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<IdusMartii:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row justify-content-md-center" style="background-image:url()">
        <div class="col-md-6">
            
            <a href="http://localhost:8080/matches/new">
                <button style="width:600px;height:100px;FONT-SIZE: 36pt;">Crear una partida</button>
              </a> 
              <p></p>
              
            
        </div>
        <div class="col-md-6">
            <spring:url value="/resources/images/icon-idus.png" htmlEscape="true" var="iconImage"/>
            <img class="img-responsive" src="${iconImage}"/>
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
   <h2>${now}></h2>
    
    
</IdusMartii:layout>