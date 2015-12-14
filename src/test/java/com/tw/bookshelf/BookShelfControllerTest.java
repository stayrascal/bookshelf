package com.tw.bookshelf;

import com.jayway.restassured.http.ContentType;
import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.entity.BookBuilder;
import com.tw.bookshelf.service.BookService;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootWebApplication.class)
@WebAppConfiguration
public class BookShelfControllerTest {

    @Autowired
    BookService bookService;

    @Test
    public void shouldReturnDetailOfBookWhichIsbnIs9780201485677(){
        Book expectBook = new BookBuilder()
                .isbn("9780201485677")
                .name("Refactoring")
                .author("Martin Fowler")
                .price(64.99).build();
       when().get("/book/get/" + expectBook.getIsbn())
               .then().statusCode(HttpStatus.SC_OK)
               .and().contentType(ContentType.JSON)
               .and().body("params.data.isbn", equalTo(expectBook.getIsbn()))
               .and().body("params.data.author", equalTo(expectBook.getAuthor()))
               //.and().body("params.data.price", equalTo(expectBook.getPrice().toString()))
               .and().body("params.data.name", equalTo(expectBook.getName()));
    }

    @Test
    public void theReturnStatusShouldBe200WhenDeleteBookSuccessfulAndTheMethodIsDelete(){
        when().delete("/book/delete/9780201485677")
                .then().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().body("status", equalTo(200))
                .and().body("message", equalTo("ok"));
    }

    @Test
    public void theReturnStatusShouldBe200WhenDeleteBookSuccessfulAndTheMethodIsGet(){
        when().get("/book/delete/9780131429017")
                .then().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void theReturnStatusShouldBe500WhenDeleteBookFaildAndTheMethodIsDelete(){
        when().delete("/book/delete/97802014856778")
                .then().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().body("status", equalTo(500))
                .and().body("message", equalTo("This book is not exist"));
    }

   /* @Test
    public void theReturnStatusShouldBe200WhenAddBook(){
        Book book = new BookBuilder()
                .isbn("97802014856775")
                .name("Refactoring")
                .author("Martin Fowler")
                .price(64.99).build();
        when().post("/book/add", book)
                .then().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON)
                .and().body("status", equalTo(200))
                .and().body("message", equalTo("ok"));
    }*/


}
