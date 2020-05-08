package de.workshops.bookdemo.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(path = BookRestController.REQUEST_URL)
public class BookRestController {

    public static final String REQUEST_URL = "/book";
    
    @NonNull
    private BookService bookService;
    
    
    @GetMapping
    public List<Book> getAllBooks() throws Exception {
        return bookService.loadAll();
    }

    @GetMapping(path = "/{isbn}")
    public Book getSingleBooks(@PathVariable(required = true, name = "isbn") String isbn) throws Exception {
        return bookService.loadSingle(isbn)
                .orElseThrow(() -> new BookException(String.format("isbn %s not found", isbn)));
    }

    @PostMapping
    public Book createBook(@RequestBody(required = true) Book book) throws Exception {
        return bookService.createBook(book);
    }

    @PostMapping(path = "/{isbn}")
    public Book updateBook(@PathVariable(required = true, name = "isbn") String isbn, 
            @RequestBody(required = true) Book book) throws Exception {
        return bookService.createBook(book);
    }
    
    
    
    
}
