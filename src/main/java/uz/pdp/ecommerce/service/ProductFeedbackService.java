package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.dto.ProductFeedbackDto;
import uz.pdp.ecommerce.entity.Product;
import uz.pdp.ecommerce.entity.ProductFeedback;
import uz.pdp.ecommerce.entity.UserEntity;
import uz.pdp.ecommerce.mappers.ProductFeedbackMapper;
import uz.pdp.ecommerce.repo.ProductFeedbackRepository;
import uz.pdp.ecommerce.repo.ProductRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductFeedbackService {

    private final ProductFeedbackRepository productFeedbackRepository;
    private final ProductRepository productRepository;
    private final ProductFeedbackMapper productFeedbackMapper;

    public List<ProductFeedbackDto> getAllFeedbacks() {
        return productFeedbackRepository.findAll()
                .stream()
                .map(productFeedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductFeedbackDto getFeedbackById(UUID id) {
        return productFeedbackRepository.findById(id)
                .map(productFeedbackMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    public ProductFeedbackDto createFeedback(ProductFeedbackDto dto,  UserEntity user) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductFeedback feedback = productFeedbackMapper.toEntity(dto);
        feedback = productFeedbackRepository.save(feedback);

        return productFeedbackMapper.toDto(feedback);
    }

    public ProductFeedbackDto updateFeedback(UUID id, ProductFeedbackDto dto) {
        ProductFeedback existingFeedback = productFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingFeedback.setComment(dto.getComment());
        existingFeedback.setProduct(product);

        productFeedbackRepository.save(existingFeedback);

        return productFeedbackMapper.toDto(existingFeedback);
    }

    public void deleteFeedback(UUID id) {
        if (!productFeedbackRepository.existsById(id)) {
            throw new RuntimeException("Feedback not found");
        }
        productFeedbackRepository.deleteById(id);
    }
}
