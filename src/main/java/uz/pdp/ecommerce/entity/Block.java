package uz.pdp.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;
import uz.pdp.ecommerce.entity.templ.AbsEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Block extends AbsEntity {
    private String description;
    private String title;
    @ManyToOne
    private Review review;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Attachment> attachments;
}
