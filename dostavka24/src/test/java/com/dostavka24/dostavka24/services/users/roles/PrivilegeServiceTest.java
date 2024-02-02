package com.dostavka24.dostavka24.services.users.roles;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.dostavka24.dostavka24.domain.dtos.users.roles.PrivilegeCreationDto;
import com.dostavka24.dostavka24.domain.entities.users.Privilege;
import com.dostavka24.dostavka24.repository.PrivilegeRepository;
import com.dostavka24.dostavka24.service.users.roles.PrivilegeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

public class PrivilegeServiceTest {

    @Mock
    private PrivilegeRepository privilegeRepository;

    @InjectMocks
    private PrivilegeService privilegeService;

    private Privilege privilege;
    private PrivilegeCreationDto privilegeCreationDto;
    private Long privilegeId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        privilegeId = 1L;
        privilege = new Privilege();
        privilege.setId(privilegeId);
        privilege.setName("Test Privilege");

        privilegeCreationDto = new PrivilegeCreationDto();
        privilegeCreationDto.setName("Test Privilege");

        when(privilegeRepository.save(any(Privilege.class))).thenReturn(privilege);

        when(privilegeRepository.findById(privilegeId)).thenReturn(Optional.of(privilege));

        when(privilegeRepository.findAll()).thenReturn(Collections.singletonList(privilege));

    }

    @Test
    void createPrivilege_Success() {
        Optional<Privilege> created = privilegeService.create(privilegeCreationDto);

        assertTrue(created.isPresent());
        assertEquals("Test Privilege", created.get().getName());
        verify(privilegeRepository).save(any(Privilege.class));
    }

    @Test
    void getPrivilegeById_Success() {
        Optional<Privilege> found = privilegeService.getById(privilegeId);

        assertTrue(found.isPresent());
        assertEquals("Test Privilege", found.get().getName());
        verify(privilegeRepository).findById(privilegeId);
    }

    @Test
    void getAllPrivileges_Success() {
        List<Privilege> privileges = privilegeService.getAll();

        assertNotNull(privileges);
        assertFalse(privileges.isEmpty());
        assertEquals(1, privileges.size());
        verify(privilegeRepository).findAll();
    }

}
