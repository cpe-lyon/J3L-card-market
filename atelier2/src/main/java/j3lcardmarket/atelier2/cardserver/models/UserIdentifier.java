package j3lcardmarket.atelier2.cardserver.models;

import j3lcardmarket.atelier2.cardserver.repositories.UserIdentifierRepository;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_identifier")
public class UserIdentifier {
    @Id
    private String surname;

    private Integer balance;

    public UserIdentifier(){
        this(null);
    }

    public UserIdentifier(String surname){
        this.surname = surname;
        this.balance = 100;
    }
}
