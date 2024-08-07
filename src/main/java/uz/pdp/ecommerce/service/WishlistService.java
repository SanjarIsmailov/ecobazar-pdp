package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.entity.Wishlist;
import uz.pdp.ecommerce.repo.WishlistRepository;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public Optional<Wishlist> getWishlistById(Integer id) {
        return wishlistRepository.findById(id);
    }

    public Wishlist createWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public Wishlist updateWishlist(Integer id, Wishlist wishlist) {
        return wishlistRepository.findById(id)
                .map(existingWishlist -> {
                    existingWishlist.setProducts(wishlist.getProducts());
                    existingWishlist.setUser(wishlist.getUser());
                    return wishlistRepository.save(existingWishlist);
                }).orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));
    }

    public void deleteWishlist(Integer id) {
        wishlistRepository.deleteById(id);
    }
}
