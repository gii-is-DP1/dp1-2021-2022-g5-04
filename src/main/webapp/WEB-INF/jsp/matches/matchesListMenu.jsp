<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>

<c:if test="${admin eq false}">
<IdusMartii:layout pageName="matches">
      <h2>Partidas</h2>
      <div class="row justify-content-my-center">
        <div class="col-md-12">
            <a href="/matches/created" id="upload-link"><button style="width:650px;height:100px;FONT-SIZE: 36pt;">Partidas creadas</button></a>
        </div>
    </div>
    <div class="row justify-content-my-center">
        <div class="col-md-12">
            <a href="/matches/played" id="upload-link"><button style="width:650px;height:100px;FONT-SIZE: 36pt;">Partidas jugadas</button></a>
        </div>
    </div>

   
</IdusMartii:layout>
</c:if>



