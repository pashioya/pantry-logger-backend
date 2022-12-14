$(".js-example-placeholder-multiple").select2({
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