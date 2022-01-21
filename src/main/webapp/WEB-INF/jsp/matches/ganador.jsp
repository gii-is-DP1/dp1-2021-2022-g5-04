<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="matches">
	<div class="row">
		<div class="col-md-6">
   			<h1>Victoria para los ${faccionGanadora}</h1>
				<img class="img-responsive" src="/resources/images/${cartaFaccion}.jpg" style="height: 400px; width: 300px;"/>
		</div>
		<div class="col-md-6">	
			<c:if test="${winner}">
				<img class="img-responsive" src="/resources/images/winner.png" style="height: 400px; width: 400px; margin-top: 50px;"/>
				
			</c:if>
			<c:if test="${!winner}">
				<img class="img-responsive" src="/resources/images/loser.png" style="height: 500px; width: 600px;"/>
			</c:if>
			
		</div>
	</div>
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="matches">
	<div class="row">
		<div class="col-md-6">
   			<h1>Victoria para los ${faccionGanadora}</h1>
			
					<img class="img-responsive" src="/resources/images/${cartaFaccion}.jpg" style="height: 400px; width: 300px;"/>
			
		</div>
		<div class="col-md-6">	
			<c:if test="${winner}">
				<img class="img-responsive" src="/resources/images/winner.png" style="height: 400px; width: 400px; margin-top: 50px;"/>
				
			</c:if>
			<c:if test="${!winner}">
				<img class="img-responsive" src="/resources/images/loser.png" style="height: 500px; width: 600px;"/>
			</c:if>
			
		</div>
	</div>
</IdusMartii:layout>
</c:if>