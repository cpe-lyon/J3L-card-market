package j3lcardmarket.atelier2.cardserver.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_identifier")
public class UserIdentifier {
    @Id
    private String surname;
}
