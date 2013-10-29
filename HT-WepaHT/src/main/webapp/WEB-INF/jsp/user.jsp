<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" type="text/css" />
        <title>${username} | user details</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        <h1>${username}</h1>
            delete user<br />
        <form:form action="${pageContext.request.contextPath}/app/user/${username}" method="DELETE">
            <input type="submit" /><br />
        </form:form>
        <form:form commandName="passwdForm" action="${pageContext.request.contextPath}/app/user/${username}" method="PUT">
            <table>
                <tbody>
                    <tr>
                        <td>new password:</td>
                        <td>
                            <form:password path="password1" />
                            <form:errors path="password1" />
                        </td>
                    </tr>
                    <tr>
                        <td>again:</td>
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
    </body>
</html>
