package j3lcardmarket.atelier2.cardserver.models;

import org.springframework.stereotype.Service;

@Service
public class Card {
    private int id;
    private String name;

    public Card(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
