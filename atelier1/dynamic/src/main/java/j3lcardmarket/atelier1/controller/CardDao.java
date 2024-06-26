package j3lcardmarket.atelier1.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import j3lcardmarket.atelier1.model.Card;

@Service
public class CardDao {
    private List<Card> myCardList;
    private Random randomGenerator;

    public CardDao() {
        myCardList=new ArrayList<>();
        randomGenerator = new Random();
        createCardList();
    }

    private void createCardList() {
        this.myCardList = new ArrayList<>();
        Card card1 = new Card("Dark Magician", "The ultimate wizard in terms of attack and defense.", "Spellcaster", "DARK", "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/192788bc-39d8-4892-a13f-ded75db42e69/dbwjsqd-34616a6d-8b30-469d-9253-a4ff4f838acf.png/v1/fill/w_894,h_894,q_70,strp/dark_magician__7th_artwork__by_omgitsjohannes_dbwjsqd-pre.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTYwMCIsInBhdGgiOiJcL2ZcLzE5Mjc4OGJjLTM5ZDgtNDg5Mi1hMTNmLWRlZDc1ZGI0MmU2OVwvZGJ3anNxZC0zNDYxNmE2ZC04YjMwLTQ2OWQtOTI1My1hNGZmNGY4MzhhY2YucG5nIiwid2lkdGgiOiI8PTE2MDAifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.CoZaLXf-gsDhQLrc2QaYkMGfnBfokq72RI6uSfOue00", "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/192788bc-39d8-4892-a13f-ded75db42e69/dbwjsqd-34616a6d-8b30-469d-9253-a4ff4f838acf.png/v1/fill/w_894,h_894,q_70,strp/dark_magician__7th_artwork__by_omgitsjohannes_dbwjsqd-pre.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTYwMCIsInBhdGgiOiJcL2ZcLzE5Mjc4OGJjLTM5ZDgtNDg5Mi1hMTNmLWRlZDc1ZGI0MmU2OVwvZGJ3anNxZC0zNDYxNmE2ZC04YjMwLTQ2OWQtOTI1My1hNGZmNGY4MzhhY2YucG5nIiwid2lkdGgiOiI8PTE2MDAifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.CoZaLXf-gsDhQLrc2QaYkMGfnBfokq72RI6uSfOue00", 1, 2500, 2100, 7, 5000);
        Card card2 = new Card("Blue-Eyes White Dragon", "This legendary dragon is a powerful engine of destruction.", "Dragon", "LIGHT", "https://wallpapercave.com/wp/wp2772500.jpg", "https://wallpapercave.com/wp/wp2772500.jpg", 2, 3000, 2500, 8, 8000);
        Card card3 = new Card("Red-Eyes Black Dragon", "A ferocious dragon with a powerful attack.", "Dragon", "FIRE", "https://s1.zerochan.net/Red-Eyes.Black.Dragon.600.1421143.jpg", "https://s1.zerochan.net/Red-Eyes.Black.Dragon.600.1421143.jpg", 3, 2400, 2000, 7, 4000);
        Card card4 = new Card("Summoned Skull", "A skull-faced fiend that attacks without mercy.", "Fiend", "DARK", "https://s1.zerochan.net/Summoned.Skull.600.3789417.jpg", "https://s1.zerochan.net/Summoned.Skull.600.3789417.jpg", 4, 2500, 1200, 6, 3000);

        this.myCardList.add(card1);
        this.myCardList.add(card2);
        this.myCardList.add(card3);
        this.myCardList.add(card4);
    }
    public List<Card> getCardList() {
        return this.myCardList;
    }
    public Card getCardByName(String name){
        for (Card CardBean : myCardList) {
            if(CardBean.getName().equals(name)){
                return CardBean;
            }
        }
        return null;
    }
    public Card getRandomCard(){
        int index=randomGenerator.nextInt(this.myCardList.size());
        return this.myCardList.get(index);
    }

    public Card addCard(Card card) {
        this.myCardList.add(card);
        return card;
    }

    public Card getCarById(int id) {
        for (Card CardBean : myCardList) {
            if(CardBean.getId() == id){
                return CardBean;
            }
        }
        return null;
    }
}


