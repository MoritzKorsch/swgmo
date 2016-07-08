<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../../head.jsp"/>
<h3>Registration:</h3>
<c:if test="${msg != null || msg != '' || !msg.isEmpty()}">
	<p><c:out value="${msg}"/></p>
</c:if>
<form action="registration.secu" method="post">
	<label for="name">Name</label>
	<input type="text" name="name" id="name">
	<p></p>
	<label for="pass">Password</label>
	<input type="password" name="pass" id="pass">
	<p></p>
	<label for="passConf">Confirm Password</label>
	<input type="password" name="passConf" id="passConf">
	<p></p>
	<p>Password must contain lower case char, uppercase char, a punctuation marks and numbers</p>
	<p></p>
	<input type="submit" value="register">
</form>
<jsp:include page="../../foot.jsp"/>