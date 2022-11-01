package com.quochuy.models.dto;


import com.quochuy.models.InfoUser;
import com.quochuy.models.Role;
import com.quochuy.models.User;
import com.quochuy.models.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserAvatarCreateDTO {

    private Long id;
    private String username;
    private String password;

    private String fullName;
    private String phone;

    private Long roleId;

    MultipartFile file;

    private Long locationId;
    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardId;
    private String wardName;
    private String address;

    public User toUser(Role role, InfoUser infoUser, LocationRegion locationRegion){
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role)
                .setInfoUser(infoUser)
                .setLocationRegion(locationRegion);
    }
}
