package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.dto.ProductDto;
import uz.pdp.ecommerce.entity.*;
import uz.pdp.ecommerce.mappers.ProductMapper;
import uz.pdp.ecommerce.repo.AttachmentRepository;
import uz.pdp.ecommerce.repo.BrandRepository;
import uz.pdp.ecommerce.repo.CategoryRepository;
import uz.pdp.ecommerce.repo.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductDto createProduct(ProductDto dto, UserEntity user) {
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Attachment> attachments = dto.getImages()
                .stream()
                .map(content -> new Attachment(content, "image/jpeg"))
                .collect(Collectors.toList());

        attachments = attachmentRepository.saveAll(attachments);

        Product product = productMapper.toEntity(dto, brand, category, attachments);
        product.setCreatedBy(user);
        product = productRepository.save(product);

        return productMapper.toDto(product);
    }

    public ProductDto updateProduct(UUID id, ProductDto dto, UserEntity user) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingProduct.setName(dto.getName());
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setStockAmount(dto.getStockAmount());
        existingProduct.setShippingPrice(dto.getShippingPrice());
        existingProduct.setBrand(brand);
        existingProduct.setCategory(category);

        // Update attachments
        attachmentRepository.deleteAll(existingProduct.getImages());
        List<Attachment> attachments = dto.getImages()
                .stream()
                .map(content -> new Attachment(content, "image/jpeg"))
                .collect(Collectors.toList());
        attachments = attachmentRepository.saveAll(attachments);
        existingProduct.setImages(attachments);
        existingProduct.setUpdatedBy(user);

        productRepository.save(existingProduct);

        return productMapper.toDto(existingProduct);
    }

    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
