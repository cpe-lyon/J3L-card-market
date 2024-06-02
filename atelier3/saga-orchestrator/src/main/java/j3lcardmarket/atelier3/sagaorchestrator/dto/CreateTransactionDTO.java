package j3lcardmarket.atelier3.sagaorchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTransactionDTO
{
    private Integer userCardId;
    private String sellerId;
    private String buyerId;
    private Integer price;
}