<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags"%>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="chats">
	<div class="row">
		<c:forEach items="${chats}" var="chat">
			<c:if test="${currentUser eq chat.user}">
				<div class="row flex-justifycontent-end">
					<div class="col-md-8"></div>
					<div class="col-md-4" style="background-color:green">
						<h1 style="color: aliceblue;"><u>${chat.user.username}: </u><h3 style="color: aliceblue;">${chat.text}</h3></h1>
					</div>
				</div>
			</c:if>
			<c:if test="${currentUser != chat.user}">
				<div class="row flex-justifycontent-end">
					<div class="col-md-4" style="background-color:red">
						<h1 style="color: aliceblue;"><u>${chat.user.username}: </u><h3 style="color: aliceblue;">${chat.text}</h3></h1>
					</div>
				</div>
			</c:if>
			
		</c:forEach>
	</div>
	<div class="row" style="margin-top: 10px;">
	
		
		<form:form modelAttribute="msg" class="form-horizontal" id="add-user-form" action="/chats/${match_id}/save">
			
				<div class="col-md-10">
					<IdusMartii:inputField label="Escribe un mensaje" name="text"/>
				</div>
				<div class="col-md-2">
					<button class="btn btn-warning" type="submit">Enviar</button>
				</div>
			
		</form:form>
	</div>
	
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="chats">
	<div class="row">
		<c:forEach items="${chats}" var="chat">
			<c:if test="${currentUser eq chat.user}">
				<div class="row flex-justifycontent-end">
					<div class="col-md-8"></div>
					<div class="col-md-4" style="background-color:green">
						<h1 style="color: aliceblue;"><u>${chat.user.username}: </u><h3 style="color: aliceblue;">${chat.text}</h3></h1>
					</div>
				</div>
			</c:if>
			<c:if test="${currentUser != chat.user}">
				<div class="row flex-justifycontent-end">
					<div class="col-md-4" style="background-color:red">
						<h1 style="color: aliceblue;"><u>${chat.user.username}: </u><h3 style="color: aliceblue;">${chat.text}</h3></h1>
					</div>
				</div>
			</c:if>
			
		</c:forEach>
	</div>
	<div class="row" style="margin-top: 10px;">
	
		
		<form:form modelAttribute="msg" class="form-horizontal" id="add-user-form" action="/chats/${match_id}/save">
			
				<div class="col-md-10">
					<IdusMartii:inputField label="Escribe un mensaje" name="text"/>
				</div>
				<div class="col-md-2">
					<button class="btn btn-warning" type="submit">Enviar</button>
				</div>
			
		</form:form>
	</div>
	
</IdusMartii:layout>
</c:if>