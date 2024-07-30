package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}