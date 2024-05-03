function generate(){

    const GET_CHUCK_URL="https://api.chucknorris.io/jokes/random"; 
    let context =   {
                        method: 'GET'
                    };
        
    fetch(GET_CHUCK_URL,context)
        .then(response => response.json())
            .then(response => callback(response))
            .catch(error => err_callback(error));
}

function callback(response){
    document.getElementById("txtChuck").innerHTML = response.value;
}

function err_callback(error){
    console.log(error);
}