package com.iconpln.microservices.book;

import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;


@Path("/api/books")
@Tag(name = "Book REST Endpoint")
public class BookResource {
    @Inject
    Logger logger;
    @Inject
    @RestClient
    NumberProxy proxy;
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(summary = "Creates a Book",
    description = "Creates a Book with an ISBN")
    @Retry(maxRetries = 1, delay = 3000)
    @Fallback(fallbackMethod = "fallingbackOnCreatingBook")
    public Response createBook(@FormParam("title") String title,
                               @FormParam("author") String author,
                               @FormParam("year") int yearOfPublication,
                               @FormParam("genre") String genre){
        Book book = new Book();
        book.isbn13 = proxy.generateIsbnNumbers().isbn13;
        book.author = author;
        book.title = title;
        book.yearOfPublication = yearOfPublication;
        book.genre = genre;
        book.creationDate = Instant.now();
        logger.info("Book saved : "+ book);
        return Response.status(201).entity(book).build();
    }
    public Response fallingbackOnCreatingBook(String title,
                               String author,
                               int yearOfPublication,
                               String genre){
        Book book = new Book();
        book.isbn13 = "Will be set later";
        book.author = author;
        book.title = title;
        book.yearOfPublication = yearOfPublication;
        book.genre = genre;
        book.creationDate = Instant.now();
        saveBookOnDisk(book);
        logger.warn("Book saved on disk : "+book);
        return Response.status(201).entity(book).build();
    }

    private void saveBookOnDisk(Book book) {
        String bookJson = JsonbBuilder.create().toJson(book);
        try {
            PrintWriter out = new PrintWriter("book-"+Instant.now().toEpochMilli()+".json");
            out.println(bookJson);
            out.close();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
