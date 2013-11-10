<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/style/style.css" />" type="text/css" />
        <title>My stops</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        <div id="mystops">
        
        <h1>My stops</h1>
        <c:if test="${empty stops}">
            no favourited stops
        </c:if>
        <c:if test="${not empty stops}">
            <ul>
                <c:forEach var="stop" items="${stops}">
                    <li>
                        <a href="<c:url value="/app/stop/${stop.code}" />">
                            <c:out value="${stop.name} ${stop.address} (${stop.shortCode})" />
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
            
        </div>
    </body>
</html>
