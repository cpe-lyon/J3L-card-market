package j3lcardmarket.atelier3.usercardserver.services;

import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import j3lcardmarket.atelier3.commons.models.UserCard;
import j3lcardmarket.atelier3.commons.utils.ForbiddenException;
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
import java.util.Optional;
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

    @Autowired
    private HttpUtils httpUtils;

    public List<UserCard> getAllByOwner(String ownerSurname) {
        return userCardRepo.findAllByOwnerSurname(ownerSurname);
    }

    public List<UserCard> getPurchasableByOwner(String ownerSurname) {
        return userCardRepo.findAllByPriceIsNotNullAndPriceGreaterThanEqualAndOwnerSurnameIsNot
                (1,ownerSurname);
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
        //No check needed: admin operation
        //if(!checkCardExisting(cardId)) throw new NotFoundException();
        return unsafeCreateUserCard(cardId, creatorSurname);
    }

    @Transactional
    public void createUserCards(List<Integer> cardIds, String creatorSurname) {
        //No check needed: admin operation
        //if(!checkCardExisting(cardId)) throw new NotFoundException();
        for (Integer cardId : cardIds) {
            unsafeCreateUserCard(cardId, creatorSurname);
        }
    }

    private List<Integer> pickStarterCards(String username){
        String url = cardServiceUrl.endsWith("/") ? cardServiceUrl : cardServiceUrl+"/";
        url += "api/pickStarterCards";
        String res = httpUtils.httpRequest(url);
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

    @Transactional
    public void sellCard(int cardId, int price, String sellerSurname) {
        UserCard card = userCardRepo.findByIdAndOwnerSurname(cardId, sellerSurname);
        if(card == null) throw new NotFoundException();
        card.setPrice(price);
        userCardRepo.save(card);
    }

    @Transactional
    public UserCard changeCard(Integer id, String owner, Integer price, boolean force) {
        Optional<UserCard> oldUcard = userCardRepo.findById(id);
        if(oldUcard.isEmpty()) throw new NotFoundException();
        UserCard toEdit = oldUcard.get().clone();
        UserCard toReturn = oldUcard.get().clone();
        if(toEdit.getPrice() == 0 && !force) throw new ForbiddenException();
        if(
                toEdit.getOwner().getSurname().equals(owner)
                        || toEdit.getPrice() == null
        ) throw new ForbiddenException();
        toEdit.setOwner(userRepo.getReference(owner));
        toEdit.setPrice(price);
        userCardRepo.save(toEdit);
        return toReturn;
    }
}