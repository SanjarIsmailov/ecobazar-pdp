package uz.pdp.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;
import uz.pdp.ecommerce.entity.templ.AbsEntity;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@SuperBuilder
@Entity
@Table
public class Product extends AbsEntity {
    private String name;
    private String description;
    private Double price;
    private Integer stockAmount;
    private Double shippingPrice;
    @OneToMany
    @ToString.Exclude
    private List<Attachment> images;
    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Category category;
    private Double rating;
    @ManyToOne
    private Discount discount;
    @ManyToOne
    private Review review;
    @OneToOne
    private Attachment video;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
