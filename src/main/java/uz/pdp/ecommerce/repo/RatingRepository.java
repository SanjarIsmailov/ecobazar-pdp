package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.Rating;

import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, UUID> {
}