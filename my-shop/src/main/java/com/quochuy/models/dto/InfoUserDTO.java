package com.quochuy.models.dto;

import com.quochuy.models.InfoUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class InfoUserDTO {

    private long id;
    private String fullName;
    private String phone;

    public InfoUser toInfoUser () {
        return new InfoUser()
                .setId(id)
                .setFullName(fullName)
                .setPhone(phone);
    }
}
