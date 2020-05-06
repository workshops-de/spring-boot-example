package de.workshops.bookdemo.books;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookControllerTest {

    @Autowired
    private BookRestController bookRestController;
    
    
    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = bookRestController.getAllBooks();
        assertNotNull(books);
        assertEquals(3, books.size());
    }
    
    
}
