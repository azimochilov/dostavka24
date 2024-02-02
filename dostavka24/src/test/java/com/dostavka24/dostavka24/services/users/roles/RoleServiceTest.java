package com.dostavka24.dostavka24.services.users.roles;

import com.dostavka24.dostavka24.domain.dtos.users.roles.PrivilegeCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.roles.RoleCreationDto;
import com.dostavka24.dostavka24.domain.entities.users.Privilege;
import com.dostavka24.dostavka24.domain.entities.users.Role;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.PrivilegeRepository;
import com.dostavka24.dostavka24.repository.RoleRepository;
import com.dostavka24.dostavka24.service.users.roles.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PrivilegeRepository privilegeRepository;
    @InjectMocks
    private RoleService roleService;

    private Role testRole;
    private Privilege testPrivilege;
    private RoleCreationDto roleCreationDto;
    private PrivilegeCreationDto privilegeCreationDto;

    @BeforeEach
    public void setUp() {
        testRole = new Role();
        testRole.setId(1L);
        testRole.setName("Admin");

        testPrivilege = new Privilege();
        testPrivilege.setId(1L);
        testPrivilege.setName("READ_PRIVILEGE");

        privilegeCreationDto = new PrivilegeCreationDto();
        privilegeCreationDto.setName("READ_PRIVILEGE");

        roleCreationDto = RoleCreationDto.builder()
                .name("Admin")
                .privileges(Collections.singletonList(privilegeCreationDto))
                .build();
    }

    @Test
    void whenGetByName_thenReturnRole() {
        when(roleRepository.findByName("Admin")).thenReturn(Optional.of(testRole));

        Optional<Role> foundRole = roleService.getByName("Admin");

        assertTrue(foundRole.isPresent());
        assertEquals("Admin", foundRole.get().getName());
    }

    @Test
    void whenGetByName_thenThrowNotFoundException() {
        when(roleRepository.findByName("Unknown")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.getByName("Unknown"));
    }

    @Test
    void whenGetById_thenReturnRole() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(testRole));

        Optional<Role> foundRole = roleService.getById(1L);

        assertTrue(foundRole.isPresent());
        assertEquals(1L, foundRole.get().getId());
    }

    @Test
    void whenGetById_thenThrowNotFoundException() {
        when(roleRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.getById(2L));
    }

    @Test
    void whenGetAll_thenReturnRoleList() {
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(testRole));

        List<Role> roles = roleService.getAll();

        assertFalse(roles.isEmpty());
        assertEquals(1, roles.size());
        assertEquals("Admin", roles.get(0).getName());
    }

    @Test
    void whenUpdate_thenReturnUpdatedRole() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(testRole));
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Role updatedRole = roleService.update(1L, roleCreationDto).orElseThrow();

        assertEquals("Admin", updatedRole.getName());
    }

    @Test
    void whenUpdate_thenThrowNotFoundException() {
        when(roleRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.update(3L, roleCreationDto));
    }

    @Test
    void whenCreate_thenReturnNewRole() {
        when(privilegeRepository.findByName("READ_PRIVILEGE")).thenReturn(Optional.of(testPrivilege));
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Role createdRole = roleService.create(roleCreationDto).orElseThrow();

        assertEquals("Admin", createdRole.getName());
        assertFalse(createdRole.getRolePrivileges().isEmpty());
    }

    @Test
    void whenCreate_thenThrowNotFoundExceptionForPrivilege() {
        when(privilegeRepository.findByName("READ_PRIVILEGE")).thenThrow(new NotFoundException("..."));


        assertThrows(NotFoundException.class, () -> roleService.create(roleCreationDto));
    }

    @Test
    void whenDelete_thenSuccess() {
        doNothing().when(roleRepository).deleteById(1L);

        assertDoesNotThrow(() -> roleService.delete(1L));
        verify(roleRepository).deleteById(1L);
    }
}

