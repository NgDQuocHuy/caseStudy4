package com.quochuy.models;

import com.quochuy.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name = "location_region_id", referencedColumnName = "id", nullable = false)
    private LocationRegion locationRegion;

    @OneToOne
    @JoinColumn(name = "info_user_id", referencedColumnName = "id", nullable = false)
    private InfoUser infoUser;

    @OneToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "id", nullable = false)
    private Avatar avatar;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public UserDTO toUserDTO() {
        return new UserDTO()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role.toRoleDTO())
                .setInfoUser(infoUser.toInfoUserDTO())
                .setLocationRegion(locationRegion.toLocationRegionDTO());
    }

}
