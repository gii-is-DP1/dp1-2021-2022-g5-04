<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:adminLayout pageName="userAuditory">
    <h2>Auditorias Disponibles</h2>

		<div class="row justify-content-my-center">
			<div class="col-md-12">
            	<a href="/auditories/users" id="upload-link"><button style="width:650px;height:100px;FONT-SIZE: 36pt;">Auditor�a de Usuarios</button></a>
			</div>
		</div>
		<div class="row justify-content-my-center">
			<div class="col-md-12">
            	<a href="/auditories/players" id="upload-link"><button style="width:650px;height:100px;FONT-SIZE: 36pt;">Auditor�a de Players</button></a>
			</div>
		</div>
		<div class="row justify-content-my-center">
			<div class="col-md-12">
            	<a href="/auditories/matches" id="upload-link"><button style="width:650px;height:100px;FONT-SIZE: 36pt;">Auditor�a de Partidas</button></a>
			</div>
		</div>
		<div class="row justify-content-my-center">
			<div class="col-md-12">
            	<a href="/auditories/achievements" id="upload-link"><button style="width:650px;height:100px;FONT-SIZE: 36pt;">Auditor�a de Logros</button></a>
			</div>
		</div>

</IdusMartii:adminLayout>