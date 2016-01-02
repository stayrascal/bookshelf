package com.tw.bookshelf.unit;

import com.tw.bookshelf.core.exception.CategoryNotFoundException;
import com.tw.bookshelf.entity.Book;
import com.tw.bookshelf.entity.Category;
import com.tw.bookshelf.repository.BookRepository;
import com.tw.bookshelf.repository.CategoryRepository;
import com.tw.bookshelf.service.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void should_find_book_by_title_when_given_title() {

        Iterable<Book> exceptedBooks = Collections.singletonList(
                new Book("12345", "Head Fist Java", "author", 55.0)
        );

        String title = "Java";
        when(bookRepository.findByTitle(title)).thenReturn(exceptedBooks);

        Iterable<Book> books = bookService.finByTitle(title);

        assertThat(exceptedBooks, is(books));
    }

    @Test
    public void should_find_book_by_tag_when_given_valid_tag() {
        Iterable<Book> expextedBooks = Collections.emptyList();
        Category category = new Category("B011", "IT", "This is a description");
        when(categoryRepository.findByName(category.getName())).thenReturn(category);
        when(bookRepository.findByCategoryCode(category.getCode())).thenReturn(expextedBooks);

        Iterable<Book> books = bookService.findByCategoryName(category.getName());

        assertThat(expextedBooks, is(books));
    }

    @Test(expected = CategoryNotFoundException.class)
    public void should_throw_exception_when_tag_not_found() {
        String categoryName = "category name";
        when(categoryRepository.findByName(categoryName)).thenReturn(null);

        bookService.findByCategoryName(categoryName);
    }
}
