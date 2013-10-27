<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Stop search</h1>
        
        <form method="get" action="${pageContext.request.contextPath}/app/stop/search">
            <input name="q" type="text" value="<c:if test="${!empty query}">${query}</c:if>" />
            <input type="submit" />
        </form>
        
        <c:if test="${!empty stops}">
            <h2>Search results for "${query}":</h2>
            
            <table>
                <thead>
                    <tr>
                        <th>name</th>
                        <th>address</th>
                        <th>code</th>
                    </tr>
                </thead>
            <c:forEach var="stop" items="${stops}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/app/stop/${stop.code}">
                                ${stop.name}
                            </a>
                        </td>
                        <td>${stop.address}</td>
                        <td>${stop.code}</td>
                    </tr>
            </c:forEach>
            </table>
        </c:if>
    </body>
</html>
