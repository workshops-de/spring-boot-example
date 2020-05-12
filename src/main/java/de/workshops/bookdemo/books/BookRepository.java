package de.workshops.bookdemo.books;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@Repository
public class BookRepository {

    @NonNull
    private ObjectMapper mapper;
    
    List<Book> books = new ArrayList<Book>();
    
    
    public List<Book> findAll() throws Exception {
        books.addAll(Arrays.asList(mapper.readValue(new File("target/classes/books.json"), Book[].class)));
        return books;
    }

    public Optional<Book> findByIsbn(String isbn) throws Exception {
        return Arrays.asList(mapper.readValue(new File("target/classes/books.json"), Book[].class))
                .stream().filter(book -> book.getIsbn().equalsIgnoreCase(isbn)).findFirst();
    }

    public Book create(Book book) {
        books.add(book);
        return book;
    }
}
