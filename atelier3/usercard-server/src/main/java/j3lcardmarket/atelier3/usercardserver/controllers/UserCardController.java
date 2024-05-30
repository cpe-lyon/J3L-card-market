package j3lcardmarket.atelier3.usercardserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.CardAuth;
import j3lcardmarket.atelier3.commons.utils.ForbiddenException;
import j3lcardmarket.atelier3.usercardserver.dto.SellCardDto;
import j3lcardmarket.atelier3.usercardserver.dto.UserCardDto;
import j3lcardmarket.atelier3.usercardserver.services.UserCardService;
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

    @Value("${userservice.transaction.token}")
    String transactionToken;

    @PostMapping("/init/{username}")
    public void userCardsInit(@RequestHeader("Authorization") String authHeader, String username){
        if(!authHeader.startsWith("Bearer ")) throw new ForbiddenException();
        String token = authHeader.substring("Bearer".length()).trim();
        if(!token.equals(transactionToken)) throw new ForbiddenException();

        cardService.giveFiveRandomCards(username);
    }

    @PostMapping("/{cardId}")
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
    public List<UserCardDto> getAllOwned(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return cardService.getAllByOwner(cardUserInfo.userName())
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

    @PutMapping("/{cardId}/sell")
    @CardAuth
    @SecurityRequirement(name = "cardauth")
    @ResponseBody
    public void sellCard(@PathVariable Integer cardId, @RequestBody SellCardDto sellCardDto, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo){
        cardService.sellCard(cardId, sellCardDto.getPrice(),  cardUserInfo.userName());
    }
}
