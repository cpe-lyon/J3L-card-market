package j3lcardmarket.atelier2.cardserver.database;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "app_user")
public class UserEntity {
    @Id
    private String surname;
}
