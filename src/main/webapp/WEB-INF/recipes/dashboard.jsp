<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>QuickPantry Dashboard</title>
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
				<a href = "/recipes/saved">Saved Recipes</a> | 
				<a href = "/pantry">My Pantry</a>
			</div>
			<div class="headerRight3">
				<form action="/logout">
			    	<input type="submit" value="Logout" class="logoutButton">
				</form>
			</div>
		</div>
	</div>
	<h3 class="dashboardHeader">Top recipes for <a href="/pantry">Your Pantry</a>:</h3>
	<div class="recipeCont">
		<c:choose>
			<c:when test="${recipeResults != undefined}">
				<c:forEach var="recipe" items="${recipeResults}">
					<div class="recipeBox">
						<a href="/recipes/${recipe.recipeID}">
							<img src="<c:out value="${recipe.imageURL}"/>" class="indexRecipeIMG">
						</a>
						<h4 class="recipeIndexTitle"><c:out value="${recipe.title}"/></h4>
						<c:set var="percentage" value="${recipe.usedIngredients.size() / userIngredients.size() * 100}"/>
						<h4>Ingredients Used (<c:out value="${fn:substring(percentage, 0, 4)}"/>% Match):</h4>
						<div class="ingredientListCont">
							<div class="indexUsedIngredients">
								<h4 class="indexUsedLabel">Used (<c:out value="${recipe.usedIngredients.size()}"/>)</h4>
								<span class="recipeInfo">
									Used Ingredients:<br>
									<c:forEach var="usedIngredient" items="${recipe.usedIngredients}">
										- <c:out value="${usedIngredient}"/><br>
									</c:forEach>
								</span>
							</div>
							<div class="indexUnusedIngredients">
								<h4 class="indexUnusedLabel">Unused (<c:out value="${recipe.unusedIngredients.size()}"/>)</h4>
									<c:choose>
										<c:when test="${recipe.unusedIngredients.size() != 0}">
											<span class="recipeInfo">
												Unused Ingredients:<br>
												<c:forEach var="unusedIngredient" items="${recipe.unusedIngredients}">
													- <c:out value="${unusedIngredient}"/><br>
												</c:forEach>
											</span>
										</c:when>
									</c:choose>
							</div>
							<div class="indexMissedIngredients">
								<h4 class="indexMissedLabel">Missing (<c:out value="${recipe.missedIngredients.size()}"/>)</h4>
								<c:choose>
									<c:when test="${recipe.missedIngredients.size() != 0}">
										<span class="recipeInfo">
											Missing Ingredients:<br>
											<c:forEach var="missedIngredient" items="${recipe.missedIngredients}">
												- <c:out value="${missedIngredient}"/><br>
											</c:forEach>
										</span>
									</c:when>
								</c:choose>
							</div>
						</div>
					</div>
				 </c:forEach>
		  	</c:when>
		  	<c:otherwise>
		    	<h4>Please add at least 3 ingredients to your pantry to generate recipes.</h4>
		  	</c:otherwise>
  		</c:choose>
	</div>
</body>
</html>