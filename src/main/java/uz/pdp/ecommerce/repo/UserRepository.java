package uz.pdp.ecommerce.repo;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommerce.entity.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
