function postLogin(){
    let data = {};
    document.querySelectorAll(".form-control").forEach( i => {
        data[i.name] = i.value; 
    });

    console.log(data);

    fetch("/login", {
        method:"POST", 
        body:JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }}).then((resp) => resp.json()).then((data) => console.log(data));
}


document.getElementById("submit").addEventListener("click", postLogin);