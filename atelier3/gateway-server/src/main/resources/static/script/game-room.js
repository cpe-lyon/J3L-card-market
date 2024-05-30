function getRoomFromAPI() {
    const roomId = getRoomId();

    fetch(`/api/game-rooms/${roomId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('title').innerText = data.name;
        })
        .catch(() => {
            location.href = '/game';
        })
}

function getCardFromAPI() {
    fetch('/api/usercards/owned', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        }
    })
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById('playable-card-table');
            data.forEach(userCard => {
                const row = table.insertRow();
                row.onclick = function() {
                    selectCard(row, userCard.id);
                };
                const idCell = row.insertCell();
                idCell.innerText = userCard.card.id;
                const nameCell = row.insertCell();
                nameCell.innerText = userCard.card.name;
                const priceCell = row.insertCell();
                priceCell.innerText = `${userCard.price}$`;
                const imageCell = row.insertCell();
                if (userCard.card.imageUrl !== null) {
                    const image = document.createElement('img');
                    image.src = userCard.card.imageUrl;
                    image.style.width = '120px';
                    imageCell.appendChild(image);
                }
            });
        });
}

function playGame() {
    const roomId = getRoomId();

    fetch(`/api/game-rooms/${roomId}/play`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
        });

}

function selectCard(row, cardId) {
    const roomId = getRoomId();
    fetch(`/api/game-rooms/${roomId}/select-card/${cardId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        },
    })
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById('playable-card-table');
            for (let i = 1; i < table.rows.length; i++) {
                table.rows[i].style.backgroundColor = 'white';
            }
            row.style.backgroundColor = '#d3d3d3';
        });
}

function getRoomId() {
    const pathname = window.location.pathname;
    const parts = pathname.split('/');
    return parts[2];
}

document.getElementById('play-button').onclick = function() {
    playGame();
};

getRoomFromAPI();
getCardFromAPI();