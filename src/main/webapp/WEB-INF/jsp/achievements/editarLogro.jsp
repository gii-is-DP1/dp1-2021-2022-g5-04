<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<IdusMartii:layout pageName="usersCreation">
    <h2>
    Achievements
    ${achievement.id}
    </h2>
    <form:form modelAttribute="achievement" class="form-horizontal" id="add-user-form" action="/achievements/${achievement.id}/save">
        <div class="form-group has-feedback">
            <IdusMartii:inputField label="Name" name="name"/>
            <IdusMartii:inputField label="Description" name="description"/>
            <IdusMartii:inputField label="Valor" name="valor"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
             
                        <button class="btn btn-default" type="submit">Confirmar cambios</button>
                  
            </div>
        </div>
    </form:form>
</IdusMartii:layout>
