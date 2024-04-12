package com.example.demo_back_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_back_api.domain.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{ 
}
