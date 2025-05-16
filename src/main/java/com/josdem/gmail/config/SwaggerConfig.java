package com.josdem.gmail.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Value("${app.version}")
    private String version;
    /**
     * Specifies Api metadata
     */
    @Bean
    public OpenAPI appInfo() {
        Server prod = new Server()
                .url("https://gmailer.josdem.io/")
                .description("Production App link");

        Server dev = new Server()
                .url("http://localhost:8083/")
                .description("Development App link");


        //TODO confirm your contacts
        Contact contact = new Contact()
                .name("Jose Morales")
                .email("contact@josdem.io")
                .url("https://josdem.io");

        //TODO: you can add a .termsOfService()
        Info info = new Info()
                .title("Gmailer metadata")
                .description("Contains the contact, license, wiki and version info of the app.")
                .version(version)
                .contact(contact)
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0")
                        .identifier("Apache-2.0")
                );
        return new OpenAPI()
                .servers(new ArrayList<Server>(Arrays.asList(prod, dev)))
                .info(info)
                .externalDocs(new ExternalDocumentation()
                        .url("https://github.com/josdem/gmailer-spring-boot/wiki")
                        .description("Checkout the app's Wiki")
                );

    }
}
