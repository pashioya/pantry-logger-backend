$(".js-example-placeholder-multiple").select2({
    placeholder: "Select your tags"
});

const edit_button = document.getElementById("edit-button");

if (edit_button) {
    edit_button.onclick = function () {
        const form = document.getElementById("tag-edit-form")
        Array.from(form.elements).forEach(x => {
            x.disabled = !x.disabled;
        })
    }
}