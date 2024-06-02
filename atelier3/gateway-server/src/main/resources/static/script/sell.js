function updateCardDetails(row, cardId) {
    document.querySelectorAll(".card-details").forEach(details => details.style.visibility = "inherit")
    const cells = row.getElementsByTagName('td');
    console.log(document.getElementById('card-image').outerHTML)
    document.getElementById('card-image').src = cells[2].querySelector("img").src;
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
    const imageUrl = document.getElementById('create-card-image-url').value;
    fetch('/api/cards', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        },
        body: JSON.stringify({ name, imageUrl })
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
            const imageCell = row.insertCell();
            if (data.imageUrl !== null) {
                const image = document.createElement('img');
                image.src = data.imageUrl;
                image.style.width = '120px';
                imageCell.appendChild(image);
            }
        });
    refreshCards();
}

function sellCard(cardId) {
    const price = parseInt(document.getElementById('card-price').value);
    if (price <= 0 || isNaN(price)) {
        document.getElementById('card-price').value = '';
        document.getElementById('card-price').focus();
        return;
    }
    fetch(`/api/usercards/${cardId}/sell`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        },
        body: JSON.stringify({ price: price })
    })
        .then(data => {
            location.reload();
            alert('Card ' + data.card.name + ' has been put on sale for ' + data.price + '$');
            document.getElementById('card-price').value = '';
        });
}

function getCardFromAPI() {
    fetch(`/api/usercards/owned`, {
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
                if (!!userCard.price) {
                    row.style.backgroundColor = '#d3d3d3';
                } else {
                    row.onclick = function() {
                        updateCardDetails(row, userCard.id);
                    };
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

function refreshCards(){
    const cardsSelect = document.querySelector("#cardsSelect");
    cardsSelect.innerHTML = "";
    fetch(`/api/cards`, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(cards => {
            cards.forEach(({id, name}) =>
                cardsSelect.appendChild(
                    $(`<option value="${id}">${name}</option>`)[0]
                ))
        });
}

function buildUserCard(){
    /**
     *
     * @type {HTMLSelectElement}
     */
    const cardsSelect = document.querySelector("#cardsSelect");
    fetch(`/api/usercards/${cardsSelect.value}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...window.authHeader
        }
    })
        .then(res => {
            if (res.ok){
                alert("You have a new card")
            }
        })
}

getCardFromAPI();
refreshCards();