package com.example.demo_back_api.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo_back_api.domain.Role;
import com.example.demo_back_api.domain.User;
import com.example.demo_back_api.dto.CreateUserDto;
import com.example.demo_back_api.repositories.RoleRepository;
import com.example.demo_back_api.repositories.UserRepository;

import jakarta.transaction.Transactional;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {

        var basicRole = roleRepository.findByNome(Role.Values.BASIC.name());

        var userFromDb = userRepository.findByUsername(dto.username());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    } 
    
    @GetMapping("/users")
    public ResponseEntity<?> listUsers(@RequestHeader("Authorization") String authorizationHeader) {
        // Verifica se o cabeçalho de autorização está presente e no formato correto
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de acesso ausente ou no formato incorreto");
        }
        // Extrai o token do cabeçalho de autorização
        String token = authorizationHeader.substring(7); // Remove "Bearer " do início

        // Agora você pode usar o token para autenticação ou qualquer outra lógica necessária
        // Por exemplo, você pode verificar se o token é válido, se está expirado, etc.

        // Se tudo estiver correto, você pode prosseguir com a lógica para listar os usuários
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    } 
}