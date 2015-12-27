package com.tw.bookshelf.intg;


import com.google.gson.Gson;
import com.tw.bookshelf.SpringBootWebApplication;
import com.tw.bookshelf.controller.BookShelfController;
import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.entity.Category;
import com.tw.bookshelf.repository.BookRepository;
import com.tw.bookshelf.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootWebApplication.class)
@WebAppConfiguration
@Transactional
public class BookShelfControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private BookShelfController bookShelfController;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookShelfController).build();

    }

    @Test
    public void should_search_book_by_isbn_successfully() throws Exception {
        bookRepository.deleteAll();
        Book book = bookRepository.save(new Book("book-isbn", "book-name", "book-author", 32.5));

        String isbn = book.getIsbn();
        mockMvc.perform(get("/book/get/{isbn}", isbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.price").value(book.getPrice()));

    }

    @Test
    public void should_be_able_to_add_book_to_shelf() throws Exception {
        bookRepository.deleteAll();
        Book book = new Book("1234567890", "Hello World", "sqlin", 54.2);
        String bookJson = new Gson().toJson(book);

        mockMvc.perform(post("/book/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()));


    }


    @Test
    public void should_add_book_conflict_when_book_already_exists() throws Exception {
        Book existedBook = new Book("9780201485677", "Hello World", "sqlin", 54.2);

        mockMvc.perform(post("/book/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(new Gson().toJson(existedBook)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is("CONFLICT")))
                .andExpect(jsonPath("$.error", is("Book Conflict")));
    }

    @Test
    public void should_search_book_by_title_fuzzily() throws Exception {
        bookRepository.deleteAll();
        bookRepository.save(Arrays.asList(new Book("12345", "Head First Java", "you", 55.6),
                new Book("45678", "Basic Java Learning", "she", 32.5),
                new Book("89234", "Other Books Basic", "me", 12.5)));

        String titleFuzzyFilter = "Java";

        mockMvc.perform(get("/book/title/{title}", titleFuzzyFilter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Head First Java"))
                .andExpect(jsonPath("$[1].title").value("Basic Java Learning"));

    }

    @Test
    public void should_search_book_by_category_name() throws Exception {
        Category category = new Category("C123456", "Category Name", "Category Description");
        categoryRepository.save(category);
        Book book1 = new Book("12345", "Hello1", "monkey1", 23.5);
        Book book2 = new Book("22345", "Hello2", "monkey2", 23.5);
        Book book3 = new Book("32345", "Hello3", "monkey3", 23.5);
        book1.setCategory(category);
        book2.setCategory(category);
        bookRepository.deleteAll();
        bookRepository.save(Arrays.asList(book1, book2, book3));

        mockMvc.perform(get("/book/category/{name}", category.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value(book1.getTitle()))
                .andExpect(jsonPath("$.[1].title").value(book2.getTitle()));

    }

    @Test
    public void should_return_book_list_order_by_price() throws Exception {
        Book book1 = new Book("12345", "Hello1", "monkey1", 23.5);
        Book book2 = new Book("22345", "Hello2", "monkey2", 23.6);
        Book book3 = new Book("32345", "Hello3", "monkey3", 23.7);
        bookRepository.deleteAll();
        bookRepository.save(Arrays.asList(book1, book2, book3));

        mockMvc.perform(get("/book/order/price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].price").value(book3.getPrice()))
                .andExpect(jsonPath("$.[1].price").value(book2.getPrice()))
                .andExpect(jsonPath("$.[2].price").value(book1.getPrice()));
    }

    @Test
    public void should_return_books_list_by_page() throws Exception {
        bookRepository.deleteAll();
        for (int i = 0; i < 12; i++) {
            bookRepository.save(new Book("123" + i, "book name" + i, "author", 10.0 + i));
        }

        mockMvc.perform(get("/book/get/{start}/{pages}", 0, 10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));

        mockMvc.perform(get("/book/get/{start}/{pages}", 1, 10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void the_return_books_size_should_be_12() throws Exception {
        bookRepository.deleteAll();
        for (int i = 0; i < 12; i++) {
            bookRepository.save(new Book("123" + i, "book name" + i, "author", 10.0 + i));
        }

        mockMvc.perform(get("/book/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(12));
    }

    @Ignore
    @Test
    public void should_return_books_by_page_and_sort() throws Exception {
        bookRepository.deleteAll();
        for (int i = 0; i < 12; i++) {
            bookRepository.save(new Book("123" + i, "book name" + i, "author", 10.0 + i));
        }
        Sort sort = new Sort(Sort.Direction.DESC, "price");
        PageRequest pageable = new PageRequest(0, 10, sort);
        System.out.println(new Gson().toJson(pageable));
        mockMvc.perform(get("/book/page").contentType(MediaType.APPLICATION_JSON_UTF8).content(new Gson().toJson(pageable)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$.[9].price").value(12.0));

    }
}
