package bookService;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import be.pvgroup.services.book.Book;
import be.pvgroup.services.book.BookController;
import be.pvgroup.services.book.BookNotFoundException;
import be.pvgroup.services.book.BookRepository;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerByIdTest {

	private static final Long ID = 15152L;
	@InjectMocks private BookController controller;
	private Book book = new Book();

	@Mock private BookRepository repository;
	
	@Test
	public void bookIsReturned(){
		Mockito.when(repository.findById(ID)).thenReturn(book);
		ResponseEntity<Book> result = controller.byId(ID);
		Assert.assertThat(result, CoreMatchers.is(ResponseEntity.ok(book)));
	}
	
	@Test(expected = BookNotFoundException.class)
	public void bookNotFoundExceptionIsReturnedWhenIdNotFound(){
		Mockito.when(repository.findById(ID)).thenReturn(null);
		controller.byId(ID);
	}
	
}
