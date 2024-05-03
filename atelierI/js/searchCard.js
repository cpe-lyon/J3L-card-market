function fetchData() {
    return new Promise((resolve, reject) => {
      const xhr = new XMLHttpRequest();
      const url = "http://tp.cpe.fr:8083/cards";
  
      xhr.open('GET', url, true);
  
      xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            resolve(JSON.parse(xhr.responseText)); // Résoudre la promesse avec les données
          } else {
            reject(new Error(xhr.statusText)); // Rejeter la promesse en cas d'erreur
          }
        }
      };
  
      xhr.send();
    });
}

fetchData()
  .then(cardList => {
    let template = document.querySelector("#selectedCard");
    let clone = document.importNode(template.content, true);

    newContent= clone.firstElementChild.innerHTML
                .replace(/{{family_src}}/g, cardList[0].family_src)
                .replace(/{{family_name}}/g, cardList[0].family)
                .replace(/{{img_src}}/g, cardList[0].image_src)
                .replace(/{{name}}/g, cardList[0].name)
                .replace(/{{description}}/g, cardList[0].description)
                .replace(/{{hp}}/g, cardList[0].hp)
                .replace(/{{energy}}/g, cardList[0].energy)
                .replace(/{{attack}}/g, cardList[0].attack)
                .replace(/{{defense}}/g, cardList[0].defence);
    clone.firstElementChild.innerHTML= newContent;

    let cardContainer= document.querySelector("#cardContainer");
    cardContainer.appendChild(clone);
})