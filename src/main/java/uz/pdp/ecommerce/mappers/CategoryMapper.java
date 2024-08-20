package uz.pdp.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.ecommerce.dto.CategoryDto;
import uz.pdp.ecommerce.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "image.content", target = "imageContent")
    @Mapping(source = "image.contentType", target = "imageContentType")
    CategoryDto toDto(Category category);

    @Mapping(target = "image", ignore = true) // Will be set in the service
    Category toEntity(CategoryDto categoryDto);
}
