<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/style/style.css" />" type="text/css" />
        <title><c:out value="${line.name}" /> | line info</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        
        <h1>Line info</h1>
        <h2><c:out value="${line.name}" /> (<c:out value="${line.shortCode}" />)</h2>
        <h3><c:out value="${line.transportTypeName}" /></h3>
        
        <table>
            <caption>
                stops
            </caption>
            <thead>
                <tr>
                    <th>#</th>
                    <th>code</th>
                    <th>address</th>
                    <th>name</th>
                    <th>time</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="linestop" items="${line.stops}" varStatus="status">
                    <tr>
                        <td>
                            ${status.count}
                        </td>
                        <td>
                            <a href="<c:url value="/app/stop/${linestop.code}" />">
                                <c:out value="${linestop.shortCode}" />
                            </a>
                        </td>
                        <td>
                            <c:out value="${linestop.address}" />
                        </td>
                        <td>
                            <c:out value="${linestop.name}" />
                        </td>
                        <td>
                            <c:out value="${linestop.time}" />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
