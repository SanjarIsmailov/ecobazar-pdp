package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
}