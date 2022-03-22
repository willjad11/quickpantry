function editIngredient(id) {
    document.getElementById("ingredientName" + id).style.display = "none";
    document.getElementById("ingredientEditForm" + id).style.display = "";
}

function cancelEditIngredient(id) {
    document.getElementById("ingredientName" + id).style.display = "";
    document.getElementById("ingredientEditForm" + id).style.display = "none";
}

function strikethrough(step) {
	if (document.getElementById("recipeSteps" + step).style.textDecoration != "line-through") {
		document.getElementById("recipeSteps" + step).style.textDecoration = "line-through";
		document.getElementById("stepItem" + step).style.textDecoration = "line-through";
	}
	else {
		document.getElementById("recipeSteps" + step).style.textDecoration = "";
		document.getElementById("stepItem" + step).style.textDecoration = "";
	}
}

function addCategory() {
	document.getElementById("addCategoryFormCont").style.display = "";
	document.getElementById("addCategoryButton").style.display = "none";
}

function cancelAddCategory() {
		document.getElementById("addCategoryFormCont").style.display = "none";
		document.getElementById("addCategoryButton").style.display = "";
}

function displayAddRecipe(name) {
	let content = document.getElementById("categoryControlAddRecipeBox" + name).parentElement.parentElement.parentElement;
	content.style.maxHeight = 200 + "px";
	document.getElementById("categoryControlAddRecipeBox" + name).style.display = "";
	document.getElementById("categoryControlsButtonAddRecipe" + name).style.display = "none";
	document.getElementById("categoryControlsButtonRemoveRecipe" + name) != null && (document.getElementById("categoryControlsButtonRemoveRecipe" + name).style.display = "none");
	document.getElementById("categoryControlsButtonDeleteCategory" + name).style.display = "none";
	document.getElementById("categoryControlsButtonRenameCategory" + name).style.display = "none";
}

function displayRemoveRecipe(name) {
	let content = document.getElementById("categoryControlAddRecipeBox" + name).parentElement.parentElement.parentElement;
	content.style.maxHeight = 200 + "px";
	document.getElementById("categoryControlRemoveRecipeBox" + name).style.display = "";
	document.getElementById("categoryControlsButtonAddRecipe" + name).style.display = "none";
	document.getElementById("categoryControlsButtonRemoveRecipe" + name) != null && (document.getElementById("categoryControlsButtonRemoveRecipe" + name).style.display = "none");
	document.getElementById("categoryControlsButtonDeleteCategory" + name).style.display = "none";
	document.getElementById("categoryControlsButtonRenameCategory" + name).style.display = "none";
}

function displayRenameCategory(name) {
	let content = document.getElementById("categoryControlAddRecipeBox" + name).parentElement.parentElement.parentElement;
	content.style.maxHeight = 200 + "px";
	document.getElementById("categoryControlRenameCategoryBox" + name).style.display = "";
	document.getElementById("categoryControlsButtonAddRecipe" + name).style.display = "none";
	document.getElementById("categoryControlsButtonRemoveRecipe" + name) != null && (document.getElementById("categoryControlsButtonRemoveRecipe" + name).style.display = "none");
	document.getElementById("categoryControlsButtonDeleteCategory" + name).style.display = "none";
	document.getElementById("categoryControlsButtonRenameCategory" + name).style.display = "none";
}

function displayDeleteCategory(name) {
	let content = document.getElementById("categoryControlAddRecipeBox" + name).parentElement.parentElement.parentElement;
	content.style.maxHeight = 200 + "px";
	document.getElementById("categoryControlDeleteCategoryBox" + name).style.display = "";
	document.getElementById("categoryControlsButtonAddRecipe" + name).style.display = "none";
	document.getElementById("categoryControlsButtonRemoveRecipe" + name) != null && (document.getElementById("categoryControlsButtonRemoveRecipe" + name).style.display = "none");
	document.getElementById("categoryControlsButtonDeleteCategory" + name).style.display = "none";
	document.getElementById("categoryControlsButtonRenameCategory" + name).style.display = "none";
}

function hideControls(name) {
	document.getElementById("categoryControlAddRecipeBox" + name).style.display = "none";
	document.getElementById("categoryControlRemoveRecipeBox" + name) != null && (document.getElementById("categoryControlRemoveRecipeBox" + name).style.display = "none");
	document.getElementById("categoryControlRenameCategoryBox" + name).style.display = "none";
	document.getElementById("categoryControlDeleteCategoryBox" + name).style.display = "none";
	document.getElementById("categoryControlsButtonAddRecipe" + name).style.display = "";
	document.getElementById("categoryControlsButtonRemoveRecipe" + name) != null && (document.getElementById("categoryControlsButtonRemoveRecipe" + name).style.display = "");
	document.getElementById("categoryControlsButtonDeleteCategory" + name).style.display = "";
	document.getElementById("categoryControlsButtonRenameCategory" + name).style.display = "";
}

var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.maxHeight){
      content.style.maxHeight = null;
    }
    else {
      content.style.maxHeight = content.scrollHeight + "px";
    }
  });
}