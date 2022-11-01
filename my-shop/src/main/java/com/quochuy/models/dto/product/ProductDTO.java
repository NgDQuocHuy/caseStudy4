package com.quochuy.models.dto.product;

import com.quochuy.models.product.Image;
import com.quochuy.models.product.Product;
import com.quochuy.models.product.Type;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductDTO {
    private Long id;
    @Length(message = "họn 6 ký tự ko qua 10", min = 6, max = 10)
    private String title;
    private BigDecimal price;
    private TypeDTO type;

    private String image;

    public ProductDTO(Long id, String title, BigDecimal price, Type type, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.type = type.toTypeDTO();
        this.image = image;
    }

    public Product toProduct() {
        return new Product()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setType(type.toType());
    }
}
