package j3lcardmarket.atelier2.cardserver.database;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_identifier")
public class UserEntity {
    @Id
    private String surname;
}