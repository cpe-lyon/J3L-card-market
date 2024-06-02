package j3lcardmarket.atelier3.authserver.services;

import j3lcardmarket.atelier3.authserver.models.User;
import j3lcardmarket.atelier3.authserver.repositories.UserRepository;
import j3lcardmarket.atelier3.commons.models.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DbLoginCheckerTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private DbLoginChecker dbLoginChecker;

    private User user;

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserName("testUser");
        user.setPassword("testPassword");
        dbLoginChecker.gatewayUrl = "http://testurl";
    }

    @Test
    void testCheckLogin() {
        User expectedUser = new User();
        when(repo.login(anyString(), anyString())).thenReturn(expectedUser);

        UserInfo actualUserInfo = dbLoginChecker.checkLogin(user);

        assertEquals(expectedUser, actualUserInfo);
        verify(repo, times(1)).login(eq("testUser"), anyString());
    }

    @Test
    void testRegister() {
        User expectedUser = new User();
        expectedUser.setUserName("testUser");
        when(repo.save(any(User.class))).thenReturn(expectedUser);
        when(repo.existsById(anyString())).thenReturn(false);
        when(restTemplate.postForObject(eq("http://testurl/api/orchestrate/register?user=testUser"), any(), eq(String.class))).thenReturn("OK");

        UserInfo actualUserInfo = dbLoginChecker.register(user);

        assertEquals(expectedUser, actualUserInfo);
        verify(repo, times(1)).save(user);
        verify(repo, times(1)).existsById("testUser");
    }

    @Test
    void testRegisterUserExists()  {
        when(repo.existsById(anyString())).thenReturn(true);

        UserInfo actualUserInfo = dbLoginChecker.register(user);

        assertNull(actualUserInfo);
        verify(repo, never()).save(any(User.class));
        verify(repo, times(1)).existsById("testUser");
    }
}