package com.quochuy.models.dto.product;

import com.quochuy.models.product.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class TypeDTO {
    private Long id;
    private String type;

    public Type toType() {
        return new Type()
                .setId(id)
                .setType(type);
    }
}
