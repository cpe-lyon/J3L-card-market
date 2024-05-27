package j3lcardmarket.atelier3.authserver.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class RegisterAuthDTO{
    private String avatarUrl = null;
    private String surname = null;

}
