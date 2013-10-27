<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pysäkkilista</title>
    </head>
    <body>
        <h1>Pysäkit</h1>
        <ul>
        <c:forEach var="stop" items="${stops}">
            <li>
                <a href="${pageContext.request.contextPath}/app/stop/${stop.code}">
                ${stop.name} ${stop.address} (${stop.code})
                </a>
            </li>
        </c:forEach>
        </ul>
    </body>
</html>
