function postRegister(){
    let data = {};
    document.querySelectorAll(".form-control").forEach(i =>{
        data[i.name] = i.value;
    });

    var sex = document.getElementsByName('sex');
    data.sex = sex[0].checked ? true : false;
    
    console.log(data);

    fetch("/register", {
        method:"POST", 
        body:JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }}).then((resp) => resp.json()).then((data) => console.log(data));
}

    
document.getElementById("submit").addEventListener("click", postRegister);