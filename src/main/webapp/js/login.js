function postLogin(){
    let data = {};
    document.querySelectorAll(".form-control").forEach( i => {
        data[i.name] = i.value; 
    });

    console.log(data);
}


document.getElementById("submit").addEventListener("click", postLogin);