package j3lcardmarket.atelier2.cardserver.controllers;

import j3lcardmarket.atelier2.cardserver.services.TransactionalCardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CardsAPI {
    @Autowired
    TransactionalCardManager cardService;

    // HTTP endpoints

}
