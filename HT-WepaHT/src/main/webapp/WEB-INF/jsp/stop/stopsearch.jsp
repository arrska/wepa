<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" type="text/css" />
        <title>Stop search</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        <h1>Stop search</h1>
        
        <form:form commandName="searchForm" method="GET" action="${pageContext.request.contextPath}/app/stop/search">
            <form:input path="query" /><form:errors path="query" />
            <br />
            <input type="submit" />
        </form:form>
        
        <c:if test="${not empty stops}">
            <h2>Search results for "${query}":</h2>
            
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>name</th>
                        <th>address</th>
                        <th>code</th>
                    </tr>
                </thead>
            <c:forEach var="stop" items="${stops}" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/app/stop/${stop.code}">
                                ${stop.name}
                            </a>
                        </td>
                        <td>${stop.address}</td>
                        <td>${stop.shortCode}</td>
                    </tr>
            </c:forEach>
            </table>
        </c:if>
    </body>
</html>
