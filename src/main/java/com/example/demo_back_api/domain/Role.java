package com.example.demo_back_api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_role")
    private Long codRole;
    private String nome;

    public Long getCodRole() {
        return codRole;
    }

    public void setCodRole(Long codRole) {
        this.codRole = codRole;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public enum Values {

        ADMIN(1L),
        BASIC(2L);

        long codRole;

        Values(long codRole) {
            this.codRole = codRole;
        }

        public long getCodRole() {
            return codRole;
        }
    }
}