function updateCardDetails(row, cardId) {
    const cells = row.getElementsByTagName('td');
    document.getElementById('card-id').innerText = 'Card ID: ' + cells[0].innerText;
    document.getElementById('card-name').innerText = 'Card Name: ' + cells[1].innerText;
    document.getElementById('sell-button').onclick = function() {
        sellCard(cardId);
    };
}

function addCard() {
    const name = document.getElementById('create-card-name').value;
    if (name.trim() === undefined || name.trim() === '') {
        document.getElementById('create-card-name').focus();
        return;
    }
    fetch('http://localhost:8080/api/cards', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        },
        body: JSON.stringify({ name: name })
    })
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById('card-table');
            const row = table.insertRow();
            row.onclick = function() {
                updateCardDetails(row, data.id);
            };
            const idCell = row.insertCell();
            idCell.innerText = data.id;
            const nameCell = row.insertCell();
            nameCell.innerText = data.name;
        });
}

function sellCard(cardId) {
    const price = parseInt(document.getElementById('card-price').value);
    if (price <= 0 || isNaN(price)) {
        document.getElementById('card-price').value = '';
        document.getElementById('card-price').focus();
        return;
    }
    fetch('http://localhost:8080/api/cards/sell/' + cardId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        },
        body: JSON.stringify({ price: price })
    })
        .then(response => response.json())
        .then(data => {
            location.reload();
            alert('Card ' + data.card.name + ' has been put on sale for ' + data.price + '$');
            document.getElementById('card-price').value = '';
        });
}

function getCardFromAPI() {
    fetch('http://localhost:8080/api/cards/owned', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        }
    })
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById('card-table');
            data.forEach(userCard => {
                const row = table.insertRow();
                if (userCard.price !== null) {
                    row.style.backgroundColor = '#d3d3d3';
                } else {
                    row.onclick = function() {
                        updateCardDetails(row, userCard.id);
                    };
                }
                const idCell = row.insertCell();
                idCell.innerText = userCard.id;
                const nameCell = row.insertCell();
                nameCell.innerText = userCard.card.name;
            });
        });
}

getCardFromAPI();