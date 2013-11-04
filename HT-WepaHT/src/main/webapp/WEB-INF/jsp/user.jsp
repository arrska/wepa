<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/style/style.css" />" type="text/css" />
        <title>${username} | user details</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        <h1>${username}</h1>
        
        <c:url value="/app/user/${username}" var="formAction" />
        <form:form action="${formAction}" method="DELETE">
            <button type="submit">delete user</button>
        </form:form>
        <br />
        api key: <b> ${apikey} </b>
        <br />
        <br />
        <form:form commandName="passwdForm" action="${formAction}" method="PUT">
            <table>
                <caption>change password</caption>
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
                            <button type="submit">ok</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form:form>
    </body>
</html>
