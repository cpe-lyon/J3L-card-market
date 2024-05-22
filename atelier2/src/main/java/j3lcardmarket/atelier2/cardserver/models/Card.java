package j3lcardmarket.atelier2.cardserver.models;

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
}
