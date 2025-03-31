package me.dio.matheusmarti.springapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API para Sistema de Estoque e Vendas")
                        .version("1.0")
                        .description("API para gestão de produtos, categorias, clientes e pedidos")
                        .contact(new Contact()
                                .name("Matheus Augusto Marti")
                                .email("matheusmarti@hotmail.com")
                                .url("https://github.com/Matheus-Marti1")));
    }
}
