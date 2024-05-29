package j3lcardmarket.atelier3.commons.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlayerCardId implements Serializable {

    private Integer userCardId;

    private String userSurname;
}
