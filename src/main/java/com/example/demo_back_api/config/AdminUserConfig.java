package com.example.demo_back_api.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_back_api.domain.Role;
import com.example.demo_back_api.domain.User;
import com.example.demo_back_api.repositories.RoleRepository;
import com.example.demo_back_api.repositories.UserRepository;  

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AdminUserConfig implements CommandLineRunner {

	private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
	public void run(String... args) throws Exception {
		var roleAdmin = roleRepository.findByNome(Role.Values.ADMIN.name());

		var userAdmin = userRepository.findByUsername("admin");

		if (userAdmin.isPresent()) {
			System.out.println("admin já existe");
		} else {
			var user = new User();
			user.setUsername("admin");
			user.setPassword(passwordEncoder.encode("123"));
			user.setRoles(Set.of(roleAdmin));
			userRepository.save(user);
			System.out.println("admin inserido com sucesso");
		}
	}
}
