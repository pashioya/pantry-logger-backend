const allTextAreas = document.querySelectorAll("textarea");

if (allTextAreas) {
    Array.from(allTextAreas).forEach(x => {
        x.setAttribute("onkeyup", "textAreaAdjust(this)")
    })
}

function textAreaAdjust(element) {
    element.style.height = "1px";
    element.style.height = (25+element.scrollHeight)+"px";
}


const addCookingStepsButton = document.getElementById("add-cooking-step");

const addIngredientButton = document.getElementById("add-ingredient");
const addIngredientName = document.getElementById("ingredient-selector").cloneNode(true);
const addedIngredients = document.getElementById("added-ingredients");

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
    newType.classList.remove("hidden")
    newType.setAttribute("name", "ingredient-types");
    newType.setAttribute("id", "newType");
    newType.classList.add("addedIngr")
    parent.appendChild(newType);


    //const newAmount = addIngredientAmount.cloneNode(true);
    const newAmount = document.createElement("input");
    newAmount.setAttribute("class", "ingredient-amount");
    newAmount.setAttribute("type", "number");
    newAmount.setAttribute("value", "amount");
    newAmount.setAttribute("required", "");
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
    newType.removeAttribute("id");
}

function ingredientSelect(jQuery_object) {
    $(document).ready(function() {
        jQuery_object.select2();
    });
}

$(document).ready(function() {
    $("#tag-selector").select2();
});

// $('.newNode').val(addIngredientName.value);
// $('.newNode').trigger('change');