package com.quochuy.services.user;

import com.quochuy.models.User;
import com.quochuy.models.dto.UserDTO;
import com.quochuy.models.dto.UserRegisterDTO;
import com.quochuy.services.IGeneralService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {

    List<User> findAllByDeletedIsFalse();

    List<UserDTO> getAllUserDTOByDeletedIsFalse();

    List<User> findAllByIdNot(long id);

    Boolean existsByIdEquals(long id);

    void softDelete(@Param("userId") long userId);

    User getByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<UserRegisterDTO> findUserDTOByUsername(String username);

    public User saveWithAvatar(User user, MultipartFile avatarFile);

    Boolean existsByUsername(String email);


}
