package com.quochuy.services.Type;

import com.quochuy.models.dto.product.TypeDTO;
import com.quochuy.models.product.Type;
import com.quochuy.repositorys.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements ITypeService{
    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public Type getById(Long id) {
        return typeRepository.getById(id);
    }

    @Override
    public Optional<Type> findById(Long id) {
        return typeRepository.findById(id);
    }

    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Type findByName(String name) {
        return null;
    }

    @Override
    public List<TypeDTO> getAllTypeDTO() {
        return typeRepository.getAllTypeDTO();
    }
}
