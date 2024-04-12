package com.example.demo_back_api.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_back_api.domain.Paciente;
import com.example.demo_back_api.dto.CreatePacienteDto;
import com.example.demo_back_api.repositories.PacienteRepository;
import com.example.demo_back_api.repositories.UserRepository;

@RestController
public class PacienteController {

	private final PacienteRepository pacienteRepository;
	private final UserRepository userRepository;

	public PacienteController(PacienteRepository pacienteRepository, UserRepository userRepository) {
		this.pacienteRepository = pacienteRepository;
		this.userRepository = userRepository;
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Void> createTweet(@RequestBody CreatePacienteDto dto, JwtAuthenticationToken token) {
		var user = userRepository.findById(UUID.fromString(token.getName()));

		var paciente = new Paciente();
		paciente.setUser(user.get());
		paciente.setNomePaciente(dto.nomePaciente());

		pacienteRepository.save(paciente);

		return ResponseEntity.ok().build();
	}

	
	@DeleteMapping("/tweets/{id}")
	public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId, JwtAuthenticationToken token) {
		var user = userRepository.findById(UUID.fromString(token.getName()));
		var tweet = tweetRepository.findById(tweetId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		var isAdmin = user.get().getRoles().stream()
				.anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

		if (isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
			tweetRepository.deleteById(tweetId);

		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		return ResponseEntity.ok().build();
	}
	 
}
