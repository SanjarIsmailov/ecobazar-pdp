package uz.pdp.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.ecommerce.dto.DiscountDto;
import uz.pdp.ecommerce.entity.Discount;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    DiscountDto toDto(Discount discount);

    @Mapping(target = "startTime", ignore = true) // Will be set in the service
    @Mapping(target = "endTime", ignore = true) // Will be set in the service
    Discount toEntity(DiscountDto discountDto);
}
