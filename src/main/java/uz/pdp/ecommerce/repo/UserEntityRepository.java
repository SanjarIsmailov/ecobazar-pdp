package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.ecommerce.entity.UserEntity;

import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByEmail(String username);
}