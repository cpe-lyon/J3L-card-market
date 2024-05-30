function createRoom() {
    const name = document.getElementById('room-name').value;
    if (name === '') {
        alert('Room name cannot be empty');
        return;
    }
    fetch('/api/game-rooms', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        },
        body: JSON.stringify({ name })
    })
        .then(response => response.json())
        .then(data => {
            joinRoom(data.id);
        });
}

function getRoomsFromAPI() {
    const table = document.getElementById('room-table');
    while (table.rows.length > 1) {
        table.deleteRow(1);
    }

    fetch('/api/game-rooms', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        }
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
                playersCell.innerText = `${gameRoom.playersCount}/2`;
            });
        });
}

function joinRoom(roomId) {
    fetch(`/api/game-rooms/${roomId}/join`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        }
    })
        .then(response => response.json())
        .then(data => {
            location.href = `/game-room/${roomId}`;
        });
}

document.getElementById('creation-button').onclick = function() {
    createRoom();
};

getRoomsFromAPI();