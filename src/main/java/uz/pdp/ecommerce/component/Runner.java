package uz.pdp.ecommerce.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import uz.pdp.ecommerce.entity.Role;
import uz.pdp.ecommerce.entity.enums.RoleName;
import uz.pdp.ecommerce.repo.RoleRepo;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor

public class Runner implements CommandLineRunner {
    private final RoleRepo roleRepo;
    @Override
    public void run(String... args) throws Exception {

        List<Role> roles = Arrays.stream(RoleName.values()).map(Role::new).toList();
        roleRepo.saveAll(roles);


    }
}
