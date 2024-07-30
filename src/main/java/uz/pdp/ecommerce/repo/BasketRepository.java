package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}