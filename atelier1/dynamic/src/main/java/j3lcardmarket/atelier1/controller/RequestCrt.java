package j3lcardmarket.atelier1.controller;

import j3lcardmarket.atelier1.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller // AND NOT @RestController
public class RequestCrt {

    @Value("${welcome.message}")
    private String message;

    private static String messageLocal="Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);
        model.addAttribute("messageLocal", messageLocal);

        return "index";
    }

    @Autowired
    CardDao cardDao;

    @RequestMapping(value = { "/view"}, method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("myCard",cardDao.getRandomCard() );
        return "cardView";
    }


    @RequestMapping(value = { "/cards"}, method = RequestMethod.GET)
    public String cards(Model model) {
        model.addAttribute("cards",cardDao.getCardList());
        return "cardView";
    }

    @RequestMapping(value = { "/cards/{id}"}, method = RequestMethod.GET)
    public String card(String id) {
        //See one card, todo Leo
        return "seeCard";
    }


    @RequestMapping(value = { "/card"}, method = RequestMethod.GET)
    public String newCard() {
        return "createCard";
    }

    @RequestMapping(value = { "/card"}, method = RequestMethod.POST)
    public void createCard(Card card) {
        cardDao.addCard(card);
    }

}


