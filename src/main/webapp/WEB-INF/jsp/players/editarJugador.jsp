<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="player">
    <h2>
        <c:if test="${player['new']}">New </c:if> Player
    </h2>
    <form:form modelAttribute="player" class="form-horizontal" id="add-player-form" action="/players2/${id}/save">
        <div class="form-group has-feedback">
            <IdusMartii:inputField label="Name" name="Name"/>
            <IdusMartii:inputField label="Card1" name="Card1"/>
            <IdusMartii:inputField label="Card2" name="Card2"/>
            <IdusMartii:inputField label="Role" name="Role"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${player['new']}">
                              
                        <button class="btn btn-default" type="submit">Add Player</button>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </form:form>
</IdusMartii:layout>
