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
<title><c:out value="${userName}"/>'s Saved Recipes</title>
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
				<a href = "/pantry">My Pantry</a>
			</div>
			<div class="headerRight3">
				<form action="/logout">
			    	<input type="submit" value="Logout" class="logoutButton">
				</form>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${favoriteRecipeList.size() != 0}">
			<h1 class="savedRecipeTitle">Your Saved Recipes:</h1>
			<div class="favoriteRecipeCont">
				<c:forEach var="favoriteRecipe" items="${favoriteRecipeList}">
					<div class="favoriteRecipeItem">
						<img src="${favoriteRecipe.imageURL}" />
						<a href="/recipes/${favoriteRecipe.recipeID}"><c:out value="${favoriteRecipe.title}"/></a>
						<form:form action="/recipes/unsave/${favoriteRecipe.id}" method="post" class="favoriteRecipeForm">
							<input type="hidden" name="_method" value="delete">
						    <input type="submit" value="Unsave" class="saveRecipeButton" />
						</form:form>
					</div>
					<hr>
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<p class="noSavedRecipes">No (unassigned) saved recipes to show!</p>
		</c:otherwise>
	</c:choose>
	<h1 class="savedRecipeTitle">Your Categories:</h1>
	<div class="addCategory">
		<button type="button" onClick="addCategory()" id="addCategoryButton">Add Category</button>
		<div id="addCategoryFormCont" style="display: none;">
			<form:form action="/category/create" method="post" modelAttribute="category" class="categoryForm">
				<form:errors path="name" class="error" />
		        <form:label path="name">Enter a name:</form:label>
		        <form:input path="name" />
		        <form:hidden path="user" value="${userId}" />
		        <p class="addCategoryActions">
		    		<input type="submit" value="Submit" class="submit" />
    				<input type="button" onclick="cancelAddCategory()"value="Cancel" />
    			</p>
			</form:form>
		</div>
	</div>
	<c:choose>
		<c:when test="${categoryList.size() != 0}">
			<div class="categoryCont">
				<c:forEach var="category" items="${categoryList}">
					<button class="collapsible"><c:out value="${category.name}"/></button>
					<div class="content">
						<div class="categoryControls">
							<div class="categoryControlButtons">
								<c:choose>
									<c:when test="${favoriteRecipeList.size() != 0}">
										<button type="button" onClick="displayAddRecipe('${category.name}')" id="categoryControlsButtonAddRecipe${category.name}">Add Recipe</button>
									</c:when>
								</c:choose>
								<c:choose>
									<c:when test="${category.favoriteRecipes.size() != 0}">
										<button type="button" onClick="displayRemoveRecipe('${category.name}')" id="categoryControlsButtonRemoveRecipe${category.name}">Remove Recipe</button>
									</c:when>
								</c:choose>
								<button type="button" onClick="displayRenameCategory('${category.name}')" id="categoryControlsButtonRenameCategory${category.name}">Rename Category</button>
								<button type="button" onClick="displayDeleteCategory('${category.name}')" id="categoryControlsButtonDeleteCategory${category.name}">Delete Category</button>
							</div>
							<div id="categoryControlsInner">
								<div id="categoryControlAddRecipeBox${category.name}" style="display: none;">
									<h4 class="recipeControlHeader">Add a recipe:</h4>
									<form:form action="/category/${category.id}" method="post" modelAttribute="category" class="categoryForm">
									  	<input type="hidden" name="_method" value="put">
									  	<form:hidden path="name" value="${category.name}" />
								        <form:hidden path="user" value="${userId}" />
								        <form:select path="favoriteRecipes" multiple="false">  
								        	<c:forEach var="favoriteRecipe" items="${favoriteRecipeList}">
									    		<form:option value="${favoriteRecipe.id}" label="${favoriteRecipe.title}"/>
									    	</c:forEach>
									    </form:select>
								    	<input type="submit" value="Add" class="submit" />
								    	<button type="button" onClick="hideControls('${category.name}')">Cancel</button>
									</form:form>
								</div>
								<div id="categoryControlRemoveRecipeBox${category.name}" style="display: none;">
									<h4 class="recipeControlHeader">Remove a recipe:</h4>
									<form:form action="/category/${category.id}" method="post" modelAttribute="category" class="categoryForm">
									  	<input type="hidden" name="_method" value="put">
									  	<form:hidden path="name" value="${category.name}" />
								        <form:hidden path="user" value="${userId}" />
								        <form:select path="favoriteRecipes" multiple="false">  
								        	<c:forEach var="favoriteRecipe" items="${category.favoriteRecipes}">
									    		<form:option value="${favoriteRecipe.id}" label="${favoriteRecipe.title}"/>
									    	</c:forEach>
									    </form:select>
								    	<input type="submit" value="Remove" class="submit" />
								    	<button type="button" onClick="hideControls('${category.name}')">Cancel</button>
									</form:form>
								</div>
								<div id="categoryControlRenameCategoryBox${category.name}" style="display: none;">
									<h4 class="recipeControlHeader">Rename this category:</h4>
									<form:form action="/category/${category.id}" method="post" modelAttribute="category" class="categoryForm">
									  	<input type="hidden" name="_method" value="put">
									  	<form:errors path="name" class="error" />
									  	<form:input path="name" value="${category.name}" />
								        <form:hidden path="user" value="${userId}" />
								        <c:forEach var="favoriteRecipe" items="${category.favoriteRecipes}">
									    		<form:hidden path="favoriteRecipes" value="${favoriteRecipe.id}" />
									    </c:forEach>
								    	<input type="submit" value="Rename Category" class="submit" />
								    	<button type="button" onClick="hideControls('${category.name}')">Cancel</button>
									</form:form>
								</div>
								<div id="categoryControlDeleteCategoryBox${category.name}" style="display: none;">
									<h4 class="recipeControlHeader">Are you sure you want to delete this category?</h4>
									<form:form action="/category/${category.id}" method="post" modelAttribute="category" class="categoryForm">
									  	<input type="hidden" name="_method" value="delete">
								    	<input type="submit" value="Delete Category" class="submit" />
								    	<button type="button" onClick="hideControls('${category.name}')">Cancel</button>
									</form:form>
								</div>
							</div>
						</div>
						<c:forEach var="favoriteRecipe" items="${category.favoriteRecipes}">
							<div class="favoriteRecipeCategoryItem">
								<img src="${favoriteRecipe.imageURL}" />
								<a href="/recipes/${favoriteRecipe.recipeID}"><c:out value="${favoriteRecipe.title}"/></a>
								<form:form action="/recipes/unsave/${favoriteRecipe.id}" method="post" class="favoriteRecipeForm">
									<input type="hidden" name="_method" value="delete">
								    <input type="submit" value="Unsave" class="saveRecipeButton" />
								</form:form>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<p class="noCategories">You have no categories yet!</p>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript" src="/javascript/pantryScript.js" ></script>
</body>
</html>