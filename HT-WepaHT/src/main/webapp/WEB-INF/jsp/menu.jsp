<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" type="text/css" />
        <title>HSL timetables</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        <h1>HSL timetables</h1>
        
        <a href="${pageContext.request.contextPath}/app/stop/search?q=patola">patola search</a><br />
        <a href="${pageContext.request.contextPath}/app/login">login</a><br />
        <a href="${pageContext.request.contextPath}/app/register">register</a><br />
        <a href="${pageContext.request.contextPath}/app/stop">mystops</a><br />
    </body>
</html>
