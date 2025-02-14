package com.planetsystems.api.school.staffTransfer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "wanfadger",
                        email = "galiwango@gmail.com",
                        url = "https://portfolio.com"
                ),
                description = "Open Api documentation for Tela schools staff transfer",
                title = "Tela Schools Staff Transfer Api Documentation",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://license.com"
                ),
                termsOfService = "Term of service"
        ),
        servers = {
                @Server(
                        description = "DEV",
                        url = "http://192.168.1.116:1408"
//                        url = "http://localhost:1408"
                ),
//                @Server(description = "PROD", url = "http://192.168.1.116:1408"),
//                @Server(description = "TEST-LIVE", url = "https://schoolsdir-priv-d.telaschool.org")

        }

//        security = {
//                @SecurityRequirement(
//                        name = "bearerAuth"
//                )
//        }

)
//@SecurityScheme(name = "bearerAuth",
//        description = "JWT auth Description",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
public class OpenApiConfig {
}
