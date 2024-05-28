package j3lcardmarket.atelier3.commons.config;

import j3lcardmarket.atelier3.commons.utils.UserUtils;
import j3lcardmarket.atelier3.commons.utils.CardAuth;
import j3lcardmarket.atelier3.commons.models.TimedUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Date;

abstract public class AuthHandler implements HandlerInterceptor {
    protected final UserUtils service;

    public AuthHandler(UserUtils service) {
        this.service = service;
    }

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (!method.isAnnotationPresent(CardAuth.class)) return true;

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null &&  authHeader.startsWith("Bearer ")){
            String token = authHeader.substring("Bearer".length()).trim();
            TimedUserInfo userInfo = service.checkLogin(token);
            if (userInfo != null && userInfo.getExpireDate().after(new Date())) {
                onLog(userInfo);
                request.setAttribute("cardUserInfo", userInfo);
                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print("NOT LOGGED IN");
        return false;
    }

    protected abstract void onLog(TimedUserInfo userInfo);
}
