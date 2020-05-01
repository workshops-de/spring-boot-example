package de.workshops.bookdemo.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String isbn;
    
    private String title;
    
    private String author;
    
    private String description;
    
    
}
