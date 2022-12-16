const addCookingStepsButton = document.getElementById("add-cooking-step");

const addIngredientButton = document.getElementById("add-ingredient");
const addIngredientName = document.getElementById("ingredient-selector").cloneNode(true);
const addIngredientAmount = document.getElementById("ingredient-amount");
const addedIngredients = document.getElementById("added-ingredients");

const addTagButton = document.getElementById("add-tag");
const addTagName = document.getElementById("tag-name");
const addedTags = document.getElementById("added-tags");

addCookingStepsButton.onclick =  function () {
    const newCookingStep = document.createElement("textarea");
    newCookingStep.setAttribute("type", "text");
    newCookingStep.setAttribute("class", "cooking-step");
    newCookingStep.setAttribute("name", "cooking-step");
    document.getElementById("cooking-steps").appendChild(newCookingStep);
    document.getElementById("cooking-steps").appendChild(document.createElement("br"));
}

addIngredientButton.onclick = function () {
    const parent = document.createElement("li");
    const newType = addIngredientName.cloneNode(true);
    newType.removeAttribute("id");
    newType.setAttribute("name", "ingredient-types");
    newType.setAttribute("id", "newType");
    newType.classList.add("addedIngr")
    parent.appendChild(newType);


    const newAmount = addIngredientAmount.cloneNode(true);
    newAmount.removeAttribute("id");
    newAmount.setAttribute("name", "ingredient-amounts");
    parent.appendChild(newAmount);

    const newButton = document.createElement("button");
    newButton.setAttribute("type", "button");
    newButton.classList.add("button-dark")
    newButton.innerText = "remove";
    parent.appendChild(newButton);
    newButton.onclick = function () {
        parent.remove();
    }

    addedIngredients.appendChild(parent);
    ingredientSelect($(".addedIngr"));
    $("#newType").val(document.getElementById("ingredient-selector").value);
    $("#newType").trigger('change');
    newType.removeAttribute("id");
}

function ingredientSelect(jQuery_object) {
    $(document).ready(function() {
        jQuery_object.select2();
    });
}

ingredientSelect($("#ingredient-selector"));

$(document).ready(function() {
    $("#tag-selector").select2();
});

// $('.newNode').val(addIngredientName.value);
// $('.newNode').trigger('change');