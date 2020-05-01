package de.workshops.bookdemo.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookRestController {

    @NonNull
    private BookService bookService;
    
    
    @GetMapping(path = "/book")
    public List<Book> getAllBooks() throws Exception {
        return bookService.loadAll();
    }
}
