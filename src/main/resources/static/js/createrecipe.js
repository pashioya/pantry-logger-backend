const addCookingStepsButton = document.getElementById("add-cooking-step");

const addIngredientButton = document.getElementById("add-ingredient");
const addIngredientName = document.getElementById("ingredient-name");
const addIngredientAmount = document.getElementById("ingredient-amount");
const addedIngredients = document.getElementById("added-ingredients");

addCookingStepsButton.onclick =  function () {
    const newCookingStep = document.createElement("input");
    newCookingStep.setAttribute("type", "text");
    newCookingStep.setAttribute("class", "cooking-step");
    newCookingStep.setAttribute("name", "cooking-step");
    document.getElementById("cooking-steps").appendChild(newCookingStep);
}


addIngredientButton.onclick = function () {
    const newIngredient = document.createElement("div");

    const newIngredientName = addIngredientName.cloneNode(true);
    newIngredientName.setAttribute("name", "ingredient-types");
    newIngredientName.value = addIngredientName.value;

    const newIngredientAmount = document.createElement("input");
    newIngredientAmount.setAttribute("name", "ingredient-amounts");
    newIngredientAmount.setAttribute("type", "number");
    newIngredientAmount.setAttribute("required", "");
    newIngredientAmount.value = addIngredientAmount.value;
    newIngredientAmount.innerText = addIngredientAmount.innerText

    const newIngredientDelete = document.createElement("input");
    newIngredientDelete.setAttribute("value", "delete")
    newIngredientDelete.setAttribute("type", "button");
    newIngredientDelete.onclick = function () {
        newIngredient.remove();
    }

    newIngredient.append(newIngredientName, newIngredientAmount, newIngredientDelete);
    addedIngredients.appendChild(newIngredient);
}