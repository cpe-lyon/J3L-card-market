package j3lcardmarket.atelier3.userserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransactionDTO
{
    private Integer userCardId;
    private String sellerId;
    private String buyerId;
    private Integer price;
}