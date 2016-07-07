<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Who needs CSS anyways...</title>
    <script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
</head>

<body>

<ul id="navigation">
    <li><a href="/secu/">Start / Main</a></li>
    <% if( session.getAttribute("loggedIn") != null ) {%>
    	<% if( (Boolean) session.getAttribute("loggedIn") ) { %>
    		<li><a href="logout.secu">Logout</a></li>
    	<% } %>
    <% } else { %>
    	<li><a href="login.secu">Login</a></li>
    	<li><a href="register.secu">Register</a></li>
    <% } %>
    <li><a href="texts.secu">Manage Texts</a></li>
    <li><a href="projects.secu">Manage Projects</a></li>
</ul>

<div id="Inhalt">
