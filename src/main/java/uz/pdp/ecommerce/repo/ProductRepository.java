package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}