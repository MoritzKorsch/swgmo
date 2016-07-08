<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../../head.jsp"/>
<h3>Projects Overview: <c:out value="${loggedIn}"/></h3>

<p>
<form action="projectCreate.secu" method="get">
	<input type="submit" value="create">					
</form>
<form action="projectDelete.secu" method="post">
	<input type="submit" value="delete">					
</form>
<form action="projectEdit.secu" method="get">
	<input type="submit" value="edit">					
</form>
</p>
<jsp:include page="../../foot.jsp"/>