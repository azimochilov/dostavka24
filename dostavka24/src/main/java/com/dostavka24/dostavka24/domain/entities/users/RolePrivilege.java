package com.dostavka24.dostavka24.domain.entities.users;

import jakarta.persistence.*;

@Entity
public class RolePrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Privilege getPrivilege() {
        return privilege;
    }



    public Role getRole() {
        return role;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
        privilege.getRolePrivileges().add(this);
    }

    public void setRole(Role role) {
        this.role = role;
        role.getRolePrivileges().add(this);
    }
}
