package com.dostavka24.dostavka24.repository;

import com.dostavka24.dostavka24.domain.entities.users.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {
}
