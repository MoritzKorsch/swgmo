<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% if( session.getAttribute("loggedIn") != null && (Boolean) session.getAttribute("loggedIn") ){ %>
	<c:set var="loggedIn" value="${true}"/>
<%} else{ %>
	<c:set var="loggedIn" value="${false}"/>
<%} %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Who needs CSS anyways...</title>
    <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>

<body>

<ul id="navigation">
    <li><a href="/secu/">Start / Main</a></li>
    
    <c:choose>
		<c:when test="${loggedIn}">
			<li><a href="logout.secu">Logout</a></li>
		</c:when>
		<c:otherwise>
	    	<li><a href="login.secu">Login</a></li>
	   		<li><a href="register.secu">Register</a></li>
	   	</c:otherwise>
 	</c:choose>
    <li><a href="texts.secu">Manage Texts</a></li>
    <li><a href="projects.secu">Manage Projects</a></li>
</ul>

<div id="Inhalt">
