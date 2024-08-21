package uz.pdp.ecommerce.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import uz.pdp.ecommerce.entity.templ.AbsEntity;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Discount extends AbsEntity {
    private Double percentage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Discount discount = (Discount) o;
        return getId() != null && Objects.equals(getId(), discount.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
