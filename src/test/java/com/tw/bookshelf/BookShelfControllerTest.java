package com.tw.bookshelf;

import com.google.gson.Gson;
import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.util.BookBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootWebApplication.class)
@WebAppConfiguration
public class BookShelfControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void ShouldReturnBookListWhenTestGetAllBooksMethod() throws Exception {
        Book first = new BookBuilder()
                .isbn("9780201485677")
                .name("Refactoring")
                .author("Martin Fowler")
                .price(64.99).build();
        Book second = new BookBuilder()
                .isbn("9780132350884")
                .name("Clean Code")
                .author("Robert C. Martin")
                .price(35.44).build();
        Book third = new BookBuilder()
                .isbn("9780131429017")
                .name("The Art of UNIX Programming")
                .author("Eric S. Raymond")
                .price(39.99).build();
        List<Book> books = Arrays.asList(first, second, third);
        mockMvc.perform(get("/book/get"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(3)))
                /*.andExpect(jsonPath("$[0].isbn", is(third.getIsbn())))
                .andExpect(jsonPath("$[0].name", is(third.getName())))
                .andExpect(jsonPath("$[0].author", is(third.getAuthor())))
                .andExpect(jsonPath("$[0].price", is(third.getPrice())))
                .andExpect(jsonPath("$[1].isbn", is(first.getIsbn())))
                .andExpect(jsonPath("$[1].name", is(first.getName())))
                .andExpect(jsonPath("$[1].author", is(first.getAuthor())))
                .andExpect(jsonPath("$[1].price", is(first.getPrice())))
                .andExpect(jsonPath("$[2].isbn", is(second.getIsbn())))
                .andExpect(jsonPath("$[2].name", is(second.getName())))
                .andExpect(jsonPath("$[2].author", is(second.getAuthor())))
                .andExpect(jsonPath("$[2s].price", is(second.getPrice())))*/;
    }


    @Test
    public void shouldReturnDetailOfBookWhichIsbnIs9780201485677() throws Exception {
        Book expectBook = new BookBuilder()
                .isbn("9780201485677")
                .name("Refactoring")
                .author("Martin Fowler")
                .price(64.99).build();
        mockMvc.perform(get("/book/get/{isbn}", expectBook.getIsbn()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.isbn", is(expectBook.getIsbn())))
                .andExpect(jsonPath("$.author", is(expectBook.getAuthor())))
                .andExpect(jsonPath("$.price", is(expectBook.getPrice())))
                .andExpect(jsonPath("$.name", is(expectBook.getName())));
    }

    @Test
    public void theReturnStatusShouldBe404WhenBookIsNotExist() throws Exception {
        mockMvc.perform(get("/book/get/3423532"))
        .andExpect(status().is(404))
        .andExpect(jsonPath("$.status", is("NOT_FOUND")))
        .andExpect(jsonPath("$.error", is("Book Not Found")));
    }


    @Test
    public void theReturnStatusShouldBe200WhenDeleteBookSuccessfulAndTheMethodIsDelete() throws Exception {

        mockMvc.perform(delete("/book/delete/{isbn}", "9780201485677"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.isbn", is("9780201485677")));
    }

    @Test
    public void theReturnStatusShouldBe405WhenDeleteBookSuccessfulAndTheMethodIsGet() throws Exception {

        mockMvc.perform(get("/book/delete/{isbn}", "9780131429017"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(405));
    }

    @Test
    public void theReturnStatusShouldBe404WhenDeleteBookAndTheMethodIsDeleteButTheBookINotExist() throws Exception {

        mockMvc.perform(delete("/book/delete/{isbn}", "97802014856778"))
                .andExpect(status().is(404))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.error", is("Book Not Found")));
    }

    @Test
    public void theReturnStatusShouldBe201WhenAddBookTheMethodIsPost() throws Exception {
        Book book = new BookBuilder()
                .isbn("9780201485456775")
                .name("test")
                .author("Martin Fowler")
                .price(64.99).build();
        mockMvc.perform(post("/book/add").contentType("application/json;charset=UTF-8").content(new Gson().toJson(book)))
                .andExpect(status().is(201))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.isbn", is(book.getIsbn())));

        mockMvc.perform(get("/book/get"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(4)));

        /*mockMvc.perform(post("/book/add").contentType("application/json;charset=UTF-8").content(new Gson().toJson(book)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status", is(500)))
                .andExpect(jsonPath("$.message", is("This book is exist")));

        mockMvc.perform(get("/book/get"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(4)));*/
    }

    @Test
    public void theReturnStatusShouldBe404WhenUpdateBookButBookIsNotExist() throws Exception {
        Book book = new BookBuilder()
                .isbn("9780201485456775")
                .name("test")
                .author("Martin Fowler")
                .price(64.99).build();

        mockMvc.perform(put("/book/update").contentType("application/json;charset=UTF-8").content(new Gson().toJson(book)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.error", is("Book Not Found")));
    }

    @Test
    public void theReturnStatusShouldBe200WhenUpdateExistBookAndTheMethodIsPut() throws Exception {
        Book book = new BookBuilder()
                .isbn("9780132350884")
                .name("Clean Code")
                .author("Robert C. Martin")
                .price(35.44).build();

        book.setPrice(55.5);

        mockMvc.perform(put("/book/update").contentType("application/json;charset=UTF-8").content(new Gson().toJson(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(55.5)));
    }

    @Test
    public void theReturnStatusShouldBe405WhenUpdateExistBookAndTheMethodIsPost() throws Exception {
        Book book = new BookBuilder()
                .isbn("9780132350884")
                .name("Clean Code")
                .author("Robert C. Martin")
                .price(35.44).build();

        book.setPrice(55.5);

        mockMvc.perform(post("/book/update").contentType("application/json;charset=UTF-8").content(new Gson().toJson(book)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(405));
    }


}
