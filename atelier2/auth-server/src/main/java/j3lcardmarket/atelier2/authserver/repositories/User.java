package j3lcardmarket.atelier2.authserver.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User extends JpaRepository<User, Integer> {

    User insert(String username, String password, String avatar, String name);
    boolean login(String username, String password);

}
