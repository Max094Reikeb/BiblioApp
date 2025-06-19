package dev.school.app.biblioapp.views;

import dev.school.app.biblioapp.models.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BookPageTest {

    @Test
    public void testBookPageCreation() throws IOException {
        PDDocument document = new PDDocument();

        Book book = new Book(
                "TitreTest",
                "PrenomAuteur",
                "NomAuteur",
                "Description du livre",
                2020,
                1, 1,
                "https://example.com/image.png",
                false
        );

        // Création de la page PDF avec le livre
        new BookPage(document, book);

        // Le document doit contenir une page (la nouvelle ajoutée)
        assertEquals(1, document.getNumberOfPages());

        PDPage page = document.getPage(0);
        assertNotNull(page);
        
        document.close();
    }
}
