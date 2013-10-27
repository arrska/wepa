<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" type="text/css" />
        <title>Information of stop ${stop.name}</title>
        
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=${gmapikey}&sensor=true">
        </script>
        <script type="text/javascript">
          function initializeMap() {
            var stop = new google.maps.LatLng(${stop.coordinates.latitude}, ${stop.coordinates.longtitude});
            var stopinfo = '${stop.name} (${stop.code})';
          
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
        <h1>Stop info</h1>
        <h2>${stop.name} (${stop.code})</h2>
            
    <sec:authorize access="isAuthenticated()">
        <h3>
            <c:if test="${!favourite}">
            <form action="${pageContext.request.contextPath}/app/stop/${stop.code}/favourite" method="post">
                <button type="submit">
                    lisää suosikkeihin
                </button>
            </form>
            </c:if>
            
            <c:if test="${favourite}">
            <form action="${pageContext.request.contextPath}/app/stop/${stop.code}/unfavourite" method="post">
                <button type="submit">
                    poista suosikeista
                </button>
            </form>
            </c:if>
        </h3>
    </sec:authorize>
    <table>
        <caption>
            departures
        </caption>
        <thead>
            <tr>
                <th>#</th>
                <th>line</th>
                <th>time</th>
                <th>date</th>
                <th>time</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="depa" items="${stop.departures}" varStatus="status">
                <tr>
                    <td>
                        ${status.count}
                    </td>
                    <td>
                        ${depa.linecode}
                    </td>
                    <td>
                        ${depa.time}
                    </td>
                    <td>
                        ${depa.date}
                    </td>
                    <td>
                        ${depa.datetime}
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
        
        
    <table>
        <caption>lines</caption>
        <thead>
            <tr>
                <th>#</th>
                <th>line</th>
                <th>destination</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="line" items="${stop.lines}" varStatus="status">
                <tr>
                    <td>
                        ${status.count}
                    </td>
                    <td>
                        ${line.linecode}
                    </td>
                    <td>
                        ${line.destination}
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
        
        
    <div id="stopmap"></div>
        
</body>
</html>
