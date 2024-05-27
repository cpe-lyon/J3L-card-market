package j3lcardmarket.atelier3.userserver.models;

import j3lcardmarket.atelier3.cardserver.models.UserCard;
import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private UserCard userCard;

    @ManyToOne
    private UserIdentifier seller;

    @ManyToOne
    private UserIdentifier buyer;

    private Integer price;

    @Column(name = "sold_on")
    private LocalDate soldOn;
}
