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
        //base data ?
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
}


