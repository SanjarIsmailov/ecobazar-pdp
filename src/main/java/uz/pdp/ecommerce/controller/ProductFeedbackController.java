package uz.pdp.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommerce.dto.ProductFeedbackDto;
import uz.pdp.ecommerce.entity.UserEntity;
import uz.pdp.ecommerce.service.ProductFeedbackService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class ProductFeedbackController {

    private final ProductFeedbackService productFeedbackService;

    @GetMapping
    public ResponseEntity<List<ProductFeedbackDto>> getAllFeedbacks() {
        return ResponseEntity.ok(productFeedbackService.getAllFeedbacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductFeedbackDto> getFeedbackById(@PathVariable UUID id) {
        return ResponseEntity.ok(productFeedbackService.getFeedbackById(id));
    }

    @PostMapping
    public ResponseEntity<ProductFeedbackDto> createFeedback(@RequestBody ProductFeedbackDto dto,
                                                             @AuthenticationPrincipal UserEntity user) {
        return new ResponseEntity<>(productFeedbackService.createFeedback(dto, user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductFeedbackDto> updateFeedback(@PathVariable UUID id, @RequestBody ProductFeedbackDto dto) {
        return ResponseEntity.ok(productFeedbackService.updateFeedback(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable UUID id) {
        productFeedbackService.deleteFeedback(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
