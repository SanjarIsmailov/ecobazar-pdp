package uz.pdp.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommerce.repo.BasketRepository;
import uz.pdp.ecommerce.repo.OrderProductRepository;
import uz.pdp.ecommerce.service.BasketService;

import java.util.UUID;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;


    @PostMapping("/add")
    public ResponseEntity<Void> addToBasket(@RequestParam UUID userId,
                                            @RequestParam UUID basketId,
                                            @RequestParam UUID productId) {
        basketService.addToBasket(userId, basketId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromBasket(@RequestParam UUID basketId,
                                                 @RequestParam UUID productId) {
        basketService.removeFromBasket(basketId, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/increase")
    public ResponseEntity<Void> increaseProduct(@RequestParam UUID basketId,
                                                @RequestParam UUID productId) {
        basketService.increaseProduct(basketId, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decrease")
    public ResponseEntity<Void> decreaseProduct(@RequestParam UUID basketId,
                                                @RequestParam UUID productId) {
        basketService.decreaseProduct(basketId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearBasket(@RequestParam UUID basketId) {
        basketService.clearBasket(basketId);
        return ResponseEntity.ok().build();
    }

}
