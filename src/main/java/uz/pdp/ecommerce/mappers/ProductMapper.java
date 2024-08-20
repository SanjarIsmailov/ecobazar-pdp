package uz.pdp.ecommerce.mappers;

import org.springframework.stereotype.Component;
import uz.pdp.ecommerce.dto.ProductDto;
import uz.pdp.ecommerce.entity.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockAmount(product.getStockAmount())
                .shippingPrice(product.getShippingPrice())
                .brandId(product.getBrand().getId())
                .categoryId(product.getCategory().getId())
                .images(product.getImages().stream()
                        .map(Attachment::getContent)
                        .collect(Collectors.toList()))
                .build();
    }

    public Product toEntity(ProductDto dto, Brand brand, Category category, List<Attachment> attachments) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockAmount(dto.getStockAmount())
                .shippingPrice(dto.getShippingPrice())
                .brand(brand)
                .category(category)
                .images(attachments)
                .build();
    }
}
