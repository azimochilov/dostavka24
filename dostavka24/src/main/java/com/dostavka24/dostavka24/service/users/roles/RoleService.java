package com.dostavka24.dostavka24.service.users.roles;


import com.dostavka24.dostavka24.domain.dtos.users.roles.PrivilegeCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.roles.RoleCreationDto;
import com.dostavka24.dostavka24.domain.entities.users.Privilege;
import com.dostavka24.dostavka24.domain.entities.users.Role;
import com.dostavka24.dostavka24.domain.entities.users.RolePrivilege;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.PrivilegeRepository;
import com.dostavka24.dostavka24.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    public Optional<Role> getByName(String name) {

        return Optional.of(roleRepository.findByName(name).orElseThrow(() ->
                new NotFoundException("Not found role with name: " + name)));
    }

    public Optional<Role> getById(Long id) {

        return Optional.of(roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id)));
    }

    public List<Role> getAll()
    {

        return roleRepository.findAll();
    }

    public Optional<Role> update(Long id, RoleCreationDto roleDto) {

        Role role = roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Not found role with id: " + id));

        role.setName(roleDto.getName());

        roleRepository.save(role);

        return Optional.of(role);
    }

    public Optional<Role> create(RoleCreationDto roleDto) {

        Role role = new Role();
        List<RolePrivilege> rolePrivileges = new ArrayList<>();
        for (PrivilegeCreationDto p : roleDto.getPrivileges()) {

            Privilege privilege = privilegeRepository.findByName(p.getName()).orElseThrow(() ->
                    new NotFoundException("Not found privilege with name: " + p.getName()));

            RolePrivilege rolePrivilege = new RolePrivilege();
            rolePrivilege.setPrivilege(privilege);

            rolePrivilege.setRole(role);
        }

        role.setName(roleDto.getName());

        roleRepository.save(role);

        return Optional.of(role);
    }

    public void delete(Long id)
    {

        roleRepository.deleteById(id);
    }
}
