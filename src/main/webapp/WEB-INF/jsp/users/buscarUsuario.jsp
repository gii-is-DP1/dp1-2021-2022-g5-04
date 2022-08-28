<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="usersFinding">
    <form:form modelAttribute="playerAux" class="form-horizontal" id="find-user-form" action="/users/finded">
        <div class="form-group has-feedback">
            <IdusMartii:inputField label="Nombre de usuario:" name="name"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Buscar</button>
            </div>
        </div>
    </form:form>
</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
<IdusMartii:layout pageName="usersFinding">
    <form:form modelAttribute="playerAux" class="form-horizontal" id="find-user-form" action="/users/finded">
        <div class="form-group has-feedback">
            <IdusMartii:inputField label="Nombre de usuario:" name="name"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Buscar</button>
            </div>
        </div>
    </form:form>
</IdusMartii:layout>
</c:if>
