package bookService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import be.pvgroup.services.book.Book;
import be.pvgroup.services.book.BookRepository;
import be.pvgroup.services.book.BookWebApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BookWebApplication.class)
@WebAppConfiguration
public class BookControllerByIdIntegrationTest {

	private static final String BOOK_AUTHOR = "a random author";
	private static final String BOOK_TITLE = "a new create book";
	private static final String BOOK_DESCRIPTION = "lots of text";
	private static final String NOT_EXISTING_ID = "9";
	private static final String BOOK_ISBN = "a isbn";
	private static final Long BOOK_ID = 2L;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private Book book = new Book();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.bookRepository.deleteAll();

        Book newBook = new Book();
        newBook.setId(BOOK_ID);
        newBook.setTitle(BOOK_TITLE);
        newBook.setAuthor(BOOK_AUTHOR);
        newBook.setDescription(BOOK_DESCRIPTION);
        newBook.setIsbn(BOOK_ISBN);
        this.book = bookRepository.save(newBook);
    }

    @Test
    public void bookNotFound() throws Exception {
        mockMvc.perform(get("/books/" + NOT_EXISTING_ID)
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleBook() throws Exception {
        mockMvc.perform(get("/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", CoreMatchers.is(book.getId().intValue())))
                .andExpect(jsonPath("$.title", CoreMatchers.is(book.getTitle())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(book.getDescription())))
                .andExpect(jsonPath("$.isbn", CoreMatchers.is(book.getIsbn())))
                .andExpect(jsonPath("$.author", CoreMatchers.is(book.getAuthor())));
    }
}
