package uz.pdp.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.entity.UserEntity;
import uz.pdp.ecommerce.repo.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity updateUser(UUID id, UserEntity userEntity) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(userEntity.getFirstName());
                    existingUser.setLastName(userEntity.getLastName());
                    existingUser.setEmail(userEntity.getEmail());
                    existingUser.setPassword(userEntity.getPassword());
                    existingUser.setPhone(userEntity.getPhone());
                    existingUser.setAddress(userEntity.getAddress());
                    existingUser.setImage(userEntity.getImage());
                    existingUser.setRoles(userEntity.getRoles());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
