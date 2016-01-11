package be.pvgroup.services.book;

		import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api
@RestController
@RequestMapping(value = "/books")
public class BookController {

	@Inject BookRepository bookRepository;
	
	@ApiOperation(value = "get a book by ID")
	@RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> byId(@ApiParam(value = "", required = true)@PathVariable("id") Long id) {
		Book book = bookRepository.findById(id);

		if (book == null)
			throw new BookNotFoundException(id);
		else {
			return ResponseEntity.ok(book);
		}
	}

	@ApiOperation(value = "create a book")
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Book book) {
		Book result = bookRepository.save(book);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);

	}
	
	@ApiOperation(value = "get all books")
	@RequestMapping( method = RequestMethod.GET )
	ResponseEntity<?> findAll() {
		List<Book> books = bookRepository.findAll();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<>(books, httpHeaders, HttpStatus.OK);	}
	
	@ApiOperation(value = "delete a book by ID")
	@RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
	ResponseEntity<?> deleteById(@ApiParam(value = "", required = true)@PathVariable("id") Long id) {
		bookRepository.delete(id);
		return new ResponseEntity<>(HttpStatus.GONE);
	}
}
