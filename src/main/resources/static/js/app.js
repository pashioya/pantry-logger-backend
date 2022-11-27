let button_images = document.getElementById("image-view-button");
let button_list = document.getElementById("list-view-button");
let item_list = document.querySelectorAll(".item");
let was_images_before = false;

function change_look_property(property) {
    item_list.forEach(element => {
        element.setAttribute("look", property);
    });
}

if(button_images) {
    button_images.onclick = function() {
        change_look_property("image");
    }    
} 

if(button_list) {
    button_list.onclick = function() {
        change_look_property("list");
    }
}



function select_item() {
    if(document.querySelector("body").getAttribute("item-selected") == "false") {
        if(this.getAttribute("look") == "image") {
            was_images_before = true;
        } else {
            was_images_before = false;
        }
        this.setAttribute("change", "selected");
        document.querySelector("body").setAttribute("item-selected", "true");
        change_look_property("list");
        this.onclick = null;
    }
}

function de_select_item() {
    if(document.querySelector("body").getAttribute("item-selected") == "true") {
        if(was_images_before) {
            change_look_property("image");
            was_images_before = false;
        }
        document.querySelector(".item[change='selected']").querySelector(".modify-container").setAttribute("modify-mode", "remove");
        let current_item = document.querySelector(".item[change='selected']");
        current_item.setAttribute("change", "none");
        document.querySelector("body").setAttribute("item-selected", "false");
        setTimeout(function(){current_item.onclick = select_item;}, 50);
    }
}

function add_to_item() {
    document.querySelector(".item[change='selected']").querySelector(".modify-container").setAttribute("modify-mode",  "add")
}

function back_button() {
    document.querySelector(".item[change='selected']").querySelector(".modify-container").setAttribute("modify-mode", "remove")
}

if(item_list) {
    item_list.forEach(element => {
        element.onclick = select_item;
        element.getElementsByClassName("exit-button")[0].onclick = de_select_item;
        element.getElementsByClassName("change-to-add-button")[0].onclick = add_to_item;
        element.getElementsByClassName("back-button")[0].onclick = back_button;
    })    
}  

// Scanner Page JS
document.getElementById("itemscan-back").onclick = function() {
    document.getElementsByTagName("body")[0].setAttribute("stage", "scan");
    console.log("back")
}
document.getElementById("item-continue").onclick = function() {
    document.getElementsByTagName("body")[0].setAttribute("stage", "two");
    console.log(document.getElementById("item-name").value)
    console.log("continue")
}

const spaces = document.getElementById("scanner").getElementsByClassName("storage-space");
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