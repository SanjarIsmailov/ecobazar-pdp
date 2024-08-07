package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.entity.Review;
import uz.pdp.ecommerce.repo.ReviewRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(UUID id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(UUID id, Review review) {
        return reviewRepository.findById(id)
                .map(existingReview -> {
                    existingReview.setViews(review.getViews());
                    return reviewRepository.save(existingReview);
                }).orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    public void deleteReview(UUID id) {
        reviewRepository.deleteById(id);
    }
}
