<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% if( session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn") ){ %>
	<c:set var="loggedIn" value="${true}"/>
	<c:set var="userName" value="${session.getAttribute("userName")}"/>
<%} else{ %>
	<c:set var="loggedIn" value="${false}"/>
<%} %>

<jsp:include page="head.jsp"/>
<c:if test="${!loggedIn}">
<h3>Welcome to our non-CSS fully functional page!</h3>
</c:if>
<c:if test="${loggedIn}">
<h3>Welcome ${userName}</h3>
</c:if>
<jsp:include page="foot.jsp"/>