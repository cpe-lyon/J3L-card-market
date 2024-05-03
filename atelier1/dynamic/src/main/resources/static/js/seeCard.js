function fetchData() {
    const id = window.location.search.replace('?','');
    let url = `http://tp.cpe.fr:8083/card/${id}`;
    return fetch(url).then(r => r.json());
}

fetchData()
  .then(card => {
    let template = document.querySelector("#selectedCard");
    let clone = document.importNode(template.content, true);

    newContent= clone.firstElementChild.innerHTML
                .replace(/{{family_src}}/g, card.smallImgUrl)
                .replace(/{{family_name}}/g, card.family)
                .replace(/{{img_src}}/g, card.imgUrl)
                .replace(/{{name}}/g, card.name)
                .replace(/{{description}}/g, card.description)
                .replace(/{{hp}}/g, card.hp)
                .replace(/{{energy}}/g, card.energy)
                .replace(/{{attack}}/g, card.attack)
                .replace(/{{defense}}/g, card.defence);
    clone.firstElementChild.innerHTML= newContent;

    let cardContainer= document.querySelector("#cardContainer");
    cardContainer.appendChild(clone);
})