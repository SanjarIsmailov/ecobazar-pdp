package uz.pdp.ecommerce.mappers;

import org.mapstruct.*;
import uz.pdp.ecommerce.dto.ProductFeedbackDto;
import uz.pdp.ecommerce.entity.ProductFeedback;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductFeedbackMapper {

    @Mapping(target = "product.id", source = "productId")
    ProductFeedback toEntity(ProductFeedbackDto dto);

    @Mapping(target = "productId", source = "product.id")
    ProductFeedbackDto toDto(ProductFeedback productFeedback);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFeedbackFromDto(ProductFeedbackDto dto, @MappingTarget ProductFeedback productFeedback);
}
