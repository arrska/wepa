<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" type="text/css" />
        <title>login</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        
        <sec:authorize access="isAuthenticated()">
            You are already logged in. :)
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <h1>Log in to myTimetables</h1>
            
            <c:if test="${error}">
                <div class="errorblock">
                    Login failed <br />
                    ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                </div>
            </c:if>
            
            <form name="f" action="<c:url value='/j_spring_security_check' />" method="post">
                <table>
                    <tbody>
                        <tr>
                            <td>username:</td>
                            <td><input type="text" name="j_username" /></td>
                        </tr>
                        <tr>
                            <td>password:</td>
                            <td><input type="password" name="j_password" /></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="submit" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </sec:authorize>
    </body>
</html>
