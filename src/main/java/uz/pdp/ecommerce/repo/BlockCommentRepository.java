package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.BlogComment;

import java.util.List;
import java.util.UUID;

public interface BlockCommentRepository extends JpaRepository<BlogComment, UUID> {
    List<BlogComment> findByBlogId(UUID blogId);
}