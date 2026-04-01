package com.articos.cancan.configurations.swagger;

import com.articos.cancan.security.jwt.role.*;
import io.swagger.v3.oas.models.*;
import org.springdoc.core.customizers.*;
import org.springframework.context.annotation.*;
import org.springframework.web.method.*;

@Configuration
public class SwaggerApiAccessCustomizer {

    @Bean
    public OperationCustomizer accessLevelCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            boolean isAdmin =
                    handlerMethod.hasMethodAnnotation(AdminOnly.class) ||
                            handlerMethod.getBeanType().isAnnotationPresent(AdminOnly.class);

            boolean isMember =
                    handlerMethod.hasMethodAnnotation(MemberAccess.class) ||
                            handlerMethod.getBeanType().isAnnotationPresent(MemberAccess.class);

            String accessDescription = null;

            if (isAdmin) {
                accessDescription = "Acesso: somente ADMIN.";
                operation.addExtension("x-access-level", "ADMIN");
            } else if (isMember) {
                accessDescription = "Acesso: MEMBER ou superior.";
                operation.addExtension("x-access-level", "MEMBER");
            }

            if (accessDescription != null) {
                String currentDescription = operation.getDescription();
                if (currentDescription == null || currentDescription.isBlank()) {
                    operation.setDescription(accessDescription);
                } else {
                    operation.setDescription(currentDescription + "\n\n" + accessDescription);
                }
            }

            return operation;
        };
    }
}