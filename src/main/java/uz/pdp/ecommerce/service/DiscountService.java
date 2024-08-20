package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.dto.DiscountDto;
import uz.pdp.ecommerce.entity.Discount;
import uz.pdp.ecommerce.entity.UserEntity;
import uz.pdp.ecommerce.mappers.DiscountMapper;
import uz.pdp.ecommerce.repo.DiscountRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DiscountDto createDiscount(DiscountDto discountDto, UserEntity user) {
        Discount discount = discountMapper.toEntity(discountDto);
        discount.setStartTime(LocalDateTime.parse(discountDto.getStartTime(), formatter));
        discount.setEndTime(LocalDateTime.parse(discountDto.getEndTime(), formatter));
        discount.setCreatedBy(user);
        discountRepository.save(discount);
        return discountMapper.toDto(discount);
    }

    public DiscountDto updateDiscount(UUID id, DiscountDto discountDto, UserEntity user) {
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            discount.setPercentage(discountDto.getPercentage());
            discount.setStartTime(LocalDateTime.parse(discountDto.getStartTime(), formatter));
            discount.setEndTime(LocalDateTime.parse(discountDto.getEndTime(), formatter));
            discount.setUpdatedBy(user);
            discountRepository.save(discount);
            return discountMapper.toDto(discount);
        }
        return null;
    }

    public List<DiscountDto> getAllDiscounts() {
        return discountRepository.findAll().stream().map(discountMapper::toDto).toList();
    }

    public DiscountDto getDiscountById(UUID id) {
        return discountRepository.findById(id).map(discountMapper::toDto).orElse(null);
    }

    public void deleteDiscount(UUID id) {
        discountRepository.deleteById(id);
    }
}
