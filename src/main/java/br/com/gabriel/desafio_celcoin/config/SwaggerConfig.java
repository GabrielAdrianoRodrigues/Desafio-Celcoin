package br.com.gabriel.desafio_celcoin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
     public OpenAPI customOpenAPI() {
       return new OpenAPI()
                    // .components(new Components()
                    //     .addSecuritySchemes("bearer-key", 
                    //         new SecurityScheme()
                    //             .type(SecurityScheme.Type.HTTP)
                    //                 .scheme("bearer")
                    //                     .bearerFormat("JWT")))
                        .info(new Info()
                                    .title("Desafio Celcoin")
                                    .description("API desenvolvida para gerenciar dividas e pagamentos")
                                    .contact(new Contact()
                                        .name("Gabriel Adriano Rodrigues")
                                        .email("gabriel.2020.adriano@gmail.com"))
                                    );
    }
}
