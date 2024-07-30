package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
}