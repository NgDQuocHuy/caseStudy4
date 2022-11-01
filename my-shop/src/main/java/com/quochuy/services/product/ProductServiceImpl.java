package com.quochuy.services.product;

import com.quochuy.exception.DataInputException;
import com.quochuy.models.Avatar;
import com.quochuy.models.InfoUser;
import com.quochuy.models.LocationRegion;
import com.quochuy.models.User;
import com.quochuy.models.dto.product.ProductDTO;
import com.quochuy.models.dto.product.TypeDTO;
import com.quochuy.models.enums.FileType;
import com.quochuy.models.product.Image;
import com.quochuy.models.product.Product;
import com.quochuy.models.product.Type;
import com.quochuy.repositorys.ImageRepository;
import com.quochuy.repositorys.ProductRepository;
import com.quochuy.services.upload.UploadService;
import com.quochuy.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService{

    @Autowired
    private UploadUtil uploadUtil;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product saveWithAvatar(Product product, MultipartFile imageFile) {

        Image image = new Image();

        String fileType = imageFile.getContentType();

        assert fileType != null;

        fileType = fileType.substring(0, 5);

        image.setFileType(fileType);
        Image newImage = imageRepository.save(image);

        product.setImage(newImage);
        Product newProduct = productRepository.save(product);

        if (fileType.equals(FileType.IMAGE.getValue())) {
            uploadAndSaveProductImage(imageFile, newProduct, newImage);
        }

        return newProduct;
    }

    private void uploadAndSaveProductImage(MultipartFile imageFile, Product product, Image image) {
        try {
            Map uploadResult = uploadService.uploadImage(imageFile, uploadUtil.buildImageUploadParams(image));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            image.setFileName(image.getId() + "." + fileFormat);
            image.setFileUrl(fileUrl);
            image.setFileFolder(UploadUtil.IMAGE_UPLOAD_FOLDER);
            image.setCloudId(image.getFileFolder() + "/" + image.getId());
            imageRepository.save(image);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public void softDelete(long productId) {
        productRepository.softDelete(productId);
    }

    @Override
    public List<ProductDTO> getAllProductDTOByDeletedIsFalse() {
        return productRepository.getAllProductDTOByDeletedIsFalse();
    }

    @Override
    public Boolean existsByTitle(String title) {
        return productRepository.existsByTitle(title);
    }
}
