package uz.pdp.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.ecommerce.entity.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String repeatPassword;
    private List<Role> roles;
}
