package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.ecommerce.entity.BasketProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, UUID> {
    List<BasketProduct> findAllByBasketId(UUID id);

    Optional<BasketProduct> findByBasketId(UUID id);

    @Transactional
    void deleteAllByBasketId(UUID id);

    void deleteByProductIdAndBasketId(UUID productId, UUID basketId);
}
