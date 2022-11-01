package com.quochuy.repositorys;

import com.quochuy.models.dto.UserDTO;
import com.quochuy.models.dto.product.TypeDTO;
import com.quochuy.models.product.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    @Query("SELECT NEW com.quochuy.models.dto.product.TypeDTO(" +
            "t.id, " +
            "t.type " +
            ")" +
            "FROM Type AS t "
    )
    List<TypeDTO> getAllTypeDTO();
}
