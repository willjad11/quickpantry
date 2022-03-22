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
<title><c:out value="${title}"/></title>
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
				<a href = "/pantry">My Pantry</a> | 
				<form action="/logout">
			    	<input type="submit" value="Logout" class="logoutButton">
				</form>
			</div>
		</div>
	</div>
	<img class="recipeDetailIMG" src="${image}" />
	<h1 class="recipeDetailIMGTitle"><c:out value="${title}"/></h1>
	<h3 class="recipeDetailIMGServings">Servings: <c:out value="${servings}"/></h3>
	<c:choose>
		<c:when test="${isFollowing == false}">
			<form:form action="/recipes/save/${id}" method="post" modelAttribute="favoriteRecipe" class="recipeForm">
				<form:hidden path="title" value="${title}" />
				<form:hidden path="imageURL" value="${image}" />
				<form:hidden path="recipeID" value="${id}" />
				<form:hidden path="user" value="${userId}" />
			    <input type="submit" value="Save" class="saveRecipeButton" />
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form action="/recipes/unsave/${dbid}/${id}" method="post" class="recipeForm">
				<input type="hidden" name="_method" value="delete">
			    <input type="submit" value="Unsave" class="saveRecipeButton" />
			</form:form>
		</c:otherwise>
	</c:choose>
	<div class="recipeDetailCont">
		<div class="recipeDetailBox1">
			<h2>Nutrition:</h2>
			<hr>
			<c:forEach var="nutrient" items="${nutrientList}">
				<div class="nutrientItem">
					<p><c:out value="${nutrient.name}"/>: </p>
					<p><c:out value="${nutrient.amount}"/><c:out value="${nutrient.unit}"/> / <c:out value="${nutrient.percentOfDailyNeeds}"/>% DV</p>
				</div>
				<hr>
			</c:forEach>
		</div>
		<div class="recipeDetailBox2">
			<div class="ingredientBox">
				<h2 class="ingredientBoxTitle">Ingredients:</h2>
				<hr>
				<c:forEach var="ingredient" items="${ingredientList}">
					<div class="ingredientItem">
						<p><c:out value="${ingredient.name.substring(0, 1).toUpperCase() += ingredient.name.substring(1)}"/>: </p>
						<p><c:out value="${ingredient.amount.toString().replace('.0', '')}"/> <c:out value="${ingredient.unit}"/></p>
					</div>
					<hr>
				</c:forEach>
			</div>
			<div class="statBox">
				<h2 class="statBoxTitle">Details:</h2>
				<hr>
				<div class="statItem">
					<p>Prep. Time: </p>
					<p><c:out value="${preparationMinutes}"/> minutes</p>
				</div>
				<div class="statItem">
					<p>Cook Time: </p>
					<p><c:out value="${cookingMinutes}"/> minutes</p>
				</div>
				<div class="statItem">
					<p>Ready In: </p>
					<p><c:out value="${readyInMinutes}"/> minutes</p>
				</div>
				<hr>
				<div class="statItem">
					<p>Vegetarian: </p>
					<c:choose>
						<c:when test="${vegetarian != 'false'}">
							<img src="/img/true.png" />
						</c:when>
						<c:otherwise>
							<img src="/img/false.png" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="statItem">
					<p>Vegan: </p>
					<c:choose>
						<c:when test="${vegan != 'false'}">
							<img src="/img/true.png" />
						</c:when>
						<c:otherwise>
							<img src="/img/false.png" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="statItem">
					<p>Gluten Free: </p>
					<c:choose>
						<c:when test="${glutenFree != 'false'}">
							<img src="/img/true.png" />
						</c:when>
						<c:otherwise>
							<img src="/img/false.png" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="statItem">
					<p>Dairy Free: </p>
					<c:choose>
						<c:when test="${dairyFree != 'false'}">
							<img src="/img/true.png" />
						</c:when>
						<c:otherwise>
							<img src="/img/false.png" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="statItem">
					<p>Cheap: </p>
					<c:choose>
						<c:when test="${cheap != 'false'}">
							<img src="/img/true.png" />
						</c:when>
						<c:otherwise>
							<img src="/img/false.png" />
						</c:otherwise>
					</c:choose>
				</div>
				<hr>
			</div>
			<div class="authorBox">
				<h2 class="authorBoxTitle">Author:</h2>
				<hr>
				<div class="statItem">
					<p>Name: </p>
					<c:choose>
						<c:when test="${sourceName == 'null'}">
							<p>Unknown</p>
						</c:when>
						<c:otherwise>
							<p><c:out value="${sourceName}"/></p>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="statItem">
					<p>Website: </p>
					<a href="<c:out value="${sourceUrl}"/>">Click Here</a>
				</div>
			</div>
		</div>
		<div class="recipeDetailBox3">
			<c:choose>
				<c:when test="${analyzedInstructions.size() != 0}">
					<h2>Instructions:</h2>
					<c:set var="steps" value="${0}"/>
					<c:forEach var="instruction" items="${analyzedInstructions}">
						<c:set var="steps" value="${steps + 1}" />
						<div class="instructionItem">
							<p class="recipeSteps" id="recipeSteps${steps}">Step <c:out value="${steps}"/></p>
							<p class="stepItem" id="stepItem${steps}"><c:out value="${instruction}"/></p>
							<input type="checkbox" onChange="strikethrough(${steps})" class="recipeCheckbox">
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<h2>Oops! The API does not have instructions for this recipe.</h2>
					<h2>Try the author's website in the sidebar.</h2>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<script type="text/javascript" src="/javascript/pantryScript.js" ></script>
</body>
</html>