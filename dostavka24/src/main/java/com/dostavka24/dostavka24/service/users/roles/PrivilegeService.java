package com.dostavka24.dostavka24.service.users.roles;

import com.dostavka24.dostavka24.domain.dtos.users.roles.PrivilegeCreationDto;
import com.dostavka24.dostavka24.domain.entities.users.Privilege;
import com.dostavka24.dostavka24.repository.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivilegeService {
    private final PrivilegeRepository privilegeRepository;
    public Optional<Privilege> create(PrivilegeCreationDto privilegeDto) {
        return Optional.of(privilegeRepository.save(Privilege.builder().name(privilegeDto.getName()).build()));
    }

    public Optional<Privilege> getById(Long id) {
        return Optional.empty();
    }

    public Optional<Privilege> update(Long id, PrivilegeCreationDto privilegeDto) {
        return Optional.empty();
    }

    public List<Privilege> getAll() {
        return null;
    }

    public void delete(Long id) {

    }
}
