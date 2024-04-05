package com.iconpln.microservices.book;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(title = "Book API",
                description = "Create Books",
                version = "1.0",
                contact = @Contact(name = "Sony Wibisono")
        ),
        tags = {
                @Tag(name = "api",description = "Public API"),
                @Tag(name = "books",description = "Interested in Books")
        }
)
public class BookMicroservice extends Application {

}
