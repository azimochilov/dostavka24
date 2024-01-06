package com.dostavka24.dostavka24.rest.users.roles;


import com.dostavka24.dostavka24.domain.dtos.users.roles.RoleCreationDto;
import com.dostavka24.dostavka24.domain.entities.users.Role;
import com.dostavka24.dostavka24.domain.entities.users.RolePrivilege;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.service.users.roles.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@PreAuthorize("hasAuthority('ROLE_PRIVILEGE_SERVICE')")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public void create(@RequestBody RoleCreationDto roleDto) {
        roleService.create(roleDto);
    }

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public List<RolePrivilege> getPrivilegeById(@PathVariable Long id) {
        Role role = roleService.getById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id));
        return role.getRolePrivileges();
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody RoleCreationDto roleDto) {
        roleService.update(id, roleDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }
}
