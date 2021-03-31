package com.br.gabryel.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Config {

	@Bean
	@Profile("test")
	public EnvironmentConfig forTest() {
		return new EnvironmentConfig("Aplicação em Teste");
	}

	@Bean
	@Profile("!test")
	public EnvironmentConfig forProduction() {
		return new EnvironmentConfig("Aplicação em Produção");
	}
}
