package br.com.fiap.iNature;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Api do iNature", description = "Projeto voltado para questões ambientais e sustentabilidade", version = "v1"))
public class INatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(INatureApplication.class, args);
	}

}
