package com.quochuy.models.product;

import com.quochuy.models.BaseEntity;
import com.quochuy.models.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
@Accessors(chain = true)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false)
    private Image image;

    @OneToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private Type type;

    public ProductDTO toProductDTO() {
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setImage(image.getId())
                .setType(type.toTypeDTO())
                ;
    }
}
