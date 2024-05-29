//tbody
let transactionsList = document.querySelector("#transactionsList");
let htmlTemplateElement = transactionsList.querySelector("template");

function add(transaction){
    const transactionRow = htmlTemplateElement.content.cloneNode(true);

    transactionRow.querySelectorAll('td').forEach((td, i) => {
        td.textContent = Object.values(transaction)[i];
    });

    transactionsList.appendChild(transactionRow);
}


fetch("/api/market/transactions", {redirect: "manual", headers: window.authHeader})
    .then(window.loginRedirectHandler)
    .then(res => {
        if(res.status === 403){
            alert("You must be administrator.");
            window.location.href = "/";
            throw "Forbidden";
        }
        return res;
    })
    .then(res => res.json())
    .then(transactions => {
        transactions.forEach(t => add(t));
    })