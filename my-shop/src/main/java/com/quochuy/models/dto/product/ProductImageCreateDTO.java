package com.quochuy.models.dto.product;

import com.quochuy.models.User;
import com.quochuy.models.product.Product;
import com.quochuy.models.product.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductImageCreateDTO {
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống.")
    @Length(message = "Tên sản phẩm chứa từ 2 đến 20 ký tự", min = 2, max = 20)
    private String title;

    @Min(message = "Giá sản phẩm thấp nhất là 100.000 Vnđ.", value = 100000)
    @Max(message = "Giá sản phẩm cao nhất là 100.000.000 Vnđ", value = 100000000)
    private BigDecimal price;

    MultipartFile file;

    @NotNull(message = "Vui lòng chọn loại sản phẩm.")
    private Long type;

    public Product toProductCreate(Type type) {
        return new Product()
                .setId(id)
                .setTitle(title)
                .setPrice(new BigDecimal(String.valueOf(price)))
                .setType(type);
    }
}
