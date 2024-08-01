package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.Discount;

import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
}