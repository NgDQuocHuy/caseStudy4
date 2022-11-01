package com.quochuy.models.dto;

import com.quochuy.models.InfoUser;
import com.quochuy.models.LocationRegion;
import com.quochuy.models.Role;
import com.quochuy.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private RoleDTO role;
    private InfoUserDTO infoUser;
    private LocationRegionDTO locationRegion;

    public UserDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDTO(Long id, String username, Role role, InfoUser infoUser, LocationRegion locationRegion) {
        this.id = id;
        this.username = username;
        this.role = role.toRoleDTO();
        this.infoUser = infoUser.toInfoUserDTO();
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }

    public User toUser() {
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role.toRole())
                .setInfoUser(infoUser.toInfoUser())
                .setLocationRegion(locationRegion.toLocationRegion());
    }
}
