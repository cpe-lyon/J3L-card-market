package j3lcardmarket.atelier3.cardserver.models;

import j3lcardmarket.atelier3.commons.models.UserIdentifier;
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

    @ManyToOne(optional = true)
    private UserIdentifier creator;

    private String imageUrl;
}
