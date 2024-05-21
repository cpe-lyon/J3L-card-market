package j3lcardmarket.atelier2.cardserver.controllers;

import j3lcardmarket.atelier2.cardserver.dto.CreateCardDto;
import j3lcardmarket.atelier2.cardserver.models.Card;
import j3lcardmarket.atelier2.cardserver.models.Transaction;
import j3lcardmarket.atelier2.cardserver.services.TransactionalCardManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CardsAPI {
    @Autowired
    TransactionalCardManager cardService;

    @GetMapping("/cards")
    public List<Card> getAll() {
        return cardService.getAll();
    }

    @GetMapping("/cards/{id}")
    public Card getById(@PathVariable Integer id) {
        return cardService.getById(id);
    }

    @PostMapping("/cards/create")
    public Card create(@Valid @RequestBody CreateCardDto createCardDto) {
        return cardService.create(createCardDto.getName());
    }

    @PutMapping("/cards/buy/{id}")
    public Transaction buy(@PathVariable Integer id) {
        return cardService.buy(id);
    }

    @PutMapping("/cards/sell/{id}")
    public Transaction sell(@PathVariable Integer id) {
        return cardService.sell(id);
    }
}
