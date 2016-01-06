package be.pvgroup.services.book;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import(BookWebApplication.class)
public class BooksServer {

	@Autowired
	protected BookRepository bookRepository;

	protected Logger logger = Logger.getLogger(BooksServer.class.getName());
	
	public static void main(String[] args) {
		SpringApplication.run(BooksServer.class, args);
	}
}
