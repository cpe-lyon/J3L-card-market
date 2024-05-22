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
    const cardId = document.getElementById('card-id').innerText;
    const price = document.getElementById('card-price').value;
    alert('Sold ' + cardId + ' for ' + price);
}

function getCardFromAPI() {
    fetch('http://localhost:8080/api/cards')
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById('card-table');
            data.forEach(card => {
                const row = table.insertRow();
                row.onclick = function() {
                    updateCardDetails(row);
                };
                const idCell = row.insertCell();
                idCell.innerText = card.id;
                const nameCell = row.insertCell();
                nameCell.innerText = card.name;
            });
        });
}

getCardFromAPI();