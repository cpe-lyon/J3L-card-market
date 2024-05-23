package j3lcardmarket.atelier2.cardserver.controllers;

import j3lcardmarket.atelier2.cardserver.dto.CardDto;
import j3lcardmarket.atelier2.cardserver.dto.CreateCardDto;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.services.TransactionalCardManager;
import j3lcardmarket.atelier2.cardserver.utils.annotations.CardAuth;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    TransactionalCardManager cardService;

    @GetMapping
    @ResponseBody
    public List<CardDto> getAll() {
        return cardService.getAll().stream().map(CardDto::new).collect(Collectors.toList());
    }

    @PostMapping
    @CardAuth
    @ResponseBody
    public Integer create(@Valid @RequestBody CreateCardDto createCardDto, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.createCard(createCardDto.getName(), cardUserInfo.userName(), createCardDto.getImageUrl()).getId();
    }

    @PostMapping("/{cardId}/user-card")
    @CardAuth
    @ResponseBody
    public Integer buildUserCard(@PathVariable Integer cardId , @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.createUserCard(cardId, cardUserInfo.userName()).getId();
    }

    @GetMapping("/owned")
    @CardAuth
    @ResponseBody
    public List<UserCard> getAllOwned(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.getAllByOwner(cardUserInfo.userName());
    }
}
