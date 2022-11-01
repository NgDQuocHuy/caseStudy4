package com.quochuy.repositorys;

import com.quochuy.models.dto.UserDTO;
import com.quochuy.models.dto.product.ProductDTO;
import com.quochuy.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("UPDATE Product AS p " +
            "SET p.deleted = true " +
            "WHERE p.id = :productId")
    void softDelete(@Param("productId") long productId);

    @Query("SELECT NEW com.quochuy.models.dto.product.ProductDTO(" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.type, " +
            "p.image.id" +
            ")" +
            "FROM Product AS p " +
            " WHERE p.deleted = false")
    List<ProductDTO> getAllProductDTOByDeletedIsFalse();

    Boolean existsByTitle(String title);
}
