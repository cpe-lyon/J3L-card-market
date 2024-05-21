package j3lcardmarket.atelier2.authserver.controllers;

import j3lcardmarket.atelier2.authserver.models.BasicAuthInfoImpl;
import j3lcardmarket.atelier2.authserver.models.TokenAuthInfo;
import j3lcardmarket.atelier2.authserver.services.TokenLoginChecker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @RequestMapping("/login")
    public String login(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("redirectUrl") String redirect,
            HttpServletResponse response) {
        return responseFromSupplier(
                authorizationHeader,
                redirect,
                response,
                () -> loginService.checkLogin(new BasicAuthInfoImpl(authorizationHeader))
        );
    }

    @RequestMapping("/register")
    public String register(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("redirectUrl") String redirect,
            HttpServletResponse response) {
        return responseFromSupplier(
                authorizationHeader,
                redirect,
                response,
                () -> loginService.register(new BasicAuthInfoImpl(authorizationHeader))
        );
    }
}
