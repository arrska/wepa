<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<nav>
    <ul>
        <li>
            <a href="<c:url value="/app/" />">front page</a>
        </li>
        <li>
            <a href="<c:url value="/app/stop/search" />">search</a>
        </li>
        <sec:authorize access="isAuthenticated()">
            <li>
                <a href="<c:url value="/app/stop" />">my stops</a>
            </li>    
                
            <li>
                <a href="<c:url value="/j_spring_security_logout" />" >logout</a>
            </li>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <li>
                <a href="<c:url value="/app/login" />" >login</a>
            </li>
            <li>
                <a href="<c:url value="/app/register" />" >register</a>
            </li>
        </sec:authorize>
    </ul>
</nav>