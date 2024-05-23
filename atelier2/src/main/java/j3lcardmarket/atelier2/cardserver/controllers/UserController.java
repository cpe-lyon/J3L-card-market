package j3lcardmarket.atelier2.cardserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import j3lcardmarket.atelier2.cardserver.dto.UserInfoDto;
import j3lcardmarket.atelier2.cardserver.utils.annotations.CardAuth;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userInfo")
public class UserController {

    @Value("${cardmanager.admin.username}")
    String adminUsername;

    @GetMapping("")
    @CardAuth()
    @SecurityRequirement(name = "cardauth")
    public UserInfoDto getInfo(@RequestAttribute("cardUserInfo") UserInfo cardUserInfo) {
        return new UserInfoDto(cardUserInfo, adminUsername);
    }
}
