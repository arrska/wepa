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
        <h2>Welcome!</h2>
        This service uses HSL timetable data via <a href="http://developer.reittiopas.fi/pages/en/http-get-interface-version-2.php">HSL Reittiopas HTTP API</a><br />
        Start by signing in or registering. Or just by searching some timetables. <br />
    </body>
</html>
