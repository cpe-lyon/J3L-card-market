package j3lcardmarket.atelier2.cardserver.controllers;

import j3lcardmarket.atelier2.cardserver.dto.CreateCardDto;
import j3lcardmarket.atelier2.cardserver.dto.SellCardDto;
import j3lcardmarket.atelier2.cardserver.models.Card;
import j3lcardmarket.atelier2.cardserver.models.Transaction;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.services.TransactionalCardManager;
import j3lcardmarket.atelier2.cardserver.utils.annotations.CardAuth;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/cards/owned")
    @CardAuth
    @ResponseBody
    public List<UserCard> getAllOwned(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.getAllByOwner(cardUserInfo.userName());
    }

    @GetMapping("/cards/on-sale")
    @ResponseBody
    public List<UserCard> getAllOnSale() {
        return cardService.getAllOnSale();
    }

    @PostMapping("/cards")
    @CardAuth
    @ResponseBody
    public UserCard create(@Valid @RequestBody CreateCardDto createCardDto, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.create(createCardDto.getName(), cardUserInfo.userName());
    }

    @PutMapping("/cards/buy/{userCardId}")
    @CardAuth
    @ResponseBody
    public Transaction buy(@PathVariable Integer userCardId, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.buy(userCardId, cardUserInfo.userName());
    }

    @PutMapping("/cards/sell/{userCardId}")
    @ResponseBody
    public UserCard sell(@Valid @RequestBody SellCardDto sellCardDto, @PathVariable Integer userCardId) {
        return cardService.sell(userCardId, sellCardDto.getPrice());
    }
}
