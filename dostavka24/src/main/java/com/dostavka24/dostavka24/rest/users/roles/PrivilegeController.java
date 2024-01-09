package com.dostavka24.dostavka24.rest.users.roles;


import com.dostavka24.dostavka24.domain.dtos.users.roles.PrivilegeCreationDto;
import com.dostavka24.dostavka24.domain.entities.users.Privilege;
import com.dostavka24.dostavka24.service.users.roles.PrivilegeService;
import com.dostavka24.dostavka24.service.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/privilege")
//@PreAuthorize("hasAuthority('ROLE_PRIVILEGE_SERVICE')")
public class PrivilegeController {
    private final PrivilegeService privilegeService;

    @PostMapping
    public void create(@RequestBody PrivilegeCreationDto privilegeDto) {
        privilegeService.create(privilegeDto);
    }

    @GetMapping
    public List<Privilege> getAll() {
        return privilegeService.getAll();
    }

    @GetMapping("/{id}")
    public PrivilegeCreationDto getById(@PathVariable Long id) {
        return ConvertDtoUtils.convertPrivilegeToDto(privilegeService.getById(id).orElseThrow());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        privilegeService.delete(id);
    }
}
