function updateCardDetails(row) {
    const cells = row.getElementsByTagName('td');
    document.getElementById('card-id').innerText = 'Card ID: ' + cells[0].innerText;
    document.getElementById('card-name').innerText = 'Card Name: ' + cells[1].innerText;
    document.getElementById('sell-button').onclick = function() {
        sellCard();
    };
}

function addCard() {
    const name = document.getElementById('create-card-name').value;
    fetch('http://localhost:8080/api/cards', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: name })
    })
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById('card-table');
            const row = table.insertRow();
            row.onclick = function() {
                updateCardDetails(row);
            };
            const idCell = row.insertCell();
            idCell.innerText = data.id;
            const nameCell = row.insertCell();
            nameCell.innerText = data.name;
        });
}

function sellCard() {
    const cardId = parseInt(document.getElementById('card-id').innerText.split(' ')[2]);
    const price = parseInt(document.getElementById('card-price').value);
    fetch('http://localhost:8080/api/cards/sell/' + cardId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ price: price })
    })
        .then(response => response.json())
        .then(data => {
            alert('Card in sell for ' + data.price + ' gold');
            document.getElementById('card-price').value = '';
        });
}

function getCardFromAPI() {
    fetch('http://localhost:8080/api/cards/owned')
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById('card-table');
            data.forEach(userCard => {
                const row = table.insertRow();
                row.onclick = function() {
                    updateCardDetails(row);
                };
                const idCell = row.insertCell();
                idCell.innerText = userCard.id;
                const nameCell = row.insertCell();
                nameCell.innerText = userCard.card.name;
            });
        });
}

getCardFromAPI();