package com.quochuy.controllers.api;

import com.quochuy.exception.DataInputException;
import com.quochuy.exception.EmailExistsException;
import com.quochuy.models.dto.UserAvatarCreateDTO;
import com.quochuy.models.dto.UserLoginDTO;
import com.quochuy.models.JwtResponse;
import com.quochuy.models.InfoUser;
import com.quochuy.models.LocationRegion;
import com.quochuy.models.Role;
import com.quochuy.models.User;
import com.quochuy.services.jwt.JwtService;
import com.quochuy.services.role.IRoleService;
import com.quochuy.services.user.IUserService;
import com.quochuy.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private AppUtil appUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody @ModelAttribute UserAvatarCreateDTO userAvatarCreateDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }
        Boolean existsByUsername = userService.existsByUsername(userAvatarCreateDTO.getUsername());

        Optional<Role> optRole = roleService.findById(Long.valueOf(userAvatarCreateDTO.getRoleId()));

        if (existsByUsername) {
            throw new EmailExistsException("Account already exists");
        }

        if (!optRole.isPresent()) {
            throw new DataInputException("Invalid account role");
        }

        try {
            LocationRegion locationRegion = new LocationRegion();
            locationRegion.setId(0L);
            locationRegion.setProvinceId(userAvatarCreateDTO.getProvinceId());
            locationRegion.setProvinceName(userAvatarCreateDTO.getProvinceName());
            locationRegion.setDistrictId(userAvatarCreateDTO.getDistrictId());
            locationRegion.setDistrictName(userAvatarCreateDTO.getDistrictName());
            locationRegion.setWardId(userAvatarCreateDTO.getWardId());
            locationRegion.setWardName(userAvatarCreateDTO.getWardName());
            locationRegion.setAddress(userAvatarCreateDTO.getAddress());

            InfoUser infoUser = new InfoUser();
            infoUser.setId(0L);
            infoUser.setFullName(userAvatarCreateDTO.getFullName());
            infoUser.setPhone(userAvatarCreateDTO.getPhone());

            userAvatarCreateDTO.setId(0L);
            User user = userAvatarCreateDTO.toUser(optRole.get(), infoUser, locationRegion);

            User newUser = userService.saveWithAvatar(user, userAvatarCreateDTO.getFile());
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.getByUsername(userLoginDTO.getUsername());

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    currentUser.getUsername(),
                    userDetails.getAuthorities()
            );

            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(60 * 1000)
                    .domain("localhost")
                    .build();

            System.out.println(jwtResponse);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
