package com.quochuy.controllers.api;

import com.quochuy.models.dto.UserDTO;
import com.quochuy.models.dto.product.ProductDTO;
import com.quochuy.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserApiController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<?> findAlUserByDeletedIsFalse() {

        List<UserDTO> users = userService.getAllUserDTOByDeletedIsFalse();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
