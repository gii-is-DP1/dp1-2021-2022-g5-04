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
    <h2>
        <c:if test="${match['new']}">New </c:if> match
    </h2>
    <form:form modelAttribute="match" class="form-horizontal" id="add-match-form" >
        <div class="form-group has-feedback">
            <IdusMartii:inputField label="contador" name="contador"/>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${match['new']}">
                        <button class="btn btn-default" type="submit">Add Match</button>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </form:form>
</IdusMartii:layout>
