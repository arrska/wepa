<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<c:url value="/style/style.css" />" type="text/css" />
        <title><c:out value="${stop.name}" /> | stop information</title>
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=${gmapikey}&sensor=true">
        </script>
        <script type="text/javascript">
            function initializeMap() {
                var longtitude = document.getElementById('longtitude').innerHTML;
                var latitude = document.getElementById('latitude').innerHTML;
                var stopinfo = document.getElementById('name').innerHTML + ' (' + document.getElementById('shortcode').innerHTML + ')';

                var stop = new google.maps.LatLng(latitude, longtitude);
          
                var mapOptions = {
                    center: stop,
                    zoom: 15,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("stopmap"), mapOptions);
                var marker = new google.maps.Marker({position: stop, map: map, title: stopinfo});
            }
            google.maps.event.addDomListener(window, 'load', initializeMap);
        </script>
        
        
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/common/header.jsp" />
        
        <div class="hidden">
            <span id="latitude">
                <c:out value="${stop.coordinates.latitude}" />
            </span>
            <span id="longtitude">
                <c:out value="${stop.coordinates.longtitude}" />
            </span>
            <span id="name">
                <c:out value="${stop.name}" />
            </span>
            <span id="shortcode">
                <c:out value="${stop.shortCode}" />
            </span>
        </div>
        
        
        <h1>Stop info</h1>
        <h2>
            <c:out value="${stop.name}" />
            <c:if test="${not empty stop.shortCode}">
                (<c:out value="${stop.shortCode}" />)
            </c:if>
        </h2>
        
        <sec:authorize access="isAuthenticated()">
            <h3>
                <c:if test="${!favourite}">
                    <form action="<c:url value="/app/stop/${stop.code}/favourite" />" method="post">
                        <button type="submit">
                            favourite
                        </button>
                    </form>
                </c:if>
                
                <c:if test="${favourite}">
                    <form action="<c:url value="/app/stop/${stop.code}/unfavourite" />" method="post">
                        <button type="submit">
                            unfavourite
                        </button>
                    </form>
                </c:if>
            </h3>
        </sec:authorize>
        <table class="deptable">
            <caption>
                next 10 departures
            </caption>
            <c:if test="${not empty stop.departures}">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>line</th>
                        <th>type</th>
                        <th>destination</th>
                        <th>time</th>
                        <th>minutes</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="depa" items="${stop.departures}" varStatus="status">
                        <tr>
                            <td>
                                ${status.count}
                            </td>
                            <td>
                                <a href="<c:url value="/app/line/${depa.line.code}/" />">
                                    <c:out value="${depa.line.shortCode}" />
                                </a>
                            </td>
                            <td>
                                <c:out value="${depa.line.transportTypeName}" />
                            </td>
                            <td>
                                <c:out value="${depa.line.end}" />
                            </td>
                            <td>
                                <fmt:formatDate value="${depa.datetime}" type="time" pattern="HH:mm" />
                            </td>
                            <td>
                                <c:out value="${depa.minutesUntil}" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </c:if>
            <c:if test="${empty stop.departures}">
                <tbody>
                    <tr>
                        <td>no departures in 6 hours</td>
                    </tr>
                </tbody>
            </c:if>
        </table>
        
        <br />
        
        <table class="linetable">
            <caption>lines</caption>
            
            <c:if test="${not empty stop.lines}">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>line</th>
                        <th>from</th>
                        <th>to</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="line" items="${stop.lines}" varStatus="status">
                        <tr>
                            <td>
                                ${status.count}
                            </td>
                            <td>
                                <a href="<c:url value="/app/line/${line.code}/" />">
                                    <c:out value="${line.shortCode}" />
                                </a>
                            </td>
                            <td>
                                <c:out value="${line.start}" />
                            </td>
                            <td>
                                <c:out value="${line.end}" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </c:if>
            
            <c:if test="${empty stop.lines}">
                <tbody>
                    <tr>
                        <td>no lines</td>
                    </tr>
                </tbody>
            </c:if>
        </table>
        
        <br />
        
        <div id="stopmap"></div>
        
    </body>
</html>
