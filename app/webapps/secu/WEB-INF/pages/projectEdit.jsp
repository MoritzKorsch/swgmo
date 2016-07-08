<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../../head.jsp"/>
<h3>Edit Project:</h3>
<c:if test="${msg != null || msg != '' || !msg.isEmpty()}">
	<p><c:out value="${msg}"/></p>
</c:if>
<form action="projectSave.secu" method="post">
	<label for="name">Project Name</label>
	<input type="text" name="name" id="name" value="${project.title}">
	<p></p>
	<label for="description">Description</label>
	<p></p>
	<textarea id="description" name="description">${project.description}</textarea>
	<p></p>
	<p></p>
	<input type="hidden" name="id" id="id" value="${project.id}">
	<input type="submit" value="save">
</form>
<jsp:include page="../../foot.jsp"/>