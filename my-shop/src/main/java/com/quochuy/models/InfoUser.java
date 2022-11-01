package com.quochuy.models;

import com.quochuy.models.dto.InfoUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "info_users")
@Accessors(chain = true)
public class InfoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    private String fullName;

    private String phone;

    public InfoUserDTO toInfoUserDTO() {
        return new InfoUserDTO()
                .setId(id)
                .setFullName(fullName)
                .setPhone(phone);
    }
}
