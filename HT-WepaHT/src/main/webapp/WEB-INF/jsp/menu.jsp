<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WepaHT</title>
    </head>
    <body>
        <h1>Le HT</h1>

        <p>Good luck!</p>
        <form method="get" action="${pageContext.request.contextPath}/app/stop/search">
            <input name="q" type="text" />
            <input type="submit" />
        </form>
        
        <a href="${pageContext.request.contextPath}/app/stop/search?q=patola">patola search</a>
        <a href="${pageContext.request.contextPath}">test</a>
    </body>
</html>
