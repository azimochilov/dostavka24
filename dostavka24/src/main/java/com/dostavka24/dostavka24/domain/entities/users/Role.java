package com.dostavka24.dostavka24.domain.entities.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Entity
@Table(name = "Role")
public class Role implements Serializable {
    @Id
    private String name;

    @jakarta.persistence.Id
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
