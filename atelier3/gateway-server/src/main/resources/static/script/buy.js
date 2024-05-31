function updateCardDetails(row, cardId) {
    document.querySelectorAll(".card-details").forEach(details => details.style.visibility = "inherit")
    const cells = row.getElementsByTagName('td');
    document.getElementById('card-image').src = cells[3].querySelector("img").src;
    document.getElementById('card-name').innerText = 'Card Name: ' + cells[1].innerText;
    document.getElementById('card-price').innerText = 'Card Price: ' + cells[2].innerText;
    document.getElementById('buy-button').onclick = function() {
        buyCard(cardId);
    };
}

function buyCard(cardId) {
    fetch(`/api/orchestrate/buy/${cardId}`, {
        method: 'POST',
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
        fetch('/api/usercards/on-sale', {
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
                fetch(`/api/cards/${userCard.cardId}`, {
                    method: 'GET'
                })
                    .then(response => response.json())
                    .then(card => {
                        const idCell = row.insertCell();
                        idCell.innerText = userCard.id;
                        const nameCell = row.insertCell();
                        nameCell.innerText = card.name;
                        const priceCell = row.insertCell();
                        priceCell.innerText = userCard.price;
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

fetch(`/api/users/balance`, {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json',
        ...window.authHeader
    }
}).then(response => response.json())
    .then(i => {
       if (i === -1) i = "ERROR";
       document.querySelector("#balance").innerHTML = `${i}`;
    });


getCardFromAPI();