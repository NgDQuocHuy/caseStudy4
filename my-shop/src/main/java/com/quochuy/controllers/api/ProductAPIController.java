package com.quochuy.controllers.api;

import com.quochuy.exception.DataInputException;
import com.quochuy.exception.EmailExistsException;
import com.quochuy.models.dto.product.ProductDTO;
import com.quochuy.models.dto.product.ProductImageCreateDTO;
import com.quochuy.models.dto.product.TypeDTO;
import com.quochuy.models.product.Product;
import com.quochuy.models.product.Type;
import com.quochuy.services.Type.ITypeService;
import com.quochuy.services.product.IProductService;
import com.quochuy.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductAPIController {

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private IProductService productService;

    @Autowired
    private ITypeService typeService;

    @GetMapping
    public ResponseEntity<?> findAllByDeletedIsFalse() {

        List<ProductDTO> products = productService.getAllProductDTOByDeletedIsFalse();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<?> findAllType() {

        List<TypeDTO> types = typeService.getAllTypeDTO();

        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getById(@PathVariable long productId) {

        Optional<Product> productOpt = productService.findById(productId);

        if (!productOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = productOpt.get();

        return new ResponseEntity<>(product.toProductDTO(), HttpStatus.OK);
    }

    @GetMapping("/edit/{productId}")
    public ResponseEntity<?> showEditProduct(@PathVariable long productId) {
        Optional<Product> productOpt = productService.findById(productId);

        if (!productOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = productOpt.get();

        return new ResponseEntity<>(product.toProductDTO(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @ModelAttribute ProductImageCreateDTO productImageCreateDTO, BindingResult bindingResult) {
        List<ObjectError> errors;
        List<String> allErrors = new ArrayList<String>();

        Boolean existsByProductTitle = productService.existsByTitle(productImageCreateDTO.getTitle());

        Optional<Type> optType = typeService.findById(Long.valueOf(productImageCreateDTO.getType()));

        try {
            if (existsByProductTitle) {
                allErrors.add("Tên sản phẩm đã tồn tại trong hệ thống");
                throw new DataInputException(allErrors.toString());
            }
            if (!optType.isPresent()) {
                allErrors.add("Loại sản phẩm không tồn tại");
                throw new DataInputException(allErrors.toString());
            }
            if (bindingResult.hasErrors()) {
                errors = bindingResult.getAllErrors();
                for (ObjectError error : errors) {
                    allErrors.add(error.getDefaultMessage());
                }
                throw new DataInputException(allErrors.toString());
            } else {
                productImageCreateDTO.setId(0L);
                Product product = productImageCreateDTO.toProductCreate(optType.get());

                Product newCustomer = productService.saveWithAvatar(product, productImageCreateDTO.getFile());

                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        } catch (Exception e) {
            return new ResponseEntity<>(allErrors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<?> update(@PathVariable long productId, @Valid @ModelAttribute ProductImageCreateDTO productImageCreateDTO, BindingResult bindingResult) {
        List<ObjectError> errors;
        List<String> allErrors = new ArrayList<String>();

        Optional<Type> optType = typeService.findById(Long.valueOf(productImageCreateDTO.getType()));
        Optional<Product> optProduct = productService.findById(productId);


        try {
            if (!optProduct.isPresent()) {
                allErrors.add("Không tìm thấy sản phẩm trong hệ thống.");
                throw new DataInputException(allErrors.toString());
            }
            if (!optType.isPresent()) {
                allErrors.add("Loại sản phẩm không tồn tại");
                throw new DataInputException(allErrors.toString());
            }
            if (bindingResult.hasErrors()) {
                errors = bindingResult.getAllErrors();
                for (ObjectError error : errors) {
                    allErrors.add(error.getDefaultMessage());
                }
                throw new DataInputException(allErrors.toString());
            } else {
                productImageCreateDTO.setId(productId);
                Product product = productImageCreateDTO.toProductCreate(optType.get());

                Product newCustomer = productService.saveWithAvatar(product, productImageCreateDTO.getFile());
                return new ResponseEntity<>(newCustomer.toProductDTO(), HttpStatus.ACCEPTED);
            }
        } catch (
                DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ProductDTO> delete(@PathVariable long productId) {

        Optional<Product> productOpt = productService.findById(productId);

        if (!productOpt.isPresent()) {
            throw new DataInputException("Thông tin sản phẩm cần xóa không hợp lệ");
        }

        try {
            productService.softDelete(productId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataInputException("Vui lòng liên hệ Administrator");
        }
    }
}
