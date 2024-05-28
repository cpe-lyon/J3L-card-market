package j3lcardmarket.atelier3.authserver.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import j3lcardmarket.atelier3.authserver.models.User;
import j3lcardmarket.atelier3.commons.models.TokenAuthInfo;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import j3lcardmarket.atelier3.commons.utils.SignatureUtils;
import j3lcardmarket.atelier3.commons.utils.UserInfoSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TokenAuthInfoFactoryTest {

    @Mock
    private UserInfoSerializer serializer;

    @Mock
    private SignatureUtils signatureUtils;

    @InjectMocks
    private TokenAuthInfoFactory authInfoFactory;

    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userInfo = new User();
    }

    @Test
    void testCreateTokenAuthInfo() {
        String unsignedToken = "unsignedToken";
        String signedToken = "signedToken";

        when(serializer.toUnsignedToken(userInfo)).thenReturn(unsignedToken);
        when(signatureUtils.sign(unsignedToken)).thenReturn(signedToken);

        TokenAuthInfo tokenAuthInfo = authInfoFactory.createTokenAuthInfo(userInfo);

        assertNotNull(tokenAuthInfo);
        assertEquals(signedToken, tokenAuthInfo.serialize());
        assertEquals(userInfo, tokenAuthInfo.getUserInfo());
    }

    @Test
    void testCreateTokenAuthInfo_NullUserInfo() {
        TokenAuthInfo tokenAuthInfo = authInfoFactory.createTokenAuthInfo(null);

        assertNull(tokenAuthInfo);
    }
}