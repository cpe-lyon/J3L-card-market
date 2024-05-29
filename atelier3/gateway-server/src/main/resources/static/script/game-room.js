function getRoomFromAPI() {
    const pathname = window.location.pathname;
    const parts = pathname.split('/');
    const roomId = parts[2];

    fetch(`/api/game-rooms/${roomId}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            document.getElementById('title').innerText = data.name;
        });
}

getRoomFromAPI();