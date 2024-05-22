package j3lcardmarket.atelier2.commons.utils;

import j3lcardmarket.atelier2.commons.models.TimedUserInfo;
import j3lcardmarket.atelier2.commons.models.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.StringJoiner;


@Component("singleton")
public class UserInfoSerializer {

    private String sanitize(String attr){
        return attr.replace("+","\\+");
    }

    private String unsanitize(String attr){
        return attr.replace("\\+","+");
    }

    private record UserInfoRecord(String userName, String surname, String avatarUrl) implements UserInfo{}
    public TimedUserInfo fromUnsignedToken(String token) throws InvalidTokenException{
        String[] parts = token.split("(?<!\\\\)\\+", 4);
        if(parts.length != 4) throw new InvalidTokenException("Incomplete UserInfo");
        return new TimedUserInfo(new UserInfoRecord(
                unsanitize(parts[0]),
                unsanitize(parts[1]),
                unsanitize(parts[2])
        ), Long.decode(unsanitize(parts[3])));
    }

    public String toUnsignedToken(UserInfo src){
        Long timestamp = new Date().getTime() + 1200000L;
        StringJoiner joiner = new StringJoiner("+");
        joiner.add(sanitize(src.userName()));
        joiner.add(sanitize(src.surname()));
        joiner.add(sanitize(src.avatarUrl()));
        joiner.add(sanitize(timestamp.toString()));
        return joiner.toString();
    }
}
