package j3lcardmarket.atelier3.cardserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier3.cardserver.dto.CardDto;
import j3lcardmarket.atelier3.cardserver.dto.CreateCardDto;
import j3lcardmarket.atelier3.cardserver.models.UserCard;
import j3lcardmarket.atelier3.cardserver.services.CardService;
import j3lcardmarket.atelier3.cardserver.utils.annotations.CardAuth;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.ForbiddenException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Value("${cardmanager.admin.username}")
    String adminUsername;

    @Autowired
    CardService cardService;

    @GetMapping
    @ResponseBody
    public List<CardDto> getAll() {
        return cardService.getAll().stream().map(CardDto::new).collect(Collectors.toList());
    }

    @PostMapping
    @CardAuth
    @SecurityRequirement(name = "cardauth")
    @ResponseBody
    public Integer create(@Valid @RequestBody CreateCardDto createCardDto, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        if(!adminUsername.equals(cardUserInfo.userName())) throw new ForbiddenException();
        return cardService.createCard(createCardDto.getName(), cardUserInfo.userName(), createCardDto.getImageUrl()).getId();
    }

    @PostMapping("/{cardId}/user-card")
    @CardAuth
    @SecurityRequirement(name = "cardauth")
    @ResponseBody
    public Integer buildUserCard(@PathVariable Integer cardId , @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        if(!adminUsername.equals(cardUserInfo.userName())) throw new ForbiddenException();
        return cardService.createUserCard(cardId, cardUserInfo.userName()).getId();
    }

    @GetMapping("/owned")
    @CardAuth
    @SecurityRequirement(name = "cardauth")
    @ResponseBody
    public List<UserCard> getAllOwned(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.getAllByOwner(cardUserInfo.userName());
    }

    @GetMapping("/on-sale")
    @CardAuth
    @SecurityRequirement(name = "cardauth")
    @ResponseBody
    public List<UserCard> getAllOnSale(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.getPurchasableByOwner(cardUserInfo.userName());
    }
}
