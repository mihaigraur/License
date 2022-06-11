function validate(){
    if(grecaptcha.getResponse()){
        return true;
    }else{
        alert("Please approve that you are not a robot!");
        return false;
    }
}

function passwordValidation(){
    let password = document.getElementById('password');
    let confirmPassword = document.getElementById('confirmPassword');
    
    if(password.value !== confirmPassword.value){
        document.getElementById('ul').style.display = 'block';
        
        // password.setCustomValidity("Passwords don t match!");
        confirmPassword.setCustomValidity('Passwords don t match!');
    }else{
        document.getElementById('ul').style.display = 'none';
        confirmPassword.setCustomValidity("");
        password.setCustomValidity("");
    }
}