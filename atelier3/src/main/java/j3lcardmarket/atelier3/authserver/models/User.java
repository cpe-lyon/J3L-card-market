package j3lcardmarket.atelier3.authserver.models;

import j3lcardmarket.atelier3.commons.models.UserInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "User", schema = "public")
public class User implements UserInfo {

    @Id
    @Column(name="user_name")
    private String userName;
    private String password;
    private String surname = "";
    @Column(name="avatar_url")
    private String avatarUrl = "";

    public User(String userName, String password, String surname, String avatarUrl){
        this.userName = userName;
        this.password = password;
        this.surname = surname;
        this.avatarUrl = avatarUrl;
    }
    public User() {}
    public User(BasicAuthInfo basicAuthInfo){
        this(basicAuthInfo.getUsername(), basicAuthInfo.getPassword(), "", "");
    }
    public User(BasicAuthInfo basicAuthInfo, String surname, String avatarUrl){
        this(basicAuthInfo.getUsername(), basicAuthInfo.getPassword(), surname, avatarUrl);
    }

    @Override
    public String userName() {
        return userName == null ? "" : userName;
    }

    @Override
    public String surname() {
        return surname == null ? "" : surname;
    }

    @Override
    public String avatarUrl() {
        return avatarUrl == null ? "" : avatarUrl;
    }

}
