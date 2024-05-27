package j3lcardmarket.atelier3.commons.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String imageUrl;
    private int level;
    private int defense;
    private int attack;
    private String attribute;
}
