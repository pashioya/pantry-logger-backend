let button_images = document.getElementById("image-view-button");
let button_list = document.getElementById("list-view-button");
let item_list = document.querySelectorAll(".item");
let recipe_list = document.querySelectorAll(".recipe");
let favorites_list = document.querySelectorAll(".favorites-button");
let add_section_list = document.querySelectorAll(".add-section");

if (button_images) {
    button_images.onclick = function () {
        change_look_property("image");
    };
}

if (button_list) {
    button_list.onclick = function () {
        change_look_property("list");
    };
}
if (item_list) {
    item_list.forEach((element) => {
        element.onclick = select_item;
    });
}
if (favorites_list) {
    favorites_list.forEach((element) => {
        element.querySelector(".icon").onclick = change_favorite_button;
    });
}

function change_favorite_button() {
    let button = this;
    if (button.src.includes("deselected-favorite-icon.png")) {
        button.src = "/icons/selected-favorite-icon.png";
    } else {
        button.src = "/icons/deselected-favorite-icon.png";
    }
}

function change_look_property(property) {
    if (item_list) {
        item_list.forEach((element) => {
            element.setAttribute("look", property);
        });
    }
    if (recipe_list) {
        recipe_list.forEach((element) => {
            element.setAttribute("look", property);
        });
    }
}

let was_images_before = false;
function select_item() {
    if (
        document.querySelector("body").getAttribute("item-selected") === "false"
    ) {
        was_images_before = this.getAttribute("look") === "image";
        this.setAttribute("change", "selected");
        document.querySelector("body").setAttribute("item-selected", "true");
        change_look_property("list");
        this.onclick = null;
        const boxes = this.getElementsByClassName("remove-percentage")
        for(let i = (boxes.length-1); i >= 0; i--) {
            boxes[i].addEventListener("mouseover", function () {
                Array.from(boxes).forEach(x => x.classList.remove("remove_hover"))
                for (let j = i; j < boxes.length; j++){
                    boxes[j].classList.add("remove_hover")
                }
            })
        }
    }
}

function de_select_item() {
    if (
        document.querySelector("body").getAttribute("item-selected") === "true"
    ) {
        if (was_images_before) {
            change_look_property("image");
            was_images_before = false;
        }

        const current = document.querySelector(".item[change='selected']");
        if (current.getAttribute("product-size") != "1") {
            current
                .querySelector(".modify-container")
                .setAttribute("modify-mode", "remove");
        }


        let current_item = document.querySelector(".item[change='selected']");
        current_item.setAttribute("change", "none");
        document.querySelector("body").setAttribute("item-selected", "false");
        setTimeout(function () {
            current_item.onclick = select_item;
        }, 50);
    }
}

function add_to_item() {
    document
        .querySelector(".item[change='selected']")
        .querySelector(".modify-container")
        .setAttribute("modify-mode", "add");
}

function back_button() {
    document
        .querySelector(".item[change='selected']")
        .querySelector(".modify-container")
        .setAttribute("modify-mode", "remove");
}

if (add_section_list) {
    add_section_list.forEach(element => {
        const remove = element.getElementsByClassName("amount-remove")[0];
        const add = element.getElementsByClassName("amount-add")[0];
        const multiply = element.getElementsByClassName("multiply-amount")[0];
        const show_amount = element.getElementsByClassName("show-amount-val")[0];

        function addAmount(num) {
            show_amount.value = parseInt(show_amount.value) + num;
        }

        add.onclick = function () {
            addAmount(1);
        }
        remove.onclick = function () {
            if (show_amount.value > 0) {
                addAmount(-1);
            }
        }
        multiply.onclick = function () {
            addAmount(parseInt(show_amount.value));
        }
    });
}

if (item_list) {
    item_list.forEach((element) => {
        if (element.getAttribute("product-size") == "1") {
            element.getElementsByClassName("modify-container")[0].setAttribute("modify-mode", "add")
        }
        element.onclick = select_item;
        element.getElementsByClassName("exit-button")[0].onclick = de_select_item;
        element.getElementsByClassName("change-to-add-button")[0].onclick = add_to_item;
        element.getElementsByClassName("back-button")[0].onclick = back_button;
    });
}

// Scanner Page JS
const itemscan_back = document.getElementById("product-scan-back");
const item_continue = document.getElementById("product-continue");


if (itemscan_back) {
    itemscan_back.onclick = function() {
        document.getElementsByTagName("body")[0].setAttribute("stage", "scan");
        console.log("back")
    }
}

if (item_continue) {
    item_continue.onclick = function() {
        document.getElementsByTagName("body")[0].setAttribute("stage", "two");
    }
}


let spaces = document.getElementById("scanner");
if (spaces) {
    spaces = spaces.getElementsByClassName("storage-space")
    Array.from(spaces).forEach(x => {
        x.querySelector("input").addEventListener("change", function () {
            Array.from(spaces).forEach(y => {
                y.setAttribute("selected", "false");
                if (y.querySelector("input").checked) {
                    y.setAttribute("selected", "true")
                }
            })
        })
    })
}

/* Recommender JS */

const have = document.getElementsByClassName("ingredients-you-have");
const need = document.getElementsByClassName("ingredients-you-need");
const max_list_length = 5;

function remove_list_items(list, max_length) {
    Array.from(list).forEach(list => {
        const elements = list.querySelectorAll("li");
        if (elements.length > max_length) {
            for(let i = max_length; i < elements.length; i++) {
                elements[i].classList.add("hidden");
            }
            list.querySelector(".dots").classList.remove("hidden");
        }
    })
}

if (have.length > 0 && need.length > 0) {
    remove_list_items(have, max_list_length);
    remove_list_items(need, max_list_length);
}

