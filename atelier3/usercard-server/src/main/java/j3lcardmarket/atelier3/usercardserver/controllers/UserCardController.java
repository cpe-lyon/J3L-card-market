package j3lcardmarket.atelier3.usercardserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.CardAuth;
import j3lcardmarket.atelier3.commons.utils.ForbiddenException;
import j3lcardmarket.atelier3.commons.utils.SagaChecker;
import j3lcardmarket.atelier3.usercardserver.dto.SellCardDto;
import j3lcardmarket.atelier3.usercardserver.dto.EditUserCardDto;
import j3lcardmarket.atelier3.usercardserver.dto.UserCardDto;
import j3lcardmarket.atelier3.usercardserver.services.UserCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usercards")
public class UserCardController {

    @Value("${cardmanager.admin.username}")
    String adminUsername;

    @Autowired
    UserCardService cardService;

    @PostMapping("/{cardId}")
    @CardAuth
    @SecurityRequirement(name = "cardauth")
    @ResponseBody
    public Integer buildUserCard(@PathVariable Integer cardId , @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        if(!adminUsername.equals(cardUserInfo.userName())) throw new ForbiddenException();
        return cardService.createUserCard(cardId, cardUserInfo.userName()).getId();
    }

    @Autowired SagaChecker checker;

    @PostMapping("/starter/{userId}")
    @ResponseBody
    public void buildUserCards(@RequestBody List<Integer> cardIds, @PathVariable String userId) {
        checker.checkSagaAuth();
        cardService.createUserCards(cardIds, userId);
    }

    @GetMapping("/owned")
    @CardAuth
    @SecurityRequirement(name = "cardauth")
    @ResponseBody
    public List<UserCardDto> getAllOwned(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.getAllByOwner(cardUserInfo.userName())
                .stream().map(UserCardDto::new).collect(Collectors.toList());
    }
    @GetMapping("/{username}")
    @CardAuth
    @ResponseBody
    public List<UserCardDto> getAllOwnedByUser(@PathVariable("username") String username) {
        return cardService.getAllByOwner(username)
                .stream().map(UserCardDto::new).collect(Collectors.toList());
    }

    @GetMapping("/on-sale")
    @CardAuth
    @SecurityRequirement(name = "cardauth")
    @ResponseBody
    public List<UserCardDto> getAllOnSale(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.getPurchasableByOwner(cardUserInfo.userName())
                .stream().map(UserCardDto::new).collect(Collectors.toList());
    }

    @Autowired SagaChecker sagaChecker;
    @PostMapping("/{ucardId}/editAndReturnPrevious")
    public EditUserCardDto changeCard(@Valid @RequestBody EditUserCardDto newCard, @PathVariable Integer ucardId, @RequestParam(required = false) String force) {
        sagaChecker.checkSagaAuth();
        return new EditUserCardDto(cardService.changeCard(ucardId, newCard.getOwner(), newCard.getPrice(), force != null && force.equals("true")));
    }

    @PutMapping("/{cardId}/sell")
    @CardAuth
    @SecurityRequirement(name = "cardauth")
    @ResponseBody
    public void sellCard(@PathVariable Integer cardId, @RequestBody SellCardDto sellCardDto, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo){
        cardService.sellCard(cardId, sellCardDto.getPrice(),  cardUserInfo.userName());
    }
}
