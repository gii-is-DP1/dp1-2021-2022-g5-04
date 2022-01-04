<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="matches">
   		<h1>Victoria para los ${faccionGanadora}</h1>
   		<c:if test="${ganadorLoyal}">
   			<h2>Has ganado</h2>
   		</c:if>
   		<c:if test="${ganadorTraitor}">
   			<h2>Has ganado</h2>
   		</c:if>
   		<c:if test="${ganadorMerchant}">
   			<h2>Has ganado</h2>
   		</c:if>
</IdusMartii:layout>