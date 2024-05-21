package j3lcardmarket.atelier2.authserver.repositories;

import j3lcardmarket.atelier2.authserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.userName=:uname AND u.password=:pwd")
    User login(@Param("uname") String userName, @Param("pwd") String password);
}
