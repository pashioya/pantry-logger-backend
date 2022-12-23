let modal = document.getElementsByClassName("modal");

let addPantryZoneModal = document.getElementById("add-pantry-zone-modal");

let addPantryZoneBtn = document.getElementById("add-pantry-zone-btn");

let closeBtn = document.getElementsByClassName("close");

if(modal) {
    addPantryZoneBtn.onclick = function () {
        addPantryZoneModal.style.display = "block";
    }

    for (let i = 0; i < closeBtn.length; i++) {
        closeBtn[i].onclick = function () {
            addPantryZoneModal.style.display = "none";
        }
    }

    window.onclick = function (event) {
        if(event.target == addPantryZoneModal) {
            addPantryZoneModal.style.display = "none";
        }
    }
}