package de.workshops.bookdemo.books;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ValueStore {

    private static ObjectMapper mapper = new ObjectMapper();
    
    public static List<Book> books() throws Exception {
        return Arrays.asList(mapper.readValue(new File("target/classes/books.json"), Book[].class));
    }
    
}
