package j3lcardmarket.atelier2.cardserver.controllers;

import j3lcardmarket.atelier2.cardserver.services.TransactionalCardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CardsAPI {
    @Autowired
    TransactionalCardManager cardService;

    @RequestMapping(value = {"/cards"}, method = RequestMethod.GET)
    public String cards(Model model) {
        model.addAttribute("cards", cardService.getAll());
        return "cardView";
    }

    @RequestMapping(value = {"/cards/{id}"}, method = RequestMethod.GET)
    public String card(Model model, @PathVariable Integer id) {
        model.addAttribute("myCard", cardService.getById(id));
        return "cardView";
    }

    @RequestMapping(value = {"/cards"}, method = RequestMethod.POST)
    public String createCard(Model model) {
        model.addAttribute("myCard", cardService.create());
        return "cardView";
    }

    @RequestMapping(value = {"/cards/{id}/buy"}, method = RequestMethod.PUT)
    public String buyCard(Model model, @PathVariable Integer id) {
        model.addAttribute("myCard", cardService.buy(id));
        return "cardView";
    }

    @RequestMapping(value = {"/cards/{id}/sell"}, method = RequestMethod.PUT)
    public String sellCard(Model model, @PathVariable Integer id) {
        model.addAttribute("myCard", cardService.sell(id));
        return "cardView";
    }

}
