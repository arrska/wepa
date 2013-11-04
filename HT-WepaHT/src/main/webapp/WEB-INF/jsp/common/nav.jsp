<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<nav>
    <ul>
        <li>
            <a href="<c:url value="/app/" />">etusivu</a>
        </li>
        <li>
            <a href="<c:url value="/app/stop/search" />">haku</a>
        </li>
        <sec:authorize access="isAuthenticated()">
            <li>
                <a href="<c:url value="/app/stop" />">my stops</a>
            </li>    
                
            <li>
                <a href="<c:url value="/j_spring_security_logout" />" >Logout</a>
            </li>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <li>
                <a href="<c:url value="/app/login" />" >Login</a>
            </li>
        </sec:authorize>
    </ul>
</nav>