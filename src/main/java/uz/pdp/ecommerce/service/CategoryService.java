package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.dto.CategoryDto;
import uz.pdp.ecommerce.entity.Attachment;
import uz.pdp.ecommerce.entity.Category;
import uz.pdp.ecommerce.entity.UserEntity;
import uz.pdp.ecommerce.mappers.CategoryMapper;
import uz.pdp.ecommerce.repo.AttachmentRepository;
import uz.pdp.ecommerce.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto createCategory(CategoryDto categoryDto, UserEntity user) {
        // Create and save Attachment
        Attachment attachment = new Attachment();
        attachment.setContent(categoryDto.getImageContent());
        attachment.setContentType(categoryDto.getImageContentType());
        attachment.setCreatedBy(user);
        attachmentRepository.save(attachment);

        // Create and save Category
        Category category = categoryMapper.toEntity(categoryDto);
        category.setImage(attachment);
        category.setCreatedBy(user);
        categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    public CategoryDto updateCategory(UUID id, CategoryDto categoryDto, UserEntity user) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();

            // Update or create Attachment
            Attachment attachment = category.getImage();
            if (attachment == null) {
                attachment = new Attachment();
                attachment.setCreatedBy(user);
            }
            attachment.setContent(categoryDto.getImageContent());
            attachment.setContentType(categoryDto.getImageContentType());
            attachment.setUpdatedBy(user);
            attachmentRepository.save(attachment);

            // Update Category
            category.setName(categoryDto.getName());
            category.setImage(attachment);
            category.setUpdatedBy(user);
            categoryRepository.save(category);

            return categoryMapper.toDto(category);
        }
        return null;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    public CategoryDto getCategoryById(UUID id) {
        return categoryRepository.findById(id).map(categoryMapper::toDto).orElse(null);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
