function updateCardDetails(row) {
    const cells = row.getElementsByTagName('td');
    document.getElementById('card-name').innerText = 'Card Name: ' + cells[0].innerText;
    document.getElementById('card-description').innerText = 'Description: ' + cells[1].innerText;
    document.getElementById('card-price').innerText = 'Price: ' + cells[2].innerText;
    document.getElementById('sell-button').onclick = function() {
        alert('Sold ' + cells[0].innerText + ' for ' + cells[2].innerText);
        // Add your sell logic here
    };
}