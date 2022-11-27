const password = document.getElementById("password");
const confirm_password = document.getElementById("confirm-password");
const submit = document.getElementById("register-submit");
const username = document.getElementById("username");
const email = document.getElementById("email")

let status = {
    "email": false,
    "username": false,
    "password_equality": false
}

function updateSubmit() {
    submit.disabled = !(status.email && status.username && status.password_equality);
}

function onChange() {
    status.password_equality = password.value === confirm_password.value;
    updateSubmit()
}

password.onkeyup = onChange;
confirm_password.onkeyup = onChange;



username.onkeyup = function() {
    $.ajax({
        url: "/checkUsername?username=" + username.value,
        type: "GET",
        success: function (data) {
            //TODO add username existance popup
            status.username = !data;
            updateSubmit();
        },
        error: function (data) {
            console.log("ERROR: Username search failed")
        }
    })
}

email.onkeyup = function() {
    $.ajax({
        url: "/checkEmail?email=" + email.value,
        type: "GET",
        success: function (data) {
            //TODO add email existance popup
            status.email = !data;
            updateSubmit();
        },
        error: function (data) {
            console.log("ERROR: Username search failed")
        }
    })
}

updateSubmit();