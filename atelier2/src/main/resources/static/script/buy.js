function updateCardDetails(row, cardId) {
    const cells = row.getElementsByTagName('td');
    document.getElementById('card-name').innerText = 'Card Name: ' + cells[1].innerText;
    document.getElementById('card-price').innerText = 'Card Price: ' + cells[2].innerText;
    document.getElementById('buy-button').onclick = function() {
        buyCard(cardId);
    };
}

function buyCard(cardId) {
    fetch('http://localhost:8080/api/cards/buy/' + cardId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        }
    })
        .then(response => response.json())
        .then(data => {
            location.reload();
            alert('Bought ' + data.userCard.card.name + ' for ' + data.price + '$');
        });

}

function getCardFromAPI() {
        fetch('http://localhost:8080/api/cards/on-sale', {
            method: 'GET',
            headers: {
                ...window.authHeader
            }
        })
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById('card-table');
            data.forEach(userCard => {
                const row = table.insertRow();
                row.onclick = function() {
                    updateCardDetails(row, userCard.id);
                };
                const idCell = row.insertCell();
                idCell.innerText = userCard.card.id;
                const nameCell = row.insertCell();
                nameCell.innerText = userCard.card.name;
                const priceCell = row.insertCell();
                priceCell.innerText = userCard.price;
            });
        });
}

getCardFromAPI();