package uz.pdp.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommerce.dto.DiscountDto;
import uz.pdp.ecommerce.entity.UserEntity;
import uz.pdp.ecommerce.service.DiscountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    public DiscountDto createDiscount(@RequestBody DiscountDto discountDto, @AuthenticationPrincipal UserEntity user) {
        return discountService.createDiscount(discountDto, user);
    }

    @PutMapping("/{id}")
    public DiscountDto updateDiscount(@PathVariable UUID id, @RequestBody DiscountDto discountDto, @AuthenticationPrincipal UserEntity user) {
        return discountService.updateDiscount(id, discountDto, user);
    }

    @GetMapping
    public List<DiscountDto> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    @GetMapping("/{id}")
    public DiscountDto getDiscountById(@PathVariable UUID id) {
        return discountService.getDiscountById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDiscount(@PathVariable UUID id) {
        discountService.deleteDiscount(id);
    }
}
