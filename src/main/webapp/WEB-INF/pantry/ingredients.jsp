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
<title><c:out value="${userName}"/>'s Pantry</title>
</head>
<body>
	<div class="header">
		<div class="headerCenter">
			<h1>QuickPantry</h1>
		</div>
		<div class="headerRightCont">
			<div class="headerRight1">
				<h4>Welcome, <c:out value="${userName}"/>!</h4>
			</div>
			<div class="headerRight2">
				<a href = "/recipes">Dashboard</a> | 
				<a href = "/recipes/saved">Saved Recipes</a>
			</div>
			<div class="headerRight3">
				<form action="/logout">
			    	<input type="submit" value="Logout" class="logoutButton">
				</form>
			</div>
		</div>
	</div>
	<h3 class="pantryHeader">Your pantry contains:</h3>
	<c:choose>
		<c:when test="${ingredientList.size() != 0}">
			<table>
				<tr>
				    <th>Name</th>
				    <th>Actions</th>
				</tr>
				<c:forEach var="ingredient" items="${ingredientList}">
					<tr>
						<td>
							<div id="ingredientName${ingredient.id}">
		    					<c:out value="${ingredient.name}" />
		    				</div>
		    				<div id="ingredientEditForm${ingredient.id}" style="display: none;">
			    				<form:form action="/ingredients/${ingredient.id}" method="post" modelAttribute="ingredient" class="ingredientEditForm" >
									<input type="hidden" name="_method" value="put">
									<form:input path="name" value="${ingredient.name}" />
									<form:hidden path="user" value="${userID}" />
									<p class="ingredientActions">
									    <input type="submit" value="Submit" class="editButton" />
									    <input type="button" onclick="cancelEditIngredient(${ingredient.id})" value="Cancel" />
									</p>
								</form:form>
		    				</div>
		    			</td>
		    			<td>
							<div class="ingredientActions">
								<button type="button" onClick="editIngredient(${ingredient.id})" class="editIngredientButton">Edit</button>
								<form action="/ingredients/<c:out value="${ingredient.id}"/>" method="post">
						    		<input type="hidden" name="_method" value="delete">
						    		<input type="submit" value="Delete" class="deleteButton">
								</form>
							</div>
		    			</td>
		  			</tr>
    			</c:forEach>
			</table>
	  	</c:when>
	  	<c:otherwise>
	    	<h4 class="noIngredients">There are no ingredients in your pantry! Enter at least 3 to generate recipes.</h4>
	  	</c:otherwise>
  	</c:choose>
	<form:form action="/ingredients/save" method="post" modelAttribute="ingredient" class="ingredientForm">
		<form:label path="name" class="ingredientLabel">Add an ingredient:</form:label>
		<form:errors path="name" class="error" />
		<form:input path="name" value="${ingredient.name}" class="ingredientInput"/>
		<form:hidden path="user" value="${userID}" />
	    <input type="submit" value="Add" class="submitIngredientButton" />
	</form:form>
	<script type="text/javascript" src="/javascript/pantryScript.js" ></script>
</body>
</html>