package com.quochuy.repositorys;

import com.quochuy.models.User;
import com.quochuy.models.dto.UserDTO;
import com.quochuy.models.dto.UserRegisterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByDeletedIsFalse();

    @Query("SELECT NEW com.quochuy.models.dto.UserDTO(" +
                "u.id, " +
                "u.username, " +
                "u.role, " +
                "u.infoUser, " +
                "u.locationRegion" +
            ")" +
            "FROM User AS u " +
            " WHERE u.deleted = false")
    List<UserDTO> getAllUserDTOByDeletedIsFalse();

    List<User> findAllByIdNot(long id);

    Boolean existsByIdEquals(long id);

    @Modifying
    @Query("UPDATE User AS u SET u.deleted = true WHERE u.id = :userId")
    void softDelete(@Param("userId") long userId);

    User getByUsername(String username);

    Optional<User> findByUsername(String username);


    @Query("SELECT NEW com.quochuy.models.dto.UserRegisterDTO (" +
            "u.id, " +
            "u.username" +
            ") " +
            "FROM User u " +
            "WHERE u.username = ?1"
    )
    Optional<UserRegisterDTO> findUserDTOByUsername(String username);


    Boolean existsByUsername(String username);


}
