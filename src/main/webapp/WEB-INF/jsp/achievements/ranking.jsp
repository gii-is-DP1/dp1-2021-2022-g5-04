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
<h2>Ranking</h2>

 <table>
        <thead>
        <tr>
            <th >Partidas jugadas</th>
            <td>
                        </td>
            
                        <th >Nombre</th>
        </tr>
        </thead>
            </table>
                 <table>
        
        <tbody>
        
        <c:forEach items="${map.keySet()}" var="keys">
            <tr>
            <td>
                                    <c:out value="${keys}"/>
                </td>
                <td>
                      
                        
                                                <td>
                
                    <c:out value="${map.get(keys)}"/>
                                    </td>
                    
              
            </tr>
                                  </c:forEach>
            
        </tbody>
    </table>
</IdusMartii:adminLayout>
                	</c:if>

<c:if test="${admin eq false}">
<IdusMartii:layout pageName="achievements">
    <h2>Ranking</h2>

 <table>
        <thead>
        <tr>
            <th >Partidas jugadas</th>
            
                        <th >Nombre</th>
            
        </tr>
        </thead>
            </table>
                 <table>
        
        <tbody>
        
        <c:forEach items="${map.keySet()}" var="keys">
            <tr>
            <td>
                                    <c:out value="${keys}"/>
                </td>
                <td>
                      
                        
                                                <td>
                
                    <c:out value="${map.get(keys)}"/>
                                    </td>
                    
              
            </tr>
                                  </c:forEach>
            
        </tbody>
    </table>
</IdusMartii:layout>
</c:if>
