package j3lcardmarket.atelier2.cardserver.models;

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

    private LocalDate soldOn;
}
