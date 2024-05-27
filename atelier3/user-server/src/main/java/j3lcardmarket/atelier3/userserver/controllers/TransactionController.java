package j3lcardmarket.atelier3.userserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier3.userserver.dto.CreateTransactionDTO;
import j3lcardmarket.atelier3.userserver.dto.TransactionDto;
import j3lcardmarket.atelier3.commons.models.Transaction;
import j3lcardmarket.atelier3.userserver.services.MarketService;
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
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    MarketService marketService;

    @Value("${userservice.transaction.token}")
    String transactionToken;

    @Value("${cardmanager.admin.username}")
    String adminUsername;

    @PostMapping
    public Transaction create(@Valid @RequestBody CreateTransactionDTO transactionDto, @RequestHeader("Authorization") String authHeader) {
        if(!authHeader.startsWith("Bearer ")) throw new ForbiddenException();
        String token = authHeader.substring("Bearer".length()).trim();
        if(!token.equals(transactionToken)) throw new ForbiddenException();

        return marketService.saveTransaction(
                transactionDto.getUserCardId(),
                transactionDto.getBuyerId(),
                transactionDto.getSellerId(),
                transactionDto.getPrice()
        );
    }

    @GetMapping()
    @ResponseBody
    @SecurityRequirement(name = "cardauth")
    @CardAuth
    public List<TransactionDto> getAll(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        if (!cardUserInfo.userName().equals(adminUsername)) throw new ForbiddenException();
        return marketService.getTransactions().stream().map(TransactionDto::new).collect(Collectors.toList());
    }
}
