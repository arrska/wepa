<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<header>
    <sec:authorize access="isAuthenticated()" >
        <sec:authentication property="principal.username" var="username" />
        logged in as
        <a href="${pageContext.request.contextPath}/app/user/${username}">
            ${username}
        </a>
    </sec:authorize>
    <jsp:include page="nav.jsp" />
    <c:if test="${not empty message}">
        <div class="messageblock">
            ${message}
        </div>
    </c:if>
    
</header>
