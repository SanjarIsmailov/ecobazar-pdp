package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.entity.Rating;
import uz.pdp.ecommerce.repo.RatingRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> getRatingById(UUID id) {
        return ratingRepository.findById(id);
    }

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating updateRating(UUID id, Rating rating) {
        return ratingRepository.findById(id)
                .map(existingRating -> {
                    existingRating.setUser(rating.getUser());
                    existingRating.setProduct(rating.getProduct());
                    existingRating.setStars(rating.getStars());
                    return ratingRepository.save(existingRating);
                }).orElseThrow(() -> new IllegalArgumentException("Rating not found"));
    }

    public void deleteRating(UUID id) {
        ratingRepository.deleteById(id);
    }
}
