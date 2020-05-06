package de.workshops.bookdemo.books;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BookControllerTest {

    @Autowired
    private BookRestController bookRestController;

    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }
    
    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = bookRestController.getAllBooks();
        assertNotNull(books);
        assertEquals(3, books.size());
    }
    
    @Test
    public void testGetAllBooksWithREST() throws Exception {
        
        // Eclipse Favorites (favorite static members of certain types
        mockMvc.perform(get(BookRestController.REQUEST_URL))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[1].title", is("Clean Code")));
    }

    @Test
    public void testGetAllBooksWithRESTResult() throws Exception {
        
        MvcResult mvcResult =  mockMvc.perform(get(BookRestController.REQUEST_URL))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        String jsonPayload = mvcResult.getResponse().getContentAsString();
        
        // pretty print existing (un-pretty) json string 
        Object json = mapper.readValue(jsonPayload, Object.class);
        log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
        
        Book[] books = mapper.readValue(jsonPayload, Book[].class);
        assertNotNull(books);
        assertEquals(3, books.length);
    }
    
    
}
