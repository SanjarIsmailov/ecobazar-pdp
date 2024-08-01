package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.BlockComment;

import java.util.UUID;

public interface BlockCommentRepository extends JpaRepository<BlockComment, UUID> {
}