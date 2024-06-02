function getRoomFromAPI() {
    const roomId = getRoomId();

    fetch(`/api/game-rooms/${roomId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('title').innerText = data.name;
            if (data?.winnerSurname !== null && data?.winnerSurname !== undefined) {
                alert(`Winner: ${data.winnerSurname}`);
                location.href = '/game';
            }
        })
        .catch(() => {
            location.href = '/game';
        })
}

function getCardsFromAPI() {
    fetch('/api/usercards/owned', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        }
    })
        .then(response => response.json())
        .then(userCards => {
            console.log(userCards);
            const table = document.getElementById('playable-card-table');
            userCards.forEach(userCard => {
                console.log(userCard);
                const row = table.insertRow();
                row.onclick = function() {
                    selectCard(row, userCard.cardId);
                }

                fetch(`/api/cards/${userCard.cardId}`, {
                    method: 'GET'
                })
                    .then(response => response.json())
                    .then(card => {
                        const idCell = row.insertCell();
                        idCell.innerText = userCard.id;
                        const nameCell = row.insertCell();
                        nameCell.innerText = card.name;
                        const imageCell = row.insertCell();
                        if (card.imageUrl !== null) {
                            const image = document.createElement('img');
                            image.src = card.imageUrl;
                            image.style.width = '120px';
                            imageCell.appendChild(image);
                        }
                    });
            });
        });
}

function playGame() {
    const roomId = getRoomId();

    fetch(`/api/game-rooms/${roomId}/play`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        }
    })
        .then(response => response.json())
        .then(data => {
            alert(`Winner: ${data.winnerSurname}`);
            location.href = '/game';
        });

}

function selectCard(row, cardId) {
    const roomId = getRoomId();
    fetch(`/api/game-rooms/${roomId}/select-card/${cardId}`, {
        method: 'POST',
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

getCardsFromAPI();

setInterval(function() {
    getRoomFromAPI();
}, 5000);