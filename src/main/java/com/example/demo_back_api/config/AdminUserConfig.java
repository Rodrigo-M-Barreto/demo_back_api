package com.example.demo_back_api.config;

import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo_back_api.domain.Role;
import com.example.demo_back_api.domain.User;
import com.example.demo_back_api.repositories.RoleRepository;
import com.example.demo_back_api.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig {

	private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByNome(Role.Values.ADMIN.name());

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user = new User();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("@dm1n1str4t0r1408"));
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                }
        );
    }
}
