package j3lcardmarket.atelier2.cardserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier2.cardserver.dto.SellCardDto;
import j3lcardmarket.atelier2.cardserver.dto.TransactionDto;
import j3lcardmarket.atelier2.cardserver.models.Transaction;
import j3lcardmarket.atelier2.cardserver.models.UserCard;
import j3lcardmarket.atelier2.cardserver.services.MarketService;
import j3lcardmarket.atelier2.cardserver.utils.annotations.CardAuth;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import j3lcardmarket.atelier2.commons.utils.ForbiddenException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/market")
public class MarketController {
    @Value("${cardmanager.admin.username}")
    String adminUsername;

    @Autowired
    MarketService marketService;

    @PutMapping("/user-cards/{userCardId}/buy")
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    @ResponseBody
    public Transaction buy(@PathVariable Integer userCardId, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return marketService.buy(userCardId, cardUserInfo.userName());
    }

    @PutMapping("/user-cards/{userCardId}/sell")
    @ResponseBody
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    public UserCard sell(@PathVariable Integer userCardId, @Valid @RequestBody SellCardDto sellCardDto, @RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return marketService.sell(userCardId, sellCardDto.getPrice(), cardUserInfo.userName());
    }

    @GetMapping("/transactions")
    @ResponseBody
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    public List<TransactionDto> getAll(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        if (!cardUserInfo.userName().equals(adminUsername)) throw new ForbiddenException();
        return marketService.getTransactions().stream().map(TransactionDto::new).collect(Collectors.toList());
    }
}
