<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags" %>
<c:if test="${admin}">
<IdusMartii:adminLayout pageName="achievement">
<h2>Achievements</h2>



    <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th >Partidas jugadas</th>
            <th ></th>
                        <th >Partidas ganadas</th>
                        <th ></th>
            
        </tr>
        </thead>
        <tbody>
            <tr>
                
                <td>
                    <c:out value="${statistics}"/>
                </td>
                  <td>
                    <c:out value="${win}"/>
                </td>
               
            </tr>
        </tbody>
    </table>
</IdusMartii:adminLayout>
                	</c:if>

<c:if test="${admin eq false}">
<IdusMartii:layout pageName="achievements">
    <h2>Achievements</h2>

      <table id="achievementsTable" class="table table-striped">
        <thead>
        <tr>
            <th >Partidas jugadas</th>
            <th ></th>
        </tr>
        </thead>
        <tbody>
            <tr>
                
                <td>
                    <c:out value="${statistics}"/>
                </td>
               
            </tr>
        </tbody>
    </table>
</IdusMartii:layout>
                	</c:if>
