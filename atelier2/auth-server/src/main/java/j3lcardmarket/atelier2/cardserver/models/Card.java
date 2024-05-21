package j3lcardmarket.atelier2.cardserver.models;

import j3lcardmarket.atelier2.cardserver.repositories.DbCardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Card {
    @Autowired
    DbCardManager repo;
}
