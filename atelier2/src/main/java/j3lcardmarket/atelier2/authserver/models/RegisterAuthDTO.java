package j3lcardmarket.atelier2.authserver.models;

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
