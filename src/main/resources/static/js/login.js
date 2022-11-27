const username = document.getElementById("username");
const password = document.getElementById("password");

const submit = document.getElementById("submit");

let status = {
    "username": false,
    "password": false
}

function updateSubmit() {
    submit.disabled = !(status.username && status.password)
}

username.onkeyup = function () {
    status.username = username.value.length > 0;
    updateSubmit()
}

password.onkeyup = function () {
    status.password = password.value.length > 0;
    updateSubmit()
}

updateSubmit()