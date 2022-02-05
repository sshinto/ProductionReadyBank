package com.hollandandbarret.bank;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
			//HOW AOP WORKS INTERNALLY
		FactoryService factoryService = new FactoryService();
		AccountServiceImpl accountService = (AccountServiceImpl) factoryService.getBean(
				"AccountService");
		accountService.getAccountName();

	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
