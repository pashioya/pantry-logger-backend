// get the add-cooking-steps button
var addCookingStepsButton = document.getElementById("add-cooking-step");

// when clicked adds a new span element to the cooking-steps div
addCookingStepsButton.addEventListener("click", function () {
    // newCookingStep is a span element
    var newCookingStep = document.createElement("span");
    newCookingStep.innerHTML = '<input class="new-cooking-step" type="text" />';
    document.getElementById("cooking-steps").appendChild(newCookingStep);
});

//
