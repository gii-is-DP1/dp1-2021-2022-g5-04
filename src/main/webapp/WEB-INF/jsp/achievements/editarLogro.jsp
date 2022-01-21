<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:adminLayout pageName="Achievements">
    <h2>
    Logros
    </h2>
    <form:form modelAttribute="achievement" class="form-horizontal" id="add-user-form" action="/achievements/${achievement.id}/save">
        <div class="form-group has-feedback">
            <input type="hidden" name="id" value="${achievement.id}"/>
            <IdusMartii:inputField label="name" name="name"/>
            <IdusMartii:inputField label="description" name="description"/>
            <IdusMartii:inputField label="valor" name="valor"/>
            <div class="control-group">
            	<IdusMartii:selectField label="achievementType" name="achievementType" names="${achievementType}" size="3"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Confirmar cambios</button>   
            </div>
        </div>
    </form:form>
</IdusMartii:adminLayout>
