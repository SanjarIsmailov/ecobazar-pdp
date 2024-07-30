package uz.pdp.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.ecommerce.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
