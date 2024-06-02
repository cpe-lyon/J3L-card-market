package j3lcardmarket.atelier3.userserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier3.commons.utils.CardAuth;
import j3lcardmarket.atelier3.commons.utils.SagaChecker;
import j3lcardmarket.atelier3.userserver.dto.UserInfoDto;
import j3lcardmarket.atelier3.userserver.services.UserService;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Value("${cardmanager.admin.username}")
    String adminUsername;

    @Autowired
    UserService userService;

    @GetMapping
    @CardAuth()
    @SecurityRequirement(name = "cardauth")
    public UserInfoDto getInfo(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return new UserInfoDto(cardUserInfo, adminUsername);
    }

    @GetMapping("/balance")
    @CardAuth()
    @SecurityRequirement(name = "cardauth")
    public Integer getBalance(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return userService.getBalance(cardUserInfo.userName());
    }


    @Autowired SagaChecker checker;

    @PostMapping("/{user}")
    public void createUser(@PathVariable String user) {
        checker.checkSagaAuth();
        userService.newCardUser(user);
    }


    @DeleteMapping("/{user}")
    public void deleteUser(@PathVariable String user) {
        checker.checkSagaAuth();
        userService.removeUser(user);
    }
}
