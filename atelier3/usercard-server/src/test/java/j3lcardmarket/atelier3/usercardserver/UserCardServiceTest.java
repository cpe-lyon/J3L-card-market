package j3lcardmarket.atelier3.usercardserver;

import j3lcardmarket.atelier3.commons.models.Card;
import j3lcardmarket.atelier3.commons.models.UserCard;
import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import j3lcardmarket.atelier3.commons.utils.HttpUtils;
import j3lcardmarket.atelier3.usercardserver.services.UserCardService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLException;
import java.util.List;

@DataJpaTest
@EntityScan("j3lcardmarker.atelier3.commons")
class UserCardServiceTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserCardService cardService;

    @MockBean
    private HttpUtils httpUtils;
    @BeforeEach
    public void setUp() throws SQLException {
        Card cmodel = new Card();
        cmodel.setName("Quoicoubeh");
        em.persist(cmodel);
        UserIdentifier pinkLily = new UserIdentifier("pink_lily");
        UserIdentifier jorge = new UserIdentifier("jorge");
        em.persist(pinkLily);
        em.persist(jorge);
        UserCard c1 = new UserCard();
        c1.setOwner(pinkLily);
        c1.setCard(cmodel);
        UserCard c2 = new UserCard();
        c2.setOwner(pinkLily);
        c2.setCard(cmodel);
        c2.setPrice(4);
        UserCard c3 = new UserCard();
        c3.setOwner(jorge);
        c3.setCard(cmodel);
        c3.setPrice(15);
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);

    }

    @Transactional
    @Test
    public void testGetAllByOwner() {
        List<UserCard> all = cardService.getAllByOwner("pink_lily");
        Assertions.assertEquals(all.size(), 2);
        Assertions.assertEquals(all.get(0).getCard().getName(), "Quoicoubeh");
    }

    @Transactional
    @Test
    public void testGetSold() {
        List<UserCard> all = cardService.getPurchasableByOwner("jorge");
        Assertions.assertEquals(all.size(), 1);
        Assertions.assertEquals(all.get(0).getPrice(), 4);
    }

    @Transactional
    @Test
    public void testCreateUserCard(){
        Card cmodel = new Card();
        cmodel.setName("Apagnan");
        Integer id = (Integer) em.persistAndGetId(cmodel);
        when(httpUtils.httpRequest(anyString())).thenReturn("ok");
        Integer id1 = cardService.createUserCard(id, "jorge").getId();
        em.find(UserCard.class, id1);
    }
}