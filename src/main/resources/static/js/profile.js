$(".js-multiple").select2({
    placeholder: "Select your tags"
});

const likes_form = document.getElementById("like-edit-form");
const edit_likes = document.getElementById("edit-likes");
const dislikes_form = document.getElementById("dislike-edit-form");
const edit_dislikes = document.getElementById("edit-dislikes");

function updateStatus(target, status) {
    const statuses = ["none", "edit"];
    if (!statuses.includes(status)) {
        console.error("Not a valid status!")
    }
    target.setAttribute("status", status);
    if (status === statuses[0]) {
        target.querySelector("select").disabled = true;
    } else if(status === statuses[1]) {
        target.querySelector("select").disabled = false;
    }
}

if(edit_likes) {
    edit_likes.onclick = function() {
        updateStatus(likes_form, "edit");
    }
}

if (edit_dislikes) {
    edit_dislikes.onclick = function() {
        updateStatus(dislikes_form, "edit");
    }
}

if (likes_form) {
    updateStatus(likes_form, "none");
}

if (dislikes_form) {
    updateStatus(dislikes_form, "none");
}



let modal = document.getElementsByClassName("modal");
let passwordModal = document.getElementById("password-modal");
let addSensorBoxModal = document.getElementById("add-sensor-box-modal");
let editSensorBoxModal = document.getElementsByClassName("sensor-box-editor-modal");

let changePassBtn = document.getElementById("change-password-btn");
let addSensorBoxBtn = document.getElementById("add-sensor-box-btn");
let sensorBox = document.getElementsByClassName("sensor-box");

let closeBtn = document.getElementsByClassName("close");

if(modal) {
    if (changePassBtn) {
        changePassBtn.onclick = function () {
            passwordModal.style.display = "block";
        }
    }

    if (addSensorBoxBtn) {
        addSensorBoxBtn.onclick = function () {
            addSensorBoxModal.style.display = "block";
        }
    }

    if (sensorBox) {
        for (let i = 0; i < sensorBox.length; i++) {
            sensorBox[i].onclick = function () {
                editSensorBoxModal[i].style.display = "block";
            }
        }
    }


    if (closeBtn) {
        for (let i = 0; i < closeBtn.length; i++) {
            closeBtn[i].onclick = function () {
                for(let j = 0; j < modal.length; j++) {
                    modal[j].removeAttribute("style");
                    modal[j].setAttribute("style", "display: none;")
                }
            }
        }
    }


    window.onclick = function (event) {
        for(let i = 0; i < modal.length; i++) {
            if (event.target === modal[i]) {
                modal[i].style.display = "none";
            }
        }
    }
}

let selectPantryZone = document.getElementById("select-pantry-zone");

// if select pantry zone is empty disable submit button
if(selectPantryZone) {
    if(selectPantryZone.options.length === 1) {
        document.getElementById("select-pantry-zone").disabled = true;
        document.getElementById("submit-sensor-box").disabled = true;
        document.getElementById("no-zones-error").style.display = "block";
    }
}
