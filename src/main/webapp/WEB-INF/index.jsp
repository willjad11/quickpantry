<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>QuickPantry - Login or Register</title>
</head>
<body>
	<div class="header">
		<div class="headerCenter">
			<h1>QuickPantry</h1>
			<p class="indexHeader">A "whats in your kitchen" recipe finder!</p>
		</div>
	</div>
	<div class="mainCont">
		<div class="registerCont">
			<h1>Register</h1>
			<form:form action="/register" method="post" modelAttribute="newUser" class="registerForm">
				<p class="form">
			        <form:label path="userName">Username:</form:label>
			        <form:errors path="userName" class="error" />
			        <form:input path="userName"/>
			    </p>
			    <p class="form">
			        <form:label path="email">Email:</form:label>
			        <form:errors path="email" class="error" />
			        <form:input path="email"/>
			    </p>
			    <p class="form">
			        <form:label path="password">Password:</form:label>
			        <form:errors path="password" class="error" />
			        <form:input type="password" path="password"/>
			    </p>
			    <p class="form">
			        <form:label path="confirm">Confirm Password:</form:label>
			        <form:errors path="confirm" class="error" />
			        <form:input type="password" path="confirm"/>
			    </p>
			    <input type="submit" value="Register" class="loginButton" />
			</form:form>
		</div>
		<div class="loginCont">
			<h1>Login</h1>
			<form:form action="/login" method="post" modelAttribute="newLogin" class="loginForm">
			    <p class="form">
			        <form:label path="email">Email:</form:label>
			        <form:errors path="email" class="error" />
			        <form:input path="email"/>
			    </p>
			    <p class="form">
			        <form:label path="password">Password:</form:label>
			        <form:errors path="password" class="error" />
			        <form:input type="password" path="password"/>
			    </p>
			    <input type="submit" value="Login" class="loginButton" />
			</form:form>
		</div>
	</div>
</body>
</html>