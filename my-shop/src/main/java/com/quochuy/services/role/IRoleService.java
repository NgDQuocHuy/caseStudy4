package com.quochuy.services.role;

import com.quochuy.models.Role;
import com.quochuy.services.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
