package j3lcardmarket.atelier2.cardserver.database;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Integer userCardId;

    private String seller;

    private String buyer;

    private int price;

    private LocalDate soldOn;
}
