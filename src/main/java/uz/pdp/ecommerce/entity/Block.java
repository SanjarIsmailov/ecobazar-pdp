package uz.pdp.ecommerce.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.ecommerce.entity.templ.AbsEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Block extends AbsEntity {
    private String description;
}
