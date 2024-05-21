package j3lcardmarket.atelier2.cardserver.database;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private UserCardEntity userCard;

    @ManyToOne
    private UserEntity seller;

    @ManyToOne
    private UserEntity buyer;

    private int price;

    private LocalDate soldOn;
}
