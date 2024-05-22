package j3lcardmarket.atelier2.cardserver.config;

import j3lcardmarket.atelier2.cardserver.services.ProxyLoginChecker;
import j3lcardmarket.atelier2.cardserver.utils.annotations.CardAuth;
import j3lcardmarket.atelier2.commons.models.TimedUserInfo;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;
import java.util.Date;

class AuthHandler implements HandlerInterceptor {
    private final ProxyLoginChecker service;

    public AuthHandler(ProxyLoginChecker service) {

        this.service = service;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (!method.isAnnotationPresent(CardAuth.class)) return true;

        String authHeader = request.getHeader("Authorization");
        if (authHeader.startsWith("Bearer ")){
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
