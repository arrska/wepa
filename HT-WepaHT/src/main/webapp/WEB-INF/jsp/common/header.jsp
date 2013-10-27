<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<header>
    <sec:authorize access="isAuthenticated()" >
        logged in as
        <a href="${pageContext.request.contextPath}/app/user/<sec:authentication property="principal.username" />">
            <sec:authentication property="principal.username" />
        </a>
    </sec:authorize>
    <jsp:include page="nav.jsp" />
    
</header>
