package uz.pdp.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer stockAmount;
    @OneToMany
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
}
