package uz.pdp.ecommerce.service;

import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.entity.Brand;
import uz.pdp.ecommerce.repo.BrandRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(UUID id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        return optionalBrand.orElse(null);
    }

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(UUID id, Brand brandDetails) {
        return brandRepository.findById(id)
                .map(brand -> {
                    brand.setName(brandDetails.getName());
                    brand.setAttachment(brandDetails.getAttachment());
                    return brandRepository.save(brand);
                })
                .orElse(null);
    }

    public boolean deleteBrand(UUID id) {
        if (brandRepository.existsById(id)) {
            brandRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
