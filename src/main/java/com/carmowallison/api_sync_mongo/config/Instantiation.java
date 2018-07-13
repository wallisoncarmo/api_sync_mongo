package com.carmowallison.api_sync_mongo.config;

import java.util.Arrays;

import com.carmowallison.api_sync_mongo.domain.enums.Perfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.carmowallison.api_sync_mongo.domain.User;
import com.carmowallison.api_sync_mongo.repositoties.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bc;

	@Override
	public void run(String... args) throws Exception {

		// EXCLUIR TODA A BASE
		userRepository.deleteAll();

		// CRIA NOVA BASE

		// BLOCO USUARIOS
		User wallison = new User(null, "Wallison do Carmo Costa", "admin@email.com", true, bc.encode("123"));
		wallison.addPerfil(Perfil.ADMIN);

		User yasmin = new User(null, "yasmin", "yasmin@gmail.com", false, bc.encode("123"));
		User lucy = new User(null, "lucy", "lucy@gmail.com", true, bc.encode("123"));

		userRepository.saveAll(Arrays.asList(wallison, yasmin, lucy));

	}

}
