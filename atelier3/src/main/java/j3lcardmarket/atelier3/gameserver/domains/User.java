package j3lcardmarket.atelier3.gameserver.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class User {

    private final String surname;
    private final List<UserCard> cards;

}
