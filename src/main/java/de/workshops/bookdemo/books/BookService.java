package de.workshops.bookdemo.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    @NonNull
    private BookRepository bookRepository;

    public List<Book> loadAll() throws Exception {
        return bookRepository.findAll();
    }
    
}
