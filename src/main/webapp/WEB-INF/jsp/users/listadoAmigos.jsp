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
<IdusMartii:adminLayout pageName="friends">
    <h2>Listado de amigos</h2>
    <tbody>
        <c:forEach items="${friends}" var="user">
            <tr>
            	<td>
                     <p><c:out value="${user.username}"/></p>
                </td>
                <td>
					<form:form modelAttribute="friend" class="form-horizontal" id="add-invitation-form" action="/users/delete/${user.username}">
						<div class="form-group">
							<div class="col-sm-offset-2">
								<button class="btn btn-danger" type="submit">Eliminar</button>
							</div>
						</div>
					</form:form>
				</td>
            </tr>
        </c:forEach>
    </tbody>
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="friends">
    <h2>Listado de amigos</h2>
    <tbody>
        <c:forEach items="${friends}" var="user">
            <tr>
            	<td>
                     <p><c:out value="${user.username}"/></p>
                </td>
                <td>
					<form:form modelAttribute="friend" class="form-horizontal" id="add-invitation-form" action="/users/delete/${user.username}">
						<div class="form-group">
							<div class="col-sm-offset-2">
								<button class="btn btn-danger" type="submit">Eliminar</button>
							</div>
						</div>
					</form:form>
				</td>
            </tr>
        </c:forEach>
    </tbody>
</IdusMartii:layout>
</c:if>