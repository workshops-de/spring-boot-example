package de.workshops.bookdemo.books;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRestController {

    @GetMapping(path = "/book")
    public List<Book> getAllBooks() throws Exception {
        return ValueStore.books();
    }
}
