package be.pvgroup.services.book;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api
@RestController
public class BookController {

	@Inject BookRepository bookRepository;
	
	@ApiOperation(value = "get book by ID")
	@RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> byId(@ApiParam(value = "", required = true)@PathVariable("id") Long id) {
		Book book = bookRepository.findById(id);

		if (book == null)
			throw new BookNotFoundException(id);
		else {
			return ResponseEntity.ok(book);
		}
	}

}
