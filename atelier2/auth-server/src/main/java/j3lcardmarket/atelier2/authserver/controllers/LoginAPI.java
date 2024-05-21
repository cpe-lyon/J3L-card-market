package j3lcardmarket.atelier2.authserver.controllers;

import j3lcardmarket.atelier2.authserver.models.AuthDTO;
import j3lcardmarket.atelier2.authserver.models.BasicAuthInfoImpl;
import j3lcardmarket.atelier2.authserver.models.RegisterAuthDTO;
import j3lcardmarket.atelier2.authserver.models.TokenAuthInfo;
import j3lcardmarket.atelier2.authserver.services.TokenLoginChecker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@Controller
public class LoginAPI {

    @Autowired
    TokenLoginChecker loginService;

    private String responseFromSupplier(
            String authHeader,
            String redirect,
            HttpServletResponse response,
            Supplier<TokenAuthInfo> tokenSupplier
    ){
        if (authHeader == null || !authHeader.startsWith("Basic")){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "Forbidden: need basic auth";
        }
        TokenAuthInfo tokenAuthInfo = tokenSupplier.get();
        if (tokenAuthInfo == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "Unauthorized: invalid credentials";
        }
        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
        response.setHeader("Location", redirect);
        return null;
    }

    // HTTP Endpoint
    @RequestMapping(value = "/login",  method = RequestMethod.POST)
    public String login(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute AuthDTO authDto,
            HttpServletResponse response) {
        return responseFromSupplier(
                authorizationHeader,
                authDto.getRedirect(),
                response,
                () -> loginService.checkLogin(new BasicAuthInfoImpl(authorizationHeader))
        );
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute RegisterAuthDTO authDto,
            HttpServletResponse response) {
        return responseFromSupplier(
                authorizationHeader,
                authDto.getRedirect(),
                response,
                () -> loginService.register(new BasicAuthInfoImpl(authorizationHeader))
        );
    }
}
