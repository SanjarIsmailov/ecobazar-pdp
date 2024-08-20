package uz.pdp.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String name;
    private String description;
    private Double price;
    private Integer stockAmount;
    private Double shippingPrice;
    private UUID brandId;
    private UUID categoryId;
    private List<byte[]> images;
}
