package j3lcardmarket.atelier3.cardserver.config;

import j3lcardmarket.atelier3.commons.models.UserIdentifier;
import j3lcardmarket.atelier3.commons.utils.UserUtils;
import j3lcardmarket.atelier3.cardserver.services.CardService;
import j3lcardmarket.atelier3.cardserver.utils.annotations.CardAuth;
import j3lcardmarket.atelier3.commons.models.TimedUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.function.Consumer;

class AuthHandler implements HandlerInterceptor {
    private final UserUtils service;
    private final Consumer<UserIdentifier> cardInitializer;

    public AuthHandler(UserUtils service, CardService manager) {
        this.service = service;
        this.cardInitializer = (ignored) -> {};
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (!method.isAnnotationPresent(CardAuth.class)) return true;

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null &&  authHeader.startsWith("Bearer ")){
            String token = authHeader.substring("Bearer".length()).trim();
            TimedUserInfo userInfo = service.checkLogin(token);
            if (userInfo != null && userInfo.getExpireDate().after(new Date())) {
                request.setAttribute("cardUserInfo", userInfo);
                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print("NOT LOGGED IN");
        return false;
    }
}
