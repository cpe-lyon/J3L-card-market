function updateCardDetails(row, cardId) {
    const cells = row.getElementsByTagName('td');
    console.log(document.getElementById('card-image').outerHTML)
    console.log(cells[2].innerHTML);
    document.getElementById('card-image').outerHTML = cells[2].innerHTML;
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
    fetch('/api/cards/', {
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
    fetch(`/api/market/user-cards/${cardId}/sell`, {
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
    fetch(`/api/market/owned`, {
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
                const idCell = row.insertCell();
                idCell.innerText = userCard.id;
                const nameCell = row.insertCell();
                nameCell.innerText = userCard.card.name;
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
    fetch(`/api/cards/${cardsSelect.value}/usercard`, {
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