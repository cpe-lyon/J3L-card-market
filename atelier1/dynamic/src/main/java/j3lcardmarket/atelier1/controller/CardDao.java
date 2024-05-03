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

        Card p1=new Card("John", "pink", "super pink", "http://ekladata.com/9-cPSlYvrenNHMVawFmf_gLx8Jw.gif");
        Card p2=new Card("Roberto", "blue", "super lazy", "http://ekladata.com/JEVyY9DkwX4vVkakeBfikSyPROA.gif");
        Card p3=new Card("Anna", "orange", "super music girl", "http://ekladata.com/fMJl--_v-3CmisaynTHju1DMeXE.gif");
        Card p4=new Card("Angry Joe", "purple", "super angry power", "http://ekladata.com/AmbNNNvv-4YFEMZR8XD8e54WoHc.gif");
        Card p5=new Card("Ursula", "green", "super cloning power", "http://ekladata.com/CXJhi2YLUbNz6__e0Ct6ZP-XOds.gif");

        myCardList.add(p1);
        myCardList.add(p2);
        myCardList.add(p3);
        myCardList.add(p4);
        myCardList.add(p5);
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

    public Card addCard(String name, String color, String superPower, String imgUrl) {
        Card p=new Card(name, color, superPower, imgUrl);
        this.myCardList.add(p);
        return p;
    }
}


