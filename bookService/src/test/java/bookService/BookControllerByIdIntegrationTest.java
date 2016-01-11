package bookService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import be.pvgroup.services.book.Book;
import be.pvgroup.services.book.BookRepository;
import be.pvgroup.services.book.BookWebApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BookWebApplication.class)
@WebAppConfiguration
public class BookControllerByIdIntegrationTest {

	private static final String BOOK_AUTHOR = "a random author";
	private static final String BOOK_TITLE = "a new create book";
	private static final String BOOK_DESCRIPTION = "lots of text";
	private static final String NOT_EXISTING_ID = "9";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;


    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Book book = new Book();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.bookRepository.deleteAll();

        Book newBook = new Book();
        newBook.setId(1L);
        newBook.setTitle(BOOK_TITLE);
        newBook.setAuthor(BOOK_AUTHOR);
        newBook.setDescription(BOOK_DESCRIPTION);
        this.book = bookRepository.save(newBook);
    }

    @Test
    public void bookNotFound() throws Exception {
        mockMvc.perform(get("/books/books/" + NOT_EXISTING_ID)
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleBook() throws Exception {
        mockMvc.perform(get("/books/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", CoreMatchers.is(this.book.getId().intValue())))
                .andExpect(jsonPath("$.title", CoreMatchers.is(BOOK_TITLE)))
                .andExpect(jsonPath("$.description", CoreMatchers.is(BOOK_DESCRIPTION)))
                .andExpect(jsonPath("$.author", CoreMatchers.is(BOOK_AUTHOR)));
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
