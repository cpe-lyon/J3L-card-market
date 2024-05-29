package j3lcardmarket.atelier3.cardserver.controllers;

import j3lcardmarket.atelier3.cardserver.services.CardService;
import j3lcardmarket.atelier3.commons.models.Card;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@EnableAspectJAutoProxy(proxyTargetClass = true)
@TestPropertySource(properties = {
        "eureka.client.enabled=false"
})
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;


    private void toString(Iterable<Card> cards){
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(String.format("%d : %s -", card.getId(), card.getName()));
        }
    }

    private String toString(Integer id, String name){
        return String.format("%d : %s -", id, name);
    }


    private List<Card> testCards(){
        List<Card> cards = new ArrayList<>();
        cards.addAll(List.of(new Card(), new Card(), new Card()));
        cards.get(0).setId(15);
        cards.get(0).setName("Dragon");
        cards.get(1).setId(16);
        cards.get(1).setName("Fairy");
        cards.get(2).setId(17);
        cards.get(2).setName("Fox");
        return cards;
    }

    @Test
    void testGetAllCards() throws Exception {
        List<Card> cards = testCards();
        when(cardService.getAll()).thenReturn(cards);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cards")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(15))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Dragon"));
    }

    @Test
    void testGetFiveRandomCards() throws Exception {
        List<Card> cards = testCards();
        cards.addAll(testCards().stream()
                .peek(c -> c.setName(c.getName()+"2"))
                .peek(c -> c.setId(c.getId()+10))
                .collect(Collectors.toList()));
        cards.sort((c1,c2)->c2.getId()-c1.getId());
        cards.remove(0);
        when(cardService.getAll()).thenReturn(cards);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cards/pickStarterCards")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(new BaseMatcher<String>() {
                    @Override
                    public boolean matches(Object o) {
                        String s = o.toString();
                        List<Integer> collect = Arrays.stream(s.split(";")).map(Integer::parseInt).collect(Collectors.toList());
                        collect.sort(Integer::compareTo);
                        return collect.stream().map(Object::toString)
                                .collect(Collectors.joining("-")).equals("15-16-17-25-26");
                    }

                    @Override
                    public void describeTo(Description description) {}
                }));
    }

    @Test
    void testGetCardById() throws Exception {
        when(cardService.getById(17)).thenReturn(testCards().get(2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cards/17")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fox"));
    }
}