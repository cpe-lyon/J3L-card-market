package j3lcardmarket.atelier3.usercardserver.services;

import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import j3lcardmarket.atelier3.commons.models.UserCard;
import j3lcardmarket.atelier3.commons.utils.HttpUtils;
import j3lcardmarket.atelier3.commons.utils.NotFoundException;
import j3lcardmarket.atelier3.usercardserver.repositories.CardReferenceRepository;
import j3lcardmarket.atelier3.usercardserver.repositories.UserCardRepository;
import j3lcardmarket.atelier3.usercardserver.repositories.UserIdentifierReferenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCardService {

    @Value("${usercardservice.cardserviceurl}")
    String cardServiceUrl;

    @Autowired
    private CardReferenceRepository cardRepo;

    @Autowired
    private UserCardRepository userCardRepo;

    @Autowired
    private UserIdentifierReferenceRepository userRepo;

    public List<UserCard> getAllByOwner(String ownerSurname) {
        return userCardRepo.findAllByOwnerSurname(ownerSurname);
    }

    public List<UserCard> getPurchasableByOwner(String ownerSurname) {
        return userCardRepo.findAllByPriceIsNotNullAndOwnerSurnameIsNot(ownerSurname);
    }

    private boolean checkCardExisting(int cardId){
        String url = cardServiceUrl.endsWith("/") ? cardServiceUrl : cardServiceUrl+"/";
        url += String.format("api/cards/%d",cardId);
        return HttpUtils.httpRequest(url) != null;
    }


    private UserCard unsafeCreateUserCard(int cardId, String creatorSurname){
        UserCard card = new UserCard();
        card.setCard(cardRepo.getReference(cardId));
        card.setOwner(new UserIdentifier(creatorSurname));
        card.setPrice(null);
        return userCardRepo.save(card);
    }

    @Transactional
    public UserCard createUserCard(int cardId, String creatorSurname) {
        if(!checkCardExisting(cardId)) throw new NotFoundException();
        return unsafeCreateUserCard(cardId, creatorSurname);
    }

    private List<Integer> pickStarterCards(String username){
        String url = cardServiceUrl.endsWith("/") ? cardServiceUrl : cardServiceUrl+"/";
        url += "api/pickStarterCards";
        String res = HttpUtils.httpRequest(url);
        if (res != null) throw new RuntimeException("Request failed");
        return Arrays.stream(res.split(";")).map(Integer::parseInt).collect(Collectors.toList());
    }

    @Transactional
    public void giveFiveRandomCards(String username) {
        List<Integer> ids = pickStarterCards(username);
        Collections.shuffle(ids);
        ids = ids.subList(0, 5);

        UserIdentifier user = userRepo.getReference(username);

        for (Integer id : ids) {
            UserCard userCard = new UserCard();
            userCard.setCard(cardRepo.getReference(id));
            userCard.setOwner(user);
            userCardRepo.save(userCard);
        }
    }
}