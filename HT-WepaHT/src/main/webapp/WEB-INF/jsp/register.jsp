<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" type="text/css" />
        <title>Register</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        <h1>Register</h1>
        
    <sec:authorize access="isAuthenticated()">
        You need to <a href="<c:url value="/j_spring_security_logout" />">logout</a> to register new account
    </sec:authorize>
    <sec:authorize access="!isAuthenticated()">
        <form:form commandName="regForm" action="${pageContext.request.contextPath}/app/register" method="POST">
            <table>
                <tbody>
                    <tr>
                        <td>username:</td>
                        <td>
                            <form:input path="username" />
                            <form:errors path="username" />
                        </td>
                    </tr>
                    <tr>
                        <td>password:</td>
                        <td>
                            <form:password path="password1" />
                            <form:errors path="password1" />
                        </td>
                    </tr>
                    <tr>
                        <td>password again:</td>
                        <td>
                            <form:password path="password2" />
                            <form:errors path="password2" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </form:form>
        
    </sec:authorize>
    
    <c:if test="${not empty users}">
        <ul>
            <c:forEach var="user" items="${users}">
                <li>${user.name} ${user.password}</li>
                </c:forEach>
        </ul>
    </c:if>
</body>
</html>
