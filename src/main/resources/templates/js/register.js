const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

function Start(){
    $('.registr_or_login').css({"display": "flex"}, {"justify-content": "center"});
    $('.container').css({"display": "block" });
    $('.main-content').css({"display": "none"});
    $('.align-footer').css({"display":"none"});
    $('.align-of-main-container').css({"height":"0vh"});
}

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});


$(document).on('click', '.right-side-header a', ()=>{
    $('.container').css({"display": "none" });
    $('.main-content').css({"display": "flex", "height":"80vh"});
    $('.align-footer').css({"display":"flex"});    
});

function showPass() {
    $(this).toggleClass("fa-eye fa-eye-slash");
    let $pass = $(".pass_input");
    $('.fa-eye-slash').hide();
    if ($pass.prop('type') === 'password') {
        $pass.prop('type', 'text');
        $('.fa-eye').show();
        $('.fa-eye-slash').hide();
    } else {
        $pass.prop('type', 'password');
        $('.fa-eye-slash').show();
        $('.fa-eye').hide();
    }
}

