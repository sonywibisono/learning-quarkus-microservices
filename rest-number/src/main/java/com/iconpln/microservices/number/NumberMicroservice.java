package com.iconpln.microservices.number;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(title = "Number API",
                description = "Create Numbers",
                version = "1.0",
                contact = @Contact(name = "Sony Wibisono")
        ),
        tags = {
                @Tag(name = "api",description = "Public API"),
                @Tag(name = "numbers",description = "Generates ISBN Number")
        }
)
public class NumberMicroservice extends Application {
}
