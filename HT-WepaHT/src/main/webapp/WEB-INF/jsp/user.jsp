<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" type="text/css" />
        <title>User details</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        <h1><sec:authentication property="principal.username" /></h1>
        <form action="${pageContext.request.contextPath}/app/user/<sec:authentication property="principal.username" />" method="post">
            delete user<br />
            <input type="submit" name="delete" /><br />
            
            <!--table>
                <caption>change password</caption>
                <tbody>
                    <tr>
                        <td>new password</td>
                        <td><input type="text" name="password1" /></td>
                    </tr>
                    <tr>
                        <td>again</td>
                        <td><input type="text" name="password2" /></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" name="password" />
                        </td>
                    </tr>
                </tbody>
            </table-->
        </form>
    </body>
</html>
