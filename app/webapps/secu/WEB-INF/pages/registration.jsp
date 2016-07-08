<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../../head.jsp"/>
<h3>Registration:</h3>
<form action="foobar.secu" type="post">
	<label for="name">Name</label>
	<input type="text" name="name" id="name">
	<p></p>
	<label for="pass">Password</label>
	<input type="password" name="pass" id="pass">
	<p></p>
	<label for="passConf">Confirm Password</label>
	<input type="password" name="passConf" id="passConf">
</form>
<jsp:include page="../../foot.jsp"/>