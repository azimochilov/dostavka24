package com.dostavka24.dostavka24.domain.dtos.users.roles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RoleCreationDto {
    private String name;
    private List<PrivilegeCreationDto> privileges;

}
