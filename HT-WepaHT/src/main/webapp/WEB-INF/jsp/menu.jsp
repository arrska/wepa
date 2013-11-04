<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/style/style.css" />" type="text/css" />
        <title>HSL timetables</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        <h1>HSL timetables</h1>
        
        <a href="<c:url value="/app/login" />">login</a><br />
        <a href="<c:url value="/app/register" />">register</a><br />
        <a href="<c:url value="/app/stop" />">mystops</a><br />
    </body>
</html>
