package de.workshops.bookdemo.books;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
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
    
    @Test
    public void testWithRestAssured() {
        given().log().all().when().get(BookRestController.REQUEST_URL).then().log().all().body("[1].title", equalTo("Clean Code"));
    }
    
    @Test
    public void testCreateBooks() throws Exception {
        Book book = Book.builder()
                .title("New Book")
                .author("The Author")
                .description("content")
                .isbn("1234567890")
                .build();
        mockMvc.perform(post(BookRestController.REQUEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is("New Book")));
    }

    @Test
    public void testUpdateBooks() throws Exception {
        Book book = Book.builder()
                .title("New Book")
                .author("The Author")
                .description("content")
                .isbn("1234567890")
                .build();
        mockMvc.perform(post(BookRestController.REQUEST_URL + "/abc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is("New Book")));
    }

}
