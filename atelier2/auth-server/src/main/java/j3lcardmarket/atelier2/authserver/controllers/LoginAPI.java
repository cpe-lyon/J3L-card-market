package j3lcardmarket.atelier2.authserver.controllers;

import j3lcardmarket.atelier2.authserver.models.*;
import j3lcardmarket.atelier2.authserver.services.TokenLoginChecker;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.function.Supplier;

@Controller
public class LoginAPI {

    @Autowired
    TokenLoginChecker loginService;

    private void responseFromSupplier(
            String authHeader,
            HttpServletResponse response,
            Supplier<TokenAuthInfo> tokenSupplier
    ) throws IOException {
        if (authHeader == null || !authHeader.startsWith("Basic")){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        TokenAuthInfo tokenAuthInfo = tokenSupplier.get();
        if (tokenAuthInfo == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(tokenAuthInfo.getToken());
    }

    // HTTP Endpoint
    @RequestMapping(value = "/login",  method = RequestMethod.POST)
    @ResponseBody
    public void login(
            @RequestHeader("Authorization") String authorizationHeader,
            HttpServletResponse response) throws IOException {
        responseFromSupplier(
                authorizationHeader,
                response,
                () -> loginService.checkLogin( new User(
                        new BasicAuthInfoImpl(authorizationHeader)
                ))
        );
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void register(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute RegisterAuthDTO authDto,
            HttpServletResponse response) throws IOException {
        responseFromSupplier(
                authorizationHeader,
                response,
                () -> loginService.register( new User(
                        new BasicAuthInfoImpl(authorizationHeader),
                        authDto.getSurname(),
                        authDto.getAvatarUrl()
                ))
        );
    }
}
