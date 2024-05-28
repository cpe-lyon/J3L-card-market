package j3lcardmarket.atelier3.authserver.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import j3lcardmarket.atelier3.authserver.models.User;
import j3lcardmarket.atelier3.authserver.utils.TokenAuthInfoFactory;
import j3lcardmarket.atelier3.commons.models.TokenAuthInfo;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TokenLoginCheckerTest {

    @Mock
    private DbLoginChecker dbService;

    @Mock
    private TokenAuthInfoFactory authInfoFactory;

    @InjectMocks
    private TokenLoginChecker tokenLoginChecker;

    private User user;
    private UserInfo userInfo;
    private TokenAuthInfo tokenAuthInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserName("testUser");
        user.setPassword("testPassword");
        userInfo = new User();
        tokenAuthInfo = mock(TokenAuthInfo.class);
    }

    @Test
    void testCheckLogin() {
        when(dbService.checkLogin(user)).thenReturn(userInfo);
        when(authInfoFactory.createTokenAuthInfo(userInfo)).thenReturn(tokenAuthInfo);

        TokenAuthInfo actualTokenAuthInfo = tokenLoginChecker.checkLogin(user);

        assertEquals(tokenAuthInfo, actualTokenAuthInfo);
        verify(dbService, times(1)).checkLogin(user);
        verify(authInfoFactory, times(1)).createTokenAuthInfo(userInfo);
    }

    @Test
    void testRegister() {
        when(dbService.register(user)).thenReturn(userInfo);
        when(authInfoFactory.createTokenAuthInfo(userInfo)).thenReturn(tokenAuthInfo);

        TokenAuthInfo actualTokenAuthInfo = tokenLoginChecker.register(user);

        assertEquals(tokenAuthInfo, actualTokenAuthInfo);
        verify(dbService, times(1)).register(user);
        verify(authInfoFactory, times(1)).createTokenAuthInfo(userInfo);
    }
}