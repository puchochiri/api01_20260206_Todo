package org.zerock.api01.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Boot 01 Project Swagger",
                version = "v1.0",
                description = "Spring Boot 3.x API 문서",
                contact = @Contact(name = "관리자", email = "admin@example.com")
        ),
        servers = {
                @Server(url = "http://localhost:9060", description = "Local Server")
        }
/*        ,security = { // 추가
               @SecurityRequirement(name = "Authorization") // 모든 api 기본 적용 추가
        }*/
)
@SecurityScheme( // 추가
        name="Authorization",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization" // Bear Token 입력하는 Header 이름
)
public class SwaggerConfig {
        //http://localhost:9050/swagger-ui/index.html
        // authorize test
        // http://localhost:9050/files/apiLogin.html//generate token 클릭//f12 콘솔 상 token 복사
        // 아래와 같이 swagger authorize 로그인 후 테스트
        // bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtaWQiOiJhcGl1c2VyMTAiLCJpYXQiOjE3NjU0Mzg3OTAsImV4cCI6MTc2NTQzODg1MH0.J0U921Eu3GAbfRJswuAjM2Dus4Z0ILozrordYqUt5S4

        @Bean
        public OpenAPI customOpenAPI(){
                return new OpenAPI()
                        .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("Authorization"))
                        .components(new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes("Authorization",
                                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                                .name("Authorization")
                                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.APIKEY)
                                                .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                                                .description("JWT Bearer Token 입력하세요.")
                                )

                        );

        }

        /*  /api/ 경로만 Authorization 필요하도록 설정 */
        @Bean
        public OpenApiCustomizer securityApiCustomizer(){
                return openApi -> openApi.getPaths().forEach((path, pathItem) -> {
                        if(path.startsWith("/api/")) {
                                pathItem.readOperations().forEach(operation ->
                                        operation.addSecurityItem(new SecurityRequirement().addList("Authorization")));
                        }
                });
        }


}