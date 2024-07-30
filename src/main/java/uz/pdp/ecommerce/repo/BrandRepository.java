package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.Brand;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}