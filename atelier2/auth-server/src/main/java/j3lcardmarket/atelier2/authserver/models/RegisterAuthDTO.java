package j3lcardmarket.atelier2.authserver.models;

public class RegisterAuthDTO extends AuthDTO{
    private String avatarUrl = null;
    private String surname = null;
    
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
