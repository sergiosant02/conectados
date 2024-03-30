package com.sergiosantiago.conectados.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.repository.UserRepository;

@Component
public class DatabaseInitializer {

	private final UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Value("${default.password}")
	private String password;

	public DatabaseInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostConstruct
	public void init() {

		if (userRepository.countByRole("ADMIN") != null && userRepository.countByRole("ADMIN") < 1) {
			User user = new User();
			user.setEmail("sergiosantiago@email.com");
			user.setPassword(passwordEncoder.encode(password));
			user.setRole("ADMIN");
			userRepository.save(user);
		}

	}
}