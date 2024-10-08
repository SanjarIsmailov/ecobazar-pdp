package uz.pdp.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.ecommerce.entity.enums.RoleName;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Override
    public String getAuthority() {
        return name.toString();
    }

    public Role(RoleName name) {
        this.name = name;
    }
}
