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

// for(const card of cardlist){
//     let clone = document.importNode(template.content, true);

//     newContent= clone.firstElementChild.innerHTML
//                 .replace(/{{family_src}}/g, card.family_src)
//                 .replace(/{{family_name}}/g, card.family_name)
//                 .replace(/{{image_src}}/g, card.image_src)
//                 .replace(/{{date}}/g, card.date)
//                 .replace(/{{comment}}/g, card.comment)
//                 .replace(/{{like}}/g, card.like)
//                 .replace(/{{button}}/g, card.button);
//     clone.firstElementChild.innerHTML= newContent;

//     let cardContainer= document.querySelector("#gridContainer");
//     cardContainer.appendChild(clone);
// }

let template = document.querySelector("#selectedCard");

fetchData()
    .then(cardList => {
        cardList.forEach(card => {
            let clone = document.importNode(template.content, true);
    
            newContent= clone.firstElementChild.innerHTML
              .replace(/{{family_src}}/g, card.smallImgUrl)
              .replace(/{{family_name}}/g, card.family)
              .replace(/{{image_src}}/g, card.imgUrl)
                .replace(/{{card_id}}/g, card.id)
              .replace(/{{date}}/g, "01/01/1999")
              .replace(/{{comment}}/g, 0)
              .replace(/{{like}}/g, 0)
              .replace(/{{button}}/g, "Display");
            clone.firstElementChild.innerHTML= newContent;
    
            let cardContainer= document.querySelector("#gridContainer");
            cardContainer.appendChild(clone);
        });
    }
);