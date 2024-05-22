function updateCardDetails(row) {
    const cells = row.getElementsByTagName('td');
    document.getElementById('card-id').innerText = 'Card ID: ' + cells[0].innerText;
    document.getElementById('card-name').innerText = 'Card Name: ' + cells[1].innerText;
    document.getElementById('card-price').innerText = 'Card Price: ' + cells[2].innerText;
    document.getElementById('buy-button').onclick = function() {
        alert('Bought ' + cells[0].innerText + ' for ' + cells[2].innerText);
    };
}

function getCardFromAPI() {
        fetch('http://localhost:8080/api/cards/on-sale')
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