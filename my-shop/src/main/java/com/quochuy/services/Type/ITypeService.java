package com.quochuy.services.Type;

import com.quochuy.models.dto.product.TypeDTO;
import com.quochuy.models.product.Type;
import com.quochuy.services.IGeneralService;

import java.util.List;

public interface ITypeService extends IGeneralService<Type> {
    Type findByName(String name);

    List<TypeDTO> getAllTypeDTO();
}
