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

@Controller
public class CardController {
    @Autowired
    TransactionalCardManager cardService;

    @GetMapping("/cards")
    public List<Card> getAll() {
        return cardService.getAll();
    }

    @GetMapping("/cards/{cardId}")
    public Card getById(@PathVariable Integer cardId) {
        return cardService.getById(cardId);
    }

    @PostMapping("/cards/create")
    public Card create(@Valid @RequestBody CreateCardDto createCardDto) {
        return cardService.create(createCardDto.getName());
    }

    @PutMapping("/cards/buy/{userCardId}")
    public UserCard buy(@PathVariable Integer userCardId) {
        // TODO: get buyer from request
        return cardService.buy(userCardId, "");
    }

    @PutMapping("/cards/sell/{userCardId}")
    public UserCard sell(@Valid @RequestBody SellCardDto sellCardDto, @PathVariable Integer userCardId) {
        return cardService.sell(userCardId, sellCardDto.getPrice());
    }
}
