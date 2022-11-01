package com.quochuy.models.product;

import com.quochuy.models.dto.product.TypeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "type")
@Accessors(chain = true)
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    public TypeDTO toTypeDTO() {
        return new TypeDTO()
                .setId(id)
                .setType(type);
    }
}
