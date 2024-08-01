package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.ProductFeedback;

import java.util.UUID;

public interface ProductFeedbackRepository extends JpaRepository<ProductFeedback, UUID> {
}