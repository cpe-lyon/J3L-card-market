document.addEventListener('DOMContentLoaded', function () {
    const cardForm = document.getElementById('card-form');

    cardForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const card = {
            "name": document.getElementById('name').value,
            "description": document.getElementById('description').value,
            "family": document.getElementById('family').value,
            "affinity": document.getElementById('affinity').value,
            "imgUrl": document.getElementById('img-url').value,
            "smallImgUrl": document.getElementById('small-img-url').value,
            "hp": document.getElementById('hp').value,
            "energy": document.getElementById('energy').value,
            "defence": document.getElementById('defense').value,
            "attack": document.getElementById('attack').value,
            "price": document.getElementById('price').value,
        };

        createCard(card);
    });
});

function createCard(card) {
    const POST_CARD_URL = "http://localhost:8080/card";
    let context = { method: 'POST', body: JSON.stringify(card), headers: { 'Content-Type': 'application/json' } };

    fetch(POST_CARD_URL, context)
        .then(response => response.json())
        .catch(error => err_callback(error))
        .then(() => window.location.href = "cards")
}

function err_callback(error) {
    console.log(error);
    alert("Erreur submit");
}