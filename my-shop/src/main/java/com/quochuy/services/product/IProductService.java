package com.quochuy.services.product;

import com.quochuy.models.User;
import com.quochuy.models.dto.product.ProductDTO;
import com.quochuy.models.product.Product;
import com.quochuy.services.IGeneralService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService extends IGeneralService<Product> {

    public Product saveWithAvatar(Product product, MultipartFile avatarFile);

    void softDelete(@Param("productId") long productId);

    List<ProductDTO> getAllProductDTOByDeletedIsFalse();

    Boolean existsByTitle(String title);
}
