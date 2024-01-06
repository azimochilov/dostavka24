package com.dostavka24.dostavka24.service.utils;


import com.dostavka24.dostavka24.domain.dtos.users.roles.PrivilegeCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.roles.RoleCreationDto;
import com.dostavka24.dostavka24.domain.entities.users.Privilege;
import com.dostavka24.dostavka24.domain.entities.users.Role;

public class ConvertDtoUtils {
    public static PrivilegeCreationDto convertPrivilegeToDto(Privilege privilege) {
        return PrivilegeCreationDto.builder()
                .name(privilege.getName())
                .build();
    }
}

