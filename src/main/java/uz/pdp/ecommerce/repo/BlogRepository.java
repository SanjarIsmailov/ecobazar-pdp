package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.Blog;

import java.util.UUID;

public interface BlogRepository extends JpaRepository<Blog, UUID> {
}