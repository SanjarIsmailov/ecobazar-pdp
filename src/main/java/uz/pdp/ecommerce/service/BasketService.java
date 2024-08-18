package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.entity.Basket;
import uz.pdp.ecommerce.entity.BasketProduct;
import uz.pdp.ecommerce.entity.Product;
import uz.pdp.ecommerce.entity.UserEntity;
import uz.pdp.ecommerce.repo.*;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketProductRepository basketProductRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final UserEntityRepository userEntityRepository;

    public void addToBasket(UUID userId, UUID basketId, UUID productId) {
        UserEntity userEntity = userEntityRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        Basket basket = getUsersBasket(userId);
        BasketProduct basketProduct = new BasketProduct();
        basketProduct.setBasket(basket);
        basketProduct.setProduct(product);
        basketProduct.setAmount(1);
        basketProductRepository.save(basketProduct);
    }

    public void removeFromBasket(UUID basketId, UUID productId) {
        basketProductRepository.deleteByProductIdAndBasketId(productId, basketId);
    }

    private Basket getUsersBasket(UUID userId) {
        Optional<Basket> basket = basketRepository.findByUserId(userId);
        UserEntity userEntity = userEntityRepository.findById(userId).orElseThrow();
        return basket.orElseGet(() -> basketRepository.save(new Basket(userEntity)));
    }

    public void increaseProduct(UUID basketId, UUID productId) {
        BasketProduct basketProduct = basketProductRepository.findByBasketId(basketId).orElseThrow();
        basketProduct.setAmount(basketProduct.getAmount() + 1);
        basketProductRepository.save(basketProduct);
    }

    public void decreaseProduct(UUID basketId, UUID productId) {
        BasketProduct basketProduct = basketProductRepository.findByBasketId(basketId).orElseThrow();
        if (basketProduct.getAmount() > 1) {
            basketProduct.setAmount(basketProduct.getAmount() - 1);
            basketProductRepository.save(basketProduct);
        } else {
            throw new RuntimeException("Invalid Amount");
        }
    }

    public void clearBasket(UUID basketId) {
        basketProductRepository.deleteAllByBasketId(basketId);
    }


}
