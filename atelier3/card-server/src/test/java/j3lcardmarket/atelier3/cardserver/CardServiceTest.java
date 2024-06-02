package j3lcardmarket.atelier3.cardserver;

import j3lcardmarket.atelier3.cardserver.services.CardService;
import j3lcardmarket.atelier3.commons.models.Card;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Transactional
@EntityScan("j3lcardmarker.atelier3.commons")
class CardServiceTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CardService cardService;

    @BeforeEach
    void setUp() throws SQLException {
        em.clear();
        em.getEntityManager().createEntityGraph(Card.class);
    }

    @Transactional
    @Test
    public void testGetAll() {
        Card c1 = new Card();
        c1.setName("Sangoku");
        Integer id = (Integer) em.persistAndGetId(c1);
        List<Card> all = cardService.getAll();
        Optional<Card> first = all.stream().filter(c -> c.getId().equals(id)).findFirst();
        Assertions.assertEquals(first.get().getName(), "Sangoku");
    }

    @Test
    public void testGetById() {
        Card c1 = new Card();
        c1.setName("Sangoku");
        Integer id = (Integer) em.persistAndGetId(c1);
        Card mayC1 = cardService.getById(id);
        Assertions.assertEquals(mayC1.getName(), "Sangoku");
    }

    @Test
    public void testCreateCard() {
        Integer id = cardService.createCard("Dark Sasuke", "url2", 67, 10, 193, "attr1").getId();
        Card c1 = em.find(Card.class, id);
        Assertions.assertEquals(c1.getLevel(), 67);
        Assertions.assertEquals(c1.getAttack(), 193);
    }

}