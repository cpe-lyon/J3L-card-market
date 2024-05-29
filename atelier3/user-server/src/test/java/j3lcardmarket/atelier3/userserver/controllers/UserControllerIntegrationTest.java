package j3lcardmarket.atelier3.userserver.controllers;

import j3lcardmarket.atelier3.commons.utils.HttpUtils;
import j3lcardmarket.atelier3.commons.utils.SignatureUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@TestPropertySource(properties = {
        "public.key=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAig59OFl3FuEeXnFladDlKBWEj38aplVnAxKjW7yZrEQwcCEKNbsrIMPr1B2saFnpYQHuvUVeqcQ4e7jE4iyZ/9e/El9rwMxnaMoUpJMQ0QaTU2zVsSKkA7zOY+wyeLiO74zRDspiRPOLPyTnRzf5Yhwfn47evlj+z3cCGadsIcVrjWhSORN/ovLb9eCdWQq9etXQ30sompRpH6ilyxJeIIPyiUTctP57/MMsVhNvFfBTFdaGiMD3YLKJt1QrgnzYI/kUj5kfspWcQa9qaEp3L9c/UAfDsulJ6lJfARqBBaENWeeP9TX/dDuC2wvZJYWGmTlBSAhBCwfc9Og8wp8w5wIDAQAB",
        "private.key=MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCKDn04WXcW4R5ecWVp0OUoFYSPfxqmVWcDEqNbvJmsRDBwIQo1uysgw+vUHaxoWelhAe69RV6pxDh7uMTiLJn/178SX2vAzGdoyhSkkxDRBpNTbNWxIqQDvM5j7DJ4uI7vjNEOymJE84s/JOdHN/liHB+fjt6+WP7PdwIZp2whxWuNaFI5E3+i8tv14J1ZCr161dDfSyialGkfqKXLEl4gg/KJRNy0/nv8wyxWE28V8FMV1oaIwPdgsom3VCuCfNgj+RSPmR+ylZxBr2poSncv1z9QB8Oy6UnqUl8BGoEFoQ1Z54/1Nf90O4LbC9klhYaZOUFICEELB9z06DzCnzDnAgMBAAECggEAPVJOQJdMlHcN/Dk2KJhHEVTteqekeLl6dhzzPq45PBFVypiCZndqorUjrSY5DGjThulK1/tWHuYviJT7rtLM3J1CG7EStaKVVDBelvNUBuR60BWJnZKbc6WLr0qc6I5hvvm3anBu0d2zvipwKGwuc2u2iDL3sWjqVdCwuCBroVvdTStDZsJD8rbSH+tgEdeOuFEE64h6RaLo+GNFrOC4RCJK4S1AOZrWS2TI2W/jqxVD+Tu30cnFrXJb8FFAI/UV56fUg2jIGsBFEnTZWBeCcRuRkkLb/f3wEFP9qAwpjcFRAOWT+xyZ2GnDDeEedcPW4eUSy5Gs54NNQwsB6fk9kQKBgQDTefHEcX1YptOJKcJG7lqApHG8o2S91FMgIkncqsRAe4tV4KomftG0c5W7F1ILWMz5eMk9L8UWYsnDsCW7JdAcpXKf0EY5CPMCrV9t5Mryyg0K3qJhMKoNaXwhTr933Icjsmc2Y1mqhC+VCfGfDAAkbC+mSMjDGftHGUGhF/BILQKBgQCnH2aUF/x+SQwphz8a3n5+F3z0sWnx/lWPLIFULeFEs2yHT5aH7coA6ElRY/dS3OmHCkLniuLNbDlIBLVkIJ3xFGqYLV0JR+SO5ZJicrYSZsHTp7kWBtRVTPfRR/bxgTq1YSj2c+cEZCbGDW3o69LWXhwVuUJoUJ9+tWS/m+GV4wKBgQCiVcWlSokx+D4B2LBtyqJmhdVZPQQkREzbKfcREXU9GplG/wMeMdPAdcnGqIwucahHCNVRxs2/9W10bk9IbipnEwn+a968AYap03Sh4zN3VGrWoTh86uJfEU5cMxr/Y6XJXTWlpnY6UuZDvjJgKNIjI3kgaFbUjak6VsTuaHXmsQKBgCaiHaZyfQ38ePFIsI/Xi0o+x8YRFTq53rGASk1ZZc/p4lbNUTEPCTl4eSp1f4EKTHnJJ3o7Iv7127AIVp5U4+4NGC8hnXY9xlo6O8LCXdCXLItvJFwxK/ikGE2q4y0lAJtZYIzIckkTldsG07eafFzN2JQ1BWLz8ErsR+P7dwSdAoGACjX8AIaGt1xie8SwK9Rq5gHb2Qtx+hqySgfWFKNTsEzOCT7zk9usw9Q5Ja4d8jegGp0xwGkxvJijZc51NI9tBM/KtNkGD9KlO3qXmi6v7Elw9UCdnnyzcVvsZ72Tjt18NEJVq4W/ExABJ12SJxtnutMOuSqlPr4taFmxyRIg4VA=",
        "cardmanager.admin.username=leon"
})
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
@EnableAspectJAutoProxy(proxyTargetClass = true)
@TestPropertySource(properties = {
        "eureka.client.enabled=false"
})
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SignatureUtils signatureUtils;

    private static String token = "---";

    @MockBean
    TransactionController tController;


    @MockBean
    private HttpUtils httpUtils;

    @BeforeEach
    public void setupToken(){
        if (!token.equals("---")) return;
        String unsToken = String.format("leon+++%d", new Date().getTime()+10000L);
        token = signatureUtils.sign(unsToken);
    }

    @Test
    void testUserInfos() throws Exception {
        when(httpUtils.httpRequest(anyString(), eq("POST"))).thenReturn("ok");

        mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/user")
                            .header("Authorization", "Bearer "+token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("leon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.admin").value(true));
    }


}