package j3lcardmarket.atelier2.cardserver.controllers;

import j3lcardmarket.atelier2.cardserver.dto.UserInfoDto;
import j3lcardmarket.atelier2.cardserver.utils.annotations.CardAuth;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/userInfo")
    @CardAuth()
    public UserInfoDto getInfo(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return new UserInfoDto(cardUserInfo);
    }
}
