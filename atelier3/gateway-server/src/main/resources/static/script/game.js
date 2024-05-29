function createRoom() {
    const name = document.getElementById('room-name').value;
    if (name === '') {
        alert('Room name cannot be empty');
        return;
    }
    fetch('/api/game-rooms', {
        method: 'POST',
        headers: window.authHeader,
        body: JSON.stringify({ name })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
        });
}

function getRoomsFromAPI() {
    const table = document.getElementById('room-table');
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }

    fetch('/api/game-rooms', {
        method: 'GET',
        headers: window.authHeader
    })
        .then(response => response.json())
        .then(data => {
            data.forEach(gameRoom => {
                const row = table.insertRow();
                row.onclick = function() {
                    joinRoom(gameRoom.id);
                };
                const nameCell = row.insertCell();
                nameCell.innerText = gameRoom.name;
                const playersCell = row.insertCell();
                playersCell.innerText = `${gameRoom.playersCount}$`;
            });
        });
}

function joinRoom(roomId) {
    fetch(`/api/game-rooms/${roomId}/join`, {
        method: 'POST',
        headers: window.authHeader
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
        });
}

document.getElementById('creation-button').onclick = function() {
    createRoom();
};

getRoomsFromAPI();