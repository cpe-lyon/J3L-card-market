package j3lcardmarket.atelier3.cardserver.controllers;

import j3lcardmarket.atelier3.cardserver.dto.CardDto;
import j3lcardmarket.atelier3.cardserver.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @GetMapping("/pickStarterCards")
    @ResponseBody
    public String getFiveRandomCards() {
        List<CardDto> all = getAll();
        Collections.shuffle(all);
        return all.subList(0,5).stream().map(CardDto::getId).map(i -> Integer.toString(i)).collect(Collectors.joining(";"));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CardDto getById(@PathVariable int id) {
        return new CardDto(cardService.getById(id));
    }
}
