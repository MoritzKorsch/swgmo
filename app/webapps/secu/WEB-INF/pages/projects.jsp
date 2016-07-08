<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../../head.jsp"/>
<h3>Projects Overview:</h3>

<form action="projectCreate.secu" method="get">
	<input type="submit" value="create new">					
</form>

<c:if test="${projects != null || !projects.isEmpty()}">
	<table border="1">
		<tr>
			<td>ID</td>
			<td>Name</td>
			<td>Description</td>
			<td>Owner (ID)</td>
			<td>Action</td>
		</tr>

	<c:forEach var="p" items="${projects}">
		<tr>
			<td>${p.id}</td>
			<td>${p.title}</td>
			<td>${p.description}</td>
			<td>${p.owner}</td>
			<td>
				<form action="projectEdit.secu" method="get">
					<input type="submit" value="edit">					
				</form>
				<form action="projectDelete.secu" method="post">
					<input type="hidden" name="id" id="id" value="${p.id}">
					<input type="submit" value="delete">					
				</form>
			</td>
		</tr>
	</c:forEach>
	</table>
</c:if>
<p>



</p>
<jsp:include page="../../foot.jsp"/>