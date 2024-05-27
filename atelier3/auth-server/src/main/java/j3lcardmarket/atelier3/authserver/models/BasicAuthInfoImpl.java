package j3lcardmarket.atelier3.authserver.models;

import org.apache.tomcat.util.codec.binary.Base64;

public class BasicAuthInfoImpl implements BasicAuthInfo{
    private final String username;
    private final String password;
    private final String authHeader;

    public BasicAuthInfoImpl(String authHeader){
        this.authHeader = authHeader;
        String base64Credentials = authHeader.substring("Basic".length()).trim();
        byte[] credentials = Base64.decodeBase64(base64Credentials);
        String decodedCredentials = new String(credentials);

        String[] values = decodedCredentials.split(":", 2);
        username = values[0];
        password = values[1];

    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String serialize() {
        return authHeader;
    }
}
