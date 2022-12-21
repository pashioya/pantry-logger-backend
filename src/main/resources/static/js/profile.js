$(".js-multiple").select2({
    placeholder: "Select your tags"
});

const edit_button = document.getElementById("edit-button");
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

edit_likes.onclick = function() {
    updateStatus(likes_form, "edit");
}

edit_dislikes.onclick = function() {
    updateStatus(dislikes_form, "edit");
}

updateStatus(likes_form, "none")
updateStatus(dislikes_form, "none")



let modal = document.getElementsByClassName("modal");
let passwordModal = document.getElementById("password-modal");
let editProfileModal = document.getElementById("edit-profile-modal");
let addSensorBoxModal = document.getElementById("add-sensor-box-modal");
let editSensorBoxModal = document.getElementById("sensor-box-editor-modal");

let editProfileBtn = document.getElementById("edit-profile-btn");
let changePassBtn = document.getElementById("change-password-btn");
let addSensorBoxBtn = document.getElementById("add-sensor-box-btn");
let sensorBox = document.getElementsByClassName("sensor-box");

let closeBtn = document.getElementsByClassName("close");

if(modal) {
    changePassBtn.onclick = function () {
        passwordModal.style.display = "block";
    }

    editProfileBtn.onclick = function () {
        editProfileModal.style.display = "block";
    }

    addSensorBoxBtn.onclick = function () {
        addSensorBoxModal.style.display = "block";
    }

    for (let i = 0; i < sensorBox.length; i++) {
        sensorBox[i].onclick = function () {
            editSensorBoxModal.style.display = "block";
            let selectedId = sensorBox[i].getAttribute("sensor-box-code");
            let sensorBoxId = document.getElementById("sensor-id");
            sensorBoxId.value = selectedId;
        }
    }


    for (let i = 0; i < closeBtn.length; i++) {
        closeBtn[i].onclick = function () {
            passwordModal.style.display = "none";
            editProfileModal.style.display = "none";
            addSensorBoxModal.style.display = "none";
            editSensorBoxModal.style.display = "none";
        }
    }

    window.onclick = function (event) {
        if (event.target == passwordModal || event.target == editProfileModal) {
            passwordModal.style.display = "none";
            editProfileModal.style.display = "none";
        }
        if(event.target == addSensorBoxModal || event.target == editSensorBoxModal) {
            addSensorBoxModal.style.display = "none";
            editSensorBoxModal.style.display = "none";
        }
    }
}

let selectPantryZone = document.getElementById("select-pantry-zone");

// if select pantry zone is empty disable submit button
if(selectPantryZone) {
    if(selectPantryZone.options.length == 1) {
        document.getElementById("select-pantry-zone").disabled = true;
        document.getElementById("submit-sensor-box").disabled = true;
        document.getElementById("no-zones-error").style.display = "block";
    }
}
// console.log(sensorBox.getAttribute("sensor-box-code"));
// document.getElementById("sensor-box-code").value = 12345;