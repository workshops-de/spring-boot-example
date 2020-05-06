package de.workshops.bookdemo.books;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Repository
public class BookRepository {

    @NonNull
    private ObjectMapper mapper;
    
    public List<Book> findAll() throws Exception {
        return Arrays.asList(mapper.readValue(new File("target/classes/books.json"), Book[].class));
    }

    public Optional<Book> findByIsbn(String isbn) throws Exception {
        return Arrays.asList(mapper.readValue(new File("target/classes/books.json"), Book[].class))
                .stream().filter(book -> book.getIsbn().equalsIgnoreCase(isbn)).findFirst();
    }
}
