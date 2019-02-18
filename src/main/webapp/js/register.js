function postRegister(){
    let data = {};
    document.querySelectorAll(".form-control").forEach(i =>{
        data[i.name] = i.value;
    });

    var sex = document.getElementsByName('sex');
    data.sex = sex[0].checked ? "male" : "female";
    
    console.log(data);
}
    
    
document.getElementById("submit").addEventListener("click", postRegister);