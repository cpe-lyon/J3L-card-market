package j3lcardmarket.atelier2.cardserver.controllers;

import j3lcardmarket.atelier2.cardserver.dto.CreateCardDto;
import j3lcardmarket.atelier2.cardserver.dto.SellCardDto;
import j3lcardmarket.atelier2.cardserver.models.Card;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.services.TransactionalCardManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    TransactionalCardManager cardService;

    @GetMapping("/cards")
    @ResponseBody
    public List<Card> getAll() {
        return cardService.getAll();
    }

    @GetMapping("/cards/on-sale")
    @ResponseBody
    public List<UserCard> getAllOnSale() {
        return cardService.getAllOnSale();
    }

    @GetMapping("/cards/{cardId}")
    @ResponseBody
    public Card getById(@PathVariable Integer cardId) {
        return cardService.getById(cardId);
    }

    @GetMapping("/cards/on-sale/{userCardId}")
    @ResponseBody
    public UserCard getOnSaleById(@PathVariable Integer userCardId) {
        return cardService.getOnSaleById(userCardId);
    }

    @PostMapping("/cards")
    @ResponseBody
    public UserCard create(@Valid @RequestBody CreateCardDto createCardDto) {
        // TODO: get creator from request
        return cardService.create(createCardDto.getName(), "josse");
    }

    @PutMapping("/cards/buy/{userCardId}")
    @ResponseBody
    public UserCard buy(@PathVariable Integer userCardId) {
        // TODO: get buyer from request
        return cardService.buy(userCardId, "josse");
    }

    @PutMapping("/cards/sell/{userCardId}")
    @ResponseBody
    public UserCard sell(@Valid @RequestBody SellCardDto sellCardDto, @PathVariable Integer userCardId) {
        return cardService.sell(userCardId, sellCardDto.getPrice());
    }
}
